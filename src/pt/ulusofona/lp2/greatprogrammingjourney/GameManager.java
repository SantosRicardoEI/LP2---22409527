package pt.ulusofona.lp2.greatprogrammingjourney;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.GameRules;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.TurnManager;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.board.Board;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence.GamePersistence;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.gamepersistence.LoadedGame;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.movehistory.MoveHistory;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.Player;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.Credits;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

import javax.swing.*;
import java.io.*;
import java.util.*;


public class GameManager {

    // ============================== State ============================================================================

    private Board board;
    private TurnManager turnManager;
    private MoveHistory moveHistory = new MoveHistory();
    private static final GameLogger LOG = new GameLogger(GameManager.class);

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
            LOG.error("createInitialBoard: " + "invalid world size for player count " + playerInfo.length);
            return false;
        }

        if (!initializeNewBoard(playerInfo, mapObjects, worldSize)) {
            LOG.error("createInitialBoard: error initializing board");
            return false;
        }

        moveHistory.reset();
        turnManager = new TurnManager(getPlayers());
        return true;
    }

    public String getImagePng(int nrSquare) {
        if (board == null) {
            throw new GameNotInitializedException();
        }

        MapObject mapObject = board.getMapObjectsAt(nrSquare);
        if (mapObject != null) {
            return mapObject.getPng();
        }

        return nrSquare == boardSize() ? "glory.png" : null;
    }

    public String[] getProgrammerInfo(int id) {
        if (board == null) {
            throw new GameNotInitializedException();
        }
        Player player = getPlayer(id);
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
        if (board == null) {
            throw new GameNotInitializedException();
        }
        Player p = getPlayer(id);
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
            throw new GameNotInitializedException();
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
            throw new GameNotInitializedException();
        }
        return board.getSlotInfo(position);
    }

    public int getCurrentPlayerID() {
        if (turnManager == null) {
            throw new GameNotInitializedException();
        }
        return turnManager.getCurrentID();
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        if (board == null || turnManager == null) {
            throw new GameNotInitializedException();
        }
        Player p = getPlayer(turnManager.getCurrentID());

        if (p == null) {
            throw new InvalidGameStateException("Current player not found");
        }

        if (!GameRules.validateDice(nrSpaces)) {
            LOG.error("moveCurrentPlayer: " + "invalid dice value");
            return false;
        }

        // Para usar a batota, que da o nrSpaces negativo
        if (nrSpaces < 0) {
            nrSpaces = nrSpaces * -1;
        }

        int oldPos = getPlayerPosition(p);

        if (p.isStuck() || p.isConfused()) {
            LOG.info("Player " + p.getName() + " is stuck or confused and cannot move this turn");
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

        int newPos = board.movePlayer(p, nrSpaces);
        moveHistory.addRecord(p.getId(), oldPos, newPos, nrSpaces);
        return true;
    }

    public String reactToAbyssOrTool() {
        if (board == null) {
            throw new GameNotInitializedException();
        }

        Player p = board.getPlayer(getCurrentPlayerID());
        if (p == null) {
            throw new InvalidGameStateException("Current player not found");
        }
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
            throw new GameNotInitializedException();
        }

        Player winner = board.getWinner();
        if (winner == null) {
            return false;
        }

        LOG.info("Game is Over. Winner: " + winner.getName());
        return true;
    }


    public ArrayList<String> getGameResults() {
        if (board == null) {
            throw new GameNotInitializedException();
        }

        Player winner = board.getWinner();
        if (winner == null) {
            return new ArrayList<>();
        }

        int turnCount = turnManager.getTurnCount();
        if (turnCount < 0) {
            throw new IllegalArgumentException("Turn count must be non-negative");
        }

        ArrayList<String[]> playersNameAndPosition = new ArrayList<>();
        for (Player p : getPlayers()) {
            int pos = getPlayerPosition(p);
            playersNameAndPosition.add(new String[]{p.getName(), String.valueOf(pos)});
        }

        if (playersNameAndPosition.isEmpty()) {
            return new ArrayList<>();
        }

        for (String[] info : playersNameAndPosition) {
            if (info == null || info.length < 2 || info[0] == null || info[1] == null) {
                throw new IllegalArgumentException("Invalid player info entry: missing name or position");
            }
        }

        playersNameAndPosition.sort((a, b) -> {
            try {
                int posA = Integer.parseInt(a[1]);
                int posB = Integer.parseInt(b[1]);

                int cmp = Integer.compare(posB, posA); // desc

                if (cmp != 0) {
                    return cmp;
                }

                return a[0].compareToIgnoreCase(b[0]);
            } catch (NumberFormatException e) {
                return 0;
            }
        });

        String winnerName = playersNameAndPosition.get(0)[0];

        ArrayList<String> results = new ArrayList<>();
        results.add("THE GREAT PROGRAMMING JOURNEY");
        results.add("");
        results.add("NR. DE TURNOS");
        results.add(String.valueOf(turnCount));
        results.add("");
        results.add("VENCEDOR");
        results.add(winnerName);
        results.add("");
        results.add("RESTANTES");

        for (int i = 1; i < playersNameAndPosition.size(); i++) {
            String[] info = playersNameAndPosition.get(i);
            results.add(info[0] + " " + info[1]);
        }

        return results;
    }

    public void loadGame(File file) throws InvalidFileException, FileNotFoundException {
        LoadedGame state = GamePersistence.loadFromFile(file);
        this.board = state.board();
        this.moveHistory = state.history();
        this.turnManager = new TurnManager(state.currentPlayerID(), state.turnCount());
    }


    public boolean saveGame(File file) {
        if (board == null || turnManager == null) {
            throw new GameNotInitializedException();
        }
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

    private Player getPlayer(int id) {
        return board.getPlayer(id);
    }

    private int getPlayerPosition(Player p) {
        return board.getPlayerPosition(p);
    }

    private boolean initializeNewBoard(String[][] playerInfo, String[][] mapObjectsInfo, int worldSize) {
        board = new Board(worldSize);
        return board.initialize(playerInfo, mapObjectsInfo);
    }

    private List<Player> getPlayers() {
        return board.getPlayers();
    }

    private int boardSize() {
        if (board == null) {
            throw new GameNotInitializedException();
        }
        return board.getSize();
    }

}