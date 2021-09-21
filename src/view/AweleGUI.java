package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Game;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Optional;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 *
 * Main method
 * 90% of all methods have been created here
 * in order to have easy access to all GUI sections
 */

public class AweleGUI extends Application {

    private static AweleGUI uniqueInstance = null;
    public TitleSection titleSection;
    public ButtonSection buttonSection;
    public ReserveSection reserveSection;
    public BoardSection boardSection;
    public InfoSection infoSection;
    public boolean fastMode = true;

        // these variables are needed for the db
    public String nameWinner = "";
    public long startTime;
    public long endTime;
    public static Text userNameText = new Text(null);
    public static int scoreWinner = 0;
    public static int scoreLooser = 0;
    public ScoreDB lastGameData;
    private Connexion connexion;

    @Override
    public void start(Stage primaryStage) {

            // connecting to the db, allows the connexion to the db from the beginning
        connexion = new Connexion("database.db");
        connexion.connect();

            // setting primary stage, global background color and dimensions
        primaryStage.setTitle("Awélé");
        primaryStage.setResizable(false);
        Group root = new Group();
        Color colorSceneBackground = Color.web("#0d201a");
        Scene scene = new Scene(root, 560, 575, colorSceneBackground);

            // set all sections into the root
        titleSection = new TitleSection();
        root.getChildren().add(titleSection);
        buttonSection = new ButtonSection();
        root.getChildren().add(buttonSection);
        reserveSection = new ReserveSection();
        root.getChildren().add(reserveSection);
        boardSection = new BoardSection();
        root.getChildren().add(boardSection);
        infoSection = new InfoSection();
        root.getChildren().add(infoSection);

            // ask user name when starting the program
        getUserName(reserveSection);

            // Actions triggered to the button sections
        buttonMethods(buttonSection, reserveSection, boardSection, primaryStage);

            // display
        primaryStage.setScene(scene);
        primaryStage.show();

            // get the game start time
        startTime = System.currentTimeMillis();

            // binding the model board seeds to the GUI
        for (Hole hole: boardSection.holes) {
            hole.seedsText.textProperty().bind(Game.getInstance().getBoard().trouver(hole.spot).getGrainesProperty().asString());
        }

            // Actions to be executed every time the mouse button is pressed and released
        scene.setOnMousePressed(me -> refreshGui(reserveSection, boardSection, primaryStage));
        scene.setOnMouseReleased(me -> cpuPlay(reserveSection, boardSection, primaryStage, infoSection));
    }

    /**
     * Refresh the Gui
     * @param reserveSection ReserveSection object
     * @param boardSection BoardSection object
     * @param ps Stage object
     */
    public void refreshGui(ReserveSection reserveSection, BoardSection boardSection, Stage ps) {
            // refreshing Score
        reserveSection.cpuScore.textProperty().bind(Game.getInstance().getBoard().reserve.getCPUScoreProperty().asString());
        reserveSection.userScore.textProperty().bind(Game.getInstance().getBoard().reserve.getUserScoreProperty().asString());

            // test if game must be stopped
        endGame(reserveSection, boardSection, ps);
    }

    /**
     * Get returned output message from model.CPUStrategy.playRandom()
     * @param infoSection InfoSection reference
     */
    public void cpuMsgProperty(InfoSection infoSection) {
        infoSection.infoMessage.textProperty().bind(Game.getInstance().getCpu().getUserMsgProperty());
    }

    /**
     * Get String variable message from model.Player.play()
     * @param infoSection InfoSection reference
     */
    public void playerMsgProperty(InfoSection infoSection) {
        infoSection.infoMessage.textProperty().bind(Game.getInstance().getUser().getUserMsgProperty());
    }

    /**
     * Get CPU play method from model
     * @param reserveSection ReserveSection object
     * @param boardSection BoardSection object
     * @param ps Stage object
     * @param is InfoSection object
     */
    private void cpuPlay(ReserveSection reserveSection, BoardSection boardSection, Stage ps, InfoSection is) {
        if (Game.getInstance().getTurn() == 1) {
            Game.getInstance().getCpu().play(Game.getInstance());
            cpuMsgProperty(is);
        }
        refreshGui(reserveSection, boardSection, ps);
    }

    /**
     * Determine if game is over
     * This method replaces the main game loop into the model
     * @param reserveSection ReserveSection reference
     * @param boardSection BoardSection reference
     * @param primaryStage Stage reference
     */
    private void endGame(ReserveSection reserveSection, BoardSection boardSection, Stage primaryStage) {

            // creating an alert window
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Awélé");
        alert.setHeaderText(null);
        ButtonType restartOption = new ButtonType("Restart");
        ButtonType changeNameOption = new ButtonType("Change Name and Restart");
        ButtonType quitOption = new ButtonType("Quit");

            // this stops the game when there aren't enough seeds
            // also, prevent the game to be an infinite loop
        if ((Game.getInstance().getTurn() == 1
                && Game.getInstance().getCpu().shortPlayer(Game.getInstance())
                && Game.getInstance().getUser().isNOURISSABLE())
                    || (Game.getInstance().getTurn() == 0
                && Game.getInstance().getUser().shortPlayer(Game.getInstance())
                && Game.getInstance().getCpu().isNOURISSABLE())) {

                // generating ScoreDB object (db)
            int cpuScore = Game.getInstance().getBoard().reserve.getCPUScore();
            int userScore = Game.getInstance().getBoard().reserve.getUserScore();
            if (userScore > cpuScore) {
                nameWinner = userNameText.getText();
                scoreWinner = Game.getInstance().getBoard().reserve.getUserScore();
                scoreLooser = Game.getInstance().getBoard().reserve.getCPUScore();
                endTime = System.currentTimeMillis();
                generateDataScoreDB(startTime, endTime);

            } else if (userScore < cpuScore) {
                nameWinner = reserveSection.cpu;
                scoreWinner = Game.getInstance().getBoard().reserve.getCPUScore();
                scoreLooser = Game.getInstance().getBoard().reserve.getUserScore();
                endTime = System.currentTimeMillis();
                generateDataScoreDB(startTime, endTime);
            }

                // display pop up
            alert.setContentText(Game.getInstance().getWinner());
            endGamePopUp(reserveSection, boardSection, primaryStage, alert, restartOption, changeNameOption, quitOption);

            // if user wins
        } else if (Game.getInstance().getBoard().reserve.getUserScore() > 24) {

                // generating ScoreDB object
            nameWinner = userNameText.getText();
            scoreWinner = Game.getInstance().getBoard().reserve.getUserScore();
            scoreLooser = Game.getInstance().getBoard().reserve.getCPUScore();
            endTime = System.currentTimeMillis();
            generateDataScoreDB(startTime, endTime);

                // display pop up
            alert.setContentText(Game.getInstance().getWinner());
            endGamePopUp(reserveSection, boardSection, primaryStage, alert, restartOption, changeNameOption, quitOption);

            // if CPU wins
        } else if (Game.getInstance().getBoard().reserve.getCPUScore() > 24) {

                // generating ScoreDB object
            nameWinner = reserveSection.cpu;
            scoreWinner = Game.getInstance().getBoard().reserve.getCPUScore();
            scoreLooser = Game.getInstance().getBoard().reserve.getUserScore();
            endTime = System.currentTimeMillis();
            generateDataScoreDB(startTime, endTime);

                // display pop up
            alert.setContentText(Game.getInstance().getWinner());
            endGamePopUp(reserveSection, boardSection, primaryStage, alert, restartOption, changeNameOption, quitOption);
        }
    }

    /**
     * When the game ends a Pop up is generated
     * This method triggers a few actions to the pop up buttons
     * @param reserveSection ReserveSection reference
     * @param boardSection BoardSection reference
     * @param primaryStage Stage reference
     * @param alert Alert reference
     * @param restartOption restart option reference
     * @param changeNameOption change name and restart option reference
     * @param quitOption quit option reference
     */
    private void endGamePopUp(ReserveSection reserveSection, BoardSection boardSection, Stage primaryStage, Alert alert, ButtonType restartOption, ButtonType changeNameOption, ButtonType quitOption) {
        alert.getButtonTypes().setAll(restartOption, changeNameOption, quitOption);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == restartOption){
            newGame(reserveSection, boardSection, primaryStage);
        } else if (result.get() == changeNameOption) {
            getUserName(reserveSection);
            newGame(reserveSection, boardSection, primaryStage);
        } else if (result.get() == quitOption) {
            quit(primaryStage);
        }
    }

    /**
     * Get the userName when starting a new game.
     * @param reserveSection reference to access userName variable
     */
    public void getUserName(ReserveSection reserveSection) {
        TextInputDialog dialog = new TextInputDialog("Name");
        dialog.setTitle("Awélé GUI");
        dialog.setHeaderText("Ready to play ?");
        dialog.setContentText("Please enter your name:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(s -> {
            Text name = new Text(s);
            reserveSection.userName.textProperty().bind(name.textProperty());
            userNameText = name;
        });
    }

    /**
     * This method triggers all actions related to the buttons into the ButtonSection
     * @param buttonSection ref to ButtonSection
     * @param reserveSection ref to ReserveSection
     * @param boardSection ref to BoardSection
     */
    public void buttonMethods(ButtonSection buttonSection, ReserveSection reserveSection, BoardSection boardSection, Stage ps) {
        for (Bouton bouton : buttonSection.buttons) {
            if (bouton.getName().equals(buttonSection.buttons[0].getName()))
                bouton.setOnMouseClicked(me -> newGame(reserveSection, boardSection, ps));
            else if (bouton.getName().equals(buttonSection.buttons[1].getName()))
                bouton.setOnMouseClicked(me -> scoresButton(ps));
            else if (bouton.getName().equals(buttonSection.buttons[2].getName()))
                bouton.setOnMouseClicked(me -> howToPlay());
        }
    }

    /**
     * Starting new game
     * @param reserveSection ReserveSection reference
     * @param boardSection BoardSection reference
     * @param ps Stage primaryStage reference
     */
    public void newGame(ReserveSection reserveSection, BoardSection boardSection, Stage ps) {
        Game.getInstance().restart();

        for (Hole hole: boardSection.holes) {
            hole.seedsText.textProperty().bind(Game.getInstance().getBoard().trouver(hole.spot).getGrainesProperty().asString());
        }

        getInfoSection().infoMessage.textProperty().bind(new Text("Select a box").textProperty());
        refreshGui(reserveSection, boardSection, ps);
        startTime = System.currentTimeMillis();
    }

    /**
     * Generate the score window composed by a TableView
     * @return score window
     */
    private Pane scoreView() {

        TableView table = new TableView(getScore());

        TableColumn scoreWinner = new TableColumn("Winner");
        scoreWinner.setCellValueFactory(new PropertyValueFactory("Winner"));
        scoreWinner.setPrefWidth(130);

        TableColumn scoreWinnerSeeds = new TableColumn("Winner Seeds");
        scoreWinnerSeeds.setCellValueFactory(new PropertyValueFactory("WinnerSeeds"));
        scoreWinnerSeeds.setPrefWidth(100);

        TableColumn scoreLoserSeeds = new TableColumn("Loser Seeds");
        scoreLoserSeeds.setCellValueFactory(new PropertyValueFactory("LooserSeeds"));
        scoreLoserSeeds.setPrefWidth(100);

        TableColumn scoreDebut = new TableColumn("Game Start");
        scoreDebut.setCellValueFactory(new PropertyValueFactory("GameDebutTime"));
        scoreDebut.setPrefWidth(150);

        TableColumn scoreDuration = new TableColumn("Duration");
        scoreDuration.setCellValueFactory(new PropertyValueFactory("GameDuration"));
        scoreDuration.setPrefWidth(90);

        table.getColumns().addAll(scoreWinner, scoreWinnerSeeds, scoreLoserSeeds, scoreDebut, scoreDuration);

        BorderPane pane = new BorderPane();
        pane.setCenter(table);

        return pane;
    }

    /**
     * Get the score from the db
     * @return observable object
     */
    private ObservableList getScore() {
        ScoreDB score;
        ObservableList scores = FXCollections.observableArrayList();

        ResultSet resultSet = connexion.query("SELECT * FROM scores");
        try {
            while (resultSet.next()) {
                score = new ScoreDB(resultSet.getString("Winner"),
                        resultSet.getInt("WinnerSeeds"),
                        resultSet.getInt("LooserSeeds"),
                        resultSet.getString("GameDebutTime"),
                        resultSet.getString("GameDuration")
                );
                scores.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    /**
     * Connects to the db and creating a new ScoreDB object collecting last game data
     * @param gameStartTime game start time in milliseconds
     * @param gameEndTime game end time in milliseconds
     */
    public void generateDataScoreDB(long gameStartTime, long gameEndTime) {

            // calculate game duration
        long durationTime = gameEndTime - gameStartTime;
        Date gameDuration = new Date(durationTime);
        Date start = new Date(gameStartTime);

            // format game start
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String startDate = dateFormat.format(start);

            // format game duration
        DateFormat dateFormatB = new SimpleDateFormat("mm:ss");
        String duration = dateFormatB.format(gameDuration);

            // connection to the db
        Connexion connexion = new Connexion("database.db");
        connexion.connect();

            // create score object
        lastGameData = new ScoreDB(
                nameWinner,
                scoreWinner,
                scoreLooser,
                startDate,
                duration);

            // add score object to the db
        connexion.addScore(lastGameData);

        connexion.close();
    }

    /**
     * Show scores by pressing button 'Scores' (db)
     */
    public void scoresButton(Stage stage) {
        String winner, winScore, looseScore, debut, duration;
        Formatter columnsDB, result;

            // opening connexion
        Connexion connexion = new Connexion("database.db");
        connexion.connect();

            // get data from db
        ResultSet resultSet = connexion.query("SELECT * FROM scores");

            // format and display db columns names
        Formatter formatter = new Formatter();
        columnsDB = formatter.format("| %-15.15s | %-8s | %-20s | %-8s |", "WINNER", "SCORE", "GAME START", "DURATION");
        System.out.println("\n" + columnsDB.toString());
        System.out.println("----------------------------------------------------------------");

            // format and display db data
        try {
            while (resultSet.next()) {

                winner = resultSet.getString("Winner");
                winScore = resultSet.getString("WinnerSeeds");
                looseScore = resultSet.getString("LooserSeeds");
                debut = resultSet.getString("GameDebutTime");
                duration = resultSet.getString("GameDuration");

                Formatter formatterDB = new Formatter();
                result = formatterDB.format("| %-15.15s | %2s vs %2s | %20s | %8s |", winner, winScore, looseScore, debut, duration);
                System.out.println(result.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("----------------------------------------------------------------");

        connexion.close();

            // create and display modal score window
        Stage scoreStage = new Stage();
        scoreStage.initOwner(stage);
        scoreStage.initModality(Modality.APPLICATION_MODAL);
        scoreStage.setTitle("Scores");
        Scene scene = new Scene(scoreView());
        scoreStage.setScene(scene);
        scoreStage.showAndWait();
    }

    /**
     * Open wikipedia website (how to play Awélé)
     */
    public void howToPlay() {
        String url = "http://en.wikipedia.org/wiki/Oware#Rules";
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Quit Awélé
     * @param primaryStage Stage reference
     */
    public void quit(Stage primaryStage) {
        primaryStage.close();
    }

    /**
     * Getter
     * @return InfoSection object
     */
    public InfoSection getInfoSection() {
        return infoSection;
    }


    /**
     * Main method
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Singleton constructor
     */
    public AweleGUI() {
        super();
        if (uniqueInstance == null)
            uniqueInstance = this;
    }

    /**
     * Give the reference of the singleton class
     * @return the reference of the singleton class
     */
    public static AweleGUI getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new AweleGUI();
        return uniqueInstance;
    }
}
