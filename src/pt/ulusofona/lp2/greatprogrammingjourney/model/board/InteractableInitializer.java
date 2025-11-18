package pt.ulusofona.lp2.greatprogrammingjourney.model.board;

import pt.ulusofona.lp2.greatprogrammingjourney.model.boardInteractable.Interactable;
import pt.ulusofona.lp2.greatprogrammingjourney.parser.InteractableParser;
import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.InputValidator;
import pt.ulusofona.lp2.greatprogrammingjourney.validator.ValidationResult;

public class InteractableInitializer {

    private static final GameLogger LOG = new GameLogger(InteractableInitializer.class);


    public static boolean placeInteractables(Board board, String[][] abyssesAndTools) {

        ValidationResult inputOk = InputValidator.validateInteractableInfo(abyssesAndTools);
        if (!inputOk.isValid()) {
            LOG.error("placeInteractables: " + inputOk.getMessage());
            return false;
        }

        LOG.info("placeInteractables: starting board population with " + abyssesAndTools.length + " interactables");

        for (String[] line : abyssesAndTools) {

            if (line == null || line.length < 3) {
                LOG.warn("placeInteractables: rejected — invalid line (expected 3 fields) ");
                return false;
            }

            Interactable interactable =
                    InteractableParser.createFromInput(new String[]{ line[0], line[1] });

            ValidationResult iOk = InputValidator.validateInteractableNotNull(interactable);
            if (!iOk.isValid()) {
                LOG.warn("placeInteractables: " + iOk.getMessage() +
                        " for input=" + String.join(",", line));
                return false;
            }

            Integer pos = parsePosition(line[2]);
            if (pos == null) {
                LOG.warn("placeInteractables: rejected — invalid position (" + line[2] + ")");
                return false;
            }

            if (!board.placeInteractable(interactable, pos)) {
                LOG.error("placeInteractables: could not place interactable=" +
                        interactable.getName() + " at pos=" + pos);
                return false;
            }
        }

        LOG.info("placeInteractables: all interactables placed successfully");
        return true;
    }

    private static Integer parsePosition(String posStr) {
        if (posStr == null) {
            return null;
        }

        posStr = posStr.trim();
        if (posStr.isEmpty()) {
            return null;
        }

        try {
            int pos = Integer.parseInt(posStr);
            if (pos <= 0) {
                return null;
            }
            return pos;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
