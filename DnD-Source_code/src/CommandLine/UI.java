package CommandLine;

import java.util.Scanner;

public class UI {
    public static void print(Object message) {
        System.out.println(message);
    }
    public static void printWithoutLn(Object message) {
        System.out.print(message);
    }

    public static String input() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }
}
