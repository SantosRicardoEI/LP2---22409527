package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

import java.util.Objects;

public abstract class Interactable {

    protected final int id;
    protected final String name;
    protected final String png;

    public Interactable(int id, String name, String png) {
        this.id = id;
        this.name = name;
        this.png = png;
    }

    public abstract String interact(Player player, Board board);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }

        Interactable that = (Interactable) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public  String getPng() {
        return png;
    }
}
