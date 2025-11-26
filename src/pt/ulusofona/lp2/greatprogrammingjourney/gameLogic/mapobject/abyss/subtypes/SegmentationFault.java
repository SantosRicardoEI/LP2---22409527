package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SegmentationFault extends Abyss {

    public SegmentationFault(int id, String name, String png, Tool counter) {
        super(id, name, png, counter);
    }

    @Override
    public String effectMessage() {
        return "Segmentation Fault! Todos os programadores nesta casa recuam 3 casas.";
    }

    @Override
    public void applyAbyssEffects(Player player, Board board, MoveHistory moveHistory) {
        int thisPosition = board.getPlayerPosition(player);

        List<Player> playersInThisSlot = new ArrayList<>(board.getPlayersAt(thisPosition));

        if (playersInThisSlot.size() < 2) {
            return;
        }

        for (Player p : playersInThisSlot) {
            board.movePlayerBySteps(p, -3);
        }
    }
}