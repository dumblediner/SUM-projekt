package Service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dumblediner
 */
public class SendEmail {
    private SendEmail instance;
    private SendEmail(){
        
    }
    
    public SendEmail getInstance(){
        if (instance == null){
            instance = new SendEmail();
        }
        return instance;
    }
    public void sendEmail(String recipient, String subject, String text){
      // Recipient's email ID needs to be mentioned.

      // Sender's email ID needs to be mentioned
      String from = "abc@gmail.com";//change accordingly
      final String username = "abc";//change accordingly
      final String password = "*****";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "smtp.gmail.com";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "587");

      Session session;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

      try {
         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
         InternetAddress.parse(recipient));

         // Set Subject: header field
         message.setSubject(subject);
         // Now set the actual message
         message.setText(text);

         // Send message
         Transport.send(message);

         System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
            throw new RuntimeException(e);
      }
   }
    
}
