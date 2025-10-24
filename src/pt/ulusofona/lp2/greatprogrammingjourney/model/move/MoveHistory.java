package pt.ulusofona.lp2.greatprogrammingjourney.model.move;

import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.util.LinkedList;

public final class MoveHistory {

    // ============================== State ============================================================================
    private final LinkedList<Move> moves = new LinkedList<>();
    private static final GameLogger LOG = new GameLogger(MoveHistory.class);

    // ============================== Constructor ======================================================================

    public MoveHistory() {
    }

    // ============================== Public API =======================================================================

    public void addRecord(int playerId, int from, int to, int die) {
        int turn = moves.size() + 1;
        moves.add(new Move(playerId, from, to, die, turn));
        LOG.info("addRecord: turn=" + turn + ", playerId=" + playerId +
                ", from=" + from + ", to=" + to + ", die=" + die);
    }

    public int getSize() {
        return moves.size();
    }

    public void reset() {
        moves.clear();
        LOG.info("reset: move log reseted");
    }

    @Override
    public String toString() {
        return moves.toString();
    }
}