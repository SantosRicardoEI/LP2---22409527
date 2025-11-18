package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssContext;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class SyntaxError extends Abyss {

    public SyntaxError() {
        super(AbyssSubType.SYNTAX_ERROR);
    }

    @Override
    public String abyssFallMessage() {
        return "Memory Fault! Programador recua para posição anterior!";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        // Jogador recua para a casa onde estava antes desta (posição anterior no historico)
        int previousPos = ctx.getPreviousPosition(player);
        ctx.movePlayerTo(player, previousPos);
    }
}
