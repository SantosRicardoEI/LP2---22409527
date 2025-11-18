package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

import java.util.List;

public interface AbyssContext {

    // ===================== Posição atual / movimento =====================

    int getPosition(Player player);

    void movePlayerTo(Player player, int newPos);

    void movePlayerRelative(Player player, int delta);

    void sendPlayerToStart(Player player);


    // ===================== Histórico de posições / jogadas =====================

    int getPreviousPosition(Player player);

    int getPositionNMovesAgo(Player player, int n);

    int getLastDiceRoll(Player player);


    // ===================== Estado especial (preso, etc.) =====================

    boolean isPlayerStuck(Player player);

    void setPlayerStuck(Player player, boolean stuck);


    // ===================== Informação sobre outros jogadores =====================

    List<Player> getPlayersAt(int position);

    Player getOtherPlayerAtSamePosition(Player player);
}