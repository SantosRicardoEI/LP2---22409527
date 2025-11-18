package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssContext;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class Exception extends Abyss {

    public Exception() {
        super(AbyssSubType.EXCEPTION);
    }

    @Override
    public String abyssFallMessage() {
        return "Exception! Porgramador recua 2 casas!";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        // Jogador recua 2 casas.
        ctx.movePlayerRelative(player, -2);
    }
}