/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author Morten
 */
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private EmailSender instance;
    private final String username = "example@gmail.com";
    private final String password = "123";
    
    private EmailSender(){
        
    }
    
    public EmailSender getInstance(){
        if (instance == null){
            instance = new EmailSender();
        }
        return instance;
    }
    /*
    Sends email to a specified email address, must use a existing gmail account
    with working password
    */
    public String sendEmail(String recipient, String subject, String text) throws RuntimeException {
        Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
                      @Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException("Emailen blev ikke afsendt");
		}
        return "Emailen er blevet afsendt til: "+recipient;
    }
    
    
}
