package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssContext;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class InfiniteLoop extends Abyss {

    public InfiniteLoop() {
        super(AbyssSubType.INFINITE_LOOP);
    }

    @Override
    public String abyssFallMessage() {
        return "Infinite loop! O programador fica preso.";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        // Jogador fica preso nesta casa (não se pdoe mover nos turnos seguintes).
        // Se outro jogador cai nesta casa (fica preso), o outro preso é libertado
        int pos = ctx.getPosition(player);

        // todos os jogadores nesta casa
        for (Player other : ctx.getPlayersAt(pos)) {
            if (other != player && ctx.isPlayerStuck(other)) {
                // libertar o que já estava preso
                ctx.setPlayerStuck(other, false);
            }
        }

        // prender o jogador atual
        ctx.setPlayerStuck(player, true);
    }
}
