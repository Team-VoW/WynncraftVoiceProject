/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.text;

/*
 * This file originates from © Wynntils 2023 https://github.com/Wynntils/Artemis/ but was modified to fit this project
 */

import com.wynnvp.wynncraftvp.events.ReceiveChatEvent;
import com.wynnvp.wynncraftvp.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffects;

public final class ChatHandler {
    // Test in ChatHandler_NPC_CONFIRM_PATTERN
    private static final Pattern NPC_CONFIRM_PATTERN =
            Pattern.compile("^ *§[47]Press §[cf](SNEAK|SHIFT) §[47]to continue$");

    // Test in ChatHandler_NPC_SELECT_PATTERN
    private static final Pattern NPC_SELECT_PATTERN =
            Pattern.compile("^ *§[47cf](Select|CLICK) §[47cf]an option (§[47])?to continue$");

    private static final Pattern EMPTY_LINE_PATTERN = Pattern.compile("^\\s*(§r|À+)?\\s*$");
    private static final long SLOWDOWN_PACKET_TICK_DELAY = 20;
    private static final int CHAT_SCREEN_TICK_DELAY = 1;

    private String lastRealChat = null;

    // This is used to detect when the lastRealChat message
    // is actually a confirmationless dialogue, but not a standard one,
    // and we can't parse it properly. That makes it be the last "real" chat,
    // so when we receive the clear screen, we think that all the messages are new.
    // By keeping track of the last two real chats, we can detect this case.
    private String oneBeforeLastRealChat = null;

    private long lastSlowdownApplied = 0;
    private List<StyledText> lastScreenNpcDialogue = List.of();
    private StyledText lastConfirmationlessDialogue = null;
    private List<StyledText> delayedDialogue;
    private NpcDialogueType delayedType = NpcDialogueType.NONE;
    private long chatScreenTicks = 0;
    private List<StyledText> collectedLines = new ArrayList<>();

    private boolean updateWrongOrder = false;

    private void updateWrongOrderPackets() {
        updateWrongOrder = false;

        if (delayedDialogue != null) {
            List<StyledText> dialogToSend = delayedDialogue;
            delayedDialogue = null;
            // If we got here, then we did not get the slowdown effect, otherwise we would
            // have sent the dialogue already
            handleNpcDialogue(dialogToSend, delayedType, false);
        }
    }

    public void onConnectionChange() {
        // Reset chat handler
        collectedLines = new ArrayList<>();
        chatScreenTicks = 0;
        lastRealChat = null;
        oneBeforeLastRealChat = null;
        lastSlowdownApplied = 0;
        lastScreenNpcDialogue = List.of();
        lastConfirmationlessDialogue = null;
        delayedDialogue = null;
        delayedType = NpcDialogueType.NONE;
    }

    public void onTick() {
        if (updateWrongOrder) updateWrongOrderPackets();

        if (collectedLines.isEmpty()) return;

        // Tick event runs after the chat packets, with the same tick number
        // as the chat packets. This means we can allow equality here.
        long ticks = Utils.mc().level.getGameTime();
        if (ticks >= chatScreenTicks + CHAT_SCREEN_TICK_DELAY) {
            // Send the collected screen lines
            processCollectedChatScreen();
        }
    }

    public void onChatReceived(Component messageComponent) {
        handleWithSeparation(messageComponent);
    }

    public void onStatusEffectUpdate(ClientboundUpdateMobEffectPacket event) {
        if (Utils.mc().level.getEntity(event.getEntityId()) != Utils.player()) return;

        if (event.getEffect().equals(MobEffects.MOVEMENT_SLOWDOWN.value())
                && event.getEffectAmplifier() == 3
                && event.getEffectDurationTicks() == 32767) {
            if (delayedDialogue != null) {
                List<StyledText> dialogue = delayedDialogue;
                delayedDialogue = null;

                handleNpcDialogue(dialogue, delayedType, true);
            } else {
                lastSlowdownApplied = Utils.mc().level.getGameTime();
            }
        }
    }

    public void onStatusEffectRemove(ClientboundRemoveMobEffectPacket event) {
        if (event.getEntity(Utils.mc().level) != Utils.player()) return;

        if (event.effect() == MobEffects.MOVEMENT_SLOWDOWN) {
            lastSlowdownApplied = 0;
        }
    }

    private void handleIncomingChatLine(Component messageComponent) {
        StyledText styledText = StyledText.fromComponent(messageComponent);

        // This is a normal one line chat, or we pass a chat screen through
        StyledText updatedMessage = postChatLine(styledText, MessageType.FOREGROUND);

        if (updatedMessage != null) {
            // TODO: PROBABLY ONE LINE PARSING?

            RecipientType type = getRecipientType(styledText, MessageType.FOREGROUND);
        }
    }

    private void handleWithSeparation(Component componentMessage) {
        StyledText styledText = StyledText.fromComponent(componentMessage);

        long currentTicks = Utils.mc().level.getGameTime();

        List<StyledText> lines = StyledTextUtils.splitInLines(styledText);

        // It is a multi-line screen if it is parsed to be multiple lines,
        // or if it is empty and sent in the same tick (with some fuzziness) as the current screen
        if (lines.size() > 1 || (styledText.isEmpty() && (currentTicks <= chatScreenTicks + CHAT_SCREEN_TICK_DELAY))) {
            // This is a "chat screen" message, which is a multi-line message

            // Allow ticks to be equal, since we want to
            // collect all lines in this tick and the next one
            if (currentTicks <= chatScreenTicks + CHAT_SCREEN_TICK_DELAY) {
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

            // For all those cases, we will collect the lines and thus need to cancel the event
            // componentMessage.setCanceled(true);
        } else {
            if (chatScreenTicks != 0) {
                // We got a normal line while collecting chat screen lines. This means the screen is
                // done, and we should process it first.
                processCollectedChatScreen();
            }

            // Process this as a normal line
            handleIncomingChatLine(componentMessage);
        }
    }

    private void processCollectedChatScreen() {
        List<StyledText> lines = new ArrayList<>(collectedLines);

        // Reset screen line collection
        collectedLines = new ArrayList<>();
        chatScreenTicks = 0;

        // From now on, we'll work on reversed lists, so the message that should
        // have been closest to the bottom is now on top.
        Collections.reverse(lines);

        LinkedList<StyledText> newLines = new LinkedList<>();
        if (lastRealChat == null) {
            // If we have no history, all lines are to be considered new
            lines.forEach(newLines::addLast);
        } else {
            // Figure out what's new since last chat message
            for (StyledText line : lines) {
                String plainText = line.getStringWithoutFormatting();
                if (plainText.equals(lastRealChat)) break;
                if (plainText.equals(oneBeforeLastRealChat)) {
                    // We've not found the last chat message, but we have found the one before that
                    // This means that the last chat message was a confirmationless dialogue
                    lastRealChat = oneBeforeLastRealChat;
                    oneBeforeLastRealChat = null;

                    break;
                }
                newLines.addLast(line);
            }
        }

        if (newLines.isEmpty()) {
            // No new lines has appeared since last registered chat line.
            // We could just have a dialog that disappeared, so we must signal this
            handleNpcDialogue(List.of(), NpcDialogueType.NONE, false);
            return;
        }

        boolean expectedConfirmationlessDialogue = false;

        if (newLines.getLast().getString().isEmpty()) {
            if (newLines.size() == 2) {
                // This should happen as a frequent case. It is a confirmationless dialogue.
                // Two new lines are supposed to be received:
                // - One empty line
                // - One line with a (confirmationless) dialogue
                // The dialogue line itself still can be full of À characters (aka. match EMPTY_LINE_PATTERN).
                // In this case, it is a "preparation" screen for the dialog,
                // that should be sent in place of the empty line, in the upcoming packets.
                // Currently, we just ignore this line.

                if (newLines.getFirst().matches(EMPTY_LINE_PATTERN)) {
                    // Both the first and the last line are empty, we expect a dialogue screen in the next packet batch
                    // Nothing to do here, as both lines are empty
                    return;
                }

                expectedConfirmationlessDialogue = true;
            } else if (newLines.size() == 4) {
                // This should happen as a special case.
                // Receiving 4 lines can result in two different scenarios:
                // - We received a normal dialogue, with the last line being the control message
                //   (Press SHIFT/Select an option to continue)
                // - We received a **temporary** confirmationless dialogue,
                //   with the last line being the control message, but it not being sent yet, making the line empty
                //   In this case, we will soon receive the control message, as a single line

                // If the first and second line is empty, the third line is a dialogue, and the fourth line is empty,
                // we can assume that the fourth line is the control message,
                // and the third line is a temporary confirmationless dialogue
                if (newLines.get(0).matches(EMPTY_LINE_PATTERN)
                        && newLines.get(1).matches(EMPTY_LINE_PATTERN)
                        && !newLines.get(2).matches(EMPTY_LINE_PATTERN)
                        && newLines.get(3).matches(EMPTY_LINE_PATTERN)) {
                    // The third line is a temporary confirmationless dialogue
                    expectedConfirmationlessDialogue = true;

                    // Remove the first two empty lines
                    newLines.removeFirst();
                    newLines.removeFirst();
                }
            }

            // Wynncraft add an empty line before the NPC dialog; remove it
            newLines.removeLast();
        }

        // Now what to do with the new lines we found?
        processNewLines(newLines, expectedConfirmationlessDialogue);
    }

    private void processNewLines(LinkedList<StyledText> newLines, boolean expectedConfirmationlessDialogue) {
        // We have new lines added to the bottom of the chat screen. They are either a dialogue,
        // or new background chat messages. Separate them in two parts
        LinkedList<StyledText> newChatLines = new LinkedList<>();
        LinkedList<StyledText> dialogue = new LinkedList<>();

        if(newLines.isEmpty()) {
            // If there are no new lines, we can't do anything
            return;
        }

        StyledText firstText = newLines.getFirst();
        boolean isNpcConfirm = firstText.find(NPC_CONFIRM_PATTERN);
        boolean isNpcSelect = firstText.find(NPC_SELECT_PATTERN);

        if (isNpcConfirm || isNpcSelect) {
            // This is an NPC dialogue screen.
            // First remove the "Press SHIFT/Select an option to continue" trailer.
            newLines.removeFirst();

            // If this happens, the "Press SHIFT/Select an option to continue" got appended to the last dialogue
            // NOTE: Currently, we do nothing in this case, as it seems to work without any issues
            //       In the future, additional handling for converting temporary confirmationless dialogues
            //       to normal dialogues may be needed
            if (newLines.isEmpty()) {
                return;
            }

            if (newLines.getFirst().getString().isEmpty()) {
                // After this we assume a blank line
                newLines.removeFirst();
            }

            boolean dialogDone = false;
            // This need to be false if we are to look for options
            boolean optionsFound = !isNpcSelect;

            // Separate the dialog part from any potential new "real" chat lines
            for (StyledText line : newLines) {
                if (!dialogDone) {
                    if (line.find(EMPTY_LINE_PATTERN)) {
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
                    if (!line.find(EMPTY_LINE_PATTERN)) {
                        newChatLines.push(line);
                    }
                }
            }
        } else if (expectedConfirmationlessDialogue) {
            // This is a confirmationless dialogue
            handleNpcDialogue(List.of(newLines.getFirst()), NpcDialogueType.CONFIRMATIONLESS, false);

            // If we expect a confirmationless dialogue, we should only have one line,
            // so we don't have to do any separation logic
            return;
        } else {
            // After a NPC dialogue screen, Wynncraft sends a "clear screen" with line of ÀÀÀ...
            // We just ignore that part. Also, remove empty lines or lines with just the §r code
            while (!newLines.isEmpty() && newLines.getFirst().find(EMPTY_LINE_PATTERN)) {
                newLines.removeFirst();
            }

            // But we may also handle new messages during the NPC dialogue screen here
            // If so, we need to separate the repeated dialogue and the new chat lines
            // The repeated dialogue starts with an empty line, followed by the actual dialogue

            // Reverse back the list, so it's in the order it was received
            Collections.reverse(newLines);

            // Add the lines to the new chat lines, until we find an empty line
            // If an empty line is found, check to see if it's followed by
            // either a confirmationless or a normal dialogue
            // If so, the rest of the lines are dialogues, so ignore them
            // If not, continue adding the lines to the new chat lines, and check for empty lines again,
            // if any are found
            while (!newLines.isEmpty()) {
                StyledText line = newLines.removeFirst();
                if (line.find(EMPTY_LINE_PATTERN)) {
                    if (newLines.isEmpty()) {
                        // If there are no more lines, we can't do anything
                        break;
                    }

                    StyledText nextLine = newLines.getFirst();
                    if (nextLine.equals(lastConfirmationlessDialogue)) {
                        // The rest of the lines is a re-sent confirmationless dialogue

                        break;
                    }

                    // Check if the following lines match the last NPC screen dialogue
                    // Otherwise, treat them as new chat lines
                    for (StyledText dialogueLine : lastScreenNpcDialogue) {
                        if (newLines.isEmpty()) {
                            // If there are no more lines, we can't do anything
                            break;
                        }

                        StyledText nextDialogueLine = newLines.getFirst();
                        if (!nextDialogueLine.equals(dialogueLine)) {
                            // If the next line does not match the dialogue line, it's a new chat line
                            break;
                        }

                        // If the next line matches the dialogue line, remove it
                        newLines.removeFirst();
                    }

                    // If we have removed all the lines, we don't need to do anything more
                    if (newLines.isEmpty()) {
                        break;
                    }
                }

                // This was not found to be a dialogue line, so add it to the new chat lines
                newChatLines.addLast(line);
            }
        }

        // Register all new chat lines
        newChatLines.forEach(this::handleFakeChatLine);

        // Handle the dialogue, if any
        handleScreenNpcDialog(dialogue, isNpcSelect);
    }

    private void handleScreenNpcDialog(List<StyledText> dialogues, boolean isSelection) {
        if (dialogues.isEmpty()) {
            // dialog could be the empty list, this means the last dialog is removed
            handleNpcDialogue(dialogues, NpcDialogueType.NONE, false);
            return;
        }

        NpcDialogueType type = isSelection ? NpcDialogueType.SELECTION : NpcDialogueType.NORMAL;

        if (Utils.mc().level.getGameTime() <= lastSlowdownApplied + SLOWDOWN_PACKET_TICK_DELAY) {
            // This is a "protected" dialogue if we have gotten slowdown effect just prior to the chat message
            // This is the normal case
            handleNpcDialogue(dialogues, type, true);
            return;
        }

        // Maybe this should be a protected dialogue but packets came in the wrong order.
        // Wait a tick for slowdown, and then send the event
        delayedDialogue = dialogues;
        delayedType = type;
        updateWrongOrder = true;
    }

    private void handleFakeChatLine(StyledText styledText) {
        // This is a normal, single line chat, sent in the background
        if (styledText.isEmpty()) return;

        // But it can weirdly enough actually also be a foreground NPC chat message, or
        // a game message; similar to a dialogue but not uttered by an NPC.
        RecipientType recipientType = getRecipientType(styledText, MessageType.FOREGROUND);
        if (recipientType == RecipientType.NPC) {
            // In this case, do *not* save this as last chat, since it will soon disappear
            // from history!
            handleNpcDialogue(List.of(styledText), NpcDialogueType.CONFIRMATIONLESS, false);
            return;
        }

        StyledText updatedMessage = postChatLine(styledText, MessageType.BACKGROUND);
        // If the message is canceled, we do not need to cancel any packets,
        // just don't send out the chat message
        if (updatedMessage == null) return;

        // Otherwise emulate a normal incoming chat message
        // McUtils.sendMessageToClient(updatedMessage.getComponent());
    }

    /**
     * Return a "massaged" version of the message, or null if we should cancel the
     * message entirely.
     */
    private StyledText postChatLine(StyledText styledText, MessageType messageType) {
        String plainText = styledText.getStringWithoutFormatting();
        if (!plainText.isBlank()) {
            // We store the unformatted string version to be able to compare between
            // foreground and background versions
            oneBeforeLastRealChat = lastRealChat;
            lastRealChat = plainText;
        }

        RecipientType recipientType = getRecipientType(styledText, messageType);

        if (recipientType == RecipientType.GAME_MESSAGE) {
            onNpcDialogue(List.of(styledText), false, NpcDialogueType.NONE);
        }

        if (recipientType == RecipientType.NPC) {
            handleNpcDialogue(List.of(styledText), NpcDialogueType.CONFIRMATIONLESS, false);
            // We need to cancel the original chat event, if any
            return null;
        }

        /*      ChatMessageReceivedEvent event = new ChatMessageReceivedEvent(styledText, messageType, recipientType);
        WynntilsMod.postEvent(event);
        if (event.isCanceled()) return null;
        return event.getStyledText();*/
        return styledText;
    }

    private void handleNpcDialogue(List<StyledText> dialogue, NpcDialogueType type, boolean isProtected) {
        if (type == NpcDialogueType.NONE) {
            // Ignore any delayed dialogues, since they are now obsolete
            delayedDialogue = null;
        }

        // Confirmationless dialogues bypass the lastScreenNpcDialogue check
        if (type == NpcDialogueType.CONFIRMATIONLESS) {
            // Store the last confirmationless dialogue, but it may be repeated,
            // so we need to check that it's not duplicated when a message is sent during the dialogue
            lastConfirmationlessDialogue = dialogue.getFirst();
        } else {
            if (lastScreenNpcDialogue.equals(dialogue)) return;

            lastScreenNpcDialogue = dialogue;
        }

        onNpcDialogue(dialogue, isProtected, type);
        // Models.NpcDialogue.handleDialogue(dialogue, isProtected, type);
    }

    private void onNpcDialogue(List<StyledText> dialogue, boolean isProtected, NpcDialogueType type) {
        for (var text : dialogue) {
            String playerName = Minecraft.getInstance().player.getName().getString();

            List<HoverEvent> hoverEvents = text.getHoverEvents();
            for (HoverEvent hoverEvent : hoverEvents) {
                // This text will be something like "SecondArcher's real username is kmaxi" where SecondArcher is the
                // nickname and kmaxi the real name
                String value = hoverEvent.getValue(HoverEvent.Action.SHOW_TEXT).getString();
                if (value.contains("real username")) {
                    playerName = value.split("'s real username")[0];
                }
            }

            ReceiveChatEvent.receivedChat(text.getStringWithoutFormatting().replace(playerName, "soldier"));
        }
    }

    private RecipientType getRecipientType(StyledText codedMessage, MessageType messageType) {
        // Check if message match a recipient category
        for (RecipientType recipientType : RecipientType.values()) {
            if (recipientType.matchPattern(codedMessage, messageType)) {
                return recipientType;
            }
        }

        // If no specific recipient matched, it is an "info" message
        return RecipientType.INFO;
    }
}
