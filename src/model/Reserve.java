package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 * Needed to keep the score of each player
 *
 * Updates for the GUI:
 *  - 2 methods ADDED: getCPUScoreProperty(), getUserScoreProperty()
 */

public class Reserve {

    private int userScore;
    private int CPUScore;

    /**
     * Constructor
     * @param userScore set initial User score
     * @param CPUScore set initial CPU score
     */
    public Reserve(int userScore, int CPUScore) {
        this.userScore = userScore;
        this.CPUScore = CPUScore;
    }

    /**
     * Getter
     * @return User reaped seeds
     */
    public int getUserScore() {
        return userScore;
    }

    /**
     * Getter
     * @return CPU reaped seeds
     */
    public int getCPUScore() {
        return CPUScore;
    }

    /**
     * setter
     * @param userScore update User score
     */
    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    /**
     * Setter
     * @param CPUScore update CPUScore
     */
    public void setCPUScore(int CPUScore) {
        this.CPUScore = CPUScore;
    }

    /**
     * Adds reaped seeds to the score of correct player depending on Game.turn
     * @param seeds score to be added
     */
    public void incrementerReserve(int seeds, Game game) {
        if (game.getTurn() == 0)
           setUserScore(getUserScore() + seeds);
        else if (game.getTurn() == 1)
            setCPUScore(getCPUScore() + seeds);
    }

    /**
     * Convert userScore to String
     * @return userScore
     */
    public String toStringUser() {
        return "" + userScore;
    }

    /**
     * Convert CPUScore to String
     * @return CPUScore
     */
    public String toStringCpu() {
        return "" + CPUScore;
    }


    /**
     * Needed for binding CPU score value to the GUI CPU score value
     * @return CPU score property value
     */
    public IntegerProperty getCPUScoreProperty() {
        return new SimpleIntegerProperty(this.getCPUScore());
    }

    /**
     * Needed for binding User score value to the GUI User score value
     * @return User score property value
     */
    public IntegerProperty getUserScoreProperty() {
        return new SimpleIntegerProperty(this.getUserScore());
    }
}
