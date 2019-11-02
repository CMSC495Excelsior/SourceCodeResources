import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GUI_FileProcessingPage {


    /* This class will have 3 utility methods for the prior Class (GUI_ChooseTaskPage). This class will either
        manipulate or search the File
     */


    /*
        HashMap should have the String value of the FIRST cell in EACH ROW as the key. The values should all be
        the String value of each cell after that first cell (though you could include the first cell).
        It'd be like this:

        HashMap:
            Key 1: "Joe, Bobby"
                Value(s):
                    "AB3S77FC" (Service Tag),
                    "Dell PC" (Computer Type),
                    "Other Stuff" (Other info)

            Key 2: "James, Stephen"
                Value(s):
                    "HH67Xq772" (Service Tag),
                    "Dell PC" (Computer Type),
                    "Other Stuff" (Other info)




       READ ME!!!!!

            BUG FOUND: If the user changes the file on the first page after going to the future pages, the program will
                        still use the older selected page instead of the new one.
     */


    private static ArrayList<XSSFRow> rows = new ArrayList<>();
    // Make foundValues a HashMap with the Key being the Cell Row/Column and the Value being the actual value of
    // the Cell.
    private static HashMap<String, HashMap<String, String>> foundValues = new HashMap<>();

    // Master Map
    private static TreeMap<Integer, Row> sheetData = new TreeMap<>();
    private static TreeMap<String, String> rowContents = new TreeMap<>();

    // GUI Components
    private static BorderPane borderPane;
    private static GridPane gridPane;
    private static Label greetingLabel, optionsLabel;
    private static TextField searchField;
    private static Button searchButton, backButton, nextButton, selectAll;
    private static CheckBox copyResultsToClipboard, exactMatch;
    private static HBox searchBox, nextBackBox;
    private static VBox optionsBox;
    private static boolean isAllSelected, copy, matchExactly;




    // Create a method that just returns a Scene, then use it to set the scene in the mainStage
    public static Scene fileProcessingPage(String message, String buttonText){
        isAllSelected = true;
        // Make BorderPane
        makeBorderPane(message, buttonText);

        Scene scene = new Scene(borderPane);

        return scene;
    }


    // Give parameters for the makeBorderPane and makeGridPane methods so it's customizable
    private static BorderPane makeBorderPane(String message, String buttonText){
        makeGridPane(message, buttonText);

        borderPane = GUI_ConvenienceMethods.createBorderPane(borderPane, Color.BEIGE,greetingLabel,
                nextBackBox, null, optionsBox, gridPane);

        return borderPane;
    }


    private static GridPane makeGridPane(String message, String buttonText) {
        // Initialize and customize GridPane
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.CENTER);

        // Create a Label for the greeting
        greetingLabel = GUI_ConvenienceMethods.createLabel(greetingLabel, message,
                Double.MAX_VALUE, Pos.CENTER, "Arial", FontWeight.BOLD, 20);
        greetingLabel.setPadding(new Insets(10, 10, 30, 10));

        optionsLabel = GUI_ConvenienceMethods.createLabel(optionsLabel, "Options", Double.MAX_VALUE,
                Pos.CENTER, "Arial", FontWeight.BOLD, 15);

        // Next & Back Buttons
        nextButton = new Button("Next");
        nextButton.setMaxWidth(Double.MAX_VALUE);
        nextButton.setAlignment(Pos.CENTER);

        backButton = new Button("Back");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setAlignment(Pos.CENTER);

        // Search Button
        searchButton = new Button(buttonText);
        searchButton.setMaxWidth(Double.MAX_VALUE);
        searchButton.setAlignment(Pos.CENTER);

        copyResultsToClipboard = new CheckBox("Copy results\nto clipboard");
        copyResultsToClipboard.setAlignment(Pos.CENTER);

        exactMatch = new CheckBox("Exact Pattern\nMatch");
        exactMatch.setAlignment(Pos.CENTER_RIGHT);

        // selectAll CheckBox
        selectAll = new Button("Select All");
        selectAll.setAlignment(Pos.CENTER);
        selectAll.setOnAction(event -> {
            // Create int used for tracking the button click to determine if the CheckBoxes should be checked or not
            // Enter Try statement
            try{
                // Create a List of all the gridPane's children
                List<Node> list = gridPane.getChildren();

                if(isAllSelected){
                    selectAll.setText("Deselect All");
                    for(Node item : list){
                    if(item instanceof CheckBox){
                        ((CheckBox) item).setSelected(true);
                    }
                    else{
                        System.out.println("Lame sauce");
                    }
                }
                isAllSelected = false;
                }
                else{
                    selectAll.setText("Select All");
                    for(Node item : list){
                        if(item instanceof CheckBox){
                            ((CheckBox) item).setSelected(false);
                        }
                        else{
                            System.out.println("Lame sauce");
                        }
                    }
                    isAllSelected = true;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        });

        // Field
        searchField = new TextField();
        searchField.setPrefColumnCount(30);

        // Box to pop-up upon a successful search
        // To create labels for the searched cells, just get the first Row by calling the method for firstRow();
        // What if you just used the numbers and a ',' between them for the key names instead of "ROW X-CELL X"?


        nextBackBox = GUI_ConvenienceMethods.createHBox(nextBackBox, Pos.CENTER, Double.MAX_VALUE, 50, null,
                10, 10, 10, 10, backButton);

        searchBox = GUI_ConvenienceMethods.createHBox(searchBox, Pos.CENTER, Double.MAX_VALUE, 25, null,
                10, 10, 10, 10, searchButton, searchField);

        optionsBox = GUI_ConvenienceMethods.createVBox(optionsBox,Pos.TOP_CENTER, Double.MAX_VALUE, 25, Color.WHITE,
                10,10,10,10, optionsLabel, selectAll, copyResultsToClipboard, exactMatch);

        // Create event listener for openButton
        nextButton.setOnAction(event -> {
            System.out.println("You hit Next");
        });

        // Create event handling for backButton
        backButton.setOnAction(event -> {
            Main_GUI.getMainStage().setScene(GUI_ChooseTaskPage.chooseTaskPage());
        });

        searchButton.setOnAction(event -> {
            // Create a List of all the gridPane's children
            List<Node> list = gridPane.getChildren();
            ArrayList<CheckBox> boxes = new ArrayList<>();
            ArrayList<Integer> columnIndexes = new ArrayList<>();

            // If the child is a CheckBox, add it to the ArrayList
            for(Node item : list){
                if(item instanceof CheckBox){
                    boxes.add((CheckBox) item);
                }
                // Otherwise, continue
                else{
                    continue;
                }
            }

            // Add all selected columns to the search parameters
            for(CheckBox box : boxes){
                if(box.isSelected()){
                    columnIndexes.add(Integer.parseInt(box.getId()));
                }
                else{
                    continue;
                }
            }


            int[] indexes = new int[columnIndexes.size()];

            // Get the ints from the ArrayList
            for(int i = 0; i < indexes.length; i++){
                indexes[i] = columnIndexes.get(i);
                // Print indexes
                System.out.println("Index: " + indexes[i]);
            }

            // Check to see what options the user enables/disables
            copy = copyResultsToClipboard.isSelected();
            matchExactly = exactMatch.isSelected();

            searchFile(Main_GUI.getChosenFile(), searchField.getText(), indexes);
        });

        int i = 0;
        int j = 3;
        // Add the CheckBoxes to the gridPane
        for(CheckBox box : createCheckBoxes(Main_GUI.getChosenFile())){
            if(i == 3){
                i = 0;
                j++;
            }
            box.setPadding(new Insets(5,10,5,10));
            gridPane.add(box, i, j);
            i++;
        }

        // Add all nodes to the GridPane
        gridPane.add(searchBox,0,1,3,1);
        //gridPane.add(optionsBox,2,2,1,1);


        return gridPane;
    }



    //-----------------------------------------------------------------------------------------------------

    //          ROWS            CELLS     VALUES
    // TreeMap<Integer, TreeMap<Integer, String>>

    public static TreeMap<Integer, Row> storeSheetData(File file){
        // Reset the TreeMaps
        rowContents.clear();
        sheetData.clear();

        // Create local int for keeping track of the rows
        int j = 0;

        // Iterate through the sheet and store all data
        try{
            // Create Excel Workbook/Sheet instances
            XSSFWorkbook workbook = new XSSFWorkbook(file.getAbsolutePath());
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Create Iterator to use for the ROWS
            Iterator<org.apache.poi.ss.usermodel.Row> rows = sheet.iterator();

            // Need to iterate through each CELL in each ROW - requires multiple loops
            while(rows.hasNext()){
                // Start iterating through the rows
                Row row = rows.next();

                // Add each row to sheetData
                sheetData.put(j, row);
                j++;
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("The size of the sheet Data is: " + sheetData.size());

        return sheetData;
    }



    public static void addEntry(File file){
        // Try opening the file
        storeSheetData(file);
        System.out.println("Path: " + file.getAbsolutePath());
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(file.getAbsolutePath());
            XSSFSheet sheet = workbook.getSheetAt(0);

            int i = 0;

            Iterator<org.apache.poi.ss.usermodel.Row> rows = sheet.iterator();


            // Need to iterate through each CELL in each ROW - requires multiple loops

            HashMap<Integer, List<String>> sheetData = new HashMap<>();

            while(rows.hasNext()){
                // Start iterating through the cells of current row
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();

                while(cells.hasNext()){
                    // Create local variable for Cells
                    Cell cell = cells.next();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            GUI_AlertPage.alert("ALERT", "Can't open file", Color.RED);
        }


    }

    public static void deleteEntry(File file){
        // USE .xlsb to save the file for Excel?
        storeSheetData(file);

        // Try opening the file
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

        }catch (IOException e){
            e.printStackTrace();
            GUI_AlertPage.alert("ALERT", "Can't open file", Color.RED);
        }

    }

    public static void searchFile(File file, String value, int... columnIndexes) {
        // Use the columnIndexes as a search parameter so that the program will only return cells of
        // that particular index.

        // Clear the foundValues HashMap
        foundValues.clear();

        // Store the sheetData in a HashMap
        storeSheetData(file);

        // Create a local variable
        String tempValue = "";

        for(int i = 0; i < sheetData.size(); i++){
            Row row = sheetData.get(i);
            for(int j = 0; j < row.getPhysicalNumberOfCells(); j++){
                Cell cell = row.getCell(j);
                if(cell == null)
                    continue;

                if(matchExactly){
                    equalsPattern(cell, value, tempValue);
                }
                else{
                    containsPattern(cell, value, tempValue);
                }
            }
        }

        showMatches(file, value, columnIndexes);
    }

    // Create method that searches for patterns contained in Strings
    private static void containsPattern(Cell cell, String searchedValue, String tempValue){
        // Create a local HashMap so the data doesn't get overwritten every time new data is appended to the
        // foundValues HashMap.
        HashMap<String, String> rowAndColumns = new HashMap<>();
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                tempValue = cell.getStringCellValue();
                if(tempValue.toLowerCase().contains(searchedValue.toLowerCase())){
                    System.out.println(searchedValue + " found!");
                    // Add the Row and Column index of the Cell to the rowAndColumn HashMap
                    rowAndColumns.put("row", String.valueOf(cell.getRowIndex()));
                    rowAndColumns.put("column", String.valueOf(cell.getColumnIndex()));
                    foundValues.put(tempValue, rowAndColumns);
                }
                break;
            case Cell.CELL_TYPE_NUMERIC:
                tempValue = String.valueOf(cell.getNumericCellValue());
                if(tempValue.toLowerCase().contains(searchedValue.toLowerCase())){
                    System.out.println(searchedValue + " found!");
                    // Add the Row and Column index of the Cell to the rowAndColumn HashMap
                    rowAndColumns.put("row", String.valueOf(cell.getRowIndex()));
                    rowAndColumns.put("column", String.valueOf(cell.getColumnIndex()));
                    foundValues.put(tempValue, rowAndColumns);
                }
                break;
        }
    }

    // Create method that searches for EXACT patterns in Strings
    private static void equalsPattern(Cell cell, String searchedValue, String tempValue){
        // Create a local HashMap so the data doesn't get overwritten every time new data is appended to the
        // foundValues HashMap.
        HashMap<String, String> rowAndColumns = new HashMap<>();
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING:
                tempValue = cell.getStringCellValue();
                if(tempValue.toLowerCase().equals(searchedValue.toLowerCase())){
                    System.out.println(searchedValue + " found!");
                    // Add the Row and Column index of the Cell to the rowAndColumn HashMap
                    rowAndColumns.put("row", String.valueOf(cell.getRowIndex()));
                    rowAndColumns.put("column", String.valueOf(cell.getColumnIndex()));
                    foundValues.put(tempValue, rowAndColumns);
                }
                break;
            case Cell.CELL_TYPE_NUMERIC:
                tempValue = String.valueOf(cell.getNumericCellValue());
                if(tempValue.toLowerCase().equals(searchedValue.toLowerCase())){
                    System.out.println(searchedValue + " found!");
                    // Add the Row and Column index of the Cell to the rowAndColumn HashMap
                    rowAndColumns.put("row", String.valueOf(cell.getRowIndex()));
                    rowAndColumns.put("column", String.valueOf(cell.getColumnIndex()));
                    foundValues.put(tempValue, rowAndColumns);
                }
                break;
        }
    }


    // Method to display the information of the searched terms.
    private static void showMatches(File file, String value, int... columnIndexes){
        // Create Objects used for copying data to clipboard
        StringSelection selection;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // Create a StringBuilder Object to append all requested data for copying
        StringBuilder builder = new StringBuilder();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getAbsolutePath());
            XSSFSheet sheet = workbook.getSheetAt(0);

            /*
                Print out:

                    builder.append("Match for  " + key + ": " + cell.getStringCellValue() + "\n");

             */

            // Search for the given value in the rowContents HashMap
            if (!foundValues.isEmpty()) {
                System.out.println("YES! The sheet contains: " + value);
                // Iterate through each found value KEY
                for (String key : foundValues.keySet()) {
                    System.out.println("\n\nKey found! Key is: " + key );

                    // Set the current row equal to the row number of the found value
                    Row row = sheet.getRow(Integer.parseInt(
                            foundValues.get(key).get("row")));

                    // Iterate through each CELL here to begin checking indexes
                    for(Cell cell : row) {
                        // Check each CELL's column index against the PASSED indexes here
                        for(int i = 0; i < columnIndexes.length; i++){
                            if(cell.getColumnIndex() != columnIndexes[i]){
                                continue;
                            }
                            else{
                                if(cell == null)
                                    continue;
                                switch (cell.getCellType()) {
                                    case Cell.CELL_TYPE_STRING:
                                        if(columnIndexes.length > 1){
                                            builder.append(cell.getStringCellValue() + "\n");
                                        }
                                        else{
                                            builder.append(cell.getStringCellValue() + "\n");
                                        }
                                        System.out.println("Match for " + key + ": " + cell.getStringCellValue() + "\n");
                                        break;
                                    case Cell.CELL_TYPE_NUMERIC:
                                        if(columnIndexes.length > 1){
                                            builder.append(cell.getNumericCellValue() + "\n");
                                        }
                                        else{
                                            builder.append(cell.getNumericCellValue() + "\n");
                                        }
                                        System.out.println("Match for " + key + ": " + cell.getNumericCellValue() + "\n");
                                        break;
                                    case Cell.CELL_TYPE_FORMULA:
                                        switch (cell.getCachedFormulaResultType()){
                                            case Cell.CELL_TYPE_NUMERIC:
                                                if(columnIndexes.length > 1){
                                                    builder.append(cell.getNumericCellValue() + "\n");
                                                }
                                                else{
                                                    builder.append(cell.getNumericCellValue() + "\n");
                                                }
                                                break;
                                            case Cell.CELL_TYPE_STRING:
                                                if(columnIndexes.length > 1){
                                                    builder.append(cell.getStringCellValue() + "\n");
                                                }
                                                else{
                                                    builder.append(cell.getStringCellValue() + "\n");
                                                }
                                                break;
                                        }
                                        System.out.println("Formula for " + key + ": " + cell.getCellFormula() + "\n");
                                        break;
                                    case Cell.CELL_TYPE_ERROR:
                                        if(columnIndexes.length > 1){
                                            builder.append(cell.getErrorCellValue() + "\n");
                                        }
                                        else{
                                            builder.append(cell.getErrorCellValue() + "\n");
                                        }
                                        System.out.println("Match for " + key + ": " + cell.getErrorCellValue() + "\n");
                                        break;
                                    case Cell.CELL_TYPE_BOOLEAN:
                                        if(columnIndexes.length > 1){
                                            builder.append(cell.getBooleanCellValue() + "\n");
                                        }
                                        else{
                                            builder.append(cell.getBooleanCellValue() + "\n");
                                        }
                                        System.out.println("Match for " + key + ": " + cell.getBooleanCellValue() + "\n");
                                        break;
                                    default:
                                        System.out.println("Error");
                                        builder.append(cell.toString() + "\n");
                                }
                                if(copy){
                                    selection = new StringSelection(builder.toString());
                                    clipboard.setContents(selection, null);
                                }

                            }
                        }
                    }

                }
                GUI_AlertPage.alert("Results", "Matches: \n\n" + builder.toString(), Color.BEIGE);
            } else {
                System.out.println("Sorry. " + value + " is not in the sheet");
                GUI_AlertPage.alert("ALERT", "Cannot find " + value + " in sheet.", Color.RED);
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Create method to create CheckBoxes with text from the first row (headers)
    private static ArrayList<CheckBox> createCheckBoxes(File file){
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();

        // Enter try block
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(file.getAbsolutePath());
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Create Iterator to use for the ROWS
            Iterator<org.apache.poi.ss.usermodel.Row> rows = sheet.iterator();

            // Set the current row equal to next iteration of rows from the rows Iterator
            Row row = rows.next();

            // Iterate through each cell and create an appropriate CheckBox for each Cell/header
            for(Cell cell : row){
                switch (cell.getCellType()){
                    // Create CheckBoxes
                    case Cell.CELL_TYPE_STRING:
                        System.out.println("String CheckBox");
                        CheckBox stringBox = new CheckBox(cell.getStringCellValue());
                        stringBox.setId(String.valueOf(cell.getColumnIndex()));
                        checkBoxes.add(stringBox);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.println("Numeric CheckBox");
                        CheckBox numBox = new CheckBox(String.valueOf(cell.getNumericCellValue()));
                        numBox.setId(String.valueOf(cell.getColumnIndex()));
                        checkBoxes.add(numBox);
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        System.out.println("Formula CheckBox");
                        CheckBox formBox = new CheckBox(String.valueOf(cell.getCellFormula()));
                        formBox.setId(String.valueOf(cell.getColumnIndex()));
                        checkBoxes.add(formBox);
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        System.out.println("Error CheckBox");
                        CheckBox errorBox = new CheckBox(String.valueOf(cell.getErrorCellValue()));
                        errorBox.setId(String.valueOf(cell.getColumnIndex()));
                        checkBoxes.add(errorBox);
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.println("Boolean CheckBox");
                        CheckBox booleanBox = new CheckBox(String.valueOf(cell.getBooleanCellValue()));
                        booleanBox.setId(String.valueOf(cell.getColumnIndex()));
                        checkBoxes.add(booleanBox);
                        break;
                    default:
                        System.out.println("Error");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // Iterate through all CheckBoxes for troubleshooting
        for(CheckBox box: checkBoxes){
            box.setOnAction(event -> {
                if(box.isSelected()){
                    System.out.println(box.getText() + " is selected.");
                }
                else{
                    System.out.println(box.getText() + " is NOT selected.");
                }
            });


        }
        // Create a Row for the first row in sheet
        return checkBoxes;

    }

}
