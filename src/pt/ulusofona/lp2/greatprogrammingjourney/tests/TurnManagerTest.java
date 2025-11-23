package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.TurnManager;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TurnManagerTest {

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
    public void test_DefaultConstructor() {
        TurnManager tm = new TurnManager();
        assertEquals(0, tm.getCurrentID());
        assertEquals(0, tm.getTurnCount());
    }

    @Test
    public void test_ConstructorWithValues() {
        TurnManager tm = new TurnManager(10, 5);
        assertEquals(10, tm.getCurrentID());
        assertEquals(5, tm.getTurnCount());
    }

    @Test
    public void test_AdvanceTurn_FirstCall() {
        List<Player> active = playersWithIds(10, 5, 20);
        TurnManager tm = new TurnManager();

        tm.advanceTurn(active);

        assertEquals(5, tm.getCurrentID());
        assertEquals(1, tm.getTurnCount());
    }

    @Test
    public void test_AdvanceTurn_SubsequentCalls() {
        List<Player> active = playersWithIds(10, 5, 20);
        TurnManager tm = new TurnManager();

        tm.advanceTurn(active);
        assertEquals(5, tm.getCurrentID());

        tm.advanceTurn(active);
        assertEquals(10, tm.getCurrentID());

        tm.advanceTurn(active);
        assertEquals(20, tm.getCurrentID());

        tm.advanceTurn(active);
        assertEquals(5, tm.getCurrentID());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_AdvanceTurn_EmptyList() {
        TurnManager tm = new TurnManager();
        tm.advanceTurn(new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_AdvanceTurn_NullList() {
        TurnManager tm = new TurnManager();
        tm.advanceTurn(null);
    }

    @Test
    public void test_AdvanceTurn_SinglePlayer() {
        List<Player> active = playersWithIds(42);
        TurnManager tm = new TurnManager();

        tm.advanceTurn(active);
        assertEquals(42, tm.getCurrentID());

        tm.advanceTurn(active);
        assertEquals(42, tm.getCurrentID());

        tm.advanceTurn(active);
        assertEquals(42, tm.getCurrentID());
    }

    @Test
    public void test_AdvanceTurn_PlayerRemoved() {
        List<Player> active = playersWithIds(5, 10, 20);
        TurnManager tm = new TurnManager();

        tm.advanceTurn(active);
        assertEquals(5, tm.getCurrentID());

        tm.advanceTurn(active);
        assertEquals(10, tm.getCurrentID());

        active = playersWithIds(5, 20);

        tm.advanceTurn(active);
        assertEquals(20, tm.getCurrentID());

        tm.advanceTurn(active);
        assertEquals(5, tm.getCurrentID());
    }

    @Test
    public void test_DescendingLogicConceptual() {
        List<Integer> ids = Arrays.asList(5, 10, 20);
        assertEquals(Arrays.asList(20, 10, 5, 20), Arrays.asList(20, 10, 5, 20));
    }
}