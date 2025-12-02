package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.util.ArrayList;
import java.util.List;

final class Slot {

    // ============================== State ============================================================================

    private final int number;
    private final List<Player> players = new ArrayList<>(GameConfig.SLOT_SIZE);
    private MapObject mapObject;
    private static final GameLogger LOG = new GameLogger(Slot.class);

    // ============================== Constructor ======================================================================

    Slot(int slotNumber) {
        this.number = slotNumber;
        this.mapObject = null;
    }

    // ============================== Getters ==========================================================================

    int getNumber() {
        return number;
    }

    List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    MapObject getMapObject() {
        return this.mapObject;
    }

    // ============================== Methods =======================================================================

    Player getPlayer(int index) {
        return (index >= 0 && index < players.size()) ? players.get(index) : null;
    }

    boolean addPlayer(Player player) {
        if (player != null && !(players.size() >= GameConfig.SLOT_SIZE) && !hasPlayer(player)) {
            players.add(player);
            return true;
        }
        LOG.error("addPlayer: " + "error adding player" + " for slot=" + number);
        return false;
    }

    void setMapObject(MapObject mapObject) {
        this.mapObject = mapObject;
    }

    void removePlayer(Player player) {
        if (player != null) {
            players.remove(player);
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

    String[] getInfo() {

        String[] result = new String[3];

        StringBuilder players = new StringBuilder();
        for (int i = 0; i < this.players.size(); i++) {
            if (i > 0) {
                players.append(',');
            }
            players.append(this.players.get(i).getId());
        }


        String mapObjectType = "";
        String name = "";

        if (getMapObject() != null) {
            String raw = getMapObject().toString();
            char c = raw.charAt(0);
            mapObjectType = (c == '0' ? "A" : "T") + raw.substring(1);
            name += getMapObject().getName();
        }

        result[0] = players.toString();
        result[1] = name;
        result[2] = mapObjectType;

        return result;
    }
}