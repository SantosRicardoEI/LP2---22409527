package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.TurnOrder;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.util.ArrayList;
import java.util.List;

import static pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig.TURN_ORDER;


public final class TurnManager {

    // ===================================== State =====================================================================

    private int currentID;
    private int turnCount;
    private static final GameLogger LOG = new GameLogger(TurnManager.class);

    // ==================================== Constructor ================================================================

    public TurnManager(List<Player> players) {
        currentID = getFirstPlayerId(players, TURN_ORDER);
        turnCount = 0;
    }

    public TurnManager(int currentID, int turnCount) {
        this.currentID = currentID;
        this.turnCount = turnCount;
    }

    public int getCurrentID() {
        return currentID;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void advanceTurn(List<Player> players) {
        currentID = getNextPlayerId(players, TURN_ORDER);
        turnCount++;
    }

    // ============================== Helper Methods ===================================================================

    private int getFirstPlayerId(List<Player> players, TurnOrder order) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("getFirstPlayerId: players list is null or empty");
        }
        TurnOrder ord = (order == null) ? TurnOrder.ASCENDING : order;

        return switch (ord) {
            case ASCENDING -> getMinimumPlayerId(players);
            case DESCENDING -> getMaximumPlayerId(players);
        };
    }

    private int getNextPlayerId(List<Player> players, TurnOrder order) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("getNextPlayerId: players list is null or empty");
        }

        if (order == null) {
            order = TurnOrder.ASCENDING;
        }

        int nextId = -1;

        switch (order) {
            case ASCENDING:
                nextId = getMinimumPlayerId(players, currentID);
                if (nextId == -1) {
                    nextId = getMinimumPlayerId(players);
                }
                break;

            case DESCENDING:
                nextId = getMaximumPlayerId(players, currentID);
                if (nextId == -1) {
                    nextId = getMaximumPlayerId(players);
                }
                break;

            default:
                LOG.warn("getNextPlayerId: unsupported order type " + order);
        }

        if (nextId == -1) {
            LOG.warn("getNextPlayerId: could not resolve next ID (" + order + ")");
        } else {
            LOG.info("Next turn ID (" + order + "): " + nextId);
        }

        return nextId;
    }

    private int getMinimumPlayerId(List<Player> players) {
        return getMinimumPlayerId(players, -1);
    }

    private int getMinimumPlayerId(List<Player> players, int greaterThan) {
        if (players == null || players.isEmpty()) {
            return -1;
        }

        List<Player> activePlayers = activePlayers(players);

        int minimum = Integer.MAX_VALUE;
        for (Player player : activePlayers) {
            if (player == null) {
                continue;
            }
            int pid = player.getId();
            if (pid > greaterThan && pid < minimum) {
                minimum = pid;
            }
        }
        return (minimum == Integer.MAX_VALUE) ? -1 : minimum;
    }

    private int getMaximumPlayerId(List<Player> players) {
        return getMaximumPlayerId(players, Integer.MAX_VALUE);
    }

    private int getMaximumPlayerId(List<Player> players, int smallerThan) {
        if (players == null || players.isEmpty()) {
            return -1;
        }

        List<Player> activePlayers = activePlayers(players);

        int maximum = -1;
        for (Player player : activePlayers) {
            if (player == null) {
                continue;
            }
            int pid = player.getId();
            if (pid < smallerThan && pid > maximum) {
                maximum = pid;
            }
        }
        return maximum;
    }

    private List<Player> activePlayers(List<Player> players) {
        List<Player> result = new ArrayList<>();
        for (Player p : players) {
            if (p.isAlive()) {
                result.add(p);
            }
        }
        return result;
    }
}