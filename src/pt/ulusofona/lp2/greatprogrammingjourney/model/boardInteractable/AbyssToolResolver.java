package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.Tool;

public final class AbyssToolResolver {

    private AbyssToolResolver() {}

    public static int getCounterToolId(Abyss abyss) {
        return abyss.getId() + 1;
    }

    public static boolean isCounter(Tool tool, Abyss abyss) {
        return tool.getId() == getCounterToolId(abyss);
    }
}