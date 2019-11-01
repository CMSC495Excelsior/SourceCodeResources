import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.io.File;

/*
    Filename: ReportGUI_SaveDocPage.java
    Author: Stephen James
    Date: 10/3/18

    Class Objective: Create another scene to be added to the Stage. This scene will display 2 TextFields:
                     1 for the File Name and 1 for the File Path. Once the user enters all necessary
                     information and clicks the "Next" button, this scene will use the
                     "ReportGUI_CreateDocument" class to create a Word Doc with the user's
                     information organized appropriately on the page.
 */

public class ReportGUI_SaveDocPage {

    // Create JavaFX Components
    private static Label greetingLabel, fileNameLabel, filePathLabel;
    private static TextField fileNameField, filePathField;
    private static HBox buttonBox;
    private static VBox textFieldBox, labelFieldBox;
    private static BorderPane borderPane;
    private static GridPane gridPane;
    private static Button nextButton, backButton, quitButton;
    private static CheckBox openDocBox;

    // Create class variables
    private static String fileName, filePath;
    private static boolean createDocSuccess;

    // Create method to build and return a new scene
    public static Scene startSaveDocPage(String greeting, String theFileNameLabel, String theFilePathLabel) {
        // Initialize createDocSuccess as false
        createDocSuccess = false;

        // Make the GridPane using the makeGridPane method (using the same parameters as the start method)
        makeGridPane(greeting, theFileNameLabel, theFilePathLabel);

        // Add a BorderPane
        borderPane = ReportGUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE, greetingLabel, buttonBox,
                null, null, gridPane);
        // Set new padding
        borderPane.setPadding(new Insets(30, 10, 30, 10));

        // Setup action handlers
        nextButton.setOnAction(event -> {
            System.out.println("You clicked NEXT");
            if(fileNameField.getText().isEmpty()){
                ReportGUI_AlertPage.alert("ALERT", "Please enter a file name");
            }
            else if(filePathField.getText().isEmpty()){
                ReportGUI_AlertPage.alert("ALERT", "Please enter a file path");
            }
            else{
                try {
                    fileName = fileNameField.getText();
                    filePath = filePathField.getText() + "\\";

                    // Try creating the document with the appropriate values
                    ReportGUI_CreateDocument.makeDoc(ReportGUI_SelectErrorPage.getErrorType(), ReportGUI_StartPage.getName(),
                            ReportGUI_StartPage.getDepartment(), ReportGUI_StartPage.getEmailUsername(),
                            String.valueOf(ReportGUI_StartPage.getPhoneNumber()), fileName, filePath,
                            ReportGUI_SurveyPage.getOsQuestionText(), ReportGUI_SurveyPage.getOsAnswer(),
                            ReportGUI_SurveyPage.getQuestion1Text(), ReportGUI_SurveyPage.getQuestion1Answer(),
                            ReportGUI_SurveyPage.getQuestion2Text(), ReportGUI_SurveyPage.getQuestion2Answer(),
                            ReportGUI_SurveyPage.getQuestion3Text(), ReportGUI_SurveyPage.getQuestion3Answer(),
                            ReportGUI_CommentPage.getComments());

                    // Change createDocSuccess to true
                    createDocSuccess = true;

                }catch (Exception e){
                    createDocSuccess = false;
                    System.out.println(e);
                }

                // Check if the document was created or not
                if(createDocSuccess){
                    // Go to EmailPage
                    ReportGUI_StartPage.getStage().setScene(ReportGUI_EmailPage.startEmailPage("Please choose an option",
                            "Choose your provider"));
                }
                else{
                    ReportGUI_AlertPage.alert("ALERT", "Document creation failed,\nplease check your file name and path.");
                }
            }

            // If the user selects the CheckBox, open their newly created file
            if(openDocBox.isSelected()){
                try {
                    File newDoc = new File(filePath + fileName+ ".docx");
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(newDoc);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
            else{
                System.out.println();
            }
        });

        // Create Action Handling for the backButton
        backButton.setOnAction(event -> ReportGUI_StartPage.getStage().setScene(ReportGUI_ReviewPage.startReviewPage(
                "Please review your information")));

        // Create Action Handling for the quitButton
        quitButton.setOnAction(event -> ReportGUI_QuitPage.display("Quit", "Are you sure?"));

        // Set the layout to the Scene
        Scene saveDocScene = new Scene(borderPane);

        // Return the Scene
        return saveDocScene;

    }

    // Create a method for creating a GridPane
    private static GridPane makeGridPane(String greeting, String theFileNameLabel, String theFilePathLabel){
        // Create GridPane
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 30, 10));

        // Create a greeting label
        greetingLabel = ReportGUI_ConvenienceMethods.createLabel(greetingLabel, greeting, Double.MAX_VALUE, Pos.CENTER,
                "Arial", FontWeight.BOLD, 20);

        // Create a label for the fileName TextField
        fileNameLabel = ReportGUI_ConvenienceMethods.createLabel(fileNameLabel, theFileNameLabel, Double.MAX_VALUE,
                Pos.CENTER, "Arial", FontWeight.BOLD, 15);

        // Create a label for the filePath TextField
        filePathLabel = ReportGUI_ConvenienceMethods.createLabel(filePathLabel, theFilePathLabel, Double.MAX_VALUE,
                Pos.CENTER, "Arial", FontWeight.BOLD, 15);

        // Create buttons
        nextButton = new Button("Next");
        backButton = new Button("Back");
        quitButton = new Button("Quit");

        // Create CheckBox
        openDocBox = new CheckBox("Open my document when created");
        openDocBox.setPadding(new Insets(10,10,10,10));

        // Create a TextField for the fileName
        fileNameField = ReportGUI_ConvenienceMethods.createTextField(fileNameField, 30, 200, 10,
                10, 10, 10, 10);
        fileNameField.setText("");

        // Create a TextField for the filePath
        filePathField = ReportGUI_ConvenienceMethods.createTextField(filePathField, 30, 200, 10,
                10, 10, 10, 10);
        // Set the text
        filePathField.setText("C:\\");

        // Create HBox for the Buttons
        buttonBox = ReportGUI_ConvenienceMethods.createHBox(buttonBox, Pos.CENTER, Double.MAX_VALUE, 50,
                null, 20,20, 10, 10, quitButton, backButton, nextButton);

        // Create VBox for TextFields
        textFieldBox = ReportGUI_ConvenienceMethods.createVBox(textFieldBox, Pos.CENTER, Double.MAX_VALUE, 50,
                null, 20,20, 10, 10, fileNameField, filePathField);

        // Create VBox for Labels
        labelFieldBox = ReportGUI_ConvenienceMethods.createVBox(labelFieldBox, Pos.CENTER, Double.MAX_VALUE, 50,
                null, 20,20, 10, 10, fileNameLabel, filePathLabel);


        // Add everything to GridPane
        gridPane.add(textFieldBox,1,0,1,1);
        gridPane.add(labelFieldBox,0,0,1,1);
        gridPane.add(openDocBox, 0, 1,2,1);

        return gridPane;
    }

    public static String getFileName(){
        return fileName;
    }

    public static String getFilePath(){
        return filePath;
    }

}
