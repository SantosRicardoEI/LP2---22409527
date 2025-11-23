package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.MapObjectType;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.Core;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MapObjectTest {

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
        assertTrue("Move after dice roll should succeed before BSOD effect is applied", moveOk);

        String msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        String state = getState(playerTestedID);
        assertEquals("Player should be defeated after BSOD", "Derrotado", state);
    }

    @Test
    public void Test_CodeDuplication() throws Exception {
        int playerTestedID;
        boolean moveOk;
        String state;
        int pos;
        String tools;

        // Without tool
        loadScenario("AbyssEffect_DuplicatedCode");
        playerTestedID = core.getCurrentPlayerId();

        int originalPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 1", 1, originalPos);

        moveOk = core.moveCurrentPlayer(3);
        assertTrue("Move after dice roll should succeed before Code Duplication effect is applied", moveOk);

        String msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        pos = getPosition(playerTestedID);
        assertEquals(
                "Player should move back to the previous position after Code Duplication abyss",
                originalPos,
                pos
        );

        state = getState(playerTestedID);
        assertEquals("Player should remain in game after Code Duplication", "Em Jogo", state);

        // With tool
        loadScenario("AbyssCounter_DuplicatedCode");
        playerTestedID = core.getCurrentPlayerId();

        originalPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 1", 1, originalPos);

        moveOk = core.moveCurrentPlayer(3);
        assertTrue("Move after dice roll should succeed when player has counter tool", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        pos = getPosition(playerTestedID);
        assertEquals(
                "Player position should not be reverted when Code Duplication is countered",
                originalPos + 3,
                pos
        );

        tools = getTools(playerTestedID);
        assertEquals(
                "Player should have no tools after using counter on Code Duplication",
                "No tools",
                tools
        );

        state = getState(playerTestedID);
        assertEquals("Player should remain in game when Code Duplication is countered", "Em Jogo", state);
    }

    @Test
    public void Test_Crash() throws Exception {
        int playerTestedID;
        boolean moveOk;

        loadScenario("AbyssEffect_Crash");
        playerTestedID = core.getCurrentPlayerId();

        int originalPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 1", 1, originalPos);

        moveOk = core.moveCurrentPlayer(3);
        assertTrue("Move after dice roll should succeed before Crash effect is applied", moveOk);

        String msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        int posAfter = getPosition(playerTestedID);
        assertEquals(
                "Player should be sent back to the starting position after Crash",
                1,
                posAfter
        );

        String state = getState(playerTestedID);
        assertEquals("Player should remain in game after Crash", "Em Jogo", state);
    }

    @Test
    public void Test_ExceptionA() throws Exception {
        int playerTestedID;
        boolean moveOk;
        int initialPos;
        int posAfter;
        String msg;
        String state;

        // ====================== Sem counter ======================
        loadScenario("AbyssEffect_ExceptionA");
        playerTestedID = core.getCurrentPlayerId();

        initialPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 1", 1, initialPos);

        moveOk = core.moveCurrentPlayer(3);
        assertTrue("Move should succeed before Exception effect is applied", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        posAfter = getPosition(playerTestedID);
        assertEquals(
                "Player should move back 2 positions after Exception (-2 from position 4 → 2)",
                2,
                posAfter
        );

        state = getState(playerTestedID);
        assertEquals("Player should stay in game after Exception", "Em Jogo", state);

        // ====================== Com counter ======================
        loadScenario("AbyssCounter_ExceptionA");
        playerTestedID = core.getCurrentPlayerId();

        initialPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 1", 1, initialPos);

        moveOk = core.moveCurrentPlayer(3);
        assertTrue("Move should succeed before Exception effect is applied", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        posAfter = getPosition(playerTestedID);
        assertEquals(
                "Player should stay on the target position when Exception is countered (from 1 to 4)",
                4,
                posAfter
        );

        state = getState(playerTestedID);
        assertEquals("Player should stay in game after Exception is countered", "Em Jogo", state);
    }

    @Test
    public void Test_FileNotFound() throws Exception {
        int playerTestedID;
        boolean moveOk;
        int initialPos;
        int posAfter;
        String msg;
        String state;

        // Without counter
        loadScenario("AbyssEffect_FileNotFound");
        playerTestedID = core.getCurrentPlayerId();

        initialPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 1", 1, initialPos);

        moveOk = core.moveCurrentPlayer(4);
        assertTrue("Move should succeed before FileNotFound effect is applied", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        posAfter = getPosition(playerTestedID);
        assertEquals(
                "Player should move back 3 positions after FileNotFound (from 5 → 2)",
                2,
                posAfter
        );

        state = getState(playerTestedID);
        assertEquals("Player should stay in game after FileNotFound", "Em Jogo", state);


        // With counter
        loadScenario("AbyssCounter_FileNotFound");
        playerTestedID = core.getCurrentPlayerId();

        initialPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 1", 1, initialPos);

        moveOk = core.moveCurrentPlayer(4);
        assertTrue("Move should succeed before FileNotFound effect is applied", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        posAfter = getPosition(playerTestedID);
        assertEquals(
                "Player should remain on the final position when FileNotFound is countered (1 + 4 = 5)",
                5,
                posAfter
        );

        state = getState(playerTestedID);
        assertEquals("Player should stay in game after FileNotFound is countered", "Em Jogo", state);
    }

    @Test
    public void Test_LogicError() throws Exception {
        int playerTestedID;
        boolean moveOk;
        int initialPos;
        int posAfter;
        String msg;
        String state;

        // Without counter
        loadScenario("AbyssEffect_LogicError");
        playerTestedID = core.getCurrentPlayerId();

        initialPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 1", 1, initialPos);

        moveOk = core.moveCurrentPlayer(4);
        assertTrue("Move should succeed before LogicError effect is applied", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        posAfter = getPosition(playerTestedID);
        assertEquals(
                "Player should move back half dice value (4/2 = 2) after LogicError (from 5 → 3)",
                3,
                posAfter
        );

        state = getState(playerTestedID);
        assertEquals("Player should stay in game after LogicError", "Em Jogo", state);


        // With counter
        loadScenario("AbyssCounter_LogicError");
        playerTestedID = core.getCurrentPlayerId();

        initialPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 1", 1, initialPos);

        moveOk = core.moveCurrentPlayer(4);
        assertTrue("Move should succeed before LogicError effect is applied", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss interaction message should not be null", msg);

        posAfter = getPosition(playerTestedID);
        assertEquals(
                "Player should remain on the final position when LogicError is countered (1 + 4 = 5)",
                5,
                posAfter
        );

        state = getState(playerTestedID);
        assertEquals("Player should stay in game after LogicError is countered", "Em Jogo", state);
    }

    @Test
    public void Test_SecondaryEffects() throws Exception {
        int playerTestedID;
        boolean moveOk;
        String msg;
        int posAfter;
        String state;

        // Without counter
        loadScenario("AbyssEffect_SecondaryEffects");
        playerTestedID = core.getCurrentPlayerId();

        int initialPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 3 after previous move history", 3, initialPos);

        moveOk = core.moveCurrentPlayer(2);
        assertTrue("Move before SecondaryEffects should succeed", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss message should not be null", msg);

        posAfter = getPosition(playerTestedID);
        assertEquals(
                "Player should return to the position from the previous turn (1)",
                1,
                posAfter
        );


        state = getState(playerTestedID);
        assertEquals("Player should remain in-game after SecondaryEffects", "Em Jogo", state);


        // With counter
        loadScenario("AbyssCounter_SecondaryEffects");
        playerTestedID = core.getCurrentPlayerId();

        initialPos = getPosition(playerTestedID);
        assertEquals("Initial position should be 3 after previous move history", 3, initialPos);

        moveOk = core.moveCurrentPlayer(2);
        assertTrue("Move before SecondaryEffects should succeed", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss message should not be null", msg);

        posAfter = getPosition(playerTestedID);
        assertEquals(
                "Player should stay on final square (5) because effect was countered",
                5,
                posAfter
        );

        state = getState(playerTestedID);
        assertEquals("Player should remain in-game after countering SecondaryEffects", "Em Jogo", state);
    }

    @Test
    public void Test_SegmentationFault() throws Exception {
        int playerA;
        int playerB;
        boolean moveOk;

        // 1 Player in slot
        loadScenario("AbyssEffect_SegFault_single");
        playerA = core.getCurrentPlayerId();

        int initialPos = getPosition(playerA);
        assertEquals("Initial position should be 2", 2, initialPos);

        moveOk = core.moveCurrentPlayer(1);  // 2 -> 3 (casa com SegFault)
        assertTrue("Move should succeed before SegFault", moveOk);

        String msg = core.reactToAbyssOrTool();
        assertNotNull("Message should not be null", msg);

        int posAfter = getPosition(playerA);
        assertEquals("Only one player: position should stay unchanged", 3, posAfter);


        // 2 players in slot
        loadScenario("AbyssEffect_SegFault_multi");

        playerA = core.getCurrentPlayerId(); // 1
        playerB = 2;

        assertEquals("Player A should start at 6", 6, getPosition(playerA));
        assertEquals("Player B should start at 6", 6, getPosition(playerB));

        msg = core.reactToAbyssOrTool();
        assertNotNull("Message should not be null when SegFault triggers", msg);

        int posA = getPosition(playerA);
        int posB = getPosition(playerB);

        assertEquals("Both players should move from 6 to 3", 3, posA);
        assertEquals("Both players should move from 6 to 3", 3, posB);
    }

    @Test
    public void Test_SyntaxError() throws Exception {

        int playerID;
        boolean moveOk;

        // Without counter
        loadScenario("AbyssEffect_SyntaxError");
        playerID = core.getCurrentPlayerId();

        int initialPos = getPosition(playerID);
        assertEquals("Initial position should be 1", 1, initialPos);

        moveOk = core.moveCurrentPlayer(3);
        assertTrue("Move should succeed before SyntaxError effect", moveOk);

        String msg = core.reactToAbyssOrTool();
        assertNotNull("Abyss message should not be null", msg);

        int posAfter = getPosition(playerID);

        assertEquals(
                "Player should move back 1 position after SyntaxError (4 → 3)",
                3,
                posAfter
        );

        String state = getState(playerID);
        assertEquals("Player should remain in game", "Em Jogo", state);

        // With counter
        loadScenario("AbyssCounter_SyntaxError");
        playerID = core.getCurrentPlayerId();

        initialPos = getPosition(playerID);
        assertEquals("Initial position should be 1", 1, initialPos);

        moveOk = core.moveCurrentPlayer(3);
        assertTrue("Move should succeed", moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull("Message should not be null", msg);

        posAfter = getPosition(playerID);
        assertEquals(
                "Player should NOT move because IDE counters SyntaxError",
                4,
                posAfter
        );

        state = getState(playerID);
        assertEquals("Player should remain in game", "Em Jogo", state);
    }

    @Test
    public void Test_InfiniteLoop() throws Exception {
        int playerA;
        int playerB;
        boolean moveOk;
        String msg;
        String state;

        // Without counter
        loadScenario("AbyssEffect_InfiniteLoop");

        playerA = core.getCurrentPlayerId();
        playerB = 2;

        assertEquals(5, getPosition(playerA));
        assertEquals(5, getPosition(playerB));

        moveOk = core.moveCurrentPlayer(1);
        assertTrue(moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull(msg);

        state = getState(playerA);
        assertEquals("Preso", state);

        state = getState(playerB);
        assertEquals("Em Jogo", state);


        // With counter
        loadScenario("AbyssCounter_InfiniteLoop");

        playerA = core.getCurrentPlayerId();
        playerB = 2;

        assertEquals(5, getPosition(playerA));
        assertEquals(5, getPosition(playerB));

        moveOk = core.moveCurrentPlayer(1);
        assertTrue(moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull(msg);

        state = getState(playerA);
        assertEquals("Em Jogo", state);

        state = getState(playerB);
        assertEquals("Em Jogo", state);

        // Player frees the others
        loadScenario("AbyssEffect_InfiniteLoop_ThirdPlayer");

        int p1 = 1;
        int p2 = 2;
        int p3 = core.getCurrentPlayerId();

        assertEquals(6, getPosition(p1));
        assertEquals(6, getPosition(p2));
        assertEquals(5, getPosition(p3));

        assertEquals("Preso", getState(p1));
        assertEquals("Preso", getState(p2));
        assertEquals("Em Jogo", getState(p3));

        moveOk = core.moveCurrentPlayer(1);
        assertTrue(moveOk);

        msg = core.reactToAbyssOrTool();
        assertNotNull(msg);

        assertEquals("Preso", getState(p3));

        assertEquals("Em Jogo", getState(p1));
        assertEquals("Em Jogo", getState(p2));

        assertEquals(6, getPosition(p1));
        assertEquals(6, getPosition(p2));
        assertEquals(6, getPosition(p3));
    }

    @Test
    public void Test_AbyssToString_All() {

        Abyss a0 = AbyssSubType.createAbyss(0);
        assertEquals("0:0", a0.toString());

        Abyss a1 = AbyssSubType.createAbyss(1);
        assertEquals("0:1", a1.toString());

        Abyss a2 = AbyssSubType.createAbyss(2);
        assertEquals("0:2", a2.toString());

        Abyss a3 = AbyssSubType.createAbyss(3);
        assertEquals("0:3", a3.toString());

        Abyss a4 = AbyssSubType.createAbyss(4);
        assertEquals("0:4", a4.toString());

        Abyss a5 = AbyssSubType.createAbyss(5);
        assertEquals("0:5", a5.toString());

        Abyss a6 = AbyssSubType.createAbyss(6);
        assertEquals("0:6", a6.toString());

        Abyss a7 = AbyssSubType.createAbyss(7);
        assertEquals("0:7", a7.toString());

        Abyss a8 = AbyssSubType.createAbyss(8);
        assertEquals("0:8", a8.toString());

        Abyss a9 = AbyssSubType.createAbyss(9);
        assertEquals("0:9", a9.toString());
    }

    @Test
    public void Test_ToolToString_All() {

        Tool t0 = ToolSubType.createTool(0);
        assertEquals("1:0", t0.toString());

        Tool t1 = ToolSubType.createTool(1);
        assertEquals("1:1", t1.toString());

        Tool t2 = ToolSubType.createTool(2);
        assertEquals("1:2", t2.toString());

        Tool t3 = ToolSubType.createTool(3);
        assertEquals("1:3", t3.toString());

        Tool t4 = ToolSubType.createTool(4);
        assertEquals("1:4", t4.toString());

        Tool t5 = ToolSubType.createTool(5);
        assertEquals("1:5", t5.toString());
    }

    @Test
    public void Test_ToolInteract_AddsToolAndReturnsMessage() {
        for (ToolSubType subType : ToolSubType.values()) {

            ArrayList<String> langs = new ArrayList<>();
            langs.add("Java");
            Player player = new Player(1, "Tester", langs, PlayerColor.BLUE);

            assertTrue("Player should start with no tools", player.getTools().isEmpty());

            Tool tool = ToolSubType.createTool(subType);
            assertNotNull("Tool should be created for subtype " + subType, tool);

            String msg = tool.interact(player, null, null);

            assertEquals(
                    "Interact message should match expected format",
                    "Jogador agarrou " + subType.getName(),
                    msg
            );

            assertTrue(
                    "Player should now have the tool " + subType,
                    player.hasTool(tool)
            );

            assertEquals(
                    "Player tools list size should be 1 after picking one tool",
                    1,
                    player.getTools().size()
            );
        }
    }

    @Test
    public void Test_Tool_PNGs() {
        for (ToolSubType t : ToolSubType.values()) {
            Tool tool = ToolSubType.createTool(t);
            assertNotNull("Tool should not be null for subtype " + t, tool);

            String expected = t.getImage();
            String actual = tool.getPng();

            assertEquals(
                    "PNG mismatch for tool " + t.name() + " (id=" + t.getId() + ")",
                    expected,
                    actual
            );
        }
    }

    @Test
    public void Test_Abyss_PNGs() {
        for (AbyssSubType a : AbyssSubType.values()) {
            Abyss abyss = AbyssSubType.createAbyss(a);
            assertNotNull("Abyss should not be null for subtype " + a, abyss);

            String expected = a.getImage();
            String actual = abyss.getPng();

            assertEquals(
                    "PNG mismatch for abyss " + a.name() + " (id=" + a.getId() + ")",
                    expected,
                    actual
            );
        }
    }

    @Test
    public void test_createMapObject_createsTool() {
        // typeID = 1 → TOOL
        // subTypeID = 0 → INHERITANCE (ToolSubType)
        MapObject obj = MapObjectType.createMapObject(1, 0);

        assertNotNull("createMapObject(1, 0) should not return null", obj);
        assertTrue("Returned object should be instance of Tool", obj instanceof Tool);

        // id do subtipo é 0, logo o Tool criado deve ter id 0
        assertEquals("Tool id should match subtype id (0)", 0, obj.getId());
    }

    @Test
    public void test_MapObjectType_fromID_invalid() {
        MapObjectType type = MapObjectType.fromID(100);
        assertNull("fromID(100) should return null for MapObjectType", type);
    }

    @Test
    public void test_ToolSubType_fromId_invalid() {
        ToolSubType tool = ToolSubType.fromId(100);
        assertNull("fromId(100) should return null for ToolSubType", tool);
    }

    @Test
    public void test_AbyssSubType_fromId_invalid() {
        AbyssSubType abyss = AbyssSubType.fromId(100);
        assertNull("fromId(100) should return null for AbyssSubType", abyss);
    }


}