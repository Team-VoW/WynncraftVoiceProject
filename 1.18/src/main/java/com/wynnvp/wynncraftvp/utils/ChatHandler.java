/*
 * This file originates from Wynntils (2022) class "ChatHandler" written by magicus: https://github.com/Wynntils/Artemis/blob/main/common/src/main/java/com/wynntils/handlers/chat/ChatHandler.java
 * but was modified to fit this project
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.utils;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ChatHandler {
    private static final Pattern NPC_CONFIRM_PATTERN =
            Pattern.compile("Press (SNEAK|SHIFT) to continue");
    private static final Pattern NPC_SELECT_PATTERN =
            Pattern.compile("(Select|CLICK) an option to continue");
    private static final Pattern NEWLINE_PATTERN = Pattern.compile("\n");

    public static String extractMessage(String messageAsString) {

        // Sometimes there is just a trailing newline; that does not
        // make it a multiline message
        if (messageAsString.contains("\n") && messageAsString.indexOf('\n') != (messageAsString.length() - 1)) {
            // This is a "chat screen"
            return getMessageFromDialogueScreen(messageAsString);

        } else {
            // No, it's a normal one line chat
            return messageAsString;
        }
    }

    private static String getMessageFromDialogueScreen(String message) {
        //Because wynn sends you a screen with a ton of messages, this list contains them split
        List<String> lines = getChatMessages(message);

        // From now on, we'll work on reversed lists, so the message that should
        // have been closest to the bottom is now on top.
        Collections.reverse(lines);

        LinkedList<String> newLines = new LinkedList<>(lines);

        // We have new lines added to the bottom of the chat screen. They are either a dialogue,
        // or new background chat messages. Separate them in two parts
        String firstLine = newLines.getFirst();
        boolean isNpcConfirm = NPC_CONFIRM_PATTERN.matcher(firstLine).find();
        boolean isNpcSelect = NPC_SELECT_PATTERN.matcher(firstLine).find();

        if (isNpcConfirm || isNpcSelect) {
            // This is an NPC dialogue screen.
            // First remove the "Press SHIFT/Select an option to continue" trailer.
            newLines.removeFirst();
        }

        if (newLines.size() == 0)
            return "";

        //It can contain 1-2 empty lines in the beginning that should be removed
        //If the line starts with 3 empty spaces then it's a dialogue choice the player can press on, so we should ignore it
        while (newLines.getFirst().isEmpty() || newLines.getFirst().startsWith("   ")) {
            newLines.removeFirst();
        }

        return newLines.getFirst();
    }


    /**
     * Wynn sends you a screen with a ton of messages, this method will return these lines split into one object
     *
     * @param message The received message
     * @return lines split into one element per line
     */
    private static LinkedList<String> getChatMessages(String message) {
        Matcher matcher = NEWLINE_PATTERN.matcher(message);
        LinkedList<String> lines = new LinkedList<>();

        int lastSegmentStart = 0;
        while (matcher.find()) {
            String segment = message.substring(lastSegmentStart, matcher.start());
            lines.add(segment);
            lastSegmentStart = matcher.end();
        }

        if (lastSegmentStart != message.length()) {
            String segment = message.substring(lastSegmentStart);
            lines.add(segment);

        }
        return lines;
    }


}
