package model;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 * Case contains 2 or 3 seeds
 */

public class EtatRecoltable implements EtatCase {

    /**
     * Sow seeds in next cases depending on nr of seeds into start case
     * Different method compared to EtatPleine.semer()
     * cause EtatRecoltable cannot have more than 3 seeds to sow
     * @param c start case
     * @param b Board instance
     */
    @Override
    public void semer(Case c, Board b) {
        int seedsToSow = c.getGraines();
        for (int seeds = 1, j=c.getEmplacement(); seeds <= seedsToSow; seeds++, j++) {
            if (j==13) j = 1;
            b.next(j).incrementerCase();
        }
        c.setGraines(0);
    }

    /**
     * Reap seeds from a case and update score
     * Also, if previous case is 'Recoltable', implement method recursively
     * @param c Case to reap from
     * @param g Game instance
     */
    @Override
    public void recolter(Case c, Game g) {
        int end=0;
        if (g.getTurn() == 0) {
            end = 6;
        } else if (g.getTurn() == 1) {
            end = 12;
        }
        g.getBoard().reserve.incrementerReserve(c.getGraines(), g);
        c.setGraines(0);
        Case previous = g.getBoard().previous(c.getEmplacement());
        if (previous.getEtatCase().equals(previous.RECOLTABLE) && previous.getEmplacement() != end) {
            recolter(previous, g);
        }
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
     * @return state case
     */
    @Override
    public String toString() {
        return "Case Recoltable";
    }
}
