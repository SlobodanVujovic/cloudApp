package com.cloudApp.utility;

import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.Reservations;
import com.cloudApp.sessions.ReservationsFacade;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
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

@Singleton
public class MailManager {

    private Integer currentHour;
    private List<MailArgument> listOfMailArguments;
    private Set<MailArgument> setOfMailArguments;
    private static final Logger LOGGER = Logger.getLogger(MailManager.class.getName());

    @EJB
    private ReservationsFacade reservationsFacade;

    public MailManager() {

    }

    @Schedule(second = "0", minute = "5", hour = "*", persistent = false)
    public void sendNotifications() {
        LOGGER.log(Level.INFO, "sendNotifications() started at: {0}", LocalTime.now());
        currentHour = LocalTime.now().minusHours(1).getHour();
        getClientsMailList();
        createSetOfMailArguments();
        if (!setOfMailArguments.isEmpty()) {
            for (MailArgument mailArgument : setOfMailArguments) {
                if (mailArgument.getRecipientEmail() != null) {
                    sentMail(mailArgument);
                }
            }
        }
    }

    // Popunjava listOfMailArguments koju kasnije koristi sendNotifications() metod. Zato ovaj metod mora ici pre
    // sendNotifications() metoda.
    public void getClientsMailList() {
        // Trazimo listu rezervacija za koje treba poslati podsetnik dana kad se metod pokrece i sata koji
        // je jednak argumentu narednog metoda.
        List<Reservations> listOfReservations = reservationsFacade.getReservationBySendingDateAndTime(currentHour.toString());
        listOfMailArguments = new ArrayList<>();
        if (!listOfReservations.isEmpty()) {
            // Za dobijenu listu rezervacija
            for (Reservations tempReservation : listOfReservations) {
                // i za svaku rezervaciju pravimo MailArgument.
                MailArgument tempMailArgument = new MailArgument();
                String reservationDate = tempReservation.getReservationDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                tempMailArgument.setAppointmentDate((reservationDate));
                String reservationTime = tempReservation.getReservationTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                tempMailArgument.setAppointmentTime(reservationTime);
                // Izdvojimo ClientOrders koji odgovara rezervaciji.
                ClientOrders tempClientOrder = tempReservation.getClientOrdersId();
                // Uzmemo e-mail i ime klijenta iz ClientOrders-a.
                String clientEmail = tempClientOrder.getClientEmail();
                tempMailArgument.setRecipientEmail(clientEmail);
                String clientName = tempClientOrder.getClientName();
                tempMailArgument.setRecipientName(clientName);
                // Uzmemo CompanyOrder - Companies objekat koji odgovara ClientOrders-u.
                CompanyOrder tempCompanyOrder = tempClientOrder.getCompanyOrderId();
                Companies tempCompany = tempCompanyOrder.getCompaniesId();
                String companyName = tempCompany.getCompanyName();
                tempMailArgument.setCompanyName(companyName);
                listOfMailArguments.add(tempMailArgument);
                // Podesimo notificationsSent na true (znaci da smo poslali obavestenje klijentu).
                tempReservation.setNotificationsSent(Boolean.TRUE);
                // Upisemo izmenjenu rezervaciju u DB.
                reservationsFacade.edit(tempReservation);
            }
        }
    }
    
    // Kada isti klijent naruci vise servisa onda za njega ima onoliko unosa u DB koliko je i servisa
    // narucio. Da ne bi dobio onoliko obavestenja koliko je narucio servisa pravimo set MailArgument-a
    // (cak i za razlicite servise MailArgument-i su isti) i na taj nacin ce klijentu biti poslat samo 1
    // mail.
    public void createSetOfMailArguments(){
        setOfMailArguments = new HashSet<>();
        for (MailArgument mailArgument : listOfMailArguments) {
            setOfMailArguments.add(mailArgument);
        }
    }

    public void sendConfirmationAfterServiceSchedule(ConfirmationMailArgument confirmationMail) {
        sentConfirmationMail(confirmationMail);
    }

    public void sentConfirmationMail(ConfirmationMailArgument confirmationMail) {
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
            // TODO Promeniti setFrom adresu u "cloud@mds.rs".
            message.setFrom(new InternetAddress("mdstac@mds.rs"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(confirmationMail.getRecipientEmail()));
            message.setSubject("Service schedule confirmation", "UTF-8");
            message.setSentDate(new Date());
            BodyPart messageBodyPart = new MimeBodyPart();
            String mailBody = "Hello " + confirmationMail.getRecipientName() + ",\n"
                    + "\n"
                    + "Your reservation has been accepted.\n"
                    + "\n"
                    + "Kind Regards,\n"
                    + confirmationMail.getCompanyName() + ".";
            messageBodyPart.setText(mailBody);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

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
            // TODO Promeniti setFrom adresu u "cloud@mds.rs".
            message.setFrom(new InternetAddress("mdstac@mds.rs"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(argument.getRecipientEmail()));
            message.setSubject("Reminder for your appointment", "UTF-8");
            message.setSentDate(new Date());
            BodyPart messageBodyPart = new MimeBodyPart();
            String mailBody = "Hello " + argument.getRecipientName() + ",\n"
                    + "\n"
                    + "We are expecting You on: " + argument.getAppointmentDate() + " at " + argument.getAppointmentTime() + ".\n"
                    + "\n"
                    + "Kind Regards,\n"
                    + argument.getCompanyName() + ".";
            messageBodyPart.setText(mailBody);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            LOGGER.log(Level.INFO, "sentMail() for user {0}", argument.getRecipientName());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
