package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import static org.junit.jupiter.api.Assertions.*;

class TestGameIsOver {

    private String[][] players3() {
        return new String[][]{
                {"1", "Dev One", "Java", "PURPLE"},
                {"2", "Dev Two", "Python", "BLUE"},
                {"3", "Dev Three", "C", "GREEN"}
        };
    }

    @Test
    void testGameIsOver_initialState_returnsFalse() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 20));
        assertFalse(gm.gameIsOver());
    }

    @Test
    void testGameIsOver_noWinnerYet_returnsFalse() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 20));
        gm.moveCurrentPlayer(5);
        gm.moveCurrentPlayer(3);
        gm.moveCurrentPlayer(2);
        assertFalse(gm.gameIsOver());
    }

    @Test
    void testGameIsOver_afterWinningMove_returnsTrue() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 6));
        gm.moveCurrentPlayer(5);
        assertTrue(gm.gameIsOver());
    }

    @Test
    void testGameIsOver_withoutBoard_returnsFalse() {
        GameManager gm = new GameManager();
        assertFalse(gm.gameIsOver());
    }
}