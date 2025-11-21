package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public abstract class Abyss extends MapObject {

    protected final Tool counter;

    protected Abyss(AbyssSubType subType) {
        super(subType.getId(), subType.getName(), subType.getImage());
        this.counter = ToolSubType.createTool(subType.getCounter());
    }

    @Override
    public final String interact(Player player, Board board, MoveHistory moveHistory) {
        boolean usedTool = counter != null && player.hasTool(counter);

        if (usedTool) {
            player.useTool(counter);
        } else {
            applyAbyssEffects(player, board, moveHistory);
        }

        return usedTool ? counteredMessage() : effectMessage();
    }

    @Override
    public final String toString() {
        return "0:" + id;
    }

    public final String counteredMessage() {
        return name + " anulado por " + counter.getName();
    }

    public abstract String effectMessage();

    public abstract void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory);
}