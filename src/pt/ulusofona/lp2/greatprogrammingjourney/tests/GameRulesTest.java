package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.GameRules;

import static org.junit.Assert.*;

public class GameRulesTest {

    // ======================================= Player Count ============================================================

    @Test
    public void Test_validatePlayerCount_TooFew() {
        boolean ok = GameRules.validatePlayerCount(GameConfig.MIN_PLAYERS - 1);
        assertFalse(ok);
    }

    @Test
    public void Test_validatePlayerCount_TooMany() {
        boolean ok = GameRules.validatePlayerCount(GameConfig.MAX_PLAYERS + 1);
        assertFalse(ok);
    }

    @Test
    public void Test_validatePlayerCount_Valid() {
        assertTrue(GameRules.validatePlayerCount(GameConfig.MIN_PLAYERS));
        assertTrue(GameRules.validatePlayerCount(GameConfig.MAX_PLAYERS));
    }

    // ========================================= World Size ============================================================

    @Test
    public void Test_validateWorldSize_TooSmall() {
        int multiplier = GameConfig.BOARD_SIZE_MULTIPLIER;
        int playerCount = 3;
        int minSize = multiplier * playerCount;

        boolean ok = GameRules.validateWorldSize(minSize - 1, playerCount);
        assertFalse(ok);
    }

    @Test
    public void Test_validateWorldSize_Valid() {
        int multiplier = GameConfig.BOARD_SIZE_MULTIPLIER;
        int playerCount = 3;
        int minSize = multiplier * playerCount;

        boolean ok = GameRules.validateWorldSize(minSize + 5, playerCount);
        assertTrue(ok);
    }

    // ============================================= Dice ==============================================================

    @Test
    public void Test_validateDice_BelowMin() {
        boolean ok = GameRules.validateDice(GameConfig.MIN_DICE - 1);
        assertFalse(ok);
    }

    @Test
    public void Test_validateDice_AboveMax() {
        boolean ok = GameRules.validateDice(GameConfig.MAX_DICE + 1);
        assertFalse(ok);
    }

    @Test
    public void Test_validateDice_Valid() {
        assertTrue(GameRules.validateDice(GameConfig.MIN_DICE));
        assertTrue(GameRules.validateDice(GameConfig.MAX_DICE));
    }
}