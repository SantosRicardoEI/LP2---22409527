package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import static org.junit.jupiter.api.Assertions.*;

class TestGetImagePng {

    private String[][] players2() {
        return new String[][]{
                {"1", "Dev One", "Java", "PURPLE"},
                {"2", "Dev Two", "Python", "BLUE"}
        };
    }

    @Test
    void testGetImagePng_outOfRangeBelow_returnsNull() {
        GameManager gm = new GameManager();
        int worldSize = 10;
        assertTrue(gm.createInitialBoard(players2(), worldSize));

        assertNull(gm.getImagePng(0), "Posições < 1 devem retornar null");
    }

    @Test
    void testGetImagePng_outOfRangeAbove_returnsNull() {
        GameManager gm = new GameManager();
        int worldSize = 10;
        assertTrue(gm.createInitialBoard(players2(), worldSize));

        assertNull(gm.getImagePng(worldSize + 1), "Posições > tamanho do tabuleiro devem retornar null");
    }

    @Test
    void testGetImagePng_lastSquare_returnsGloryPng() {
        GameManager gm = new GameManager();
        int worldSize = 10;
        assertTrue(gm.createInitialBoard(players2(), worldSize));

        assertEquals("glory.png", gm.getImagePng(worldSize), "A última posição deve retornar \"glory.png\"");
    }

    @Test
    void testGetImagePng_middleSquare_returnsNull() {
        GameManager gm = new GameManager();
        int worldSize = 10;
        assertTrue(gm.createInitialBoard(players2(), worldSize));

        assertNull(gm.getImagePng(5), "Posições intermédias devem retornar null");
    }
}