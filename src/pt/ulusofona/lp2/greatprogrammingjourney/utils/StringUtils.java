package pt.ulusofona.lp2.greatprogrammingjourney.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}