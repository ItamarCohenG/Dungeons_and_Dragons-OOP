package main;

import CommandLine.Control;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Error: No levels path provided.");
            System.exit(1);
        }

        String levels_dir = args[0]; // Get the directory path from the arguments
        File levelsDirectory = new File(levels_dir);

        if (!levelsDirectory.exists() || !levelsDirectory.isDirectory()) {
            System.err.println("Error: The provided path is not a valid directory.");
            System.exit(1);
        }

        Control gm = new Control();
        List<String> levels = Arrays.stream(Objects.requireNonNull(levelsDirectory.listFiles())).map(File::getAbsolutePath).collect(Collectors.toList());
        levels.sort(String::compareTo);

        // Start the game!
        gm.start(levels);
    }
}

