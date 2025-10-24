package pt.ulusofona.lp2.greatprogrammingjourney.model.player;

import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

public enum PlayerColor {
    PURPLE,
    BLUE,
    GREEN,
    BROWN,
    RED,
    GRAY,
    ORANGE,
    YELLOW,
    CYAN,
    PINK,
    MAGENTA,
    LIME,
    WHITE,
    BLACK,
    OLIVE,
    NAVY,
    MAROON,
    TEAL,
    SILVER,
    GOLD;

    private static final GameLogger LOG = new GameLogger(PlayerColor.class);

    // ============================== Public API ==============================

    public static PlayerColor from(String s) {
        if (s == null || s.isBlank()) {
            LOG.warn("from: received null or empty input");
            return null;
        }

        String normalized = s.trim().toUpperCase();

        try {
            return PlayerColor.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            LOG.warn("from: unrecognized color name=\"" + s + "\"");
            return null;
        }
    }
}