package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.subtypes;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;

public class ChatGPT extends Tool {

    public ChatGPT(int id, String name, String png) {
        super(id, name, png);
    }

    public boolean canHelp(Abyss abyss) {
        return Math.random() < 0.5;
    }
}