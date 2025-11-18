package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class SecondaryEffects extends Abyss {

    public SecondaryEffects() {
        super(AbyssSubType.SECONDARY_EFFECTS);
    }

    @Override
    public String abyssFallMessage() {
        return "Secondary Effects! Programador recua para a posição de duas jogadas atrás!";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        // Jogador recua para a casa onde estava há dois movimentos atrás
        // (duas posições atrás no historico).
        int posTwoMovesAgo = ctx.getPositionNMovesAgo(player, 2);
        ctx.movePlayerTo(player, posTwoMovesAgo);
    }
}
