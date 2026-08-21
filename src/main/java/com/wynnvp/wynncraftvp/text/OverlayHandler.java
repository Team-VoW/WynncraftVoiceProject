/*
 * Copyright © Team-VoW 2024-2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.logging.VowLogger;
import com.wynnvp.wynncraftvp.utils.LineFormatter;
import com.wynnvp.wynncraftvp.utils.Utils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;

/**
 * Handles Wynncraft overlay-based dialogue (used in areas like Fruma).
 * Kept entirely separate from the existing chat-based dialogue system.
 *
 * <p>This class is a thin Minecraft adapter: it extracts text from {@link Component}
 * objects and wires Minecraft-dependent side effects (sound playback, config reads,
 * logging) into the pure {@link OverlayStateMachine} via {@link OverlayDialogueListener}.
 *
 * <p>All tick-based stability logic, player-name substitution, and early play key
 * comparison live in {@link OverlayStateMachine}, which has no {@code net.minecraft.*}
 * dependencies and is directly unit-testable.
 */
public final class OverlayHandler implements OverlayDialogueListener {
    // Matched as substrings, because the real fonts are namespaced and numbered per line:
    // minecraft:hud/dialogue/text/wynncraft/body_0 … body_3, minecraft:hud/dialogue/text/nameplate.
    // The nameplate needle keeps its "text/" segment on purpose — the box also ships a
    // hud/dialogue/style/default/nameplate font, which draws the plate itself and carries no name.
    private static final String OVERLAY_BODY_FONT = "dialogue/text/wynncraft/body";
    private static final String OVERLAY_NAMEPLATE_FONT = "dialogue/text/nameplate";
    private static final String PLAYER_REPLACEMENT = "soldier";

    /**
     * How long a seen choice keeps blocking auto progress. Wynncraft repeats the overlay every tick
     * while it is up, so this only has to outlive a brief gap in the stream, not a whole prompt.
     */
    private static final int CHOICE_MEMORY_TICKS = 20;

    /** Ceiling on the one-off font report, so a server cannot turn it into a log flood. */
    private static final int MAX_REPORTED_FONTS = 40;

    private final OverlayStateMachine stateMachine;

    /** Fonts already reported, so each one is logged once per session rather than every tick. */
    private final Set<String> seenFonts = new HashSet<>();

    private String lastLoggedTree = null;
    private long choiceSeenTick = Long.MIN_VALUE;
    private long choiceStartTick = Long.MIN_VALUE;
    private String choiceSignature = "";
    private String choiceReason = null;

    public OverlayHandler() {
        stateMachine = new OverlayStateMachine(
                () -> Objects.requireNonNull(Utils.mc().level).getGameTime(),
                this,
                () -> ModCore.config.isEarlyPlayOverlay(),
                OverlayHandler::replacePlayerName);
    }

    public void onConnectionChange() {
        stateMachine.reset();
        seenFonts.clear();
        lastLoggedTree = null;
        choiceSeenTick = Long.MIN_VALUE;
        choiceStartTick = Long.MIN_VALUE;
        choiceSignature = "";
        choiceReason = null;
    }

    public void onTick() {
        stateMachine.onTick();
    }

    public void onOverlayReceived(Component content) {
        long tick = Utils.mc().level == null ? 0L : Utils.mc().level.getGameTime();

        List<DialogueChoiceDetector.Segment> segments = flatten(content);
        reportNewFonts(segments);
        // Wynncraft owns this overlay for the health and mana bar too, and that arrives every tick.
        // Only dialogue is asked the choice question, so a stray number in a status bar cannot put
        // auto progress on hold. isVoiceDialogActive() is still the previous packet's answer here,
        // which is what covers a choice that replaces the dialogue body outright.
        if (stateMachine.isVoiceDialogActive() || hasDialogueFont(segments)) {
            updateChoiceState(segments, tick);
        }

        String body = extractAllBodyText(content);
        String npc = extractFontText(content, OVERLAY_NAMEPLATE_FONT);
        if (ModCore.config.isLogOverlayPackets() && Utils.mc().level != null) {
            VowLogger.logComment("feed(" + tick + ", \"" + body + "\", \"" + npc + "\")");
            logTreeIfChanged(segments);
        }
        stateMachine.onTextReceived(body, npc);
    }

    public boolean isVoiceDialogActive() {
        return stateMachine.isVoiceDialogActive();
    }

    /** True while the current line's text is still being typed out. See the state machine for why. */
    public boolean isTypewriterRunning() {
        return stateMachine.isTypewriterRunning();
    }

    /**
     * True while Wynncraft is showing something that looks like a list of answers.
     *
     * <p>Deliberately time limited rather than cleared on the next packet: the answer to "may auto
     * progress press sneak" has to stay "no" across the odd tick where the overlay arrives empty or
     * half rendered, and it is only ever consulted a fraction of a second after the last packet.
     */
    public boolean isDialogueChoiceOnScreen() {
        if (choiceSeenTick == Long.MIN_VALUE) return false;
        long tick = Utils.mc().level == null ? 0L : Utils.mc().level.getGameTime();
        return tick - choiceSeenTick <= CHOICE_MEMORY_TICKS;
    }

    /**
     * True when an answer is still wanted for a question posed at or after {@code sinceTick}.
     *
     * <p>Wynncraft does not take the answer list down once it is answered — it stays on screen while
     * the reply plays — so "a choice box is visible" is not the same as "the dialogue is waiting".
     * What settles it is time: the server will not send a fresh voice line while it is waiting for an
     * answer, so a line that started <em>after</em> the question appeared is proof the question was
     * already answered, and its box is just left over.
     *
     * <p>Callers pass the tick their line began. Ties block, since a question arriving in the same
     * tick a line starts is one that line is posing.
     */
    public boolean isChoiceAwaitingAnswerSince(long sinceTick) {
        return isDialogueChoiceOnScreen() && choiceStartTick >= sinceTick;
    }

    private void updateChoiceState(List<DialogueChoiceDetector.Segment> segments, long tick) {
        Optional<String> reason = DialogueChoiceDetector.reason(segments);
        if (reason.isEmpty()) return;

        String signature = DialogueChoiceDetector.signature(segments);
        boolean wasOnScreen = isDialogueChoiceOnScreen();
        // A different set of answers is a different question, even if the box never went away.
        boolean differentAnswers = !signature.isEmpty() && !signature.equals(choiceSignature);
        boolean isNew = !wasOnScreen || differentAnswers || !reason.get().equals(choiceReason);

        if (isNew) {
            choiceStartTick = tick;
        }
        choiceSeenTick = tick;
        choiceSignature = signature;
        choiceReason = reason.get();

        if (isNew) {
            ModCore.info("[AutoProgress] Dialogue choice detected at tick " + tick
                    + ", auto progress is held back for lines started from now on: " + choiceReason);
            if (ModCore.config.isLogOverlayDialogueToChat()) {
                Utils.sendMessage("§eDialogue choice detected §7(" + choiceReason + ")");
            }
            if (ModCore.config.isLogOverlayPackets()) {
                VowLogger.logComment("choice detected: " + choiceReason + " | " + describe(segments));
            }
        }
    }

    /**
     * Logs every font the overlay uses the first time it is seen.
     *
     * <p>Not gated behind a debug toggle and cheap after the first packet: which font Wynncraft puts
     * its answer list in is the one fact needed to make choice detection exact, and it has to be
     * recoverable from an ordinary log a player sends in.
     */
    private void reportNewFonts(List<DialogueChoiceDetector.Segment> segments) {
        for (DialogueChoiceDetector.Segment segment : segments) {
            // Sprite fonts carry an item or player id, so an unbounded server could mint a new name
            // per packet. Wynncraft's real font set is under ten.
            if (seenFonts.size() >= MAX_REPORTED_FONTS) return;
            if (!seenFonts.add(segment.font())) continue;
            ModCore.info("[VoW] New overlay font seen: " + segment.font() + " — first text: \""
                    + abbreviate(segment.text()) + "\"");
        }
    }

    private static boolean hasDialogueFont(List<DialogueChoiceDetector.Segment> segments) {
        for (DialogueChoiceDetector.Segment segment : segments) {
            if (segment.font().contains("dialogue")) return true;
        }
        return false;
    }

    private void logTreeIfChanged(List<DialogueChoiceDetector.Segment> segments) {
        String tree = describe(segments);
        if (tree.equals(lastLoggedTree)) return;
        lastLoggedTree = tree;
        VowLogger.logComment("overlay tree: " + tree);
    }

    private static String describe(List<DialogueChoiceDetector.Segment> segments) {
        StringBuilder out = new StringBuilder();
        for (DialogueChoiceDetector.Segment segment : segments) {
            if (out.length() > 0) out.append(" + ");
            out.append('[')
                    .append(segment.font())
                    .append("]\"")
                    .append(segment.text())
                    .append('"');
        }
        return out.toString();
    }

    private static String abbreviate(String text) {
        if (text == null) return "";
        return text.length() <= 40 ? text : text.substring(0, 40) + "…";
    }

    /**
     * Flattens the overlay into its styled runs.
     *
     * <p>{@code visit} walks the whole tree and hands back each run with its <em>resolved</em> style,
     * so a run inherits the font of whichever ancestor set it — which is exactly how Wynncraft builds
     * these components, with the text sitting a level below the styled node.
     */
    private static List<DialogueChoiceDetector.Segment> flatten(Component content) {
        List<DialogueChoiceDetector.Segment> segments = new ArrayList<>();
        content.visit(
                (style, text) -> {
                    if (!text.isEmpty()) {
                        segments.add(new DialogueChoiceDetector.Segment(fontName(style), text));
                    }
                    return Optional.empty();
                },
                Style.EMPTY);
        return segments;
    }

    private static String fontName(Style style) {
        FontDescription font = style.getFont();
        return font instanceof FontDescription.Resource resource ? resource.id().toString() : String.valueOf(font);
    }

    // OverlayDialogueListener implementation ----------------------------------------

    @Override
    public void onDialogueFired(String combined, String formattedLine, String finalKey, String npc) {
        if (ModCore.config.isLogOverlayDialogueToChat()) {
            Utils.sendMessage("§f" + combined);
        }
        // Narration has no speaker; pass "" so the reporter does not fall back to guessing one out
        // of the sentence itself.
        ModCore.instance.soundPlayer.playSound(LineFormatter.formatToLineData(formattedLine, npc == null ? "" : npc));
    }

    @Override
    public void onDialogueAlreadyPlayed(String combined, String formattedLine) {
        if (ModCore.config.isLogOverlayDialogueToChat()) {
            Utils.sendMessage("§f" + combined);
        }
        if (!ModCore.config.isOnlyLogNotPlayingLines() && ModCore.config.isLogDialogueLines()) {
            VowLogger.logLine(formattedLine + " [PLAYED]");
        }
    }

    @Override
    public void onWrongEarlyPlay() {
        ModCore.instance.soundPlayer.stopCurrentAudio();
    }

    @Override
    public String tryEarlyPlay(String rawCombined, String excludeKey) {
        String prefix =
                LineFormatter.formatToLineData(replacePlayerName(rawCombined)).getSoundLine();
        if (prefix.length() < ModCore.config.getEarlyPlayOverlayMinChars()) return null;

        var match = ModCore.instance.soundsHandler.findEarlyMatch(prefix);
        if (match.isEmpty()) return null;

        String key = match.get().getKey();
        if (key.equals(excludeKey)) return null;

        ModCore.instance.soundPlayer.playFromObject(match.get().getValue());
        return key;
    }

    // Text extraction (Minecraft Component API) ------------------------------------

    /**
     * Extracts and joins all body-font text segments from the component tree.
     * Delegates the codepoint-cleaning step to {@link #cleanBodyRawText(List)}.
     */
    private String extractAllBodyText(Component content) {
        List<String> parts = new ArrayList<>();
        collectFontText(content, OVERLAY_BODY_FONT, parts);
        return cleanBodyRawText(parts);
    }

    /**
     * Cleans a list of raw body-font strings by replacing high Unicode codepoints
     * (custom font separators used by Wynncraft) with spaces.
     *
     * <p>Package-private to allow direct testing without a Minecraft environment.
     */
    static String cleanBodyRawText(List<String> rawParts) {
        if (rawParts.isEmpty()) return null;

        StringBuilder cleaned = new StringBuilder();
        for (String part : rawParts) {
            for (int i = 0; i < part.length(); ) {
                int cp = part.codePointAt(i);
                if (cp > 0x00FF) {
                    if (cleaned.length() > 0 && cleaned.charAt(cleaned.length() - 1) != ' ') {
                        cleaned.append(' ');
                    }
                } else {
                    cleaned.appendCodePoint(cp);
                }
                i += Character.charCount(cp);
            }
            if (cleaned.length() > 0 && cleaned.charAt(cleaned.length() - 1) != ' ') {
                cleaned.append(' ');
            }
        }
        String result = cleaned.toString().trim();
        return result.isBlank() ? null : result;
    }

    private void collectFontText(Component component, String fontSubstring, List<String> results) {
        if (component.getStyle().getFont().toString().contains(fontSubstring)) {
            List<Component> siblings = component.getSiblings();
            if (!siblings.isEmpty()) {
                results.add(siblings.get(0).getString());
            }
            // Don't recurse into siblings of a matched font component — they belong to this
            // font span and would produce duplicate fragments (e.g. the wrapped second line).
            return;
        }
        for (Component sibling : component.getSiblings()) {
            collectFontText(sibling, fontSubstring, results);
        }
    }

    private String extractFontText(Component component, String fontSubstring) {
        if (component.getStyle().getFont().toString().contains(fontSubstring)) {
            List<Component> siblings = component.getSiblings();
            if (!siblings.isEmpty()) {
                return siblings.get(0).getString();
            }
        }
        for (Component sibling : component.getSiblings()) {
            String result = extractFontText(sibling, fontSubstring);
            if (result != null) return result;
        }
        return null;
    }

    private static String replacePlayerName(String text) {
        LocalPlayer player = Utils.player();
        if (player == null) return text;
        String name = player.getName().getString();
        if (name.isEmpty()) return text;
        text = text.replace(name, PLAYER_REPLACEMENT);
        String nickname = ModCore.config.getNicknameOverride();
        if (!nickname.isEmpty()) {
            text = text.replace(nickname, PLAYER_REPLACEMENT);
        }
        return text;
    }
}
