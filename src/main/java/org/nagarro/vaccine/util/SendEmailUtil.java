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
	
	private SendEmailUtil() {
		super();
	}

	public static void sendEmail(Vaccine vaccineRequest) {
		// Recipient's email ID needs to be mentioned.
		String to = vaccineRequest.getRequesterEmailId();

		// Sender's email ID needs to be mentioned
		String from = String.format("vaccineTeam.%s@vaccnow.eg", vaccineRequest.getBranchName());

		// Get system properties
		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.defaultEncoding", "UTF-8");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.required", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");

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

			message.setText(String.format(
					"Dear User,%n%nThanks for applying for vaccination. Please find below the INVOICE CERTIFICATE. %n%nVaccine name : %s%nBranch name : %s%nTime of appointment : %s%nPayment received through : %s%n%nPlease reach the venue at scheduled time to avoid any hassle. Thanks for applying. We wish a good health for you and your family.",
					vaccineRequest.getVaccineName(), vaccineRequest.getBranchName(), vaccineRequest.getScheduledTime(),
					vaccineRequest.getPaymentMethod()));

			// Send message
			Transport.send(message, "<google_mail_id>", "<google_mail_password>");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}