package pt.ulusofona.lp2.greatprogrammingjourney.enums;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.Abyss;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.abyss.subtypes.*;

public enum AbyssSubType {

    SYNTAX_ERROR(0, "Erro de sintaxe", "syntax.png", ToolSubType.IDE),
    LOGIC_ERROR(1, "Erro de Lógica", "logic.png", ToolSubType.UNIT_TESTS),
    EXCEPTION(2, "Exception", "exception.png", ToolSubType.EXCEPTION_HANDLING),
    FILE_NOT_FOUND(3, "File Not Found Exeption", "file-not-found-exception.png", ToolSubType.EXCEPTION_HANDLING),
    CRASH(4, "Crash", "crash.png", null),
    DUPLICATED_CODE(5, "Código Duplicado", "duplicated-code.png", ToolSubType.INHERITANCE),
    SECONDARY_EFFECTS(6, "Efeitos Secundários", "secondary-effects.png", ToolSubType.FUNCTIONAL_PROGRAMMING),
    BSOD(7, "Blue Screen of Death", "bsod.png", null),
    INFINITE_LOOP(8, "Ciclo infinito", "infinite-loop.png", ToolSubType.TEACHER_HELP),
    SEGMENTATION_FAULT(9, "Segmentation Fault", "core-dumped.png", null);

    private final int id;
    private final String name;
    private final ToolSubType counter;
    private final String image;

    AbyssSubType(int id, String name, String icon, ToolSubType counter) {
        this.id = id;
        this.name = name;
        this.image = icon;
        this.counter = counter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ToolSubType getCounter() {
        return counter;
    }

    public String getImage() {
        return image;
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
        AbyssSubType subType = fromId(id);
        return createAbyss(subType);
    }

    public static Abyss createAbyss(AbyssSubType subType) {

        if (subType == null) {
            return null;
        }

        switch (subType) {
            case BSOD:
                return new BlueScreenOfDeath();

            case DUPLICATED_CODE:
                return new CodeDuplication();

            case SECONDARY_EFFECTS:
                return new SecondaryEffects();

            case FILE_NOT_FOUND:
                return new FileNotFound();

            case INFINITE_LOOP:
                return new InfiniteLoop();

            case LOGIC_ERROR:
                return new LogicError();

            case SYNTAX_ERROR:
                return new SyntaxError();

            case CRASH:
                return new Crash();

            case EXCEPTION:
                return new ExceptionA();

            case SEGMENTATION_FAULT:
                return new SegmentationFault();

            default:
                return null;
        }
    }
}