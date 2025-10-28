package pt.ulusofona.lp2.greatprogrammingjourney.ui.theme;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class ThemeLibrary {

    public static final String SLOT_NUMBER_COLOR = "slotNumberColor";
    public static final String SLOT_NUMBER_FONT_SIZE = "slotNumberFontSize";
    public static final String SLOT_BACKGROUND_COLOR = "slotBackgroundColor";
    public static final String CELL_SPACING = "cellSpacing";
    public static final String GRID_BACKGROUND_COLOR = "gridBackgroundColor";
    public static final String TOOLBAR_BACKGROUND = "toolbarBackgroundColor";
    
    public static final String WHITE = "#FFFFFF";
    public static final String BLACK = "#000000";
    public static final String DARK_GRAY = "#2E2E2E";
    public static final String GRAY_BG = "#1C1C1C";

    // Retro
    public static final String RETRO_GREEN = "#00FF00";
    public static final String RETRO_BG = "#001100";

    // Ocean
    public static final String OCEAN_DEEP = "#005F73";
    public static final String OCEAN_LIGHT = "#0A9396";
    public static final String OCEAN_DARK = "#001219";

    // Neon
    public static final String NEON_MAGENTA = "#FF00FF";
    public static final String NEON_BG = "#111111";

    // Desert
    public static final String DESERT_BROWN = "#4E342E";
    public static final String DESERT_SAND = "#F4A261";
    public static final String DESERT_GOLD = "#E9C46A";
    public static final String DESERT_RED = "#E76F51";

    // Terminal (neon terminal)
    public static final String TERMINAL_GREEN = "#00FFB4";
    public static final String TERMINAL_DARK1 = "#001A16";
    public static final String TERMINAL_DARK2 = "#00211C";

    // Créditos (usar no painel de créditos)
    public static final String CREDITS_TEXT_NEON = TERMINAL_GREEN;
    public static final String CREDITS_BORDER_NEON = TERMINAL_GREEN;
    public static final String CREDITS_BG_NEON = BLACK;

    private static final Map<ThemeType, HashMap<String, String>> THEMES;

    static {
        Map<ThemeType, HashMap<String, String>> m = new HashMap<>();
        m.put(ThemeType.LIGHT, lightTheme());
        m.put(ThemeType.DARK, darkTheme());
        m.put(ThemeType.RETRO, retroTheme());
        m.put(ThemeType.OCEAN, oceanTheme());
        m.put(ThemeType.NEON, neonTheme());
        m.put(ThemeType.DESERT, desertTheme());
        m.put(ThemeType.NEON_TERMINAL, neonTerminalTheme());
        m.put(ThemeType.NIGHT_HACKER, nightHackerTheme());
        THEMES = Map.copyOf(m);
    }

    private ThemeLibrary() {
    }

    public static HashMap<String, String> get(ThemeType type) {
        return new HashMap<>(THEMES.getOrDefault(type, darkTheme()));
    }
    public static Color getCreditsTextColor(ThemeType type) {
        HashMap<String, String> t = THEMES.get(type);
        if (t == null) {
            return Color.WHITE;
        }
        String hex = t.getOrDefault(SLOT_NUMBER_COLOR, "#FFFFFF");
        return Color.decode(hex);
    }

    public static Color getCreditsBorderColor(ThemeType type) {
        HashMap<String, String> t = THEMES.get(type);
        if (t == null) {
            return Color.WHITE;
        }
        String hex = t.getOrDefault(GRID_BACKGROUND_COLOR, "#FFFFFF");
        return Color.decode(hex);
    }

    public static Color getCreditsBackgroundColor(ThemeType type) {
        HashMap<String, String> t = THEMES.get(type);
        if (t == null) {
            return Color.BLACK;
        }
        String hex = t.getOrDefault(TOOLBAR_BACKGROUND, "#000000");
        return Color.decode(hex);
    }

    // ===================== Blocos de criação de cada tema =====================

    private static HashMap<String, String> lightTheme() {
        return new HashMap<>();
    }

    private static HashMap<String, String> darkTheme() {
        HashMap<String, String> m = new HashMap<>();
        m.put(SLOT_NUMBER_COLOR, WHITE);
        m.put(SLOT_NUMBER_FONT_SIZE, "14");
        m.put(SLOT_BACKGROUND_COLOR, DARK_GRAY);
        m.put(CELL_SPACING, "4");
        m.put(GRID_BACKGROUND_COLOR, BLACK);
        m.put(TOOLBAR_BACKGROUND, GRAY_BG);
        return m;
    }

    private static HashMap<String, String> retroTheme() {
        HashMap<String, String> m = new HashMap<>();
        m.put(SLOT_NUMBER_COLOR, RETRO_GREEN);
        m.put(SLOT_NUMBER_FONT_SIZE, "14");
        m.put(SLOT_BACKGROUND_COLOR, RETRO_BG);
        m.put(CELL_SPACING, "4");
        m.put(GRID_BACKGROUND_COLOR, BLACK);
        m.put(TOOLBAR_BACKGROUND, RETRO_BG);
        return m;
    }

    private static HashMap<String, String> oceanTheme() {
        HashMap<String, String> m = new HashMap<>();
        m.put(SLOT_NUMBER_COLOR, WHITE);
        m.put(SLOT_NUMBER_FONT_SIZE, "14");
        m.put(SLOT_BACKGROUND_COLOR, OCEAN_DEEP);
        m.put(CELL_SPACING, "3");
        m.put(GRID_BACKGROUND_COLOR, OCEAN_LIGHT);
        m.put(TOOLBAR_BACKGROUND, OCEAN_DARK);
        return m;
    }

    private static HashMap<String, String> neonTheme() {
        HashMap<String, String> m = new HashMap<>();
        m.put(SLOT_NUMBER_COLOR, NEON_MAGENTA);
        m.put(SLOT_NUMBER_FONT_SIZE, "15");
        m.put(SLOT_BACKGROUND_COLOR, NEON_BG);
        m.put(CELL_SPACING, "4");
        m.put(GRID_BACKGROUND_COLOR, BLACK);
        m.put(TOOLBAR_BACKGROUND, "#222222");
        return m;
    }

    private static HashMap<String, String> desertTheme() {
        HashMap<String, String> m = new HashMap<>();
        m.put(SLOT_NUMBER_COLOR, DESERT_BROWN);
        m.put(SLOT_NUMBER_FONT_SIZE, "14");
        m.put(SLOT_BACKGROUND_COLOR, DESERT_SAND);
        m.put(CELL_SPACING, "3");
        m.put(GRID_BACKGROUND_COLOR, DESERT_GOLD);
        m.put(TOOLBAR_BACKGROUND, DESERT_RED);
        return m;
    }

    private static HashMap<String, String> neonTerminalTheme() {
        HashMap<String, String> m = new HashMap<>();
        m.put(SLOT_NUMBER_COLOR, TERMINAL_GREEN);
        m.put(SLOT_NUMBER_FONT_SIZE, "14");
        m.put(SLOT_BACKGROUND_COLOR, TERMINAL_DARK1);
        m.put(CELL_SPACING, "4");
        m.put(GRID_BACKGROUND_COLOR, BLACK);
        m.put(TOOLBAR_BACKGROUND, TERMINAL_DARK2);
        return m;
    }

    // ThemeLibrary.java
    private static HashMap<String, String> nightHackerTheme() {
        HashMap<String, String> m = new HashMap<>();
        m.put(SLOT_NUMBER_COLOR, "#00F3FF");
        m.put(SLOT_NUMBER_FONT_SIZE, "15");
        m.put(SLOT_BACKGROUND_COLOR, "#111827");
        m.put(CELL_SPACING, "4");
        m.put(GRID_BACKGROUND_COLOR, "#0B0F14");
        m.put(TOOLBAR_BACKGROUND, "#0A0F1A");
        return m;
    }
}