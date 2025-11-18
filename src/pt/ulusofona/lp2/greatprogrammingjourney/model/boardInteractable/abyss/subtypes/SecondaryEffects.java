package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
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
    public String abyssFallMessage() {
        return "Secondary Effects! Programador recua para a posição de duas jogadas atrás!";
    }

    @Override
    public void affectPlayer(Player player, Board board) {
    }
}
