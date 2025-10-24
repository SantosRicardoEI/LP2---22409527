package pt.ulusofona.lp2.greatprogrammingjourney.model.player;

import java.util.ArrayList;

import static pt.ulusofona.lp2.greatprogrammingjourney.utils.StringUtils.capitalize;

public class Player {

    // ============================== State ============================================================================
    private final int id;
    private final String name;
    private final ArrayList<String> languages;
    private final PlayerColor color;
    private PlayerState state;

    // ============================== Constructor ======================================================================

    public Player(int id, String name, ArrayList<String> languages, PlayerColor color) {
        this.id = id;
        this.name = name;
        this.languages = languages;
        this.color = color;
        this.state = PlayerState.IN_GAME;
    }

    // ============================== Getters & Setters ================================================================

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getLanguages() {
        return new ArrayList<>(languages);
    }

    public ArrayList<String> getSortedLangs() {
        ArrayList<String> sorted = new ArrayList<>(languages);
        sorted.sort(String.CASE_INSENSITIVE_ORDER);
        return sorted;
    }

    public PlayerColor getColor() {
        return color;
    }

    public String getColorAsStr() {
        return capitalize(color.toString());
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }
}