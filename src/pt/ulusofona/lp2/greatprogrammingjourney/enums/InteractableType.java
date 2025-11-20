package pt.ulusofona.lp2.greatprogrammingjourney.enums;


import pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.interactable.Interactable;

public enum InteractableType {
    ABYSS,
    TOOL;

    public static InteractableType fromID(int id) {

        switch (id) {
            case 0:
                return ABYSS;
            case 1:
                return TOOL;
            default:
                return null;
        }
    }


    public static Interactable createInteractable(int typeID, int subTypeID) {
        InteractableType type = fromID(typeID);

        if (type == null) {
            return null;
        }

        switch (type) {
            case ABYSS -> {
                return AbyssSubType.createAbyss(subTypeID);
            }
            case TOOL -> {
                return ToolSubType.createTool(subTypeID);
            }
            default -> {
                return null;
            }
        }
    }
}
