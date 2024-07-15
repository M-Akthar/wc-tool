package com.ma.wctool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WCToolApplicationMain {
    public static void main(String[] args) {
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

            //Using wctest.txt without the line sep the count was 327900, with it 342190 (which is the same as the built-in wc -c)
        }

        return sb.toString().getBytes().length;
    }

    private static long getLinesInFile(BufferedReader input) {
        return input.lines().count();
    }
}