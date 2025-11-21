package pt.ulusofona.lp2.greatprogrammingjourney.utils;

import java.util.ArrayList;

public class ResultsBuilder {

    // ============================== Constructor ======================================================================

    private ResultsBuilder() {
    }

    // ============================== Public API =======================================================================

    public static ArrayList<String> build(String title, int turnCount, ArrayList<String[]> players) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }

        if (turnCount < 0) {
            throw new IllegalArgumentException("Turn count must be non-negative");
        }

        if (players == null || players.isEmpty()) {
            return new ArrayList<>();
        }

        for (String[] info : players) {
            if (info == null || info.length < 2 || info[0] == null || info[1] == null) {
                throw new IllegalArgumentException("Invalid player info entry: missing name or position");
            }
        }

        players.sort((a, b) -> {
            try {
                int posA = Integer.parseInt(a[1]);
                int posB = Integer.parseInt(b[1]);

                int cmp = Integer.compare(posB, posA);

                if (cmp != 0) {
                    return cmp;
                }

                return a[0].compareToIgnoreCase(b[0]);

            } catch (NumberFormatException e) {
                return 0;
            }
        });

        ArrayList<String> results = new ArrayList<>();
        results.add(title);
        results.add("");
        results.add("NR. DE TURNOS");
        results.add(String.valueOf(turnCount));
        results.add("");
        results.add("VENCEDOR");
        results.add(players.getFirst()[0]);
        results.add("");
        results.add("RESTANTES");

        for (int i = 1; i < players.size(); i++) {
            String[] info = players.get(i);
            results.add(info[0] + " " + info[1]);
        }

        return results;
    }
}