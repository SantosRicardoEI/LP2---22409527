package pt.ulusofona.lp2.greatprogrammingjourney.config;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.TurnOrder;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.theme.ThemeType;

public class GameConfig {

    private GameConfig() {
    }

    // Game settings

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    public static final int MIN_DICE = 1;
    public static final int MAX_DICE = 6;
    public static final int BOARD_SIZE_MULTIPLIER = 2;
    public static final int SLOT_SIZE = MAX_PLAYERS;
    public static final int BOARD_OFFSET = 1;
    public static final int INITIAL_POSITION = 1;
    public static final TurnOrder TURN_ORDER = TurnOrder.ASCENDING;
    public static final boolean ENABLE_BOUNCE = true;

    // Theme

    public static final ThemeType THEME = ThemeType.DESERT;

    // Log settings

    public static final boolean DEBUG_MODE = true;
    public static final String RESET = "\u001B[0m";
    public static final String INFO_COLOR = "\u001B[37m";
    public static final String WARNING_COLOR = "\u001B[33m";
    public static final String ERROR_COLOR = "\u001B[31m";


    // Save & Load settings

    public static final String BOARD_SECTION = "BOARD";
    public static final String BOARD_SIZE_KEY = "BOARD_SIZE";
    public static final String TURN_COUNT_KEY = "TURN_COUNT";
    public static final String CURRENT_ID_KEY = "CURRENT_PLAYER_ID";

    public static final String PLAYER_SECTION = "PLAYERS";
    public static final String PLAYER_KEY = "PLAYER";

    public static final String MAP_OBJECT_SECTION = "MAP_OBJECTS";
    public static final String MAP_OBJECT_KEY = "MAP_OBJECT";

    public static final String MOVE_SECTION = "MOVES";
    public static final String MOVE_KEY = "MOVE";


}