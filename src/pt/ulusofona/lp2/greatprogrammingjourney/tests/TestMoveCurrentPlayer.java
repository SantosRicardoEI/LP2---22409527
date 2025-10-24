package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import static org.junit.jupiter.api.Assertions.*;

class TestMoveCurrentPlayer {

    private String[][] players3() {
        return new String[][]{
                {"1", "Dev One", "Java", "PURPLE"},
                {"2", "Dev Two", "Python", "BLUE"},
                {"3", "Dev Three", "C", "GREEN"}
        };
    }

    @Test
    void testMoveCurrentPlayer_validMove_returnsTrue_andUpdatesPosition() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 10));

        assertTrue(gm.moveCurrentPlayer(3), "Movimento válido deve retornar true");

        String[] info = gm.getProgrammerInfo(1);
        assertNotNull(info);
        assertEquals("4", info[4], "Jogador 1 deve estar na posição 4 após mover 3 casas");
    }

    @Test
    void testMoveCurrentPlayer_rollTooLow_returnsFalse_andNoChange() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 10));

        assertFalse(gm.moveCurrentPlayer(0), "Valor abaixo de 1 deve retornar false");

        String[] info = gm.getProgrammerInfo(1);
        assertNotNull(info);
        assertEquals("1", info[4], "Jogador deve permanecer na posição inicial");
    }

    @Test
    void testMoveCurrentPlayer_rollTooHigh_returnsFalse_andNoChange() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 10));

        assertFalse(gm.moveCurrentPlayer(7), "Valor acima de 6 deve retornar false");

        String[] info = gm.getProgrammerInfo(1);
        assertNotNull(info);
        assertEquals("1", info[4], "Jogador deve permanecer na posição inicial");
    }

    @Test
    void testMoveCurrentPlayer_reachesGoal_exactLanding() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 6));

        assertTrue(gm.moveCurrentPlayer(5));

        String[] info = gm.getProgrammerInfo(1);
        assertNotNull(info);
        assertEquals("6", info[4], "Jogador deve terminar exatamente na meta");
    }

    @Test
    void testMoveCurrentPlayer_reboundsIfExceedsGoal() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 6));

        assertTrue(gm.moveCurrentPlayer(7 - 1));
        assertTrue(gm.moveCurrentPlayer(1));

        String[] info = gm.getProgrammerInfo(1);
        assertNotNull(info);
        assertEquals("5", info[4]);
    }

    @Test
    void testMoveCurrentPlayer_turnPassesToNextPlayer() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 10));

        int firstId = gm.getCurrentPlayerID();
        assertTrue(gm.moveCurrentPlayer(2));

        int nextId = gm.getCurrentPlayerID();
        assertNotEquals(firstId, nextId, "Após o movimento, o turno deve passar ao próximo jogador");
    }

    @Test
    void testMoveCurrentPlayer_boardNotInitialized_returnsFalse() {
        GameManager gm = new GameManager();
        assertFalse(gm.moveCurrentPlayer(3), "Sem tabuleiro inicializado deve retornar false");
    }
}