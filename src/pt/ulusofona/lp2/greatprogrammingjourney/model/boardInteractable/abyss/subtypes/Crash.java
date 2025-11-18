package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssContext;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class Crash extends Abyss {

    public Crash() {
        super(AbyssSubType.CRASH);
    }

    @Override
    public String abyssFallMessage() {
        return "Crash! Programador volta ao inicio!";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        // Jogador volta á primeira casa do jogo (pos 1).
        ctx.sendPlayerToStart(player);
    }
}
