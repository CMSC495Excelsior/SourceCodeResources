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
import javafx.scene.text.Text;


/*
    Filename: ReportGUI_ReviewPage.java
    Author: Stephen James
    Date: 10/3/18

    Class objective: To create a Java Class that will be used as a page for the GUI where the user gets a
                     chance to review their information and change any information before creating the
                     Word Document. The reviewPage will consist of many V & HBoxes as well as Labels, Text,
                     Buttons and a CheckBox.
 */


public class ReportGUI_ReviewPage {

    // Create all JavaFX components
    private static Label nameLabel, departmentLabel, emailLabel, phoneNumberLabel, surveyLabel, basicInfoLabel,
                        greetingLabel, errorTypeLabel, question1Label, question2Label, question3Label, osLabel;
    private static Text name, department, email, phoneNumber, errorType,
            osAnswer, question1Answer, question2Answer, question3Answer,
            changeBasicText, changeSurveyText, updateBasicText, nameUpdate, departmentUpdate,
            emailUpdate, phoneNumberUpdate, errorTypeUpdate, updateSurveyText,
            osUpdate, question1Update, question2Update, question3Update;
    private static TextField updatedName, updatedDepartment, updatedEmail, updatedPhoneNumber;
    private static VBox basicInfoLabelsBox, infoTextBox, surveyLabelsBox, changeBasicBox, changeSurveyBox, helpInfoBox,
                        helpSurveyBox;
    private static HBox buttonBox, greetingBox, nameBox, departmentBox, emailBox, phoneNumberBox,
                        osBox, question1Box, question2Box, question3Box;
    private static BorderPane borderPane;
    private static Button nextButton, backButton, quitButton, changeBasicInfo, changeSurveyResults, updateBasicInfo,
                            updateSurveyResults;
    private static CheckBox confirmBox;
    private static GridPane gridPane, gridPane2;

    // Create Class fields
    private static boolean isBasicInfoClicked, isSurveyInfoClicked;

    // Create static method to switch scenes
    public static Scene startReviewPage(String greeting) {

        // set isSurveyInfoClicked to false for changeSurveyResults Button
        isSurveyInfoClicked = false;
        // Set isBasicInfoClicked equal to false for the changeBasicInfo action handling
        isBasicInfoClicked = false;

        // Create the GridPane
        makeGridPane(greeting);

        // Create the BorderPane
        makeBorderPane();

        // Set the layout to the Scene
        Scene surveyScene = new Scene(borderPane);

        // Create Action Handling for the nextButton
        nextButton.setOnAction(event -> {
            if(confirmBox.isSelected()){
                ReportGUI_StartPage.getStage().setScene(ReportGUI_SaveDocPage.startSaveDocPage(
                        "Please enter the following information",
                        "Please enter your desired file name",
                        "Please enter your desired file path"
                ));
            }
            else{
                ReportGUI_AlertPage.alert("ALERT", "Please confirm you have reviewed your information");
            }

        });


        // Create action handling for the changeBasicInfo button
        changeBasicInfo.setOnAction(event ->{
            // Create string used for switch statement
            String isClickedString = String.valueOf(isBasicInfoClicked);

            // Close the changeSurveyResults if the user wants to edit their basic info. This will
            // prevent the boxes from over-inflating the screen
            if(isSurveyInfoClicked = true){
                gridPane2.getChildren().remove(changeSurveyBox);
                changeSurveyResults.setText("Change Survey Results");
                isSurveyInfoClicked = false;
            }
            else{
                // Do nothing
                System.out.println();
            }

            // If the string = "false" add the box then reset isClicked. If its
            // true, delete the box and set isClicked to false.
            switch (isClickedString){
                case "false":
                    gridPane2.add(changeBasicBox,0,1,1,1);
                    // Reset isClicked
                    isBasicInfoClicked = true;
                    changeBasicInfo.setText("Close");
                    break;

                case "true":
                    gridPane2.getChildren().remove(changeBasicBox);
                    isBasicInfoClicked = false;
                    changeBasicInfo.setText("Change Basic Info");
                    break;

                default:
                    gridPane2.getChildren().remove(changeBasicBox);
                    isBasicInfoClicked = true;
                    break;
            }

        });

        // Create action handling for the updateBasicInfo button
        updateBasicInfo.setOnAction(event -> {
            // Set updated information
            ReportGUI_StartPage.setName(updatedName.getText());
            ReportGUI_StartPage.setDepartment(updatedDepartment.getText());
            ReportGUI_StartPage.setEmailUsername(updatedEmail.getText());
            ReportGUI_StartPage.setPhoneNumber(Long.parseLong(updatedPhoneNumber.getText()));

            // Reload the scene
            ReportGUI_StartPage.getStage().setScene(startReviewPage("Please review your information"));
        });


        // Create action handling for changeSurveyResults button
        changeSurveyResults.setOnAction(event -> {
            // Create string for switch statement
            String isClickedString = String.valueOf(isSurveyInfoClicked);

            // Close the basicInfoBox if the user wants to edit the surveyResults. This will
            // prevent the boxes from over-inflating the screen
            if(isBasicInfoClicked = true){
                gridPane2.getChildren().remove(changeBasicBox);
                changeBasicInfo.setText("Change Basic Info");
                isBasicInfoClicked = false;
            }
            else{
                // Do nothing
                System.out.println();
            }
            // Make the survey results box able to close and open smoothly
            switch (isClickedString){
                case "false":
                    gridPane2.add(changeSurveyBox,0,3,1,1);
                    // Reset isClicked
                    isSurveyInfoClicked = true;
                    changeSurveyResults.setText("Close");
                    break;

                case "true":
                    gridPane2.getChildren().remove(changeSurveyBox);
                    isSurveyInfoClicked = false;
                    changeSurveyResults.setText("Change Survey Results");
                    break;

                default:
                    gridPane2.getChildren().remove(changeSurveyBox);
                    isSurveyInfoClicked = true;
                    break;
            }
        });

        // Create action handling for the updateBasicInfo button
        updateSurveyResults.setOnAction(event -> {
            // Get the String value of the selected toggle in ToggleGroups, clip the string, and change the answer
            // to clipped string
            String osString = String.valueOf(ReportGUI_SurveyPage.getOperatingSystemGroup().getSelectedToggle());
            String osStringClipped = osString.substring(osString.indexOf('\'')+1, osString.length()-1);

            String question1String = String.valueOf(ReportGUI_SurveyPage.getQuestion1Group().getSelectedToggle());
            String question1StringClipped = question1String.substring(
                    question1String.indexOf('\'')+1, question1String.length()-1);

            String question2String = String.valueOf(ReportGUI_SurveyPage.getQuestion2Group().getSelectedToggle());
            String question2StringClipped = question2String.substring(
                    question2String.indexOf('\'')+1, question2String.length()-1);

            String question3String = String.valueOf(ReportGUI_SurveyPage.getQuestion3Group().getSelectedToggle());
            String question3StringClipped = question3String.substring(
                    question3String.indexOf('\'')+1, question3String.length()-1);

            // Set the answers
            ReportGUI_SurveyPage.setOsAnswer(osStringClipped);
            ReportGUI_SurveyPage.setQuestion1Answer(question1StringClipped);
            ReportGUI_SurveyPage.setQuestion2Answer(question2StringClipped);
            ReportGUI_SurveyPage.setQuestion3Answer(question3StringClipped);

            // Reload the scene
            ReportGUI_StartPage.getStage().setScene(startReviewPage("Please review your information"));
        });

        // Create Action Handling for the backButton
        backButton.setOnAction(event ->
                ReportGUI_StartPage.getStage().setScene(
                        ReportGUI_CommentPage.startCommentPage("Please enter additional comments")));

        //Create Action Handling for the quitButton
        quitButton.setOnAction(event -> ReportGUI_QuitPage.display("Quit", "Are you sure?"));


        return surveyScene;
    }

    // Create a method for creating a GridPane
    private static GridPane makeGridPane(String greeting){
        // Create new GridPane for the center of BorderPane
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //******************************************Creating Labels**************************************************

        // Create Label
        greetingLabel = ReportGUI_ConvenienceMethods.createLabel(greetingLabel, greeting, Double.MAX_VALUE,
                Pos.CENTER, "Arial", FontWeight.BOLD, 20);

        // Create Label for survey results title
        surveyLabel = ReportGUI_ConvenienceMethods.createLabel(surveyLabel, "Survey Results", Double.MAX_VALUE,
                Pos.CENTER, "Arial", FontWeight.BOLD, 15);
        // Also make surveyLabel underlined
        surveyLabel.setUnderline(true);

        // Create Label for basic info title
        basicInfoLabel = ReportGUI_ConvenienceMethods.createLabel(basicInfoLabel, "Basic Info", Double.MAX_VALUE,
                Pos.CENTER, "Arial", FontWeight.BOLD, 15);
        // Also make surveyLabel underlined
        basicInfoLabel.setUnderline(true);

        // Create Labels for basic info
        nameLabel = ReportGUI_ConvenienceMethods.createLabel(nameLabel, "Name: ", Double.MAX_VALUE,
                Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        departmentLabel = ReportGUI_ConvenienceMethods.createLabel(departmentLabel, "Department: ", Double.MAX_VALUE,
                Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        emailLabel = ReportGUI_ConvenienceMethods.createLabel(emailLabel, "Email: ", Double.MAX_VALUE,
                Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        phoneNumberLabel = ReportGUI_ConvenienceMethods.createLabel(phoneNumberLabel, "Phone Number: ", Double.MAX_VALUE,
                Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        errorTypeLabel = ReportGUI_ConvenienceMethods.createLabel(errorTypeLabel, "Error Type: ", Double.MAX_VALUE,
                Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        question1Label = ReportGUI_ConvenienceMethods.createLabel(question1Label, ReportGUI_SurveyPage.getQuestion1Text(),
                Double.MAX_VALUE, Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        question2Label = ReportGUI_ConvenienceMethods.createLabel(question2Label, ReportGUI_SurveyPage.getQuestion2Text(),
                Double.MAX_VALUE, Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        question3Label = ReportGUI_ConvenienceMethods.createLabel(question3Label, ReportGUI_SurveyPage.getQuestion3Text(),
                Double.MAX_VALUE, Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        osLabel = ReportGUI_ConvenienceMethods.createLabel(osLabel, ReportGUI_SurveyPage.getOsQuestionText(),
                Double.MAX_VALUE, Pos.BASELINE_LEFT, "Arial", FontWeight.BOLD, 15);

        //******************************************Creating Text**************************************************

        // Create Text objects for survey answers and basic info
        osAnswer = new Text(ReportGUI_SurveyPage.getOsAnswer());
        question1Answer = new Text(ReportGUI_SurveyPage.getQuestion1Answer());
        question2Answer = new Text(ReportGUI_SurveyPage.getQuestion2Answer());
        question3Answer = new Text(ReportGUI_SurveyPage.getQuestion3Answer());
        name = new Text(ReportGUI_StartPage.getName());
        department = new Text(ReportGUI_StartPage.getDepartment());
        email = new Text(ReportGUI_StartPage.getEmailUsername());
        phoneNumber = new Text(String.valueOf(ReportGUI_StartPage.getPhoneNumber()));
        errorType= new Text(ReportGUI_SelectErrorPage.getErrorType());


        //******************************************Creating Buttons/CheckBox***************************************

        // Create Buttons
        nextButton = new Button("Next");
        backButton = new Button("Back");
        quitButton = new Button("Quit");

        // Create a checkbox for the user to confirm their info is correct
        confirmBox = new CheckBox("I confirm my information is correct");
        confirmBox.setPadding(new Insets(10,10,10,10));

        //******************************************Creating HBoxes**************************************************

        // Create new HBoxes
        buttonBox = ReportGUI_ConvenienceMethods.createHBox(buttonBox,Pos.CENTER, Double.MAX_VALUE,100,null,
                10,20, 10,20, quitButton,backButton,nextButton);

        greetingBox = ReportGUI_ConvenienceMethods.createHBox(greetingBox, Pos.CENTER, Double.MAX_VALUE, 30, null,
                10,20,10, 20, greetingLabel);

        //******************************************Creating VBoxes**************************************************

        // Create new VBox for basic information labels
        basicInfoLabelsBox = ReportGUI_ConvenienceMethods.createVBox(basicInfoLabelsBox,Pos.BASELINE_LEFT, Double.MAX_VALUE,
                8,null, 10,10, 10, 10, nameLabel,departmentLabel,
                emailLabel, phoneNumberLabel, errorTypeLabel);

        // Create new VBox for the information text
        infoTextBox = ReportGUI_ConvenienceMethods.createVBox(infoTextBox, Pos.BASELINE_LEFT, Double.MAX_VALUE, 6, null,
                10, 10, 10, 10, name, department, email, phoneNumber,
                errorType);
        infoTextBox.setPadding(new Insets(0));

        // Create new VBox for the survey questions and answers
        surveyLabelsBox = ReportGUI_ConvenienceMethods.createVBox(surveyLabelsBox, Pos.BASELINE_LEFT, Double.MAX_VALUE,
                8, null,10, 10, 10, 10, osLabel, osAnswer,question1Label,question1Answer,
                question2Label, question2Answer, question3Label, question3Answer, confirmBox);

        // Add everything to GridPane
        gridPane.add(basicInfoLabel, 0,1,2,1);
        gridPane.add(basicInfoLabelsBox, 0,2,1,1);
        gridPane.add(infoTextBox, 1,2,1,1);
        gridPane.add(surveyLabel, 0,3,2,1);
        gridPane.add(surveyLabelsBox, 0,4,2,1);
        gridPane.setAlignment(Pos.CENTER);

        // Return the gridPane
        return gridPane;

    }

    // Create a method for creating the BorderPane (and adding in the 2nd GridPane)
    private static BorderPane makeBorderPane(){
        // Create gridPane2 for the right of BorderPane
        gridPane2 = new GridPane();
        gridPane2.setVgap(10);
        gridPane2.setHgap(10);

        //******************************************Creating Text**************************************************

        // Make Text for updated basic info and survey answers
        nameUpdate = new Text("Name: ");
        departmentUpdate = new Text("Department: ");
        emailUpdate = new Text("Email: ");
        phoneNumberUpdate = new Text("Phone Number: ");
        errorTypeUpdate = new Text("Error Type");

        osUpdate = new Text("OS: ");
        question1Update = new Text("Question 1: ");
        question2Update = new Text("Question 2: ");
        question3Update = new Text("Question 3: ");

        // Create Text for changing basic info
        changeBasicText = new Text("Want to change your basic information?\n\t\t\tClick below:");
        updateBasicText = new Text("Please enter the updated information");
        changeSurveyText = new Text("Want to change your survey answers?\n\t\t\tClick below:");
        updateSurveyText = new Text("Please enter the updated information");

        //******************************************Creating Buttons**************************************************

        // Create Buttons for gridPane2
        changeBasicInfo = new Button("Change Basic Info");
        updateBasicInfo = new Button("Update Basic Info");
        changeSurveyResults = new Button("Change Survey Answers");
        updateSurveyResults = new Button("Update Survey Answers");


        //******************************************Creating TextFields**************************************************

        // Create TextFields
        updatedName = ReportGUI_ConvenienceMethods.createTextField(updatedName, 30, 175, 8,
                0,0,0,0);
        // Set the TextField's text
        updatedName.setText(ReportGUI_StartPage.getName());

        // Create TextFields
        updatedDepartment = ReportGUI_ConvenienceMethods.createTextField(updatedDepartment, 30, 175,
                8,0,0,0,0);
        // Set the TextField's text
        updatedDepartment.setText(ReportGUI_StartPage.getDepartment());

        // Create TextFields
        updatedEmail = ReportGUI_ConvenienceMethods.createTextField(updatedEmail, 30, 175, 8,
                0,0,0,0);
        // Set the TextField's text
        updatedEmail.setText(ReportGUI_StartPage.getEmailUsername());

        // Create TextFields
        updatedPhoneNumber = ReportGUI_ConvenienceMethods.createTextField(updatedPhoneNumber, 30,
                175, 8,0,0,0,0);
        // Set the TextField's text
        updatedPhoneNumber.setText(String.valueOf(ReportGUI_StartPage.getPhoneNumber()));


        //******************************************Creating HBoxes**************************************************

        // Create new HBoxes for updating the user's information
        nameBox = ReportGUI_ConvenienceMethods.createHBox(nameBox, Pos.BASELINE_LEFT, Double.MAX_VALUE, 20, null,
                10,10, 10,10, nameUpdate, updatedName);

        departmentBox = ReportGUI_ConvenienceMethods.createHBox(departmentBox, Pos.BASELINE_LEFT, Double.MAX_VALUE, 20,
                null,10,10,10, 10, departmentUpdate, updatedDepartment);

        emailBox = ReportGUI_ConvenienceMethods.createHBox(emailBox, Pos.BASELINE_LEFT, Double.MAX_VALUE, 20, null,
                10, 10, 10,10, emailUpdate, updatedEmail);

        phoneNumberBox = ReportGUI_ConvenienceMethods.createHBox(phoneNumberBox, Pos.BASELINE_LEFT, Double.MAX_VALUE,
                20, null,10, 10,10, 10, phoneNumberUpdate,
                updatedPhoneNumber);

        osBox = ReportGUI_ConvenienceMethods.createHBox(osBox, Pos.BASELINE_LEFT, Double.MAX_VALUE, 20, null,
                10,10, 10,10, osUpdate, ReportGUI_SurveyPage.getWindowsRadioButton(),
                ReportGUI_SurveyPage.getLinuxRadioButton(), ReportGUI_SurveyPage.getMacRadioButton());

        question1Box = ReportGUI_ConvenienceMethods.createHBox(question1Box, Pos.BASELINE_LEFT, Double.MAX_VALUE, 20,
                null,10,10,10, 10, question1Update, ReportGUI_SurveyPage.getQuestion1Yes(),
                ReportGUI_SurveyPage.getQuestion1No());

        question2Box = ReportGUI_ConvenienceMethods.createHBox(question2Box, Pos.BASELINE_LEFT, Double.MAX_VALUE, 20,
                null,10, 10, 10,10, question2Update, ReportGUI_SurveyPage.getQuestion2Yes(),
                ReportGUI_SurveyPage.getQuestion2No());

        question3Box = ReportGUI_ConvenienceMethods.createHBox(question3Box, Pos.BASELINE_LEFT, Double.MAX_VALUE, 20,
                null,10, 10,10, 10, question3Update, ReportGUI_SurveyPage.getQuestion3Yes(),
                ReportGUI_SurveyPage.getQuestion3No());


        //******************************************Creating VBoxes**************************************************

        // Create new VBox for changing information
        changeBasicBox = ReportGUI_ConvenienceMethods.createVBox(changeBasicBox, Pos.TOP_CENTER, Double.MAX_VALUE,
                0, Color.WHITESMOKE,10, 10, 10, 10, updateBasicText,
                nameBox, departmentBox, emailBox, phoneNumberBox, updateBasicInfo);

        // Create new VBox for changing information
        changeSurveyBox = ReportGUI_ConvenienceMethods.createVBox(changeSurveyBox, Pos.TOP_CENTER, Double.MAX_VALUE,
                0, Color.WHITESMOKE,10, 10, 10, 10, updateSurveyText,
                osBox, question1Box, question2Box, question3Box, updateSurveyResults);

        // Create new VBox for the Help section
        helpInfoBox = ReportGUI_ConvenienceMethods.createVBox(helpInfoBox, Pos.TOP_CENTER, Double.MAX_VALUE, 10, null,
                10, 10, 10, 10, changeBasicText, changeBasicInfo);

        // Create new VBox for the Help section
        helpSurveyBox = ReportGUI_ConvenienceMethods.createVBox(helpSurveyBox, Pos.TOP_CENTER, Double.MAX_VALUE, 10,
                null,10, 10, 10, 10, changeSurveyText, changeSurveyResults);



        gridPane2.add(helpInfoBox, 0,0,1,1);
        gridPane2.add(helpSurveyBox, 0,2,1,1);

        // Create new BorderPane to nest layouts inside
        borderPane = ReportGUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE, greetingBox, buttonBox,
                null, gridPane2, gridPane);

        // Return the borderPane
        return borderPane;

    }


}
