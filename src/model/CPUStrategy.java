package model;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 * All strategies used by CPU are implemented here randomPlay(), miniMax() (soon...)
 *
 * Updates for the GUI:
 *  - 1 method UPDATED: playRandom(Game g): method output changed from void to String
 */

public class CPUStrategy {

    Case startCase;
    Case destinationCase;
    public int randomChoice;

    /**
     * the algorithm is very similar to Player.play() method with some adjustment
     * @param g Game instance providing access to all Game methods
     */
    public String playRandom(Game g) {

        String cpuChoice;
        int minCase = 7;
        int maxCase = 12;
        randomChoice = (int) (Math.random() * (maxCase - minCase + 1) + minCase);
        startCase = g.getBoard().trouver(randomChoice);

            // Box cannot be empty
        while (startCase.getEtatCase().equals(startCase.VIDE)) {
            randomChoice = (int) (Math.random() * (maxCase - minCase + 1) + minCase);
            startCase = g.getBoard().trouver(randomChoice);
            destinationCase = g.getBoard().caseDestination(g.getBoard().trouver(randomChoice));
        }
        startCase = g.getBoard().trouver(randomChoice);
        destinationCase = g.getBoard().caseDestination(g.getBoard().trouver(randomChoice));

            // If user is "nourissable" be sure we sow some seeds on his board side
        if (g.getUser().isNOURISSABLE()) {

            while (destinationCase.getEmplacement() > 6 ||
                    (destinationCase.getEmplacement() > 6 && startCase.getGraines() < 6)) {

                randomChoice = (int) (Math.random() * (maxCase - minCase + 1) + minCase);
                startCase = g.getBoard().trouver(randomChoice);
                destinationCase = g.getBoard().caseDestination(g.getBoard().trouver(randomChoice));
            }
            if (destinationCase.getEmplacement() < 7 || startCase.getGraines() > 5)
                startCase.semer(g.getBoard());

            // if user is "affamable" control the reaping action
            // "affamable" means user has "recoltable" boxes one after the other starting from his first box (1)
            // so CPU can sow and reap seeds unless the 'destination' box matches with the last 'recoltable' user box
            // in that case reaping is not possible
        } else if (g.getUser().isAFFAMABLE()) {

            while (startCase.getEtatCase().equals(startCase.VIDE)) {
                randomChoice = (int) (Math.random() * (maxCase - minCase + 1) + minCase);
                startCase = g.getBoard().trouver(randomChoice);
                destinationCase = g.getBoard().caseDestination(g.getBoard().trouver(randomChoice));
            }
            startCase.semer(g.getBoard());

            ArrayList<Case> recoltable = new ArrayList<>();
            for (Case c: g.getBoard().getCases().subList(0, 6)) {
                if (c.getGraines() > 0 && c.getGraines() < 3) {
                    recoltable.add(c);
                }
            }
            if (!(recoltable.get(recoltable.size()-1).getEmplacement() == g.getBoard().trouver(destinationCase.getEmplacement()).getEmplacement()
                    && startCase.getGraines() < 12)
                    && destinationCase.getEmplacement() < 7
                    && destinationCase.getEtatCase().equals(destinationCase.RECOLTABLE)) {
                g.getBoard().trouver(destinationCase.getEmplacement()).recolter(g);
            }

            // All other/normal cases
        } else {

            startCase.semer(g.getBoard());

            if (g.getUser().isNORMAL() && destinationCase.getEmplacement() < 7
                    && destinationCase.getEtatCase().equals(destinationCase.RECOLTABLE)) {

                g.getBoard().trouver(destinationCase.getEmplacement()).recolter(g);
            }
        }
            // Inform user about the box selected by CPU
        cpuChoice = g.messageInfo(8) + startCase.getEmplacement();
        return cpuChoice;
    }

    /**
     * This method is only used for testing.
     * It allows to put user in a certain condition
     */

        // Only for testing method
    Scanner sc = new Scanner(System.in);
    int caseChoice;

    public void TestingCPUStrategy(Game g) {

        System.out.print(g.messageInfo(8));
        caseChoice = sc.nextInt();
        try {
            startCase = g.getBoard().trouver(caseChoice);
            destinationCase = g.getBoard().caseDestination(g.getBoard().trouver(caseChoice));
        } catch (IndexOutOfBoundsException e) {
            while (caseChoice < 6 || caseChoice > 13) {
                System.out.print("CPU qui joue !");
                caseChoice = sc.nextInt();
            }
        }
        startCase = g.getBoard().trouver(caseChoice);
        destinationCase = g.getBoard().caseDestination(g.getBoard().trouver(caseChoice));
        if (caseChoice < 6 || caseChoice > 13) {
            System.out.println(g.messageInfo(6));
        } else  if (startCase.getEtatCase().equals(startCase.VIDE)) {
            startCase.semer(g.getBoard());
        } else {
            if (g.getUser().isNOURISSABLE()) {

                while (destinationCase.getEmplacement() > 6 && startCase.getGraines() < 6) {
                    System.out.println(g.messageInfo(7));
                    caseChoice = sc.nextInt();
                    startCase = g.getBoard().trouver(caseChoice);
                    destinationCase = g.getBoard().caseDestination(g.getBoard().trouver(caseChoice));
                }
                if (destinationCase.getEmplacement() < 7 || startCase.getGraines() > 5)
                    startCase.semer(g.getBoard());
            } else if (g.getUser().isAFFAMABLE()) {
                g.getUser().starvableOpponent(g);
            } else {
                startCase.semer(g.getBoard());
                if (g.getUser().isNORMAL() && destinationCase.getEmplacement() < 7
                        && destinationCase.getEtatCase().equals(destinationCase.RECOLTABLE)) {

                    g.getBoard().trouver(destinationCase.getEmplacement()).recolter(g);
                }
            }
        }
    }
}
