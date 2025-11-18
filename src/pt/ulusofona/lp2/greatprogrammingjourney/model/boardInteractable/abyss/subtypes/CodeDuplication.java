package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

public class CodeDuplication extends Abyss {

    public CodeDuplication() {
        super(AbyssSubType.DUPLICATED_CODE);
    }

    @Override
    public String abyssFallMessage() {
        return "Code Duplication! Programador volta para a posição anterior!";
    }

    @Override
    public void affectPlayer(Player player, AbyssContext ctx) {
        // Jogador recua para a casa onde estava antes de chegar á casa atual
        // (Volta para posiçao anterior no historico)
        int previousPos = ctx.getPreviousPosition(player);
        ctx.movePlayerTo(player, previousPos);
    }
}
