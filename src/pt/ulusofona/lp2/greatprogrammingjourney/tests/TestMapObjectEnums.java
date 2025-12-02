package pt.ulusofona.lp2.greatprogrammingjourney.tests;

import org.junit.jupiter.api.Test;
import pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig;
import pt.ulusofona.lp2.greatprogrammingjourney.enums.MapObjectType;
import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;

import static org.junit.jupiter.api.Assertions.*;

public class TestMapObjectEnums {

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
        // typeID = 0 -> ABYSS
        // subTypeID = 0 -> SYNTAX_ERROR
        MapObject obj = MapObjectType.getMapObject(0, 0);

        assertNotNull(obj, "Abyss válido não deve ser null");
        assertEquals(0, obj.getId(), "ID do abyss devolvido deve ser 0 (SYNTAX_ERROR)");
    }

    @Test
    public void testGetMapObjectValidTool() {
        // typeID = 1 -> TOOL
        // subTypeID = 0 -> INHERITANCE
        MapObject obj = MapObjectType.getMapObject(1, 0);

        assertNotNull(obj, "Tool válida não deve ser null");
        assertEquals(0, obj.getId(), "ID da tool devolvida deve ser 0 (INHERITANCE)");
    }

    @Test
    public void testGetMapObjectUndocumentedAbyssRespectsConfigFlag() {
        int undocumentedId = 10;

        MapObject obj = MapObjectType.getMapObject(0, undocumentedId);

        if (GameConfig.ENABLE_ABYYS_UNDOCUMENTED_CODE) {
            assertNotNull(obj,
                    "Com ENABLE_ABYYS_UNDOCUMENTED_CODE = true, o abyss undocumented deve existir");
            assertEquals(undocumentedId, obj.getId(),
                    "ID do abyss undocumented devolvido deve ser 10");
        } else {
            assertNull(obj,
                    "Com ENABLE_ABYYS_UNDOCUMENTED_CODE = false, o abyss undocumented deve ser null");
        }
    }

    @Test
    public void testGetMapObjectChatGptToolRespectsConfigFlag() {
        int chatGptId = 6;

        MapObject obj = MapObjectType.getMapObject(1, chatGptId);

        if (GameConfig.ENABLE_TOOL_CHAT_GPT) {
            assertNotNull(obj,
                    "Com ENABLE_TOOL_CHAT_GPT = true, a tool ChatGPT deve existir");
            assertEquals(chatGptId, obj.getId(),
                    "ID da tool ChatGPT devolvida deve ser 6");
        } else {
            assertNull(obj,
                    "Com ENABLE_TOOL_CHAT_GPT = false, a tool ChatGPT deve ser null");
        }
    }

    @Test
    public void testGetMapObjectUnknownAbyssIdReturnsNull() {
        MapObject obj = MapObjectType.getMapObject(0, 999);

        assertNull(obj,
                "SubTypeId de abyss desconhecido deve devolver null");
    }

    @Test
    public void testGetMapObjectUnknownToolIdReturnsNull() {
        MapObject obj = MapObjectType.getMapObject(1, 999);

        assertNull(obj,
                "SubTypeId de tool desconhecido deve devolver null");
    }

    @Test
    public void testGetMapObjectInvalidTypeIdReturnsNull() {
        MapObject obj = MapObjectType.getMapObject(99, 0);

        assertNull(obj,
                "Tipo de objeto inválido deve devolver null em getMapObject");
    }
}