package pt.ulusofona.lp2.greatprogrammingjourney;

import pt.ulusofona.lp2.greatprogrammingjourney.core.Core;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    // ============================== State ============================================================================

    private final Core core = new Core();

    // ============================== Public API =======================================================================

    public boolean createInitialBoard(String[][] playerInfo, int worldSize) {
        return core.createInitialBoard(playerInfo, worldSize);
    }

    public String getImagePng(int nrSquare) {
        return core.getImagePng(nrSquare);
    }

    public String[] getProgrammerInfo(int id) {
        return core.getProgrammerInfo(id);
    }

    public String getProgrammerInfoAsStr(int id) {
        return core.getProgrammerInfoAsStr(id);
    }

    public String[] getSlotInfo(int position) {
        return core.getSlotInfo(position);
    }

    public int getCurrentPlayerID() {
        return core.getCurrentPlayerId();
    }

    public boolean moveCurrentPlayer(int nrSpaces) {
        return core.moveCurrentPlayer(nrSpaces);
    }

    public boolean gameIsOver() {
        return core.gameIsOver();
    }

    public ArrayList<String> getGameResults() {
        return core.getGameResults();
    }

    public JPanel getAuthorsPanel() {
        return core.getAuthorsPanel();
    }

    public HashMap<String, String> customizeBoard() {
        return core.customizeBoard();
    }
}