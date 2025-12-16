package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

    private String[][] fourValidPlayers() {
        return new String[][]{
                {"1", "Alice", "Java", "BLUE"},
                {"2", "Bob", "C", "RED"},
                {"3", "Carol", "Python", "GREEN"},
                {"4", "Dave", "Kotlin", "PURPLE"}
        };
    }

    private GameManager newInitializedGame() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(fourValidPlayers(), 80));
        return gm;
    }

    // ===================== createInitialBoard =====================

    @Test
    public void test_create_initial_board_valid() {
        GameManager gm = new GameManager();
        assertTrue(gm.createInitialBoard(fourValidPlayers(), 80));
    }

    @Test
    public void test_create_initial_board_invalid_players() {
        GameManager gm = new GameManager();
        assertFalse(gm.createInitialBoard(null, 80));
        assertFalse(gm.createInitialBoard(new String[0][0], 80));
    }

    // ===================== getImagePng =====================

    @Test
    public void test_get_image_png_end_slot() {
        GameManager gm = newInitializedGame();
        assertEquals("glory.png", gm.getImagePng(80));
    }

    // ===================== getProgrammerInfo =====================

    @Test
    public void test_get_programmer_info() {
        GameManager gm = newInitializedGame();
        String[] info = gm.getProgrammerInfo(1);

        assertNotNull(info);
        assertEquals("1", info[0]);
    }

    @Test
    public void test_get_programmer_info_invalid_id() {
        GameManager gm = newInitializedGame();
        assertNull(gm.getProgrammerInfo(99));
    }

    // ===================== getProgrammerInfoAsStr =====================

    @Test
    public void test_get_programmer_info_as_str() {
        GameManager gm = newInitializedGame();
        String info = gm.getProgrammerInfoAsStr(1);

        assertNotNull(info);
        assertTrue(info.contains("Alice"));
    }

    // ===================== getProgrammersInfo =====================

    @Test
    public void test_get_programmers_info() {
        GameManager gm = newInitializedGame();
        String info = gm.getProgrammersInfo();
        assertNotNull(info);
        assertFalse(info.isBlank());
    }

    // ===================== getSlotInfo =====================

    @Test
    public void test_get_slot_info() {
        GameManager gm = newInitializedGame();
        String[] info = gm.getSlotInfo(1);
        assertNotNull(info);
    }

    // ===================== getCurrentPlayerID =====================

    @Test
    public void test_get_current_player_id() {
        GameManager gm = newInitializedGame();
        int id = gm.getCurrentPlayerID();
        assertTrue(id >= 1 && id <= 4);
    }

    // ===================== moveCurrentPlayer =====================

    @Test
    public void test_move_current_player_valid() {
        GameManager gm = newInitializedGame();
        assertTrue(gm.moveCurrentPlayer(1));
    }

    @Test
    public void test_move_current_player_invalid_dice() {
        GameManager gm = newInitializedGame();
        assertFalse(gm.moveCurrentPlayer(100));
    }

    @Test
    public void test_move_current_player_negative_cheat() {
        GameManager gm = newInitializedGame();
        assertTrue(gm.moveCurrentPlayer(-2));
    }

    // ===================== reactToAbyssOrTool =====================

    @Test
    public void test_react_to_abyss_or_tool_no_object() {
        GameManager gm = newInitializedGame();

        gm.moveCurrentPlayer(1);
        String result = gm.reactToAbyssOrTool();

        assertNull(result);
    }

    // ===================== gameIsOver =====================

    @Test
    public void test_game_is_over_false() {
        GameManager gm = newInitializedGame();
        assertFalse(gm.gameIsOver());
    }

    // ===================== getGameResults =====================

    @Test
    public void test_get_game_results_no_winner() {
        GameManager gm = newInitializedGame();
        ArrayList<String> results = gm.getGameResults();
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    // ===================== saveGame =====================

    @Test
    public void test_save_game() throws Exception {
        GameManager gm = newInitializedGame();

        File tmp = File.createTempFile("game", ".txt");
        tmp.deleteOnExit();

        assertTrue(gm.saveGame(tmp));
    }

    // ===================== loadGame =====================

    @Test
    public void test_load_game() throws Exception {
        GameManager gm = new GameManager();

        File save = new File("test-files/AbyssEffect_Crash");
        gm.loadGame(save);

        assertNotNull(gm.getProgrammerInfo(1));
    }

    // ===================== getAuthorsPanel =====================

    @Test
    public void test_get_authors_panel() {
        GameManager gm = new GameManager();
        JPanel panel = gm.getAuthorsPanel();
        assertNotNull(panel);
    }

    // ===================== customizeBoard =====================

    @Test
    public void test_customize_board() {
        GameManager gm = new GameManager();
        HashMap<String, String> theme = gm.customizeBoard();

        assertNotNull(theme);
        assertTrue(theme.containsKey("logoImage"));
        assertTrue(theme.containsKey("hasNewAbyss"));
        assertTrue(theme.containsKey("hasNewTool"));
    }
}