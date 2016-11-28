package com.cloudApp.controller;

import com.cloudApp.entity.Agents;
import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.ClientOrdersAgents;
import com.cloudApp.entity.ClientOrdersReservations;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.CompanysContacts;
import com.cloudApp.entity.CompanysLocation;
import com.cloudApp.entity.Owners;
import com.cloudApp.entity.OwnersContacts;
import com.cloudApp.entity.Services;
import com.cloudApp.sessions.AgentsFacade;
import com.cloudApp.sessions.ClientOrdersAgentsFacade;
import com.cloudApp.sessions.ClientOrdersFacade;
import com.cloudApp.sessions.ClientOrdersReservationsFacade;
import com.cloudApp.sessions.CompanyOrderFacade;
import com.cloudApp.sessions.CompanysContactsFacade;
import com.cloudApp.sessions.CompanysLocationFacade;
import com.cloudApp.sessions.OwnersContactsFacade;
import com.cloudApp.sessions.OwnersFacade;
import com.cloudApp.sessions.ServicesFacade;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LogInController {

    private String username;
    private String password;
    private Companies company;
    private List<Agents> agents;
    private CompanysContacts companysContact;
    private CompanysLocation companysLocation;
    private Owners owner;
    private List<OwnersContacts> ownersContacts;
    private List<CompanyOrder> companyOrders;
    private List<Services> services;
    private ClientOrdersAgents clientOrdersAgent;
    private ClientOrdersReservations clientOrdersReservation;
    private ClientOrderPresenter clientOrderPresenter;
    private List<ClientOrderPresenter> clientOrderPresenterList;
    private UIComponent usernameComponent;
    private UIComponent passwordComponent;

    public LogInController() {

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
    private ClientOrdersAgentsFacade clientOrdersAgentsFacade;
    @Inject
    private ClientOrdersReservationsFacade clientOrdersReservationsFacade;

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
                clientOrderPresenter.setClientName(tempClientOrder.getClientName());
                clientOrderPresenter.setOrderedService(tempClientOrder.getOrderedService());

                clientOrdersAgent = clientOrdersAgentsFacade.getClientOrdersAgentsByClientOrdersId(tempClientOrder);
                clientOrderPresenter.setAgent(clientOrdersAgent.getAgentsId().getFirstName() + " " + clientOrdersAgent.getAgentsId().getLastName());

                clientOrdersReservation = clientOrdersReservationsFacade.getClientOrdersAgentsByClientOrdersId(tempClientOrder);
                if (clientOrdersReservation != null) {
                    // Podesavanje formata datuma da bi bilo citljivije.
                    DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
                    String reservationDate = mediumDf.format(clientOrdersReservation.getReservationDate());
                    clientOrderPresenter.setReservationDate(reservationDate);
                    clientOrderPresenter.setReservationTime(clientOrdersReservation.getReservationTime());
                }
                clientOrderPresenterList.add(clientOrderPresenter);
            }
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
    }

    public void setCompanyOrder() {
        companyOrders = orderFacade.getOrdersByCompanyId(company);
    }

    public void setCompanyServices() {
        for (CompanyOrder order : companyOrders) {
            services = servicesFacade.getServicesByCompanyOrderId(order);
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

    public List<CompanyOrder> getCompanyOrders() {
        return companyOrders;
    }

    public List<Services> getServices() {
        return services;
    }

}
