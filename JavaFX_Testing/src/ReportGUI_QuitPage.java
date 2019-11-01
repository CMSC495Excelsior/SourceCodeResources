import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
    Filename: ReportGUI_QuitPage.java
    Author: Stephen James
    Date: 10/3/18

    Class objective: To create a Java Class that will be used as a page for the GUI when the user
                     selects the "quitButton". This Class will confirm that the user really does
                     want to quit. This helps prevent loss of data if the user accidentally clicks
                     the quitButton.
 */

public class ReportGUI_QuitPage {

    // Create all JavaFX components
    private static Stage quitStage;
    private static Label confirmLabel;
    private static Button yesButton, noButton;
    private static HBox buttonBox;
    private static GridPane gridPane;


   public static void display(String title, String message){
       // Create values for setting the quitPage in the center of the stage
       double x = (ReportGUI_StartPage.getStage().getWidth()/2) - (ReportGUI_StartPage.getStage().getWidth()
               /6)+ ReportGUI_StartPage.getStage().getX();

       double y = (ReportGUI_StartPage.getStage().getHeight()/2 - (ReportGUI_StartPage.getStage().getHeight()
               /6)) + ReportGUI_StartPage.getStage().getY();

       quitStage = new Stage();
       quitStage.setResizable(false);

       // Make window modal
       quitStage.initModality(Modality.APPLICATION_MODAL);

       // Set stage properties
       quitStage.setTitle(title);
       quitStage.setMinWidth(250);
       quitStage.setX(x);
       quitStage.setY(y);


       // Make the GridPane using the makeGridPane method
       makeGridPane(message);

       // Create Action Handling for the yesButton
       yesButton.setOnAction(event -> System.exit(0));

       // Create Action Handling for the noButton
       noButton.setOnAction(event -> quitStage.close());

       // Set the layout to the Scene
       Scene scene = new Scene(gridPane);
       scene.setFill(Color.RED);
       quitStage.setScene(scene);
       quitStage.showAndWait();

   }

   // Create a method for creating a GridPane
    private static GridPane makeGridPane(String message){
        // Create GridPane
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Create buttons
        yesButton = new Button("Yes");
        noButton = new Button("No");

        // Create label
        confirmLabel = ReportGUI_ConvenienceMethods.createLabel(confirmLabel, message, Double.MAX_VALUE, Pos.CENTER,
                "Arial", FontWeight.BOLD, 15);

        // Create HBox
        buttonBox = ReportGUI_ConvenienceMethods.createHBox(buttonBox, Pos.CENTER, Double.MAX_VALUE, 20, null,
                20, 20, 10, 10, yesButton, noButton);

        // Add everything to GridPane
        gridPane.add(confirmLabel,0,0,3,1);
        gridPane.add(buttonBox,0,1,3,1);

        return gridPane;

    }


}
