package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes.*;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.subtypes.Exception;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.ToolSubType;

public enum AbyssSubType {

    //TODO
    // Corrigir counters quando o bob ganhar juízo, para ja vou deixar todos iguais

    LOGIC_ERROR(0, "Logic Error", "logic.png", ToolSubType.INHERITANCE),
    EXCEPTION(1, "Exception", "exception.png", ToolSubType.INHERITANCE),
    FILE_NOT_FOUND(2, "File Not Found", "file-not-found-exception.png", ToolSubType.INHERITANCE),
    CRASH(3, "Crash", "crash.png", ToolSubType.INHERITANCE),
    MEMORY_FAULT(4, "Memory Fault", "core-dumped.png", ToolSubType.INHERITANCE),
    DUPLICATED_CODE(5, "Duplicated Code", "duplicated-code.png", ToolSubType.INHERITANCE),
    SECONDARY_EFFECTS(6, "Secondary Effects", "secondary-effects.png", ToolSubType.INHERITANCE),
    BSOD(7, "Blue Screen of Death", "bsod.png", ToolSubType.INHERITANCE),
    INFINITE_LOOP(8, "Infinite Loop", "infinite-loop.png", ToolSubType.INHERITANCE),
    SEGMENTATION_FAULT(9, "Segmentation Fault", "catch.png", ToolSubType.INHERITANCE);

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

    public static Abyss createAbyss(AbyssSubType type) {
        switch (type) {
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

            case MEMORY_FAULT:
                return new MemoryFault();

            case CRASH:
                return new Crash();

            case EXCEPTION:
                return new Exception();

            case SEGMENTATION_FAULT:
                return new SegmentationFault();

            default:
                return null;
        }
    }
}