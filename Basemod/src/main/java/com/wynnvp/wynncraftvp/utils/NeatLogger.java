package com.wynnvp.wynncraftvp.utils;


import java.io.*;
import java.util.HashSet;

public class NeatLogger {

    private final HashSet<String> addedLines = new HashSet<>();

    private static final String fileName = "neatLog.txt";

    private int linesSinceLastSpaceWasAdded = 1;

    public NeatLogger(){
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Initialize();

    }


    private void Initialize(){
        //Adds all lines in the file to the HashSet
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals(""))
                    continue;

                addedLines.add(line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReceivedChat(String message){

       // if (!message.startsWith("[")) return;

        if (!LineFormatter.isNPCSentLine(message)){
            return;
        }

        if (Integer.parseInt("" + message.toCharArray()[1]) == 1 && linesSinceLastSpaceWasAdded >= 2
                && message.toCharArray()[2] == '/'){
            linesSinceLastSpaceWasAdded = 0;
            write("\n");
        }


        if (addedLines.add(message)){
            linesSinceLastSpaceWasAdded++;
            write(message + "\n");

        }
    }

    public void write(String message){
        try {

            FileWriter fw = new FileWriter(fileName,true); //the true will append the new data
            fw.write(message);//appends the string to the file
            fw.close();

            //    FileWriter myWriter = new FileWriter(fileName);
            //   myWriter.append(message);
            //   myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
