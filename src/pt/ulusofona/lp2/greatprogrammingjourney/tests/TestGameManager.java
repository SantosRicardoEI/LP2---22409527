package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.GameManager;
import pt.ulusofona.lp2.greatprogrammingjourney.GameNotInitializedException;
import pt.ulusofona.lp2.greatprogrammingjourney.InvalidGameStateException;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.TurnManager;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.parser.Parser;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.Credits;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void test_get_game_results_with_winner() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "Alice", "Java", "BLUE"},
                {"2", "Bob", "Java", "RED"},
                {"3", "Carol", "Java", "GREEN"},
                {"4", "Dave", "Java", "PURPLE"}
        };

        assertTrue(gm.createInitialBoard(players, 80));

        int safety = 5000;
        while (!gm.gameIsOver() && safety-- > 0) {
            int currentId = gm.getCurrentPlayerID();
            int pos = Integer.parseInt(gm.getProgrammerInfo(currentId)[4]);
            int remaining = 80 - pos;

            if (remaining <= 0) {
                break;
            }

            int roll = Math.min(6, remaining);
            if (roll < 1) {
                roll = 1;
            }

            assertTrue(gm.moveCurrentPlayer(roll));
            gm.reactToAbyssOrTool();
        }

        assertTrue(gm.gameIsOver(), "O jogo devia terminar com um vencedor.");

        ArrayList<String> results = gm.getGameResults();
        assertNotNull(results);
        assertFalse(results.isEmpty());

        assertEquals("THE GREAT PROGRAMMING JOURNEY", results.get(0));
        assertTrue(results.contains("NR. DE TURNOS"));
        assertTrue(results.contains("VENCEDOR"));
        assertTrue(results.contains("RESTANTES"));

        int idxWinner = results.indexOf("VENCEDOR");
        assertTrue(idxWinner >= 0 && idxWinner + 1 < results.size());

        String winnerName = results.get(idxWinner + 1);
        assertTrue(
                winnerName.equals("Alice") || winnerName.equals("Bob") || winnerName.equals("Carol") || winnerName.equals("Dave"),
                "Vencedor inesperado: " + winnerName
        );

        int idxRest = results.indexOf("RESTANTES");
        assertTrue(idxRest >= 0);
        int remainingLines = results.size() - (idxRest + 1);
        assertEquals(3, remainingLines);
    }

    @Test
    public void test_createInitialBoard_returnsFalse_when_playerInfo_null_or_empty() {
        GameManager gm = new GameManager();

        assertFalse(gm.createInitialBoard(null, 80, new String[0][0]));
        assertFalse(gm.createInitialBoard(new String[0][0], 80, new String[0][0]));
    }

    @Test
    public void test_createInitialBoard_returnsFalse_when_invalid_player_count() {
        GameManager gm = new GameManager();

        String[][] players3 = {
                {"1", "A", "Java", "BLUE"},
                {"2", "B", "Java", "RED"},
                {"3", "C", "Java", "GREEN"}
        };

        assertFalse(gm.createInitialBoard(players3, 2, new String[0][0]));
    }

    @Test
    public void test_createInitialBoard_returnsFalse_when_invalid_world_size_for_4_players() {
        GameManager gm = new GameManager();

        String[][] players4 = {
                {"1", "A", "Java", "BLUE"},
        };

        assertFalse(gm.createInitialBoard(players4, 20, new String[0][0]));
    }

    @Test
    public void test_createInitialBoard_returnsFalse_when_initializeNewBoard_fails() {
        GameManager gm = new GameManager();

        String[][] players4 = {
                {"1", "A", "Java", "BLUE"},
                {"2", "B", "Java", "RED"},
                {"3", "C", "Java", "GREEN"},
                {"4", "D", "Java", "PURPLE"}
        };

        String[][] badMapObjects = {
                {"P", "4"}
        };

        assertFalse(gm.createInitialBoard(players4, 80, badMapObjects));
    }

    @Test
    public void test_moveCurrentPlayer_assembly_cannot_move_more_than_2() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "A", "Assembly", "BLUE"},
                {"2", "B", "Java", "RED"},
                {"3", "C", "Java", "GREEN"},
                {"4", "D", "Java", "PURPLE"}
        };

        assertTrue(gm.createInitialBoard(players, 80));

        assertFalse(gm.moveCurrentPlayer(3));
    }

    @Test
    public void test_moveCurrentPlayer_c_cannot_move_more_than_3() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "A", "C", "BLUE"},
                {"2", "B", "Java", "RED"},
                {"3", "C", "Java", "GREEN"},
                {"4", "D", "Java", "PURPLE"}
        };

        assertTrue(gm.createInitialBoard(players, 80));

        assertFalse(gm.moveCurrentPlayer(4));
    }

    @Test
    public void test_moveCurrentPlayer_invalid_dice_returns_false() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "A", "Java", "BLUE"},
                {"2", "B", "Java", "RED"},
                {"3", "C", "Java", "GREEN"},
                {"4", "D", "Java", "PURPLE"}
        };

        assertTrue(gm.createInitialBoard(players, 80));
        assertFalse(gm.moveCurrentPlayer(100));
    }

    @Test
    public void test_moveCurrentPlayer_negative_cheat_is_accepted() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1", "A", "Java", "BLUE"},
                {"2", "B", "Java", "RED"},
                {"3", "C", "Java", "GREEN"},
                {"4", "D", "Java", "PURPLE"}
        };

        assertTrue(gm.createInitialBoard(players, 80));
        assertTrue(gm.moveCurrentPlayer(-2)); // vira +2
    }

    @Test
    public void test_methods_throw_when_not_initialized() {
        GameManager gm = new GameManager();

        assertThrows(GameNotInitializedException.class, () -> gm.getImagePng(1));
        assertThrows(GameNotInitializedException.class, () -> gm.getProgrammerInfo(1));
        assertThrows(GameNotInitializedException.class, () -> gm.getProgrammerInfoAsStr(1));
        assertThrows(GameNotInitializedException.class, gm::getProgrammersInfo);
        assertThrows(GameNotInitializedException.class, () -> gm.getSlotInfo(1));
        assertThrows(GameNotInitializedException.class, gm::getCurrentPlayerID);
        assertThrows(GameNotInitializedException.class, () -> gm.moveCurrentPlayer(1));
        assertThrows(GameNotInitializedException.class, gm::reactToAbyssOrTool);
        assertThrows(GameNotInitializedException.class, gm::gameIsOver);
        assertThrows(GameNotInitializedException.class, gm::getGameResults);
        assertThrows(GameNotInitializedException.class, () -> gm.saveGame(new File("x")));
    }

    @Test
    public void test_createInitialBoard_with_4_players_without_color() {
        GameManager gm = new GameManager();

        String[][] playersWithoutColor = {
                {"1", "Alice", "Java"},
                {"2", "Bob", "C"},
                {"3", "Carol", "Python"},
                {"4", "Dave", "Java"}
        };

        boolean result = gm.createInitialBoard(playersWithoutColor, 80);

        assertTrue(result);

        assertNotNull(gm.getProgrammerInfo(1));
        assertNotNull(gm.getProgrammerInfo(2));
        assertNotNull(gm.getProgrammerInfo(3));
        assertNotNull(gm.getProgrammerInfo(4));
    }

    @Test
    public void test_credits_panel_paint_component_runs() {
        JPanel panel = Credits.buildPanel();
        panel.setSize(400, 200);

        BufferedImage img = new BufferedImage(400, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        assertDoesNotThrow(() -> panel.paint(g2));

        g2.dispose();
    }

    @Test
    void test_boardSize_throws_when_board_null() throws Exception {
        GameManager gm = new GameManager();

        var m = GameManager.class.getDeclaredMethod("boardSize");
        m.setAccessible(true);

        assertThrows(GameNotInitializedException.class, () -> {
            try { m.invoke(gm); }
            catch (InvocationTargetException e) { throw (RuntimeException) e.getCause(); }
        });
    }

    static void setField(Object target, String name, Object value) throws Exception {
        var f = target.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(target, value);
    }

    static class BoardWithWinner extends Board {
        private final Player winner;
        private final ArrayList<Player> players;

        BoardWithWinner(int size, Player winner, ArrayList<Player> players) {
            super(size);
            this.winner = winner;
            this.players = players;
        }

        @Override public Player getWinner() { return winner; }
        @Override public ArrayList<Player> getPlayers() { return players; }
        @Override public int getPlayerPosition(Player p) { return 1; }
    }

    @Test
    void test_getGameResults_throws_when_turnCount_negative() throws Exception {
        GameManager gm = new GameManager();

        Player p = new Player(1,"Alice",new ArrayList<>(), PlayerColor.BROWN);
        ArrayList<Player> player1 = new ArrayList<>();
        player1.add(p);
        Board b = new BoardWithWinner(80, p, player1);

        setField(gm, "board", b);
        setField(gm, "turnManager", new TurnManager(1, -1));

        assertThrows(IllegalArgumentException.class, gm::getGameResults);
    }

    static class BoardNullPlayer extends Board {
        BoardNullPlayer(int size) { super(size); }
        @Override public Player getPlayer(int id) { return null; }
    }

    @Test
    void test_reactToAbyssOrTool_throws_when_current_player_not_found() throws Exception {
        GameManager gm = new GameManager();

        setField(gm, "board", new BoardNullPlayer(80));
        setField(gm, "turnManager", new TurnManager(1, 0));

        assertThrows(InvalidGameStateException.class, gm::reactToAbyssOrTool);
    }

    @Test
    void test_moveCurrentPlayer_throws_when_current_player_not_found() throws Exception {
        GameManager gm = new GameManager();

        setField(gm, "board", new BoardNullPlayer(80));
        setField(gm, "turnManager", new TurnManager(1, 0));

        assertThrows(InvalidGameStateException.class, () -> gm.moveCurrentPlayer(1));
    }

    @Test
    void test_getProgrammerInfoAsStr_returns_null_when_player_not_found() throws Exception {
        GameManager gm = new GameManager();

        setField(gm, "board", new BoardNullPlayer(80));

        assertNull(gm.getProgrammerInfoAsStr(999));
    }

    @Test
    void test_getImagePng_returns_png_when_slot_has_map_object() {
        GameManager gm = new GameManager();

        String[][] players = {
                {"1","P1","Java","BLUE"},
                {"2","P2","Java","BROWN"},
                {"3","P3","Java","PURPLE"},
                {"4","P4","Java","GREEN"},
        };

        String[][] mapObjects = {
                {"1", "4", "3"}
        };

        assertTrue(gm.createInitialBoard(players, 80, mapObjects));

        String png = gm.getImagePng(3);
        assertNotNull(png);
    }

    private static ArrayList<Player> emptyPlayers() {
        return new ArrayList<>();
    }

    @Test
    void parsePlayer_throws_when_info_null() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePlayer(null, emptyPlayers())
        );
        assertEquals("Invalid player input line", ex.getMessage());
    }

    @Test
    void parsePlayer_throws_when_info_length_invalid() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePlayer(new String[]{"1", "Ana"}, emptyPlayers()) // length 2
        );
        assertEquals("Invalid player input line", ex.getMessage());
    }

    @Test
    void parsePlayer_throws_when_name_empty() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePlayer(new String[]{"1", "   ", "Java", "BLUE"}, emptyPlayers())
        );
        assertEquals("Empty name", ex.getMessage());
    }

    @Test
    void parsePlayer_throws_when_languages_empty_or_invalid() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePlayer(new String[]{"1", "Ana", "   ;  ;   ", "BLUE"}, emptyPlayers())
        );
        assertEquals("Empty or invalid language list", ex.getMessage());
    }

    @Test
    void parsePlayer_auto_assigns_color_when_missing() {
        ArrayList<Player> players = emptyPlayers();

        Player p = Parser.parsePlayer(new String[]{"1", "Ana", "Java;Kotlin"}, players);

        assertNotNull(p);
        assertNotNull(p.getColor());
    }

    @Test
    void parsePlayer_throws_when_duplicate_id() {
        ArrayList<Player> players = new ArrayList<>();

        ArrayList<String> langs = new ArrayList<>(Arrays.asList("Java"));
        players.add(new Player(1, "Existente", langs, PlayerColor.BLUE));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePlayer(new String[]{"1", "Ana", "Java", "RED"}, players)
        );

        assertEquals("Duplicate id or color", ex.getMessage());
    }

    @Test
    void parsePlayer_throws_when_duplicate_color() {
        ArrayList<Player> players = new ArrayList<>();

        ArrayList<String> langs = new ArrayList<>(Arrays.asList("Java"));
        players.add(new Player(1, "Existente", langs, PlayerColor.BLUE));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePlayer(new String[]{"2", "Ana", "Java", "BLUE"}, players)
        );

        assertEquals("Duplicate id or color", ex.getMessage());
    }
}

