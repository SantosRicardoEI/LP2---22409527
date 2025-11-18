package pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable;

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
}
