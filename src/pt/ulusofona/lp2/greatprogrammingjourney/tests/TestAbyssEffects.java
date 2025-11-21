package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.Core;

import java.io.File;

import static org.junit.Assert.*;

public class TestAbyssEffects {

    private Core core;

    private void loadScenario(String fileName) throws Exception {
        core = new Core();
        File f = new File("src/pt/ulusofona/lp2/greatprogrammingjourney/tests/saves/" + fileName);
        core.loadGame(f);
    }

    private String getState(int playerId) {
        String[] info = core.getProgrammerInfo(playerId);
        return info[6];
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
    public void Test_BSOD() throws Exception {
        int playerTestedID;
        boolean moveOk;

        // No counter tool
        loadScenario("AbyssEffect_BSOD");
        playerTestedID = core.getCurrentPlayerId();

        moveOk = core.moveCurrentPlayer(3);
        assertTrue(moveOk);

        String msg = core.reactToAbyssOrTool();
        assertNotNull(msg);

        String state = getState(playerTestedID);
        assertEquals("Derrotado", state);
    }

    @Test
    public void Test_StuckAndDefeated() throws Exception {
        int playerTestedID;
        String state;
        boolean moveOk;

        // Try to move Defeated player
        loadScenario("AbyssEffect_tryMoveDefeated");
        playerTestedID = core.getCurrentPlayerId();

        moveOk = core.moveCurrentPlayer(3);
        assertFalse(moveOk);

        state = getState(playerTestedID);
        assertEquals("Derrotado", state);

        // Try to move Stuck player
        loadScenario("AbyssEffect_tryMoveStuck");
        playerTestedID = core.getCurrentPlayerId();

        moveOk = core.moveCurrentPlayer(3);
        assertFalse(moveOk);

        state = getState(playerTestedID);
        assertEquals("Preso", state);
    }
}