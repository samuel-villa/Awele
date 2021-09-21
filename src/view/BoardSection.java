package view;

import javafx.scene.Parent;
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
 * Board section, containing all holes.
 */

public class BoardSection extends Parent {

    public Hole[] holes;
    Text[] idHoles;
    GridPane gridPane = new GridPane();
    GridPane gridPaneNbs = new GridPane();

    /**
     * constructor
     */
    public BoardSection() {

            // board background
        Rectangle boardBackground = new Rectangle();
        boardBackground.setWidth(550);
        boardBackground.setHeight(240);
        boardBackground.setArcWidth(30);
        boardBackground.setArcHeight(30);
        boardBackground.setFill(
                new LinearGradient(0f, 0f, 0f, 1f, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#d8efe8")),
                        new Stop(1, Color.web("#235646")))
        );

            // board
        Rectangle board = new Rectangle();
        board.setWidth(540);
        board.setHeight(225);
        board.setArcWidth(150);
        board.setArcHeight(150);
        board.setTranslateX(5);
        board.setTranslateY(5);
        board.setFill(
                new LinearGradient(0f, 0f, 0f, 1f, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#9d6262")),
                        new Stop(1, Color.web("#9d9d62")))
        );

            // creating holes iD numbers
        idHoles = new Text[] {
                new Text("12"),
                new Text("11"),
                new Text("10"),
                new Text("9"),
                new Text("8"),
                new Text("7"),
                new Text("1"),
                new Text("2"),
                new Text("3"),
                new Text("4"),
                new Text("5"),
                new Text("6"),
        };

            // placing iD numbers in the right order
        int col=0, line=0;
        for (Text id: idHoles) {
            id.setFont(Font.font("monaco", FontWeight.BOLD, FontPosture.REGULAR, 13));
            id.setFill(Color.web("#123711"));
            if (col == 6) {
                line++;
                col = 0;
            }
            gridPaneNbs.add(id, col, line);
            col++;
        }

            // placing the iDs gridPane
        gridPaneNbs.setTranslateX(60);
        gridPaneNbs.setTranslateY(11);
        gridPaneNbs.setHgap(70);
        gridPaneNbs.setVgap(180);

            // separator
        Rectangle separator = new Rectangle();
        separator.setWidth(530);
        separator.setHeight(5);
        separator.setArcWidth(2);
        separator.setArcHeight(2);
        separator.setTranslateX(10);
        separator.setTranslateY(115);
        separator.setFill(
                new LinearGradient(0f, 0f, 0f, 1f, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#9d9d62")),
                        new Stop(1, Color.web("#9d6262")))
        );

            // creating the 12 holes and placing them in the right order on the BoardSection
            // in order to match each Hole 'place' with each model.Case 'emplacement'
        holes = new Hole[] {
                new Hole(12, 4),
                new Hole(11, 4),
                new Hole(10, 4),
                new Hole(9, 4),
                new Hole(8, 4),
                new Hole(7, 4),
                new Hole(1, 4),
                new Hole(2, 4),
                new Hole(3, 4),
                new Hole(4, 4),
                new Hole(5, 4),
                new Hole(6, 4)
        };

            // ordering all holes in 2 rows
        int i=0, j=0;
        for (Hole hole: holes) {
            if (i == 6) {
                j++;
                i=0;
            }
            gridPane.add(hole, i, j);
            i++;
        }

            // placing the holes
        gridPane.setTranslateX(28);
        gridPane.setTranslateY(30);
        gridPane.setHgap(5);
        gridPane.setVgap(20);

            // placing all elements into the group
        this.getChildren().addAll(boardBackground, board, separator, gridPaneNbs, gridPane);
        this.setTranslateX(5);
        this.setTranslateY(275);
    }
}
