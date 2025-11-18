package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class FileNotFound extends Abyss {

    public FileNotFound() {
        super(AbyssSubType.FILE_NOT_FOUND);
    }

    @Override
    public String effectMessage() {
        return "DEFAULT ABYSS MESSAGE: " + name;
    }

    @Override
    public String abyssFallMessage() {
        return "File Not Found! Programador recua 3 casas!";
    }

    @Override
    public void affectPlayer(Player player, Board board) {
    }
}