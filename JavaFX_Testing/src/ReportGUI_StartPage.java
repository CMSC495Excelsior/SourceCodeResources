import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/*
    Filename: ReportGUI_StartPage.java
    Author: Stephen James
    Date: 10/3/18

    Class objective: To create a Java Class that will be used for the starting page in the GUI. This is where the
                     primaryStage will be defined and used as the basis for all other Scenes. This page specifically
                     will have multiple TextBoxes and Labels and two Buttons used for collecting the user's input
                     for various fields like name, department, email, phone number and more.
 */


public class ReportGUI_StartPage extends Application {

    // Create GUI elements
    private static Button quitButton, nextButton;
    private static Stage startStage;
    private static Scene startScene;
    private static GridPane gridLayout;
    private static BorderPane borderPane;
    private static VBox labelVBox, textFieldVBox;
    private static HBox buttonBox;
    private static Group infoGroup;
    private static TextField nameField, departmentField, emailField, phoneNumberField;
    private static Label greetingLabel, nameLabel, departmentLabel, emailLabel, phoneNumberLabel;


    // Create field variables
    private static String name, department, emailUsername, domainName;
    private static long phoneNumber;
    private String[] infoArray;
    private static boolean fieldsAreGood;


    // Create starting method used to generate the stage and scene
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Create Stage
        primaryStage = new Stage();
        startStage = primaryStage;

        // Customize Stage
        primaryStage.setTitle("Error Report");
        primaryStage.setHeight(650);
        primaryStage.setWidth(900);
        primaryStage.setResizable(false);

        // Create BorderPane
        makeBorderPane();

        // Create scene
        Scene scene = new Scene(borderPane);
        startScene = scene;

        // Create action handling for quitButton
        quitButton.setOnAction(event -> ReportGUI_QuitPage.display("Quit", "Are you sure?"));

        // Create action handling for nextButton
        nextButton.setOnAction(event ->  {
            fieldsAreGood = false;
                domainName = emailField.getText().substring((emailField.getText().indexOf('@') + 1),
                        (emailField.getText().length()));
                System.out.println("The domain name is: " + domainName);
                String tempName = nameField.getText();
                String tempDepartment = departmentField.getText();
                String tempEmailUsername = emailField.getText();
                String tempPhoneNumber = phoneNumberField.getText();

                infoArray = new String[] {tempName, tempDepartment, tempEmailUsername, tempPhoneNumber};

                // Iterate through infoArray (array of temporary variables) to ensure all fields
                // are filled out correctly
                for(String value : infoArray){
                    if(value == tempName){
                        if (value.isEmpty()) {
                            ReportGUI_AlertPage.alert("ALERT", "Please enter a name");
                            break;
                        }
                        else {
                            name = tempName;
                            System.out.println(name);
                        }
                    }
                    else if(value == tempDepartment){
                        if(value.isEmpty()){
                            ReportGUI_AlertPage.alert("ALERT", "Please enter a department");
                            break;
                        }
                        else{
                            department = tempDepartment;
                            System.out.println(department);
                        }
                    }
                    else if(value == tempEmailUsername){
                        if(tempEmailUsername.indexOf('@') < 1 || domainName.isEmpty()){
                            ReportGUI_AlertPage.alert("ALERT", "Please enter a valid Email");
                            break;
                            }
                            else{
                            emailUsername = tempEmailUsername;
                            System.out.println(emailUsername);
                            }
                    }
                    else if(value == tempPhoneNumber){
                        if (tempPhoneNumber.length() < 10 || tempPhoneNumber.length() > 10) {
                            ReportGUI_AlertPage.alert("ALERT", "Please enter a valid 10-digit phone number");
                            break;
                        }
                        else {
                            try {
                                phoneNumber = Long.parseLong(tempPhoneNumber);
                                System.out.println(phoneNumber);
                                fieldsAreGood = true;
                            } catch (NumberFormatException e) {
                                ReportGUI_AlertPage.alert("ALERT", "Please enter a valid 10-digit phone number");
                                System.out.println(e);
                            }
                        }
                    }
                    else{
                        System.out.println("Fields are good");
                    }
                }
                if(fieldsAreGood){
                    startStage.setScene(ReportGUI_SelectErrorPage.startSelectErrorPage(
                            "Hello, " + name + "\nPlease select the error you're experiencing"));
                }
                else{
                    System.out.println("Your information is invalid");
                }
                });

        // Set the Scene
        primaryStage.setScene(scene);
        // Show the Stage
        primaryStage.show();
    }

    public GridPane makeGridPane(){
        // Create Group
        infoGroup = new Group();

        // Create layout
        gridLayout = new GridPane();
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);

        // Create text for label "Name: "
        nameLabel = ReportGUI_ConvenienceMethods.createLabel(nameLabel, "Name: ",
                Double.MAX_VALUE, Pos.CENTER, "Arial", FontWeight.BOLD, 15);

        // Create text for label "Department: "
        departmentLabel = ReportGUI_ConvenienceMethods.createLabel(departmentLabel, "Department: ",
                Double.MAX_VALUE, Pos.CENTER, "Arial", FontWeight.BOLD, 15);

        // Create text for label "Email: "
        emailLabel = ReportGUI_ConvenienceMethods.createLabel(emailLabel, "Email: ",
                Double.MAX_VALUE, Pos.CENTER, "Arial", FontWeight.BOLD, 15);

        // Create text for label "Phone Number: "
        phoneNumberLabel = ReportGUI_ConvenienceMethods.createLabel(phoneNumberLabel, "Phone Number: ",
                Double.MAX_VALUE, Pos.CENTER, "Arial", FontWeight.BOLD, 15);

        greetingLabel = ReportGUI_ConvenienceMethods.createLabel(greetingLabel, "Enter your information below",
                Double.MAX_VALUE, Pos.CENTER, "Arial", FontWeight.BOLD, 20);

        // Make all textfields
        nameField = new TextField();
        nameField.setPrefColumnCount(30);

        emailField = new TextField();
        emailField.setPrefColumnCount(30);

        departmentField = new TextField();
        departmentField.setPrefColumnCount(30);

        phoneNumberField = new TextField();
        phoneNumberField.setPrefColumnCount(30);


        // Create a VBox for all labels
        labelVBox = ReportGUI_ConvenienceMethods.createVBox(labelVBox, Pos.BASELINE_LEFT, Double.MAX_VALUE, 65,
                null, 12, 12, 15, 15, nameLabel, departmentLabel,
                emailLabel, phoneNumberLabel);
        labelVBox.setTranslateY(50);

        // Create a VBox for all textfields
        textFieldVBox = ReportGUI_ConvenienceMethods.createVBox(textFieldVBox, Pos.CENTER, Double.MAX_VALUE,
                50, null, 12, 12, 15, 15, nameField,
                departmentField, emailField, phoneNumberField);
        textFieldVBox.setTranslateY(50);

        // Add VBoxes to Group
        infoGroup.getChildren().addAll(labelVBox, textFieldVBox);


        // Add everything to teh gridLayout
        gridLayout.add(greetingLabel, 0,0,10,1);
        gridLayout.add(labelVBox,0,1);
        gridLayout.add(textFieldVBox, 1,1);
        gridLayout.setAlignment(Pos.CENTER);

        return gridLayout;
    }


    // Create a change scene method
    public BorderPane makeBorderPane(){
        // Create Buttons
        // Make a buttons to add to scene
        quitButton = new Button("Quit");
        quitButton.setAlignment(Pos.CENTER);
        nextButton = new Button("Next");
        nextButton.setAlignment(Pos.CENTER);

        // Create GridPane
        makeGridPane();

        // Create HBox for buttons
        buttonBox = ReportGUI_ConvenienceMethods.createHBox(buttonBox, Pos.CENTER, Double.MAX_VALUE, 100,
                null, 10, 10, 10, 10, quitButton, nextButton);

        // Create BorderPane
        borderPane = ReportGUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE, null, buttonBox,
                null, null, gridLayout);


        // Return the BorderPane
        return borderPane;
    }


    //----------------------------------Get&Set Methods----------------------------------

    // Get&Set method for the stage (Might have to make EVERYTHING static)
    public static Stage getStage(){
        return startStage;
    }
    public static void setStage(Stage theStage){
        startStage = theStage;
    }

    // Get&Set method for the scene
    public static Scene getScene(){
        return startScene;
    }
    public static void setScene(Scene theScene){
        startScene = theScene;
    }

    // Get&Set method for the quitButton
    public static Button getQuitButton(){
        return quitButton;
    }
    public static void setQuitButton(Button theQuitButton){
        quitButton = theQuitButton;
    }

    // Get&Set method for the nextButton
    public static Button getNextButton(){
        return nextButton;
    }
    public static void setNextButton(Button theNextButton){
        nextButton = theNextButton;
    }

    // Get&Set method for the name
    public static String getName(){
        return name;
    }
    public static void setName(String theName){
        name = theName;
    }

    // Get&Set method for the department
    public static String getDepartment(){
        return department;
    }
    public static void setDepartment(String theDepartment){
        department = theDepartment;
    }

    // Get&Set method for the emailUsername
    public static String getEmailUsername(){
        return emailUsername;
    }
    public static void setEmailUsername(String theEmailUsername){
        emailUsername = theEmailUsername;
    }

    // Get&Set method for the phoneNumber
    public static long getPhoneNumber(){
        return phoneNumber;
    }
    public static void setPhoneNumber(long thePhoneNumber){
        phoneNumber = thePhoneNumber;
    }

    public static String getDomainName(){
        return domainName;
    }
    public static void setDomainName(String theDomainName){
        domainName = theDomainName;
    }



}
