import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;


/*
    Filename: ReportGUI_EmailPage.java
    Author: Stephen James
    Date: 10/3/18

    Class objective: To create a Java Class that will be used as the last page for the GUI. The page
                     will thank the user for using Error Report. This page will have two Labels and
                     a finishButton. Once clicked, the finishButton will terminate the program.
 */


public class ReportGUI_EndPage {

    // Create JavaFX components
    private static Label successLabel, summaryLabel, thankYouLabel;
    private static HBox buttonsBox;
    private static VBox summaryBox;
    private static BorderPane borderPane;
    private static GridPane gridPane;
    private static Button finishButton;


    public static Scene startEndPage(String message,String thankYou, String summary){
        // Make the GridPane
        makeGridPane(message, thankYou, summary);

        //Create Action Handling for the finishButton
        finishButton.setOnAction(event -> System.exit(0));

        // Create new BorderPane to nest layouts inside
        borderPane = ReportGUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE, null, buttonsBox,
                null, null, gridPane);

        // Set the layout to the scene
        Scene endScene = new Scene(borderPane);

        return endScene;
    }


    // Create a method to create a GridPane
    private static GridPane makeGridPane(String message, String thankYou, String summary){
        // Make gridPane
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create Button
        finishButton = new Button("Finish");

        // Create Labels
        successLabel = ReportGUI_ConvenienceMethods.createLabel(successLabel, message, Double.MAX_VALUE, Pos.CENTER,
                "Arial", FontWeight.BOLD, 20);
        successLabel.setPadding(new Insets(30));

        summaryLabel = ReportGUI_ConvenienceMethods.createLabel(summaryLabel, summary, Double.MAX_VALUE, Pos.CENTER,
                "Arial", FontWeight.BOLD, 18);
        summaryLabel.setPadding(new Insets(30, 0,0,0));

        thankYouLabel = ReportGUI_ConvenienceMethods.createLabel(thankYouLabel, thankYou, Double.MAX_VALUE, Pos.CENTER,
                "Arial", FontWeight.BOLD, 18);

        // Create VBox
        summaryBox = ReportGUI_ConvenienceMethods.createVBox(summaryBox, Pos.CENTER, Double.MAX_VALUE, 30,
                null, 20, 20, 10, 10, thankYouLabel, summaryLabel);

        // Create HBox
        buttonsBox = ReportGUI_ConvenienceMethods.createHBox(buttonsBox, Pos.CENTER, Double.MAX_VALUE, 50,
                null, 20, 20, 10, 10, finishButton);

        gridPane.add(successLabel, 0,0,1,1);
        gridPane.add(summaryBox, 0,1,1,1);
        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }
}
