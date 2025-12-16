package pt.ulusofona.lp2.greatprogrammingjourney.enums;

public enum PlayerColor {
    PURPLE,
    BLUE,
    GREEN,
    BROWN,
    RED,
    YELLOW
    ;

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}