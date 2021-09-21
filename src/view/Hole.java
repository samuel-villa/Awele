package view;

import javafx.scene.Parent;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Game;

/**
 * @author Samuel CIULLA
 * Email: ciullasamuel@gmail.com
 *
 * Building the board holes
 */

public class Hole extends Parent {

    public int spot;
    public int nrOfSeeds;
    Circle holeBackground;
    Circle hole;
    String seedsToString;
    Text seedsText;

    /**
     * Constructor
     * @param place name (referring to model.Case.emplacement)
     * @param s number of seeds associated with this hole
     */
    public Hole(int place, int s) {

        spot = place;
        nrOfSeeds = s;

            // background
        Color colorHoleBackground = Color.web("#bac0be");
        holeBackground = new Circle();
        holeBackground.setRadius(39.0f);
        holeBackground.setFill(colorHoleBackground);

            // hole
        Color colorHole = Color.web("#7e4e4e");
        hole = new Circle();
        hole.setRadius(37.0f);
        hole.setFill(colorHole);

            // hole light effect
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);
        Lighting li = new Lighting();
        li.setLight(light);
        hole.setEffect(li);

            // number of seeds
        Color colorSeeds = Color.web("#ecf3f3");
        seedsToString = Integer.toString(nrOfSeeds);
        seedsText = new Text(seedsToString);
        seedsText.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 30));
        seedsText.setFill(colorSeeds);
        seedsText.setX(-10);
        seedsText.setY(9);

            // adding elements to the group
        this.getChildren().addAll(holeBackground, hole, seedsText);

            // color changing when hovering and not hovering, only apply to user holes
        this.setOnMouseEntered(me -> { if (this.spot < 7) holeBackground.setFill(Color.web("#09fbaa")); });
        this.setOnMouseExited(me -> holeBackground.setFill(colorHoleBackground));

            // when button is pressed and released
        this.setOnMousePressed(me -> pressButton());
        this.setOnMouseReleased(me -> {
            releaseButton();
            if (!AweleGUI.getInstance().fastMode) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Actions when button is pressed, only apply to user holes
     */
    public void pressButton() {
        InfoSection infosection = AweleGUI.getInstance().infoSection;

        if (this.spot < 7) {

                // the selected Hole will be the user model.caseChoice
            Game.getInstance().getUser().caseChoice = this.spot;
            Game.getInstance().getUser().play(Game.getInstance());
            AweleGUI.getInstance().playerMsgProperty(infosection);
        }
    }

    /**
     * Actions when button is released, only apply to user holes
     */
    public void releaseButton() {
        if (this.spot < 7) {
            hole.setFill(Color.web("#7e4e4e"));
            double y = this.getTranslateX();
            this.setTranslateY(y);
        }
    }
}
