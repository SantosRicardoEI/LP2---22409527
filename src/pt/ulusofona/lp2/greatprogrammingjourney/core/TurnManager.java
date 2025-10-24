package pt.ulusofona.lp2.greatprogrammingjourney.core;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.util.List;


final class TurnManager {

    private static final GameLogger LOG = new GameLogger(TurnManager.class);

    // ============================== Constructor ======================================================================

    private TurnManager() {
    }

    // ============================== Static Methods ===================================================================

    static int getFirstPlayerId(List<Player> players, TurnOrder order) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("getFirstPlayerId: players list is null or empty");
        }
        TurnOrder ord = (order == null) ? TurnOrder.ASCENDING : order;

        return switch (ord) {
            case ASCENDING -> getMinimumPlayerId(players);
            case DESCENDING -> getMaximumPlayerId(players);
        };
    }

    static int getNextPlayerId(List<Player> players, int currentId, TurnOrder order) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("getNextPlayerId: players list is null or empty");
        }

        if (order == null) {
            order = TurnOrder.ASCENDING;
        }

        int nextId = -1;

        switch (order) {
            case ASCENDING:
                nextId = getMinimumPlayerId(players, currentId);
                if (nextId == -1) {
                    nextId = getMinimumPlayerId(players);
                }
                break;

            case DESCENDING:
                nextId = getMaximumPlayerId(players, currentId);
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


    // ============================== Helper Methods ===================================================================

    private static int getMinimumPlayerId(List<Player> players) {
        return getMinimumPlayerId(players, -1);
    }

    private static int getMinimumPlayerId(List<Player> players, int greaterThan) {
        if (players == null || players.isEmpty()) {
            return -1;
        }

        int minimum = Integer.MAX_VALUE;
        for (Player player : players) {
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

    private static int getMaximumPlayerId(List<Player> players) {
        return getMaximumPlayerId(players, Integer.MAX_VALUE);
    }

    private static int getMaximumPlayerId(List<Player> players, int smallerThan) {
        if (players == null || players.isEmpty()) {
            return -1;
        }

        int maximum = -1;
        for (Player player : players) {
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
}