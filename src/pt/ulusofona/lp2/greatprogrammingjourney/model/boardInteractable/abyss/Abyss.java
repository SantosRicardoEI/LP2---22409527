package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.InteractableType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public abstract class Abyss extends Interactable {

    private final ToolSubType counter;

    protected Abyss(AbyssSubType subType) {
        super(subType.getId(), subType.getName(), InteractableType.ABYSS ,subType.getImage());
        this.counter = subType.getCounter();
    }

    @Override
    public String interact(Player player, Board board) {
        affectPlayer(player,board);
        return effectMessage();
    }

    public abstract String effectMessage();

    public ToolSubType getCounter() {
        return counter;
    }

    public final String counteredMessage(String counterName) {
        return name + " anulado por " + counterName;
    }

    public abstract String abyssFallMessage();

    public abstract void affectPlayer(Player player, Board board);

}