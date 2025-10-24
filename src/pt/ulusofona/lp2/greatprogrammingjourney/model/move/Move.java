package pt.ulusofona.lp2.greatprogrammingjourney.model.move;

final class Move {

    // ============================== State ==========================================
    private final int playerId;
    private final int from;
    private final int to;
    private final int die;
    private final int turn;

    // ============================ Constructor =====================================

    Move(int playerId, int from, int to, int die, int turn) {
        this.playerId = playerId;
        this.from = from;
        this.to = to;
        this.die = die;
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "[#" + turn + " (dice: " + die + "): P" + playerId + " " + from + " -> " + to + "]";
    }
}