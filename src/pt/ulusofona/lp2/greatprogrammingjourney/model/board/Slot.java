package pt.ulusofona.lp2.greatprogrammingjourney.model.board;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.InputValidator;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

import java.util.ArrayList;
import java.util.List;

final class Slot {

    // ============================== State ============================================================================
    private final int number;
    private final List<Player> players = new ArrayList<>(GameConfig.SLOT_SIZE);
    private static final GameLogger LOG = new GameLogger(Slot.class);

    // ============================== Constructor ======================================================================

    Slot(int slotNumber) {
        this.number = slotNumber;
    }

    // ============================== Getters ==========================================================================

    int getNumber() {
        return number;
    }

    List<Player> getPlayers() {
        return java.util.Collections.unmodifiableList(players);
    }

    // ============================== Public API =======================================================================

    Player getPlayer(int index) {
        return (index >= 0 && index < players.size()) ? players.get(index) : null;
    }

    boolean addPlayer(Player player) {
        ValidationResult playerOk = InputValidator.validatePlayerNotNull(player);
        if (!playerOk.isValid()) {
            LOG.error("addPlayer: " + playerOk.getMessage() + " for slot=" + number);
            return false;
        }
        if (isFull()) {
            LOG.warn("addPlayer: slot=" + number + " is full");
            return false;
        }
        if (hasPlayer(player.getId())) {
            LOG.warn("addPlayer: player id=" + player.getId() + " already present in slot=" + number);
            return false;
        }

        players.add(player);
        return true;
    }


    void removePlayer(Player player) {
        var playerOk = InputValidator.validatePlayerNotNull(player);
        if (!playerOk.isValid()) {
            LOG.error("removePlayer: " + playerOk.getMessage() + " for slot=" + number);
            return;
        }
        if (!players.remove(player)) {
            LOG.warn("removePlayer: player id=" + player.getId() + " not found in slot=" + number);
        }
    }


    boolean hasPlayer(int id) {
        for (Player player : players) {
            if (player.getId() == id) {
                return true;
            }
        }
        return false;
    }

    boolean isFull() {
        return players.size() >= GameConfig.SLOT_SIZE;
    }


    String[] getInfo() {
        if (players.isEmpty()) {
            return new String[]{""};
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(players.get(i).getId());
        }
        return new String[]{sb.toString()};
    }
}