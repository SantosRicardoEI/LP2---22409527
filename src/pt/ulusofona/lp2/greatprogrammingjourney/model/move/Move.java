package pt.ulusofona.lp2.greatprogrammingjourney.model.move;

final class Move {

    // ============================== State ==========================================
    private final int playerId;
    private final int from;
    private final int to;
    private final int dice;
    private final int turn;

    // ============================ Constructor =====================================

    Move(int playerId, int from, int to, int die, int turn) {
        this.playerId = playerId;
        this.from = from;
        this.to = to;
        this.dice = die;
        this.turn = turn;
    }

    public int getDice() {
        return dice;
    }

    public int getPlayerId() {
        return playerId;
    }

    @Override
    public String toString() {
        return "[#" + turn + " (dice: " + dice + "): P" + playerId + " " + from + " -> " + to + "]";
    }

    public int getFrom() {
        return from;
    }
}