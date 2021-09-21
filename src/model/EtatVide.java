package model;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 * Case is empty
 */

public class EtatVide implements EtatCase {

    /**
     * Cannot sow from an empty case
     * @param c case to sow from
     * @param b Board instance
     */
    @Override
    public void semer(Case c, Board b) {
        System.out.println("Box " + c.getEmplacement() + " is empty. Select another box");

    }

    /**
     * Cannot reap if case is 'Vide'
     * If game is implemented correctly the below message shouldn't be displayed
     */
    @Override
    public void recolter(Case c, Game g) {
        System.out.println("Il n'y a pas de graines à récolter dans la case " + c.getEmplacement());
    }

    /**
     * Add/sow one seed to a case
     * @param c case to increment
     */
    @Override
    public void incrementerCase(Case c) {
        c.setGraines(c.getGraines() + 1);
    }

    /**
     * Used for testing
     * @return case state
     */
    @Override
    public String toString() {
        return "Case Vide";
    }
}
