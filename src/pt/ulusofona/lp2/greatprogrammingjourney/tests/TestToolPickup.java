package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestToolPickup {

    private Player createPlayer() {
        ArrayList<String> langs = new ArrayList<>();
        langs.add("Java");
        return new Player(1, "Dev", langs, PlayerColor.BLUE);
    }

    private void testPickupForTool(ToolSubType subType) {
        Player player = createPlayer();
        Tool tool = subType.getInstance();
        Board board = new Board(20);
        MoveHistory history = new MoveHistory();

        assertFalse(player.hasTool(tool));

        String msg = tool.interact(player, board, history);

        assertTrue(player.hasTool(tool));
        assertNotNull(msg);
        assertFalse(msg.isBlank());
    }

    @Test
    public void testPickupInheritance() {
        testPickupForTool(ToolSubType.INHERITANCE);
    }

    @Test
    public void testPickupFunctionalProgramming() {
        testPickupForTool(ToolSubType.FUNCTIONAL_PROGRAMMING);
    }

    @Test
    public void testPickupUnitTests() {
        testPickupForTool(ToolSubType.UNIT_TESTS);
    }

    @Test
    public void testPickupExceptionHandling() {
        testPickupForTool(ToolSubType.EXCEPTION_HANDLING);
    }

    @Test
    public void testPickupIDE() {
        testPickupForTool(ToolSubType.IDE);
    }

    @Test
    public void testPickupTeacherHelp() {
        testPickupForTool(ToolSubType.TEACHER_HELP);
    }

    @Test
    public void testPickupChatGpt() {
        testPickupForTool(ToolSubType.CHAT_GPT);
    }
}