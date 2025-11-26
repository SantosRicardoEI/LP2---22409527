package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public class LogicError extends Abyss {

    public LogicError(int id, String name, String png, Tool counter) {
        super(id, name, png, counter);
    }

    @Override
    public String effectMessage() {
        return "Erro de Lógica! O programador recua metade do valor do dado.";
    }

    @Override
    public void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory) {
        int lastRoll = moveHistory.getRoll(player,0);
        int stepsBack = lastRoll / 2;
        board.movePlayerBySteps(player,-stepsBack);
    }
}
