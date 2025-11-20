package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory;

final class Move {

    // ============================== State ==========================================
    private final int playerId;
    private final int from;
    private final int to;
    private final int dice;
    private final int turn;

    // ============================ Constructor =====================================

    Move(int playerId, int from, int to, int dice, int turn) {
        this.playerId = playerId;
        this.from = from;
        this.to = to;
        this.dice = dice;
        this.turn = turn;
    }

    int getPlayerId() {
        return playerId;
    }

    int getFrom() {
        return from;
    }

    int getTo() {
        return to;
    }

    int getTurn() {
        return turn;
    }

    int getDice() {
        return dice;
    }

    String[] getInfo() {
        return new String[]{
                String.valueOf(playerId),
                String.valueOf(from),
                String.valueOf(to),
                String.valueOf(dice)
        };
    }

    @Override
    public String toString() {
        return "[#" + turn + " (dice: " + dice + "): P" + playerId + " " + from + " -> " + to + "]";
    }
}