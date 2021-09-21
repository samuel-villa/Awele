package view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import model.Game;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 *
 * Score section, first player is always CPU, second is always the User.
 */

public class ReserveSection extends Parent {

    GridPane gridPane = new GridPane();
    String cpu, user;
    String cpuPtsToString, userPtsToString;
    Text cpuName, cpuScore, userName, userScore;

    /**
     * Constructor
     */
    public ReserveSection() {

            // background
        Rectangle reserveBackground = new Rectangle();
        reserveBackground.setWidth(550);
        reserveBackground.setHeight(100);
        reserveBackground.setArcWidth(30);
        reserveBackground.setArcHeight(30);
        reserveBackground.setFill(
                new LinearGradient(0f, 0f, 0f, 1f, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#d8efe8")))
        );

            // CPU score section (background)
        Color c = Color.web("#296652");
        Rectangle CPUBackground = new Rectangle();
        CPUBackground.setWidth(267);
        CPUBackground.setHeight(90);
        CPUBackground.setArcWidth(30);
        CPUBackground.setArcHeight(30);
        CPUBackground.setX(5);
        CPUBackground.setY(5);
        CPUBackground.setFill(c);

            // player score section (background)
        Rectangle playerBackground = new Rectangle();
        playerBackground.setWidth(267);
        playerBackground.setHeight(90);
        playerBackground.setArcWidth(30);
        playerBackground.setArcHeight(30);
        playerBackground.setFill(c);

            // adding and placing the 2 players section to a gridpane
        gridPane.add(CPUBackground, 0, 0);
        gridPane.add(playerBackground, 1, 0);
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(5);

            // setting players names and score
        cpu = "Machine";
        cpuName = new Text(cpu);
        cpuName.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 25));
        cpuName.setFill(Color.LIGHTGREY);
        cpuName.setX(85);
        cpuName.setY(35);

            // get CPU pts from model and convert it to bindable object (for displaying purpose)
        cpuPtsToString = Integer.toString(Game.getInstance().getBoard().reserve.getCPUScore());
        cpuScore = new Text(cpuPtsToString);
        cpuScore.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 35));
        cpuScore.setFill(Color.LIGHTGREY);
        cpuScore.setX(115);
        cpuScore.setY(80);

            // bindable object used by method AweleGUI.getUserName()
        userName = new Text(user);
        userName.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 25));
        userName.setFill(Color.LIGHTGREY);
        userName.setX(370);
        userName.setY(35);

            // get User pts from model and convert it to bindable object (for displaying purpose)
        userPtsToString = Integer.toString(Game.getInstance().getBoard().reserve.getUserScore());
        userScore = new Text(userPtsToString);
        userScore.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 35));
        userScore.setFill(Color.LIGHTGREY);
        userScore.setX(390);
        userScore.setY(80);

            // placing the elements into the group
        this.getChildren().add(reserveBackground);
        this.setTranslateX(5);
        this.setTranslateY(170);
        this.getChildren().add(gridPane);
        this.getChildren().addAll(cpuName, cpuScore, userName, userScore);
    }
}
