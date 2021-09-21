package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Samuel CIULLA
 * Email: samuel.ciulla@portail.itlg.be
 * The Board is composed by the 12 'Cases' and the 'Reserve' section.
 * Where the Awele game physically takes place
 *
 * Updates for the GUI:
 *  - 1 method REMOVED: refreshBoard(Game game)
 *  - 1 method ADDED: resetBoard()
 */

public class Board {

    public List<Case> cases;
    public Reserve reserve;
    int seedsInit = 4;

    /**
     * Constructor
     * from 1 to 6 are the User cases
     * from 7 to 12 are the CPU cases
     * Graphic Board construction
     */
    public Board() {
        reserve = new Reserve(0, 0);
        cases = new ArrayList<>();
        for (int i=1; i<=12; i++) {
            cases.add(new Case(i, seedsInit));
        }
    }

    /**
     * Getter
     * @return list of Board Cases
     */
    public List<Case> getCases() {
        return this.cases;
    }

    /**
     * Get a Case by its spot in the Board (from 1 to 12)
     * @param emplacement spot into the board
     * @return Case object
     */
    public Case trouver(int emplacement) {
        return cases.get(emplacement-1);
    }

    /**
     * Get Board next Case
     * @param emplacement spot on the board
     * @return next Case object
     */
    public Case next(int emplacement) {
        if (emplacement == 12) return trouver(1);
        else return trouver(emplacement+1);
    }

    /**
     * Get Board previous Case
     * @param emplacement spot on the board
     * @return previous Case object
     */
    public Case previous(int emplacement) {
        if (emplacement == 1) return trouver(12);
        else return trouver(emplacement-1);
    }

    /**
     * Get the destination Case after sowing, if there are >= 12 seeds to sow
     * when we pass on 12th case we jump to the next
     * @param choice Case the player choose to sow from
     * @return Case where we sow the last seed
     */
    public Case caseDestination(Case choice) {
        int dest = choice.getEmplacement() + choice.getGraines();
        if (dest % 12 == 0) {
            dest = 12;
        } else if (dest > 12) {
            dest = dest % 12;
        }
        if (choice.getGraines() >= 12) return trouver(dest%12+1);
        else return trouver(dest);
    }

    /**
     * Reset Board, used with GUI
     */
    public void resetBoard() {
        reserve = new Reserve(0, 0);
        cases = new ArrayList<>();
        for (int i=1; i<=12; i++) {
            cases.add(new Case(i, seedsInit));
        }
    }
}
