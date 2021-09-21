package view;

import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 *
 * Section containing all Bouton objects
 */

public class ButtonSection extends Parent {

    Bouton[] buttons;
    CheckBox mode;

    /**
     * Constructor
     */
    public ButtonSection() {

            // background
        Rectangle butSecBackground = new Rectangle();
        butSecBackground.setWidth(125);
        butSecBackground.setHeight(160);
        butSecBackground.setArcWidth(30);
        butSecBackground.setArcHeight(30);
        butSecBackground.setFill(
                new LinearGradient(0f, 0f, 0f, 1f, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#d8efe8")),
                        new Stop(1, Color.web("#235646")))
        );

            // creating buttons
        String button1 = "New Game";
        String button2 = "Scores";
        String button3 = "How to play";
        buttons = new Bouton[] {
                new Bouton(button1, 12, 7),
                new Bouton(button2, 12, 47),
                new Bouton(button3, 12, 88)
        };

            // creating the FastMode checkBox
        mode = new CheckBox("Fast Mode");
        mode.setSelected(true);
        mode.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 13));
        mode.setTextFill(Color.BLACK);
        mode.setTranslateX(14);
        mode.setTranslateY(130);
        mode.setOnAction(e -> activateFastMode(mode));

            // placing the group into the scene
        this.setTranslateX(430);
        this.setTranslateY(5);
        this.getChildren().add(butSecBackground);
        for (Bouton bouton: buttons) {
            this.getChildren().add(bouton);
        }
        this.getChildren().add(mode);
    }

    /**
     * Action triggered with checkbox state
     * if selected -> FastMode. If not -> CPU 'thinks' for 2 sec
     * @param mode checkbox
     */
    private void activateFastMode(CheckBox mode) {
        AweleGUI.getInstance().fastMode = mode.isSelected();
    }
}
