package au.edu.unsw.soacourse.job.dao;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class MailService {
   
    void sendMail(String recipientEmail, String subject, String fileLocation) {
		final String from = "comp9322ass2soa@gmail.com";
		final String password = "randompassword";
		String smtp = "smtp.gmail.com";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		Session session = Session.getInstance(props,
		      new javax.mail.Authenticator() {
		        protected PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication(from, password);
		        }
		      });
		
		System.out.println("Preparing Email");
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(recipientEmail));
			msg.setSubject(subject);
			
			BodyPart messageBodyPart = new MimeBodyPart();
			
			// Now set the actual message
	        messageBodyPart.setText("Enjoy your job alerts!");
	         
			// Create a multipar message
	        Multipart multipart = new MimeMultipart();

			DataSource source = new FileDataSource(fileLocation);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("JobAlerts.xml");
			multipart.addBodyPart(messageBodyPart);
			
			msg.setContent(multipart);
			
			Transport.send(msg);
			System.out.println("Email Sent");
		} catch (AddressException e) {
			System.out.println("Address");
		} catch (MessagingException e) {
			System.out.println(e);
		}
    }
}
