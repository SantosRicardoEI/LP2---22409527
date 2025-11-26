package pt.ulusofona.lp2.greatprogrammingjourney.config;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.TurnOrder;

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
    public static final boolean ENABLE_TOOL_CHAT_GPT = false;
    public static final boolean ENABLE_ABYYS_NONAMEYET = false;

    // Colors
    public static final String WHITE = "#FFFFFF";
    public static final String BLACK = "#000000";
    public static final String DARK_GRAY = "#2E2E2E";
    public static final String GRAY_BG = "#1C1C1C";

    public static final String RETRO_GREEN = "#00FF00";
    public static final String RETRO_BG = "#001100";

    public static final String OCEAN_DEEP = "#005F73";
    public static final String OCEAN_LIGHT = "#0A9396";
    public static final String OCEAN_DARK = "#001219";

    public static final String NEON_MAGENTA = "#FF00FF";
    public static final String NEON_BG = "#111111";

    public static final String DESERT_BROWN = "#4E342E";
    public static final String DESERT_SAND = "#F4A261";
    public static final String DESERT_GOLD = "#E9C46A";
    public static final String DESERT_RED = "#E76F51";

    public static final String TERMINAL_GREEN = "#00FFB4";
    public static final String TERMINAL_DARK1 = "#001A16";
    public static final String TERMINAL_DARK2 = "#00211C";

    // Theme
    public static final String PLAYER_BLUE_IMAGE = "blueP.png";
    public static final String PLAYER_BROWN_IMAGE = "brownP.png";
    public static final String PLAYER_GREEN_IMAGE = "greenP.png";
    public static final String PLAYER_PURPLE_IMAGE = "purpleP.png";

    public static final String SLOT_NUMBER_COLOR = WHITE;
    public static final int SLOT_NUMBER_FONT_SIZE = 18;

    public static final String SLOT_BACKGROUND_COLOR= NEON_BG;
    public static final int CELL_SPACING = 4;
    public static final String GRID_BACKGROUND_COLOR = BLACK;
    public static final String TOOLBAR_BACKGROUND_COLOR = GRAY_BG;
    public static final String LOGO = "logo2.png";

    // Credits
    public static final String CREDITS_TEXT_COLOR = WHITE;
    public static final String CREDITS_BORDER_COLOR = GRAY_BG;
    public static final String CREDITS_BACKGROUND_COLOR = BLACK;



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