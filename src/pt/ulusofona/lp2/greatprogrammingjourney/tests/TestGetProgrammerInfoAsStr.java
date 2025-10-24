package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import static org.junit.jupiter.api.Assertions.*;

class TestGetProgrammerInfoAsStr {

    private String[][] players3MixedLangs() {
        return new String[][]{
                {"1", "Dev One", "Python; Lisp; Ada", "PURPLE"},
                {"2", "Dev Two", "Java", "BLUE"},
                {"3", "Dev Three", "ada; lisp; PYTHON", "GREEN"}
        };
    }

    @Test
    void testGetProgrammerInfoAsStr_validId_returnsFormattedString() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3MixedLangs(), 10));

        String s = gm.getProgrammerInfoAsStr(1);
        assertNotNull(s);

        assertEquals(
                "1 | Dev One | 1 | Ada; Lisp; Python | Em Jogo",
                s,
                "Deves formatar como '<ID> | <Nome> | <Pos> | <Langs ordenadas> | <Estado>'"
        );
    }

    @Test
    void testGetProgrammerInfoAsStr_validId_singleLanguage() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3MixedLangs(), 10));

        String s = gm.getProgrammerInfoAsStr(2);
        assertNotNull(s);
        assertEquals(
                "2 | Dev Two | 1 | Java | Em Jogo",
                s,
                "Com uma linguagem deves manter o mesmo formato e não adicionar espaços extra"
        );
    }

    @Test
    void testGetProgrammerInfoAsStr_orderingIsAlphabetical_caseInsensitive() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3MixedLangs(), 10));

        String s = gm.getProgrammerInfoAsStr(3);
        assertNotNull(s);
        assertTrue(
                s.endsWith("ada; lisp; PYTHON | Em Jogo"),
                "As linguagens devem aparecer ordenadas alfabeticamente e separadas por '; ' (com espaço)"
        );
        assertTrue(s.startsWith("3 | Dev Three | 1 | "), "Prefixo deve conter '3 | Dev Three | 1 | '");
    }

    @Test
    void testGetProgrammerInfoAsStr_unknownId_returnsNull() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3MixedLangs(), 10));

        assertNull(gm.getProgrammerInfoAsStr(999), "IDs inexistentes devem retornar null");
        assertNull(gm.getProgrammerInfoAsStr(-1), "IDs inválidos devem retornar null");
    }
}