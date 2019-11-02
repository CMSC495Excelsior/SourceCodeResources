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

import java.util.ArrayList;

public class GUI_ChooseTaskPage {

    // Create GUI components
    private static BorderPane borderPane;
    private static GridPane gridPane;
    private static Label greetingLabel;
    private static Button nextButton, backButton;
    private static VBox radioButtonsBox;
    private static HBox nextBackBox;
    private static RadioButton addEntryButton, deleteEntryButton, searchButton;
    private static ArrayList<RadioButton> buttonList = new ArrayList<>();
    private static ToggleGroup buttonsGroup;


    // Create a method that just returns a Scene, then use it to set the scene in the mainStage
    public static Scene chooseTaskPage(){
        // Make BorderPane
        makeBorderPane();

        Scene scene = new Scene(borderPane);

        return scene;
    }


    private static BorderPane makeBorderPane(){
        makeGridPane();

        borderPane = GUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE,greetingLabel,
                nextBackBox, null, null, gridPane);

        return borderPane;
    }


    private static GridPane makeGridPane(){
        // Initialize and customize GridPane
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setAlignment(Pos.CENTER);

        // Create a Label for the greeting
        greetingLabel = GUI_ConvenienceMethods.createLabel(greetingLabel, "Please select your desired task",
                Double.MAX_VALUE, Pos.CENTER, "Arial", FontWeight.BOLD, 20);
        greetingLabel.setPadding(new Insets(10,10,30,10));

        // Next & Back Buttons
        nextButton = new Button("Next");
        nextButton.setMaxWidth(Double.MAX_VALUE);
        nextButton.setAlignment(Pos.CENTER);
        //nextButton.setVisible(false);

        backButton = new Button("Back");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setAlignment(Pos.CENTER);

        buttonsGroup = new ToggleGroup();

        // Create radio buttons
        addEntryButton = new RadioButton("Add Cell Entries");
        addEntryButton.setUserData("Add");
        addEntryButton.setToggleGroup(buttonsGroup);
        addEntryButton.setSelected(true);

        deleteEntryButton = new RadioButton("Delete Cell Entries");
        deleteEntryButton.setUserData("Delete");
        deleteEntryButton.setToggleGroup(buttonsGroup);

        searchButton = new RadioButton("Search Cell Entries");
        searchButton.setUserData("Search");
        searchButton.setToggleGroup(buttonsGroup);

        // Add buttons to list
        buttonList.add(addEntryButton);
        buttonList.add(deleteEntryButton);
        buttonList.add(searchButton);


        // Create HBox to store the button and field in
        radioButtonsBox = GUI_ConvenienceMethods.createVBox(radioButtonsBox,Pos.CENTER,Double.MAX_VALUE, 50,
                null, 10,10,10,10, addEntryButton, deleteEntryButton,
                searchButton);

        nextBackBox = GUI_ConvenienceMethods.createHBox(nextBackBox, Pos.CENTER, Double.MAX_VALUE, 50, null,
                10,10,10,10, backButton, nextButton);

        // Create event listener for openButton
        nextButton.setOnAction(event -> {
            // Enter for loop
            for(Toggle toggle : buttonsGroup.getToggles()){
                if(toggle.isSelected()){
                    switch (toggle.getUserData().toString().toLowerCase()){
                        case "add":
                            Main_GUI.getMainStage().setScene(GUI_FileProcessingPage.
                                    fileProcessingPage("Enter term to add and\nselect all parameters", "Add"));
                            System.out.println("Add");
                            break;
                        case "delete":
                            Main_GUI.getMainStage().setScene(GUI_FileProcessingPage.
                                    fileProcessingPage("Enter term to delete and\nselect all parameters", "Delete"));
                            System.out.println("Delete");
                            break;
                        case "search":
                            System.out.println("Searching");
                            Main_GUI.getMainStage().setScene(GUI_FileProcessingPage.
                                    fileProcessingPage("Enter your search term and\nselect all search parameters", "Search"));
                            break;
                    }
                }
                else{
                    // Do nothing
                }
            }


        });

        // Create event handling for backButton
        backButton.setOnAction(event -> {
            Main_GUI.getMainStage().setScene(Main_GUI.getMainScene());
        });


        // Add all nodes to the GridPane
        //gridPane.add(greetingLabel,0,0,2,1);
        gridPane.add(radioButtonsBox,0,1,2,1);
        //gridPane.add(nextBackBox,0,2,2,1);


        return gridPane;
    }

}
