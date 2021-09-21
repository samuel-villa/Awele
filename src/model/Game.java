package model;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 * Game masterclass
 * Containing the game global attributes - Singleton class
 *
 *  Updates for the GUI:
 *   - 1 method ADDED: restart()
 *   - 2 method UPDATED: getWinner(): output has changed
 *                       Game(): constructor simplified, the game loop has been removed
 */

public class Game {

    private final Board board;
    private final Player user;
    private final Player cpu;
    private boolean playing = true;
    private int turn = 0;

    /**
     * Getter
     * @return get User instance
     */
    public Player getUser() {
        return user;
    }

    /**
     * Getter
     * @return get CPU instance
     */
    public Player getCpu() {
        return cpu;
    }

    /**
     * Getter
     * @return true if playing, false when the game is over
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Getter
     * @return 0 if it's User turn, 1 if it's CPU turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Getter, board gives access to all single Case objects
     * @return Board object
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Setter
     * @param playing false if game is over else true
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    /**
     * Setter
     * @param turn 0 for User, 1 for CPU
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Switch between User turn (0) and CPU turn (1)
     * replaces the Setter
     */
    public void switchTurn() {
        if (turn == 0) setTurn(1);
        else if (turn == 1) setTurn(0);
    }

    /**
     * We get a winner by counting the seeds reaped by each player
     */
    public String getWinner() {

        String winnerMsg;
        int cpuScore = getBoard().reserve.getCPUScore();
        int userScore = getBoard().reserve.getUserScore();

        setPlaying(false);

        if (cpuScore > userScore) {
            //System.out.println(messageInfo(2));
            //System.out.println("CPU has reaped " + cpuScore + " seeds.");
            winnerMsg = messageInfo(2) + "\n" + "CPU has reaped " + cpuScore + " seeds.";
        } else if (cpuScore < userScore) {
            //System.out.println(messageInfo(3));
            //System.out.println("You have reaped " + userScore + " seeds.");
            winnerMsg = messageInfo(3) + "\n" + "You have reaped " + userScore + " seeds.";
        } else {
            //System.out.println(messageInfo(4));
            winnerMsg = messageInfo(4);
        }
        return winnerMsg;
    }

    /**
     * All messages and pop ups collected in one method.
     * @param value message value
     * @return String related to value
     */
    public String messageInfo(int value) {

        String message = "";

        switch(value) {

            case 0:
                message = "Loading new game...";
                break;
            case 1:
                message = "Insert your name: ";
                break;
            case 2:
                message = "You Loose !";
                break;
            case 3:
                message = "You Win !";
                break;
            case 4:
                message = "You both Win ;)";
                break;
            case 5:
                message = "Select a box from 1 to 6: ";
                break;
            case 6:
                message = "You cannot sow your opponent seeds";
                break;
            case 7:
                message = "You must feed your opponent, select another box";
                break;
            case 8:
                message = "CPU selected box ";
                break;
        }
        return message;
    }

    /**
     * Call Board.resetBoard()
     */
    public void restart() {
        getBoard().resetBoard();
    }

    /*------------------------- Singleton Section ------------------------*/

    /**
     * instantiate unique ('final') Game object
     */
    private static final Game instance = new Game();

    /**
     * Singleton constructor
     * Set up the game and display the Board
     */
    private Game() {
        board = new Board();
        user = new Player("");
        cpu = new CPU("CPU");
        cpu.setOpponent(user);
        user.setOpponent(cpu);
    }

    /**
     * Getting the private instance of Game()
     * @return unique Game instance
     */
    public static Game getInstance() {
        return instance;
    }
}