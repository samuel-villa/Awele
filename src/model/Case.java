package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 * cases composing the Board, each case can have different states (VIDE, PLEINE or RECOLTABLE)
 * all below methods are implemented into each state cases classes
 *
 * Updates for the GUI:
 *  - 1 method ADDED: getGrainesProperty()
 */

public class Case {

    private EtatCase etatCase;
    public final EtatCase VIDE = new EtatVide();
    public final EtatCase PLEINE = new EtatPleine();
    public final EtatCase RECOLTABLE = new EtatRecoltable();
    private final int emplacement;
    private final IntegerProperty graines = new SimpleIntegerProperty();

    /**
     * Constructor
     * @param emplacement Case place on the Board (from 1 to 12)
     * @param graines number of seeds into this Case
     */
    public Case(int emplacement, int graines) {
        this.emplacement = emplacement;
        this.graines.set(graines);

        if (graines == 0) this.etatCase = VIDE;
        else if (graines == 1 || graines >= 4) this.etatCase = PLEINE;
        else if (graines == 2 || graines == 3) this.etatCase = RECOLTABLE;
    }

    /**
     * Getter
     * @return Case place on the board (1 to 12)
     */
    public int getEmplacement() {
        return emplacement;
    }

    /**
     * Getter
     * @return number of seeds contained in this Case
     */
    public int getGraines() {
        return graines.get();
    }

    /**
     * Setter, used to increase or decrease the number of seeds on this Case
     * While modifying Case nr of seeds we also modify its state
     * @param graines start with 4
     */
    public void setGraines(int graines) {
        this.graines.set(graines);

        if (graines == 0) this.etatCase = VIDE;
        else if (graines == 1 || graines >= 4) this.etatCase = PLEINE;
        else if (graines == 2 || graines == 3) this.etatCase = RECOLTABLE;
    }

    /**
     * Switch between Case states:
     * EtatVide: Case is empty
     * EtatPleine: Case contains 1 or >3 seeds
     * EtatRecoltable: Case contains 2 or 3 seeds
     */
    public void setEtatCase(EtatCase etatCase) {
        this.etatCase = etatCase;
    }

    /**
     * Getter
     * @return actual Case state
     */
    public EtatCase getEtatCase() {
        return etatCase;
    }

    /**
     * call semer() method in EtatCase interface
     */
    public void semer(Case this, Board b) {
        etatCase.semer(this, b);
    }

    /**
     * call recolter() method in EtatCase interface
     */
    public void recolter(Case this, Game game) {
        etatCase.recolter(this, game);
    }

    /**
     * call incrementerCase() method in EtatCase interface
     */
    public void incrementerCase(Case this) {
        etatCase.incrementerCase(this);
    }

    /**
     * @return Case description
     */
    @Override
    public String toString() {
        return "Case{" +
                "emplacement=" + emplacement +
                ", graines=" + graines +
                ", etatCase=" + etatCase +
                '}';
    }

    /**
     * Needed for binding this Case seeds value to the GUI Hole value
     * @return seeds property value
     */
    public IntegerProperty getGrainesProperty() {
        return graines;
    }
}
