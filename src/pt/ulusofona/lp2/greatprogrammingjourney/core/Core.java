package pt.ulusofona.lp2.greatprogrammingjourney.core;

import pt.ulusofona.lp2.greatprogrammingjourney.InvalidFileException;
import pt.ulusofona.lp2.greatprogrammingjourney.gamerules.GameRules;
import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.board.InteractableInitializer;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.move.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.Credits;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.theme.ThemeLibrary;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.ResultsBuilder;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.InputValidator;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig.THEME;
import static pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig.TURN_ORDER;

public class Core {

    // ============================== State ============================================================================

    private Board board;
    private int currentPlayerId;
    private final MoveHistory moveHistory = new MoveHistory();
    private static final GameLogger LOG = new GameLogger(Core.class);

    // ==================================== API Methods ================================================================

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        return createInitialBoard(playerInfo, worldSize, new String[0][0]);
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssAndTools) {

        ValidationResult playersOk = InputValidator.validatePlayerInfo(playerInfo);
        if (!playersOk.isValid()) {
            LOG.error("createInitialBoard: " + playersOk.getMessage());
            return false;
        }

        ValidationResult playerCountOk = GameRules.validatePlayerCount(playerInfo.length);
        if (!playerCountOk.isValid()) {
            LOG.error("createInitialBoard: " + playerCountOk.getMessage());
            return false;
        }

        ValidationResult worldSizeOk = GameRules.validateWorldSize(worldSize, playerInfo.length);
        if (!worldSizeOk.isValid()) {
            LOG.error("createInitialBoard: " + worldSizeOk.getMessage());
            return false;
        }

        if (!startBoard(playerInfo, abyssAndTools, worldSize)) {
            LOG.error("createInitialBoard: startBoard() failed");
            return false;
        }

        moveHistory.reset();
        setFirstPlayer();
        LOG.info("createInitialBoard: board created and initialized — starting game...");
        return true;
    }

    public String getImagePng(int nrSquare) {
        if (!InputValidator.validateBoardInitialized(board).isValid()) {
            return null;
        }
        if (!InputValidator.validatePosition(nrSquare, boardSize()).isValid()) {
            return null;
        }

        if (board.getIntercatableOfSlot(nrSquare) != null) {
            return board.getIntercatableOfSlot(nrSquare).getPng();
        }

        if (nrSquare == boardSize()) {
            return "glory.png";
        }

        return null;
    }

    public String[] getProgrammerInfo(int id) {
        if (!InputValidator.validateBoardInitialized(board).isValid()) {
            return null;
        }
        if (!InputValidator.validatePlayerExists(board, id).isValid()) {
            return null;
        }

        Player player = player(id);
        return new String[]{
                String.valueOf(player.getId()),
                player.getName(),
                String.join(";", player.getLanguages()),
                player.getColorAsStr(),
                String.valueOf(playerPosition(player)),
        };
    }

    public String getProgrammerInfoAsStr(int id) {
        if (!InputValidator.validateBoardInitialized(board).isValid()) {
            return null;
        }
        if (!InputValidator.validatePlayerExists(board, id).isValid()) {
            return null;
        }

        Player p = player(id);

        String name = p.getName();
        String pos = playerPosition(p) + "";
        String tools = p.getToolsAsStr();
        String state = p.getState() + "";
        String langsStr = String.join("; ", p.getSortedLangs());

        return id + " | " + name + " | " + pos + " | " + tools + " | " + langsStr + " | " + state;
    }

    public String[] getSlotInfo(int position) {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(board);
        if (!boardOk.isValid()) {
            LOG.error("getSlotInfo: " + boardOk.getMessage());
            return null;
        }

        ValidationResult positionOk = InputValidator.validatePosition(position, boardSize());
        if (!positionOk.isValid()) {
            LOG.warn("getSlotInfo: " + positionOk.getMessage());
            return null;
        }

        return board.getSlotInfo(position);
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(board);
        if (!boardOk.isValid()) {
            LOG.error("moveCurrentPlayer: " + boardOk.getMessage());
            return false;
        }

        ValidationResult diceOk = GameRules.validateDice(nrSpaces);
        if (!diceOk.isValid()) {
            LOG.error("moveCurrentPlayer: " + diceOk.getMessage());
            return false;
        }

        ValidationResult playerOk = InputValidator.validatePlayerExists(board, currentPlayerId);
        if (!playerOk.isValid()) {
            LOG.error("moveCurrentPlayer: " + playerOk.getMessage());
            return false;
        }

        Player p = player(currentPlayerId);
        String firstLanguage = p.getLanguages().getFirst();
        if (Objects.equals(firstLanguage, "Assembly") && nrSpaces > 2) {
            return false;
        }

        if (Objects.equals(firstLanguage, "C") && nrSpaces > 3) {
            return false;
        }

        int newPos = board.movePlayerBySteps(p, nrSpaces);
        int oldPos = playerPosition(p);
        if (p.isStuck()) {
            LOG.info("moveCurrentPlayer: player " + p.getName() + " is stuck and cannot move this turn");
            moveHistory.addRecord(p.getId(), oldPos, oldPos, nrSpaces);
            return true;
        }


        moveHistory.addRecord(p.getId(), oldPos, newPos, nrSpaces);
        return true;
    }

    public boolean gameIsOver() {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(board);
        if (!boardOk.isValid()) {
            LOG.error("gameIsOver: " + boardOk.getMessage());
            return false;
        }

        Player winner = board.getWinner();
        if (winner == null) {
            return false;
        }

        LOG.info("gameIsOver: winner=" + winner.getName());
        return true;
    }


    public ArrayList<String> getGameResults() {
        ValidationResult boardOk = InputValidator.validateBoardInitialized(board);
        if (!boardOk.isValid()) {
            LOG.error("getGameResults: " + boardOk.getMessage());
            return new ArrayList<>();
        }

        Player winner = board.getWinner();
        if (winner == null) {
            LOG.info("getGameResults: no winner yet, returning empty results");
            return new ArrayList<>();
        }

        ArrayList<String[]> defeatedInfo = new ArrayList<>();
        for (Player p : allPlayers()) {
            if (p.getId() != winner.getId()) {
                int pos = playerPosition(p);
                defeatedInfo.add(new String[]{p.getName(), String.valueOf(pos)});
            }
        }

        return ResultsBuilder.build(
                "THE GREAT PROGRAMMING JOURNEY",
                moveHistory.getSize(),
                winner.getName(),
                defeatedInfo
        );
    }

    public JPanel getAuthorsPanel() {
        return Credits.buildPanel();
    }

    public HashMap<String, String> customizeBoard() {
        return ThemeLibrary.get(THEME);
    }

    // ============================== Helper Methods ===================================================================

    private Player player(int id) {
        if (board == null) {
            throw new IllegalStateException("Board not initialized");
        }
        return board.getPlayer(id);
    }

    private int playerPosition(Player p) {
        if (board == null) {
            throw new IllegalStateException("Board not initialized");
        }
        if (p == null) {
            throw new IllegalArgumentException("Player is null");
        }
        return board.getPlayerPosition(p);
    }

    private boolean startBoard(String[][] playerInfo, String[][] interactableInfo, int worldSize) {
        board = new Board(worldSize);
        return board.initializePlayers(playerInfo) && InteractableInitializer.placeInteractables(board, interactableInfo);
    }

    private List<Player> allPlayers() {
        return board.getPlayers();
    }

    private List<Player> activePlayers() {
        List<Player> activePlayers = new ArrayList<>();
        for (Player p : allPlayers()) {
            if (p.isAlive()) {
                activePlayers.add(p);
            }
        }
        return activePlayers;
    }

    private int boardSize() {
        if (board == null) {
            throw new IllegalStateException("Board not initialized");
        }
        return board.getSize();
    }

    private void setFirstPlayer() {
        currentPlayerId = TurnManager.getFirstPlayerId(allPlayers(), TURN_ORDER);
    }

    private void advanceTurn() {
        currentPlayerId = TurnManager.getNextPlayerId(activePlayers(), currentPlayerId, TURN_ORDER);
    }

    // ======================================================= Parte II ================================================

    public String getProgrammersInfo() {
        StringBuilder sb = new StringBuilder();

        boolean first = true;
        for (Player p : board.getPlayers()) {
            if (!p.isAlive()) {
                continue;
            }

            String playerString = p.getName() + " : " + p.getToolsAsStr();

            if (first) {
                sb.append(playerString);
                first = false;
            } else {
                sb.append(" | " + playerString);
            }

        }
        return sb.toString();
    }

    public String reactToAbyssOrTool() {

        Player lastPlayer = board.getPlayer(getCurrentPlayerId());
        int pos = board.getPlayerPosition(lastPlayer);
        advanceTurn();

        if (!InputValidator.validateBoardInitialized(board).isValid()) {
            LOG.error("reactToAbyssOrTool: board not initialized");
            return null;
        }


        Interactable interactable = board.getIntercatableOfSlot(pos);
        if (interactable == null) {
            return null;
        }

        return interactable.interact(lastPlayer,board,moveHistory);
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
    }

    public boolean saveGame(File file) {
        return false;
    }

}