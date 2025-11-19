package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.move.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

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
    public void affectPlayer(Player player, Board board, MoveHistory moveHistory) {
        int thisPosition = board.getPlayerPosition(player);
        List<Player> playersInThisSlot = board.getPlayersAt(thisPosition);

        if (playersInThisSlot.size() < 2) {
            return;
        }

        for (Player p : playersInThisSlot) {
            board.movePlayerBySteps(p,-3);
        }
    }
}