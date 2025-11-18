package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.InteractableType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public abstract class Tool extends Interactable {

    protected Tool(ToolSubType type) {
        super(type.getId(), type.getName(), InteractableType.TOOL ,type.getIcon());
    }

    @Override
    public String interact(Player player, Board board) {
        return "Jogador agarrou " + name;
    }
}
