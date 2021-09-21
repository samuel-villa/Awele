package model;

import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 * Contains all algorithms used for the player play by following the rules of the game
 *
 *  Updates for the GUI:
 *   - 1 method UPDATED: play(Game g): * simplified because Scanner is not used anymore
 *                                       and many tests have been removed cause useless
 *                                       (ex: test if Case is not CPU's: GUI CPU's cases
 *                                       aren't clickable)
 *                                     * All infoMessages are now saved into a variable
 *                                       (UserMsg) and passed to the view package
 *   - 1 method ADDED: getUserMsgProperty()
 */

public class Player {

    private boolean NORMAL;
    private boolean NOURISSABLE;
    private boolean AFFAMABLE;
    public String userMsg = "";
    int playerBoardFirstCase;
    int playerBoardLastCase;
    int opponentBoardFirstCase;
    int opponentBoardLastCase;
    public int caseChoice;
    public Case destinationCase;
    public Case startCase;
    String name;
    Player opponent;
    int grainesBoardPlayer;
    boolean allSeedsRecoltable;
    ArrayList<Case> recoltables = new ArrayList<>();

    /**
     * constructor
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Assigns an opponent to this player
     * @param opponent other player playing against this player
     */
    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    /**
     * Getter
     * @return NORMAL state
     */
    public boolean isNORMAL() {
        return NORMAL;
    }

    /**
     * Getter
     * @return NOURISSABLE state
     */
    public boolean isNOURISSABLE() {
        return NOURISSABLE;
    }

    /**
     * Getter
     * @return AFFAMABLE state
     */
    public boolean isAFFAMABLE() {
        return AFFAMABLE;
    }

    /**
     * Setter
     * @param NORMAL true or false
     */
    public void setNORMAL(boolean NORMAL) {
        this.NORMAL = NORMAL;
    }

    /**
     * Setter
     * @param NOURISSABLE true or false
     */
    public void setNOURISSABLE(boolean NOURISSABLE) {
        this.NOURISSABLE = NOURISSABLE;
    }

    /**
     * Setter
     * @param AFFAMABLE true or false
     */
    public void setAFFAMABLE(boolean AFFAMABLE) {
        this.AFFAMABLE = AFFAMABLE;
    }

    /**
     * After a lot of tests I came to the conclusion the State Pattern is not optimal for Players cause
     * we must check the opponent state instead of user (this) state (more useful for Cases)
     * so this method uses simple booleans to set one Player state.
     * @param game Game instance
     */
    public void setEtatPlayer(Game game) {

            // setting up the Player board side. CPU class also uses this method.
        if (game.getTurn() == 0) {
            playerBoardFirstCase = 0;
            playerBoardLastCase = 6;
        } else if (game.getTurn() == 1) {
            playerBoardFirstCase = 6;
            playerBoardLastCase = 12;
        }

            // get Player board side total nr of seeds (if 0 --> 'Nourissable')
        grainesBoardPlayer = 0;
        for (Case c: game.getBoard().getCases().subList(playerBoardFirstCase, playerBoardLastCase)) {
            grainesBoardPlayer += c.getGraines();
        }

            // initialize 'recoltables' ArrayList
        recoltables.clear();

            // if case contains less than 3 seeds -> add case to Array 'recoltables'
            // (I didn't use Case state here cause when sowing we first increment
            // and then get the state, here we check the state first and then sow)
        for (Case c: game.getBoard().getCases().subList(playerBoardFirstCase, playerBoardLastCase)) {
            if (c.getGraines() < 3) {
                recoltables.add(c);
            }
        }

            // filter 1: 'recoltables' Array and remove empty cases leaving only recoltable cases (containing seeds)
        if (recoltables.size() == 6) {
            recoltables.removeIf(c -> c.getGraines() == 0);
            allSeedsRecoltable = true;
        } else {
            allSeedsRecoltable = false;
        }

            // filter 2:
        if (allSeedsRecoltable) {

                // if after removing 0s 'recoltables' is empty
            if (recoltables.size() == 0) {
                allSeedsRecoltable = false;

                // if after removing 0s 'recoltables' has only 1 element in the first spot of board
            } else if (recoltables.size() == 1 && recoltables.get(recoltables.size()-1).getEmplacement() == playerBoardFirstCase+1) {
                allSeedsRecoltable = true;

                // if after removing 0s 'recoltables' has more than 1 element starting by the first board spot
            } else if (recoltables.size() > 1 && recoltables.get(0).getEmplacement() == playerBoardFirstCase+1) {

                    // test if each Case 'emplacement' is consecutive to the next, meaning we could recolter() backwards
                for (int i = 0; i < recoltables.size()-1; i++) {
                    if (recoltables.get(i).getEmplacement() == recoltables.get(i+1).getEmplacement()-1)
                        allSeedsRecoltable = true;
                    else {
                        allSeedsRecoltable = false;
                        break;
                    }
                }
            } else {
                allSeedsRecoltable = false;
            }
        }

            // setting state
        if (grainesBoardPlayer == 0) {
            setNOURISSABLE(true);
            setNORMAL(false);
            setAFFAMABLE(false);
        } else if (allSeedsRecoltable) {
            setNOURISSABLE(false);
            setNORMAL(false);
            setAFFAMABLE(true);
        } else {
            setNORMAL(true);
            setNOURISSABLE(false);
            setAFFAMABLE(false);
        }
    }

    /**
     * if Opponent state is 'Affamable' we check his cases and if his cases are 'recoltables'
     * starting by his first boardcase then we cannot reap (we only sow)
     * Actions to be done only if Player state is 'Affamable'
     * @param game Game instance
     */
    public void starvableOpponent(Game game) {

            // setting up the Player OPPONENT board side.
        if (game.getTurn() == 0) {
            opponentBoardFirstCase = 6;
            opponentBoardLastCase = 12;
        } else if (game.getTurn() == 1) {
            opponentBoardFirstCase = 0;
            opponentBoardLastCase = 6;
        }

            // Add opponent cases to 'recoltable' new ArrayList
            // no need to test cause it has already been done while setting player state
        ArrayList<Case> recoltable = new ArrayList<>();
        for (Case c: game.getBoard().getCases().subList(opponentBoardFirstCase, opponentBoardLastCase)) {
            if (c.getGraines() > 0 && c.getGraines() < 3) {
                recoltable.add(c);
            }
        }

            // If the last element of 'recoltables' (its 'emplacement') matches with case of destination -> no reap possible
            // example           cpu:   0 - 0 - 0 - 0 - 1 - 1
            //                  user:   0 - 0 - 0 - 0 - 3 - 0
        if (recoltable.size() != 0 && recoltable.get(recoltable.size()-1).getEmplacement() == game.getBoard().trouver(destinationCase.getEmplacement()).getEmplacement()
                && startCase.getGraines() < 12) {
            startCase.semer(game.getBoard());

        } else {
            startCase.semer(game.getBoard());
            if ((game.getTurn() == 0
                    && destinationCase.getEmplacement() > 6
                    && destinationCase.getEtatCase().equals(destinationCase.RECOLTABLE)))
                game.getBoard().trouver(destinationCase.getEmplacement()).recolter(game);
        }
    }

    /**
     * If one player is 'Nourissable', meaning he has 0 seeds into his board, we check if the game can continue
     * by counting the other player seeds and test if he could get to the opponent board side by sowing.
     * @param game Game instance
     */
    public boolean shortPlayer(Game game) {

        int endGameValue = 0;

            // setting up the Player board side. (indexing)
        if (game.getTurn() == 0) {
            playerBoardFirstCase = 0;
            playerBoardLastCase = 6;
        } else if (game.getTurn() == 1) {
            playerBoardFirstCase = 6;
            playerBoardLastCase = 12;
        }

            // test if player has enough seeds to get to other side of the board
        for (Case c: game.getBoard().getCases().subList(playerBoardFirstCase, playerBoardLastCase)) {
            if (c.getGraines() <= playerBoardLastCase-c.getEmplacement()) {
                endGameValue++;
            }
        }
        return endGameValue == 6;
    }

    /**
     * Play method
     * Allow the player to sow and to reap by following the Awele rules
     * @param g Game instance
     */
    public void play(Game g) {

            // Defining Player cases
        if (this.opponent instanceof CPU) {
            playerBoardFirstCase = 0;
            playerBoardLastCase = 6;
            opponentBoardFirstCase = 6;
            opponentBoardLastCase = 12;
        } else {
            playerBoardFirstCase = 6;
            playerBoardLastCase = 12;
            opponentBoardFirstCase = 0;
            opponentBoardLastCase = 6;
        }

            // this prevent to get a useless InfoMessage in the GUI when mouse is kept pressed
        userMsg = "";

            // Getting user selected case
            // ERROR when User enter 0 due to algorithm implemented in Board.trouver()
            // ==> 0-1 goes out of bounds of the list
        try {
            startCase = g.getBoard().trouver(caseChoice);
            destinationCase = g.getBoard().caseDestination(g.getBoard().trouver(caseChoice));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        startCase = g.getBoard().trouver(caseChoice);
        destinationCase = g.getBoard().caseDestination(g.getBoard().trouver(caseChoice));

        if (caseChoice < 1 || caseChoice > 6) {
            userMsg = g.messageInfo(6);

            // test if selected box is empty
        } else if (startCase.getEtatCase().equals(startCase.VIDE)) {
            userMsg = "Box " + caseChoice + " is empty. Select another box";

            // --- TESTING OPPONENT STATE ---
        } else {

                // if opponent is Nourissable => destination MUST be >= 7 in order to feed the opponent
            if (this.opponent.isNOURISSABLE()) {

                if (destinationCase.getEmplacement() < 7 && startCase.getGraines() < 6) {
                    userMsg = g.messageInfo(7);
                    g.switchTurn();
                } else if (destinationCase.getEmplacement() > 6 || startCase.getGraines() > 5)
                    startCase.semer(g.getBoard());

                // if opponent is Affamable starvableOpponent() method
            } else if (this.opponent.isAFFAMABLE()) {

                starvableOpponent(g);

                // if opponent is 'Normal'
            } else {

                startCase.semer(g.getBoard());

                    // if opponent is Normal && dest is in CPU side && destination is Recoltable => recolter()
                if (this.opponent.isNORMAL() && destinationCase.getEmplacement() > 6
                        && destinationCase.getEtatCase().equals(destinationCase.RECOLTABLE)) {

                    g.getBoard().trouver(destinationCase.getEmplacement()).recolter(g);
                }
            }
            setEtatPlayer(g);
            g.switchTurn();
        }
    }

    /**
     * This method allows to pass userMsg variable to view package
     * @return bindable object
     */
    public SimpleStringProperty getUserMsgProperty() {
        return new SimpleStringProperty(this.userMsg);
    }

    /**
     * Simple toString method
     * @return Player description
     */
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}
