package pt.ulusofona.lp2.greatprogrammingjourney.model.player;

import pt.ulusofona.lp2.greatprogrammingjourney.utils.GameLogger;

public enum PlayerState {
    IN_GAME,
    DEFEATED;

    private static final GameLogger LOG = new GameLogger(PlayerState.class);

    // ============================== Overrides =======================================================================

    public static PlayerState from(String s) {
        if (s == null || s.isBlank()) {
            LOG.warn("from: received null or empty input");
            return null;
        }

        String normalized = s.trim().toUpperCase();

        try {
            return PlayerState.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            LOG.warn("from: unrecognized state name=\"" + s + "\"");
            return null;
        }
    }

    @Override
    public String toString() {
        return switch (this) {
            case IN_GAME -> "Em Jogo";
            case DEFEATED -> "Derrotado";
        };
    }
}