package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.util.Objects;


public abstract class MapObject {

    protected final int id;
    protected final String name;
    protected final String png;

    public MapObject(int id, String name, String png) {
        this.id = id;
        this.name = name;
        this.png = png;
    }

    @Override
    public boolean equals(Object o) {
        MapObject i = (MapObject) o;
        return id == i.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public abstract String interact(Player player, Board board, MoveHistory moveHistory);

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
