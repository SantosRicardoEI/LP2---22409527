package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;


public record LoadedGame(Board board, MoveHistory history, int currentPlayerID) {
}