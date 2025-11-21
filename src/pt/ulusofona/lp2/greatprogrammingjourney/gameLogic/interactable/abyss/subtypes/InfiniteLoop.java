package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public class InfiniteLoop extends Abyss {

    public InfiniteLoop() {
        super(AbyssSubType.INFINITE_LOOP);
    }

    @Override
    public String effectMessage() {
        return "DEFAULT ABYSS MESSAGE: " + name;
    }


    @Override
    public void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory) {
        player.lock(true);
    }
    @Override
    public void applyCommonEffects(Player player, Board board, MoveHistory moveHistory) {
        int thisPosition = board.getPlayerPosition(player);
        for (Player p : board.getPlayersAt(thisPosition)) {
            if (!p.equals(player)) {
                p.lock(false);
            }
        }
    }

    @Override
    public void applyOnToolCounterEffects(Player player, Board board, MoveHistory moveHistory) {
        player.lock(false);
    }
}
