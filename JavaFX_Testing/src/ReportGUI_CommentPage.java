import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

/*
    Filename: ReportGUI_CommentPage.java
    Author: Stephen James
    Date: 10/3/18

    Class objective: To create a Java Class that will be used as a page for the GUI where the user can enter
                     additional comments to explain their issue further. The commentPage will only consist of
                     a TextArea for the user to enter a detailed explanation of their issue, the basic navigation
                     buttons (nextButton, backButton and quitButton) and a greeting label.
 */

public class ReportGUI_CommentPage {

    // Create JavaFX Components
    private static Label greetingLabel;
    private static HBox buttonBox;
    private static BorderPane borderPane;
    private static GridPane gridPane;
    private static TextArea textArea;
    private static Button nextButton, backButton, quitButton;

    // Create class variables
    private static String comments;

    public static Scene startCommentPage(String message){
        // Create GridPane by using method makeGridPane (using the same parameters as the start method)
        makeGridPane(message);

        // Add a BorderPane
        borderPane = ReportGUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE, greetingLabel,
                buttonBox, null, null, gridPane);
        // Also set BorderPane padding
        borderPane.setPadding(new Insets(30,10,30,10));

        // Create Action Handling for the nextButton
        nextButton.setOnAction(event -> {
            if(textArea.getText().isEmpty()){
                ReportGUI_AlertPage.alert("ALERT", "Please enter additional comments");
            }
            else{
                comments = textArea.getText();
                ReportGUI_StartPage.getStage().setScene(ReportGUI_ReviewPage.startReviewPage(
                        "Please review your information"));
            }
        });
        // Create Action Handling for the backButton
        backButton.setOnAction(event -> {
            if(ReportGUI_SelectErrorPage.getDisplayErrorsTrue()){
                ReportGUI_StartPage.getStage().setScene(ReportGUI_SurveyPage.startSurveyPage("Please complete the survey",
                        "Which OS do you have?", "1. Are your screens flickering?",
                        "2. Have you tried installing driver updates?", "3. Have you replaced the display cables?"));
            }
            else if(ReportGUI_SelectErrorPage.getNetworkErrorsTrue()){
                ReportGUI_StartPage.getStage().setScene(ReportGUI_SurveyPage.startSurveyPage("Please complete the survey",
                        "Which OS do you have?", "1. Is the LAN working?",
                        "2. Is the WiFi working?", "3. Have you tried disabling/re-enabling network adapters?"));
            }
            else if(ReportGUI_SelectErrorPage.getOtherErrorsTrue()){
                ReportGUI_StartPage.getStage().setScene(ReportGUI_SelectErrorPage.
                        startSelectErrorPage("Please select the error you're experiencing"));
            }
        });

        // Create Action Handling for the quitButton
        quitButton.setOnAction(event -> ReportGUI_QuitPage.display("Quit", "Are you sure?"));

        // Set the layout to the Scene
        Scene commentScene = new Scene(borderPane);

        return commentScene;
    }


    // Create method for creating a GridPane
    private static GridPane makeGridPane(String message){
        // Initialize the gridPane
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10,10,30,10));

        // Create label
        greetingLabel = ReportGUI_ConvenienceMethods.createLabel(greetingLabel, message, Double.MAX_VALUE,
                Pos.CENTER, "Arial", FontWeight.BOLD, 20);

        // Create buttons
        nextButton = new Button("Next");
        backButton = new Button("Back");
        quitButton = new Button("Quit");

        // Create a TextArea for the comments
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefColumnCount(30);
        textArea.setPrefRowCount(150);

        // Create HBox
        buttonBox = ReportGUI_ConvenienceMethods.createHBox(buttonBox, Pos.CENTER, Double.MAX_VALUE, 50, null,
                20, 20, 10 ,10, quitButton, backButton, nextButton);

        // Add everything to GridPane
        gridPane.add(textArea,0,0,3,1);

        // Return the gridPane
        return gridPane;

    }

    public static String getComments(){
        return comments;
    }


}
