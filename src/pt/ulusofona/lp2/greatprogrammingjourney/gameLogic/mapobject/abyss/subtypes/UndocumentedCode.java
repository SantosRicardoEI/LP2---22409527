package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public class UndocumentedCode extends Abyss {

    public UndocumentedCode(int id, String name, String png, Tool counter) {
        super(id, name, png, counter);
    }

    @Override
    public String effectMessage() {
        return "Projeto não documentado! O programador está preso durante um turno.";
    }

    @Override
    public void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory) {
        //TODO
    }
}