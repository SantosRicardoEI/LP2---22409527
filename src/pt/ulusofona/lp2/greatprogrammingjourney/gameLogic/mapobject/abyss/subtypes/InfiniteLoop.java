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
    // Ainda nao passa no drop, move está a devolver false
    // Player deveria ter-se movido e nao moveu:
    // - player deveria estar stuck = false e estava true:
    //      - Player deveria ter sido libertado e não foi
    //

    // Se o player chegou a esta casa, fica preso e liberta todos os outros
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
