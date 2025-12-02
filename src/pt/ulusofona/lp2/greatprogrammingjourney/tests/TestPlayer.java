package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerState;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.subtypes.Inheritance;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.subtypes.UnitTests;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.effect.PlayerEffect;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    private ArrayList<String> langs(String... ls) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : ls) {
            list.add(s);
        }
        return list;
    }

    @Test
    public void testConstructorsAndGettersAndCompareTo() {
        ArrayList<String> languages = langs("Java", "C");

        Player p1 = new Player(1, "Alice", languages, PlayerColor.BLUE);
        Player p2 = new Player(2, "Bob", languages, PlayerColor.RED, PlayerState.STUCK);

        assertEquals(1, p1.getId());
        assertEquals("Alice", p1.getName());
        assertEquals(PlayerColor.BLUE, p1.getColor());
        assertEquals(PlayerState.IN_GAME, p1.getState());

        assertEquals(2, p2.getId());
        assertEquals("Bob", p2.getName());
        assertEquals(PlayerColor.RED, p2.getColor());
        assertEquals(PlayerState.STUCK, p2.getState());

        List<String> copyLangs = p1.getLanguages();
        assertEquals(languages, copyLangs);
        copyLangs.add("Python");
        assertEquals(2, p1.getLanguages().size());

        List<Tool> toolsCopy = p1.getTools();
        assertTrue(toolsCopy.isEmpty());

        assertTrue(p1.compareTo(p2) < 0);
        assertEquals(0, p1.compareTo(p1));
    }

    @Test
    public void testLifeAndStates() {
        Player p = new Player(10, "X", langs("Java"), PlayerColor.GREEN);

        assertTrue(p.isAlive());
        p.defeat();
        assertFalse(p.isAlive());
        assertEquals(PlayerState.DEFEATED, p.getState());

        p = new Player(11, "Y", langs("Java"), PlayerColor.YELLOW);

        assertEquals(PlayerState.IN_GAME, p.getState());
        p.stuck(true);
        assertTrue(p.isStuck());
        assertEquals(PlayerState.STUCK, p.getState());
        p.stuck(false);
        assertFalse(p.isStuck());
        assertEquals(PlayerState.IN_GAME, p.getState());

        p.confused(true);
        assertTrue(p.isConfused());
        assertEquals(PlayerState.CONFUSED, p.getState());
        p.confused(false);
        assertFalse(p.isConfused());
        assertEquals(PlayerState.IN_GAME, p.getState());
    }

    @Test
    public void testToolsHasAddUseJoinTools() {
        Player p = new Player(3, "Tools", langs("Java"), PlayerColor.BLUE);

        assertFalse(p.hasTool(null));

        assertEquals("No tools", p.joinTools(","));

        Tool t1 = new Inheritance(0, "Herança", "inheritance.png");
        Tool t2 = new UnitTests(2, "Testes Unitários", "unit-tests.png");

        assertFalse(p.hasTool(t1));
        assertFalse(p.hasTool(t2));

        p.addTool(t1);
        assertTrue(p.hasTool(t1));
        assertFalse(p.hasTool(t2));

        p.addTool(t2);
        assertTrue(p.hasTool(t2));

        String joined = p.joinTools(",");
        assertTrue(joined.contains("Herança"));
        assertTrue(joined.contains("Testes Unitários"));
        assertTrue(joined.contains(","));

        p.useTool(t1);
        assertFalse(p.hasTool(t1));
        assertTrue(p.hasTool(t2));
    }

    @Test
    public void testJoinLanguagesEmptyAndSorted() {
        Player pEmpty = new Player(4, "Empty", new ArrayList<>(), PlayerColor.RED);
        assertEquals("", pEmpty.joinLanguages(";", false));
        assertEquals("", pEmpty.joinLanguages(";", true));

        ArrayList<String> langs = langs("Zlang", "alang");
        Player p = new Player(5, "Langs", langs, PlayerColor.GREEN);

        String noSort = p.joinLanguages(";", false);
        assertEquals("Zlang;alang", noSort);

        String sorted = p.joinLanguages(";", true);
        assertEquals("alang;Zlang", sorted);
    }

    @Test
    public void testEffectsUpdateAndCounter() {
        Player p = new Player(6, "Effects", langs("Java"), PlayerColor.BLUE);

        assertEquals(PlayerState.IN_GAME, p.getState());
        assertEquals(0, p.getEffectCounter());
        p.updateEffects();
        assertEquals(PlayerState.IN_GAME, p.getState());
        assertEquals(0, p.getEffectCounter());

        PlayerEffect effect = new PlayerEffect(PlayerState.CONFUSED, 2);
        p.setEffect(effect);

        assertEquals(PlayerState.CONFUSED, p.getState());
        assertTrue(p.getEffectCounter() > 0);

        p.updateEffects();
        assertEquals(PlayerState.CONFUSED, p.getState());
        assertEquals(1, p.getEffectCounter());

        p.updateEffects();
        assertEquals(PlayerState.IN_GAME, p.getState());
        assertEquals(0, p.getEffectCounter());

        p.updateEffects();
        assertEquals(PlayerState.IN_GAME, p.getState());
    }

    @Test
    public void testToStringCoversAllStates() {
        assertEquals("Em Jogo", PlayerState.IN_GAME.toString());
        assertEquals("Derrotado", PlayerState.DEFEATED.toString());
        assertEquals("Preso", PlayerState.STUCK.toString());
        assertEquals("Confuso", PlayerState.CONFUSED.toString());
    }

    @Test
    public void testPlayerEffect_startAndRunUntilDeactivate() {
        Player p = new Player(1, "A", new ArrayList<>(), PlayerColor.BLUE);

        // Aplica um efeito de CONFUSED com duração 2
        PlayerEffect effect = new PlayerEffect(PlayerState.CONFUSED, 2);
        p.setEffect(effect);

        assertTrue(effect.isActive());
        assertEquals(PlayerState.CONFUSED, p.getState());
        assertEquals(2, p.getEffectCounter());

        // 1ª atualização → ainda ativo
        p.updateEffects();
        assertEquals(PlayerState.CONFUSED, p.getState());
        assertEquals(1, p.getEffectCounter());
        assertTrue(effect.isActive());

        // 2ª atualização → termina aqui
        p.updateEffects();
        assertEquals(PlayerState.IN_GAME, p.getState()); // voltou ao normal
        assertEquals(0, p.getEffectCounter());
        assertFalse(effect.isActive());
    }

    @Test
    public void testPlayerEffect_zeroDurationBecomesInactiveImmediately() {
        Player p = new Player(2, "B", new ArrayList<>(), PlayerColor.RED);

        PlayerEffect effect = new PlayerEffect(PlayerState.STUCK, 0);
        p.setEffect(effect);

        assertFalse(effect.isActive());
        assertEquals(PlayerState.STUCK, p.getState());

        p.updateEffects();
        assertEquals(PlayerState.IN_GAME, p.getState());
    }

    @Test
    public void testPlayerEffect_runWhenInactiveReturnsInGame() {
        PlayerEffect effect = new PlayerEffect(PlayerState.STUCK, 0);

        assertFalse(effect.isActive());

        assertEquals(PlayerState.IN_GAME, effect.run());
    }

}