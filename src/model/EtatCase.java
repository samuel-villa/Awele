package model;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 *
 * State Pattern, all below methods are implemented into each Case state class
 *              - Possible Case states -
 *          EtatVide: Case is empty
 *          EtatPleine: Case contains 1 or >3 seeds
 *          EtatRecoltable: Case contains 2 or 3 seeds
 */


public interface EtatCase {

    /**
     * All below methods are implemented into each Case state class
     */

    void semer(Case c, Board b);

    void recolter(Case c, Game g);

    void incrementerCase(Case c);
}
