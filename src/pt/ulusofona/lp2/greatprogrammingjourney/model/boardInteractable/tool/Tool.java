package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public abstract class Tool extends BoardInteractable {

    public Tool(int id, String name, int interactionCode) {
        super(id, name, interactionCode,InteractableType.TOOL);
    }

    @Override
    public final void interact(Player player) {
        use(player);
    }

    public abstract void use(Player player);
}
