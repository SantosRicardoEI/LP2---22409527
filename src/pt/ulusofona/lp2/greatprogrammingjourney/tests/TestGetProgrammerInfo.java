package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import static org.junit.jupiter.api.Assertions.*;

class TestGetProgrammerInfo {

    private String[][] players3MixedLangs() {
        return new String[][]{
                {"1", "Dev One", "Python; Lisp; Ada", "PURPLE"},
                {"2", "Dev Two", "Java", "BLUE"},
                {"3", "Dev Three", "Ada", "GREEN"}
        };
    }

    @Test
    void testGetProgrammerInfo_validId_returnsArrayOf5() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3MixedLangs(), 10));

        String[] info = gm.getProgrammerInfo(1);
        assertNotNull(info);
        assertEquals(5, info.length, "Deve devolver exatamente 5 campos");
    }

    @Test
    void testGetProgrammerInfo_validId_fieldValuesAreCorrect() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3MixedLangs(), 10));

        String[] info = gm.getProgrammerInfo(1);
        assertNotNull(info);

        assertEquals("1", info[0]);
        assertEquals("Dev One", info[1]);
        assertEquals("Python;Lisp;Ada", info[2], "Linguagens devem manter a ordem original e usar ';' (sem espaços)");
        assertEquals("Purple", info[3], "Cor deve iniciar com maiúscula");
        assertEquals("1", info[4], "Posição inicial deve ser \"1\"");
    }

    @Test
    void testGetProgrammerInfo_otherPlayer_valuesCorrect() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3MixedLangs(), 10));

        String[] info = gm.getProgrammerInfo(2);
        assertNotNull(info);

        assertEquals("2", info[0]);
        assertEquals("Dev Two", info[1]);
        assertEquals("Java", info[2]);
        assertEquals("Blue", info[3]);
        assertEquals("1", info[4]);
    }

    @Test
    void testGetProgrammerInfo_unknownId_returnsNull() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3MixedLangs(), 10));

        assertNull(gm.getProgrammerInfo(999), "IDs inexistentes devem retornar null");
        assertNull(gm.getProgrammerInfo(-5), "IDs inválidos devem retornar null");
    }
}