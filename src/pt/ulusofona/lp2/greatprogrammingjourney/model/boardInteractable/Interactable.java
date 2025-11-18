package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

import java.util.Objects;

public abstract class BoardInteractable {

    protected final int id;
    protected final String name;
    protected final InteractableType interactableType;
    protected final String png;

    public BoardInteractable(int id, String name, InteractableType type,String png) {
        this.id = id;
        this.name = name;
        this.interactableType = type;
        this.png = png;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardInteractable that)) {
            return false;
        }
        return id == that.id && interactableType == that.interactableType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, interactableType);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public InteractableType getInteractableType() {
        return interactableType;
    }


    public String getDescription() {
        return "OLA";
    }

    public  String getPng() {
        return png;
    }

    @Override
    public String toString() {
        return interactableType + " " + name;
    }
}
