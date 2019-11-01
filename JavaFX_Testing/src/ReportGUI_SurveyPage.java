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
    Filename: ReportGUI_SurveyPage.java
    Author: Stephen James
    Date: 10/3/18

    Class objective: To create a Java Class that will be used as a survey page for the GUI. The survey questions
                     will change depending on which error type the user picks in the previous page
                     (selectErrorPage). Every survey will have a question relating to OS however. The page
                     must not progress to the next page (commentPage) unless all questions are answered.
                     Survey questions will be in a Yes/No answer format.
 */

public class ReportGUI_SurveyPage {

    // Create JavaFX components
    private static RadioButton[][] buttonsArray;
    private static ToggleGroup[] groupArray;
    private static Label greetingLabel, osLabel, question1Label, question2Label, question3Label;
    private static RadioButton windowsRadioButton,linuxRadioButton,macRadioButton, question1Yes, question1No,
                                question2Yes, question2No, question3Yes, question3No;
    private static ToggleGroup operatingSystemGroup, question1Group, question2Group, question3Group;
    private static HBox buttonBox, labelBox;
    private static VBox questionsBox;
    private static BorderPane borderPane;
    private static GridPane gridPane;
    private static Button nextButton, backButton, quitButton;

    // Create Class fields
    private static String osQuestionText, question1Text, question2Text, question3Text, osAnswer, question1Answer, question2Answer, question3Answer;
    private static String[][] answersArray;
    private static String[] summarizedAnswersArray;


    // Create static method to switch scenes
    public static Scene startSurveyPage(String greeting, String osSelection, String question1, String question2, String question3) {

        // Create GridPane using the makeGridPane method (and using same parameters as the start method)
        makeGridPane(greeting, osSelection, question1, question2, question3);

        // Create Action Handling for the nextButton
        nextButton.setOnAction(event -> {
            // Create answersArray default values
            String windowsAns = "";
            String linuxAns = "";
            String macAns = "";
            String question1YesAns = "";
            String question1NoAns = "";
            String question2YesAns = "";
            String question2NoAns = "";
            String question3YesAns = "";
            String question3NoAns = "";
            // Initialize answersArray
            answersArray = new String[][] {
                    {windowsAns, linuxAns, macAns},
                    {question1YesAns, question1NoAns},
                    {question2YesAns, question2NoAns},
                    {question3YesAns, question3NoAns}
            };
            // Initialize buttonsArray
            buttonsArray = new RadioButton[][] {
                    {windowsRadioButton, linuxRadioButton, macRadioButton},  // Locations: [Row:[0] Column:[0-2]]
                    {question1Yes, question1No},                            // Locations: [Row:[1] Column:[0-2]]
                    {question2Yes, question2No},                            // Locations: [Row:[2] Column:[0-2]]
                    {question3Yes, question3No}                             // Locations: [Row:[3] Column:[0-2]]
            };
            // Store the answers in the answers array
            for(int theRow = 0; theRow < buttonsArray.length; theRow++){
                for(int theColumn = 0; theColumn < buttonsArray[theRow].length; theColumn++){
                    if(buttonsArray[theRow][theColumn].isSelected()) {
                        answersArray[theRow][theColumn] = buttonsArray[theRow][theColumn].getText();
                    }
                    else{
                        answersArray[theRow][theColumn] = "";
                    }
                }
            }
            // Print the answers
            System.out.println("-------------\n");
            for(String[] currentString : answersArray){
                for(String element : currentString){
                    System.out.println("Answer: " + element);
                }
                System.out.println();
            }

            // Store the answers in a summarized array
            storeAnswers(answersArray);

            // Create variables based on the answers to check how many answers were
            // completed
            int answerFilled = 0;
            int answerNotFilled = 0;
            for(String[] currentString : answersArray){
                for(String element : currentString){
                    if(element.isEmpty()){
                        answerNotFilled = answerNotFilled + 1;
                    }
                    else{
                        answerFilled = answerFilled + 1;
                    }
                }
            }

            // Check if all the questions are answered
            if(answerFilled == 4){
                ReportGUI_StartPage.getStage().setScene
                        (ReportGUI_CommentPage.startCommentPage("Please add additional comments"));
            }
            else{
                ReportGUI_AlertPage.alert(
                        "ALERT", "Please answer all the questions");
            }
        });

        // Create Action Handling for the backButton
        backButton.setOnAction(event ->
                ReportGUI_StartPage.getStage().setScene(ReportGUI_SelectErrorPage.
                        startSelectErrorPage("Please select the error you're experiencing")));

        //Create Action Handling for the quitButton
        quitButton.setOnAction(event -> ReportGUI_QuitPage.display("Quit", "Are you sure?"));

        // Create new BorderPane to nest layouts inside
        borderPane = ReportGUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE, labelBox, buttonBox,
                null, null, gridPane);

        // Set the layout in the Scene
        Scene surveyScene = new Scene(borderPane);

        return surveyScene;
    }

    // Create method to store answers in a summarized answer array, this
    // way there's not white space
    public static void storeAnswers(String[][] array){
        // Create temporary Strings
        String tempOsAnswer = "";
        String tempQuestion1Answer = "";
        String tempQuestion2Answer = "";
        String tempQuestion3Answer = "";

        // Initialize the summarized array
        summarizedAnswersArray = new String[] {tempOsAnswer, tempQuestion1Answer,
                tempQuestion2Answer, tempQuestion3Answer};

        // Populate the summarized Array with the user's answers
        for(int theRow = 0; theRow < array.length; theRow++){
            for(int theColumn = 0; theColumn < array[theRow].length; theColumn++){
                if(array[theRow][theColumn].isEmpty()){
                    continue;
                }
                else{
                    summarizedAnswersArray[theRow] = array[theRow][theColumn];
                }
            }
        }

        // Print out answers for confirmation/debugging
        for (int i = 0; i<summarizedAnswersArray.length; i++){
            System.out.println(summarizedAnswersArray[i]);
        }
        // Store the answers in separate variables
        osAnswer = summarizedAnswersArray[0];
        question1Answer = summarizedAnswersArray[1];
        question2Answer = summarizedAnswersArray[2];
        question3Answer = summarizedAnswersArray[3];

    }

    // Create a method to create a GridPane
    private static GridPane makeGridPane(String greeting, String osSelection, String question1, String question2, String question3){

        // Create new GridPane
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Set variables
        question1Text = question1;
        question2Text = question2;
        question3Text = question3;
        osQuestionText = osSelection;

        // Create Labels
        greetingLabel = ReportGUI_ConvenienceMethods.createLabel(greetingLabel, greeting, Double.MAX_VALUE, Pos.CENTER,
                "Arial", FontWeight.BOLD, 20);

        osLabel = ReportGUI_ConvenienceMethods.createLabel(osLabel, osQuestionText, Double.MAX_VALUE, Pos.BASELINE_LEFT,
                "Arial", FontWeight.BOLD, 15);

        question1Label = ReportGUI_ConvenienceMethods.createLabel(question1Label, question1Text, Double.MAX_VALUE, Pos.BASELINE_LEFT,
                "Arial", FontWeight.BOLD, 15);

        question2Label = ReportGUI_ConvenienceMethods.createLabel(question2Label, question2Text, Double.MAX_VALUE, Pos.BASELINE_LEFT,
                "Arial", FontWeight.BOLD, 15);

        question3Label = ReportGUI_ConvenienceMethods.createLabel(question3Label, question3Text, Double.MAX_VALUE, Pos.BASELINE_LEFT,
                "Arial", FontWeight.BOLD, 15);

        // Create Buttons
        nextButton = new Button("Next");
        backButton = new Button("Back");
        quitButton = new Button("Quit");

        // Create ButtonGroups for RadioButtons
        operatingSystemGroup = new ToggleGroup();
        question1Group = new ToggleGroup();
        question2Group = new ToggleGroup();
        question3Group = new ToggleGroup();

        // Initialize groupArray
        groupArray = new ToggleGroup[] {operatingSystemGroup, question1Group, question2Group, question3Group};

        // Create OS RadioButtons
        windowsRadioButton = new RadioButton("Windows 7/8/10");
        linuxRadioButton = new RadioButton("Linux");
        macRadioButton = new RadioButton("Macintosh");
        // Add OS buttons to group
        windowsRadioButton.setToggleGroup(operatingSystemGroup);
        linuxRadioButton.setToggleGroup(operatingSystemGroup);
        macRadioButton.setToggleGroup(operatingSystemGroup);

        // Create question1 RadioButtons
        question1Yes = new RadioButton("Yes");
        question1No = new RadioButton("No");
        // Add question1 buttons to group
        question1Yes.setToggleGroup(question1Group);
        question1No.setToggleGroup(question1Group);

        // Create question2 RadioButtons
        question2Yes = new RadioButton("Yes");
        question2No = new RadioButton("No");
        // Add question2 buttons to group
        question2Yes.setToggleGroup(question2Group);
        question2No.setToggleGroup(question2Group);

        // Create question3 RadioButtons
        question3Yes = new RadioButton("Yes");
        question3No = new RadioButton("No");
        // Add question3 buttons to group
        question3Yes.setToggleGroup(question3Group);
        question3No.setToggleGroup(question3Group);

        // Create new HBoxes
        buttonBox = ReportGUI_ConvenienceMethods.createHBox(buttonBox, Pos.CENTER, Double.MAX_VALUE, 100, null,
                20, 20, 10 ,10, quitButton, backButton, nextButton);

        labelBox = ReportGUI_ConvenienceMethods.createHBox(labelBox, Pos.CENTER, Double.MAX_VALUE, 30, null,
                20, 20, 10 ,10, greetingLabel);


        // Create new VBoxes
        questionsBox = ReportGUI_ConvenienceMethods.createVBox(questionsBox, Pos.BASELINE_LEFT, Double.MAX_VALUE,
                8, null,20, 20, 10, 10, osLabel, windowsRadioButton,
                linuxRadioButton, macRadioButton, question1Label, question1Yes, question1No, question2Label,
                question2Yes, question2No, question3Label, question3Yes, question3No);

        // Add everything to GridPane
        gridPane.add(questionsBox, 0,1,1,1);

        // Return the gridPane
        return gridPane;
    }



    public static String[][] getAnswersArray(){
        return answersArray;
    }

    public static String[] getSummarizedAnswersArray(){
        return summarizedAnswersArray;
    }

    public static String getOsQuestionText(){
        return osQuestionText;
    }

    public static String getQuestion1Text(){
        return question1Text;
    }

    public static String getQuestion2Text(){
        return question2Text;
    }

    public static String getQuestion3Text(){
        return question3Text;
    }

    public static String getOsAnswer(){
        return osAnswer;
    }
    public static void setOsAnswer(String theOSAnswer){
        osAnswer = theOSAnswer;
    }

    public static String getQuestion1Answer(){
        return question1Answer;
    }
    public static void setQuestion1Answer(String theQuestion1Answer){
        question1Answer = theQuestion1Answer;
    }

    public static String getQuestion2Answer(){
        return question2Answer;
    }
    public static void setQuestion2Answer(String theQuestion2Answer){
        question2Answer = theQuestion2Answer;
    }

    public static String getQuestion3Answer(){
        return question3Answer;
    }
    public static void setQuestion3Answer(String theQuestion3Answer){
        question3Answer = theQuestion3Answer;
    }

    // Get/Set methods for RadioButtons
    public static RadioButton getWindowsRadioButton(){
        return windowsRadioButton;
    }
    public static RadioButton getLinuxRadioButton(){
        return linuxRadioButton;
    }
    public static RadioButton getMacRadioButton(){
        return macRadioButton;
    }
    public static RadioButton getQuestion1Yes(){
        return question1Yes;
    }
    public static RadioButton getQuestion1No(){
        return question1No;
    }
    public static RadioButton getQuestion2Yes(){
        return question2Yes;
    }
    public static RadioButton getQuestion2No(){
        return question2No;
    }
    public static RadioButton getQuestion3Yes(){
        return question3Yes;
    }
    public static RadioButton getQuestion3No(){
        return question3No;
    }

    // Create get/set methods for the ToggleGroup
    public static ToggleGroup getOperatingSystemGroup(){
        return operatingSystemGroup;
    }
    public static ToggleGroup getQuestion1Group(){
        return question1Group;
    }
    public static ToggleGroup getQuestion2Group(){
        return question2Group;
    }
    public static ToggleGroup getQuestion3Group(){
        return question3Group;
    }

    }
