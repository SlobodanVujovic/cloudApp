package com.cloudApp.controller;

import com.cloudApp.entity.Agents;
import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.Reservations;
import com.cloudApp.entity.Services;
import com.cloudApp.sessions.AgentsFacade;
import com.cloudApp.sessions.ClientOrdersFacade;
import com.cloudApp.sessions.CompanyOrderFacade;
import com.cloudApp.sessions.ReservationsFacade;
import com.cloudApp.sessions.ServicesFacade;
import com.cloudApp.utility.ConfirmationMailArgument;
import com.cloudApp.utility.MailManager;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;

@Named
@ViewScoped
public class ServicesController implements Serializable {

    // orderId je u URL-u (tu vrednost uzimamo iz GET-a) i na osnovu ovog parametra odredjujemo sve ostalo sto
    // se tice podataka vezanih za tu kompaniju.
    private int orderId;
    private CompanyOrder order;
    private List<Services> allServices;
    private List<String> serviceIdWithRequiredReservation;
    private String checkedServices;
    private Companies company;
    private List<Agents> allAgents;
    private Agents selectedAgent;
    private ClientOrders clientOrder;
    private Reservations reservation;
    private static final Logger LOGGER = Logger.getLogger(ServicesController.class.getName());

    public ServicesController() {

    }

    @PostConstruct
    public void init() {
        clientOrder = new ClientOrders();
        reservation = new Reservations();
    }

    @Inject
    private CompanyOrderFacade orderFacade;
    @Inject
    private ClientOrdersFacade clientOrderFacade;
    @Inject
    private ServicesFacade servicesFacade;
    @Inject
    private AgentsFacade agentsFacade;
    @Inject
    private ReservationsFacade reservationsFacade;
    @EJB
    private MailManager mailManager;

    public void setCompanyOrder() {
        order = orderFacade.find(orderId);
    }

    public List<Services> getAllServices() {
        allServices = servicesFacade.getServicesByCompanyOrderId(order);
        return allServices;
    }

    public List<String> getServiceIdWithRequiredReservation() {
        return serviceIdWithRequiredReservation;
    }

    public void setServiceIdWithRequiredReservation() {
        allServices = getAllServices();
        serviceIdWithRequiredReservation = new ArrayList<>();
        for (Services service : allServices) {
            if (service.getReservation() == true) {
                String serviceName = service.getId().toString();
                serviceIdWithRequiredReservation.add(serviceName);
            }
        }
    }

    public String takeClientsOrder() {
        String[] idsOfCheckedServices = checkedServices.split(" ");
        for (int i = 0; i < idsOfCheckedServices.length; i++) {
            // find() metodi prosledjujemo ID objekata, koji trazimo iz DB-a, u obliku Integer-a.
            Services tempCheckedService = servicesFacade.find(Integer.parseInt(idsOfCheckedServices[i]));
            clientOrder.setServicesId(tempCheckedService);
            clientOrder.setCompanyOrderId(order);
            clientOrder.setAgentsId(selectedAgent);
            clientOrderFacade.create(clientOrder);
            // Proverimo da li servis zahteva rezervaciju.
            if (tempCheckedService.getReservation()) {
//              Ako zahteva, proverimo da li je setovani datum.
                LocalDate reservationDate = reservation.getReservationDate();
                if (reservationDate != null) {
                    LocalTime reservationLocalTime = reservation.getReservationTime();
                    // Kreiramo LocalDateTime da bi od njega oduzeli vreme notifikacije, odnosno da ne bi morali da brinemo 
                    // kada se menja samo vreme a kada i datum.
                    LocalDateTime reservationLocalDateTime = LocalDateTime.of(reservationDate, reservationLocalTime);
                    // Nadjemo notification vrednost.
                    CompanyOrder companyOrder = clientOrder.getCompanyOrderId();
                    int notification = companyOrder.getNotification();
//                  Oduzimamo "notification + 1", a ovo "+1" je iz razloga sto kada saljmo notifikaciju, kroz sendNotifications()
//                  metodu, za trenutno vreme uzimamo pravo trenutno vreme -1h sto znaci da i notifikacija treba da bude 1h ranije
//                  nego sto bi inace bila.
                    LocalDateTime sendingLocalDateTime = reservationLocalDateTime.minusHours(notification + 1);
                    // Nakon odredjivanja trenutnka slanja, razdvojimo na date i time deo.
                    LocalDate sendingLocalDate = sendingLocalDateTime.toLocalDate();
                    LocalTime sendingLocalTime = sendingLocalDateTime.toLocalTime();
                    // TODO Kada isti klijent naruci vise servisa onda za njega ima onoliko unosa u DB koliko je i servisa
                    // narucio. Samim tim ce i dobiti onoliko obavestenja koliko je narucio servisa. Videti sta da se radi 
                    // sa ovim.
                    reservation.setSendingDate(sendingLocalDate);
                    reservation.setSendingTime(sendingLocalTime);
                    reservation.setClientOrdersId(clientOrder);
//                  Upis rezervacije u bazu.
                    reservationsFacade.create(reservation);
                }
            }
        }
        // Pravimo da li je klijent uneo svoju E-mail adresu.
        if (!clientOrder.getClientEmail().isEmpty()) {
            // Proverimo da li je upisan u DB (tako sto vidimo da li mu je dodeljen ID).
            if (clientOrder.getId() != null) {
                LOGGER.log(Level.INFO, "Client order with ID = {0} is written to DB.", clientOrder.getId());
                // Ako jeste pravimo confirmationMailArgument da bi mogli da posaljemo mail da je rezervacija prihvacena ili nije.
                ConfirmationMailArgument confirmationMailArgument = new ConfirmationMailArgument();
                confirmationMailArgument.setRecipientEmail(clientOrder.getClientEmail());
                confirmationMailArgument.setRecipientName(clientOrder.getClientName());
                confirmationMailArgument.setCompanyName(clientOrder.getCompanyOrderId().getCompaniesId().getCompanyName());
                mailManager.sendConfirmationAfterServiceSchedule(confirmationMailArgument);
                // Obavestenje da treba proveriti mail.
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Notification", "Check you e-mail for reservation confirmation.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            // Ako clientOrder nije upisan u DB, izbaci obavestenje o tome.
            } else{
                LOGGER.log(Level.WARNING, "Client order with companyOrderId = {0}, agentsId = {1}, servicesId = {2}, clientName = {3}, clientPhone = {4} "
                        + "and clientEmail = {5} is NOT written to DB!", new Object[] {clientOrder.getCompanyOrderId().getId(), 
                            clientOrder.getAgentsId().getId(), clientOrder.getServicesId().getId(), clientOrder.getClientName(), 
                            clientOrder.getClientPhone(), clientOrder.getClientEmail()});
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Notification", "Your reservation is not accepted. Try again later.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        reservation = new Reservations();
        clientOrder = new ClientOrders();
        return "";
    }

    public String getCheckedServices() {
        // Ovo je vrednost hidden input-a koje kad se set-uje treba uvek da bude blanko jer ce u suprotnom pamtiti
        // servise narucene iz ranijih request-ova.
        checkedServices = "";
        return checkedServices;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
        setCompanyOrder();
        setCompany();
        setServiceIdWithRequiredReservation();
    }

    public void setCheckedServices(String checkedServices) {
        this.checkedServices = checkedServices;
    }

    public Companies getCompany() {
        return company;
    }

    public void setCompany() {
        company = order.getCompaniesId();
    }

    public List<Agents> getAllAgents() {
        allAgents = agentsFacade.getAgentsByCompanyId(company);
        return allAgents;
    }

    public Agents getSelectedAgent() {
        return selectedAgent;
    }

    public void setSelectedAgent(Agents selectedAgent) {
        this.selectedAgent = selectedAgent;
    }

    public void setClientOrder(ClientOrders clientOrder) {
        this.clientOrder = clientOrder;
    }

    public ClientOrders getClientOrder() {
        return clientOrder;
    }

    public Reservations getReservation() {
        return reservation;
    }

    public void setReservation(Reservations reservation) {
        this.reservation = reservation;
    }

}
