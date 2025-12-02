package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.parser.Parser;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig.*;

public class Board {

    // ============================== State ============================================================================

    private Slot[] slots;
    private static final GameLogger LOG = new GameLogger(Board.class);

    // ============================== Constructor ======================================================================

    public Board(int amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Board size must be >= 1");
        }
        createSlots(amount);
    }

    // ======================================== Public Methods =========================================================

    public int movePlayerBySteps(Player player, int steps) {

        if (player == null || getPlayerPosition(player) < 1) {
            LOG.error("movePlayer: " + "invalid player or player position");
            return -1;
        }

        int oldPos = getPlayerPosition(player);
        int newPos = calculateNewPosition(oldPos, steps);

        removePlayer(player, oldPos);
        if (!placePlayer(player, newPos)) {
            placePlayer(player, oldPos);
            LOG.error("movePlayer: failed to place at " + newPos + ", rolled back to " + oldPos);
            return oldPos;
        }
        if (oldPos + steps > getSize()) {
            LOG.info("movePlayer: bounce to " + newPos);
        }
        return newPos;
    }

    public void movePlayerTo(Player p, int newPos) {
        int current = getPlayerPosition(p);
        int delta = newPos - current;
        movePlayerBySteps(p, delta);
    }

    public boolean placePlayer(Player player, int position) {
        if (player == null) {
            LOG.error("placePlayer: " + "player is null");
            return false;
        }

        if (position < 1 || position > getSize()) {
            LOG.error("placePlayer: " + "invalid slot");
            return false;
        }

        return slots[position].addPlayer(player);
    }

    public boolean placeMapObject(MapObject mapObject, int pos) {
        if (pos < 1 || pos > getSize()) {
            LOG.error("placeMapObject: " + "invalid slot");
            return false;
        }

        slots[pos].setMapObject(mapObject);
        return true;
    }

    public boolean initialize(String[][] players, String[][] mapObjectInfo) {
        return initializePlayers(players) &&
                initializeMapObjects(mapObjectInfo);
    }

    // ======================================== Getters ================================================================

    public int getSize() {
        return slots.length - BOARD_OFFSET; // 1-based
    }

    public String[] getSlotInfo(int position) {
        if (position < 1 || position > getSize()) {
            LOG.error("getSlotInfo: " + "invalid slot");
            return null;
        }

        return slots[position].getInfo();
    }

    public List<Player> getPlayers() {
        List<Player> all = new ArrayList<>();

        for (Slot slot : slots) {
            if (slot != null) {
                all.addAll(slot.getPlayers());
            }
        }

        Collections.sort(all);

        return all;
    }

    public Player getPlayer(int id) {
        for (Player player : getPlayers()) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }

    public List<Player> getPlayersAt(int slot) {
        return slots[slot].getPlayers();
    }

    public List<MapObject> getMapObjects() {
        List<MapObject> all = new ArrayList<>();

        for (Slot slot : slots) {
            if (slot != null) {
                if (slot.getMapObject() != null) {
                    all.add(slot.getMapObject());
                }
            }
        }
        return all;
    }

    public MapObject getMapObjectsAt(int slotNumber) {
        if (slotNumber < 1 || slotNumber > getSize()) {
            LOG.error("getMapObjectAt: invalid slot");
            return null;
        }
        return slots[slotNumber].getMapObject();
    }

    public int getPlayerPosition(Player player) {
        for (Slot slot : slots) {
            if (slot != null) {
                if (slot.hasPlayer(player)) {
                    return slot.getNumber();
                }
            }
        }
        return -1;
    }

    public int getMapObjectPosition(MapObject mapObject) {
        if (mapObject == null) {
            return -1;
        }

        for (Slot slot : slots) {
            if (slot != null && slot.getMapObject() == mapObject) {
                return slot.getNumber();
            }
        }
        return -1;
    }


    public Player getWinner() {
        return slots[getSize()].getPlayer(0);
    }


    // ======================================== Helper Methods =========================================================

    private void createSlots(int size) {
        slots = new Slot[size + BOARD_OFFSET];
        for (int i = BOARD_OFFSET; i <= size; i++) {
            slots[i] = new Slot(i);
        }
        LOG.info("createSlots: created " + size + " slots");
    }

    private void removePlayer(Player player, int position) {
        if (player != null && !(position < 1 || position > getSize())) {
            slots[position].removePlayer(player);
            LOG.error("removePlayer: " + "player is null or slot is invalid");
        }
    }

    private int calculateNewPosition(int oldPos, int steps) {
        int size = getSize();
        int target = oldPos + steps;

        if (target > size) {
            if (ENABLE_BOUNCE) {
                target = size - (target - size);
            } else {
                target = size;
            }
        }

        return Math.max(1, target);
    }

    private boolean initializePlayers(String[][] playerInfo) {
        if (playerInfo == null || playerInfo.length == 0) {
            LOG.error("initializePlayers: " + "invalid player info");
            return false;
        }

        LOG.info("initializePlayers: starting board population with " + playerInfo.length + " players");

        for (String[] info : playerInfo) {
            Player p;
            try {
                p = Parser.parsePlayer(info, getPlayers());
            } catch (IllegalArgumentException e) {
                LOG.warn("initializePlayers: rejected — invalid player for input=" + String.join(",", info));
                return false;
            }

            if (!placePlayer(p, GameConfig.INITIAL_POSITION)) {
                LOG.error("initializePlayers: could not place player=" + p.getName() + " on board");
                return false;
            }
        }

        LOG.info("initializePlayers: all players placed successfully");
        return true;
    }

    private boolean initializeMapObjects(String[][] mapObjects) {

        if (mapObjects == null || mapObjects.length == 0) {
            LOG.info("initializeMapObjects: no objects to place");
            return true;
        }

        LOG.info("initializeMapObjects: starting board population with " + mapObjects.length + " objects");

        for (String[] line : mapObjects) {

            if (line == null || line.length < 3) {
                LOG.warn("initializeMapObjects: rejected — invalid line (expected 3 fields) ");
                return false;
            }

            MapObject mapObject;
            int pos;

            try {
                mapObject = Parser.parseMapObject(new String[]{line[0], line[1]});

                pos = Parser.parsePosition(line[2]);
            } catch (IllegalArgumentException e) {
                LOG.warn("initializeMapObjects: rejected — invalid object data for input=" +
                        String.join(",", line));
                return false;
            }

            if (!placeMapObject(mapObject, pos)) {
                LOG.error("initializeMapObjects: could not place object=" +
                        mapObject.getName() + " at pos=" + pos);
                return false;
            }
        }

        LOG.info("initializeMapObjects: all objects placed successfully");
        return true;
    }
}