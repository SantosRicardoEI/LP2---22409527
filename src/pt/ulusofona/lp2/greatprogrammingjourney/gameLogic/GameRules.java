package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic;

import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

import static pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig.*;

public final class GameRules {

    // ============================== Constructor ======================================================================

    private GameRules() {
    }

    // ============================== Validation Methods ==============================================================

    public static ValidationResult validatePlayerCount(int n) {
        if (n < MIN_PLAYERS) {
            return ValidationResult.fail("Too few players. Minimum allowed: " + MIN_PLAYERS);
        }
        if (n > MAX_PLAYERS) {
            return ValidationResult.fail("Too many players. Maximum allowed: " + MAX_PLAYERS);
        }
        return ValidationResult.ok();
    }

    public static ValidationResult validateWorldSize(int worldSize, int playerCount) {
        int minWorldSize = playerCount * BOARD_SIZE_MULTIPLIER;
        if (worldSize < minWorldSize) {
            return ValidationResult.fail("World size too small. Minimum for " + playerCount + " players: " + minWorldSize);
        }
        return ValidationResult.ok();
    }

    public static ValidationResult validateDice(int roll) {
        if (roll < MIN_DICE || roll > MAX_DICE) {
            return ValidationResult.fail("Dice roll " + roll + " out of range (" + MIN_DICE + "-" + MAX_DICE + ")");
        }
        return ValidationResult.ok();
    }

}