package pt.ulusofona.lp2.greatprogrammingjourney.simulation;

import pt.ulusofona.lp2.greatprogrammingjourney.core.Core;

import static pt.ulusofona.lp2.greatprogrammingjourney.simulation.Dice.rollDice;

public class RunGameSimulation {

    private static final int MIN_DICE = 1;
    private static final int MAX_DICE = 6;
    private static final int NUMBER_OF_PLAYERS = 4;
    private static final int WORLD_SIZE = 80;

    // ============================== Available Players ================================================================

    private static final String[][] ALL_PLAYERS = {
            {"3", "Joshua Bloch", "Java", "PURPLE"},
            {"2", "Guido van Rossum", "Python", "BLUE"},
            {"4", "Ada Lovelace", "ADA", "GREEN"},
            {"1", "John McCarthy", "LISP", "BROWN"},
            {"5", "Bjarne Stroustrup", "CPLUSPLUS", "RED"},
            {"6", "Dennis Ritchie", "C", "GRAY"},
            {"7", "James Gosling", "JAVA", "ORANGE"},
            {"8", "Ken Thompson", "GO", "YELLOW"},
            {"9", "Anders Hejlsberg", "CSHARP", "CYAN"},
            {"10", "Brendan Eich", "JAVASCRIPT", "PINK"},
            {"11", "Yukihiro Matsumoto", "RUBY", "MAGENTA"},
            {"12", "Rasmus Lerdorf", "PHP", "LIME"},
            {"13", "Larry Wall", "PERL", "WHITE"},
            {"14", "John Backus", "FORTRAN", "BLACK"},
            {"15", "Niklaus Wirth", "PASCAL", "OLIVE"},
            {"16", "Grace Hopper", "COBOL", "NAVY"},
            {"17", "Martin Odersky", "SCALA", "MAROON"},
            {"18", "Guido Rossum Jr", "PYTHON", "TEAL"},
            {"19", "Robert Griesemer", "GO", "SILVER"},
            {"20", "Linus Torvalds", "C", "GOLD"}
    };

    public static void main(String[] args) {
        Core gm = new Core();

        String[][] players = pickFirstN(ALL_PLAYERS, NUMBER_OF_PLAYERS);
        int[] ids = extractIds(players);
        gm.createInitialBoard(players, WORLD_SIZE);

        int lastId = -1;

        while (!gm.gameIsOver()) {
            printState(gm, ids);
            lastId = gm.getCurrentPlayerId();

            int dice = rollDice(MIN_DICE, MAX_DICE);

            gm.moveCurrentPlayer(dice);
        }

        System.out.println("\nFinal State:");
        printState(gm, ids);
        System.out.println("\nGAME OVER - WINNER: " + gm.getProgrammerInfoAsStr(lastId));
    }

    // ============================== Helper Methods ===================================================================

    private static String[][] pickFirstN(String[][] src, int n) {
        if (n > src.length) {
            throw new IllegalArgumentException("Requested " + n + " players but only " + src.length + " available");
        }
        String[][] out = new String[n][4];
        for (int i = 0; i < n; i++) {
            out[i] = src[i];
        }
        return out;
    }

    private static int[] extractIds(String[][] players) {
        int[] ids = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            ids[i] = Integer.parseInt(players[i][0]);
        }
        return ids;
    }

    private static void printState(Core gm, int[] ids) {
        for (int id : ids) {
            System.out.println(gm.getProgrammerInfoAsStr(id));
        }
        System.out.println();
    }
}