package pt.ulusofona.lp2.greatprogrammingjourney.enums;

public enum PlayerState {
    IN_GAME,
    DEFEATED,
    STUCK;

    @Override
    public String toString() {
        return switch (this) {
            case IN_GAME -> "Em Jogo";
            case DEFEATED -> "Derrotado";
            case STUCK -> "Preso";
        };
    }
}