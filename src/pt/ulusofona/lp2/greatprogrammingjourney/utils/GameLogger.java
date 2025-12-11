package pt.ulusofona.lp2.greatprogrammingjourney.utils;

import static pt.ulusofona.lp2.greatprogrammingjourney.config.GameConfig.*;

public class GameLogger {

    // ============================== State ============================================================================

    private final String className;

    // ============================ Constructor ========================================================================

    public GameLogger(Class<?> clazz) {
        this.className = clazz.getSimpleName();
    }

    // ============================== Public API =======================================================================

    public void info(String message) {
        if (DEBUG_MODE) {
            print("INFO", message, null);
        }
    }

    public void warn(String message) {
        if (DEBUG_MODE) {
            print("WARN", message, null);
        }
    }

    public void error(String message) {
        print("ERROR", message, null);
    }

    public void error(String message, Throwable t) {
        print("ERROR", message, t);
    }

    // ============================ Helper Methods =====================================================================

    private void print(String level, String message, Throwable t) {
        String color = switch (level) {
            case "INFO" -> INFO_COLOR;
            case "WARN" -> WARNING_COLOR;
            case "ERROR" -> ERROR_COLOR;
            default -> "";
        };

        boolean showLocation = level.equals("WARN") || level.equals("ERROR");

        if (showLocation) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            StackTraceElement caller = stack[4];

            System.out.printf(
                    "%s[%s] [%s.%s:%d] %s%s%n",
                    color,
                    level,
                    caller.getClassName(),
                    caller.getMethodName(),
                    caller.getLineNumber(),
                    message,
                    RESET
            );
        } else {
            System.out.printf(
                    "%s[%s] [%s] %s%s%n",
                    color,
                    level,
                    className,
                    message,
                    RESET
            );
        }

        if (t != null) {
            System.out.print(color);
            t.printStackTrace(System.out);
            System.out.print(RESET);
        }
    }
}