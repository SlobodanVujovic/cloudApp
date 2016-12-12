package com.cloudApp.utility;

import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {

    public void sentMail(MailArgument argument) {
        Properties props = new Properties();
        // Drugi parametar je ime mail server-a ili njegova IP adresa.
        props.setProperty("mail.smtp.host", "email.mds.rs");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Autentifikacija prilikom slanja mail-ova.
                return new PasswordAuthentication("cloud@mds.rs", "cloud");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setFrom(new InternetAddress("cloud@mds.rs"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(argument.getRecipientEmail()));
            message.setSubject("Reminder for your appointment", "UTF-8");
            message.setSentDate(new Date());
            BodyPart messageBodyPart = new MimeBodyPart();
            String mailBody = "Hello, " + argument.getRecipientName() + ",\n"
                    + "\n"
                    + "We are expecting You on " + argument.getAppointmentDate() + " at " + argument.getAppointmentTime() + ".\n"
                    + "\n"
                    + "Kind Regards,\n"
                    + argument.getCompanyName() + ".";
            messageBodyPart.setText(mailBody);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
