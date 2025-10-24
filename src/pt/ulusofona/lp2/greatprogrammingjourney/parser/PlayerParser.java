package pt.ulusofona.lp2.greatprogrammingjourney.parser;

import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.InputValidator;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

import java.util.ArrayList;
import java.util.List;

public class PlayerParser {

    // ============================== State ============================================================================
    private static final GameLogger LOG = new GameLogger(PlayerParser.class);

    // ============================== Constructor ======================================================================

    private PlayerParser() {
    }

    // ============================== Public API =======================================================================

    public static Player createPlayerFromInput(String[] info, List<Player> players) {
        ValidationResult playersOk = InputValidator.validatePlayerList(players);
        if (!playersOk.isValid()) {
            LOG.error("createPlayerFromInput: " + playersOk.getMessage());
            return null;
        }

        ValidationResult infoOk = InputValidator.validatePlayerInfoLine(info);
        if (!infoOk.isValid()) {
            LOG.error("createPlayerFromInput: " + infoOk.getMessage());
            return null;
        }

        String stringID = info[0].trim();
        String name = info[1].trim();
        String langsRaw = info[2].trim();
        String stringColor = info[3].trim();

        if (name.isEmpty()) {
            LOG.warn("createPlayerFromInput: rejected — empty name");
            return null;
        }

        int id;
        try {
            id = Integer.parseInt(stringID);
        } catch (NumberFormatException e) {
            LOG.warn("createPlayerFromInput: rejected — invalid ID format (" + stringID + ")");
            return null;
        }

        if (id < 0) {
            LOG.warn("createPlayerFromInput: rejected — negative ID (" + id + ")");
            return null;
        }

        ArrayList<String> langsList = new ArrayList<>();
        for (String token : langsRaw.split(";")) {
            String t = token.trim();
            if (!t.isEmpty()) {
                langsList.add(t);
            }
        }

        if (langsList.isEmpty()) {
            LOG.warn("createPlayerFromInput: rejected — empty or invalid language list");
            return null;
        }

        PlayerColor color = PlayerColor.from(stringColor);
        if (color == null) {
            LOG.warn("createPlayerFromInput: rejected — invalid color (" + stringColor + ")");
            return null;
        }

        if (!isUnique(players, id, color)) {
            return null;
        }

        Player newPlayer = new Player(id, name, langsList, color);
        LOG.info("createPlayerFromInput: player created — id=" + id + ", name=" + name + ", color=" + color);
        return newPlayer;
    }

    // ============================== Helper Methods ===================================================================

    private static boolean isUnique(List<Player> players, int id, PlayerColor color) {
        for (Player p : players) {
            if (p.getId() == id) {
                LOG.warn("isUnique: duplicate player ID (" + id + ")");
                return false;
            }
            if (p.getColor() == color) {
                LOG.warn("isUnique: duplicate player color (" + color + ")");
                return false;
            }
        }
        return true;
    }

}