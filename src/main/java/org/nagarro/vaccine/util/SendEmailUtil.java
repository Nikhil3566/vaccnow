package org.nagarro.vaccine.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.nagarro.vaccine.model.Vaccine;

public class SendEmailUtil {

public static void sendEmail(Vaccine vaccineRequest) {    
   // Recipient's email ID needs to be mentioned.
   String to = vaccineRequest.getEmailId();

   // Sender's email ID needs to be mentioned
  // String from = String.format("vaccineTeam.%s@vaccnow.eg", vaccineRequest.getBranchName());
   
   String from = vaccineRequest.getEmailId();
   // Assuming you are sending email from localhost
   String host = "localhost";

   // Get system properties
   Properties properties = System.getProperties();

   // Setup mail server
   properties.setProperty("mail.smtp.host", host);

   // Get the default Session object.
   Session session = Session.getDefaultInstance(properties);

   try {
      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);

      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));

      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

      // Set Subject: header field
      message.setSubject("Congratulations, Your Vaccination is scheduled!");

      // Now set the actual message
      message.setText(String.format("Dear User, Thanks for applying for vaccination at Branch : %s , Vaccine name : %s , at time : %s ",vaccineRequest.getBranchName(),vaccineRequest.getVaccineName(),vaccineRequest.getScheduledTime()));

      // Send message
      Transport.send(message);
      System.out.println("Sent message successfully....");
   } catch (MessagingException mex) {
      mex.printStackTrace();
   }
}
}