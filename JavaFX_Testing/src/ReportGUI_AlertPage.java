import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
    Filename: ReportGUI_AlertPage.java
    Author: Stephen James
    Date: 10/3/18

    Class objective: To create a Java Class that will be used as a page for the GUI when the user
                     makes some kind of mistake like invalid input or pressing the nextButton too early.
                     The popup will warn the user of the specific issue.
 */

public class ReportGUI_AlertPage {

    // Create all JavaFX components
    private static Label confirmLabel;
    private static Stage alertPage;
    private static Button okButton;
    private static HBox buttonBox;
    private static GridPane gridPane;


    public static void alert(String title, String message){
        // Create values for setting the alert in the center of the stage
        double x = (ReportGUI_StartPage.getStage().getWidth()/2) - (ReportGUI_StartPage.getStage().getWidth()
                /6)+ ReportGUI_StartPage.getStage().getX();

        double y = (ReportGUI_StartPage.getStage().getHeight()/2 - (ReportGUI_StartPage.getStage().getHeight()
                /6)) + ReportGUI_StartPage.getStage().getY();

        // Initialize the alertPage
        alertPage = new Stage();
        alertPage.setResizable(false);

        // Make window modal
        alertPage.initModality(Modality.APPLICATION_MODAL);

        // Set stage properties
        alertPage.setTitle(title);
        alertPage.setMinWidth(250);
        alertPage.setX(x);
        alertPage.setY(y);

        // Create the GridPane
        makeGridPane(message);

        // Create Action Handling for the okButton
        okButton.setOnAction(event -> alertPage.close());

        // Set the layout to the Scene
        Scene scene = new Scene(gridPane);
        alertPage.setScene(scene);

        // Try to set the alertPage relative to the main Stage (startPage)
        // [Work in progress]

        alertPage.showAndWait();
    }

    // Create method to create a GridPane
    private static GridPane makeGridPane(String message){
        // Create & customize GridPane
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.backgroundProperty().setValue(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setAlignment(Pos.CENTER);

        // Create button
        okButton = new Button("Ok");

        // Create label
        confirmLabel = ReportGUI_ConvenienceMethods.createLabel(confirmLabel, message, Double.MAX_VALUE, Pos.CENTER,
                "Arial", FontWeight.BOLD, 17);

        // Create HBox
        buttonBox = ReportGUI_ConvenienceMethods.createHBox(buttonBox, Pos.CENTER, Double.MAX_VALUE, 20, null,
                20, 20, 10, 10, okButton);

        // Add everything to GridPane
        gridPane.add(confirmLabel,0,0,3,1);
        gridPane.add(buttonBox,0,1,3,1);

        return gridPane;


    }


}
