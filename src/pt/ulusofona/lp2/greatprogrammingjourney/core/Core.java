package pt.ulusofona.lp2.greatprogrammingjourney.core;

import pt.ulusofona.lp2.greatprogrammingjourney.gamerules.GameRules;
import pt.ulusofona.lp2.greatprogrammingjourney.model.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.model.move.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.model.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.Credits;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.theme.ThemeLibrary;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.ResultsBuilder;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.StringUtils;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.InputValidator;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        if (!startBoard(playerInfo, worldSize)) {
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
        return (nrSquare == boardSize()) ? "glory.png" : null;
    }

    public String[] getProgrammerInfo(int id) {
        if (!InputValidator.validateBoardInitialized(board).isValid()) {
            return null;
        }
        if (!InputValidator.validatePlayerExists(board, id).isValid()) {
            return null;
        }

        Player player = player(id); // seguro aqui
        return new String[]{
                String.valueOf(player.getId()),
                player.getName(),
                String.join(";", player.getLanguages()),
                player.getColorAsStr(),
                String.valueOf(playerPosition(player))
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
        return StringUtils.formatProgrammerInfoStr(
                p.getId(),
                p.getName(),
                playerPosition(p),
                p.getSortedLangs(),
                p.getState().toString()
        );
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
        int oldPos = playerPosition(p);
        int newPos = board.movePlayer(p, nrSpaces);

        moveHistory.addRecord(p.getId(), oldPos, newPos, nrSpaces);
        advanceTurn();
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
        for (Player p : players()) {
            if (p.getId() != winner.getId()) {
                int pos = playerPosition(p);
                defeatedInfo.add(new String[]{p.getName(), String.valueOf(pos)});
            }
        }

        return ResultsBuilder.build(
                "THE GREAT PROGRAMMING JOURNEY",
                moveHistory.getSize() + 1,
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

    private boolean startBoard(String[][] playerInfo, int worldSize) {
        board = new Board(worldSize);
        return board.initializePlayers(playerInfo);
    }

    private List<Player> players() {
        return board.getPlayers();
    }

    private int boardSize() {
        if (board == null) {
            throw new IllegalStateException("Board not initialized");
        }
        return board.getSize();
    }

    private void setFirstPlayer() {
        currentPlayerId = TurnManager.getFirstPlayerId(players(), TURN_ORDER);
    }

    private void advanceTurn() {
        currentPlayerId = TurnManager.getNextPlayerId(players(), currentPlayerId, TURN_ORDER);
    }
}