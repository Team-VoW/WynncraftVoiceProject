package com.wynnvp.wynncraftvp.utils;

import com.wynnvp.wynncraftvp.sound.line.LineData;

public class LineFormatter {

    public static LineData formatToLineData(String message) {
        LineData lineData = new LineData();

        if (message.contains("iso95bf")) { // unnatural message
            return null;
        }

        message = message.replace("\n", "iso95bf");
        message = handleLineSpaces(message);
        message = message.trim();
        lineData.setRealLine(message);
        message = message.toLowerCase();
        message = message.replaceAll("[^abcdefghijklmnopqrstuvwxyz?!0123456789/]", "");

        lineData.setSoundLine(message);
        return lineData;
    }

    private static String handleLineSpaces(String message) {
        if (message.contains("iso95bfiso95bf")) {

            //Get the last sentence after the \n\n
            String messageAfterDoubleSlashN = getTextAfterLastSplit(message, "iso95bfiso95bf");
            messageAfterDoubleSlashN = messageAfterDoubleSlashN.trim();

            if (messageAfterDoubleSlashN.contains("Press SHIFT to continue")) {
                message = getTextFromLastInSplit(message, "iso95bfiso95bf", 1);
            } else if (messageAfterDoubleSlashN.contains("Select an option to continue")) {
                message = getTextFromLastInSplit(message, "iso95bfiso95bf", 2);
            } else {
                message = messageAfterDoubleSlashN;
            }


        }
        message = message.replace("iso95bf", "");
        return message;
    }

    private static String getTextAfterLastSplit(String message, String split) {
        String[] splitMessage = message.split(split);
        message = splitMessage[splitMessage.length - 1];
        return message;
    }

    private static String getTextFromLastInSplit(String message, String split, int numberFromBehind) {
        String[] splitMessage = message.split(split);
        if (splitMessage.length > numberFromBehind) {
            //Gets the second to last message inbetween the split
            message = splitMessage[splitMessage.length - (numberFromBehind + 1)];


        } else {
            message = splitMessage[0];
        }
        return message;
    }



    public static boolean isNPCSentLine(String line){
        char[] chars = line.toCharArray();
        if (chars.length == 0
                || chars[0] != '['
                || !line.split(" ")[0].contains("/")
                || !Character.isDigit(chars[1])
                || (!Character.isDigit(chars[2]) && !(chars[2] == '/'))
        ){
            return false;
        }

        line = line.substring(line.indexOf('/'));
        chars = line.toCharArray();

        for(int i = 1; i < chars.length; i++){
            switch (chars[i]){
                case '/':
                    return false;
                case ']':
                    break;
            }
        }

        return true;


    }
}
