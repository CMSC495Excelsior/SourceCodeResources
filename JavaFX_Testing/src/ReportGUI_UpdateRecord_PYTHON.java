/*
    Filename: ReportGUI_UpdateRecord_PYTHON
    Author: Stephen James
    Date: 10/04/18

    Class Objective: To create a Java Class that will call a Python script 'RecordUpdate' to update an
                     on-going record stored on the network drive. The Java Class will pass two arguments to the
                     Python script: fileName and recordName. The 'fileName' will be the full path of the newly
                     created Word Doc the program recently created. The 'recordName' will be the full path of the
                     on-going record.

 */

// "C:\Program Files\Python36\python.exe" "C:\Users\sjames\Documents\Python\PenTesting\CommandLineTest.py"

public class ReportGUI_UpdateRecord_PYTHON {

    // Create Class fields
    private static String[] commands;


    public static void updateRecord(){

        // Initialize the String Array of commands to run
        commands = new String[] {
                "\"C:\\Program Files\\Python36\\python.exe\" \"C:\\Users\\sjames\\Documents\\Python\\PenTesting\\CommandLineTest.py\"",
        };

    }
}
