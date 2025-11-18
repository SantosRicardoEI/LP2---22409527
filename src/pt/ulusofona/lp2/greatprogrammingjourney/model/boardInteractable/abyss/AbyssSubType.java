package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss;

public enum AbyssType {

    LOGIC_ERROR(0, "Logic Error", "logic.png"),
    EXCEPTION(1, "Exception", "exception.png"),
    FILE_NOT_FOUND(2, "File Not Found", "file-not-found-exception.png"),
    CRASH(3, "Crash", "crash.png"),
    MEMORY_FAULT(4, "Memory Fault", "core-dumped.png"),
    DUPLICATED_CODE(5, "Duplicated Code", "duplicated-code.png"),
    SECONDARY_EFFECTS(6, "Secondary Effects", "secondary-effects.png"),
    BSOD(7, "Blue Screen of Death", "bsod.png"),
    INFINITE_LOOP(8, "Infinite Loop", "infinite-loop.png"),
    SEGMENTATION_FAULT(9, "Segmentation Fault", "catch.png");

    // Regra: Abismo é anulado pela ferramenta cujo id é *id do abismo + 1*

    private final int id;
    private final String name;
    private final String image;

    AbyssType(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.image = icon;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public static AbyssType fromId(int id) {
        for (AbyssType t : values()) {
            if (t.getId() == id) return t;
        }
        return null;
    }
}