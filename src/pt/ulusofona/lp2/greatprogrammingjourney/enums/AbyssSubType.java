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
        return (subType == null) ? null : switch (subType) {
            case BSOD -> new BlueScreenOfDeath();
            case DUPLICATED_CODE -> new CodeDuplication();
            case SECONDARY_EFFECTS -> new SecondaryEffects();
            case FILE_NOT_FOUND -> new FileNotFound();
            case INFINITE_LOOP -> new InfiniteLoop();
            case LOGIC_ERROR -> new LogicError();
            case SYNTAX_ERROR -> new SyntaxError();
            case CRASH -> new Crash();
            case EXCEPTION -> new ExceptionA();
            case SEGMENTATION_FAULT -> new SegmentationFault();
        };
    }
}