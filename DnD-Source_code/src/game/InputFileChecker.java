package game;

import CommandLine.UI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/*
    * This class is used to check the input file for the game.
    * It reads the file and prints the characters in it.
    * I had a TOUGH time reading the files so this is like a test (;
 */
public class InputFileChecker {
    public static void main(String[] args) {
        UI.print("Number of arguments: " + args.length);
        for (String arg : args) {
            UI.print("Argument: " + arg);
        }
        if (args.length != 1) {
            UI.print("Usage: java InputFileChecker <filename>");
            return;
        }

        String filename = args[0];

        try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
            int x = 0;
            String line;
            while ((line = buffer.readLine()) != null) {
                for (int y = 0; y < line.length(); y++) {
                    char currentChar = line.charAt(y);
                    UI.print("Character at (" + x + ", " + y + "): " + (int)currentChar + " (" + currentChar + ")");
                }
                x++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
