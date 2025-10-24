package pt.ulusofona.lp2.greatprogrammingjourney.utils;

import java.util.ArrayList;

public class ResultsBuilder {

    // ============================== Constructor ======================================================================

    private ResultsBuilder() {
    }

    // ============================== Public API =======================================================================

    public static ArrayList<String> build(String title, int turnCount, String winnerName, ArrayList<String[]> defeatedInfo) {
        if (title == null || title.isBlank() || winnerName == null || winnerName.isBlank()) {
            throw new IllegalArgumentException("Title and winner name cannot be null or blank");
        }

        if (turnCount < 0) {
            throw new IllegalArgumentException("Turn count must be non-negative");
        }

        if (defeatedInfo == null || defeatedInfo.isEmpty()) {
            return new ArrayList<>();
        }

        for (String[] info : defeatedInfo) {
            if (info == null || info.length < 2 || info[0] == null || info[1] == null) {
                throw new IllegalArgumentException("Invalid player info entry: missing name or position");
            }
        }

        defeatedInfo.sort((a, b) -> {
            try {
                int posA = Integer.parseInt(a[1]);
                int posB = Integer.parseInt(b[1]);
                return Integer.compare(posB, posA);
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
        results.add(winnerName);
        results.add("");
        results.add("RESTANTES");

        for (String[] info : defeatedInfo) {
            results.add(info[0] + " " + info[1]);
        }

        return results;
    }
}