package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerState;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.tool.Tool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

import static pt.ulusofona.lp2.greatprogrammingjourney.utils.StringUtils.capitalize;

public class Player implements Comparable<Player> {

    // ============================== State ============================================================================
    private final int id;
    private final String name;
    private final ArrayList<String> languages;
    private final PlayerColor color;
    private PlayerState state;
    private HashSet<Tool> tools;

    // ============================== Constructor ======================================================================

    public Player(int id, String name, ArrayList<String> languages, PlayerColor color) {
        this.id = id;
        this.name = name;
        this.languages = languages;
        this.color = color;
        this.state = PlayerState.IN_GAME;
        this.tools = new HashSet();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Player p = (Player) o;
        return id == p.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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


    public void kill() {
        state = PlayerState.DEFEATED;
    }

    public void lock(boolean stuck) {
        if (stuck && state == PlayerState.IN_GAME) {
            state = PlayerState.STUCK;
        } else if (!stuck && state == PlayerState.STUCK) {
            state = PlayerState.IN_GAME;
        }
    }

    public void addTool(Tool tool) {
        tools.add(tool);
    }

    public void useTool(Tool tool) {
        tools.remove(tool);
    }

    public String getToolsAsStr() {
        String toolsStr = "";

        if (tools.isEmpty()) {
            return "No tools";
        }

        boolean first = true;
        for (Tool tool : tools) {
            if (first) {
                toolsStr += tool.getName();
                first = false;
            } else {
                toolsStr += ", " + tool.getName();
            }
        }
        return toolsStr;
    }

    @Override
    public int compareTo(Player other) {
        return this.name.compareTo(other.name);
    }
}