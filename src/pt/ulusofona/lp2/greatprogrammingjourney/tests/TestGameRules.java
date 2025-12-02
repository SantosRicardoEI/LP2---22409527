package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.GameRules;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameRules {

    // ======================================= Player Count ============================================================

    @Test
    public void testValidatePlayerCountTooFew() {
        boolean ok = GameRules.validatePlayerCount(GameConfig.MIN_PLAYERS - 1);
        assertFalse(ok);
    }

    @Test
    public void testValidatePlayerCountTooMany() {
        boolean ok = GameRules.validatePlayerCount(GameConfig.MAX_PLAYERS + 1);
        assertFalse(ok);
    }

    @Test
    public void testValidatePlayerCountValid() {
        assertTrue(GameRules.validatePlayerCount(GameConfig.MIN_PLAYERS));
        assertTrue(GameRules.validatePlayerCount(GameConfig.MAX_PLAYERS));
    }

    // ========================================= World Size ============================================================

    @Test
    public void testValidateWorldSizeTooSmall() {
        int multiplier = GameConfig.BOARD_SIZE_MULTIPLIER;
        int playerCount = 3;
        int minSize = multiplier * playerCount;

        boolean ok = GameRules.validateWorldSize(minSize - 1, playerCount);
        assertFalse(ok);
    }

    @Test
    public void testValidateWorldSizeValid() {
        int multiplier = GameConfig.BOARD_SIZE_MULTIPLIER;
        int playerCount = 3;
        int minSize = multiplier * playerCount;

        boolean ok = GameRules.validateWorldSize(minSize + 5, playerCount);
        assertTrue(ok);
    }

    // ============================================= Dice ==============================================================

    @Test
    public void testValidateDiceBelowMin() {
        boolean ok = GameRules.validateDice(GameConfig.MIN_DICE - 1);
        assertFalse(ok);
    }

    @Test
    public void testValidateDiceAboveMax() {
        boolean ok = GameRules.validateDice(GameConfig.MAX_DICE + 1);
        assertFalse(ok);
    }

    @Test
    public void testValidateDiceValid() {
        assertTrue(GameRules.validateDice(GameConfig.MIN_DICE));
        assertTrue(GameRules.validateDice(GameConfig.MAX_DICE));
    }
}