package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
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

    // ============================== Public API =======================================================================

    public boolean initializePlayers(String[][] players) {
        return BoardInitializer.initializePlayers(this, players);
    }

    public int getSize() {
        return slots.length - BOARD_OFFSET; // 1-based
    }

    public String[] getSlotInfo(int position) {
        if (!validateSlot(position, getSize())) {
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

    public List<Interactable> getInteractables() {
        List<Interactable> all = new ArrayList<>();

        for (Slot slot : slots) {
            if (slot != null) {
                if (slot.getInteractable() != null) {
                    all.add(slot.getInteractable());
                }
            }
        }
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

    public int getInteractablePosition(Interactable interactable) {
        if (interactable == null) {
            return -1;
        }

        for (Slot slot : slots) {
            if (slot != null && slot.getInteractable() == interactable) {
                return slot.getNumber();
            }
        }
        return -1;
    }


    public Player getWinner() {
        return slots[getSize()].getPlayer(0);
    }

    public int movePlayerBySteps(Player player, int steps) {

        if (player == null || getPlayerPosition(player) < 1 ) {
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

    // ============================== Helper Methods ===================================================================

    private void createSlots(int size) {
        slots = new Slot[size + BOARD_OFFSET];
        for (int i = BOARD_OFFSET; i <= size; i++) {
            slots[i] = new Slot(i);
        }
        LOG.info("createSlots: created " + size + " slots");
    }

    public boolean placePlayer(Player player) {
        return placePlayer(player, INITIAL_POSITION);
    }

    public boolean placePlayer(Player player, int position) {
        if (player == null) {
            LOG.error("placePlayer: " + "player is null");
            return false;
        }

        if (!validateSlot(position, getSize())) {
            LOG.error("placePlayer: " + "invalid slot");
            return false;
        }

        return slots[position].addPlayer(player);
    }

    private void removePlayer(Player player, int position) {
        if (player == null){
            LOG.error("removePlayer: " + "player is null");
            return;
        }

        if (!validateSlot(position, getSize())) {
            LOG.error("removePlayer: " + "invalid slot");
            return;
        }

        slots[position].removePlayer(player);
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

    public boolean placeInteractable(Interactable interactable, int pos) {
        if (!validateSlot(pos, getSize())) {
            LOG.error("placeInteractable: " + "invalid slot");
            return false;
        }

        slots[pos].setInteractable(interactable);
        return true;
    }


    public Interactable getIntercatableOfSlot(int slotNumber) {
        return slots[slotNumber].getInteractable();
    }

    public static boolean validateSlot(int pos, int max) {
        if (pos < 1 || pos > max) {
            return false;
        }
        return true;
    }
}