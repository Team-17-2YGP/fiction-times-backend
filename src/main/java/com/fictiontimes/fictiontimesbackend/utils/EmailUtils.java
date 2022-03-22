package com.fictiontimes.fictiontimesbackend.utils;

import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailUtils {

    private static Session session = null;
    private static final Logger logger = Logger.getLogger(ReaderService.class.getName());

    public static Session getSession() throws IOException {
        if (session == null) {
            Properties properties = new Properties();
            properties.load(EmailUtils.class.getResourceAsStream("/email.properties"));
            session = Session.getInstance(
                    properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    properties.getProperty("username"),
                                    properties.getProperty("password")
                            );
                        }
                    }
            );
            return session;
        }
        return session;
    }

    public static void sendMail(User user, String subject, String body, String link) throws IOException {
        link = link.replace("{{userId}}", String.valueOf(user.getUserId()));
        try {
            Message message = new MimeMessage(getSession());
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail())
            );
            message.setSubject(subject);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            String content = "Hello "+ user.getFirstName() + " " + user.getLastName()
                    + ", <br /> <br />"
                    + body
                    + "<br /> <br />"
                    + "<a href=\"" + link + "\">Click here</a>"
                    + "<br /> <br />"
                    + "Cheers, <br/> Fiction times team"
                    ;
            logger.info("Email: \n" + content);
            mimeBodyPart.setContent(content, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmailBulk (List<User> users, String subject, String body, String link) {
        Thread thread = new Thread(() -> {
            for (User user: users) {
                try {
                    sendMail(user, subject, body, link);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
