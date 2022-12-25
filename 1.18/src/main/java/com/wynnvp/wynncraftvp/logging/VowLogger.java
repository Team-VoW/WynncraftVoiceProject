package com.wynnvp.wynncraftvp.logging;


import com.wynnvp.wynncraftvp.ModCore;

import java.io.*;
import java.util.HashSet;


public class VowLogger {

    private static final HashSet<String> addedLines = new HashSet<>();

    private static final String fileName = "logs/vowLog.txt";


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

    public static void Log(String message, String logType) {

        if (!ModCore.config.doLogging())
            return;

        writeIfDoesNotContain("[" + logType + "] " + message);

        if (isLastLineInDialogue(message))
            write("\n");

    }


    private static boolean writeIfDoesNotContain(String message) {

        if (!addedLines.add(message))
            return false;
        message += "\n";

        write(message);
        return true;
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

        char[] msgInArray = message.toCharArray();

        //Does not contain dialogue numbers
        if (!Character.isDigit(msgInArray[1])) {
            return false;
        }

        //Example of a dialogue that will go through with this
        // [2/2] Aledar: Hi!
        if (charNumbersAreSame(msgInArray[1], msgInArray[3])) {
            return true;
        }

        //Example of dialogues that will go through with this
        // [15/15] Tasim: Stop.
        if (charNumbersAreSame(msgInArray[1], msgInArray[4]) && charNumbersAreSame(msgInArray[2], msgInArray[5])) {
            return true;
        }

        return false;
    }

    private static boolean charNumbersAreSame(char first, char second) {

        //No numbers
        if (!Character.isDigit(first) || !Character.isDigit(second)) {
            return false;
        }

        if (Integer.parseInt("" + first) == (Integer.parseInt("" + second))) {
            return true;
        }
        return false;
    }

}
