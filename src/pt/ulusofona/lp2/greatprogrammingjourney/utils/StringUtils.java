package pt.ulusofona.lp2.greatprogrammingjourney.utils;

import java.util.List;

public class StringUtils {

    private StringUtils() {
    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static String formatProgrammerInfoStr(int id, String name, int pos, List<String> langs, String state) {
        String langsStr = String.join("; ", langs);
        return id + " | " + name + " | " + pos + " | " + langsStr + " | " + state;
    }
}