package com.wynnvp.wynncraftvp.logging;


import com.wynnvp.wynncraftvp.ModCore;

import java.io.*;
import java.util.HashSet;


public class VowLogger {

    private static final HashSet<String> addedLines = new HashSet<>();

    private static final String fileName = "logs/vowLog.txt";

    private static final String lastLineInDialoguePattern = "^\\[(\\d+)\\/\\1\\]\\s.*";

    public static void Initialize() {

        createFile();
    }

    private static void createFile() {
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                clearFile();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void clearFile()

    {
        try{
            FileWriter fw = new FileWriter(fileName, false);

            PrintWriter pw = new PrintWriter(fw, false);

            pw.flush();

            pw.close();

            fw.close();

        }catch(Exception exception){

            System.out.println("Exception have been caught when clearing the vowLog file");

        }

    }

    public static void logLine(String message, String logType) {

        writeIfDoesNotContain("[" + logType + "] " + message);

        if (isLastLineInDialogue(message))
            write("\n");
    }

    public static void logLine(String message) {

        writeIfDoesNotContain(message);

        if (isLastLineInDialogue(message))
            write("\n");
    }


    private static void writeIfDoesNotContain(String message) {

        if (!addedLines.add(message))
            return;
        message += "\n";

        write(message);
    }

    private static void write(String text) {
        try {

            FileWriter fw = new FileWriter(fileName, true); //the true will append the new data
            fw.write(text); //appends the string to the file
            fw.close();

        } catch (IOException e) {
            System.out.println("En error happened while writing " + text + " to the file.");
            e.printStackTrace();
        }
    }


    private static boolean isLastLineInDialogue(String message) {

        return message.matches(lastLineInDialoguePattern);
    }

}
