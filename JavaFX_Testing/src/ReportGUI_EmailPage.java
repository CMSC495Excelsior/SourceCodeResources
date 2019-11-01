import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    Class objective: To create a Java Class that will be used as a page for the GUI where the user will
                     select their Email provider and enter their password in. These values will be stored and
                     then retrieved in the class "ReportGui_SendMail" in order to authenticate and send the mail.

    ***NOTE***:     Currently working on a way to allow other Email providers (by adding an "other" RadioButton.
                    In theory, if the user selects this button, the program will take their Email provider's DNS
                    (stored during the startPage) and search for their provider using the DNS in a separate class.
                    The searching will be done preferably using a HashMap. The separate class will then return the
                    proper host details so the Email can then be sent.

     *VERY IMPORTANT*:
     *
     * IN ORDER TO HAVE THE EMAIL SUCCESSFULLY SEND, REPLACE THE "theToEmail" IN THE METHOD CALL ON LINE 106!!!!
 */

public class ReportGUI_EmailPage {

    // Create JavaFX Components
    private static Label emailProvider, greetingLabel, emailFieldLabel;
    private static ToggleGroup emailProviderGroup;
    private static RadioButton gmailRadioButton, outlookRadioButton, comcastRadioButton, yahooRadioButton, otherRadioButton;
    private static RadioButton[] buttonsArray;
    private static HBox buttonBox, labelBox;
    private static VBox buttonsBox;
    private static BorderPane borderPane;
    private static GridPane gridPane;
    private static Button sendButton, quitButton;
    private static PasswordField passwordField;

    // Create class variables
    private static String[] answersArray;
    private static String providerAnswer, emailPassword;
    private static boolean mailSuccess;

    public static Scene startEmailPage(String greeting, String providerText){
        // Make the gridPane
        makeGridPane(greeting, providerText);

        // Create Action Handler for the nextButton
        sendButton.setOnAction(event -> {
            // Create answersArray default values
            String gmailAns = "";
            String outlookAns = "";
            String comcastAns = "";
            String yahooAns = "";
            String otherAns = "";

            // Initialize answersArray
            answersArray = new String[] {gmailAns, outlookAns, comcastAns, yahooAns, otherAns};

            // Initialize buttonsArray
            buttonsArray = new RadioButton[] {gmailRadioButton, outlookRadioButton, comcastRadioButton,
                                            yahooRadioButton, otherRadioButton};

            // Store the answers in the answers array
            for(int button = 0; button < buttonsArray.length; button++){
                if(buttonsArray[button].isSelected()) {
                    answersArray[button] = buttonsArray[button].getText();
                }
                else{
                    continue;
                }
            }

            // Print the answers
            for(int iterator = 0; iterator < answersArray.length; iterator++){
                System.out.println(answersArray[iterator]);
            }

            // Store the answer into a single String variable
            storeAnswer(answersArray);


            // Check if all information is filled out correctly
            if(emailProviderGroup.getSelectedToggle() == null){
                ReportGUI_AlertPage.alert("ALERT", "Please choose an email provider");
            }
            else if(passwordField.getText().isEmpty()){
                ReportGUI_AlertPage.alert("ALERT", "Please enter your email password");
            }
            else {
                // Try removing the sendButton to prevent the user from clicking it again while
                // the email is being sent *****[WORK IN PROGRESS - NOT FULLY FUNCTIONAL]*****
                buttonBox.getChildren().remove(sendButton);
                emailPassword = passwordField.getText();
                try {
                    mailSuccess = true;
                    // Try sending the email
                    ReportGUI_SendMail.sendMail(ReportGUI_StartPage.getEmailUsername(), "ENTER EMAIL HERE",
                            ReportGUI_StartPage.getEmailUsername(), emailPassword, ReportGUI_SaveDocPage.getFileName());
                }catch (Exception e){
                    mailSuccess = false;
                    ReportGUI_StartPage.getStage().setScene(ReportGUI_EmailPage.startEmailPage("Please choose an option",
                            "Choose your provider"));
                }

                // If the mail succeeded in sending, do this...
                if(mailSuccess) {
                    ReportGUI_StartPage.getStage().setScene(ReportGUI_EndPage.startEndPage("SUCCESS!",
                            "Thank you for using Error Report!","Your issue has been reported to your" +
                                    " System Administrator."));
                }
                else{
                    // Tell the user to try again
                    ReportGUI_AlertPage.alert("ALERT", "Authentication Failed - Please try again");
                    ReportGUI_StartPage.getStage().setScene(ReportGUI_EmailPage.startEmailPage(
                            "Please choose an option", "Choose your provider"));
                }
            }
        });

        //Create Action Handling for the quitButton
        quitButton.setOnAction(event -> ReportGUI_QuitPage.display("Quit", "Are you sure?"));

        // Create new BorderPane to nest layouts inside
        borderPane = ReportGUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE, labelBox, buttonBox, null,
                null, gridPane);

        // Set layout to Scene
        Scene emailScene = new Scene(borderPane);

        // Return the Scene
        return emailScene;
    }

    // Create method to store answers in a summarized answer array, this
    // way there's not white space
    public static void storeAnswer(String[] array){
        // Create temporary Strings
        String tempAnswer = "";

        // Populate the summarized Array with the user's answers
        for(int possibleAnswer = 0; possibleAnswer < array.length; possibleAnswer++){
            if(array[possibleAnswer].isEmpty()){
                continue;
            }
            else{
                tempAnswer = array[possibleAnswer];
            }
        }
        // Set the permanent value
        providerAnswer = tempAnswer;
    }

    // Create a method to create a gridPane
    private static GridPane makeGridPane(String greeting, String providerText){
        // Create new GridPane
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create Label
        greetingLabel = ReportGUI_ConvenienceMethods.createLabel(greetingLabel, greeting, Double.MAX_VALUE, Pos.CENTER,
                "Arial", FontWeight.BOLD, 20);
        greetingLabel.setPadding(new Insets(30,10,10,10));

        // Create Label
        emailProvider = ReportGUI_ConvenienceMethods.createLabel(emailProvider, providerText, Double.MAX_VALUE, Pos.BASELINE_LEFT,
                "Arial", FontWeight.BOLD, 15);

        emailFieldLabel = ReportGUI_ConvenienceMethods.createLabel(emailFieldLabel, "Enter the password for Email: " +
                        ReportGUI_StartPage.getEmailUsername(), Double.MAX_VALUE, Pos.BASELINE_LEFT,"Arial", FontWeight.BOLD,
                15);
        emailFieldLabel.setPadding(new Insets(10,10,10,10));


        // Create Buttons
        sendButton = new Button("Send");
        quitButton = new Button("Quit");

        // Create ButtonGroups for RadioButtons
        emailProviderGroup = new ToggleGroup();

        // Create OS RadioButtons
        gmailRadioButton = new RadioButton("Gmail");
        outlookRadioButton = new RadioButton("Outlook");
        comcastRadioButton = new RadioButton("Comcast");
        yahooRadioButton = new RadioButton("Yahoo");
        otherRadioButton = new RadioButton("Other");


        // Add OS buttons to group
        gmailRadioButton.setToggleGroup(emailProviderGroup);
        outlookRadioButton.setToggleGroup(emailProviderGroup);
        comcastRadioButton.setToggleGroup(emailProviderGroup);
        yahooRadioButton.setToggleGroup(emailProviderGroup);
        otherRadioButton.setToggleGroup(emailProviderGroup);

        // Create TextField for password
        passwordField = new PasswordField();
        passwordField.setPrefColumnCount(30);

        // Create new HBoxes
        buttonBox = ReportGUI_ConvenienceMethods.createHBox(buttonBox, Pos.CENTER, Double.MAX_VALUE, 100, null,
                20, 20, 10, 10, quitButton, sendButton);

        labelBox = ReportGUI_ConvenienceMethods.createHBox(labelBox, Pos.CENTER, Double.MAX_VALUE, 30, null,
                20, 20, 10, 10, greetingLabel);


        // Create new VBoxes
        buttonsBox = ReportGUI_ConvenienceMethods.createVBox(buttonsBox, Pos.BASELINE_LEFT, Double.MAX_VALUE, 8, null,
                20, 20, 10, 10, gmailRadioButton, outlookRadioButton,
                comcastRadioButton, yahooRadioButton, otherRadioButton, emailFieldLabel, passwordField);

        // Add everything to GridPane
        gridPane.add(emailProvider, 0,0,1,1);
        gridPane.add(buttonsBox, 0,1,1,1);
        gridPane.setAlignment(Pos.CENTER);

        // Return the gridPane
        return gridPane;

    }

    public static ToggleGroup getEmailProviderGroup(){
        return emailProviderGroup;
    }

    public static String getProviderAnswer(){
        return providerAnswer;
    }
}


