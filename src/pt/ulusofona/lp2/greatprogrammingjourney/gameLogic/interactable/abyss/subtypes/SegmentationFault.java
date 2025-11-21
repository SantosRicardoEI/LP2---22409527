package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SegmentationFault extends Abyss {

    public SegmentationFault() {
        super(AbyssSubType.SEGMENTATION_FAULT);
    }

    @Override
    public String effectMessage() {
        return "DEFAULT ABYSS MESSAGE: " + name;
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