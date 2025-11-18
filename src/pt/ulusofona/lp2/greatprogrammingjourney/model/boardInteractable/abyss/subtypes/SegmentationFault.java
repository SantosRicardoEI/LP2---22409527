package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssContext;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

import java.util.List;

// TODO
// BUGS
// - ATIVADO COM APENAS UM PLAYER NA CASA
// - PLAYERS NAO RECUAM


public class SegmentationFault extends Abyss {

    public SegmentationFault() {
        super(AbyssSubType.SEGMENTATION_FAULT);
    }

    @Override
    public String abyssFallMessage() {
        return "Segmentation Fault! Mais de 1 programador na casa, todos recuam 3 casas!";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        // Se estao pelo menos 2 jogadorres nesta casa:
        // - Todos os jogadores recuam3 3 casas
        int pos = ctx.getPosition(player);
        List<Player> players = ctx.getPlayersAt(pos);

        if (players.size() >= 2) {
            for (Player p : players) {
                ctx.movePlayerRelative(p, -3);
            }
        }
    }
}