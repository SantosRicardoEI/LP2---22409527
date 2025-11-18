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

    public static Player createFromInput(String[] info, List<Player> players) {
        if (!validatePlayersList(players) || !validateInfoLine(info)) {
            return null;
        }

        String stringID   = info[0].trim();
        String name       = info[1].trim();
        String langsRaw   = info[2].trim();
        String stringColor = (info.length >= 4) ? info[3].trim() : null;

        if (name.isEmpty()) {
            LOG.warn("createPlayerFromInput: rejected — empty name");
            return null;
        }

        Integer id = parseId(stringID);
        if (id == null) {
            return null;
        }

        ArrayList<String> langsList = parseLanguages(langsRaw);
        if (langsList.isEmpty()) {
            LOG.warn("createPlayerFromInput: rejected — empty or invalid language list");
            return null;
        }

        PlayerColor color = resolveColor(stringColor, players);
        if (color == null) {
            // resolveColor já faz o log certo
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

    private static PlayerColor pickAutomaticColor(List<Player> players) {
        boolean[] used = new boolean[PlayerColor.values().length];
        for (Player p : players) {
            if (p == null || p.getColor() == null) continue;
            used[p.getColor().ordinal()] = true;
        }

        for (PlayerColor c : PlayerColor.values()) {
            if (!used[c.ordinal()]) {
                return c;
            }
        }
        return null;
    }

    private static boolean validatePlayersList(List<Player> players) {
        ValidationResult playersOk = InputValidator.validatePlayerList(players);
        if (!playersOk.isValid()) {
            LOG.error("createPlayerFromInput: " + playersOk.getMessage());
            return false;
        }
        return true;
    }

    private static boolean validateInfoLine(String[] info) {
        ValidationResult infoOk = InputValidator.validatePlayerInfoLine(info);
        if (!infoOk.isValid()) {
            LOG.error("createPlayerFromInput: " + infoOk.getMessage());
            return false;
        }
        return true;
    }

    private static Integer parseId(String stringID) {
        try {
            int id = Integer.parseInt(stringID);
            if (id < 0) {
                LOG.warn("createPlayerFromInput: rejected — negative ID (" + id + ")");
                return null;
            }
            return id;
        } catch (NumberFormatException e) {
            LOG.warn("createPlayerFromInput: rejected — invalid ID format (" + stringID + ")");
            return null;
        }
    }

    private static ArrayList<String> parseLanguages(String langsRaw) {
        ArrayList<String> langsList = new ArrayList<>();
        for (String token : langsRaw.split(";")) {
            String t = token.trim();
            if (!t.isEmpty()) {
                langsList.add(t);
            }
        }
        return langsList;
    }

    private static PlayerColor resolveColor(String stringColor, List<Player> players) {
        if (stringColor != null && !stringColor.isEmpty()) {
            PlayerColor color = PlayerColor.from(stringColor);
            if (color == null) {
                LOG.warn("createPlayerFromInput: rejected — invalid color (" + stringColor + ")");
                return null;
            }
            return color;
        }

        PlayerColor auto = pickAutomaticColor(players);
        if (auto == null) {
            LOG.warn("createPlayerFromInput: rejected — no available colors left");
            return null;
        }
        return auto;
    }

}