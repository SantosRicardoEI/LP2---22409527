package pt.ulusofona.lp2.greatprogrammingjourney.model.player;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.Tool;

import java.util.ArrayList;
import java.util.HashSet;

import static pt.ulusofona.lp2.greatprogrammingjourney.utils.StringUtils.capitalize;

public class Player {

    // ============================== State ============================================================================
    private final int id;
    private final String name;
    private final ArrayList<String> languages;
    private final PlayerColor color;
    private int lives;
    private PlayerState state;
    private HashSet<Tool> tools;

    // ============================== Constructor ======================================================================

    public Player(int id, String name, ArrayList<String> languages, PlayerColor color) {
        this.id = id;
        this.name = name;
        this.languages = languages;
        this.color = color;
        this.lives = GameConfig.PLAYER_STARTING_LIVES;
        this.state = PlayerState.IN_GAME;
        this.tools = new HashSet();
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

    public boolean isStuck() {
        return state == PlayerState.STUCK;
    }

    public void setStuck(boolean stuck) {
        if (stuck && state == PlayerState.IN_GAME) {
            state = PlayerState.STUCK;
        } else if (!stuck && state == PlayerState.STUCK) {
            state = PlayerState.IN_GAME;
        }
    }

    public ArrayList<Tool> getTools() {
        return new ArrayList<>(tools);
    }

    public boolean isAlive() {
        return state != PlayerState.DEFEATED;
    }

    public boolean hasTool(Tool tool) {
        for (Tool t : tools) {
            if (t.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean takeLife() {
        if (lives == 0) {
            return false;
        }
        lives--;
        if (lives == 0) {
            kill();
        }
        return true;
    }

    public void kill() {
        state = PlayerState.DEFEATED;
        lives = 0;
    }

    public void addTool(Tool tool) {
        tools.add(tool);
    }

    public void removeTool(Tool tool) {
        tools.remove(tool);
    }
}