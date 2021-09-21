package model;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 * Case containing 1 or >3 seeds
 */

public class EtatPleine implements EtatCase {

    /**
     * sow seeds into each case depending on nr of seeds into the first case
     * also doesn't sow into start case after a full round but jump to the next case
     * (if start case has 12 or more seeds)
     * @param c start case
     * @param b Board instance
     */
    @Override
    public void semer(Case c, Board b) {
        int seedsToSow = c.getGraines();
        int step = c.getEmplacement();
        int cpt=1;
        while (seedsToSow != 0) {
            if (step != 12) step = step % 12;
            if (cpt % 12 == 0) {
                cpt = 1;
            } else {
                b.next(step).incrementerCase();
                seedsToSow--;
                cpt++;
            }
            step++;
        }
        c.setGraines(0);
    }

    /**
     * Cannot reap if case is 'Pleine'
     * If game is implemented correctly the below message shouldn't be displayed
     */
    @Override
    public void recolter(Case c, Game g) {
        System.out.println("la case " + c.getEmplacement() + " contient " + c.getGraines() + " graine(s)");
        System.out.println("On peut récolter que dans les cases où il y a 2 ou 3 graines");
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
     * @return Case state
     */
    @Override
    public String toString() {
        return "Case Pleine";
    }
}
