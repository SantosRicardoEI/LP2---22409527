package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.subtypes.*;

public enum ToolSubType {

    INHERITANCE(0, "Inheritance", "inheritance.png"),
    FUNCTIONAL_PROGRAMMING(1, "Functional Programming", "functional.png"),
    UNIT_TESTS(2, "Unit Tests", "unit-tests.png"),
    EXCEPTION_HANDLING(3, "Exception Handling", "exception.png"),
    IDE(4, "IDE", "IDE.png"),
    TEACHER_HELP(5, "Teacher Help", "ajuda-professor.png");

    private final int id;
    private final String name;
    private final String icon;

    ToolSubType(int id, String name, String icon) {
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

    public static ToolSubType fromId(int id) {
        for (ToolSubType t : values()) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public static Tool createTool(ToolSubType type) {
        switch (type) {
            case TEACHER_HELP:
                return new TeacherHelp();
            case EXCEPTION_HANDLING:
                return new ExceptionHandling();
            case IDE:
                return new IDE();
            case FUNCTIONAL_PROGRAMMING:
                return new FunctionalProgramming();
            case INHERITANCE:
                return new Inheritance();
            case UNIT_TESTS:
                return new UnitTests();
            default:
                return null;
        }
    }
}