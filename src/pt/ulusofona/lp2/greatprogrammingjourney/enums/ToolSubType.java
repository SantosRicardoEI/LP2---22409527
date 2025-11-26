package pt.ulusofona.lp2.greatprogrammingjourney.enums;

import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.Tool;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.tool.subtypes.*;

public enum ToolSubType {


    INHERITANCE(new Inheritance(0, "Herança", "inheritance.png")),
    FUNCTIONAL_PROGRAMMING(new FunctionalProgramming(1, "Programação Funcional", "functional.png")),
    UNIT_TESTS(new UnitTests(2, "Testes Unitários", "unit-tests.png")),
    EXCEPTION_HANDLING(new ExceptionHandling(3, "Tratamento de Excepções", "catch.png")),
    IDE(new IDE(4, "IDE", "IDE.png")),
    TEACHER_HELP(new TeacherHelp(5, "Ajuda do Professor", "ajuda-professor.png")),
    CHAT_GPT(new ChatGPT(6, "Chat GPT", "chatgpt.png"));

    private final Tool instance;

    ToolSubType(Tool instance) {
        this.instance = instance;
    }

    public Tool getInstance() {
        return instance;
    }

    public static Tool getToolByID(int id) {
        if (!GameConfig.ENABLE_TOOL_CHAT_GPT && id == CHAT_GPT.getInstance().getId()) {
            return null;
        }

        for (ToolSubType toolType : values()) {
            if (toolType.getInstance().getId() == id) {
                return toolType.getInstance();
            }
        }
        return null;
    }
}