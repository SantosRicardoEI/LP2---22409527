package pt.ulusofona.lp2.greatprogrammingjourney.enums;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes.*;

public enum AbyssSubType {

    SYNTAX_ERROR(new SyntaxError(0, "Erro de sintaxe", "syntax.png", ToolSubType.IDE.getInstance())),
    LOGIC_ERROR(new LogicError(1, "Erro de Lógica", "logic.png", ToolSubType.UNIT_TESTS.getInstance())),
    EXCEPTION(new ExceptionA(2, "Exception", "exception.png", ToolSubType.EXCEPTION_HANDLING.getInstance())),
    FILE_NOT_FOUND(new FileNotFound(3, "File Not Found Exeption", "file-not-found-exception.png", ToolSubType.EXCEPTION_HANDLING.getInstance())),
    CRASH(new Crash(4, "Crash", "crash.png", null)),
    DUPLICATED_CODE(new CodeDuplication(5, "Código Duplicado", "duplicated-code.png", ToolSubType.INHERITANCE.getInstance())),
    SECONDARY_EFFECTS(new SecondaryEffects(6, "Efeitos Secundários", "secondary-effects.png", ToolSubType.FUNCTIONAL_PROGRAMMING.getInstance())),
    BSOD(new BlueScreenOfDeath(7, "Blue Screen of Death", "bsod.png", null)),
    INFINITE_LOOP(new InfiniteLoop(8, "Ciclo infinito", "infinite-loop.png", ToolSubType.TEACHER_HELP.getInstance())),
    SEGMENTATION_FAULT(new SegmentationFault(9, "Segmentation Fault", "core-dumped.png", null)),
    UNDOCUMENTED_CODE(new UndocumentedCode(10, "Projeto sem Documentação", "undocumented.png", ToolSubType.CHAT_GPT.getInstance()));

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
        for (AbyssSubType t : values()) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public static Abyss createAbyss(int id) {
        AbyssSubType sub = fromId(id);
        return (sub == null) ? null : sub.getInstance();
    }
}