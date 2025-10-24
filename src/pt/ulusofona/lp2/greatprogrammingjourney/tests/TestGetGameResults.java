package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestGetGameResults {

    private String[][] players3() {
        return new String[][]{
                {"1", "Dev One", "Java", "PURPLE"},
                {"2", "Dev Two", "Python", "BLUE"},
                {"3", "Dev Three", "C", "GREEN"}
        };
    }

    @Test
    void testGetGameResults_beforeGameOver_returnsEmptyList() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 20));

        ArrayList<String> results = gm.getGameResults();
        assertNotNull(results);
        assertTrue(results.isEmpty(), "Antes de o jogo terminar a lista deve estar vazia");
    }

    @Test
    void testGetGameResults_afterWin_returnsFormattedList() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 6));

        gm.moveCurrentPlayer(5);

        ArrayList<String> results = gm.getGameResults();
        assertNotNull(results);
        assertFalse(results.isEmpty(), "Após a vitória deve devolver uma lista preenchida");

        ArrayList<String> expected = new ArrayList<>();
        expected.add("THE GREAT PROGRAMMING JOURNEY");
        expected.add("");
        expected.add("NR. DE TURNOS");
        expected.add("2");
        expected.add("");
        expected.add("VENCEDOR");
        expected.add("Dev One");
        expected.add("");
        expected.add("RESTANTES");
        expected.add("Dev Two 1");
        expected.add("Dev Three 1");

        assertEquals(expected, results);
    }

    @Test
    void testGetGameResults_noBoardInitialized_returnsEmptyList() {
        GameManager gm = new GameManager();
        ArrayList<String> results = gm.getGameResults();
        assertNotNull(results);
        assertTrue(results.isEmpty(), "Sem tabuleiro inicializado deve retornar lista vazia");
    }
}