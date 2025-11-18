package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

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
