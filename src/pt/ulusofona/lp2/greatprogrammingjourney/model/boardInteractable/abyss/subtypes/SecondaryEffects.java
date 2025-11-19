package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.move.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class SecondaryEffects extends Abyss {

    public SecondaryEffects() {
        super(AbyssSubType.SECONDARY_EFFECTS);
    }

    @Override
    public String effectMessage() {
        return "DEFAULT ABYSS MESSAGE: " + name;
    }

    @Override
    public void affectPlayer(Player player, Board board, MoveHistory moveHistory) {
        int lastPosition = moveHistory.getPosition(player,1);
        board.movePlayerTo(player,lastPosition);
    }
}
