package com.cloudApp.controller;

import com.cloudApp.entity.Activity;
import com.cloudApp.entity.Agents;
import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyActivities;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.CompanyOrderHasDaysOfWeek;
import com.cloudApp.entity.CompanyOrderHasDaysOfWeekPK;
import com.cloudApp.entity.CompanysContacts;
import com.cloudApp.entity.CompanysLocation;
import com.cloudApp.entity.DaysOfWeek;
import com.cloudApp.entity.Owners;
import com.cloudApp.entity.OwnersContacts;
import com.cloudApp.entity.Reservations;
import com.cloudApp.entity.Services;
import com.cloudApp.pojo.CompanyOrderWorkingDaysPresenter;
import com.cloudApp.pojo.MailFromAdminTable;
import com.cloudApp.sessions.ActivityFacade;
import com.cloudApp.sessions.AgentsFacade;
import com.cloudApp.sessions.ClientOrdersFacade;
import com.cloudApp.sessions.CompaniesFacade;
import com.cloudApp.sessions.CompanyActivitiesFacade;
import com.cloudApp.sessions.CompanyOrderFacade;
import com.cloudApp.sessions.CompanyOrderHasDaysOfWeekFacade;
import com.cloudApp.sessions.CompanysContactsFacade;
import com.cloudApp.sessions.CompanysLocationFacade;
import com.cloudApp.sessions.DaysOfWeekFacade;
import com.cloudApp.sessions.OwnersContactsFacade;
import com.cloudApp.sessions.OwnersFacade;
import com.cloudApp.sessions.ReservationsFacade;
import com.cloudApp.sessions.ServicesFacade;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

@Named
@ViewScoped
// Ova klasa treba da se zove "AdminLoginPageController".
public class LogInController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(LogInController.class.getName());
    private Companies company;
    private List<Agents> agents;
    private Set<String> agentsNames;
    private Agents tempAgentForAdding;
    private CompanysContacts companysContact;
    private CompanysLocation companysLocation;
    private Owners owner;
    private List<OwnersContacts> ownersContacts;
    private List<CompanyOrder> companyOrders;
    private List<Services> services;
    private Set<String> servicesNames;
    private Services tempServiceForAdding;
    private Set<CompanyActivities> setOfCompanyActivities;
    private CompanyActivities tempCompanyActivitiesForAdding;
    private List<Activity> listOfActivities;
    private Activity choosenActivity;
    private List<ClientOrders> listOfClientOrders;
    private List<ClientOrderPresenter> filteredClientOrderPresenter;
    // Ukazuje na redni broj u listOfClientOrders koji se koristi adminLoginPage kako bi odatle poceo prikaz u clientOrder
    // tabeli.
    private int firstRowToShow;
    private int pageRows;
    private long todayAsLong = LocalDate.now().toEpochDay();
    private List<ClientOrderPresenter> listOfClientOrderPresenters;
    private MailFromAdminTable mailFromAdminTable;
    private CompanyOrder tempOrderId;
    private List<DaysOfWeek> daysOfWeek;
    // Ovoj listi cemo pristupati kroz JSF i eventualno edit-ovati.
    private List<CompanyOrderWorkingDaysPresenter> listOrderWorkingDays;
    // Ova lista predstavlja stanje u DB-u i nju koristimo kada treba da update-ujemo DB da bi proverili da li je update uopste i potreban.
    private List<CompanyOrderWorkingDaysPresenter> oldOrderWorkingDays;
    // Cuva objekat (red) koji je trenutno selektovan.
    private ClientOrderPresenter selectedClientOrderPresenter;
    // Cuva listu int-ova koji predstavljaju ID-jeve radnih dana za izabrani red (company order).
    private List<Integer> selectedOrderWorkingDays;

    public LogInController() {

    }

    @PostConstruct
    public void init() {
        tempAgentForAdding = new Agents();
        tempServiceForAdding = new Services();
        servicesNames = new HashSet<>();
        agentsNames = new HashSet<>();
        tempCompanyActivitiesForAdding = new CompanyActivities();
        mailFromAdminTable = new MailFromAdminTable();
        listOrderWorkingDays = new ArrayList<>();
        oldOrderWorkingDays = new ArrayList<>();
        setAllData();
    }

    @Inject
    private AuthenticationController authenticationController;
    @Inject
    private AgentsFacade agentsFacade;
    @Inject
    private CompanysContactsFacade contactsFacade;
    @Inject
    private CompanysLocationFacade locationFacade;
    @Inject
    private OwnersFacade ownersFacade;
    @Inject
    private CompanyOrderFacade orderFacade;
    @Inject
    private ServicesFacade servicesFacade;
    @Inject
    private OwnersContactsFacade ownersContactsFacade;
    @Inject
    private ClientOrdersFacade clientOrdersFacade;
    @Inject
    private CompaniesFacade companiesFacade;
    @Inject
    private ReservationsFacade reservationsFacade;
    @Inject
    private CompanyActivitiesFacade companyActivitiesFacade;
    @Inject
    private ActivityFacade activityFacade;
    @Inject
    private DaysOfWeekFacade daysOfWeekFacade;
    @Inject
    private CompanyOrderHasDaysOfWeekFacade companyOrderHasDaysOfWeekFacade;

//  Methods for all data.
    public void setAllData() {
        // Uzimamo owner-a, koji se ulog-ovao iz map-e flash scope-a.
        owner = authenticationController.getOwner();
        company = owner.getCompaniesId();
        setListOfCompanyActivities();
        setOwnersContacts();
        setCompanysLocation();
        setCompanysContact();
        setAgents();
        setCompanyOrder();
        setCompanyServices();
        listOfClientOrders = setListOfClientOrders();
        listOfClientOrderPresenters = setListOfClientOrderPresenters();
        setFirstRowToShow();
        setDaysOfWeek();
        setListOrderWorkingDays();
        LOGGER.log(Level.INFO, "Method setAllData() finished successfully.");
    }

    public void updateDatabase() {
        updateCompanyInfo();
        updateOwners();
        updateOwnersContacts();
        updateCompanysLocation();
        updateCompanysContact();
        updateAgents();
        updateCompanyServices();
        updateCompanyOrder();
        updateOrderWorkingDays();
    }

//  ClientOrders methods.
    public List<ClientOrders> setListOfClientOrders() {
        List<ClientOrders> tempList = new ArrayList<>();
        for (CompanyOrder tempCompanyOrder : companyOrders) {
            List<ClientOrders> tempClientOrders = clientOrdersFacade.getClientOrdersWithReservations(tempCompanyOrder);
            tempList.addAll(tempClientOrders);
        }
        return tempList;
    }

    public void refreshListOfClientOrderPresenters() {
        /* 
        Ako je adminLoginPage pokrenuta i korisnik naruci servis, pozivanjem ovog metoda ce se naruceni servis dodati u client
        orders tabelu. Medjutim ako se client orders tabela na neki nacin izmeni u medjuvremenu (filtrira se po nekoj 
        vrednosti, sortira po nekoj koloni) onda, kada korisnik naruci servis, ponovnim pozivanjem ovog metoda
        se taj servis nece dodati u tabelu. Razlog je sto se ne poziva getListOfClientOrders() metod nakon set-ovanja nove liste
        listOfClientOrders. Neophodno je dodati sledeci red da bi se getListOfClientOrders() pozivao i nakon izmene tabele. 
        Ista stvar je se javljala i nakon brisanja reda ali je resenje za to bilo izbacivanje:
        oncomplete="PF('clientOrdersWidgetVar').filter()"
        iz <p:commandButton value="Delete" .../> tag-a u client orders tabeli.
         */
        filteredClientOrderPresenter = null;
        listOfClientOrders = setListOfClientOrders();
        listOfClientOrderPresenters = setListOfClientOrderPresenters();
    }

    public List<ClientOrders> getListOfClientOrders() {
        return listOfClientOrders;
    }

    public void deleteClientOrderPresenterFromTable(ClientOrderPresenter clientOrderPresenter) {
        ClientOrders clientOrderForDelete = clientOrderPresenter.getClientOrder();
        // Prvo proverimo da li za clientOrderForDelete postoji rezervacija u reservations tabeli i ako postoji 1. nju brisemo
        // pa onda clientOrderForDelete.
        Reservations reservationForDelete = clientOrderPresenter.getReservation();
        if (reservationForDelete != null) {
            reservationsFacade.remove(reservationForDelete);
        }
        clientOrdersFacade.remove(clientOrderForDelete);
        listOfClientOrders.remove(clientOrderForDelete);
        listOfClientOrderPresenters.remove(clientOrderPresenter);
    }

//  Agents methods.
//  Kada se klikne na "Delete" dugme na adminLoginPage str. poziva se ovaj metod.
    public void agentOutOfService(Agents agent) {
        // Nadjemo agenta iz argumenta.
        Agents outOfServiceAgent = agentsFacade.find(agent.getId());
        // Setujemo polje "inService" na false.
        outOfServiceAgent.setInService(Boolean.FALSE);
        // Upisemo agenta u DB.
        agentsFacade.edit(outOfServiceAgent);
        // Zatim izbrisemo agenta iz liste agenata koji su prikazani na adminLoginPage.
        agents.remove(agent);
        // Kao i iz liste koja sadrzi imena agenata koji cine listu (padajuci meni) u client order tabeli.
        agentsNames.remove(agent.getFirstName() + " " + agent.getLastName());
    }

    public void addAgent() {
        tempAgentForAdding.setCompaniesId(company);
        agents.add(tempAgentForAdding);
        agentsFacade.create(tempAgentForAdding);
        addAgentName(tempAgentForAdding);
        tempAgentForAdding = new Agents();
    }

    public void updateAgents() {
        for (int i = 0; i < agents.size(); i++) {
            if (agents.get(i).getId() != null) {
                agentsFacade.edit(agents.get(i));
            } else {
                agentsFacade.create(agents.get(i));
            }
        }
    }

    public void setAgents() {
        agents = agentsFacade.getAgentsByCompanyId(company);
        for (Agents tempAgent : agents) {
            addAgentName(tempAgent);
        }
    }

    public void addAgentName(Agents agent) {
        String agentName = agent.getFirstName() + " " + agent.getLastName();
        agentsNames.add(agentName);
    }

    public List<Agents> getAgents() {
        return agents;
    }

    public Set<String> getAgentsNames() {
        return agentsNames;
    }

    public Agents getTempAgentForAdding() {
        return tempAgentForAdding;
    }

    public void setTempAgentForAdding(Agents tempAgentForAdding) {
        this.tempAgentForAdding = tempAgentForAdding;
    }

    public void clearTempAgent() {
        tempAgentForAdding = new Agents();
    }

//  Services methods.
    public void unavailableService(Services service) {
        int serviceId = service.getId();
        Services serviceForDelete = servicesFacade.find(serviceId);
        serviceForDelete.setServiceAvailable(Boolean.FALSE);
        servicesFacade.edit(serviceForDelete);
        services.remove(service);
        deleteServiceName(service);
    }

    public void deleteServiceName(Services service) {
        servicesNames.remove(service.getName());
    }

    public void addService() {
        services.add(tempServiceForAdding);
        servicesFacade.create(tempServiceForAdding);
        addServiceName(tempServiceForAdding);
        tempServiceForAdding = new Services();
    }

    public void updateCompanyServices() {
        for (int i = 0; i < services.size(); i++) {
            servicesFacade.edit(services.get(i));
        }
    }

    public void setCompanyServices() {
        for (CompanyOrder order : companyOrders) {
            services = servicesFacade.getServicesByCompanyOrderId(order);
        }
        for (Services tempService : services) {
            addServiceName(tempService);
        }
    }

    public void addServiceName(Services services) {
        servicesNames.add(services.getName());
    }

    public Services getTempServiceForAdding() {
        return tempServiceForAdding;
    }

    public void setTempServiceForAdding(Services tempServiceForAdding) {
        this.tempServiceForAdding = tempServiceForAdding;
    }

    public void clearTempService() {
        tempServiceForAdding = new Services();
    }

    public List<Services> getServices() {
        return services;
    }

    public void updateCompanyInfo() {
        companiesFacade.edit(company);
    }

    public void updateOwners() {
        ownersFacade.edit(owner);
    }

    public void updateOwnersContacts() {
        ownersContactsFacade.edit(ownersContacts.get(0));
    }

    public void updateCompanysLocation() {
        locationFacade.edit(companysLocation);
    }

    public void updateCompanysContact() {
        contactsFacade.edit(companysContact);
    }

    public void updateActivity() {
        tempCompanyActivitiesForAdding.setActivityId(choosenActivity);
        tempCompanyActivitiesForAdding.setCompaniesId(company);

        setOfCompanyActivities.add(tempCompanyActivitiesForAdding);
        tempCompanyActivitiesForAdding = new CompanyActivities();
        for (CompanyActivities tempCompanyActivity : setOfCompanyActivities) {
            if (tempCompanyActivity.getId() != null) {
                companyActivitiesFacade.edit(tempCompanyActivity);
            } else {
                companyActivitiesFacade.create(tempCompanyActivity);
            }
        }
    }

    public void deleteCompanyActivity(CompanyActivities companyActivity) {
        if (setOfCompanyActivities.size() > 1) {
            setOfCompanyActivities.remove(companyActivity);
            companyActivitiesFacade.remove(companyActivity);
        }
    }

    public void updateCompanyOrder() {
        for (int i = 0; i < companyOrders.size(); i++) {
            orderFacade.edit(companyOrders.get(i));
        }
    }

    public void setOwnersContacts() {
        ownersContacts = ownersContactsFacade.getOwnersContactsByOwnerId(owner);
    }

    public void setCompanysLocation() {
        companysLocation = locationFacade.getLocationByCompanyId(company);
    }

    public void setCompanysContact() {
        companysContact = contactsFacade.getContactByCompanyId(company);
    }

    public void setCompanyOrder() {
        companyOrders = orderFacade.getOrdersByCompanyId(company);
    }

    public void setListOfCompanyActivities() {
        setOfCompanyActivities = companyActivitiesFacade.getCompanyActivitiesByCompanyId(company);
    }

    public Companies getCompany() {
        return company;
    }

    public CompanysLocation getCompanysLocation() {
        return companysLocation;
    }

    public CompanysContacts getCompanysContact() {
        return companysContact;
    }

    public Owners getOwner() {
        return owner;
    }

    public List<OwnersContacts> getOwnersContacts() {
        return ownersContacts;
    }

    public List<CompanyOrder> getCompanyOrders() {
        return companyOrders;
    }

    public Set<String> getServicesNames() {
        return servicesNames;
    }

    public Set<CompanyActivities> getSetOfCompanyActivities() {
        return setOfCompanyActivities;
    }

    public List<Activity> getListOfActivities() {
        listOfActivities = activityFacade.findAll();
        return listOfActivities;
    }

    public Activity getChoosenActivity() {
        return choosenActivity;
    }

    public void setChoosenActivity(Activity choosenActivity) {
        this.choosenActivity = choosenActivity;
    }

    public List<ClientOrderPresenter> getFilteredClientOrderPresenter() {
        return filteredClientOrderPresenter;
    }

    public void setFilteredClientOrderPresenter(List<ClientOrderPresenter> filteredClientOrderPresenter) {
        this.filteredClientOrderPresenter = filteredClientOrderPresenter;
    }

    public ReservationsFacade getReservationsFacade() {
        return reservationsFacade;
    }

    public void onRowEdit(RowEditEvent event) {
        ClientOrderPresenter tempClientOrderPresenter = (ClientOrderPresenter) event.getObject();
        ClientOrders tempClientOrders = tempClientOrderPresenter.getClientOrder();
        Reservations temReservations = tempClientOrderPresenter.getReservation();
        clientOrdersFacade.edit(tempClientOrders);
        reservationsFacade.edit(temReservations);
        LOGGER.log(Level.INFO, "Method onRowEdit() finished successfully.");
    }

    // Ovaj metod ne radi nista, na taj nacin se nece sacuvati vrednosti koje su set-ovane, nego ce ostati vrednosti koje
    // su bile pre edit-ovanja reda.
    public void onRowCancel(RowEditEvent event) {

    }

    public int getFirstRowToShow() {
        return firstRowToShow;
    }

    public void setFirstRowToShow() {
        LocalDate today = LocalDate.now();
        for (int i = 0; i < listOfClientOrderPresenters.size(); i++) {
            ClientOrderPresenter tempClientOrderPresenter = listOfClientOrderPresenters.get(i);
            if (tempClientOrderPresenter.getReservation() != null) {
                LocalDate tempReservationDate = tempClientOrderPresenter.getReservation().getReservationDate();
                if (today.isEqual(tempReservationDate)) {
                    firstRowToShow = i;
                    setPageRows();
                    break;
                }
            }
        }
    }

    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows() {
        if (firstRowToShow <= 10 && firstRowToShow != 0) {
            pageRows = firstRowToShow;
        } else if (firstRowToShow > 10 && firstRowToShow < 20) {
            pageRows = 10;
        } else if (firstRowToShow >= 20) {
            pageRows = 20;
        }
    }

    public long getTodayAsLong() {
        return todayAsLong;
    }

//  Parametar "value" je ono sto je ispisano u tabeli (odnosno ono sto je iscitano iz baze). Parametar "filter" je ona vrednost
//  na osnovu koje filtriramo podatke (odnosno vrednost iz p:calendar tag-a u header-u).
    public boolean filterByDate(Object value, Object filter, Locale locale) {
        // Ako filter nije podesen onda se ispisuju sve vrednosti.
        if (filter == null) {
            return true;
        }
        // Ako poredimo sa null vrednoscu onda kazemo da nema poklapanja (odbacujemo taj red).
        if (value == null) {
            return false;
        }
        // Prvo cast-ujemo "value" u svoju klasu.
        LocalDate valueLocaleDate = (LocalDate) value;
        // Zatim cast-ujemo "filter" u svoju klasu.
        Date filterDate = (Date) filter;
        // Zatim prebacujemo "filter" u LocalDate klasu. Koristimo klasu java.sql.Date
        // koja extend-uje java.util.Date.
        LocalDate filterLocalDate = new java.sql.Date(filterDate.getTime()).toLocalDate();
        // I na kraju poredimo datume.
        return filterLocalDate.isEqual(valueLocaleDate);
    }

    public List<ClientOrderPresenter> getListOfClientOrderPresenters() {
        return listOfClientOrderPresenters;
    }

    public List<ClientOrderPresenter> setListOfClientOrderPresenters() {
        ClientOrderPresenter tempClientOrderPresenter;
        List<ClientOrderPresenter> tempList = new ArrayList<>();
        for (ClientOrders tempClientOrder : listOfClientOrders) {
            tempClientOrderPresenter = new ClientOrderPresenter();
            tempClientOrderPresenter.setClientOrder(tempClientOrder);
            Reservations tempReservation = reservationsFacade.getReservationByClientOrdersId(tempClientOrder);
            tempClientOrderPresenter.setReservation(tempReservation);
            tempList.add(tempClientOrderPresenter);
        }
        return tempList;
    }

    public MailFromAdminTable getMailFromAdminTable() {
        return mailFromAdminTable;
    }

    public void sendMailFromAdminTable() {
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
            message.setFrom(new InternetAddress(mailFromAdminTable.getEmailFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailFromAdminTable.getEmailTo()));
            message.setSubject(mailFromAdminTable.getEmailSubject(), "UTF-8");
            message.setSentDate(new Date());
            BodyPart messageBodyPart = new MimeBodyPart();
            String mailBody = mailFromAdminTable.getEmailBody();
            messageBodyPart.setText(mailBody);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Your e-mail is sent."));
            LOGGER.log(Level.INFO, "sendMailFromAdminTable() for user {0}", mailFromAdminTable.getEmailTo());
        } catch (MessagingException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Failure", "Your e-mail is not sent."));
            e.printStackTrace();
        }
        mailFromAdminTable = new MailFromAdminTable();
    }

    public void setTempOrderId(CompanyOrder tempOrderId) {
        this.tempOrderId = tempOrderId;
    }

    public CompanyOrder getTempOrderId() {
        return tempOrderId;
    }

    public String goToServicePage() {
        // TODO Promeniti argument substring() metoda kada se promeni URL sa "localhost".
        String orderId = tempOrderId.getUrl().substring(51);
        // Obrati paznju na navigaciju na stranicu u 2. folderu. Koristi se apsolutna putanja i "faces-redirect" atribut.
        String url = "/cloud/services.xhtml?" + orderId + "&faces-redirect=true";
        return url;
    }

    public List<DaysOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek() {
        daysOfWeek = daysOfWeekFacade.findAll();
    }

    public List<CompanyOrderWorkingDaysPresenter> getListOrderWorkingDays() {
        return listOrderWorkingDays;
    }

    public void setListOrderWorkingDays() {
        List<CompanyOrderHasDaysOfWeek> tempListWorkingDaysOfOrder;
        CompanyOrderWorkingDaysPresenter tempOrderWorkingDaysPresenter;
        List<DaysOfWeek> tempWorkingDays;
        // Posto svi radni dani imaju isto radno vreme i posto svi vikend dani imaju isto radno vreme, da ne bi 5, odnosno 2, puta
        // set-ovali vrednosti za pocetak i kraj radnog vremena radnih, odnosno vikend, dana, koristimo naredna 2 flag-a koji se set-uju
        // na true kada odgovarajuce promenljive dobiju vrednost. Na taj naci sprecavamo kasniji ponovni ulazak u if statement.
        boolean wordTimeSet = false;
        boolean weekendTimeSet = false;
        // Moramo inicijalizovati vremena da se ne bi desilo da neke promenljive ostanu bez vrednosti.
        LocalTime tempWorkStart = LocalTime.of(0, 0),
                tempWorkClose = LocalTime.of(0, 0),
                tempWeekednStart = LocalTime.of(0, 0),
                tempWeekendClose = LocalTime.of(0, 0);
        // Za svaki order koji kompanija ima
        for (CompanyOrder tempCompanyOrder : companyOrders) {
            tempOrderWorkingDaysPresenter = new CompanyOrderWorkingDaysPresenter();
            tempWorkingDays = new ArrayList<>();
            // uzimamo iz "company_order_has_days_of_week" DB tabele record-e koji imaju navedeni order.
            tempListWorkingDaysOfOrder = companyOrderHasDaysOfWeekFacade.getCompanyOrderWorkingDaysByCompanyOrderId(tempCompanyOrder.getId());
            // Prolazimo kroz listu dobijenih record-a i u posebnu listu, "tempWorkingDays" izdvajamo dane koji su vezani za navedeni order.
            for (CompanyOrderHasDaysOfWeek tempDay : tempListWorkingDaysOfOrder) {
                tempWorkingDays.add(tempDay.getDaysOfWeek());
                // Ovde koristimo flag-ove. Kada se 1 set-uju na true, vise se nece ulaziti u if statement.
                if (!wordTimeSet && tempDay.getDaysOfWeek().getId() >= 1 && tempDay.getDaysOfWeek().getId() <= 5) {
                    tempWorkStart = tempDay.getStartTime();
                    tempWorkClose = tempDay.getCloseTime();
                    wordTimeSet = true;
                } else if (!weekendTimeSet && tempDay.getDaysOfWeek().getId() >= 6 && tempDay.getDaysOfWeek().getId() <= 7) {
                    tempWeekednStart = tempDay.getStartTime();
                    tempWeekendClose = tempDay.getCloseTime();
                    weekendTimeSet = true;
                }
            }
            // Sada kada imamo order, listu dana i vremena, setujemo ih u presenter objektu.
            tempOrderWorkingDaysPresenter.setCompanyOrder(tempCompanyOrder);
            tempOrderWorkingDaysPresenter.setWorkingDays(tempWorkingDays);
            tempOrderWorkingDaysPresenter.setWorkingDayStartTime(tempWorkStart);
            tempOrderWorkingDaysPresenter.setWorkingDayStopTime(tempWorkClose);
            tempOrderWorkingDaysPresenter.setWeekendDayStartTime(tempWeekednStart);
            tempOrderWorkingDaysPresenter.setWeekendDayStopTime(tempWeekendClose);
            listOrderWorkingDays.add(tempOrderWorkingDaysPresenter);
            // Posto, pre svakog update-a baze, proveravamo da li se stanje baze izmenilo, popunjavamo ovu listu i imacemo to stanje za kasnije.
            // Da nema ove liste morali bi prvo citati bazu, da bi uzeli trenutno stanje, sto nepotrebno povecava broj upita ka bazi.
            oldOrderWorkingDays.add(tempOrderWorkingDaysPresenter);
        }
    }

    public void updateOrderWorkingDays() {
        List<CompanyOrderHasDaysOfWeek> currentListOfOrdersWorkingDays = getCurrentOrderWorkingDays();
        if (areWorkingDaysChanged(currentListOfOrdersWorkingDays)) {
            // 1. izbrisemo unose za sve CompanyOrder-e.
            for (CompanyOrder tempCompanyOrder : companyOrders) {
                List<CompanyOrderHasDaysOfWeek> oldListOfOrdersWorkingDays = companyOrderHasDaysOfWeekFacade.getCompanyOrderWorkingDaysByCompanyOrderId(tempCompanyOrder.getId());
                for (CompanyOrderHasDaysOfWeek tempCompanyOrderWorkingDays : oldListOfOrdersWorkingDays) {
                    companyOrderHasDaysOfWeekFacade.remove(tempCompanyOrderWorkingDays);
                }
            }
            // Zatim unesemo (kreiramo) nove vrednosti (redove).
            for (CompanyOrderHasDaysOfWeek tempCurrentOrderWorkingDays : currentListOfOrdersWorkingDays) {
                companyOrderHasDaysOfWeekFacade.create(tempCurrentOrderWorkingDays);
            }
        }
        // Po ucitavanju adminLoginPage.xhtml-a se setuju vrednosti u timepicker-ima na osnovu hidden input-a u kojima se nalaze iscitane
        // vrednosti iz baze. Medjutim kada se pritisne "Update data" dugme koristi se AJAX, sto znaci da se strana ne ucitava ponovo i 
        // zato je potrebno ovde pozvati JS funkciju koja ce dodeliti vrednosti timepicker-ima (u suprotnom ce u njima biti blanko).
        RequestContext.getCurrentInstance().execute("checkIfThisIsAdminLoginPage();");
    }

    // Vreca listu CompanyOrderHasDaysOfWeek objekata koji sadrze sve promene koje je korisnik napravio (stanje koje je korisnik ostavio).
    public List<CompanyOrderHasDaysOfWeek> getCurrentOrderWorkingDays() {
        List<CompanyOrderHasDaysOfWeek> tempListOfOrdersWorkingDays = new ArrayList<>();
        CompanyOrderHasDaysOfWeek tempOrderHasDaysOfWeek;
        for (CompanyOrderWorkingDaysPresenter tempOrderWoringDays : listOrderWorkingDays) {
            List<DaysOfWeek> tempWorkingDays = tempOrderWoringDays.getWorkingDays();
            for (int i = 0; i < tempWorkingDays.size(); i++) {
                DaysOfWeek tempDay = tempWorkingDays.get(i);
                tempOrderHasDaysOfWeek = new CompanyOrderHasDaysOfWeek();
                CompanyOrderHasDaysOfWeekPK tempPK = new CompanyOrderHasDaysOfWeekPK(tempOrderWoringDays.getCompanyOrder().getId(), tempDay.getId());
                // Obati paznju da je potrebno setovati i PK (koji se sastoji od CompanyOrder ID-a i DaysOfWeek ID-a) kao i sama polja
                // CompanyOrder i DaysOfWeek, iako su to ista ona polja od kojih je sacinjen PK.
                tempOrderHasDaysOfWeek.setCompanyOrderHasDaysOfWeekPK(tempPK);
                tempOrderHasDaysOfWeek.setCompanyOrder(tempOrderWoringDays.getCompanyOrder());
                tempOrderHasDaysOfWeek.setDaysOfWeek(tempDay);
                tempOrderHasDaysOfWeek.setSortIdx(i);
                if (tempDay.getId() >= 1 && tempDay.getId() <= 5) {
                    tempOrderHasDaysOfWeek.setStartTime(tempOrderWoringDays.getWorkingDayStartTime());
                    tempOrderHasDaysOfWeek.setCloseTime(tempOrderWoringDays.getWorkingDayStopTime());
                } else {
                    tempOrderHasDaysOfWeek.setStartTime(tempOrderWoringDays.getWeekendDayStartTime());
                    tempOrderHasDaysOfWeek.setCloseTime(tempOrderWoringDays.getWeekendDayStopTime());
                }
                tempListOfOrdersWorkingDays.add(tempOrderHasDaysOfWeek);
            }
        }
        return tempListOfOrdersWorkingDays;
    }

    // Proverimo da li je zaista potrebno vrsiti update baze. Ovo radimo jer se update sastoji od brisanja starih unosa i upisa novih.
    // To ne zelimo da radimo osim ako nije neophodno (napravljene promene).
    public boolean areWorkingDaysChanged(List<CompanyOrderHasDaysOfWeek> currentOrderWorkingDays) {
        if (currentOrderWorkingDays.size() == oldOrderWorkingDays.size() && currentOrderWorkingDays.containsAll(oldOrderWorkingDays) && oldOrderWorkingDays.containsAll(currentOrderWorkingDays)) {
            return false;
        } else {
            return true;
        }
    }

    public ClientOrderPresenter getSelectedClientOrderPresenter() {
        return selectedClientOrderPresenter;
    }

    // Kada se set-uje selectedClientOrderPresenter znaci da je izabran novi red
    public void setSelectedClientOrderPresenter(ClientOrderPresenter selectedClientOrderPresenter) {
        this.selectedClientOrderPresenter = selectedClientOrderPresenter;
        // i tada pozivamo metod koji set-uje novu vrednost u input polje za radne dane selektovanog reda (company order-a).
        setSelectedOrderWorkingDays();
    }

    public List<Integer> getSelectedOrderWorkingDays() {
        return selectedOrderWorkingDays;
    }

    public void setSelectedOrderWorkingDays() {
        // Svaki put pravimo novu list.
        selectedOrderWorkingDays = new ArrayList<>();
        // 1. odredimo ID company order-a za selektovani red (objekat).
        int tempCompanyOrderId = selectedClientOrderPresenter.getClientOrder().getCompanyOrderId().getId();
        // Onda procitamo iz baze CompanyOrderHasDaysOfWeek objekte koji imaju navedeni company order ID.
        List<CompanyOrderHasDaysOfWeek> tempOrderWorkingDays = companyOrderHasDaysOfWeekFacade.getCompanyOrderWorkingDaysByCompanyOrderId(tempCompanyOrderId);
        // Prodjemo kroz dobijenu listu CompanyOrderHasDaysOfWeek i izdvojimo ID-jeve dana (radni dani za navedeni company order).
        for (CompanyOrderHasDaysOfWeek tempDay : tempOrderWorkingDays) {
            selectedOrderWorkingDays.add(tempDay.getDaysOfWeek().getId());
        }
        // Na kraju update-ujemo input polje u JSF-u sa novim nizom int-ova.
        RequestContext.getCurrentInstance().update("clientOrdersForm:workingDaysInputForAdminTable");
    }

    // Nista ne redimo sa naredna 2 metoda. Morali smo ih implementirati da bi zadovoljili formu. Cilj smo postigli u prethodnom
    // metodu.
    public void onRowSelect(SelectEvent event) {
        
    }
 
    public void onRowUnselect(UnselectEvent event) {
        
    }
}
