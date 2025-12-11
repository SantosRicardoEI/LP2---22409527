package pt.ulusofona.lp2.greatprogrammingjourney;

public abstract class GameException extends RuntimeException {
  public GameException(String message) {
    super(message);
  }
}