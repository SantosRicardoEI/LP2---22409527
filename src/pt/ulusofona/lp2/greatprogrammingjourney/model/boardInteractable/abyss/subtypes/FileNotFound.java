package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class FileNotFound extends Abyss {

    public FileNotFound() {
        super(AbyssSubType.FILE_NOT_FOUND);
    }

    @Override
    public String abyssFallMessage() {
        return "File Not Found! Programador recua 3 casas!";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        // Jogador recua 3 casas
        ctx.movePlayerRelative(player, -3);
    }
}