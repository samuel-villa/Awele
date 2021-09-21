package view;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 *
 * All messages to the user will be shown in this section
 */

public class InfoSection extends Parent {

    public String message;
    Text infoMessage;

    /**
     * Constructor
     */
    public InfoSection() {

            // background
        Rectangle infoBackground = new Rectangle();
        infoBackground.setWidth(550);
        infoBackground.setHeight(50);
        infoBackground.setArcWidth(30);
        infoBackground.setArcHeight(30);
        infoBackground.setFill(
                new LinearGradient(0f, 0f, 0f, 1f, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#d8efe8")),
                        new Stop(1, Color.web("#235646")))
        );

            // styling and placing the Info message
        infoMessage = new Text(message);
        infoMessage.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 18));
        infoMessage.setFill(Color.BLACK);
        infoMessage.setTranslateX(15);
        infoMessage.setTranslateY(25);

            // placing the elements into the group
        this.getChildren().addAll(infoBackground, infoMessage);
        this.setTranslateX(5);
        this.setTranslateY(520);
    }
}
