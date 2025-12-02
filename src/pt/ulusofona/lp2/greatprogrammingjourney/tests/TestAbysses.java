package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.Core;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;

import java.io.File;

import static org.junit.Assert.*;

public class TestAbysses {

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

    // ========================= Test Counters ====================

    /*
    @Test
    public void testAbyssCountersCorrectTools() {
        Abyss syntax = AbyssSubType.SYNTAX_ERROR.getInstance();
        Abyss logic = AbyssSubType.LOGIC_ERROR.getInstance();
        Abyss exception = AbyssSubType.EXCEPTION.getInstance();
        Abyss fileNotFound = AbyssSubType.FILE_NOT_FOUND.getInstance();
        Abyss duplicated = AbyssSubType.DUPLICATED_CODE.getInstance();
        Abyss secondary = AbyssSubType.SECONDARY_EFFECTS.getInstance();
        Abyss infiniteLoop = AbyssSubType.INFINITE_LOOP.getInstance();

        Tool ide = ToolSubType.IDE.getInstance();
        Tool unitTests = ToolSubType.UNIT_TESTS.getInstance();
        Tool exceptionHandling = ToolSubType.EXCEPTION_HANDLING.getInstance();
        Tool inheritance = ToolSubType.INHERITANCE.getInstance();
        Tool fp = ToolSubType.FUNCTIONAL_PROGRAMMING.getInstance();
        Tool teacherHelp = ToolSubType.TEACHER_HELP.getInstance();

        assertSame(ide, syntax.getCounter());
        assertSame(unitTests, logic.getCounter());
        assertSame(exceptionHandling, exception.getCounter());
        assertSame(exceptionHandling, fileNotFound.getCounter());
        assertSame(inheritance, duplicated.getCounter());
        assertSame(fp, secondary.getCounter());
        assertSame(teacherHelp, infiniteLoop.getCounter());
    }

    // ========================= BSOD ====================

    @Test
    public void test_blue_screen_of_death_defeats_player() throws Exception {
        loadScenario("AbyssEffect_BlueScreenOfDeath");

        int playerId = 1;

        assertEquals("Em Jogo", getState(playerId));
        assertEquals(5, getPosition(playerId));
        assertEquals("A:7", getAbyssOnSlot(7));

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();

        assertEquals(7, getPosition(playerId));
        assertEquals("Derrotado", getState(playerId));
    }

    // ========================= SYNTAX_ERROR ====================

    @Test
    public void test_syntax_error_effect() throws Exception {
        loadScenario("AbyssEffect_SyntaxError");

        int playerId = 1;
        int posBefore = getPosition(playerId);
        String stateBefore = getState(playerId);

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        String stateAfter = getState(playerId);

        assertTrue(posAfter != posBefore || !stateAfter.equals(stateBefore));
    }

    @Test
    public void test_syntax_error_vsTool() throws Exception {
        loadScenario("AbyssEffect_SyntaxError_vsTool");

        int playerId = 1;
        int posBefore = getPosition(playerId);
        assertTrue(getTools(playerId).contains("IDE"));

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        String stateAfter = getState(playerId);

        assertEquals("Em Jogo", stateAfter);
        assertTrue(posAfter > posBefore);
        assertEquals("", getTools(playerId));
    }

    // ========================= LOGIC_ERROR ====================

    @Test
    public void test_logic_error_effect() throws Exception {
        loadScenario("AbyssEffect_LogicError");

        int playerId = 1;
        int posBefore = getPosition(playerId);
        String stateBefore = getState(playerId);

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        String stateAfter = getState(playerId);

        assertTrue(posAfter != posBefore || !stateAfter.equals(stateBefore));
    }

    @Test
    public void test_logic_error_vsTool() throws Exception {
        loadScenario("AbyssEffect_LogicError_vsTool");

        int playerId = 1;
        int posBefore = getPosition(playerId);
        assertTrue(getTools(playerId).contains("Testes Unitários"));

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        String stateAfter = getState(playerId);

        assertEquals("Em Jogo", stateAfter);
        assertTrue(posAfter > posBefore);
        assertEquals("", getTools(playerId));
    }

    // ========================= EXCEPTION ====================

    @Test
    public void test_exception_effect() throws Exception {
        loadScenario("AbyssEffect_Exception");

        int playerId = 1;
        int posBefore = getPosition(playerId);
        String stateBefore = getState(playerId);

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        String stateAfter = getState(playerId);

        assertTrue(posAfter != posBefore || !stateAfter.equals(stateBefore));
    }

    @Test
    public void test_exception_vsTool() throws Exception {
        loadScenario("AbyssEffect_Exception_vsTool");

        int playerId = 1;
        int posBefore = getPosition(playerId);
        assertTrue(getTools(playerId).contains("Tratamento de Excepções"));

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        String stateAfter = getState(playerId);

        assertEquals("Em Jogo", stateAfter);
        assertTrue(posAfter > posBefore);
        assertEquals("", getTools(playerId));
    }

    // ========================= FILE_NOT_FOUND ====================

    @Test
    public void test_file_not_found_effect() throws Exception {
        loadScenario("AbyssEffect_FileNotFound");

        int playerId = 1;
        int posBefore = getPosition(playerId);

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        String stateAfter = getState(playerId);

        assertEquals("Em Jogo", stateAfter);
        assertTrue(posAfter < posBefore + 4);
    }

    @Test
    public void test_file_not_found_vsTool() throws Exception {
        loadScenario("AbyssEffect_FileNotFound_vsTool");

        int playerId = 1;
        int posBefore = getPosition(playerId);
        assertTrue(getTools(playerId).contains("Tratamento de Excepções"));

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        String stateAfter = getState(playerId);

        assertEquals("Em Jogo", stateAfter);
        assertEquals(posBefore + 4, posAfter);
        assertEquals("", getTools(playerId));
    }

    // ========================= CRASH ====================

    @Test
    public void test_crash_effect() throws Exception {
        loadScenario("AbyssEffect_Crash");

        int playerId = 1;

        assertEquals("Em Jogo", getState(playerId));

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();

        assertEquals("Derrotado", getState(playerId));
    }

    // ========================= DUPLICATED_CODE ====================

    @Test
    public void test_duplicated_code_effect() throws Exception {
        loadScenario("AbyssEffect_DuplicatedCode");

        int playerId = 1;
        int posBefore = getPosition(playerId);

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        assertTrue(posAfter != posBefore);
    }

    @Test
    public void test_duplicated_code_vsTool() throws Exception {
        loadScenario("AbyssEffect_DuplicatedCode_vsTool");

        int playerId = 1;
        int posBefore = getPosition(playerId);
        assertTrue(getTools(playerId).contains("Herança"));

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        assertEquals("Em Jogo", getState(playerId));
        assertTrue(posAfter > posBefore);
        assertEquals("", getTools(playerId));
    }

    // ========================= SECONDARY_EFFECTS ====================

    @Test
    public void test_secondary_effects_effect() throws Exception {
        loadScenario("AbyssEffect_SecondaryEffects");

        int p1 = 1;
        int p2 = 2;

        assertEquals(p1, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();

        assertEquals(p2, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();

        int pos1 = getPosition(p1);
        int pos2 = getPosition(p2);

        assertEquals(pos1, pos2);
    }

    @Test
    public void test_secondary_effects_vsTool() throws Exception {
        loadScenario("AbyssEffect_SecondaryEffects_vsTool");

        int p1 = 1;
        int p2 = 2;

        assertTrue(getTools(p1).contains("Programação Funcional"));

        assertEquals(p1, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();

        assertEquals(p2, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();

        int pos1 = getPosition(p1);
        int pos2 = getPosition(p2);

        assertNotEquals(pos1, pos2);
    }

    // ========================= SEGMENTATION_FAULT ====================

    @Test
    public void test_segmentation_fault_effect() throws Exception {
        loadScenario("AbyssEffect_SegmentationFault");

        int playerId = 1;
        int posBefore = getPosition(playerId);

        assertEquals(playerId, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();

        int posAfter = getPosition(playerId);
        assertTrue(posAfter != posBefore);
    }

     */


// ========================= INFINITE_LOOP ====================

    @Test
    public void test_infinite_loop() throws Exception {
        int player1 = 1;
        int player2 = 2;
        int player3 = 3;
        int player4 = 4;

        loadScenario("AbyssEffect_Infinite_Loop");

        // Estado inicial: 1 em jogo, 2–4 presos na casa 10
        assertEquals("Em Jogo", getState(player1));
        assertEquals("Preso", getState(player2));
        assertEquals("Preso", getState(player3));
        assertEquals("Preso", getState(player4));

        assertEquals(1, getPosition(player1));
        assertEquals(10, getPosition(player2));
        assertEquals(10, getPosition(player3));
        assertEquals(10, getPosition(player4));
        assertEquals("A:8", getAbyssOnSlot(10));

        // ====== Jogador 1 anda 6 casas (fica fora do abismo) ==================
        assertEquals(player1, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(6));
        core.reactToAbyssOrTool();

        assertEquals(7, getPosition(player1));
        assertEquals("Em Jogo", getState(player1));

        // ====== Jogadores 2, 3 e 4 continuam presos ===========================
        assertEquals(player2, core.getCurrentPlayerId());
        assertFalse(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(10, getPosition(player2));
        assertEquals("Preso", getState(player2));

        assertEquals(player3, core.getCurrentPlayerId());
        assertFalse(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(10, getPosition(player3));
        assertEquals("Preso", getState(player3));

        assertEquals(player4, core.getCurrentPlayerId());
        assertFalse(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(10, getPosition(player4));
        assertEquals("Preso", getState(player4));

        // ====== Jogador 1 entra no abismo e prende-se, libertando os outros ===
        assertEquals(player1, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(3));
        core.reactToAbyssOrTool();

        assertEquals(10, getPosition(player1));
        assertEquals(10, getPosition(player2));
        assertEquals(10, getPosition(player3));
        assertEquals(10, getPosition(player4));

        assertEquals("Preso", getState(player1));
        assertEquals("Em Jogo", getState(player2));
        assertEquals("Em Jogo", getState(player3));
        assertEquals("Em Jogo", getState(player4));

        // ====== Agora só o jogador 1 está preso; 2,3,4 já se podem mover ======
        assertEquals(player2, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertNotEquals(10, getPosition(player2));
        assertEquals("Em Jogo", getState(player2));

        assertEquals(player3, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertNotEquals(10, getPosition(player3));
        assertEquals("Em Jogo", getState(player3));

        assertEquals(player4, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertNotEquals(10, getPosition(player4));
        assertEquals("Em Jogo", getState(player4));
    }

    @Test
    public void test_infinite_loop_tool() throws Exception {
        loadScenario("AbyssEffect_InfiniteLoop_vsTool");
        int player1 = 1;
        int player2 = 2;
        int player3 = 3;
        int player4 = 4;

        // Verifico o estado inicial
        assertEquals("Em Jogo", getState(player1));
        assertEquals("Em Jogo", getState(player2));
        assertEquals("Em Jogo", getState(player3));
        assertEquals("Em Jogo", getState(player4));
        assertEquals(1, getPosition(player1));
        assertEquals(1, getPosition(player2));
        assertEquals(1, getPosition(player3));
        assertEquals(1, getPosition(player4));
        assertEquals("A:8", getAbyssOnSlot(10));

        // ======== Movo todos para a casa 5 ===================

        // Movo o jogador 1
        assertEquals(player1, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(5, getPosition(player1));

        // Movo o jogador 2
        assertEquals(player2, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(5, getPosition(player2));

        // Movo o jogador 3
        assertEquals(player3, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(5, getPosition(player3));

        // Movo o jogador 4
        assertEquals(player4, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(5, getPosition(player4));

        // ======== Movo 1 para o abismo e restantes para uma casa antes ==============

        // Movo o jogador 1 para o abismo e nao fica preso porque tem tool
        assertEquals(player1, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(5));
        core.reactToAbyssOrTool();
        assertEquals(10, getPosition(player1));
        assertEquals("Em Jogo", getState(player1));

        // Movo o jogador 2 para casa antes do abismo
        assertEquals(player2, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(9, getPosition(player2));
        assertEquals("Em Jogo", getState(player2));

        // Movo o jogador 3 para casa antes do abismo
        assertEquals(player3, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(9, getPosition(player3));
        assertEquals("Em Jogo", getState(player3));

        // Movo o jogador 4 para casa antes do abismo
        assertEquals(player4, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(4));
        core.reactToAbyssOrTool();
        assertEquals(9, getPosition(player4));
        assertEquals("Em Jogo", getState(player4));

        // ======== Movo todos 2, 3 e 4 para o abismo (1 sai) ===================

        // Movo o jogador 1 para fora do abismo
        assertEquals(player1, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(2));
        core.reactToAbyssOrTool();
        assertEquals(12, getPosition(player1));
        assertEquals("Em Jogo", getState(player1));

        // Movo o jogador 2 para o abismo (sem tool fica preso)
        assertEquals(player2, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(10, getPosition(player2));
        assertEquals("Preso", getState(player2));

        // Movo o jogador 3 para o abismo (sem tool fica preso, e liberta o jogador 2)
        assertEquals(player3, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(10, getPosition(player3));
        assertEquals("Preso", getState(player3));

        // Movo o jogador 4 o abismo (com tool nao fica preso)
        assertEquals(player4, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(10, getPosition(player4));
        assertEquals("Em Jogo", getState(player4));

        // Verifico os estados
        // Segundo o Bob um jogador com a tool nao liberta os outros
        assertEquals("Em Jogo", getState(player1));
        assertEquals("Em Jogo", getState(player2));
        assertEquals("Preso", getState(player3));
        assertEquals("Em Jogo", getState(player4));

        // ======== Movo todos, só o 3 está preso ===================

        // Movo o jogador 1
        assertEquals(player1, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(1));
        core.reactToAbyssOrTool();
        assertEquals(13, getPosition(player1));
        assertEquals("Em Jogo", getState(player1));

        // Movo o jogador 2
        assertEquals(player2, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(3));
        core.reactToAbyssOrTool();
        assertEquals(13, getPosition(player2));
        assertEquals("Em Jogo", getState(player2));

        // Movo o jogador 3
        assertEquals(player3, core.getCurrentPlayerId());
        assertFalse(core.moveCurrentPlayer(3));
        core.reactToAbyssOrTool();
        assertEquals(10, getPosition(player3));
        assertEquals("Preso", getState(player3));

        // Movo o jogador 4
        assertEquals(player4, core.getCurrentPlayerId());
        assertTrue(core.moveCurrentPlayer(3));
        core.reactToAbyssOrTool();
        assertEquals(13, getPosition(player4));
        assertEquals("Em Jogo", getState(player4));
    }
}