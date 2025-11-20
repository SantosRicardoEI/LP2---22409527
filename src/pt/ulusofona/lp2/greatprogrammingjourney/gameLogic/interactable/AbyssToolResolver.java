package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.tool.Tool;

public final class AbyssToolResolver {

    private AbyssToolResolver() {}

    public static int getCounterToolId(Abyss abyss) {
        return abyss.getId() + 1;
    }

    public static boolean isCounter(Tool tool, Abyss abyss) {
        return tool.getId() == getCounterToolId(abyss);
    }
}