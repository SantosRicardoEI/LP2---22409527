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

import java.io.*;
import java.util.ArrayList;

public final class GamePersistence {

    private static final GameLogger LOG = new GameLogger(GamePersistence.class);


    private GamePersistence() {
    }

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
                String chave;
                String valorStr;

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
                chave = partes[0].trim();
                valorStr = partes[1].trim();

                switch (currentSection) {

                    case "BOARD":
                        if (chave.equals("BOARD_SIZE")) {
                            int boardSize = Parser.parseInt(valorStr); // se falhar, atira NFE
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
                            throw new InvalidFileException();
                        }

                        if (chave.equals("PLAYER")) {
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

                            newBoard.placePlayer(player, position);
                        } else {
                            throw new InvalidFileException("Line " + lineNumber +
                                    ": unknown key in section [PLAYERS]: '" + chave + "'");
                        }
                        break;

                    case "ITEMS":

                        if (newBoard == null) {
                            throw new InvalidFileException();
                        }

                        if (chave.equals("INTERACTABLE")) {
                            String[] valores = valorStr.split("\\|", -1);
                            if (valores.length != 2) {
                                throw new InvalidFileException("Linhe " + lineNumber +
                                        ": INTERACTABLE with invalid number of data (expected 2), value='" + valorStr + "'");
                            }

                            int position = Parser.parsePosition(valores[0].trim());

                            Interactable interactable;
                            try {
                                interactable = Parser.parseInteractable(valores[1].trim());
                            } catch (IllegalArgumentException e) {
                                throw new InvalidFileException();
                            }

                            newBoard.placeInteractable(interactable, position);

                        } else {
                            throw new InvalidFileException("Linhe " + lineNumber +
                                    ": unknown key in section [ITEMS]: '" + chave + "'");
                        }
                        break;

                    case "MOVES":

                        if (newBoard == null) {
                            throw new InvalidFileException();
                        }

                        if (chave.equals("MOVE")) {
                            String[] valores = valorStr.split("\\|", -1);
                            if (valores.length != 4) {
                                throw new InvalidFileException("Linhe " + lineNumber +
                                        ": MOVE with invalid number of data (expected 4), value='" + valorStr + "'");
                            }

                            String playerIdStr = valores[0].trim();
                            int playerID = Parser.parseID(playerIdStr);

                            String fromStr = valores[1].trim();
                            int from = Parser.parsePosition(fromStr);

                            String toStr = valores[2].trim();
                            int to = Parser.parsePosition(toStr);

                            String diceStr = valores[3].trim();
                            int dice = Parser.parseDice(diceStr);

                            newHistory.addRecord(playerID, from, to, dice);

                        } else {
                            throw new InvalidFileException();
                        }
                        break;

                    default:
                        throw new InvalidFileException();
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

    public static boolean saveToFile(File file, Board board, MoveHistory moveHistory, int currentPlayerID) {
        if (file == null) {
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            bw.write("[BOARD]");
            bw.newLine();
            bw.write("BOARD_SIZE=" + board.getSize());
            bw.newLine();
            bw.write("CURRENT_PLAYER=" + currentPlayerID);
            bw.newLine();
            bw.newLine();
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
                        sb.append(t.toString().charAt(t.toString().length() - 1));
                        first = false;
                    }
                    line.append("|").append(sb);
                }

                bw.write(line.toString());
                bw.newLine();
            }

            bw.newLine();
            bw.write("[ITEMS]");
            bw.newLine();

            for (Interactable i : board.getInteractables()) {
                int pos = board.getInteractablePosition(i);
                bw.write("INTERACTABLE=" + pos + "|" + i);
                bw.newLine();
            }

            bw.newLine();
            bw.write("[MOVES]");
            bw.newLine();

            String[][] moves = moveHistory.getAllMovesInfo();
            for (int i = 0; i < moves.length; i++) {
                String[] m = moves[i];
                bw.write("MOVE=" + m[0] + "|" + m[1] + "|" + m[2] + "|" + m[3]);
                bw.newLine();
            }

            return true;

        } catch (IOException e) {
            return false;
        }
    }
}