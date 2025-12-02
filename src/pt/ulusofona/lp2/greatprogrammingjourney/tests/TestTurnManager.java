package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.TurnManager;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTurnManager {

    private Player makePlayer(int id) {
        ArrayList<String> langs = new ArrayList<>();
        langs.add("Java");
        return new Player(id, "P" + id, langs, PlayerColor.values()[0]);
    }

    @Test
    public void testConstructorWithPlayersInitializesCurrentIdAndTurnCount() {
        List<Player> players = new ArrayList<>();
        players.add(makePlayer(10));
        players.add(makePlayer(3));

        TurnManager tm = new TurnManager(players);

        assertEquals(1, tm.getTurnCount(), "Primeiro turno deve ser 1");
        int current = tm.getCurrentID();

        assertTrue(current == 10 || current == 3,
                "currentID deve ser o id de um jogador da lista");
    }

    @Test
    public void testConstructorWithExplicitValues() {
        TurnManager tm = new TurnManager(42, 7);

        assertEquals(42, tm.getCurrentID());
        assertEquals(7, tm.getTurnCount());
    }

    @Test
    public void testAdvanceTurnCyclesAndSkipsDefeatedPlayers() {
        Player p1 = makePlayer(1);
        Player p2 = makePlayer(2);
        Player p3 = makePlayer(3);

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        TurnManager tm = new TurnManager(players);

        int firstId = tm.getCurrentID();
        assertTrue(firstId == 1 || firstId == 2 || firstId == 3);

        tm.advanceTurn(players);
        int secondId = tm.getCurrentID();
        assertNotEquals(firstId, secondId, "Deve mudar de jogador no próximo turno");

        p2.defeat();

        tm.advanceTurn(players);
        int thirdId = tm.getCurrentID();
        assertNotEquals(2, thirdId, "Jogador derrotado (id=2) não deve ser escolhido");

        tm.advanceTurn(players);
        int fourthId = tm.getCurrentID();
        assertTrue(fourthId == 1 || fourthId == 3,
                "Depois de dar a volta só ids vivos devem aparecer");
    }

    @Test
    public void testConstructorWithEmptyPlayerListThrows() {
        List<Player> empty = new ArrayList<>();

        assertThrows(IllegalArgumentException.class,
                () -> new TurnManager(empty),
                "Lista vazia de jogadores não deve ser aceite");
    }

    @Test
    public void testAdvanceTurnWithNullListThrows() {
        List<Player> players = new ArrayList<>();
        players.add(makePlayer(1));

        TurnManager tm = new TurnManager(players);

        assertThrows(IllegalArgumentException.class,
                () -> tm.advanceTurn(null),
                "advanceTurn deve lançar exceção com lista null");
    }

    @Test
    public void testAdvanceTurnWithEmptyListThrows() {
        List<Player> players = new ArrayList<>();
        players.add(makePlayer(1));

        TurnManager tm = new TurnManager(players);

        List<Player> empty = new ArrayList<>();

        assertThrows(IllegalArgumentException.class,
                () -> tm.advanceTurn(empty),
                "advanceTurn deve lançar exceção com lista vazia");
    }

    @Test
    public void testAllPlayersDefeatedThrowsException() {
        Player p1 = makePlayer(5);
        Player p2 = makePlayer(10);

        p1.defeat();
        p2.defeat();

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        assertThrows(IllegalStateException.class, () -> new TurnManager(players));

        TurnManager tm = new TurnManager( -1, 1 );

        assertThrows(IllegalStateException.class,
                () -> tm.advanceTurn(players),
                "advanceTurn deve falhar quando não existem jogadores ativos");
    }

    @Test
    public void testAdvanceTurnWithPlayersListEmpty() {
        List<Player> initial = new ArrayList<>();
        initial.add(makePlayer(1));
        TurnManager tm = new TurnManager(initial);

        List<Player> empty = new ArrayList<>();

        assertThrows(IllegalArgumentException.class,
                () -> tm.advanceTurn(empty),
                "advanceTurn deve lançar exceção quando recebe lista vazia");
    }
}