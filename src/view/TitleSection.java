package view;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
 * Title section including a Text field (title) and an image
 */

public class TitleSection extends Parent {

    GridPane gridPane = new GridPane();

    /**
     * Constructor
     */
    public TitleSection() {

            // background
        Rectangle titleBackground = new Rectangle();
        titleBackground.setWidth(420);
        titleBackground.setHeight(160);
        titleBackground.setArcWidth(30);
        titleBackground.setArcHeight(30);
        titleBackground.setFill(
                new LinearGradient(0f, 0f, 0f, 1f, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#d8efe8")),
                        new Stop(1, Color.web("#235646")))
        );

            // title
        Text title = new Text();
        title.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 30));
        title.setText(" Awélé  ");
        title.setX(50);
        title.setY(50);

            // image
        ImageView aweleImage = new ImageView(new Image(TitleSection.class.getResourceAsStream("images/Awalé.png")));
        aweleImage.setFitHeight(150);
        aweleImage.setPreserveRatio(true);

            // ordering the title and the image into a table
        gridPane.add(title, 0, 0);
        gridPane.add(aweleImage, 1,0);

            // placing the group into the scene
        this.setTranslateX(5);
        this.setTranslateY(5);
        this.getChildren().addAll(titleBackground, gridPane);
    }
}
