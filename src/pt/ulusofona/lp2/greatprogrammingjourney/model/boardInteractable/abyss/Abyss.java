package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public abstract class Abyss extends BoardInteractable {

    public Abyss(int id, String name, int interactionCode) {
        super(id, name, interactionCode, InteractableType.ABYSS);
    }

    @Override
    public final void interact(Player player) {
        affectPlayer(player);
    }

    public abstract void affectPlayer(Player player);
}