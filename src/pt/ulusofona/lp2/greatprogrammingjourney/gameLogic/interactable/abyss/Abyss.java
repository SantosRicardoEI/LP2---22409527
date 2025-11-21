package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public abstract class Abyss extends Interactable {

    protected final Tool counter;

    protected Abyss(AbyssSubType subType) {
        super(subType.getId(), subType.getName(),subType.getImage());
        this.counter = ToolSubType.createTool(subType.getCounter());
    }

    @Override
    public final String interact(Player player, Board board, MoveHistory moveHistory) {
        boolean usedTool = counter != null && player.hasTool(counter);

        if (usedTool) {
            player.useTool(counter);
            applyOnToolCounterEffects(player,board,moveHistory);
        } else {
            applyAbyssEffects(player, board, moveHistory);
        }

        applyCommonEffects(player, board, moveHistory);

        return usedTool ? counteredMessage() : effectMessage();
    }

    public final String counteredMessage() {
        return name + " anulado por " + counter.getName();
    }

    @Override
    public final String toString() {
        return "0:" + id;
    }

    public abstract String effectMessage();


    public abstract void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory);

    public void applyCommonEffects(Player player, Board board, MoveHistory moveHistory) {
    }

    public void applyOnToolCounterEffects(Player player, Board board, MoveHistory moveHistory) {

    }

}