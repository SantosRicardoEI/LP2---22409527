package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestMoveHistory {

    private Player makePlayer(int id) {
        return new Player(id, "P" + id, new ArrayList<>(), PlayerColor.BLUE);
    }

    @Test
    public void testAddRecordAndBasicGetters() {
        MoveHistory history = new MoveHistory();
        Player p1 = makePlayer(1);
        Player p2 = makePlayer(2);

        history.addRecord(1, 1, 3, 2);
        history.addRecord(2, 2, 4, 3);
        history.addRecord(1, 3, 6, 4);

        assertEquals(4, history.getRoll(p1, 0));
        assertEquals(2, history.getRoll(p1, 1));

        assertEquals(3, history.getPosition(p1, 0));
        assertEquals(1, history.getPosition(p1, 1));

        assertEquals(3, history.getRoll(p2, 0));
        assertEquals(2, history.getPosition(p2, 0)); 
    }

    @Test
    public void testGetRollWhenNotEnoughHistoryReturnsZero() {
        MoveHistory history = new MoveHistory();
        Player p1 = makePlayer(1);

        history.addRecord(1, 1, 3, 2);

        assertEquals(0, history.getRoll(p1, 5));
    }

    @Test
    public void testGetPositionWhenNotEnoughHistoryReturnsOne() {
        MoveHistory history = new MoveHistory();
        Player p1 = makePlayer(1);

        history.addRecord(1, 5, 7, 3);

        assertEquals(1, history.getPosition(p1, 10));
    }

    @Test
    public void testGetMoveInfoAndAllMovesInfo() {
        MoveHistory history = new MoveHistory();

        history.addRecord(1, 1, 3, 2);
        history.addRecord(2, 2, 4, 3);

        String[] move1 = history.getMoveInfo(1);
        assertArrayEquals(new String[]{"1", "1", "3", "2"}, move1);

        String[] move2 = history.getMoveInfo(2);
        assertArrayEquals(new String[]{"2", "2", "4", "3"}, move2);

        String[][] all = history.getAllMovesInfo();
        assertEquals(2, all.length);
        assertArrayEquals(move1, all[0]);
        assertArrayEquals(move2, all[1]);
    }

    @Test
    public void testGetMoveInfoInvalidTurnThrows() {
        MoveHistory history = new MoveHistory();
        history.addRecord(1, 1, 3, 2);

        assertThrows(IllegalArgumentException.class,
                () -> history.getMoveInfo(5));
    }

    @Test
    public void testResetAndToString() {
        MoveHistory history = new MoveHistory();

        history.addRecord(1, 1, 3, 2);
        history.addRecord(2, 2, 4, 3);

        String before = history.toString();
        assertTrue(before.contains("P1"));
        assertTrue(before.contains("P2"));

        history.reset();

        String[][] allAfter = history.getAllMovesInfo();
        assertEquals(0, allAfter.length);

        String after = history.toString();
        assertEquals("[]", after);
    }
}