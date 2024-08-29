package CommandLine;

public class SpaceSetter {
    public static String evenSpacing(String s, int i) {
        return " ".repeat(i - s.length());
    }

    public static String SpaceMulti(String s) {
        return " ".repeat(s.length());
    }
}
