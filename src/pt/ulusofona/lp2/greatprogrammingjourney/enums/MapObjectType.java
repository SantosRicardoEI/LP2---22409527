package pt.ulusofona.lp2.greatprogrammingjourney.enums;

import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.mapobject.MapObject;

public enum MapObjectType {
    ABYSS,
    TOOL;

    public static MapObjectType fromID(int id) {

        switch (id) {
            case 0:
                return ABYSS;
            case 1:
                return TOOL;
            default:
                return null;
        }
    }

    public static MapObject createMapObject(int typeID, int subTypeID) {
        MapObjectType type = fromID(typeID);
        if (type == null) {
            return null;
        }

        switch (type) {
            case ABYSS: {
                AbyssSubType st = AbyssSubType.fromId(subTypeID);
                if (st == null) {
                    return null;
                }
                return st.getInstance();
            }

            case TOOL: {
                ToolSubType st = ToolSubType.fromId(subTypeID);
                if (st == null) {
                    return null;
                }
                return st.getInstance();
            }

            default:
                return null;
        }
    }
}
