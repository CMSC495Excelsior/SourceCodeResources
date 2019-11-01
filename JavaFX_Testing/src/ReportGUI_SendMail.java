import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class ReportGUI_SendMail  {

    /*
        Filename: ReportGUI_SendMail.java
        Author: Stephen James
        Date: 10/3/18

        Class objective: To create a Java Class that will be used as a page for the GUI that
                         sends the user's generated document to an IT manager to notify
                         him/her of a new error needed to be solved. This

        *CREDIT NOTE* - Basic framework taken from:
        * https://www.tutorialspoint.com/javamail_api/javamail_api_send_email_with_attachment.htm

        ***NOTE***:      Ideas for improving this Class involve allowing other Email providers by providing a
        *                "other" button. When selected, this button will search a separate Class for the Email
        *                provider's DNS using a HashMap. If found, the Class will use a method to return the
        *                properties of the Host.

     */

    private static boolean mailSent;


    public static void sendMail(String theFromEmail, String theToEmail, String theEmailUsername, String theEmailPassword, String theFileName){

        // Set the host
        String theHost = determineHost();

        // Store the mail properties using the class Properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");

        // Start TLS (security feature)
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", theHost);
        properties.put("mail.smtp.port", "587");

        // Get Session object
        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(theEmailUsername, theEmailPassword);
            }
        });

        try {

            // Make the MIME object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(theFromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(theToEmail));
            message.setSubject("Error Report From: " + ReportGUI_StartPage.getName());

            // Make the BodyPart
            BodyPart bodyPart = new MimeBodyPart();
            ((MimeBodyPart) bodyPart).setText("Dear, " + theToEmail + "\n\n\nA new error has been reported by: " +
                                            ReportGUI_StartPage.getName() + ".\nPlease see the attached document for details." +
                                            "\n\n\nThanks,\n" + "Your System Admin\n" + "ACGI Software");

            //Create the Multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);

            // Attach the document
            bodyPart = new MimeBodyPart();
            String filename = ReportGUI_SaveDocPage.getFilePath() + theFileName + ".docx";
            javax.activation.DataSource source = new FileDataSource(filename);
            bodyPart.setDataHandler(new DataHandler(source));
            bodyPart.setFileName(filename);
            multipart.addBodyPart(bodyPart);

            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Message sent.");
            mailSent = true;

        }catch (MessagingException e){
            System.out.println(e);
            mailSent = false;
            // Throw a new exception (used for telling the EmailPage to start an Alert Popup indicating
            // the failure of the authentication).
            throw new RuntimeException(e);
        }
    }

    // Create method for determining the host based on which email provider user chooses
    private static String determineHost(){
        String host = "";

        if(ReportGUI_EmailPage.getProviderAnswer().equals("Gmail")){
            host = "smtp.gmail.com";
        }
        else if(ReportGUI_EmailPage.getProviderAnswer().equals("Outlook")){
            host = "smtp.office365.com";
        }
        else if(ReportGUI_EmailPage.getProviderAnswer().equals("Comcast")){
            host = "smtp.comcast.net";
        }
        else if(ReportGUI_EmailPage.getProviderAnswer().equals("Yahoo")){
            host = "smtp.mail.yahoo.com";
        }
        else{
            host = "";
        }
        System.out.println("The host is: " + host);

        // Return the host
        return host;
    }

    public static boolean isMailSent(){
        return mailSent;
    }
}
