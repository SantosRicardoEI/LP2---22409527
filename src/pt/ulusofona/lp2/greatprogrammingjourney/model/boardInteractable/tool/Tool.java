package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.move.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public abstract class Tool extends Interactable {

    protected Tool(ToolSubType type) {
        super(type.getId(), type.getName(),type.getImage());
    }

    @Override
    public String interact(Player player, Board board, MoveHistory moveHistory) {
        player.addTool(this);
        return "Jogador agarrou " + name;
    }

    @Override
    public String toString() {
        return "F";
    }
}
