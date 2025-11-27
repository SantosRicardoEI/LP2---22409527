package pt.ulusofona.lp2.greatprogrammingjourney.enums;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes.*;

public enum AbyssSubType {

    SYNTAX_ERROR(new SyntaxError(0, "Erro de sintaxe", "syntax.png", ToolSubType.getToolByID(ToolSubType.IDE.getInstance().getId()))),
    LOGIC_ERROR(new LogicError(1, "Erro de Lógica", "logic.png", ToolSubType.getToolByID(ToolSubType.UNIT_TESTS.getInstance().getId()))),
    EXCEPTION(new ExceptionA(2, "Exception", "exception.png", ToolSubType.getToolByID(ToolSubType.EXCEPTION_HANDLING.getInstance().getId()))),
    FILE_NOT_FOUND(new FileNotFound(3, "File Not Found Exeption", "file-not-found-exception.png", ToolSubType.getToolByID(ToolSubType.EXCEPTION_HANDLING.getInstance().getId()))),
    CRASH(new Crash(4, "Crash", "crash.png", null)),
    DUPLICATED_CODE(new CodeDuplication(5, "Código Duplicado", "duplicated-code.png", ToolSubType.getToolByID(ToolSubType.INHERITANCE.getInstance().getId()))),
    SECONDARY_EFFECTS(new SecondaryEffects(6, "Efeitos Secundários", "secondary-effects.png", ToolSubType.getToolByID(ToolSubType.FUNCTIONAL_PROGRAMMING.getInstance().getId()))),
    BSOD(new BlueScreenOfDeath(7, "Blue Screen of Death", "bsod.png", null)),
    INFINITE_LOOP(new InfiniteLoop(8, "Ciclo infinito", "infinite-loop.png", ToolSubType.getToolByID(ToolSubType.TEACHER_HELP.getInstance().getId()))),
    SEGMENTATION_FAULT(new SegmentationFault(9, "Segmentation Fault", "core-dumped.png", null)),
    UNDOCUMENTED_CODE(new UndocumentedCode(10, "Projeto sem Documentação", "undocumented-code.png", ToolSubType.getToolByID(ToolSubType.CHAT_GPT.getInstance().getId())));

    private final Abyss instance;

    AbyssSubType(Abyss instance) {
        this.instance = instance;
    }

    public Abyss getInstance() {
        return instance;
    }

    public static Abyss getAbyssByID(int id) {
        if (!GameConfig.ENABLE_ABYYS_UNDOCUMENTED_CODE && id == UNDOCUMENTED_CODE.getInstance().getId()) {
            return null;
        }

        for (AbyssSubType abyssType : values()) {
            if (abyssType.getInstance().getId() == id) {
                return abyssType.getInstance();
            }
        }

        return null;
    }

}