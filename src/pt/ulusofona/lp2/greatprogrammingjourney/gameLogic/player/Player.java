package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerState;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.effect.PlayerEffect;

import java.util.ArrayList;
import java.util.HashSet;

public class Player implements Comparable<Player> {

    // ============================== State ============================================================================

    private final int id;
    private final String name;
    private final ArrayList<String> languages;
    private final PlayerColor color;
    private PlayerState state;
    private final HashSet<Tool> tools;
    private PlayerEffect effect;

    // ========================================== Constructor ==========================================================

    public Player(int id, String name, ArrayList<String> languages, PlayerColor color) {
        this.id = id;
        this.name = name;
        this.languages = new ArrayList<>(languages);
        this.color = color;
        this.state = PlayerState.IN_GAME;
        this.tools = new HashSet<>();
    }

    public Player(int id, String name, ArrayList<String> languages, PlayerColor color, PlayerState state) {
        this(id, name, languages, color);
        this.state = state;
    }

    @Override
    public int compareTo(Player other) {
        return this.name.compareTo(other.name);
    }

    // ========================================= Getters ===============================================================

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getLanguages() {
        return new ArrayList<>(languages);
    }

    public ArrayList<Tool> getTools() {
        return new ArrayList<>(tools);
    }

    public PlayerColor getColor() {
        return color;
    }

    public PlayerState getState() {
        return state;
    }

    // ============================== Public Methods ================================================================

    public void updateEffects() {
        if (effect == null) {
            return;
        }

        state = effect.run();

        if (!effect.isActive()) {
            effect = null;
        }
    }

    public void setEffect(PlayerEffect effect) {
        this.effect = effect;
        this.state = effect.getStateToApply();
    }

    public boolean isAlive() {
        return state != PlayerState.DEFEATED;
    }

    public boolean isStuck() {
        return state == PlayerState.STUCK;
    }

    public boolean isConfused() {
        return state == PlayerState.CONFUSED;
    }

    public boolean hasTool(Tool tool) {
        return tool != null && tools.contains(tool);
    }

    public void defeat() {
        state = PlayerState.DEFEATED;
    }

    public void lock(boolean stuck) {
        if (stuck && state == PlayerState.IN_GAME) {
            state = PlayerState.STUCK;
        } else if (!stuck && state == PlayerState.STUCK) {
            state = PlayerState.IN_GAME;
        }
    }

    public void confused(boolean confused) {
        if (confused) {
            state = PlayerState.CONFUSED;
        } else {
            state = PlayerState.IN_GAME;
        }
    }

    public void addTool(Tool tool) {
        tools.add(tool);
    }

    public void useTool(Tool tool) {
        tools.remove(tool);
    }

    // ================================== String format ================================================================


    public String joinTools(String sep) {
        if (tools.isEmpty()) {
            return "No tools";
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Tool t : tools) {
            if (!first) {
                sb.append(sep);
            }
            sb.append(t.getName());
            first = false;
        }

        return sb.toString();
    }

    public String joinLanguages(String sep, boolean sort) {
        if (languages.isEmpty()) {
            return "";
        }

        ArrayList<String> list = new ArrayList<>(languages);

        if (sort) {
            list.sort(String.CASE_INSENSITIVE_ORDER);
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String lang : list) {
            if (!first) {
                sb.append(sep);
            }
            sb.append(lang);
            first = false;
        }

        return sb.toString();
    }

    public int getEffectCounter() {
        if (effect != null) {
            return effect.getTurnCounter();
        }
        return 0;
    }
}