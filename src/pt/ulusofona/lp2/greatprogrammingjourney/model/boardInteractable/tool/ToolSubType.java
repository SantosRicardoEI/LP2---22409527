package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool;

public enum ToolType {

    INHERITANCE(0, "Inheritance", "inheritance.png"),
    FUNCTIONAL_PROGRAMMING(1, "Functional Programming", "functional.png"),
    UNIT_TESTS(2, "Unit Tests", "unit-tests.png"),
    EXCEPTION_HANDLING(3, "Exception Handling", "exception.png"),
    IDE(4, "IDE", "IDE.png"),
    TEACHER_HELP(5, "Teacher Help", "ajuda-professor.png");

    private final int id;
    private final String name;
    private final String icon;

    ToolType(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public static ToolType fromId(int id) {
        for (ToolType t : values()) {
            if (t.getId() == id) return t;
        }
        return null;
    }
}