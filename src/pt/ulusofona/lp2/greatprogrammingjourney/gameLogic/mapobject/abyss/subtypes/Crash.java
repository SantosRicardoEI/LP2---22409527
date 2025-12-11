package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public class Crash extends Abyss {

    public Crash(int id, String name, String png, Tool counter) {
        super(id, name, png, counter);
    }

    @Override
    public String effectMessage() {
        return "Crash! O programador volta para o início.";
    }

    @Override
    public void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory) {
        board.changePlayerPosition(player,1);
    }
}
