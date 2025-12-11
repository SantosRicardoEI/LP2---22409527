package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoardAndSlots {


    // --------------------- Helpers ---------------------

    private Player makePlayer(int id, PlayerColor color) {
        ArrayList<String> langs = new ArrayList<>();
        langs.add("Java");
        return new Player(id, "P" + id, langs, color);
    }

    // --------------------- Board constructor ---------------------

    @Test
    public void testBoardConstructorValidAndInvalid() {
        Board b = new Board(5);
        assertEquals(5, b.getSize());

        assertThrows(IllegalArgumentException.class,
                () -> new Board(0),
                "Board size < 1 deve lançar IllegalArgumentException");
    }

    // --------------------- placePlayer ---------------------

    @Test
    public void testPlacePlayerNullAndInvalidSlotAndSuccess() {
        Board b = new Board(5);
        Player p = makePlayer(1, PlayerColor.BLUE);

        // player null
        assertFalse(b.placePlayer(null, 1));

        // slot inválido
        assertFalse(b.placePlayer(p, 0));
        assertFalse(b.placePlayer(p, 6));

        // sucesso
        assertTrue(b.placePlayer(p, 1));
        assertEquals(1, b.getPlayerPosition(p));
    }

    @Test
    public void testPlacePlayerSlotFullAndDuplicate() {
        Board b = new Board(3);
        int slot = 1;

        for (int i = 0; i < GameConfig.SLOT_SIZE; i++) {
            Player p = makePlayer(10 + i, PlayerColor.values()[i % PlayerColor.values().length]);
            assertTrue(b.placePlayer(p, slot), "Até SLOT_SIZE deve conseguir adicionar");
        }

        Player extra = makePlayer(99, PlayerColor.values()[0]);
        boolean added = b.placePlayer(extra, slot);
        assertFalse(added, "Quando o slot está cheio, placePlayer deve falhar");

        Board b2 = new Board(3);
        Player pDup = makePlayer(50, PlayerColor.BLUE);
        assertTrue(b2.placePlayer(pDup, 2));
        assertFalse(b2.placePlayer(pDup, 2), "Adicionar o mesmo player duas vezes na mesma casa deve falhar");
    }

    // --------------------- placeMapObject ---------------------

    @Test
    public void testPlaceMapObjectValidAndInvalid() {
        Board b = new Board(5);

        assertFalse(b.placeMapObject(null, 0));
        assertFalse(b.placeMapObject(null, 6));
        assertTrue(b.placeMapObject(null, 3));
        assertNull(b.getMapObjectsAt(3));

        String[][] playersInfo = {
                {"1", "Alice", "Java", "BLUE"}
        };
        String[][] objectsInfo = {
                {"0", "0", "5"}
        };

        Board b2 = new Board(5);
        assertTrue(b2.initialize(playersInfo, objectsInfo));
        MapObject obj = b2.getMapObjectsAt(5);
        assertNotNull(obj);
        assertEquals(5, b2.getMapObjectPosition(obj));
    }

    // --------------------- movePlayerBySteps & movePlayerTo ---------------------

    @Test
    public void testMovePlayerByStepsInvalidPlayerOrPosition() {
        Board b = new Board(5);
        Player p = makePlayer(1, PlayerColor.BLUE);

        int res = b.movePlayer(p, 2);
        assertEquals(-1, res);
    }

    @Test
    public void testMovePlayerByStepsNormalMove() {
        Board b = new Board(10);
        Player p = makePlayer(1, PlayerColor.BLUE);
        assertTrue(b.placePlayer(p, 1));

        int newPos = b.movePlayer(p, 3);
        assertEquals(4, newPos);
        assertEquals(4, b.getPlayerPosition(p));
    }

    @Test
    public void testMovePlayerBounceEnabledAndDisabled() {
        Board b = new Board(5);
        Player p = makePlayer(1, PlayerColor.BLUE);
        assertTrue(b.placePlayer(p, 4));

        int pos = b.movePlayer(p, 3);
        assertEquals(3, pos);
        assertEquals(3, b.getPlayerPosition(p));

        Board b2 = new Board(5);
        Player p2 = makePlayer(2, PlayerColor.RED);
        assertTrue(b2.placePlayer(p2, 4));

    }

    @Test
    public void testMovePlayerClampTo1() {
        Board b = new Board(10);
        Player p = makePlayer(1, PlayerColor.BLUE);
        assertTrue(b.placePlayer(p, 3));

        int pos = b.movePlayer(p, -10);
        assertEquals(1, pos);
        assertEquals(1, b.getPlayerPosition(p));
    }

    @Test
    public void testMovePlayerRollbackWhenPlaceFails() {
        Board b = new Board(5);

        int fullSlot = 3;
        for (int i = 0; i < GameConfig.SLOT_SIZE; i++) {
            Player extra = makePlayer(100 + i, PlayerColor.values()[i % PlayerColor.values().length]);
            assertTrue(b.placePlayer(extra, fullSlot));
        }

        Player p = makePlayer(1, PlayerColor.BLUE);
        assertTrue(b.placePlayer(p, 1));

        int newPos = b.movePlayer(p, 2);
        assertEquals(1, newPos);
        assertEquals(1, b.getPlayerPosition(p));
    }

    @Test
    public void testMovePlayerToUsesChangeBySteps() {
        Board b = new Board(10);
        Player p = makePlayer(1, PlayerColor.BLUE);
        assertTrue(b.placePlayer(p, 2));

        b.changePlayerPosition(p, 7);
        assertEquals(7, b.getPlayerPosition(p));
    }

    // --------------------- getSize, getSlotInfo ---------------------

    @Test
    public void testGetSizeAndSlotInfo() {
        Board b = new Board(5);
        assertEquals(5, b.getSize());

        assertNull(b.getSlotInfo(0));
        assertNull(b.getSlotInfo(6));

        String[] info = b.getSlotInfo(1);
        assertNotNull(info);
        assertEquals(3, info.length);
        assertEquals("", info[0]);
        assertEquals("", info[1]);
        assertEquals("", info[2]);

        Player p = makePlayer(1, PlayerColor.BLUE);
        assertTrue(b.placePlayer(p, 2));
        String[][] playersInfo = {
                {"10", "Alice", "Java", "RED"}
        };
        String[][] objectsInfo = {
                {"0", "0", "3"}
        };
        Board b2 = new Board(5);
        assertTrue(b2.initialize(playersInfo, objectsInfo));

        String[] info2 = b2.getSlotInfo(3);
        assertNotNull(info2);
        assertTrue(info2[2].startsWith("A") || info2[2].startsWith("T"));
    }

    // --------------------- getPlayers, getPlayer, getPlayersAt ---------------------

    @Test
    public void testGetPlayersAndGetPlayerAndGetPlayersAt() {
        Board b = new Board(5);
        Player p1 = makePlayer(1, PlayerColor.BLUE);
        Player p2 = makePlayer(2, PlayerColor.RED);

        assertTrue(b.placePlayer(p1, 1));
        assertTrue(b.placePlayer(p2, 2));

        List<Player> all = b.getPlayers();
        assertEquals(2, all.size());

        Player found1 = b.getPlayer(1);
        Player found2 = b.getPlayer(2);
        Player notFound = b.getPlayer(999);

        assertEquals(p1, found1);
        assertEquals(p2, found2);
        assertNull(notFound);

        List<Player> at1 = b.getPlayersAt(1);
        assertEquals(1, at1.size());
        assertEquals(p1, at1.get(0));
    }

    // --------------------- getMapObjects & getMapObjectsAt ---------------------

    @Test
    public void testGetMapObjectsAndGetMapObjectsAtInvalidAndValid() {
        Board b = new Board(5);

        assertTrue(b.getMapObjects().isEmpty());
        assertNull(b.getMapObjectsAt(0));
        assertNull(b.getMapObjectsAt(6));

        String[][] playersInfo = {
                {"1", "Alice", "Java", "BLUE"}
        };
        String[][] objectsInfo = {
                {"0", "0", "2"},
                {"1", "0", "4"}
        };

        assertTrue(b.initialize(playersInfo, objectsInfo));

        List<MapObject> list = b.getMapObjects();
        assertEquals(2, list.size());

        assertNotNull(b.getMapObjectsAt(2));
        assertNotNull(b.getMapObjectsAt(4));
    }

    // --------------------- getPlayerPosition & getMapObjectPosition ---------------------

    @Test
    public void testGetPlayerPositionAndMapObjectPosition() {
        Board b = new Board(5);
        Player p = makePlayer(1, PlayerColor.BLUE);
        assertEquals(-1, b.getPlayerPosition(p));

        assertTrue(b.placePlayer(p, 3));
        assertEquals(3, b.getPlayerPosition(p));

        assertEquals(-1, b.getMapObjectPosition(null));

        String[][] playersInfo = {
                {"2", "Bob", "Java", "RED"}
        };
        String[][] objectsInfo = {
                {"0", "0", "5"} // abyss na 5
        };
        Board b2 = new Board(5);
        assertTrue(b2.initialize(playersInfo, objectsInfo));

        MapObject obj = b2.getMapObjectsAt(5);
        assertNotNull(obj);
        assertEquals(5, b2.getMapObjectPosition(obj));
    }

    // --------------------- getWinner ---------------------

    @Test
    public void testGetWinner() {
        Board b = new Board(5);
        Player p1 = makePlayer(1, PlayerColor.BLUE);
        Player p2 = makePlayer(2, PlayerColor.RED);

        assertTrue(b.placePlayer(p1, 1));
        assertTrue(b.placePlayer(p2, 1));

        b.changePlayerPosition(p1, 5);

        Player winner = b.getWinner();
        assertNotNull(winner);
        assertEquals(p1, winner);
    }

    // --------------------- initialize (players) ---------------------

    @Test
    public void testInitializePlayersInvalidAndValid() {
        Board b = new Board(5);
        assertFalse(b.initialize(null, null));
        String[][] badPlayers = {
                {"-1", "X", "Java"}
        };
        assertFalse(b.initialize(badPlayers, null));
        String[][] okPlayers = {
                {"1", "Alice", "Java", "BLUE"},
                {"2", "Bob", "Java", "RED"}
        };
        Board b2 = new Board(5);
        assertTrue(b2.initialize(okPlayers, null));
        assertEquals(2, b2.getPlayers().size());
    }

    // --------------------- initialize (mapObjects) ---------------------

    @Test
    public void testInitializeMapObjectsVariousCases() {
        String[][] players = {
                {"1", "Alice", "Java", "BLUE"}
        };

        Board b1 = new Board(5);
        assertTrue(b1.initialize(players, null));

        Board b2 = new Board(5);
        String[][] mapNullLine = {
                null
        };
        assertFalse(b2.initialize(players, mapNullLine));

        Board b3 = new Board(5);
        String[][] mapTooShort = {
                {"0", "0"}
        };
        assertFalse(b3.initialize(players, mapTooShort));

        Board b4 = new Board(5);
        String[][] mapBadSubtype = {
                {"0", "999", "3"}
        };
        assertFalse(b4.initialize(players, mapBadSubtype));

        Board b5 = new Board(5);
        String[][] mapPosTooBig = {
                {"0", "0", "999"}
        };
        assertFalse(b5.initialize(players, mapPosTooBig));

        Board b6 = new Board(5);
        String[][] mapOk = {
                {"0", "0", "2"},
                {"1", "0", "4"}
        };
        assertTrue(b6.initialize(players, mapOk));
        assertNotNull(b6.getMapObjectsAt(2));
        assertNotNull(b6.getMapObjectsAt(4));
    }

    @Test
    public void testGetMapObjectPositionNotFoundButNonNull() {
        Board b1 = new Board(5);
        String[][] players1 = {
                {"1", "Alice", "Java", "BLUE"}
        };
        String[][] objects1 = {
                {"0", "0", "3"}
        };
        assertTrue(b1.initialize(players1, objects1));
        MapObject obj = b1.getMapObjectsAt(3);
        assertNotNull(obj);

        Board b2 = new Board(5);
        String[][] players2 = {
                {"2", "Bob", "Java", "RED"}
        };
        assertTrue(b2.initialize(players2, null));

        int pos = b2.getMapObjectPosition(obj);
        assertEquals(-1, pos);
    }

    @Test
    public void testInitializePlayersFailsWhenInitialSlotIsFull() {
        Board b = new Board(5);

        for (int i = 0; i < GameConfig.SLOT_SIZE; i++) {
            ArrayList<String> langs = new ArrayList<>();
            langs.add("Java");
            Player dummy = new Player(100 + i, "Dummy" + i, langs, PlayerColor.BLUE);
            assertTrue(b.placePlayer(dummy, GameConfig.INITIAL_POSITION));
        }

        String[][] newPlayers = {
                {"1", "Alice", "Java", "RED"} // id e cor novos
        };

        boolean ok = b.initialize(newPlayers, null);
        assertFalse(ok, "Com a casa inicial cheia, initialize deve falhar quando placePlayer falha");
    }

    @Test
    public void testGetInfoWithMultiplePlayers() {
        Board board = new Board(5);

        ArrayList<String> langs = new ArrayList<>();
        langs.add("Java");

        Player p1 = new Player(1, "Alice", langs, PlayerColor.BLUE);
        Player p2 = new Player(2, "Bob", langs, PlayerColor.RED);

        assertTrue(board.placePlayer(p1, 3));
        assertTrue(board.placePlayer(p2, 3));

        String[] info = board.getSlotInfo(3);

        assertEquals("1,2", info[0],
                "getInfo deve listar os IDs dos jogadores separados por vírgulas na ordem de inserção");

        assertEquals("", info[1]);
        assertEquals("", info[2]);
    }

}