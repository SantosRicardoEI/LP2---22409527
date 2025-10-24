package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import static org.junit.jupiter.api.Assertions.*;

class TestGetSlotInfo {

    private String[][] players3() {
        return new String[][]{
                {"1", "Dev One", "Java", "PURPLE"},
                {"2", "Dev Two", "Python", "BLUE"},
                {"3", "Dev Three", "C", "GREEN"}
        };
    }

    @Test
    void testGetSlotInfo_validPosition_returnsArrayWithIds() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 10));

        String[] slot = gm.getSlotInfo(1);
        assertNotNull(slot, "Deve devolver array não nulo");
        assertEquals(1, slot.length, "O array deve ter exatamente uma posição");
        assertEquals("1,2,3", slot[0], "A posição 1 deve conter todos os IDs dos jogadores iniciais");
    }

    @Test
    void testGetSlotInfo_emptySlot_returnsArrayWithEmptyString() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 10));

        String[] slot = gm.getSlotInfo(5);
        assertNotNull(slot, "Mesmo slot vazio deve devolver array não nulo");
        assertEquals(1, slot.length, "O array deve ter exatamente uma posição");
        assertEquals("", slot[0], "Slot vazio deve conter string vazia na primeira posição");
    }

    @Test
    void testGetSlotInfo_invalidBelowRange_returnsNull() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 10));

        assertNull(gm.getSlotInfo(0), "Posições abaixo de 1 devem retornar null");
    }

    @Test
    void testGetSlotInfo_invalidAboveRange_returnsNull() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(players3(), 10));

        assertNull(gm.getSlotInfo(11), "Posições acima do tamanho do tabuleiro devem retornar null");
    }

    @Test
    void testGetSlotInfo_notInitialized_returnsNull() {
        GameManager gm = new GameManager();
        assertNull(gm.getSlotInfo(1), "Deve retornar null se o tabuleiro ainda não tiver sido criado");
    }
}