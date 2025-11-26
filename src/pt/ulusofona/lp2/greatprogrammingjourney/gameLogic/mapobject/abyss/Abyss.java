package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public abstract class Abyss extends MapObject {

    protected final Tool counter;

    public Abyss(int id, String name, String png, Tool counter) {
        super(id, name, png);
        this.counter = counter;
    }


    public Tool getCounter() {
        return counter;
    }

    @Override
    public final String interact(Player player, Board board, MoveHistory moveHistory) {
        Tool chatGPT = ToolSubType.getTool(ToolSubType.CHAT_GPT.getId());

        if (this.counter != null) {

            if (player.hasTool(counter)) {
                player.useTool(counter);
                return counteredMessage(counter);
            } else if (player.hasTool(chatGPT) && GameConfig.ENABLE_TOOL_CHAT_GPT) {
                player.useTool(chatGPT);
                return counteredMessage(chatGPT);
            }
        }
        applyAbyssEffects(player, board, moveHistory);
        return effectMessage();
    }

    @Override
    public final String toString() {
        return "0:" + id;
    }

    public final String counteredMessage(Tool tCounter) {
        return name + " anulado por " + tCounter.getName();
    }

    public abstract String effectMessage();

    public abstract void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory);
}