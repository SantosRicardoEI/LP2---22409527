package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import static org.junit.jupiter.api.Assertions.*;

class TestCreateInitBoard {

    private String[][] validPlayers() {
        return new String[][]{
                {"1", "Dev One", "Java", "PURPLE"},
                {"2", "Dev Two", "Python", "BLUE"},
                {"3", "Dev Three", "C", "GREEN"}
        };
    }

    private String[][] duplicateIds() {
        return new String[][]{
                {"1", "Dev One", "Java", "PURPLE"},
                {"1", "Dev Two", "Python", "BLUE"}
        };
    }

    private String[][] invalidIds() {
        return new String[][]{
                {"-1", "Dev One", "Java", "PURPLE"},
                {"2", "Dev Two", "Python", "BLUE"}
        };
    }

    private String[][] duplicateColors() {
        return new String[][]{
                {"1", "Dev One", "Java", "PURPLE"},
                {"2", "Dev Two", "Python", "PURPLE"}
        };
    }

    private String[][] invalidColor() {
        return new String[][]{
                {"1", "Dev One", "Java", "UNKNOWN"},
                {"2", "Dev Two", "Python", "BLUE"}
        };
    }


    @Test
    void testCreateInitialBoard_validInput_returnsTrue() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(validPlayers(), 10),
                "Deveria retornar true com worldSize >= (2 * nº jogadores)");
    }

    @Test
    void testCreateInitialBoard_invalidWorldSize_returnsFalse() {
        GameManager gm = new GameManager();
        assertFalse(gm.createInitialBoard(validPlayers(), 4),
                "Deve retornar false se o tamanho do tabuleiro for menor que (2 * nº jogadores)");
    }

    @Test
    void testCreateInitialBoard_lessThanTwoPlayers_returnsFalse() {
        GameManager gm = new GameManager();
        String[][] players = {{"1", "Solo", "Java", "PURPLE"}};
        assertFalse(gm.createInitialBoard(players, 10),
                "Deve retornar false se houver menos de 2 jogadores");
    }

    @Test
    void testCreateInitialBoard_moreThanFourPlayers_returnsFalse() {
        GameManager gm = new GameManager();
        String[][] players = {
                {"1", "A", "Java", "PURPLE"},
                {"2", "B", "Python", "BLUE"},
                {"3", "C", "C", "GREEN"},
                {"4", "D", "Ruby", "RED"},
                {"5", "E", "C++", "GRAY"}
        };
        assertFalse(gm.createInitialBoard(players, 20),
                "Deve retornar false se houver mais de 4 jogadores");
    }

    @Test
    void testCreateInitialBoard_duplicateIds_returnsFalse() {
        GameManager gm = new GameManager();
        assertFalse(gm.createInitialBoard(duplicateIds(), 10),
                "Deve retornar false se existirem IDs duplicados");
    }

    @Test
    void testCreateInitialBoard_invalidIds_returnsFalse() {
        GameManager gm = new GameManager();
        assertFalse(gm.createInitialBoard(invalidIds(), 10),
                "Deve retornar false se existirem IDs negativos ou inválidos");
    }

    @Test
    void testCreateInitialBoard_duplicateColors_returnsFalse() {
        GameManager gm = new GameManager();
        assertFalse(gm.createInitialBoard(duplicateColors(), 10),
                "Deve retornar false se existirem cores duplicadas");
    }

    @Test
    void testCreateInitialBoard_invalidColor_returnsFalse() {
        GameManager gm = new GameManager();
        assertFalse(gm.createInitialBoard(invalidColor(), 10),
                "Deve retornar false se alguma cor não for reconhecida");
    }
}