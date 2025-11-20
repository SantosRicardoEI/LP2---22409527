package pt.ulusofona.lp2.greatprogrammingjourney.parser;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.InteractableType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerState;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.InputValidator;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    // ================================================== Player Parsing ===============================================

    public static Player parsePlayer(String[] info, List<Player> players) {
        if (!validatePlayersList(players) || !validateInfoLine(info)) {
            throw new IllegalArgumentException("Invalid player input line");
        }

        String stringID = info[0].trim();
        String name = info[1].trim();
        String langsRaw = info[2].trim();
        String stringColor = (info.length >= 4) ? info[3].trim() : null;

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Empty name");
        }

        int id = Parser.parseID(stringID);

        ArrayList<String> langsList = Parser.parseLanguages(langsRaw);
        if (langsList.isEmpty()) {
            throw new IllegalArgumentException("Empty or invalid language list");
        }

        PlayerColor color = resolveColor(stringColor, players);

        if (!areIdAndColorUnique(players, id, color)) {
            throw new IllegalArgumentException("Duplicate id or color");
        }

        Player newPlayer = new Player(id, name, langsList, color);
        return newPlayer;
    }

    // ========================================== Player Parsing Helpers ===============================================

    private static boolean areIdAndColorUnique(List<Player> players, int id, PlayerColor color) {
        for (Player p : players) {
            if (p.getId() == id) {
                return false;
            }
            if (p.getColor() == color) {
                return false;
            }
        }
        return true;
    }

    private static PlayerColor resolveColor(String stringColor, List<Player> players) {
        if (stringColor != null && !stringColor.isBlank()) {
            try {
                return Parser.parseColor(stringColor);
            } catch (IllegalArgumentException e) {
                throw e;
            }
        }

        PlayerColor auto = pickAutomaticColor(players);
        if (auto == null) {
            throw new IllegalArgumentException("No colors available");
        }

        return auto;
    }

    private static PlayerColor pickAutomaticColor(List<Player> players) {
        boolean[] used = new boolean[PlayerColor.values().length];
        for (Player p : players) {
            if (p == null || p.getColor() == null) {
                continue;
            }
            used[p.getColor().ordinal()] = true;
        }

        for (PlayerColor c : PlayerColor.values()) {
            if (!used[c.ordinal()]) {
                return c;
            }
        }
        return null;
    }

    // ================================================== General Parsing ==============================================

    public static int parseInt(String raw) {
        if (raw == null) {
            throw new NumberFormatException();
        }

        raw = raw.trim();
        if (raw.isEmpty()) {
            throw new NumberFormatException();
        }

        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public static int parsePosition(String posStr) {
        int pos = parseInt(posStr);

        if (pos < 1) {
            throw new NumberFormatException();
        }

        return pos;
    }

    public static int parseID(String idStr) {
        int id = parseInt(idStr);

        if (id < 0) {
            throw new NumberFormatException();
        }

        return id;
    }

    public static int parseDice(String diceStr) {
        int dice = parseInt(diceStr);

        if (dice < GameConfig.MIN_DICE || dice > GameConfig.MAX_DICE) {
            throw new NumberFormatException();
        }

        return dice;
    }

    public static PlayerColor parseColor(String color) {
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException();
        }

        String normalized = color.trim().toUpperCase();

        try {
            return PlayerColor.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }

    public static PlayerState parseState(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException();
        }

        String normalized = s.trim().toUpperCase();

        try {
            return PlayerState.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }


    public static ArrayList<String> parseLanguages(String langsRaw) {
        ArrayList<String> langsList = new ArrayList<>();
        for (String token : langsRaw.split(";")) {
            String t = token.trim();
            if (!t.isEmpty()) {
                langsList.add(t);
            }
        }
        return langsList;
    }

    // ============================================ Interactable Parsing ===============================================

    public static Interactable parseInteractable(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException();
        }

        String[] parts = raw.trim().split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException();
        }

        int typeID = parseID(parts[0].trim());
        int subType = parseID(parts[1].trim());

        Interactable interactable = InteractableType.createInteractable(typeID, subType);
        if (interactable == null) {
            throw new IllegalArgumentException();
        }

        return interactable;
    }

    public static Interactable parseInteractable(String[] parts) {
        if (parts == null || parts.length != 2) {
            throw new IllegalArgumentException();
        }

        String raw = parts[0].trim() + ":" + parts[1].trim();
        return parseInteractable(raw);
    }

    // ============================================== Validation Helpers ===============================================


    private static boolean validatePlayersList(List<Player> players) {
        ValidationResult playersOk = InputValidator.validatePlayerList(players);
        if (!playersOk.isValid()) {
            return false;
        }
        return true;
    }

    private static boolean validateInfoLine(String[] info) {
        ValidationResult infoOk = InputValidator.validatePlayerInfoLine(info);
        if (!infoOk.isValid()) {
            return false;
        }
        return true;
    }
}
