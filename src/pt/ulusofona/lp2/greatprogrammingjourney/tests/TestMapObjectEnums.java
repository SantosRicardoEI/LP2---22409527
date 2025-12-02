package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.MapObjectType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;

import static org.junit.jupiter.api.Assertions.*;

public class TestMapObjectEnums {

    @BeforeEach
    public void resetConfig() {
        GameConfig.ENABLE_TOOL_CHAT_GPT = true;
        GameConfig.ENABLE_ABYYS_UNDOCUMENTED_CODE = true;
    }

    @Test
    public void testFromIDValidAndInvalid() {
        assertEquals(MapObjectType.ABYSS, MapObjectType.fromID(0),
                "ID 0 deve mapear para ABYSS");
        assertEquals(MapObjectType.TOOL, MapObjectType.fromID(1),
                "ID 1 deve mapear para TOOL");
        assertNull(MapObjectType.fromID(99),
                "IDs desconhecidos devem devolver null");
    }

    @Test
    public void testGetMapObjectValidAbyss() {
        GameConfig.ENABLE_ABYYS_UNDOCUMENTED_CODE = true;

        MapObject obj = MapObjectType.getMapObject(0, 0);

        assertNotNull(obj, "Abyss válido não deve ser null");
        assertEquals(0, obj.getId(), "ID do abyss devolvido deve ser 0 (SYNTAX_ERROR)");
    }

    @Test
    public void testGetMapObjectValidTool() {
        GameConfig.ENABLE_TOOL_CHAT_GPT = true;

        MapObject obj = MapObjectType.getMapObject(1, 0);

        assertNotNull(obj, "Tool válida não deve ser null");
        assertEquals(0, obj.getId(), "ID da tool devolvida deve ser 0 (INHERITANCE)");
    }

    @Test
    public void testGetMapObjectUndocumentedAbyssDisabledAndEnabled() {
        int undocumentedId = 10;

        GameConfig.ENABLE_ABYYS_UNDOCUMENTED_CODE = false;
        MapObject disabled = MapObjectType.getMapObject(0, undocumentedId);
        assertNull(disabled,
                "Quando ENABLE_ABYYS_UNDOCUMENTED_CODE é false, o abyss undocumented deve ser null");

        GameConfig.ENABLE_ABYYS_UNDOCUMENTED_CODE = true;
        MapObject enabled = MapObjectType.getMapObject(0, undocumentedId);
        assertNotNull(enabled,
                "Quando ENABLE_ABYYS_UNDOCUMENTED_CODE é true, o abyss undocumented deve ser criado");
        assertEquals(undocumentedId, enabled.getId(),
                "ID do abyss undocumented devolvido deve ser 10");
    }

    @Test
    public void testGetMapObjectChatGptToolDisabledAndEnabled() {
        int chatGptId = 6;

        GameConfig.ENABLE_TOOL_CHAT_GPT = false;
        MapObject disabled = MapObjectType.getMapObject(1, chatGptId);
        assertNull(disabled,
                "Quando ENABLE_TOOL_CHAT_GPT é false, a tool ChatGPT deve ser null");

        GameConfig.ENABLE_TOOL_CHAT_GPT = true;
        MapObject enabled = MapObjectType.getMapObject(1, chatGptId);
        assertNotNull(enabled,
                "Quando ENABLE_TOOL_CHAT_GPT é true, a tool ChatGPT deve ser criada");
        assertEquals(chatGptId, enabled.getId(),
                "ID da tool ChatGPT devolvida deve ser 6");
    }

    @Test
    public void testGetMapObjectUnknownAbyssIdReturnsNull() {
        GameConfig.ENABLE_ABYYS_UNDOCUMENTED_CODE = true;

        MapObject obj = MapObjectType.getMapObject(0, 999);

        assertNull(obj,
                "SubTypeId de abyss desconhecido deve devolver null (ramo final de AbyssSubType.getAbyssByID)");
    }

    @Test
    public void testGetMapObjectUnknownToolIdReturnsNull() {
        GameConfig.ENABLE_TOOL_CHAT_GPT = true;

        MapObject obj = MapObjectType.getMapObject(1, 999);

        assertNull(obj,
                "SubTypeId de tool desconhecido deve devolver null (ramo final de ToolSubType.getToolByID)");
    }

    @Test
    public void testGetMapObjectInvalidTypeIdReturnsNull() {
        MapObject obj = MapObjectType.getMapObject(99, 0);

        assertNull(obj,
                "Tipo de objeto inválido deve devolver null em getMapObject");
    }
}