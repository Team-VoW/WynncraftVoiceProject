/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Recognises the overlay states where Wynncraft is asking the player to pick an answer.
 *
 * <p>Auto progress must never tap sneak on one of these: at best it answers the question for the
 * player, at worst it locks in a quest branch they did not want.
 *
 * <p>The overlay carries no explicit "this is a choice" flag, but Wynncraft draws every part of its
 * dialogue box with a dedicated bitmap font, and the answer list gets its own. An in-game capture
 * shows a choice arriving as {@code hud/dialogue/style/default/choice} plus one
 * {@code hud/dialogue/text/wynncraft/choice_N} per option, against
 * {@code hud/dialogue/text/wynncraft/body_N} and {@code hud/dialogue/text/nameplate} for ordinary
 * lines. Matching those fonts is the primary test.
 *
 * <p>Two text-shaped fallbacks stand behind it in case a Wynncraft update renames the fonts:
 *
 * <ul>
 *   <li>a prompt phrase such as "select an option"
 *   <li>a numbered list: option markers for at least 1 and 2, in ascending order, which is what a
 *       list of answers looks like and what running prose almost never does
 * </ul>
 *
 * <p>Pure: no {@code net.minecraft.*} dependencies, so the whole thing is unit tested. Feed it the
 * raw styled runs of the overlay component — {@link OverlayHandler} flattens them with
 * {@code Component.visit}.
 */
public final class DialogueChoiceDetector {
    /** One styled text run of the overlay component: the font it is drawn in and its raw text. */
    public record Segment(String font, String text) {}

    /**
     * The fonts Wynncraft actually draws an answer list with, captured in-game on 2026-08-21 during
     * Ensembles of Hope: the box style plus one text font per option slot.
     */
    private static final List<String> CHOICE_FONTS =
            List.of("hud/dialogue/style/default/choice", "hud/dialogue/text/wynncraft/choice");

    /**
     * Looser words, in case a future update renames the fonts above.
     *
     * <p>Only ever tested against a path that also contains {@code dialogue}. Without that scope,
     * {@code select} would match {@code minecraft:hud/selector/default/top_left} — a font Wynncraft
     * has on screen permanently, which would wedge auto progress off for good.
     */
    private static final List<String> CHOICE_FONT_HINTS = List.of("choice", "option", "answer", "response", "select");

    private static final List<String> CHOICE_PHRASES = List.of(
            "select an option",
            "select an answer",
            "select a response",
            "select one of",
            "select your",
            "choose an option",
            "choose an answer",
            "choose a response",
            "pick an option",
            "press shift to select",
            "shift to select",
            "to select an option");

    /** {@code [1]}, {@code 1.} or {@code 1)} standing alone, which is how answers get numbered. */
    private static final Pattern OPTION_MARKER =
            Pattern.compile("(?:^|[\\s\\u00a0])(?:\\[\\s*([1-9])\\s*]|([1-9])[.)])(?=[\\s\\u00a0]|$)");

    /** The font that carries the answer text itself, as opposed to the box drawn around it. */
    private static final String CHOICE_TEXT_FONT = "hud/dialogue/text/wynncraft/choice";

    private DialogueChoiceDetector() {}

    public static boolean isChoice(List<Segment> segments) {
        return reason(segments).isPresent();
    }

    /**
     * A fingerprint of the answers currently offered.
     *
     * <p>Wynncraft leaves the box on screen after an answer is picked, so "the box is up" cannot mean
     * "an answer is still wanted". Callers compare this instead, which lets one question replacing
     * another read as a new question even though the box never left.
     *
     * <p>Only the answer text counts. The box and frame glyphs are excluded because they animate, and
     * a fingerprint that changed every tick would make every choice look brand new forever.
     *
     * @return the answer text joined by NUL, or empty when this overlay offers no answers
     */
    public static String signature(List<Segment> segments) {
        if (segments == null) return "";
        StringBuilder out = new StringBuilder();
        for (Segment segment : segments) {
            if (segment.font() == null || segment.text() == null) continue;
            if (!segment.font().toLowerCase(Locale.ROOT).contains(CHOICE_TEXT_FONT)) continue;
            out.append(segment.text()).append('\0');
        }
        return out.toString();
    }

    private static boolean isChoiceFont(String rawFont) {
        String font = rawFont == null ? "" : rawFont.toLowerCase(Locale.ROOT);

        for (String known : CHOICE_FONTS) {
            if (font.contains(known)) return true;
        }

        if (!font.contains("dialogue")) return false;

        for (String hint : CHOICE_FONT_HINTS) {
            if (font.contains(hint)) return true;
        }
        return false;
    }

    /**
     * @return why this overlay looks like a choice, or empty if it does not — the text is meant to
     *         go straight into the log so a miss can be diagnosed from one play session
     */
    public static Optional<String> reason(List<Segment> segments) {
        if (segments == null || segments.isEmpty()) return Optional.empty();

        StringBuilder combined = new StringBuilder();
        for (Segment segment : segments) {
            if (isChoiceFont(segment.font())) {
                return Optional.of("font '" + segment.font() + "' draws an answer list");
            }
            if (segment.text() != null) {
                combined.append(segment.text()).append(' ');
            }
        }

        String text = normalise(combined.toString());

        for (String phrase : CHOICE_PHRASES) {
            if (text.contains(phrase)) {
                return Optional.of("text contains the prompt '" + phrase + "'");
            }
        }

        Set<String> markers = optionMarkers(text);
        if (markers.contains("1") && markers.contains("2")) {
            return Optional.of("text is a numbered list of options " + markers);
        }

        return Optional.empty();
    }

    /**
     * Lower-cases and collapses whitespace, and drops the private-use codepoints Wynncraft's custom
     * fonts are built from so that a glyph never welds two words together.
     */
    private static String normalise(String raw) {
        StringBuilder out = new StringBuilder(raw.length());
        for (int i = 0; i < raw.length(); ) {
            int cp = raw.codePointAt(i);
            i += Character.charCount(cp);
            if (cp > 0x00ff || Character.isWhitespace(cp)) {
                if (out.length() > 0 && out.charAt(out.length() - 1) != ' ') out.append(' ');
            } else {
                out.appendCodePoint(Character.toLowerCase(cp));
            }
        }
        return out.toString().trim();
    }

    /** The option numbers present, in the order they appear, ignoring any that go backwards. */
    private static Set<String> optionMarkers(String text) {
        Set<String> markers = new LinkedHashSet<>();
        Matcher matcher = OPTION_MARKER.matcher(text);
        int previous = 0;
        int searchFrom = 0;
        while (matcher.find(searchFrom)) {
            // The lookbehind is a consumed character, so a marker directly after another one would be
            // skipped without rewinding to just before the separator.
            searchFrom = Math.max(matcher.end() - 1, matcher.start() + 1);
            String number = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            int value = Integer.parseInt(number);
            if (value != previous + 1) {
                // Not a run of answers — "I need 3 more." should not read as option three.
                continue;
            }
            previous = value;
            markers.add(number);
        }
        return markers;
    }
}
