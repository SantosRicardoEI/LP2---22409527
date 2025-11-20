package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence;

import pt.ulusofona.lp2.greatprogrammingjourney.InvalidFileException;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerColor;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerState;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.parser.Parser;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

        Board newBoard = null;
        MoveHistory newHistory = new MoveHistory();
        int newCurrentPlayer = -1;

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
                    throw new InvalidFileException("Line " + lineNumber +
                            ": data found before any section [BOARD]/[PLAYERS]/[ITEMS]/[MOVES]");
                }

                String[] partes = line.split("=");
                if (partes.length != 2) {
                    throw new InvalidFileException("Line " + lineNumber +
                            ": invalid format (expected key=value), line='" + line + "'");
                }
                String chave = partes[0].trim();
                String valorStr = partes[1].trim();

                switch (currentSection) {

                    case "BOARD":
                        if (chave.equals("BOARD_SIZE")) {
                            int boardSize = Parser.parseInt(valorStr);
                            newBoard = new Board(boardSize);
                        } else if (chave.equals("CURRENT_PLAYER")) {
                            newCurrentPlayer = Parser.parseInt(valorStr);
                        } else {
                            throw new InvalidFileException("Line " + lineNumber +
                                    ": unknown key in section [BOARD]: '" + chave + "'");
                        }
                        break;

                    case "PLAYERS":
                        if (newBoard == null) {
                            throw new InvalidFileException("Line " + lineNumber +
                                    ": [PLAYERS] section found before [BOARD]");
                        }
                        if (chave.equals("PLAYER")) {
                            parseAndPlacePlayer(newBoard, valorStr, lineNumber);
                        } else {
                            throw new InvalidFileException("Line " + lineNumber +
                                    ": unknown key in section [PLAYERS]: '" + chave + "'");
                        }
                        break;

                    case "ITEMS":
                        if (newBoard == null) {
                            throw new InvalidFileException("Line " + lineNumber +
                                    ": [ITEMS] section found before [BOARD]");
                        }
                        if (chave.equals("INTERACTABLE")) {
                            parseAndPlaceInteractable(newBoard, valorStr, lineNumber);
                        } else {
                            throw new InvalidFileException("Line " + lineNumber +
                                    ": unknown key in section [ITEMS]: '" + chave + "'");
                        }
                        break;

                    case "MOVES":
                        if (newBoard == null) {
                            throw new InvalidFileException("Line " + lineNumber +
                                    ": [MOVES] section found before [BOARD]");
                        }
                        if (chave.equals("MOVE")) {
                            parseAndAddMove(newHistory, valorStr, lineNumber);
                        } else {
                            throw new InvalidFileException("Line " + lineNumber +
                                    ": unknown key in section [MOVES]: '" + chave + "'");
                        }
                        break;

                    default:
                        throw new InvalidFileException("Line " + lineNumber +
                                ": unknown section '" + currentSection + "'");
                }
            }

            if (newBoard == null || newCurrentPlayer == -1) {
                throw new InvalidFileException("Incomplete file: BOARD or CURRENT_PLAYER missing");
            }

            return new LoadedGame(newBoard, newHistory, newCurrentPlayer);

        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            LOG.error("loadFromFile: error while reading file", e);
            throw new InvalidFileException("Error while reading file: " + e.getMessage(), e);
        }
    }

    private static void parseAndPlacePlayer(Board board, String valorStr, int lineNumber) throws InvalidFileException {

        String[] valores = valorStr.split("\\|", -1);
        if (valores.length < 6) {
            throw new InvalidFileException("Line " + lineNumber +
                    ": PLAYER with invalid number of data (expected >= 6), value='" + valorStr + "'");
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

        Player player = new Player(id, name, languages, color);
        player.setState(state);

        if (!toolsStr.isEmpty()) {
            String[] tools = toolsStr.split(";");
            for (String s : tools) {
                s = s.trim();
                if (s.isEmpty()) {
                    continue;
                }
                int subType = Parser.parseInt(s);
                Tool tool = ToolSubType.createTool(subType);
                player.addTool(tool);
            }
        }

        board.placePlayer(player, position);
    }

    private static void parseAndPlaceInteractable(Board board, String valorStr, int lineNumber) throws InvalidFileException {

        String[] valores = valorStr.split("\\|", -1);
        if (valores.length != 2) {
            throw new InvalidFileException("Line " + lineNumber +
                    ": INTERACTABLE with invalid number of data (expected 2), value='" + valorStr + "'");
        }

        int position = Parser.parsePosition(valores[0].trim());

        Interactable interactable;
        try {
            interactable = Parser.parseInteractable(valores[1].trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidFileException("Line " + lineNumber +
                    ": invalid INTERACTABLE data '" + valorStr + "'");
        }

        board.placeInteractable(interactable, position);
    }

    private static void parseAndAddMove(MoveHistory history, String valorStr, int lineNumber) throws InvalidFileException {

        String[] valores = valorStr.split("\\|", -1);
        if (valores.length != 4) {
            throw new InvalidFileException("Line " + lineNumber +
                    ": MOVE with invalid number of data (expected 4), value='" + valorStr + "'");
        }

        int playerID = Parser.parseID(valores[0].trim());
        int from = Parser.parsePosition(valores[1].trim());
        int to = Parser.parsePosition(valores[2].trim());
        int dice = Parser.parseDice(valores[3].trim());

        history.addRecord(playerID, from, to, dice);
    }


    // ================================================= Save ==========================================================

    public static boolean saveToFile(File file, Board board, MoveHistory moveHistory, int currentPlayerID) {
        if (file == null || board == null || moveHistory == null) {
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            writeBoardSection(bw, board, currentPlayerID);
            writePlayersSection(bw, board);
            writeItemsSection(bw, board);
            writeMovesSection(bw, moveHistory);

            return true;

        } catch (IOException e) {
            return false;
        }
    }


    private static void writeBoardSection(BufferedWriter bw, Board board, int currentPlayerID) throws IOException {

        bw.write("[BOARD]");
        bw.newLine();
        bw.write("BOARD_SIZE=" + board.getSize());
        bw.newLine();
        bw.write("CURRENT_PLAYER=" + currentPlayerID);
        bw.newLine();
        bw.newLine();
    }

    private static void writePlayersSection(BufferedWriter bw, Board board) throws IOException {

        bw.write("[PLAYERS]");
        bw.newLine();

        for (Player p : board.getPlayers()) {
            int pos = board.getPlayerPosition(p);
            String languages = String.join(";", p.getLanguages());

            StringBuilder line = new StringBuilder();
            line.append("PLAYER=")
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

    private static void writeItemsSection(BufferedWriter bw, Board board) throws IOException {

        bw.write("[ITEMS]");
        bw.newLine();

        for (Interactable i : board.getInteractables()) {
            int pos = board.getInteractablePosition(i);
            bw.write("INTERACTABLE=" + pos + "|" + i.toString());
            bw.newLine();
        }

        bw.newLine();
    }

    private static void writeMovesSection(BufferedWriter bw, MoveHistory moveHistory) throws IOException {

        bw.write("[MOVES]");
        bw.newLine();

        String[][] moves = moveHistory.getAllMovesInfo();
        for (String[] m : moves) {
            bw.write("MOVE=" + m[0] + "|" + m[1] + "|" + m[2] + "|" + m[3]);
            bw.newLine();
        }
    }
}