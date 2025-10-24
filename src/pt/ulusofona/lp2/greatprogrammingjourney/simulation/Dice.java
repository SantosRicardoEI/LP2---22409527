package pt.ulusofona.lp2.greatprogrammingjourney.simulation;

public class Dice {

    private Dice() {
    }

    public static int rollDice(int minVal, int maxVal) {
        return (int) (Math.random() * (maxVal - minVal + 1)) + minVal;
    }
}