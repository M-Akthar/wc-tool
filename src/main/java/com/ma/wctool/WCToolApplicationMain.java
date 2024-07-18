package com.ma.wctool;

import java.io.*;
import java.nio.file.Path;

public class WCToolApplicationMain {
    public static void main(String[] args) throws IOException {
//        Update as needed
//        You can use a file to get absolute path if necessary
//        File testFile = new File("src/main/resources/wctest2.txt");
//        processRequest(new String[] {"-l", "src/main/resources/wctest2.txt"});

        if (args.length > 0) {
            try {
                processRequest(args);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Invalid arguments, insert command followed by the file or just the file itself.");
        }
    }

    private static void processRequest(String[] args) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader inputFileBR;

        try {
            inputFileBR = args.length == 1 ? new BufferedReader(new FileReader(args[0])) : new BufferedReader(new FileReader(args[1]));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (args.length == 1) {
            result.append("Work in progress");
        } else {
            switch (args[0]) {
                case "-c":
                    result.append(calculateByteCount(inputFileBR));
                    break;
                case "-l":
                    result.append(getLinesInFile(inputFileBR));
                    break;
                case "-w":
                    result.append(getWordCount(inputFileBR));
                    break;
                default:
                    result.append("Unrecognised Command: ").append(args[0]);

            }
        }

        System.out.println(result.toString());
        inputFileBR.close();
    }

    private static int calculateByteCount(BufferedReader input) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = input.readLine()) != null) {
            //Just appending the line can result in removing the system specific line separator causing the byte count to differ
            //compared the terminal wc -c command. Windows separator \r\n is two bytes while the unix one is a single byte
            sb.append(line).append(System.getProperty("line.separator"));
        }

        return sb.toString().getBytes().length;
    }

    private static long getLinesInFile(BufferedReader input) {
        return input.lines().count();
    }

    private static int getWordCount(BufferedReader input) throws IOException {
        int count = 0;
        String line;

        while ((line = input.readLine()) != null) {
            if (!line.isEmpty()) {
                count += line.trim().split("\\s+").length;
            }
        }

        return count;
    }
}