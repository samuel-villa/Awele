package model;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 * CPU inherit from Player and override play() method
 * to give it the possibility to implement different strategies
 */

public class CPU extends Player {

    CPUStrategy cpuStrategy = new CPUStrategy();

    /**
     * Constructor
     * @param name CPU name inherited from Player
     */
    public CPU(String name) {
        super(name);
    }

    /**
     * CPU play method
     * @param g Game instance
     */
    @Override
    public void play(Game g) {

        userMsg = cpuStrategy.playRandom(g);
        setEtatPlayer(g);
        g.switchTurn();
    }
}
