package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.util.ArrayList;
import java.util.List;

final class Slot {

    // ============================== State ============================================================================
    private final int number;
    private final List<Player> players = new ArrayList<>(GameConfig.SLOT_SIZE);
    private Interactable interactable;

    private static final GameLogger LOG = new GameLogger(Slot.class);

    // ============================== Constructor ======================================================================

    Slot(int slotNumber) {
        this.number = slotNumber;
        this.interactable = null;
    }

    // ============================== Getters ==========================================================================

    int getNumber() {
        return number;
    }

    List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    Interactable getInteractable() {
        return this.interactable;
    }

    // ============================== Public API =======================================================================

    Player getPlayer(int index) {
        return (index >= 0 && index < players.size()) ? players.get(index) : null;
    }

    boolean addPlayer(Player player) {
        if (player == null) {
            LOG.error("addPlayer: " + "player null" + " for slot=" + number);
            return false;
        }
        if (isFull()) {
            LOG.warn("addPlayer: slot=" + number + " is full");
            return false;
        }
        if (hasPlayer(player)) {
            LOG.warn("addPlayer: player id=" + player.getId() + " already present in slot=" + number);
            return false;
        }

        players.add(player);
        return true;
    }

    public void setInteractable(Interactable interactable) {
        this.interactable = interactable;
    }

    void removePlayer(Player player) {
        if (player == null) {
            LOG.error("removePlayer: " + "player null" + " for slot=" + number);
            return;
        }
        if (!players.remove(player)) {
            LOG.warn("removePlayer: player id=" + player.getId() + " not found in slot=" + number);
        }
    }


    boolean hasPlayer(Player player) {
        for (Player p : players) {
            if (p.equals(player)) {
                return true;
            }
        }
        return false;
    }

    boolean isFull() {
        return players.size() >= GameConfig.SLOT_SIZE;
    }


    String[] getInfo() {

        String[] result = new String[3];

        StringBuilder pl = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            if (i > 0) {
                pl.append(',');
            }
            pl.append(players.get(i).getId());
        }


        String interType = "";
        String name = "";

        if (getInteractable() != null) {
            String raw = getInteractable().toString();
            char c = raw.charAt(0);
            interType = (c == '0' ? "A" : "T") + raw.substring(1);
            name += getInteractable().getName();
        }

        result[0] = pl.toString();
        result[1] = name;
        result[2] = interType;

        return result;
    }
}