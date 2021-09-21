package view;

import javafx.beans.property.*;
import javafx.scene.Parent;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 *
 * Grab data from last game and add it to the database
 */

public class ScoreDB extends Parent {

    private final StringProperty Winner = new SimpleStringProperty();
    private final IntegerProperty WinnerSeeds = new SimpleIntegerProperty();
    private final IntegerProperty LooserSeeds = new SimpleIntegerProperty();
    private final StringProperty GameDebutTime = new SimpleStringProperty();
    private final StringProperty GameDuration = new SimpleStringProperty();

    /**
     * Constructor
     * @param playerName user name
     * @param winnerPts seeds reaped by winner at the end of the game
     * @param loserPts seeds reaped by loser at the end of the game
     * @param start game start time
     */
    public ScoreDB(String playerName, int winnerPts, int loserPts, String start, String duration) {

        super();
        this.Winner.set(playerName);
        this.WinnerSeeds.set(winnerPts);
        this.LooserSeeds.set(loserPts);
        this.GameDebutTime.set(start);
        this.GameDuration.set(duration);
    }

    public String getWinner() {
        return Winner.get();
    }

    public StringProperty winnerProperty() {
        return Winner;
    }

    public void setWinner(String winner) {
        this.Winner.set(winner);
    }

    public int getWinnerSeeds() {
        return WinnerSeeds.get();
    }

    public IntegerProperty winnerSeedsProperty() {
        return WinnerSeeds;
    }

    public void setWinnerSeeds(int winnerSeeds) {
        this.WinnerSeeds.set(winnerSeeds);
    }

    public int getLooserSeeds() {
        return LooserSeeds.get();
    }

    public IntegerProperty looserSeedsProperty() {
        return LooserSeeds;
    }

    public void setLooserSeeds(int looserSeeds) {
        this.LooserSeeds.set(looserSeeds);
    }

    public String getGameDebutTime() {
        return GameDebutTime.get();
    }

    public StringProperty gameDebutTimeProperty() {
        return GameDebutTime;
    }

    public void setGameDebutTime(String gameDebutTime) {
        this.GameDebutTime.set(gameDebutTime);
    }

    public String getGameDuration() {
        return GameDuration.get();
    }

    public StringProperty gameDurationProperty() {
        return GameDuration;
    }

    public void setGameDuration(String gameDuration) {
        this.GameDuration.set(gameDuration);
    }
}
