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
        return (type == null) ? null : switch (type) {
            case ABYSS -> AbyssSubType.getAbyss(subTypeID);
            case TOOL  -> ToolSubType.getTool(subTypeID);
        };
    }
}
