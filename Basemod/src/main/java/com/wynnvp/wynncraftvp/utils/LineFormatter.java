package com.wynnvp.wynncraftvp.utils;

import com.wynnvp.wynncraftvp.sound.LineData;

public class LineFormatter {

    public static LineData formatToLineData(String message) {
        LineData lineData = new LineData();
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

            //Checks if the message ends with Press SHIFT to continue\n
            if (messageAfterDoubleSlashN.contains("Press SHIFT to continue")){
                message = getTextSecondToLastInSplit(message, "iso95bfiso95bf");
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



    public static boolean isNPCSentLine(String line){
        char[] chars = line.toCharArray();
        if (chars.length == 0
                || chars[0] != '['
                || !line.contains("/")
                || !Character.isDigit(chars[1])
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
