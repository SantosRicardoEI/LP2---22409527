package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic;

import pt.ulusofona.lp2.greatprogrammingjourney.InvalidFileException;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.BoardInitializer;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence.GamePersistence;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence.LoadedGame;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.Credits;
import pt.ulusofona.lp2.greatprogrammingjourney.ui.theme.ThemeLibrary;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.StringUtils;

import javax.swing.*;
import java.io.*;
import java.util.*;


public class Core {

    // ============================== State ============================================================================

    private Board board;
    private TurnManager turnManager = new TurnManager();
    private MoveHistory moveHistory = new MoveHistory();
    private static final GameLogger LOG = new GameLogger(Core.class);

    // ==================================== API Methods ================================================================

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        return createInitialBoard(playerInfo, worldSize, new String[0][0]);
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssAndTools) {
        if (playerInfo == null || playerInfo.length == 0) {
            LOG.error("createInitialBoard: " + "invalid player info");
            return false;
        }

        if (!GameRules.validatePlayerCount(playerInfo.length)) {
            LOG.error("createInitialBoard: " + "invalid player count");
            return false;
        }

        if (!GameRules.validateWorldSize(worldSize, playerInfo.length)) {
            LOG.error("createInitialBoard: " + "invalid world size");
            return false;
        }

        if (!initializeBoard(playerInfo, abyssAndTools, worldSize)) {
            LOG.error("createInitialBoard: startBoard() failed");
            return false;
        }

        moveHistory.reset();
        turnManager.advanceTurn(activePlayers());
        LOG.info("createInitialBoard: board created and initialized — starting game...");
        return true;
    }

    public String getImagePng(int nrSquare) {
        if (board == null) {
            return null;
        }
        if (!validatePosition(nrSquare, boardSize())) {
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
        if (board == null) {
            return null;
        }
        if (!validatePlayerExists(board, id)) {
            return null;
        }

        Player player = player(id);

        return new String[]{
                String.valueOf(player.getId()),
                player.getName(),
                String.join(";", player.getLanguages()),
                player.getColorAsStr(),
                String.valueOf(playerPosition(player)),
                String.join(";", player.getToolsInfo()),
                player.getState().toString(),
        };
    }

    public String getProgrammerInfoAsStr(int id) {
        if (board == null) {
            return null;
        }
        if (!validatePlayerExists(board, id)) {
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

    public String getProgrammersInfo() {
        List<Player> jogadores = new ArrayList<>(board.getPlayers());

        jogadores.sort(Comparator.comparingInt(Player::getId));

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Player p : jogadores) {
            if (!p.isAlive()) {
                continue;
            }

            String playerString = p.getName() + " : " + p.getToolsAsStr();

            if (first) {
                sb.append(playerString);
                first = false;
            } else {
                sb.append(" | ").append(playerString);
            }
        }

        return sb.toString();
    }

    public String[] getSlotInfo(int position) {
        if (board == null) {
            LOG.error("getSlotInfo: " + "board is null");
            return null;
        }

        if (!validatePosition(position, boardSize())) {
            LOG.warn("getSlotInfo: " + "invalid position");
            return null;
        }

        return board.getSlotInfo(position);
    }

    public int getCurrentPlayerId() {
        return turnManager.getCurrentID();
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        if (board == null) {
            LOG.error("moveCurrentPlayer: " + "board is null");
            return false;
        }

        if (!GameRules.validateDice(nrSpaces)) {
            LOG.error("moveCurrentPlayer: " + "invalid dice value");
            return false;
        }

        if (!validatePlayerExists(board, turnManager.getCurrentID())) {
            LOG.error("moveCurrentPlayer: " + "no player found for id " + turnManager.getCurrentID());
            return false;
        }

        Player p = player(turnManager.getCurrentID());
        int oldPos = playerPosition(p);
        String firstLanguage = p.getLanguages().get(0);
        if (Objects.equals(firstLanguage, "Assembly") && nrSpaces > 2) {
            moveHistory.addRecord(p.getId(), oldPos, oldPos, nrSpaces);
            return false;
        }

        if (Objects.equals(firstLanguage, "C") && nrSpaces > 3) {
            moveHistory.addRecord(p.getId(), oldPos, oldPos, nrSpaces);
            return false;
        }

        if (p.isStuck()) {
            LOG.info("moveCurrentPlayer: player " + p.getName() + " is stuck and cannot move this turn");
            moveHistory.addRecord(p.getId(), oldPos, oldPos, nrSpaces);
            return false;
        }

        int newPos = board.movePlayerBySteps(p, nrSpaces);

        moveHistory.addRecord(p.getId(), oldPos, newPos, nrSpaces);
        return true;
    }

    public String reactToAbyssOrTool() {
        if (board == null) {
            LOG.error("reactToAbyssOrTool: board is null");
            turnManager.advanceTurn(activePlayers());
            return null;
        }

        Player p = board.getPlayer(getCurrentPlayerId());
        Interactable inter = board.getIntercatableOfSlot(board.getPlayerPosition(p));

        turnManager.advanceTurn(activePlayers());

        if (inter == null) {
            return null;
        }

        return inter.interact(p, board, moveHistory);
    }

    public boolean gameIsOver() {
        if (board == null) {
            LOG.error("gameIsOver: " + "board is null");
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
        if (board == null) {
            LOG.error("getGameResults: " + "board is null");
            return new ArrayList<>();
        }

        Player winner = board.getWinner();
        if (winner == null) {
            LOG.info("getGameResults: no winner yet, returning empty results");
            return new ArrayList<>();
        }

        ArrayList<String[]> playersNameAndPosition = new ArrayList<>();
        for (Player p : allPlayers()) {
            int pos = playerPosition(p);
            playersNameAndPosition.add(new String[]{p.getName(), String.valueOf(pos)});
        }

        return StringUtils.buildResultsString(
                turnManager.getTurnCount(),
                playersNameAndPosition
        );
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
        LoadedGame state = GamePersistence.loadFromFile(file);
        this.board = state.board();
        this.moveHistory = state.history();
        this.turnManager = new TurnManager(state.currentPlayerID(), state.turnCount());
    }


    public boolean saveGame(File file) {
        return GamePersistence.saveToFile(file, board, moveHistory, turnManager.getCurrentID(), turnManager.getTurnCount());
    }

    public JPanel getAuthorsPanel() {
        return Credits.buildPanel();
    }

    public HashMap<String, String> customizeBoard() {
        return ThemeLibrary.get(GameConfig.THEME);
    }

    // =============================================== Helpers =========================================================

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

    private boolean initializeBoard(String[][] playerInfo, String[][] interactableInfo, int worldSize) {
        board = new Board(worldSize);
        return board.initializePlayers(playerInfo) && BoardInitializer.initializeInteractables(board, interactableInfo);
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

    private static boolean validatePosition(int pos, int boardSize) {
        if (pos < 1 || pos > boardSize) {
            return false;
        }
        return true;
    }

    private static boolean validatePlayerExists(Board board, int playerId) {
        if (board.getPlayer(playerId) == null) {
            return false;
        }
        return true;
    }
}