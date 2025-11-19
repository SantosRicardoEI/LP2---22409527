package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.subtypes.*;

public enum ToolSubType {

    INHERITANCE(0, "Herança", "inheritance.png"),
    FUNCTIONAL_PROGRAMMING(1, "Programação Funcional", "functional.png"),
    UNIT_TESTS(2, "Testes Unitários", "unit-tests.png"),
    EXCEPTION_HANDLING(3, "Tratamento de Excepções", "exception.png"),
    IDE(4, "IDE", "IDE.png"),
    TEACHER_HELP(5, "Ajuda do Professor", "ajuda-professor.png");

    private final int id;
    private final String name;
    private final String image;

    ToolSubType(int id, String name, String icon) {
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

    public static ToolSubType fromId(int id) {
        for (ToolSubType t : values()) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public static Tool createTool(ToolSubType type) {

        if (type == null) {
            return null;
        }

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