package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.util.LinkedList;

public final class MoveHistory {

    // ============================== State ============================================================================

    private final LinkedList<Move> moves = new LinkedList<>();
    private static final GameLogger LOG = new GameLogger(MoveHistory.class);

    // ============================== Constructor ======================================================================

    public MoveHistory() {
    }

    // ================================== Getters ======================================================================

    public int getRoll(Player p, int stepsBack) {
        int playerId = p.getId();
        int count = 0;

        for (int i = moves.size() - 1; i >= 0; i--) {
            Move m = moves.get(i);

            if (m.getPlayerId() == playerId) {
                if (count == stepsBack) {
                    return m.getDice();
                }
                count++;
            }
        }

        LOG.warn("getRoll: playerId=" + playerId + " does not have " + stepsBack + " past rolls");
        return 0;
    }

    public int getPosition(Player p, int stepsBack) {
        int playerId = p.getId();
        int count = 0;

        for (int i = moves.size() - 1; i >= 0; i--) {
            Move m = moves.get(i);

            if (m.getPlayerId() == playerId) {
                if (count == stepsBack) {
                    return m.getFrom();
                }
                count++;
            }
        }

        LOG.warn("getPosition: playerId=" + playerId
                + " does not have " + stepsBack + " past positions (using start position 1)");
        return 1;
    }

    public String[] getMoveInfo(int turn) {
        for (Move m : moves) {
            if (m.getTurn() == turn) {
                return m.getInfo();
            }
        }
        throw new IllegalArgumentException("Turn " + turn + " does not exist in move history");
    }

    public String[][] getAllMovesInfo() {
        String[][] info = new String[moves.size()][4];

        for (int i = 0; i < moves.size(); i++) {
            info[i] = getMoveInfo(i + 1);
        }

        return info;
    }


    // ======================================== Public Methods =========================================================

    public void addRecord(int playerId, int from, int to, int dice) {
        int turn = moves.size() + 1;
        moves.add(new Move(playerId, from, to, dice, turn));
        LOG.info("Move record: turn: " + turn + ", player: " + playerId +
                ", from: " + from + ", to: " + to + ", dice: " + dice);
    }

    public void reset() {
        moves.clear();
    }

    @Override
    public String toString() {
        return moves.toString();
    }

}