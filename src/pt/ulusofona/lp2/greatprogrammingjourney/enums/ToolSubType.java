package pt.ulusofona.lp2.greatprogrammingjourney.enums;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.subtypes.*;

public enum ToolSubType {

    INHERITANCE(0, "Herança", "inheritance.png"),
    FUNCTIONAL_PROGRAMMING(1, "Programação Funcional", "functional.png"),
    UNIT_TESTS(2, "Testes Unitários", "unit-tests.png"),
    EXCEPTION_HANDLING(3, "Tratamento de Excepções", "catch.png"),
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


    public static Tool createTool(int toolID) {
        ToolSubType subType = fromId(toolID);
        return createTool(subType);
    }

    public static Tool createTool(ToolSubType subType) {
        return (subType == null) ? null : switch (subType) {
            case TEACHER_HELP -> new TeacherHelp();
            case EXCEPTION_HANDLING -> new ExceptionHandling();
            case IDE -> new IDE();
            case FUNCTIONAL_PROGRAMMING -> new FunctionalProgramming();
            case INHERITANCE -> new Inheritance();
            case UNIT_TESTS -> new UnitTests();
        };
    }
}