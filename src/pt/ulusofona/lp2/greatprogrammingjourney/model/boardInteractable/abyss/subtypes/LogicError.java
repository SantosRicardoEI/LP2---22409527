package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssContext;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class LogicError extends Abyss {

    public LogicError() {
        super(AbyssSubType.LOGIC_ERROR);
    }

    @Override
    public String abyssFallMessage() {
        return "Logic Error! Programador recua dado/2 casas!";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        // Jogador recua N casas. N = valor do dado / 2 (truncado).
        int roll = ctx.getLastDiceRoll(player);
        int delta = -(roll / 2);
        ctx.movePlayerRelative(player, delta);
    }
}
