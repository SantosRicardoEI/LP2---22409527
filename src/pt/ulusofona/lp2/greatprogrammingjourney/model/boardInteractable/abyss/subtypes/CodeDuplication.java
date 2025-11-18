package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class CodeDuplication extends Abyss {

    public CodeDuplication() {
        super(AbyssSubType.DUPLICATED_CODE);
    }

    @Override
    public String effectMessage() {
        return "DEFAULT ABYSS MESSAGE: " + name;
    }

    @Override
    public String abyssFallMessage() {
        return "Code Duplication! Programador volta para a posição anterior!";
    }

    @Override
    public void affectPlayer(Player player, Board board) {
    }
}
