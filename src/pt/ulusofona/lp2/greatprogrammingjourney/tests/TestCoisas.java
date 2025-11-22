package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestCoisas {

    @Test
    public void testEqualIdsWithManualBoard() {
        Board board = new Board(10);

        // Dois players com o mesmo ID, como acontece num save file
        Player a = new Player(4, "A", new ArrayList<>(), PlayerColor.BLUE);
        Player b = new Player(4, "B", new ArrayList<>(), PlayerColor.RED);

        // Ambos começam na posição 1
        board.placePlayer(a, 1);
        board.placePlayer(b, 1);   // Isto simula o loadGame (NÃO usa parser)

        // O board pensa que são o MESMO jogador
        assertEquals(1, board.getPlayerPosition(a));
        assertEquals(1, board.getPlayerPosition(b));

        // Move A três casas
        board.movePlayerBySteps(a, 3);

        // BUG: como equals() compara só ID,
        // removePlayer(a) remove B ou vice-versa
        // e o outro fica preso num slot inválido
        int posA = board.getPlayerPosition(a);
        int posB = board.getPlayerPosition(b);

        // Mostra o bug
        assertNotEquals(
                posA, posB
        );
    }

}
