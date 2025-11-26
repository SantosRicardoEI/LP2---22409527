package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public abstract class Tool extends MapObject {

    public Tool(int id, String name, String png) {
        super(id, name, png);
    }

    @Override
    public String interact(Player player, Board board, MoveHistory moveHistory) {
        player.addTool(this);
        return "Jogador agarrou " + name;
    }

    @Override
    public String toString() {
        return "1:" + id;
    }
}
