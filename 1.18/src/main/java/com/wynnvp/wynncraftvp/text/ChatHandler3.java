/*
 * This file originates from © Wynntils 2023 https://github.com/Wynntils/Artemis/
 * but was modified to fit this project
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import com.wynnvp.wynncraftvp.sound.line.LineData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public final class ChatHandler3 {
    // Test suite: https://regexr.com/7esj7
    private static final Pattern NPC_CONFIRM_PATTERN =
            Pattern.compile("^ *§[47]Press §[cf](SNEAK|SHIFT) §[47]to continue$");
    private static final Pattern NPC_SELECT_PATTERN =
            Pattern.compile("^ *§[47cf](Select|CLICK) §[47cf]an option (§[47])?to continue$");

    private static final Pattern EMPTY_LINE_PATTERN = Pattern.compile("^\\s*(§r|À+)?\\s*$");
    private static final long SLOWDOWN_PACKET_DIFF_MS = 500;
    private static final int CHAT_SCREEN_TICK_DELAY = 1;

    private String lastRealChat = null;
    private long lastSlowdownApplied = 0;
    private List<Component> lastScreenNpcDialog = List.of();
    private List<Component> delayedDialogue;
    private NpcDialogueType delayedType;
    private long chatScreenTicks = 0;
    private List<Component> collectedLines = new ArrayList<>();


    private boolean updateWrongOrder = false;

    private void updateWrongOrderPackets(){

        updateWrongOrder = false;

        if (delayedDialogue != null) {
            List<Component> dialogToSend = delayedDialogue;
            delayedDialogue = null;
            // If we got here, then we did not get the slowdown effect, otherwise we would
            // have sent the dialogue already
            postNpcDialogue(dialogToSend, delayedType);

        }
    }

    public void onTick() {

        if (updateWrongOrder)
            updateWrongOrderPackets();

        if (collectedLines.isEmpty()) return;

        long ticks = getGameTime();
        if (ticks > chatScreenTicks + CHAT_SCREEN_TICK_DELAY) {
            // Send the collected screen lines
            processCollectedChatScreen();
        }
    }

    public void onChatReceived(Component message) {

        handleWithSeparation(message);

    }

    public void onStatusEffectUpdate(ClientboundUpdateMobEffectPacket packet) {

        if (packet.getEffect() == MobEffects.MOVEMENT_SLOWDOWN
                && packet.getEffectAmplifier() == 3
                && packet.getEffectDurationTicks() == 32767) {
            if (delayedDialogue != null) {
                List<Component> dialogue = delayedDialogue;
                delayedDialogue = null;

                postNpcDialogue(dialogue, delayedType);
            } else {
                lastSlowdownApplied = System.currentTimeMillis();
            }
        }
    }

    public void onStatusEffectRemove(ClientboundRemoveMobEffectPacket packet) {

        if (packet.effect() == MobEffects.MOVEMENT_SLOWDOWN) {
            lastSlowdownApplied = 0;
        }
    }

    private void handleIncomingChatLine(Component message) {
        StyledText styledText = StyledText.fromComponent(message);

        // This is a normal one line chat, or we pass a chat screen through
        postChatLine(message, styledText);

    }

    private void handleWithSeparation(Component message) {
        StyledText styledText = StyledText.fromComponent(message);

        long currentTicks = getGameTime();

        // It is a multi-line screen if it contains a newline, or if it is empty and sent in the same tick (with
        // some fuzziness) as the current screen
        if (styledText.contains("\n")
                || (styledText.isEmpty() && (currentTicks <= chatScreenTicks + CHAT_SCREEN_TICK_DELAY))) {
            // This is a "chat screen"

            if (LineData.NPC_DIALOGUE_PATTERN.matcher(message.getString().replace("\n", "").trim()).find()) {
                // This is a NPC dialogue, but it is not a foreground one
                postNpcDialogue(List.of(message), NpcDialogueType.NORMAL);
                return;
            }

            List<Component> lines = ComponentUtils.splitComponentInLines(message);
            if (currentTicks < chatScreenTicks + CHAT_SCREEN_TICK_DELAY) {
                // We are collecting lines, so add to the current collection
                collectedLines.addAll(lines);
            } else {
                // Start a new collection
                if (chatScreenTicks != 0) {
                    // Send the old before starting a new. We should not really end up here since this should
                    // be done in the tick handler.
                    processCollectedChatScreen();
                }

                collectedLines = new ArrayList<>(lines);
                chatScreenTicks = currentTicks;
            }

        } else {
            if (chatScreenTicks != 0) {
                // We got a normal line while collecting chat screen lines. This means the screen is
                // done and we should process it first.
                processCollectedChatScreen();
            }

            // Process this as a normal line
            handleIncomingChatLine(message);
        }
    }

    private void processCollectedChatScreen() {
        List<Component> lines = collectedLines;

        // Reset screen line collection
        collectedLines = new ArrayList<>();
        chatScreenTicks = 0;

        // From now on, we'll work on reversed lists, so the message that should
        // have been closest to the bottom is now on top.
        Collections.reverse(lines);

        LinkedList<Component> newLines = new LinkedList<>();
        if (lastRealChat == null) {
            // If we have no history, all lines are to be considered new
            lines.forEach(newLines::addLast);
        } else {
            // Figure out what's new since last chat message
            for (Component line : lines) {
                String plainText = StyledText.fromComponent(line).getStringWithoutFormatting();
                if (plainText.equals(lastRealChat)) break;
                newLines.addLast(line);
            }
        }

        if (newLines.isEmpty()) {
            // No new lines has appeared since last registered chat line.
            // We could just have a dialog that disappeared, so we must signal this
            postNpcDialogue(List.of(), NpcDialogueType.NONE);

            return;
        }

        if (newLines.getLast().getString().isEmpty()) {
            // Wynntils add an empty line before the NPC dialog; remove it
            newLines.removeLast();
        }

        // Now what to do with the new lines we found?
        processNewLines(newLines);
    }

    private void processNewLines(LinkedList<Component> newLines) {

        if (newLines == null || newLines.isEmpty())
            return;

        // We have new lines added to the bottom of the chat screen. They are either a dialogue,
        // or new background chat messages. Separate them in two parts
        LinkedList<Component> newChatLines = new LinkedList<>();
        LinkedList<Component> dialogue = new LinkedList<>();

        StyledText firstLineCoded = StyledText.fromComponent(newLines.getFirst());
        boolean isNpcConfirm = firstLineCoded.find(NPC_CONFIRM_PATTERN);
        boolean isNpcSelect = firstLineCoded.find(NPC_SELECT_PATTERN);

        if (isNpcConfirm || isNpcSelect) {
            // This is an NPC dialogue screen.
            // First remove the "Press SHIFT/Select an option to continue" trailer.
            newLines.removeFirst();

            if (!newLines.isEmpty() && newLines.getFirst() != null && newLines.getFirst().getString().isEmpty()) {
                // After this we assume a blank line
                newLines.removeFirst();
            } else {
                System.out.println("Wynnvp: Malformed dialog [#1]: ");
            }

            boolean dialogDone = false;
            // This need to be false if we are to look for options
            boolean optionsFound = !isNpcSelect;

            // Separate the dialog part from any potential new "real" chat lines
            for (Component line : newLines) {
                StyledText codedLine = StyledText.fromComponent(line);
                if (!dialogDone) {
                    if (codedLine.find(EMPTY_LINE_PATTERN)) {
                        if (!optionsFound) {
                            // First part of the dialogue found
                            optionsFound = true;
                            dialogue.push(line);
                        } else {
                            dialogDone = true;
                        }
                        // Intentionally throw away this line
                    } else {
                        dialogue.push(line);
                    }
                } else {
                    // If there is anything after the dialogue, it is new chat lines
                    if (!codedLine.find(EMPTY_LINE_PATTERN)) {
                        newChatLines.push(line);
                    }
                }
            }
        } else {
            // After a NPC dialog screen, Wynncraft sends a "clear screen" with line of ÀÀÀ...
            // We just ignore that part. Also, remove empty lines or lines with just the §r code
            while (!newLines.isEmpty()
                    && StyledText.fromComponent(newLines.getFirst()).find(EMPTY_LINE_PATTERN)) {
                newLines.removeFirst();
            }

            // What remains, if any, are new chat lines
            newLines.forEach(newChatLines::push);
        }

        // Register all new chat lines
        newChatLines.forEach(this::handleFakeChatLine);

        // Handle the dialogue, if any
        handleScreenNpcDialog(dialogue, isNpcSelect);
    }

    private void handleScreenNpcDialog(List<Component> dialog, boolean isSelection) {
        if (dialog.isEmpty()) {
            // dialog could be the empty list, this means the last dialog is removed
            postNpcDialogue(dialog, NpcDialogueType.NONE);
            return;
        }

        NpcDialogueType type = isSelection ? NpcDialogueType.SELECTION : NpcDialogueType.NORMAL;

        if ((System.currentTimeMillis() <= lastSlowdownApplied + SLOWDOWN_PACKET_DIFF_MS)) {
            // This is a "protected" dialogue if we have gotten slowdown effect just prior to the chat message
            // This is the normal case
            postNpcDialogue(dialog, type);
            return;
        }

        // Maybe this should be a protected dialogue but packets came in the wrong order.
        // Wait a tick for slowdown, and then send the event
        delayedDialogue = dialog;
        delayedType = type;

        updateWrongOrder = true;
    }



    private void handleFakeChatLine(Component message) {
        // This is a normal, single line chat, sent in the background
        StyledText styledText = StyledText.fromComponent(message);

        // But it can weirdly enough actually also be a foreground NPC chat message...
        if (getRecipientType(styledText) == RecipientType.NPC) {
            postNpcDialogue(List.of(message), NpcDialogueType.CONFIRMATIONLESS);
        }

    }

    /**
     * Return a "massaged" version of the message, or null if we should cancel the
     * message entirely.
     */
    private void postChatLine(Component message, StyledText styledText) {
        String plainText = styledText.getStringWithoutFormatting();
        if (!plainText.isBlank()) {
            // We store the unformatted string version to be able to compare between
            // foreground and background versions
            lastRealChat = plainText;
        }

        // Normally § codes are stripped from the log; need this to be able to debug chat formatting
        RecipientType recipientType = getRecipientType(styledText);


        switch (recipientType) {
            case INFO:
                if (LineData.NPC_DIALOGUE_PATTERN.matcher(message.getString()).find()
                || !message.getString().contains("[")) {
                    // This is a NPC dialogue, but it is not a foreground one
                    postNpcDialogue(List.of(message), NpcDialogueType.NORMAL);
                }
                break;
            case NPC:
                postNpcDialogue(List.of(message), NpcDialogueType.NORMAL);
                break;
        }
    }

    private void postNpcDialogue(List<Component> dialogue, NpcDialogueType type) {
        // Confirmationless dialoges bypass the lastScreenNpcDialogue check
        if (type != NpcDialogueType.CONFIRMATIONLESS) {
            if (lastScreenNpcDialog.equals(dialogue)) return;

            lastScreenNpcDialog = dialogue;
        }

        if (type == NpcDialogueType.NONE) {
            // Ignore any delayed dialogues, since they are now obsolete
            delayedDialogue = null;
        }

        onNpcDialogue(dialogue);
    }



    private RecipientType getRecipientType(StyledText codedMessage) {
        // Check if message match a recipient category
        for (RecipientType recipientType : RecipientType.values()) {
            if (recipientType.matchPattern(codedMessage)) {
                return recipientType;
            }
        }

        // If no specific recipient matched, it is an "info" message
        return RecipientType.INFO;
    }


    private static long getGameTime() {
        return Minecraft.getInstance().level.getLevelData().getGameTime();
    }

    private void onNpcDialogue(List<Component> dialogue){
        for (var comp : dialogue){
            ReceiveChatEvent.receivedChat(comp.getString());
        }
    }


}
