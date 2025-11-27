package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

public class InfiniteLoop extends Abyss {

    public InfiniteLoop(int id, String name, String png, Tool counter) {
        super(id, name, png, counter);
    }

    @Override
    public String effectMessage() {
        return "Ciclo infinito! O programador ficou preso e libertou os restantes.";
    }


    //TODO
    // Acrescente o last e actul para ver se era o problema
    @Override
    public void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory) {
        int lastPosition = moveHistory.getPosition(player, 0);
        int thisPosition = board.getPlayerPosition(player);

        if (thisPosition != lastPosition) {
            player.lock(true);
        }
        for (Player p : board.getPlayersAt(thisPosition)) {
            if (!p.equals(player)) {
                p.lock(false);
            }
        }
    }
}
