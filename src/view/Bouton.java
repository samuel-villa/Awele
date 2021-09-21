package view;

import javafx.scene.Parent;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 *
 * Building the buttons for the ButtonSection
 */

public class Bouton extends Parent {

    public String Name;
    private final int posY;
    Rectangle buttonBackground;
    Text buttonName;

    /**
     * Constructor
     * @param bName button name
     * @param x position in the x axe
     * @param y position in the y axe
     */
    public Bouton(String bName, int x, int y) {
        Name = bName;
        posY = y;

            // button background
        Color colorButtonBackground = Color.web("#bac0be");
        buttonBackground = new Rectangle(100,35, colorButtonBackground);
        buttonBackground.setArcHeight(20);
        buttonBackground.setArcWidth(20);

            // button background light effect
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-45.0);
        Lighting li = new Lighting();
        li.setLight(light);
        buttonBackground.setEffect(li);

            // button name
        buttonName = new Text(Name);
        buttonName.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 12));
        buttonName.setFill(Color.BLACK);
        buttonName.setX(10);
        buttonName.setY(22);

            // adding button elements to the group and placing it
        this.getChildren().add(buttonBackground);
        this.getChildren().add(buttonName);
        this.setTranslateX(x);
        this.setTranslateY(posY);

            // color changing when hovering
        this.setOnMouseEntered(me -> buttonBackground.setFill(Color.web("#9fa8a5")));
        this.setOnMouseExited(me -> buttonBackground.setFill(Color.web("#bac0be")));

            // actions triggered when pressed and released
        this.setOnMousePressed(me -> pressButton());
        this.setOnMouseReleased(me -> releaseButton());
    }

    /**
     * Trigger button design changing when pushing the button
     */
    public void pressButton() {
        buttonBackground.setFill(Color.web("#a1d9c6"));
        this.setTranslateY(posY+2);
    }

    /**
     * Trigger button design changing when releasing the button
     */
    public void releaseButton() {
        buttonBackground.setFill(Color.web("#bac0be"));
        this.setTranslateY(posY);
    }

    /**
     * Getter
     * @return button text
     */
    public String getName() {
        return Name;
    }
}
