package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class SyntaxError extends Abyss {

    public SyntaxError() {
        super(AbyssSubType.SYNTAX_ERROR);
    }

    @Override
    public String effectMessage() {
        return "DEFAULT ABYSS MESSAGE: " + name;
    }

    @Override
    public String abyssFallMessage() {
        return "Memory Fault! Programador recua para posição anterior!";
    }

    @Override
    public void affectPlayer(Player player, Board board) {
    }
}
