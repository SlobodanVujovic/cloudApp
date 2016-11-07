package com.cloudApp.controller;

import com.cloudApp.entity.Agents;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.CompanysContacts;
import com.cloudApp.entity.CompanysLocation;
import com.cloudApp.entity.Owners;
import com.cloudApp.entity.OwnersContacts;
import com.cloudApp.entity.Services;
import com.cloudApp.sessions.AgentsFacade;
import com.cloudApp.sessions.CompanyOrderFacade;
import com.cloudApp.sessions.CompanysContactsFacade;
import com.cloudApp.sessions.CompanysLocationFacade;
import com.cloudApp.sessions.OwnersFacade;
import com.cloudApp.sessions.ServicesFacade;
import java.util.List;
import javax.enterprise.context.RequestScoped;
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

    public String validateUsernameAndPassword() {
        owner = ownersFacade.getOwnerByUsername(username);
        // System.out.println(owner);
        String resultPassword = owner.getPassword();
        if (resultPassword.equals(password)) {
            company = owner.getCompaniesId();
            // System.out.println(company);
            ownersContacts = owner.getOwnersContactsList();
            // System.out.println(ownersContacts);
            setCompanysLocation();
            setCompanysContact();
            setAgents();
            setCompanyOrder();
            setCompanyServices();
            return navigationController.goToLoginAdmin();
        } else {
            // TODO Izbaciti obavestenje da je lozinka pogresna.
            return navigationController.goToLogin();
        }
    }

    public void setCompanysLocation() {
        companysLocation = locationFacade.getLocationByCompanyId(company);
        // System.out.println(companysLocation);
    }

    public void setCompanysContact() {
        companysContact = contactsFacade.getContactByCompanyId(company);
        // System.out.println(companysContact);
    }

    public void setAgents() {
        agents = agentsFacade.getAgentsByCompanyId(company);
        // System.out.println(agents);
    }

    public void setCompanyOrder() {
        companyOrders = orderFacade.getOrdersByCompanyId(company);
        // System.out.println(companyOrders);
    }

    public void setCompanyServices() {
        for (CompanyOrder order : companyOrders) {
            services = servicesFacade.getServicesByCompanyOrderId(order);
            // System.out.println(services);
        }
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
