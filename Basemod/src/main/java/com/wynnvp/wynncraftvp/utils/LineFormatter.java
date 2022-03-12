package com.wynnvp.wynncraftvp.utils;

public class LineFormatter {

    public static String formatToSound(String message) {
        message = getActualText(message);
        message = message.toLowerCase();
        message = message.replaceAll("[^abcdefghijklmnopqrstuvwxyz?!0123456789/]", "");
        message = message.replace("iso95bf", "");

        return message;
    }

    private static String getActualText(String message) {
        if (message.contains("iso95bfiso95bf")) {

            //Get the last sentence after the \n\n
            String messageAfterDoubleSlashN = getTextAfterSplit(message, "iso95bfiso95bf");
            messageAfterDoubleSlashN = messageAfterDoubleSlashN.trim();

            //Checks if the message ends with Press SHIFT to continue\n
            if (messageAfterDoubleSlashN.contains("Press SHIFT to continue")){
                message = getTextSecondToLastInSplit(message, "iso95bfiso95bf");
            }
            //IF the message does not end with press shift to continue
            else {
                //Remove any \n that is remaining
                message = messageAfterDoubleSlashN.replace("iso95bf", "");
            }
        }
        return message;
    }

    private static String getTextAfterSplit(String message, String split) {
        String[] splitMessage = message.split(split);
        message = splitMessage[splitMessage.length - 1];
        return message;
    }

    private static String getTextSecondToLastInSplit(String message, String split) {
        String[] splitMessage = message.split(split);
        if (splitMessage.length > 1) {
            //Gets the second to last message inbetween the split
            message = splitMessage[splitMessage.length - 2];


        } else {
            message = splitMessage[0];
        }
        return message;
    }
}
