package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.move.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public abstract class Abyss extends Interactable {

    private final Tool counter;

    protected Abyss(AbyssSubType subType) {
        super(subType.getId(), subType.getName(),subType.getImage());
        this.counter = ToolSubType.createTool(subType.getCounter());
    }

    @Override
    public final String interact(Player player, Board board, MoveHistory moveHistory) {
        if (player.hasTool(counter)) {
            player.useTool(counter);
            return counteredMessage();
        }
        affectPlayer(player,board,moveHistory);
        return effectMessage();
    }

    public final String counteredMessage() {
        return name + " anulado por " + counter.getName();
    }

    @Override
    public final String toString() {
        return "A";
    }

    public abstract String effectMessage();


    public abstract void affectPlayer(Player player, Board board, MoveHistory moveHistory);

}