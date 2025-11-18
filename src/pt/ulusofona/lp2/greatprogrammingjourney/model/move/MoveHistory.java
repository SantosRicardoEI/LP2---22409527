package pt.ulusofona.lp2.greatprogrammingjourney.model.move;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;
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

    public void addRecord(int playerId, int from, int to, int dice) {
        int turn = moves.size() + 1;
        moves.add(new Move(playerId, from, to, dice, turn));
        LOG.info("addRecord: turn=" + turn + ", playerId=" + playerId +
                ", from=" + from + ", to=" + to + ", dice=" + dice);
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

    public int getLastRoll(Player p) {
        int playerId = p.getId();

        // percorre de trás para a frente
        for (int i = moves.size() - 1; i >= 0; i--) {
            Move m = moves.get(i);
            if (m.getPlayerId() == playerId) {
                return m.getDice();
            }
        }

        LOG.warn("getLastRoll: no moves found for playerId=" + playerId);
        return 0;
    }

    public int getLastPositionBefore(Player p) {
        int playerId = p.getId();

        for (int i = moves.size() - 1; i >= 0; i--) {
            Move m = moves.get(i);
            if (m.getPlayerId() == playerId) {
                return m.getFrom();
            }
        }

        LOG.warn("getLastPositionBefore: no moves found for playerId=" + playerId + " (using start position 1)");
        return 1;
    }

    public int getPositionNMovesAgo(Player p, int n) {
        int playerId = p.getId();
        int count = 0;

        // Voltar para tras
        for (int i = moves.size() - 1; i >= 0; i--) {
            Move m = moves.get(i);
            if (m.getPlayerId() != playerId) {
                continue;
            }

            count++;

            // 1 movimento atras é o from do último
            // 2 movimentos atráa é o from do penúltimo
            if (count == n) {
                return m.getFrom();
            }
        }

        LOG.warn("getPositionNMovesAgo: not enough moves for playerId=" + playerId +
                ", requested n=" + n + " (using start position 1)");
        return 1;
    }
}