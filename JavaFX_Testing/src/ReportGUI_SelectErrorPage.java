import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;


/*
    Filename: ReportGUI_SelectErrorPage.java
    Author: Stephen James
    Date: 10/3/18

    Class objective: To create a Java Class for the GUI that will be used for selecting the error the user is
                     experiencing. This page will include 3 RadioButtons, each for a different
                     kind of error (display, networking, and other).
 */

public class ReportGUI_SelectErrorPage {

    // Create JavaFX components
    private static Label greetingLabel, displayDescription, networkDescription, otherDescription;
    private static Scene selectErrorScene;
    private static HBox buttonBox, labelBox;
    private static VBox radioButtonBox, descriptions;
    private static BorderPane borderPane;
    private static Button nextButton, backButton, quitButton;
    private static RadioButton displayRadioButton, networkRadioButton, otherRadioButton;
    private static ToggleGroup buttonGroup;
    private static GridPane gridPane;

    // Create class variables
    private static String errorType;
    private static boolean displayErrorsTrue, networkErrorsTrue, otherErrorsTrue;

    // Create static method to switch scenes
    public static Scene startSelectErrorPage(String message){
        // Add the GridPane
        makeGridPane(message);

        //Create Action Handling for the nextButton
        nextButton.setOnAction(event -> {
            try {
                // Set default values
                displayErrorsTrue = false;
                networkErrorsTrue = false;
                otherErrorsTrue = false;

                System.out.println("Clicked NEXT button");
                if (buttonGroup.getSelectedToggle().equals(displayRadioButton)) {
                    displayErrorsTrue = true;
                    errorType = "Display";
                    ReportGUI_StartPage.getStage().setScene(ReportGUI_SurveyPage.startSurveyPage("Please complete the survey", "Which OS do you have?",
                                                                "1. Are your screens flickering?", "2. Have you tried installing driver updates?",
                                                                "3. Have you replaced the display cables?"));
                } else if (buttonGroup.getSelectedToggle().equals(networkRadioButton)) {
                    networkErrorsTrue = true;
                    errorType = "Network";
                    ReportGUI_StartPage.getStage().setScene(ReportGUI_SurveyPage.startSurveyPage("Please complete the survey", "Which OS do you have?",
                            "1. Is the LAN working?", "2. Is the WiFi working?",
                            "3. Have you tried disabling/re-enabling network adapters?"));
                } else if (buttonGroup.getSelectedToggle().equals(otherRadioButton)) {
                    otherErrorsTrue = true;
                    errorType = "Other";
                    ReportGUI_StartPage.getStage().setScene(ReportGUI_SurveyPage.startSurveyPage("Please complete the survey",
                            "Which OS do you have?", "1. Has there been any recent OS updates?",
                            "2. Have you updated drivers recently?","3. Is there any physical damage to your computer?"));

                } else {
                    ReportGUI_AlertPage.alert("ALERT", "Please make a selection");
                }
            }catch (Exception e){
                System.out.println(e);
            }

        });
        // Create Action Handling for the backButton
        backButton.setOnAction(event -> ReportGUI_StartPage.getStage().setScene(ReportGUI_StartPage.getScene()));
        // Create Action Handling for the quitButton
        quitButton.setOnAction(event -> ReportGUI_QuitPage.display("Quit", "Are you sure?"));

        // Create BorderPane
        borderPane = ReportGUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE, labelBox, buttonBox,
                null, null, gridPane);

        // Set the layout to the Scene
        selectErrorScene = new Scene(borderPane);

        return selectErrorScene;
    }


    // Create a method to create the basic Layout and then add it to the BorderPane
    private static GridPane makeGridPane(String message){
        // Create new GridPane
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create Buttons
        nextButton = new Button("Next");
        backButton = new Button("Back");
        quitButton = new Button("Quit");

        // Create RadioButtons
        displayRadioButton = new RadioButton("Display Issues");
        networkRadioButton = new RadioButton("Network Issues");
        otherRadioButton = new RadioButton("Other Issues");

        // Create a ButtonGroup
        buttonGroup = new ToggleGroup();
        displayRadioButton.setToggleGroup(buttonGroup);
        networkRadioButton.setToggleGroup(buttonGroup);
        otherRadioButton.setToggleGroup(buttonGroup);

        // Create Labels
        greetingLabel = ReportGUI_ConvenienceMethods.createLabel(greetingLabel, message,
                Double.MAX_VALUE, Pos.CENTER, "Arial", FontWeight.BOLD, 20);
        greetingLabel.setPadding(new Insets(30,10,10,10));

        displayDescription = ReportGUI_ConvenienceMethods.createLabel(displayDescription,
                "- EX: Blinking or flickering monitors, no input detected, resolution is strange, etc. ",
                Double.MAX_VALUE, Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        networkDescription = ReportGUI_ConvenienceMethods.createLabel(networkDescription,
                "- EX: Unable to connect to WiFi or LAN, unable to print, etc. ",
                Double.MAX_VALUE, Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        otherDescription = ReportGUI_ConvenienceMethods.createLabel(otherDescription, "- You'll get a chance to explain later ",
                Double.MAX_VALUE, Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        // Create new HBoxes
        buttonBox = ReportGUI_ConvenienceMethods.createHBox(buttonBox, Pos.CENTER, Double.MAX_VALUE, 100, null,
                20, 20, 10, 10, quitButton, backButton, nextButton);

        labelBox = ReportGUI_ConvenienceMethods.createHBox(labelBox, Pos.CENTER, Double.MAX_VALUE, 30, null,
                20, 20, 10, 10, greetingLabel);

        // Create new VBoxes
        radioButtonBox = ReportGUI_ConvenienceMethods.createVBox(radioButtonBox, Pos.CENTER, Double.MAX_VALUE, 50,
                null,20,20,10,10, displayRadioButton,
                networkRadioButton, otherRadioButton);

        descriptions = ReportGUI_ConvenienceMethods.createVBox(descriptions, Pos.BASELINE_LEFT, Double.MAX_VALUE, 50,
                null,20,20,10,10, displayDescription, networkDescription,
                otherDescription);

        // Add everything to GridPane
        gridPane.add(radioButtonBox, 0,1,1,1);
        gridPane.add(descriptions, 1,1,1,1);
        gridPane.setAlignment(Pos.CENTER);

        // Return the GridPane
        return gridPane;
    }


    // Create Get&Set methods
    // Get&Set method for displayErrorsTrue
    public static boolean getDisplayErrorsTrue(){
        return displayErrorsTrue;
    }
    public static void setDisplayErrorsTrue(boolean theDisplayErrorsTrue){
        displayErrorsTrue = theDisplayErrorsTrue;
    }

    // Get&Set method for networkErrorsTrue
    public static boolean getNetworkErrorsTrue() {
        return networkErrorsTrue;
    }
    public static void setNetworkErrorsTrue(boolean theNetworkErrorsTrue){
        networkErrorsTrue = theNetworkErrorsTrue;
    }

    // Get&Set method for otherErrorsTrue
    public static boolean getOtherErrorsTrue() {
        return otherErrorsTrue;
    }
    public static void setOtherErrorsTrue(boolean theOtherErrorsTrue){
        otherErrorsTrue = theOtherErrorsTrue;
    }

    public static String getErrorType(){
        return errorType;
    }
    public static void setErrorType(String theErrorType){
        errorType = theErrorType;
    }

}
