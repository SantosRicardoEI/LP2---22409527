package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic;

import static pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig.*;

public final class GameRules {

    // ============================== Constructor ======================================================================

    private GameRules() {
    }

    // ============================== Validation Methods ==============================================================

    public static boolean validatePlayerCount(int n) {
        if (n < MIN_PLAYERS) {
            return false;
        }
        if (n > MAX_PLAYERS) {
            return false;
        }
        return true;
    }

    public static boolean validateWorldSize(int worldSize, int playerCount) {
        int minWorldSize = playerCount * BOARD_SIZE_MULTIPLIER;
        if (worldSize < minWorldSize) {
            return false;
        }
        return true;
    }

    public static boolean validateDice(int roll) {
        if (roll < MIN_DICE || roll > MAX_DICE) {
            return false;
        }
        return true;
    }

}