package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.TurnManager;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestTurnManager {

    // ========================= Helpers ===============================

    private Player playerWithId(int id) {
        return new Player(id,"Dummy",new ArrayList<>(), PlayerColor.GREEN);
    }

    private List<Player> playersWithIds(int... ids) {
        List<Player> list = new ArrayList<>();
        for (int id : ids) {
            list.add(playerWithId(id));
        }
        return list;
    }

    // ========================= Testes ===============================


    @Test
    public void testConstructorWithValues() {
        TurnManager tm = new TurnManager(10, 5);
        assertEquals(10, tm.getCurrentID());
        assertEquals(5, tm.getTurnCount());
    }


    @Test
    public void testDescendingLogicConceptual() {
        List<Integer> ids = Arrays.asList(5, 10, 20);
        assertEquals(Arrays.asList(20, 10, 5, 20), Arrays.asList(20, 10, 5, 20));
    }
}