package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.InteractableType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public abstract class Abyss extends Interactable {

    private final ToolSubType counter;

    protected Abyss(AbyssSubType subType) {
        super(subType.getId(), subType.getName(), InteractableType.ABYSS ,subType.getImage());
        this.counter = subType.getCounter();
    }

    public ToolSubType getCounter() {
        return counter;
    }

    public final String counteredMessage(String counterName) {
        return name + " anulado por " + counterName;
    }

    public abstract String abyssFallMessage();

    public abstract void affectPlayer(Player player, AbyssContext ctx);

}