package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss;

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
        boolean usedTool = counter != null && player.hasTool(counter);

        if (usedTool) {
            player.useTool(counter);
        } else {
            if (player.hasTool(ToolSubType.CHAT_GPT.getInstance())) {
                double r = Math.random();

                if (r < 0.5) {
                    return name + " anulado por ChatGPT";
                }

                if (r < 0.75) {
                    applyAbyssEffects(player, board, moveHistory);
                    if (!player.isStuck()) {
                        board.movePlayerBySteps(player, -1);
                    }
                    return "ChatGPT confundiu ainda mais o programador no abismo " + name + " (recuou 1 casa)";
                }

                applyAbyssEffects(player, board, moveHistory);
                if (!player.isStuck()) {
                    board.movePlayerBySteps(player, 1);
                }
                return "ChatGPT reduziu o efeito do abismo " + name + " (avançou 1 casa)";
            }
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