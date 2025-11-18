package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.move.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameAbyssContext implements AbyssContext {

    private final Board board;
    private final MoveHistory history;
    private final Map<Player, Boolean> stuck = new HashMap<>();

    public GameAbyssContext(Board board, MoveHistory moveHistory) {
        this.board = board;
        this.history = moveHistory;
    }

    @Override
    public void movePlayerTo(Player p, int newPos) {
        board.movePlayerTo(p, newPos);
    }

    @Override
    public void movePlayerRelative(Player p, int delta) {
        board.movePlayerBySteps(p, delta);
    }

    @Override
    public void sendPlayerToStart(Player p) {
        board.movePlayerTo(p, 1);
    }

    @Override
    public int getPreviousPosition(Player p) {
        return history.getLastPositionBefore(p);
    }

    @Override
    public int getPositionNMovesAgo(Player p, int n) {
        return history.getPositionNMovesAgo(p, n);
    }

    @Override
    public int getLastDiceRoll(Player p) {
        return history.getLastRoll(p);
    }

    public boolean isPlayerStuck(Player p) {
        return p.isStuck();
    }

    @Override
    public void setPlayerStuck(Player p, boolean s) {
        stuck.put(p, s);
    }

    @Override
    public int getPosition(Player player) {
        return board.getPlayerPosition(player);
    }

    @Override
    public List<Player> getPlayersAt(int pos) {
        return board.getPlayersAt(pos);
    }

    @Override
    public Player getOtherPlayerAtSamePosition(Player p) {
        int pos = board.getPlayerPosition(p);
        for (Player other : board.getPlayersAt(pos)) {
            if (other != p) {
                return other;
            }
        }
        return null;
    }
}