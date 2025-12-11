package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.util.ArrayList;
import java.util.List;


public final class TurnManager {

    // ===================================== State =====================================================================

    private int currentID;
    private int turnCount;
    private static final GameLogger LOG = new GameLogger(TurnManager.class);

    // ==================================== Constructor ================================================================

    public TurnManager(List<Player> players) {
        currentID = getFirstPlayerId(players);
        turnCount = 1;
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
        currentID = getNextPlayerId(players);
        turnCount++;
    }

    // ============================== Helper Methods ===================================================================

    private int getFirstPlayerId(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("getFirstPlayerId: players list is null or empty");
        }

        int firstId = getMinimumPlayerId(players);
        if (firstId == -1) {
            throw new IllegalStateException("getFirstPlayerId: no active players available");
        }

        LOG.info("First turn ID: " + firstId);
        return firstId;
    }


    private int getNextPlayerId(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("getNextPlayerId: players list is null or empty");
        }

        int nextId = getMinimumPlayerId(players, currentID);
        if (nextId == -1) {
            nextId = getMinimumPlayerId(players);
        }

        if (nextId == -1) {
            throw new IllegalStateException("getNextPlayerId: no active players to select as next");
        }

        LOG.info("Next turn ID: " + nextId);
        return nextId;
    }

    private int getMinimumPlayerId(List<Player> players) {
        return getMinimumPlayerId(players, -1);
    }

    private int getMinimumPlayerId(List<Player> players, int greaterThan) {

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