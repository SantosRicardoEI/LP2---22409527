package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.GameRules;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

import static org.junit.Assert.*;

public class TestGameRules {

    // ======================================= Player Count ============================================================

    @Test
    public void Test_validatePlayerCount_TooFew() {
        ValidationResult r = GameRules.validatePlayerCount(GameConfig.MIN_PLAYERS - 1);
        assertFalse(r.isValid());
    }

    @Test
    public void Test_validatePlayerCount_TooMany() {
        ValidationResult r = GameRules.validatePlayerCount(GameConfig.MAX_PLAYERS + 1);
        assertFalse(r.isValid());
    }

    @Test
    public void Test_validatePlayerCount_Valid() {
        ValidationResult r = GameRules.validatePlayerCount(GameConfig.MIN_PLAYERS);
        assertTrue(r.isValid());
        r = GameRules.validatePlayerCount(GameConfig.MAX_PLAYERS);
        assertTrue(r.isValid());
    }

    // ========================================= World Size ============================================================


    @Test
    public void Test_validateWorldSize_TooSmall() {
        int multiplier = GameConfig.BOARD_SIZE_MULTIPLIER;
        int playerCount = 3;
        int minSize = multiplier * playerCount;

        ValidationResult r = GameRules.validateWorldSize(minSize - 1, playerCount);
        assertFalse(r.isValid());
    }

    @Test
    public void Test_validateWorldSize_Valid() {
        int multiplier = GameConfig.BOARD_SIZE_MULTIPLIER;
        int playerCount = 3;
        int minSize = multiplier * playerCount;

        ValidationResult r = GameRules.validateWorldSize(minSize + 5, playerCount);
        assertTrue(r.isValid());
    }

    // ============================================= Dice ==============================================================


    @Test
    public void Test_validateDice_BelowMin() {
        ValidationResult r = GameRules.validateDice(GameConfig.MIN_DICE - 1);
        assertFalse(r.isValid());
    }

    @Test
    public void Test_validateDice_AboveMax() {
        // MAX_DICE = 6 → testar 7 → inválido
        ValidationResult r = GameRules.validateDice(GameConfig.MAX_DICE + 1);
        assertFalse(r.isValid());
    }

    @Test
    public void Test_validateDice_Valid() {
        ValidationResult r = GameRules.validateDice(GameConfig.MIN_DICE );
        assertTrue(r.isValid());
        r = GameRules.validateDice(GameConfig.MAX_DICE );
        assertTrue(r.isValid());
    }
}