package com.cloudApp.controller;

import com.cloudApp.entity.Activity;
import com.cloudApp.entity.Agents;
import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyActivities;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.CompanysContacts;
import com.cloudApp.entity.CompanysLocation;
import com.cloudApp.entity.Owners;
import com.cloudApp.entity.OwnersContacts;
import com.cloudApp.entity.Reservations;
import com.cloudApp.entity.Services;
import com.cloudApp.sessions.ActivityFacade;
import com.cloudApp.sessions.AgentsFacade;
import com.cloudApp.sessions.ClientOrdersFacade;
import com.cloudApp.sessions.CompaniesFacade;
import com.cloudApp.sessions.CompanyActivitiesFacade;
import com.cloudApp.sessions.CompanyOrderFacade;
import com.cloudApp.sessions.CompanysContactsFacade;
import com.cloudApp.sessions.CompanysLocationFacade;
import com.cloudApp.sessions.OwnersContactsFacade;
import com.cloudApp.sessions.OwnersFacade;
import com.cloudApp.sessions.ReservationsFacade;
import com.cloudApp.sessions.ServicesFacade;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.*;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

@Named
@ViewScoped
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
    private List<ClientOrders> filteredClientOrders;
    // Ukazuje na redni broj u listOfClientOrders koji se koristi adminLoginPage kako bi odatle poceo prikaz u clientOrder
    // tabeli.
    private int firstRowToShow;
    private int pageRows;
    private long todayAsLong = LocalDate.now().toEpochDay();

    public LogInController() {

    }

    @PostConstruct
    public void init() {
        tempAgentForAdding = new Agents();
        tempServiceForAdding = new Services();
        servicesNames = new HashSet<>();
        agentsNames = new HashSet<>();
        tempCompanyActivitiesForAdding = new CompanyActivities();
        setAllData();
    }

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

//  Methods for all data.
    public void setAllData() {
        // Uzimamo owner-a, koji se ulog-ovao iz map-e flash scope-a.
        owner = (Owners) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("owner");
        company = owner.getCompaniesId();
        setListOfCompanyActivities();
        setOwnersContacts();
        setCompanysLocation();
        setCompanysContact();
        setAgents();
        setCompanyOrder();
        setCompanyServices();
        listOfClientOrders = setListOfClientOrders();
        setFirstRowToShow();
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
    
    public void refreshListOfClientOrders(){
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
        filteredClientOrders = null;
        listOfClientOrders = setListOfClientOrders();
    }

    public List<ClientOrders> getListOfClientOrders() {
        return listOfClientOrders;
    }

    public void deleteClientOrderFromTable(ClientOrders clientOrder) {
        int clientOrderId = clientOrder.getId();
        ClientOrders clientOrderForDelete = clientOrdersFacade.find(clientOrderId);
        // Prvo proverimo da li za clientOrderForDelete postoji rezervacija u reservations tabeli i ako postoji 1. nju brisemo
        // pa onda clientOrderForDelete.
        Reservations reservationForDelete = reservationsFacade.getReservationByClientOrdersId(clientOrderForDelete);
        if (reservationForDelete != null) {
            reservationsFacade.remove(reservationForDelete);
        }
        clientOrdersFacade.remove(clientOrderForDelete);
        listOfClientOrders.remove(clientOrder);
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

    public List<ClientOrders> getFilteredClientOrders() {
        return filteredClientOrders;
    }

    public void setFilteredClientOrders(List<ClientOrders> filteredClientOrders) {
        this.filteredClientOrders = filteredClientOrders;
    }

    public ReservationsFacade getReservationsFacade() {
        return reservationsFacade;
    }

    public void onRowEdit(RowEditEvent event) {
        ClientOrders tempClientOrders = (ClientOrders) event.getObject();
        clientOrdersFacade.edit(tempClientOrders);
        LOGGER.log(Level.INFO, "Method onCellEdit() finished successfully.");
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
        for (int i = 0; i < listOfClientOrders.size(); i++) {
            ClientOrders tempClientOrder = listOfClientOrders.get(i);
            if (!tempClientOrder.getReservationsList().isEmpty()) {
                LocalDate tempReservationDate = tempClientOrder.getReservationsList().get(0).getReservationDate();
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

}
