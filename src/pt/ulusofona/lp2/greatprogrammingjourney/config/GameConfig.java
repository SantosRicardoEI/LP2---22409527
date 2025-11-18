package pt.ulusofona.lp2.greatprogrammingjourney.config;

import pt.ulusofona.lp2.greatprogrammingjourney.core.TurnOrder;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.theme.ThemeType;

public class GameConfig {

    private GameConfig() {
    }

    public static final int PLAYER_STARTING_LIVES = 3;

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


    public static final int COMPLIATION_ERROR_BAN_DURATION = 1;

    public static final ThemeType THEME = ThemeType.NIGHT_HACKER;

    public static final boolean DEBUG_MODE = true;
    public static final String RESET = "\u001B[0m";
    public static final String INFO_COLOR = "\u001B[37m";
    public static final String WARNING_COLOR = "\u001B[33m";
    public static final String ERROR_COLOR = "\u001B[31m";
}