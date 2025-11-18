package pt.ulusofona.lp2.greatprogrammingjourney.parser;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.InteractableType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.abyss.AbyssSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.tool.ToolSubType;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;


public class InteractableParser {

    // ============================== State ============================================================================

    private static final GameLogger LOG = new GameLogger(InteractableParser.class);

    // ============================== Constructor ======================================================================

    private InteractableParser() {
    }

    // =================================== Parser ======================================================================

    public static Interactable createFromInput(String[] info) {
        if (info == null) {
            LOG.error("createFromInput: rejected — null input array");
            return null;
        }

        if (info.length < 2) {
            LOG.error("createFromInput: rejected — expected at least 2 fields (type;subType), got " + info.length);
            return null;
        }

        String typeStr = info[0] == null ? "" : info[0].trim();
        String subTypeStr = info[1] == null ? "" : info[1].trim();

        if (typeStr.isEmpty()) {
            LOG.warn("createFromInput: rejected — empty type field");
            return null;
        }

        if (subTypeStr.isEmpty()) {
            LOG.warn("createFromInput: rejected — empty subtype field");
            return null;
        }

        int type;
        try {
            type = Integer.parseInt(typeStr);
        } catch (NumberFormatException e) {
            LOG.warn("createFromInput: rejected — invalid type format (" + typeStr + ")");
            return null;
        }

        InteractableType enumType = InteractableType.fromID(type);
        if (enumType == null) {
            LOG.warn("createFromInput: rejected — unknown interactable type id (" + type + ")");
            return null;
        }

        int subType;
        try {
            subType = Integer.parseInt(subTypeStr);
        } catch (NumberFormatException e) {
            LOG.warn("createFromInput: rejected — invalid subtype format (" + subTypeStr + ")");
            return null;
        }

        Interactable result;

        switch (enumType) {
            case ABYSS:
                AbyssSubType abyssType = AbyssSubType.fromId(subType);
                if (abyssType == null) {
                    LOG.warn("createFromInput: rejected — unknown abyss id (" + subType + ")");
                    return null;
                }
                result = AbyssSubType.createAbyss(abyssType);
                break;

            case TOOL:
                ToolSubType toolType = ToolSubType.fromId(subType);
                if (toolType == null) {
                    LOG.warn("createFromInput: rejected — unknown tool id (" + subType + ")");
                    return null;
                }
                result = ToolSubType.createTool(toolType);
                break;

            default:
                LOG.error("createFromInput: rejected — unsupported InteractableType (" + type + ")");
                return null;
        }

        if (result == null) {
            LOG.error("createFromInput: failed to instantiate interactable for type="
                    + type + ", id=" + subType);
            return null;
        }

        LOG.info("createFromInput: interactable created — type="
                + type + ", id=" + subType);

        return result;
    }
}