package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestBugs {

    @Test
    public void testEqualIdsWithManualBoard() {
        Board board = new Board(10);
        Player a = new Player(4, "A", new ArrayList<>(), PlayerColor.BLUE);
        Player b = new Player(4, "B", new ArrayList<>(), PlayerColor.RED);

        board.placePlayer(a, 1);
        board.placePlayer(b, 1);

        assertEquals(1, board.getPlayerPosition(a));
        assertEquals(1, board.getPlayerPosition(b));

        board.movePlayerBySteps(a, 3);
        int posA = board.getPlayerPosition(a);
        int posB = board.getPlayerPosition(b);

        assertNotEquals(
                posA, posB
        );
    }

}
