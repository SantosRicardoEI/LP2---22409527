package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pt.ulusofona.lp2.greatprogrammingjourney.InvalidFileException;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.MapObjectType;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence.GamePersistence;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence.LoadedGame;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestGamePersistence {

    @TempDir
    Path tempDir;

    private File tempFile(String name) {
        return tempDir.resolve(name).toFile();
    }

    private Player makePlayer(int id, String name, PlayerColor color) {
        return new Player(id, name, new ArrayList<>(Arrays.asList("Java")), color);
    }

    @Test
    public void testSaveToFileNullArguments() {
        Board board = new Board(5);
        MoveHistory history = new MoveHistory();
        File file = tempFile("f1.txt");

        assertFalse(GamePersistence.saveToFile(null, board, history, 1, 1));
        assertFalse(GamePersistence.saveToFile(file, null, history, 1, 1));
        assertFalse(GamePersistence.saveToFile(file, board, null, 1, 1));
    }

    @Test
    public void testSaveAndLoadHappyPath() throws Exception {
        File file = tempFile("happy.txt");

        Board board = new Board(10);
        Player p1 = makePlayer(1, "Alice", PlayerColor.BLUE);
        Player p2 = makePlayer(2, "Bob", PlayerColor.RED);

        board.placePlayer(p1, 1);
        board.placePlayer(p2, 2);

        MapObject abyss = MapObjectType.getMapObject(0, 0);
        MapObject tool = MapObjectType.getMapObject(1, 0);
        board.placeMapObject(abyss, 3);
        board.placeMapObject(tool, 4);

        MoveHistory history = new MoveHistory();
        history.addRecord(1, 1, 3, 2);
        history.addRecord(2, 2, 4, 2);

        p1.addTool(MapObjectType.getMapObject(1, 0) instanceof Tool
                ? (pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool) MapObjectType.getMapObject(1, 0)
                : null);

        boolean saved = GamePersistence.saveToFile(file, board, history, 1, 5);
        assertTrue(saved);

        LoadedGame loaded = GamePersistence.loadFromFile(file);
        assertNotNull(loaded);
        assertNotNull(loaded.getBoard());
        assertNotNull(loaded.getMoveHistory());
        assertEquals(1, loaded.getCurrentPlayerID());
        assertEquals(5, loaded.getTurnCount());
    }

    @Test
    public void testLoadFileNotFound() {
        File file = tempDir.resolve("no_such_file.dat").toFile();
        assertFalse(file.exists());
        assertThrows(FileNotFoundException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadNullFile() {
        assertThrows(FileNotFoundException.class, () -> GamePersistence.loadFromFile(null));
    }

    @Test
    public void testLoadDataBeforeAnySection() throws Exception {
        File file = tempFile("beforeSection.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("key=value");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadInvalidKeyValueFormat() throws Exception {
        File file = tempFile("invalidKV.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.BOARD_SIZE_KEY + "10");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadUnknownSection() throws Exception {
        File file = tempFile("unknownSection.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[UNKNOWN]");
            bw.newLine();
            bw.write("key=value");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadUnknownKeyInBoardSection() throws Exception {
        File file = tempFile("unknownKeyBoard.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write("X=10");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadUnknownKeyInPlayerSection() throws Exception {
        File file = tempFile("unknownKeyPlayer.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.BOARD_SIZE_KEY + "=10");
            bw.newLine();
            bw.write(GameConfig.CURRENT_ID_KEY + "=1");
            bw.newLine();
            bw.write("[" + GameConfig.PLAYER_SECTION + "]");
            bw.newLine();
            bw.write("X=1|1|Alice|Java|BLUE|IN_GAME");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadUnknownKeyInMapObjectSection() throws Exception {
        File file = tempFile("unknownKeyMapObject.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.BOARD_SIZE_KEY + "=10");
            bw.newLine();
            bw.write(GameConfig.CURRENT_ID_KEY + "=1");
            bw.newLine();
            bw.write("[" + GameConfig.MAP_OBJECT_SECTION + "]");
            bw.newLine();
            bw.write("X=1|0:0");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadUnknownKeyInMoveSection() throws Exception {
        File file = tempFile("unknownKeyMove.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.BOARD_SIZE_KEY + "=10");
            bw.newLine();
            bw.write(GameConfig.CURRENT_ID_KEY + "=1");
            bw.newLine();
            bw.write("[" + GameConfig.MOVE_SECTION + "]");
            bw.newLine();
            bw.write("X=1|1|2|3");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadPlayerSectionBeforeBoard() throws Exception {
        File file = tempFile("playerBeforeBoard.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.PLAYER_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.PLAYER_KEY + "=1|1|Alice|Java|BLUE|IN_GAME");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadMapObjectSectionBeforeBoard() throws Exception {
        File file = tempFile("mapBeforeBoard.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.MAP_OBJECT_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.MAP_OBJECT_KEY + "=1|0:0");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadMoveSectionBeforeBoard() throws Exception {
        File file = tempFile("moveBeforeBoard.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.MOVE_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.MOVE_KEY + "=1|1|2|3");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadInvalidPlayerDataLength() throws Exception {
        File file = tempFile("invalidPlayerData.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.BOARD_SIZE_KEY + "=10");
            bw.newLine();
            bw.write(GameConfig.CURRENT_ID_KEY + "=1");
            bw.newLine();
            bw.write("[" + GameConfig.PLAYER_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.PLAYER_KEY + "=1|1|Alice|Java|BLUE");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadInvalidMapObjectDataLength() throws Exception {
        File file = tempFile("invalidMapObjectData.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.BOARD_SIZE_KEY + "=10");
            bw.newLine();
            bw.write(GameConfig.CURRENT_ID_KEY + "=1");
            bw.newLine();
            bw.write("[" + GameConfig.MAP_OBJECT_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.MAP_OBJECT_KEY + "=1|0:0|extra");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadInvalidMoveDataLength() throws Exception {
        File file = tempFile("invalidMoveData.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.BOARD_SIZE_KEY + "=10");
            bw.newLine();
            bw.write(GameConfig.CURRENT_ID_KEY + "=1");
            bw.newLine();
            bw.write("[" + GameConfig.MOVE_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.MOVE_KEY + "=1|1|2");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadIncompleteFileMissingCurrentId() throws Exception {
        File file = tempFile("incompleteMissingCurrentId.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.BOARD_SIZE_KEY + "=10");
            bw.newLine();
            bw.write(GameConfig.TURN_COUNT_KEY + "=3");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }

    @Test
    public void testLoadBoardSizeNotIntegerTriggersCatch() throws Exception {
        File file = tempFile("invalidBoardSize.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("[" + GameConfig.BOARD_SECTION + "]");
            bw.newLine();
            bw.write(GameConfig.BOARD_SIZE_KEY + "=abc");
            bw.newLine();
            bw.write(GameConfig.CURRENT_ID_KEY + "=1");
            bw.newLine();
        }
        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(file));
    }
    @Test
    public void testParseAndPlaceMapObjects_invalidMapObjectData() throws Exception {
        File f = File.createTempFile("gpj_invalid_mapobj", ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
            bw.write("[BOARD]");
            bw.newLine();
            bw.write("BOARD_SIZE=10");
            bw.newLine();
            bw.write("CURRENT_PLAYER_ID=1");
            bw.newLine();
            bw.write("TURN_COUNT=1");
            bw.newLine();
            bw.newLine();
            bw.write("[MAP_OBJECTS]");
            bw.newLine();
            bw.write("MAP_OBJECT=5|coisa_muito_errada");
            bw.newLine();
        }

        assertThrows(InvalidFileException.class, () -> GamePersistence.loadFromFile(f));
    }

    @Test
    public void testSaveToFile_catchIOException() throws Exception {
        java.nio.file.Path dirPath = Files.createTempDirectory("gpj_dir_as_file");
        File dirAsFile = dirPath.toFile();

        Board board = new Board(10);
        MoveHistory history = new MoveHistory();

        boolean ok = GamePersistence.saveToFile(dirAsFile, board, history, 1, 1);

        assertFalse(ok);
    }

    @Test
    public void testWritePlayersSection_multipleTools() throws Exception {
        Board board = new Board(10);

        Player p = new Player(1, "A", new ArrayList<>(), PlayerColor.BLUE);
        Tool t1 = ToolSubType.INHERITANCE.getInstance();
        Tool t2 = ToolSubType.UNIT_TESTS.getInstance();
        Tool t3 = ToolSubType.EXCEPTION_HANDLING.getInstance();

        p.addTool(t1);
        p.addTool(t2);
        p.addTool(t3);

        board.placePlayer(p, 3);

        File f = File.createTempFile("gpj_write_players", ".txt");

        boolean ok = GamePersistence.saveToFile(f, board, new MoveHistory(), 1, 1);
        assertTrue(ok);
    }
}