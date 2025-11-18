package pt.ulusofona.lp2.greatprogrammingjourney.model.board;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.InputValidator;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

import java.util.ArrayList;
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
        return PlayerInitializer.initializePlayers(this, players);
    }

    public int getSize() {
        return slots.length - BOARD_OFFSET; // 1-based
    }

    public String[] getSlotInfo(int position) {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(this);
        if (!boardOk.isValid()) {
            LOG.error("getSlotInfo: " + boardOk.getMessage());
            return null;
        }

        ValidationResult slotOk = InputValidator.validateSlot(position, getSize());
        if (!slotOk.isValid()) {
            LOG.error("getSlotInfo: " + slotOk.getMessage());
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
        ValidationResult playerOk = InputValidator.validatePlayerNotNull(player);
        if (!playerOk.isValid()) {
            return -1;
        }

        for (Slot slot : slots) {
            if (slot != null && slot.hasPlayer(player.getId())) {
                return slot.getNumber();
            }
        }
        return -1;
    }


    public Player getWinner() {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(this);
        if (!boardOk.isValid()) {
            LOG.error("getWinner: " + boardOk.getMessage());
            return null;
        }

        return slots[getSize()].getPlayer(0);
    }

    public int movePlayerBySteps(Player player, int steps) {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(this);
        if (!boardOk.isValid()) {
            LOG.error("movePlayer: " + boardOk.getMessage());
            return -1;
        }

        ValidationResult playerOk = InputValidator.validatePlayerOnBoard(this, player);
        if (!playerOk.isValid()) {
            LOG.error("movePlayer: " + playerOk.getMessage());
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

    private boolean placePlayer(Player player, int position) {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(this);
        if (!boardOk.isValid()) {
            LOG.error("placePlayer: " + boardOk.getMessage());
            return false;
        }

        ValidationResult playerOk = InputValidator.validatePlayerNotNull(player);
        if (!playerOk.isValid()) {
            LOG.error("placePlayer: " + playerOk.getMessage());
            return false;
        }

        ValidationResult slotOk = InputValidator.validateSlot(position, getSize());
        if (!slotOk.isValid()) {
            LOG.error("placePlayer: " + slotOk.getMessage());
            return false;
        }

        return slots[position].addPlayer(player);
    }

    private void removePlayer(Player player, int position) {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(this);
        if (!boardOk.isValid()) {
            LOG.error("removePlayer: " + boardOk.getMessage());
            return;
        }

        ValidationResult playerOk = InputValidator.validatePlayerNotNull(player);
        if (!playerOk.isValid()) {
            LOG.error("removePlayer: " + playerOk.getMessage());
            return;
        }

        ValidationResult slotOk = InputValidator.validateSlot(position, getSize());
        if (!slotOk.isValid()) {
            LOG.error("removePlayer: " + slotOk.getMessage());
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
        ValidationResult slotOk = InputValidator.validateSlot(pos, getSize());
        if (!slotOk.isValid()) {
            LOG.error("placeInteractable: " + slotOk.getMessage());
            return false;
        }

        slots[pos].setInteractable(interactable);
        return true;
    }


    public Interactable getIntercatableOfSlot(int slotNumber) {
        return slots[slotNumber].getInteractable();
    }
}