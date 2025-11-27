package pt.ulusofona.lp2.greatprogrammingjourney.gameLogic.player.effect;

import pt.ulusofona.lp2.greatprogrammingjourney.enums.PlayerState;

public class PlayerEffect {

    private String name;
    PlayerState stateToApply;
    private int turnCounter;
    boolean active;

    public PlayerEffect(PlayerState stateOnPlayer, int turnDuration) {
        this.stateToApply = stateOnPlayer;
        this.turnCounter = 0;
        this.active = false;
        start(turnDuration);
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public PlayerState getStateToApply() {
        return stateToApply;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void start(int turnDuration) {
        if (turnDuration <= 0) {
            this.active = false;
            this.turnCounter = 0;
            return;
        }

        this.turnCounter = turnDuration;
        this.active = true;
    }


    public PlayerState run() {
        if (!active) {
            return PlayerState.IN_GAME;
        }

        turnCounter--;
        if (turnCounter <= 0) {
            active = false;
            return PlayerState.IN_GAME;
        }
        return stateToApply;
    }
}
