package com.cloudApp.utility;

import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.Reservations;
import com.cloudApp.sessions.ReservationsFacade;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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

/**
 *
 * @author svujovic
 */
@Singleton
public class MailManager {

    private Integer currentHour;
    private List<MailArgument> listOfMailArguments;

    @EJB
    private ReservationsFacade reservationsFacade;

    public MailManager() {

    }

//    Popunjava listOfMailArguments koju kasnije koristi sendNotifications() metod. Zato ovaj metod mora ici pre
//    sendNotifications() metoda.
    public void getClientsMailList() {
        // Trazimo listu rezervacija za koje treba poslati podsetnik dana kad se metod pokrece i sata koji
        // je jednak argumentu narednog metoda.
        List<Reservations> listOfReservations = reservationsFacade.getReservationBySendingDateAndTime(currentHour.toString());
        listOfMailArguments = new ArrayList<>();
        if (!listOfReservations.isEmpty()) {
            DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
            // Za dobijenu listu rezervacija
            for (Reservations tempReservation : listOfReservations) {
                // i za svaku rezervaciju pravimo MailArgument.
                MailArgument tempMailArgument = new MailArgument();
                String reservationDate = mediumDf.format(tempReservation.getReservationDate());
                tempMailArgument.setAppointmentDate((reservationDate));
                String reservationTime = tempReservation.getReservationTime();
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

    @Schedule(minute = "5,35", hour = "*", dayOfWeek = "*", persistent = false)
    public void sendNotifications() {
        currentHour = LocalTime.now().minusHours(1).getHour();
        getClientsMailList();
        if (!listOfMailArguments.isEmpty()) {
            for (MailArgument mailArgument : listOfMailArguments) {
                if (mailArgument.getRecipientEmail() != null) {
                    sentMail(mailArgument);
                }
            }
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
            message.setFrom(new InternetAddress("cloud@mds.rs"));
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
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
