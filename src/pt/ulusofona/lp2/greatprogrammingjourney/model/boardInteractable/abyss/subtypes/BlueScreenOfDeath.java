package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssContext;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class BlueScreenOfDeath extends Abyss {

    public BlueScreenOfDeath() {
        super(AbyssSubType.BSOD);
    }

    @Override
    public String abyssFallMessage() {
        return "Blue Screen of Death! O programador perde o jogo.";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        player.kill();
    }
}
