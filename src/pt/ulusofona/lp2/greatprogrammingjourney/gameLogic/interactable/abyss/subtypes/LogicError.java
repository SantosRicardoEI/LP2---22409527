package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public class LogicError extends Abyss {

    public LogicError() {
        super(AbyssSubType.LOGIC_ERROR);
    }

    @Override
    public String effectMessage() {
        return "DEFAULT ABYSS MESSAGE: " + name;
    }

    @Override
    public void affectPlayer(Player player, Board board, MoveHistory moveHistory) {
        int lastRoll = moveHistory.getRoll(player,0);
        int stepsBack = lastRoll / 2;
        board.movePlayerBySteps(player,stepsBack);
    }
}
