package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence;

import pt.ulusofona.lp2.greatprogrammingjourney.InvalidFileException;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerState;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.parser.Parser;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.io.*;
import java.util.ArrayList;

public final class GamePersistence {

    private static final GameLogger LOG = new GameLogger(GamePersistence.class);

    private GamePersistence() {
    }

    // ================================================= Load ==========================================================

    public static LoadedGame loadFromFile(File file) throws InvalidFileException, FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException("File not found");
        }

        LoadContext ctx = new LoadContext();
        MoveHistory newHistory = new MoveHistory();
        int lineNumber = 0;
        String currentSection = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.startsWith("[") && line.endsWith("]")) {
                    currentSection = line.substring(1, line.length() - 1).trim();
                    continue;
                }

                if (currentSection == null) {
                    throw new InvalidFileException(
                            "Line " + lineNumber +
                                    ": data found before any section [" +
                                    GameConfig.BOARD_SECTION + "]/[" +
                                    GameConfig.PLAYER_SECTION + "]/[" +
                                    GameConfig.MAP_OBJECT_SECTION + "]/[" +
                                    GameConfig.MOVE_SECTION + "]"
                    );
                }

                String[] partes = line.split("=", 2);
                if (partes.length != 2) {
                    throw new InvalidFileException("Line " + lineNumber +
                            ": invalid format (expected key=value), line='" + line + "'");
                }

                processSectionLine(currentSection, partes[0].trim(), partes[1].trim(), lineNumber, ctx, newHistory);
            }

            if (ctx.board == null || ctx.currentPlayer == -1) {
                throw new InvalidFileException("Incomplete file: " +
                        GameConfig.BOARD_SECTION + " or " + GameConfig.CURRENT_ID_KEY + " missing");
            }

            return new LoadedGame(ctx.board, newHistory, ctx.currentPlayer, ctx.turnCount);

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            LOG.error("loadFromFile: error while reading file", e);
            throw new InvalidFileException("Error while reading file: " + e.getMessage(), e);
        }
    }

    // Para reduzir parametros
    private static class LoadContext {
        Board board;
        int currentPlayer = -1;
        int turnCount = -1;
    }

    private static void processSectionLine(String section, String key, String value, int lineNumber, LoadContext ctx, MoveHistory history
    ) throws InvalidFileException {

        switch (section) {
            case GameConfig.BOARD_SECTION:
                if (key.equals(GameConfig.BOARD_SIZE_KEY)) {
                    ctx.board = new Board(Parser.parseInt(value));
                } else if (key.equals(GameConfig.CURRENT_ID_KEY)) {
                    ctx.currentPlayer = Parser.parseInt(value);
                } else if (key.equals(GameConfig.TURN_COUNT_KEY)) {
                    ctx.turnCount = Parser.parseInt(value);
                } else {
                    throw new InvalidFileException("Line " + lineNumber +
                            ": unknown key in section [" + GameConfig.BOARD_SECTION + "]: '" + key + "'");
                }
                break;

            case GameConfig.PLAYER_SECTION:
                if (ctx.board == null) {
                    throw new InvalidFileException("Line " + lineNumber +
                            ": [" + GameConfig.PLAYER_SECTION + "] section found before [" + GameConfig.BOARD_SECTION + "]");
                }
                if (key.equals(GameConfig.PLAYER_KEY)) {
                    parseAndPlacePlayer(ctx.board, value, lineNumber);
                } else {
                    throw new InvalidFileException("Line " + lineNumber +
                            ": unknown key in section [" + GameConfig.PLAYER_SECTION + "]: '" + key + "'");
                }
                break;

            case GameConfig.MAP_OBJECT_SECTION:
                if (ctx.board == null) {
                    throw new InvalidFileException("Line " + lineNumber +
                            ": [" + GameConfig.MAP_OBJECT_SECTION + "] section found before [" + GameConfig.BOARD_SECTION + "]");
                }
                if (key.equals(GameConfig.MAP_OBJECT_KEY)) {
                    parseAndPlaceMapObjects(ctx.board, value, lineNumber);
                } else {
                    throw new InvalidFileException("Line " + lineNumber +
                            ": unknown key in section [" + GameConfig.MAP_OBJECT_SECTION + "]: '" + key + "'");
                }
                break;

            case GameConfig.MOVE_SECTION:
                if (ctx.board == null) {
                    throw new InvalidFileException("Line " + lineNumber +
                            ": [" + GameConfig.MOVE_SECTION + "] section found before [" + GameConfig.BOARD_SECTION + "]");
                }
                if (key.equals(GameConfig.MOVE_KEY)) {
                    parseAndAddMove(history, value, lineNumber);
                } else {
                    throw new InvalidFileException("Line " + lineNumber +
                            ": unknown key in section [" + GameConfig.MOVE_SECTION + "]: '" + key + "'");
                }
                break;

            default:
                throw new InvalidFileException("Line " + lineNumber +
                        ": unknown section '" + section + "'");
        }
    }

    private static void parseAndPlacePlayer(Board board, String valorStr, int lineNumber) throws InvalidFileException {

        String[] valores = valorStr.split("\\|", -1);
        if (valores.length < 6) {
            throw new InvalidFileException("Line " + lineNumber +
                    ": " + GameConfig.PLAYER_KEY +
                    " with invalid number of data (expected >= 6), value='" + valorStr + "'");
        }

        String posStr = valores[0].trim();
        String idStr = valores[1].trim();
        String name = valores[2].trim();
        String languagesStr = valores[3].trim();
        String colorStr = valores[4].trim();
        String stateStr = valores[5].trim();
        String toolsStr = valores.length > 6 ? valores[6].trim() : "";

        int position = Parser.parsePosition(posStr);
        int id = Parser.parseID(idStr);
        PlayerColor color = Parser.parseColor(colorStr);
        PlayerState state = Parser.parseState(stateStr);
        ArrayList<String> languages = Parser.parseLanguages(languagesStr);

        Player player = new Player(id, name, languages, color, state);

        if (!toolsStr.isEmpty()) {
            String[] tools = toolsStr.split(";");
            for (String s : tools) {
                s = s.trim();
                if (s.isEmpty()) {
                    continue;
                }
                int subType = Parser.parseInt(s);
                Tool tool = ToolSubType.getTool(subType);
                player.addTool(tool);
            }
        }

        board.placePlayer(player, position);
    }

    private static void parseAndPlaceMapObjects(Board board, String valorStr, int lineNumber) throws InvalidFileException {

        String[] valores = valorStr.split("\\|", -1);
        if (valores.length != 2) {
            throw new InvalidFileException("Line " + lineNumber +
                    ": " + GameConfig.MAP_OBJECT_KEY +
                    " with invalid number of data (expected 2), value='" + valorStr + "'");
        }

        int position = Parser.parsePosition(valores[0].trim());

        MapObject mapObject;
        try {
            mapObject = Parser.parseMapObject(valores[1].trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidFileException("Line " + lineNumber +
                    ": invalid " + GameConfig.MAP_OBJECT_KEY + " data '" + valorStr + "'");
        }

        board.placeMapObject(mapObject, position);
    }

    private static void parseAndAddMove(MoveHistory history, String valorStr, int lineNumber) throws InvalidFileException {

        String[] valores = valorStr.split("\\|", -1);
        if (valores.length != 4) {
            throw new InvalidFileException("Line " + lineNumber +
                    ": " + GameConfig.MOVE_KEY +
                    " with invalid number of data (expected 4), value='" + valorStr + "'");
        }

        int playerID = Parser.parseID(valores[0].trim());
        int from = Parser.parsePosition(valores[1].trim());
        int to = Parser.parsePosition(valores[2].trim());
        int dice = Parser.parseDice(valores[3].trim());

        history.addRecord(playerID, from, to, dice);
    }


    // ================================================= Save ==========================================================

    public static boolean saveToFile(File file, Board board, MoveHistory moveHistory, int currentPlayerID, int turnCount) {
        if (file == null || board == null || moveHistory == null) {
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            writeBoardSection(bw, board, currentPlayerID, turnCount);
            writePlayersSection(bw, board);
            writeMapObjectsSection(bw, board);
            writeMovesSection(bw, moveHistory);

            return true;

        } catch (IOException e) {
            return false;
        }
    }


    private static void writeBoardSection(BufferedWriter bw, Board board, int currentPlayerID, int turnCount) throws IOException {

        bw.write("[" + GameConfig.BOARD_SECTION + "]");
        bw.newLine();
        bw.write(GameConfig.BOARD_SIZE_KEY + "=" + board.getSize());
        bw.newLine();
        bw.write(GameConfig.CURRENT_ID_KEY + "=" + currentPlayerID);
        bw.newLine();
        bw.write(GameConfig.TURN_COUNT_KEY + "=" + turnCount);
        bw.newLine();
        bw.newLine();
    }

    private static void writePlayersSection(BufferedWriter bw, Board board) throws IOException {


        bw.write("[" + GameConfig.PLAYER_SECTION + "]");
        bw.newLine();
        bw.write("# Each PLAYER line follows the format:");
        bw.newLine();
        bw.write("# PLAYER=position|id|name|languages(;)|color|state|tools(optional)");
        bw.newLine();
        bw.write("# Example:");
        bw.newLine();
        bw.write("# PLAYER=5|2|Miguel|Java;C|BLUE|IN_GAME|1;3");
        bw.newLine();


        for (Player p : board.getPlayers()) {
            int pos = board.getPlayerPosition(p);
            String languages = p.joinLanguages(";", false);

            StringBuilder line = new StringBuilder();
            line.append(GameConfig.PLAYER_KEY).append("=")
                    .append(pos).append("|")
                    .append(p.getId()).append("|")
                    .append(p.getName()).append("|")
                    .append(languages).append("|")
                    .append(p.getColor().name()).append("|")
                    .append(p.getState().name());

            if (!p.getTools().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                boolean first = true;
                for (Tool t : p.getTools()) {
                    if (!first) {
                        sb.append(";");
                    }
                    String s = t.toString();
                    sb.append(s.charAt(s.length() - 1));
                    first = false;
                }
                line.append("|").append(sb);
            }

            bw.write(line.toString());
            bw.newLine();
        }

        bw.newLine();
    }

    private static void writeMapObjectsSection(BufferedWriter bw, Board board) throws IOException {

        bw.write("[" + GameConfig.MAP_OBJECT_SECTION + "]");
        bw.newLine();
        bw.write("# Each MAP_OBJECT line follows the format:");
        bw.newLine();
        bw.write("# MAP_OBJECT=position|type(id)");
        bw.newLine();
        bw.write("# Example:");
        bw.newLine();
        bw.write("# MAP_OBJECT=7|0:3");
        bw.newLine();
        bw.write("# (Example above: object of type 0 with id 3 placed at position 7)");
        bw.newLine();

        for (MapObject i : board.getMapObjects()) {
            int pos = board.getMapObjectPosition(i);
            bw.write(GameConfig.MAP_OBJECT_KEY + "=" + pos + "|" + i.toString());
            bw.newLine();
        }

        bw.newLine();
    }

    private static void writeMovesSection(BufferedWriter bw, MoveHistory moveHistory) throws IOException {

        bw.write("[" + GameConfig.MOVE_SECTION + "]");
        bw.newLine();
        bw.write("# Each MOVE line follows the format:");
        bw.newLine();
        bw.write("# MOVE=playerID|fromPosition|toPosition|diceValue");
        bw.newLine();
        bw.write("# Example:");
        bw.newLine();
        bw.write("# MOVE=2|5|8|3");
        bw.newLine();
        bw.write("# (Example above: player 2 moved from position 5 to 8 using dice value 3)");
        bw.newLine();

        String[][] moves = moveHistory.getAllMovesInfo();
        for (String[] m : moves) {
            bw.write(GameConfig.MOVE_KEY + "=" + m[0] + "|" + m[1] + "|" + m[2] + "|" + m[3]);
            bw.newLine();
        }
    }
}