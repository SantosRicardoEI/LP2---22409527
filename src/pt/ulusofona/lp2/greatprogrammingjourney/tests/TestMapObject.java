package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.Core;

import java.io.File;

import static org.junit.Assert.*;

public class TestMapObject {

    private Core core;

    private void loadScenario(String fileName) throws Exception {
        core = new Core();
        File f = new File("test-files/" + fileName);
        core.loadGame(f);
    }

    private String getState(int playerId) {
        String[] info = core.getProgrammerInfo(playerId);
        return info[6];
    }

    private String getAbyssOnSlot(int nrSlot) {
        String[] info = core.getSlotInfo(nrSlot);
        return info[2];
    }

    private int getPosition(int playerId) {
        String[] info = core.getProgrammerInfo(playerId);
        return Integer.parseInt(info[4]);
    }

    private String getTools(int playerId) {
        String[] info = core.getProgrammerInfo(playerId);
        return info[5];
    }


    @Test
    public void test_infinite_loop() throws Exception {
        int player1 = 1;
        int player2 = 2;
        int player3 = 3;
        int player4 = 4;

        // Without counter
        loadScenario("AbyssEffect_Infinite_Loop_Liberta_Jogadores");

        // Verifico o estado dos jogadores (1 está em jogo e os restantes presos na casa 10)
        assertEquals("Em Jogo", getState(player1));
        assertEquals("Preso", getState(player2));
        assertEquals("Preso", getState(player3));
        assertEquals("Preso", getState(player4));

        // Movo o jogador 1
        assertTrue(core.moveCurrentPlayer(6));
        core.reactToAbyssOrTool();

        // Movo o jogador 2
        assertFalse(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();

        // Movo o jogador 3
        assertFalse(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();

        // Movo o jogador 4
        assertFalse(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();

        // Movo o jogador 1 e deverá cair no abismo
        assertTrue(core.moveCurrentPlayer(3));
        core.reactToAbyssOrTool();

        // Agora estao todos na posicao 10
        assertEquals(10,getPosition(player1));
        assertEquals(10,getPosition(player2));
        assertEquals(10,getPosition(player3));
        assertEquals(10,getPosition(player4));

        // O unico preso devera ser o jogador 1
        assertEquals("Preso", getState(player1));
        assertEquals("Em Jogo", getState(player2));
        assertEquals("Em Jogo", getState(player3));
        assertEquals("Em Jogo", getState(player4));

        // Movo o jogador 2
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();

        // Movo o jogador 3
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();

        // Movo o jogador 4
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
    }

    @Test
    public void test_infinite_loop_tool() throws Exception {
        int player1 = 1;
        int player2 = 2;
        int player3 = 3;
        int player4 = 4;

        loadScenario("AbyssCounter_Infinite_Loop_TestTool");

        // Verifico o estado inicial
        assertEquals("Em Jogo", getState(player1));
        assertEquals("Em Jogo", getState(player2));
        assertEquals("Em Jogo", getState(player3));
        assertEquals("Em Jogo", getState(player4));
        assertEquals(1,getPosition(player1));
        assertEquals(1,getPosition(player2));
        assertEquals(1,getPosition(player3));
        assertEquals(1,getPosition(player4));
        assertEquals("A:8", getAbyssOnSlot(10));

        // ======== Movo todos para a casa 5 ===================

        // Movo o jogador 1
        assertEquals(player1,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(5,getPosition(player1));

        // Movo o jogador 2
        assertEquals(player2,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(5,getPosition(player2));

        // Movo o jogador 3
        assertEquals(player3,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(5,getPosition(player3));

        // Movo o jogador 4
        assertEquals(player4,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(5,getPosition(player4));

        // ======== Movo 1 para o abismo e restantes para uma casa antes ==============

        // Movo o jogador 1 para o abismo e nao fica preso porque tem tool
        assertEquals(player1,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(5));
        core.reactToAbyssOrTool();
        assertEquals(10,getPosition(player1));
        assertEquals("Em Jogo", getState(player1));

        // Movo o jogador 2 para casa antes do abismo
        assertEquals(player2,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(9,getPosition(player2));
        assertEquals("Em Jogo", getState(player2));

        // Movo o jogador 3 para casa antes do abismo
        assertEquals(player3,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(9,getPosition(player3));
        assertEquals("Em Jogo", getState(player3));

        // Movo o jogador 4 para casa antes do abismo
        assertEquals(player4,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(9,getPosition(player4));
        assertEquals("Em Jogo", getState(player4));

        // ======== Movo todos 2, 3 e 4 para o abismo (1 sai) ===================

        // Movo o jogador 1 para fora do abismo
        assertEquals(player1,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();
        assertEquals(12,getPosition(player1));
        assertEquals("Em Jogo", getState(player1));

        // Movo o jogador 2 para o abismo (sem tool fica preso)
        assertEquals(player2,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(10,getPosition(player2));
        assertEquals("Preso", getState(player2));

        // Movo o jogador 3 para o abismo (sem tool fica preso, e liberta o jogador 2)
        assertEquals(player3,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(10,getPosition(player3));
        assertEquals("Preso", getState(player3));

        // Movo o jogador 4 o abismo (com tool nao fica preso)
        assertEquals(player4,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(10,getPosition(player4));
        assertEquals("Em Jogo", getState(player4));

        // Verifico os estados
        // Segundo o Bob um jogador com a tool nao liberta os outros
        assertEquals("Em Jogo", getState(player1));
        assertEquals("Em Jogo", getState(player2));
        assertEquals("Preso", getState(player3));
        assertEquals("Em Jogo", getState(player4));

        // ======== Movo todos, só o 3 está preso ===================

        // Movo o jogador 1
        assertEquals(player1,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(13,getPosition(player1));
        assertEquals("Em Jogo", getState(player1));

        // Movo o jogador 2
        assertEquals(player2,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(3));
        core.reactToAbyssOrTool();
        assertEquals(13,getPosition(player2));
        assertEquals("Em Jogo", getState(player2));

        // Movo o jogador 3
        assertEquals(player3,core.getCurrentPlayerId());
        assertFalse(core.moveCurrentPlayer(3));
        core.reactToAbyssOrTool();
        assertEquals(10,getPosition(player3));
        assertEquals("Preso", getState(player3));

        // Movo o jogador 4
        assertEquals(player4,core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(3));
        core.reactToAbyssOrTool();
        assertEquals(13,getPosition(player4));
        assertEquals("Em Jogo", getState(player4));
    }
}