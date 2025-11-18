package pt.ulusofona.lp2.greatprogrammingjourney.validator;

import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;

import java.util.List;

public final class InputValidator {
    private InputValidator() {
    }

    public static ValidationResult validatePlayerNotNull(Player p) {
        return (p == null) ? ValidationResult.fail("player is null") : ValidationResult.ok();
    }

    public static ValidationResult validateInteractableNotNull(Interactable interactable) {
        return (interactable == null) ? ValidationResult.fail("interactable is null") : ValidationResult.ok();
    }

    public static ValidationResult validatePlayerInfo(String[][] players) {
        if (players == null) {
            return ValidationResult.fail("playerInfo is null");
        }
        if (players.length == 0) {
            return ValidationResult.fail("playerInfo is empty");
        }
        return ValidationResult.ok();
    }

    public static ValidationResult validateInteractableInfo(String[][] abyssesAndTools) {
        if (abyssesAndTools == null) {
            return ValidationResult.fail("abyssAndTools is null");
        }
        return ValidationResult.ok();
    }

    public static ValidationResult validateBoardInitialized(Board b) {
        return (b == null || b.getSize() < 1)
                ? ValidationResult.fail("board not initialized")
                : ValidationResult.ok();
    }

    public static ValidationResult validatePosition(int pos, int boardSize) {
        if (pos < 1 || pos > boardSize) {
            return ValidationResult.fail("invalid position=" + pos + " (valid 1.." + boardSize + ")");
        }
        return ValidationResult.ok();
    }

    public static ValidationResult validatePlayerExists(Board board, int playerId) {
        if (board == null) {
            return ValidationResult.fail("board not initialized");
        }
        return (board.getPlayer(playerId) == null)
                ? ValidationResult.fail("player id=" + playerId + " not found")
                : ValidationResult.ok();
    }

    public static ValidationResult validateSlot(int pos, int max) {
        return (pos < 1 || pos > max)
                ? ValidationResult.fail("invalid slot=" + pos + " (valid 1.." + max + ")")
                : ValidationResult.ok();
    }

    public static ValidationResult validatePlayerOnBoard(Board b, Player p) {
        if (!validatePlayerNotNull(p).isValid()) {
            return ValidationResult.fail("player is null");
        }
        int pos = b.getPlayerPosition(p);
        return (pos < 1) ? ValidationResult.fail("player id=" + p.getId() + " not on board") : ValidationResult.ok();
    }

    public static ValidationResult validatePlayerList(List<Player> players) {
        if (players == null) {
            return ValidationResult.fail("players list is null");
        }
        return ValidationResult.ok();
    }

    public static ValidationResult validatePlayerInfoLine(String[] info) {
        if (info == null) {
            return ValidationResult.fail("info array is null");
        }
        if (info.length != 4 && info.length != 3) {
            return ValidationResult.fail("invalid info length — expected 4 or 3 fields, got " + (info == null ? "null" : info.length));
        }
        return ValidationResult.ok();
    }
}