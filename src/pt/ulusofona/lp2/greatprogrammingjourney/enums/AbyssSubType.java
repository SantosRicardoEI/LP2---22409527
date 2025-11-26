package pt.ulusofona.lp2.greatprogrammingjourney.enums;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes.*;

public enum AbyssSubType {

    SYNTAX_ERROR(new SyntaxError(0, "Erro de sintaxe", "syntax.png", ToolSubType.getTool(ToolSubType.IDE.getId()))),
    LOGIC_ERROR(new LogicError(1, "Erro de Lógica", "logic.png", ToolSubType.getTool(ToolSubType.UNIT_TESTS.getId()))),
    EXCEPTION(new ExceptionA(2, "Exception", "exception.png", ToolSubType.getTool(ToolSubType.EXCEPTION_HANDLING.getId()))),
    FILE_NOT_FOUND(new FileNotFound(3, "File Not Found Exeption", "file-not-found-exception.png", ToolSubType.getTool(ToolSubType.EXCEPTION_HANDLING.getId()))),
    CRASH(new Crash(4, "Crash", "crash.png", null)),
    DUPLICATED_CODE(new CodeDuplication(5, "Código Duplicado", "duplicated-code.png", ToolSubType.getTool(ToolSubType.INHERITANCE.getId()))),
    SECONDARY_EFFECTS(new SecondaryEffects(6, "Efeitos Secundários", "secondary-effects.png", ToolSubType.getTool(ToolSubType.FUNCTIONAL_PROGRAMMING.getId()))),
    BSOD(new BlueScreenOfDeath(7, "Blue Screen of Death", "bsod.png", null)),
    INFINITE_LOOP(new InfiniteLoop(8, "Ciclo infinito", "infinite-loop.png", ToolSubType.getTool(ToolSubType.TEACHER_HELP.getId()))),
    SEGMENTATION_FAULT(new SegmentationFault(9, "Segmentation Fault", "core-dumped.png", null)),
    UNDOCUMENTED_CODE(new UndocumentedCode(10, "Projeto sem Documentação", "unknownPiece.png", ToolSubType.getTool(ToolSubType.CHAT_GPT.getId())));

    private final Abyss instance;

    AbyssSubType(Abyss instance) {
        this.instance = instance;
    }

    public int getId() {
        return instance.getId();
    }

    public String getName() {
        return instance.getName();
    }

    public String getImage() {
        return instance.getPng();
    }

    public Abyss getInstance() {
        return instance;
    }

    public static AbyssSubType fromId(int id) {

        if (!GameConfig.ENABLE_ABYYS_NONAMEYET && id == UNDOCUMENTED_CODE.getId()) {
            return null;
        }

        for (AbyssSubType t : values()) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public static Abyss getAbyss(int id) {
        AbyssSubType sub = fromId(id);
        return (sub == null) ? null : sub.getInstance();
    }

}