package pt.ulusofona.lp2.greatprogrammingjourney.model.board;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.parser.PlayerParser;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.InputValidator;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

final class PlayerInitializer {

    private static final GameLogger LOG = new GameLogger(PlayerInitializer.class);

    // ============================== Constructor ======================================================================

    private PlayerInitializer() {
    }

    // ============================== Public API =======================================================================

    static boolean initializePlayers(Board board, String[][] playerInfo) {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(board);
        if (!boardOk.isValid()) {
            LOG.error("populate: " + boardOk.getMessage());
            return false;
        }

        ValidationResult playersOk = InputValidator.validatePlayerInfo(playerInfo);
        if (!playersOk.isValid()) {
            LOG.error("populate: " + playersOk.getMessage());
            return false;
        }

        LOG.info("populate: starting board population with " + playerInfo.length + " players");

        for (String[] info : playerInfo) {
            Player p = PlayerParser.createFromInput(info, board.getPlayers());

            ValidationResult playerOk = InputValidator.validatePlayerNotNull(p);
            if (!playerOk.isValid()) {
                LOG.warn("populate: " + playerOk.getMessage() +
                        " for input=" + String.join(",", info));
                return false;
            }

            if (!board.placePlayer(p)) {
                LOG.error("populate: could not place player=" + p.getName() + " on board");
                return false;
            }
        }

        LOG.info("populate: all players placed successfully");
        return true;
    }
}