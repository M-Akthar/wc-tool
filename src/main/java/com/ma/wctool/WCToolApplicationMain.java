package com.ma.wctool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Some potential improvements is to accept multiple arguments, could have a boolean flag for each argument and instead of
//the switch statement we have blocks that execute if the flag for that argument is true etc
public class WCToolApplicationMain {
    public static void main(String[] args) throws IOException {
//        Update as needed
//        You can use a file to get absolute path if necessary
//        File testFile = new File("src/main/resources/wctest2.txt");
//        processRequest(new String[] {"-c", "src/main/resources/wctest2.txt"});
//        processRequest(new String[] {"-l", "src/main/resources/wctest2.txt"});
//        processRequest(new String[] {"-w", "src/main/resources/wctest2.txt"});
//        processRequest(new String[] {"-u", "src/main/resources/wctest2.txt"});
//        processRequest(new String[] {"src/main/resources/wctest2.txt"});

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

        if (args.length == 1) {
            result.append(calculateByteCount(new BufferedReader(new FileReader(args[0])))).append(" ")
                    .append(getLinesInFile(new BufferedReader(new FileReader(args[0])))).append(" ")
                    .append(getWordCount(new BufferedReader(new FileReader(args[0]))));
        } else {
            switch (args[0]) {
                case "-c":
                    result.append(calculateByteCount(new BufferedReader(new FileReader(args[1]))));
                    break;
                case "-l":
                    result.append(getLinesInFile(new BufferedReader(new FileReader(args[1]))));
                    break;
                case "-w":
                    result.append(getWordCount(new BufferedReader(new FileReader(args[1]))));
                    break;
                default:
                    result.append("Unrecognised Command: ").append(args[0]);
            }
        }

        System.out.println(result.toString());
    }

    private static int calculateByteCount(BufferedReader input) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = input.readLine()) != null) {
            sb.append(line).append(System.getProperty("line.separator"));
        }

        input.close();
        return sb.toString().getBytes().length;
    }

    private static long getLinesInFile(BufferedReader input) throws IOException {
        long lineCount = input.lines().count();
        input.close();
        return lineCount;
    }

    private static int getWordCount(BufferedReader input) throws IOException {
        int count = 0;
        String line;

        while ((line = input.readLine()) != null) {
            if (!line.isEmpty()) {
                count += line.trim().split("\\s+").length;
            }
        }

        input.close();
        return count;
    }
}