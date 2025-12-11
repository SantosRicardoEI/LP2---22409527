package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public class SecondaryEffects extends Abyss {

    public SecondaryEffects(int id, String name, String png, Tool counter) {
        super(id, name, png, counter);
    }

    @Override
    public String effectMessage() {
        return "Efeitos secundários! O programador recua para a sua casa duas jogadas atrás";
    }

    @Override
    public void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory) {
        int lastPosition = moveHistory.getPosition(player,1);
        board.changePlayerPosition(player,lastPosition);
    }
}
