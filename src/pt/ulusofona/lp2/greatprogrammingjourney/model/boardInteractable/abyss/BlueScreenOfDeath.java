package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

// Programador perde imediatamente o jogo
// Sem counter
public class BlueScreenOfDeath extends Abyss{
    public BlueScreenOfDeath(int id, String name) {
        super(7, "Blue Screen of Death");
    }

    @Override
    public String abyssFallMessage() {
        return "";
    }

    @Override
    public void affectPlayer(Player player) {
        player.kill();
    }
}
