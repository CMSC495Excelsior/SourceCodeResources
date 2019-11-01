import org.apache.log4j.BasicConfigurator;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

    /*
        Filename: ReportGUI_CreateDocument.java
        Author: Stephen James
        Date: 10/3/18

        Class objective: To create a Java Class that will be used to actually create a Word Doc
                         with the user's information summarized in an organized format. The class
                         will use XWPFDocument to do this.

        ***NOTE***:      The bottom half of this Class is still under development. The ultimate goal with
        *                this class is to not only create a document on the user's local machine, but also
        *                update an on-going record of Error Reports kept on a network drive.

     */



public class ReportGUI_CreateDocument {

    private static String nameString, departmentString, emailString, phoneNumberString, additionalCommentsString,
            date, time, lineBreak;

    private static XWPFDocument document;
    private static FileReader fileReader;
    private static FileWriter fileWriter;
    private static BufferedWriter bufferedWriter;
    private static PrintWriter printWriter;

    private static File localFile;
    private static XWPFParagraph[] paragraphs;


    public static void makeDoc(String theErrorType, String theName, String theDepartment, String theEmail, String thePhoneNumber, String fileName,
                               String filePath,String osQuestion, String osAnswer, String theQuestion1, String theQuestion1Answer, String theQuestion2, String theQuestion2Answer,
                               String theQuestion3, String theQuestion3Answer, String theAdditionalComments)throws Exception{
        // Capture the current date
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm a");
        Date currentDate = new Date();
        Date currentTime = new Date();
        date = dateFormat.format(currentDate);
        time = timeFormat.format(currentTime);


        // Configure logging
        BasicConfigurator.configure();

        nameString = "Name: ";
        departmentString = "Department: ";
        emailString = "Email: ";
        phoneNumberString = "Phone Number: ";
        additionalCommentsString = "Additional Comments: ";
        lineBreak = "________________________________________________________________________________________";

        try{
            // Create Document
            document = new XWPFDocument();
            localFile = new File(filePath + "\\" + fileName+ ".docx");
            //Write the Document in file system
            FileOutputStream out = new FileOutputStream(localFile);

            // Create a paragraph for the diving section/title
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            titleParagraph.setPageBreak(true);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("----------NEW REPORT FROM: " + theName + "----------");
            titleParagraph.setSpacingAfter(BigInteger.valueOf(100));

            XWPFParagraph errorTypeParagraph = document.createParagraph();
            errorTypeParagraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun errorTypeRun = errorTypeParagraph.createRun();
            errorTypeRun.setBold(true);
            errorTypeRun.setText("ERROR TYPE: " + theErrorType);

            XWPFParagraph dateParagraph = document.createParagraph();
            dateParagraph.setSpacingAfter(BigInteger.TEN);
            dateParagraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun dateRun = dateParagraph.createRun();
            dateRun.setBold(true);
            dateRun.setText("Date: " + date);

            XWPFParagraph timeParagraph = document.createParagraph();
            timeParagraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun timeRun = timeParagraph.createRun();
            timeRun.setBold(true);
            timeRun.setText("Time: " + time);

            // Create a paragraph for the user's name
            XWPFParagraph nameParagraph = document.createParagraph();
            nameParagraph.setSpacingAfter(BigInteger.TEN);
            XWPFRun nameRun = nameParagraph.createRun();
            XWPFRun nameRun2 = nameParagraph.createRun();
            nameRun2.setBold(false);
            nameRun2.setText(theName);
            nameRun.setBold(true);
            nameRun.setText(nameString);

            // Create a paragraph for the user's department
            XWPFParagraph departmentParagraph = document.createParagraph();
            departmentParagraph.setSpacingAfter(BigInteger.TEN);
            XWPFRun departmentRun = departmentParagraph.createRun();
            XWPFRun departmentRun2 = departmentParagraph.createRun();
            departmentRun2.setBold(false);
            departmentRun2.setText(theDepartment);
            departmentRun.setBold(true);
            departmentRun.setText(departmentString);

            // Create a paragraph for the user's email
            XWPFParagraph emailParagraph = document.createParagraph();
            emailParagraph.setSpacingAfter(BigInteger.TEN);
            XWPFRun emailRun = emailParagraph.createRun();
            XWPFRun emailRun2 = emailParagraph.createRun();
            emailRun2.setBold(false);
            emailRun2.setText(theEmail);
            emailRun.setBold(true);
            emailRun.setText(emailString);

            // Create a paragraph for the user's phone number
            XWPFParagraph phoneNumberParagraph = document.createParagraph();
            XWPFRun phoneNumberRun = phoneNumberParagraph.createRun();
            XWPFRun phoneNumberRun2 = phoneNumberParagraph.createRun();
            phoneNumberRun2.setBold(false);
            phoneNumberRun2.setText(thePhoneNumber);
            phoneNumberRun.setBold(true);
            phoneNumberRun.setText(phoneNumberString);

            // Create a paragraph for question1 on Survey
            XWPFParagraph surveyOSQuestionParagraph = document.createParagraph();
            XWPFRun surveyOSQuestionRun = surveyOSQuestionParagraph.createRun();
            surveyOSQuestionRun.setBold(true);
            surveyOSQuestionRun.setText(osQuestion);

            // Create a paragraph for question1 on Survey
            XWPFParagraph surveyOSAnswerParagraph = document.createParagraph();
            XWPFRun surveyOSAnswerRun = surveyOSAnswerParagraph.createRun();
            surveyOSAnswerRun.setBold(false);
            surveyOSAnswerRun.setText(osAnswer);

            // Create a paragraph for question1 on Survey
            XWPFParagraph surveyQuestion1Paragraph = document.createParagraph();
            XWPFRun surveyQuestion1Run = surveyQuestion1Paragraph.createRun();
            surveyQuestion1Run.setBold(true);
            surveyQuestion1Run.setText(theQuestion1);

            // Create a paragraph for question1 answer on Survey
            XWPFParagraph surveyQuestion1AnswerParagraph = document.createParagraph();
            XWPFRun surveyQuestion1AnswerRun = surveyQuestion1AnswerParagraph.createRun();
            surveyQuestion1AnswerRun.setText(theQuestion1Answer);

            // Create a paragraph for question2 on Survey
            XWPFParagraph surveyQuestion2Paragraph = document.createParagraph();
            XWPFRun surveyQuestion2Run = surveyQuestion2Paragraph.createRun();
            surveyQuestion2Run.setBold(true);
            surveyQuestion2Run.setText(theQuestion2);

            // Create a paragraph for question1 answer on Survey
            XWPFParagraph surveyQuestion2AnswerParagraph = document.createParagraph();
            XWPFRun surveyQuestion2AnswerRun = surveyQuestion2AnswerParagraph.createRun();
            surveyQuestion2AnswerRun.setText(theQuestion2Answer);

            // Create a paragraph for question3 on Survey
            XWPFParagraph surveyQuestion3Paragraph = document.createParagraph();
            XWPFRun surveyQuestion3Run = surveyQuestion3Paragraph.createRun();
            surveyQuestion3Run.setBold(true);
            surveyQuestion3Run.setText(theQuestion3);

            // Create a paragraph for question1 answer on Survey
            XWPFParagraph surveyQuestion3AnswerParagraph = document.createParagraph();
            XWPFRun surveyQuestion3AnswerRun = surveyQuestion3AnswerParagraph.createRun();
            surveyQuestion3AnswerRun.setText(theQuestion3Answer);

            // Create a paragraph for additional comments title
            XWPFParagraph additionalCommentsParagraphTitle = document.createParagraph();
            XWPFRun additionalCommentsTitleRun = additionalCommentsParagraphTitle.createRun();
            additionalCommentsTitleRun.setBold(true);
            additionalCommentsTitleRun.setText(additionalCommentsString);

            // Create a paragraph for additional comments title
            XWPFParagraph additionalCommentsParagraph = document.createParagraph();
            XWPFRun additionalCommentsRun = additionalCommentsParagraph.createRun();
            additionalCommentsRun.setText(theAdditionalComments);

            // Create a paragraph for the diving section/title
            XWPFParagraph endParagraph = document.createParagraph();
            endParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun endRun = endParagraph.createRun();
            endRun.setText("----------REPORT FROM: " + theName + " FINISHED" + "----------");
            endParagraph.setSpacingAfter(BigInteger.valueOf(100));

            paragraphs = document.getParagraphs();

            document.write(out);
            out.close();
            // Open file for user to see
            //desktop.open(localFile);

            System.out.println(fileName + "written successfully");
        }catch (Exception e){
            System.out.println(e);
            // Throw new exception to tell the "SaveDocPage" to let the user know they entered an
            // invalid file name or path
            throw new Exception(e);
        }

        /***THIS SECTION IS CURRENTLY UNDER DEVELOPMENT***
         * GOAL: The section below will append the user's information and survey results to an
         * on-going record stored on a network drive.
         */


        try{
            // https://excelinjava.wordpress.com/2016/03/13/creating-docx/

            // Create temporary String
            String tempContent = "";
            // Create the File for the on-going record
            File recordFile = new File("P:\\tmp\\Java_Error-Report_Records\\Error_Report_Test.docx");
            FileReader fileReader = new FileReader(localFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // Create a List for adding the user's paragraphs to the record
            List<String> paragraphList = new ArrayList<>();

            // Read all data in user file
            while((tempContent = bufferedReader.readLine()) != null){
                paragraphList.add(tempContent);
            }

            // Create a new document called record
            XWPFDocument record = new XWPFDocument();

            XWPFParagraph recordParagraph = record.createParagraph();
            XWPFRun recordRun = recordParagraph.createRun();

            for(int i = 0; i < paragraphs.length; i++){
                System.out.println("Paragraphs: " + paragraphs[i]);
            }

            for(int i = 0; i < paragraphs.length; i++){
                XWPFParagraph tempParagraph = paragraphs[i];
                XWPFRun tempRun = tempParagraph.createRun();
                tempRun.setText(paragraphList.get(i));
                recordRun.setText(tempRun.getText());
            }
            //recordRun.setText(tempContent + "\n");

            // Create a FileOutputStream for writing the paragraphs
            FileOutputStream writeToRecord = new FileOutputStream(recordFile);

            record.write(writeToRecord);
            writeToRecord.close();

            System.out.println("Record Updated");



        }catch (Exception e){
            System.out.println(e);
            throw new Exception(e);

        }




    }
}

/*
if(fileIn.hasNextByte()){
                    System.out.println("Found Byte: " + fileIn.nextByte());
                }
                System.out.println("Byte Not Found: " + fileIn.next());
                //printWriter.println(fileIn.nextByte());
 */