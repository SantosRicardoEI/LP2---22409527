package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.parser.Parser;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

public class BoardInitializer {

    private static final GameLogger LOG = new GameLogger(BoardInitializer.class);

    static boolean initializePlayers(Board board, String[][] playerInfo) {
        if (board == null) {
            LOG.error("initializePlayers: " + "board is null");
            return false;
        }

        if (playerInfo == null || playerInfo.length == 0) {
            LOG.error("initializePlayers: " + "invalid player info");
            return false;
        }

        LOG.info("initializePlayers: starting board population with " + playerInfo.length + " players");

        for (String[] info : playerInfo) {
            Player p;
            try {
                p = Parser.parsePlayer(info, board.getPlayers());
            } catch (IllegalArgumentException e) {
                LOG.warn("initializePlayers: rejected — invalid player for input=" + String.join(",", info));
                return false;
            }

            if (!board.placePlayer(p, GameConfig.INITIAL_POSITION)) {
                LOG.error("initializePlayers: could not place player=" + p.getName() + " on board");
                return false;
            }
        }

        LOG.info("initializePlayers: all players placed successfully");
        return true;
    }

    public static boolean initializeInteractables(Board board, String[][] abyssesAndTools) {

        if (abyssesAndTools == null || abyssesAndTools.length == 0) {
            LOG.info("placeInteractables: no interactables to place");
            return true;
        }

        LOG.info("placeInteractables: starting board population with " + abyssesAndTools.length + " interactables");

        for (String[] line : abyssesAndTools) {

            if (line == null || line.length < 3) {
                LOG.warn("placeInteractables: rejected — invalid line (expected 3 fields) ");
                return false;
            }

            Interactable interactable;
            int pos;

            try {
                interactable = Parser.parseInteractable(new String[]{ line[0], line[1] });

                pos = Parser.parsePosition(line[2]);
            } catch (IllegalArgumentException e) {
                LOG.warn("placeInteractables: rejected — invalid interactable data for input=" +
                        String.join(",", line));
                return false;
            }

            if (!board.placeInteractable(interactable, pos)) {
                LOG.error("placeInteractables: could not place interactable=" +
                        interactable.getName() + " at pos=" + pos);
                return false;
            }
        }

        LOG.info("placeInteractables: all interactables placed successfully");
        return true;
    }
}
