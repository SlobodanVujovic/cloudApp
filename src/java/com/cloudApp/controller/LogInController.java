package com.cloudApp.controller;

import com.cloudApp.entity.Agents;
import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.CompanysContacts;
import com.cloudApp.entity.CompanysLocation;
import com.cloudApp.entity.Owners;
import com.cloudApp.entity.OwnersContacts;
import com.cloudApp.entity.Services;
import com.cloudApp.sessions.AgentsFacade;
import com.cloudApp.sessions.ClientOrdersFacade;
import com.cloudApp.sessions.CompaniesFacade;
import com.cloudApp.sessions.CompanyOrderFacade;
import com.cloudApp.sessions.CompanysContactsFacade;
import com.cloudApp.sessions.CompanysLocationFacade;
import com.cloudApp.sessions.OwnersContactsFacade;
import com.cloudApp.sessions.OwnersFacade;
import com.cloudApp.sessions.ServicesFacade;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class LogInController implements Serializable {
    
    private String username;
    private String password;
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
    private ClientOrderPresenter clientOrderPresenter;
    private List<ClientOrderPresenter> clientOrderPresenterList;
    private List<ClientOrderPresenter> filteredClientOrderPresenter;
    private UIComponent usernameComponent;
    private UIComponent passwordComponent;
    
    public LogInController() {
        
    }
    
    @PostConstruct
    public void init() {
        tempAgentForAdding = new Agents();
        tempServiceForAdding = new Services();
        servicesNames = new HashSet<>();
        agentsNames = new HashSet<>();
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
    private NavigationController navigationController;
    @Inject
    private OwnersContactsFacade ownersContactsFacade;
    @Inject
    private ClientOrdersFacade clientOrdersFacade;
    @Inject
    private CompaniesFacade companiesFacade;
    
    public String validateUsernameAndPassword() {
        owner = ownersFacade.getOwnerByUsername(username);
        if (owner != null) {
            String resultPassword = owner.getPassword();
            if (resultPassword.equals(password)) {
                company = owner.getCompaniesId();
                setOwnersContacts();
                setCompanysLocation();
                setCompanysContact();
                setAgents();
                setCompanyOrder();
                setCompanyServices();
                setClientOrderPresenters();
                return navigationController.goToLoginAdmin();
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(passwordComponent.getClientId(), new FacesMessage("Incorrect password."));
                return navigationController.goToLogin();
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(usernameComponent.getClientId(), new FacesMessage("Incorrect username."));
            return navigationController.goToLogin();
        }
    }
    
    public void setClientOrderPresenters() {
        clientOrderPresenterList = new ArrayList<>();
        List<ClientOrders> tempClientOrders;
        for (CompanyOrder tempCompanyOrder : companyOrders) {
            tempClientOrders = clientOrdersFacade.getClientOrdersByCompanyOrderId(tempCompanyOrder);
            for (ClientOrders tempClientOrder : tempClientOrders) {
                clientOrderPresenter = new ClientOrderPresenter();
                clientOrderPresenter.setId(tempClientOrder.getId());
                clientOrderPresenter.setClientName(tempClientOrder.getClientName());
                clientOrderPresenter.setOrderedService(tempClientOrder.getServicesId().getName());
                clientOrderPresenter.setClientPhone(tempClientOrder.getClientPhone());
                clientOrderPresenter.setClientEmail(tempClientOrder.getClientEmail());
                
                Agents tempAgent = agentsFacade.find(tempClientOrder.getAgentsId().getId());
                clientOrderPresenter.setAgent(tempAgent.getFirstName() + " " + tempAgent.getLastName());
                
                if (tempClientOrder.getReservationDate() != null) {
                    // Podesavanje formata datuma da bi bilo citljivije.
                    DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
                    String reservationDate = mediumDf.format(tempClientOrder.getReservationDate());
                    String reservationTime = tempClientOrder.getReservationTime();
                    clientOrderPresenter.setReservationDate(reservationDate);
                    clientOrderPresenter.setReservationTime(reservationTime);
                }
                clientOrderPresenterList.add(clientOrderPresenter);
            }
        }
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
    
    public void deleteClientOrderFromTable(ClientOrderPresenter clientOrderPresenter) {
        int clientOrderId = clientOrderPresenter.getId();
        ClientOrders clientOrderForDelete = clientOrdersFacade.find(clientOrderId);
        clientOrdersFacade.remove(clientOrderForDelete);
        clientOrderPresenterList.remove(clientOrderPresenter);
    }
    
    public void deleteAgent(Agents agent) {
        int agentId = agent.getId();
        Agents agentForDelete = agentsFacade.find(agentId);
        agentsFacade.remove(agentForDelete);
        agents.remove(agent);
    }
    
    public void deleteService(Services service) {
        int serviceId = service.getId();
        Services serviceForDelete = servicesFacade.find(serviceId);
        servicesFacade.remove(serviceForDelete);
        services.remove(service);
    }
    
    public void addAgent() {
        tempAgentForAdding.setCompaniesId(company);
        agents.add(tempAgentForAdding);
        agentsFacade.create(tempAgentForAdding);
        tempAgentForAdding = new Agents();
    }
    
    public void addService() {
        services.add(tempServiceForAdding);
        servicesFacade.create(tempServiceForAdding);
        tempServiceForAdding = new Services();
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
    
    public void updateAgents() {
        for (int i = 0; i < agents.size(); i++) {
            if (agents.get(i).getId() != null) {
                agentsFacade.edit(agents.get(i));
            } else {
                agentsFacade.create(agents.get(i));
            }
        }
    }
    
    public void updateCompanyServices() {
        for (int i = 0; i < services.size(); i++) {
            servicesFacade.edit(services.get(i));
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
    
    public void setAgents() {
        agents = agentsFacade.getAgentsByCompanyId(company);
        for (Agents tempAgent : agents) {
            String agentName = tempAgent.getFirstName() + " " + tempAgent.getLastName();
            agentsNames.add(agentName);
        }
    }
    
    public Agents getTempAgentForAdding() {
        return tempAgentForAdding;
    }
    
    public void setTempAgentForAdding(Agents tempAgentForAdding) {
        this.tempAgentForAdding = tempAgentForAdding;
    }
    
    public Services getTempServiceForAdding() {
        return tempServiceForAdding;
    }
    
    public void setTempServiceForAdding(Services tempServiceForAdding) {
        this.tempServiceForAdding = tempServiceForAdding;
    }
    
    public void setCompanyOrder() {
        companyOrders = orderFacade.getOrdersByCompanyId(company);
    }
    
    public void setCompanyServices() {
        for (CompanyOrder order : companyOrders) {
            services = servicesFacade.getServicesByCompanyOrderId(order);
        }
        for (Services tempService : services) {
            servicesNames.add(tempService.getName() + " ");
        }
    }
    
    public UIComponent getUsernameComponent() {
        return usernameComponent;
    }
    
    public void setUsernameComponent(UIComponent usernameComponent) {
        this.usernameComponent = usernameComponent;
    }
    
    public UIComponent getPasswordComponent() {
        return passwordComponent;
    }
    
    public void setPasswordComponent(UIComponent passwordComponent) {
        this.passwordComponent = passwordComponent;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<ClientOrderPresenter> getClientOrderPresenterList() {
        return clientOrderPresenterList;
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
    
    public List<Agents> getAgents() {
        return agents;
    }
    
    public Set<String> getAgentsNames() {
        return agentsNames;
    }
    
    public List<CompanyOrder> getCompanyOrders() {
        return companyOrders;
    }
    
    public List<Services> getServices() {
        return services;
    }
    
    public Set<String> getServicesNames() {
        return servicesNames;
    }
    
    public List<ClientOrderPresenter> getFilteredClientOrderPresenter() {
        return filteredClientOrderPresenter;
    }
    
    public void setFilteredClientOrderPresenter(List<ClientOrderPresenter> filteredClientOrderPresenter) {
        this.filteredClientOrderPresenter = filteredClientOrderPresenter;
    }
    
}
