package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;


public record LoadedGame(Board board, MoveHistory history, int currentPlayerID, int turnCount) {
    public Board getBoard() {
        return this.board;
    }

    public MoveHistory getMoveHistory() {
        return this.history;
    }

    public int getCurrentPlayerID() {
        return this.currentPlayerID;
    }

    public int getTurnCount() {
        return this.turnCount;
    }
}