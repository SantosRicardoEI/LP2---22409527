package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import static org.junit.jupiter.api.Assertions.*;

class TestGetCurrentPlayerId {

    private String[][] players135() {
        return new String[][]{
                {"1", "P1", "Java", "PURPLE"},
                {"3", "P3", "Python", "BLUE"},
                {"5", "P5", "C", "GREEN"}
        };
    }

    private String[][] playersUnsortedIds() {
        return new String[][]{
                {"5", "P5", "C", "GREEN"},
                {"3", "P3", "Python", "BLUE"},
                {"1", "P1", "Java", "PURPLE"}
        };
    }

    @Test
    void testInitialPlayerIsSmallestId() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players135(), 20));
        assertEquals(1, gm.getCurrentPlayerID());
    }

    @Test
    void testInitialPlayerWithUnsortedInput() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(playersUnsortedIds(), 20));
        assertEquals(1, gm.getCurrentPlayerID(), "Deve começar no menor ID independentemente da ordem de input");
    }

    @Test
    void testTurnAdvancesAfterValidMove() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players135(), 20));
        assertTrue(gm.moveCurrentPlayer(1));
        assertEquals(3, gm.getCurrentPlayerID(), "Após 1 jogada válida, passa do 1 para o 3 (players 1 - 3 -5)");
    }

    @Test
    void testTurnDoesNotAdvanceAfterInvalidMove() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players135(), 20));
        assertFalse(gm.moveCurrentPlayer(0));
        assertEquals(1, gm.getCurrentPlayerID(), "Turno não deve avançar após jogada inválida");
    }

    @Test
    void testCircularOrderById() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players135(), 20));

        assertEquals(1, gm.getCurrentPlayerID());
        assertTrue(gm.moveCurrentPlayer(1));
        assertEquals(3, gm.getCurrentPlayerID());
        assertTrue(gm.moveCurrentPlayer(1));
        assertEquals(5, gm.getCurrentPlayerID());
        assertTrue(gm.moveCurrentPlayer(1));
        assertEquals(1, gm.getCurrentPlayerID(), "Após 3 jogadas válidas, volta ao 1 (players 1 - 3 - 5)");
    }

    @Test
    void testOrderIndependentOfPositions() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players135(), 50));

        assertTrue(gm.moveCurrentPlayer(6));
        assertEquals(3, gm.getCurrentPlayerID());

        assertTrue(gm.moveCurrentPlayer(2));
        assertEquals(5, gm.getCurrentPlayerID());

        assertTrue(gm.moveCurrentPlayer(4));
        assertEquals(1, gm.getCurrentPlayerID(), "Ordem depende dos IDs e não da posição");
    }
}