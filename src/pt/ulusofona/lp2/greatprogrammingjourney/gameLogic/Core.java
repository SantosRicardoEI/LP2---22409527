package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic;

import pt.ulusofona.lp2.greatprogrammingjourney.InvalidFileException;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence.GamePersistence;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence.LoadedGame;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.Credits;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.StringUtils;

import javax.swing.*;
import java.io.*;
import java.util.*;


public class Core {

    // ============================== State ============================================================================

    private Board board;
    private TurnManager turnManager;
    private MoveHistory moveHistory = new MoveHistory();
    private static final GameLogger LOG = new GameLogger(Core.class);

    // ==================================== API Methods ================================================================

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        return createInitialBoard(playerInfo, worldSize, new String[0][0]);
    }

    public boolean createInitialBoard(String[][] playerInfo, int worldSize, String[][] mapObjects) {

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

        if (!initializeBoard(playerInfo, mapObjects, worldSize)) {
            LOG.error("createInitialBoard: startBoard() failed");
            return false;
        }

        moveHistory.reset();
        turnManager = new TurnManager(getPlayers());
        LOG.info("createInitialBoard: board created and initialized — starting game...");
        return true;
    }

    public String getImagePng(int nrSquare) {
        if (board == null) {
            return null;
        }

        MapObject mapObject = board.getMapObjectsAt(nrSquare);
        if (mapObject != null) {
            return mapObject.getPng();
        }

        return nrSquare == boardSize() ? "glory.png" : null;
    }

    public String[] getProgrammerInfo(int id) {
        Player player = safeGetPlayer(id);
        if (player == null) {
            return null;
        }

        return new String[]{
                String.valueOf(player.getId()),
                player.getName(),
                player.joinLanguages(";", false),
                player.getColor().toString(),
                String.valueOf(getPlayerPosition(player)),
                player.joinTools(";"),
                player.getState().toString(),
        };
    }

    public String getProgrammerInfoAsStr(int id) {
        Player p = safeGetPlayer(id);
        if (p == null) {
            return null;
        }

        String name = p.getName();
        String pos = String.valueOf(getPlayerPosition(p));
        String tools = p.joinTools(",");
        String state = p.getState().toString();
        String langsStr = p.joinLanguages("; ", true);

        return p.getId() + " | " + name + " | " + pos + " | " + tools + " | " + langsStr + " | " + state;
    }

    public String getProgrammersInfo() {
        if (board == null) {
            return "";
        }

        List<Player> jogadores = board.getPlayers();
        jogadores.sort(Comparator.comparingInt(Player::getId));

        StringBuilder sb = new StringBuilder();

        for (Player p : jogadores) {
            if (!p.isAlive()) {
                continue;
            }

            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append(p.getName()).append(" : ").append(p.joinTools(","));
        }

        return sb.toString();
    }

    public String[] getSlotInfo(int position) {
        if (board == null) {
            LOG.error("getSlotInfo: board is null");
            return null;
        }
        return board.getSlotInfo(position);
    }

    public int getCurrentPlayerId() {
        return turnManager.getCurrentID();
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        Player p = safeGetPlayer(turnManager.getCurrentID());
        if (board == null) {
            LOG.error("moveCurrentPlayer: " + "board is null");
            return false;
        }

        if (p == null) {
            LOG.error("moveCurrentPlayer: " + "no player found for id " + turnManager.getCurrentID());
            return false;
        }

        if (!GameRules.validateDice(nrSpaces)) {
            LOG.error("moveCurrentPlayer: " + "invalid dice value");
            return false;
        }


        int oldPos = getPlayerPosition(p);


        //TODO
        // "moveCurrentPlayer() devolveu false erradamente" em "test_018_InfiniteLoopVsTool"
        // Player estaria stuck = true e nao seria suposto?
        // - Tool ok (Teachers Help)
        // Se o player atual foi libertado foi no react anterior, aqui ja deveria chegar stuck = false,
        // nunca será libertado durante este metodo.

        if (p.isStuck() || p.isConfused()) {
            LOG.info("moveCurrentPlayer: player " + p.getName() + " is stuck or confused and cannot move this turn");
            moveHistory.addRecord(p.getId(), oldPos, oldPos, nrSpaces);
            return false;
        }

        String firstLanguage = p.getLanguages().get(0);
        if (Objects.equals(firstLanguage, "Assembly") && nrSpaces > 2) {
            moveHistory.addRecord(p.getId(), oldPos, oldPos, nrSpaces);
            return false;
        }

        if (Objects.equals(firstLanguage, "C") && nrSpaces > 3) {
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
            turnManager.advanceTurn(getPlayers());
            return null;
        }

        Player p = board.getPlayer(getCurrentPlayerId());
        MapObject object = board.getMapObjectsAt(board.getPlayerPosition(p));

        if (object == null) {
            turnManager.advanceTurn(getPlayers());
            return null;
        }

        turnManager.advanceTurn(getPlayers());
        return object.interact(p, board, moveHistory);
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
        for (Player p : getPlayers()) {
            int pos = getPlayerPosition(p);
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
        HashMap<String, String> theme = new HashMap<>();

        // Player images
        theme.put("playerBlueImage", GameConfig.PLAYER_BLUE_IMAGE);
        theme.put("playerBrownImage", GameConfig.PLAYER_BROWN_IMAGE);
        theme.put("playerPurpleImage", GameConfig.PLAYER_PURPLE_IMAGE);
        theme.put("playerGreenImage", GameConfig.PLAYER_GREEN_IMAGE);

        // Board
        theme.put("slotNumberColor", GameConfig.SLOT_NUMBER_COLOR);
        theme.put("slotNumberFontSize", GameConfig.SLOT_NUMBER_FONT_SIZE + "");

        theme.put("slotBackgroundColor", GameConfig.SLOT_BACKGROUND_COLOR);
        theme.put("cellSpacing", GameConfig.CELL_SPACING + "");
        theme.put("gridBackgroundColor", GameConfig.GRID_BACKGROUND_COLOR);
        theme.put("toolbarBackgroundColor", GameConfig.TOOLBAR_BACKGROUND_COLOR);
        theme.put("logoImage", GameConfig.LOGO);

        // New abyss and tool
        theme.put("hasNewAbyss", GameConfig.ENABLE_ABYYS_UNDOCUMENTED_CODE + "");
        theme.put("hasNewTool", GameConfig.ENABLE_TOOL_CHAT_GPT + "");
        return theme;
    }

    // =============================================== Helpers =========================================================

    private Player safeGetPlayer(int id) {
        return (board == null) ? null : board.getPlayer(id);
    }

    private int getPlayerPosition(Player p) {
        return board.getPlayerPosition(p);
    }

    private boolean initializeBoard(String[][] playerInfo, String[][] mapObjectsInfo, int worldSize) {
        board = new Board(worldSize);
        return board.initialize(playerInfo, mapObjectsInfo);
    }

    private List<Player> getPlayers() {
        return board.getPlayers();
    }

    private int boardSize() {
        if (board == null) {
            throw new IllegalStateException("Board not initialized");
        }
        return board.getSize();
    }

}