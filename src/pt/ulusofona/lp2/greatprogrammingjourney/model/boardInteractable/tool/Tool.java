package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.InteractableType;

public abstract class Tool extends Interactable {

    protected Tool(ToolSubType type) {
        super(type.getId(), type.getName(), InteractableType.TOOL ,type.getIcon());
    }

    public final String pickupMessage() {
        return "Jogador agarrou " + name;
    }
}
