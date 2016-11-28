package com.cloudApp.controller;

import com.cloudApp.entity.Agents;
import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.ClientOrdersAgents;
import com.cloudApp.entity.ClientOrdersReservations;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.Services;
import com.cloudApp.sessions.AgentsFacade;
import com.cloudApp.sessions.ClientOrdersAgentsFacade;
import com.cloudApp.sessions.ClientOrdersFacade;
import com.cloudApp.sessions.ClientOrdersReservationsFacade;
import com.cloudApp.sessions.CompanyOrderFacade;
import com.cloudApp.sessions.ServicesFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;

// TODO Ne moze kroz isti browser da se zakazuje vise razlicitih servisa, zato sto je ServicesController session scoped.
@Named
@SessionScoped
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
    private ClientOrders clientOrder;
    private ClientOrdersAgents clientOrdersAgent;
    private ClientOrdersReservations clientOrdersReservation;

    public ServicesController() {

    }

    @PostConstruct
    public void init() {
        clientOrder = new ClientOrders();
        clientOrdersReservation = new ClientOrdersReservations();
        clientOrdersAgent = new ClientOrdersAgents();
    }

    @Inject
    private CompanyOrderFacade orderFacade;
    @Inject
    private ClientOrdersFacade clientOrderFacade;
    @Inject
    private ClientOrdersAgentsFacade clientOrdersAgentFacade;
    @Inject
    private ClientOrdersReservationsFacade clientReservationFacade;
    @Inject
    private ServicesFacade servicesFacade;
    @Inject
    private AgentsFacade agentsFacade;

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
                String serviceName = service.getName();
                if (serviceName.contains(" ")) {
                    serviceName = serviceName.replace(" ", "");
                }
                serviceIdWithRequiredReservation.add(serviceName);
            }
        }
    }

    public String takeClientsOrder() {
        clientOrder.setOrderedService(checkedServices);
        clientOrder.setCompanyOrderId(order);
        clientOrderFacade.create(clientOrder);
        if (clientOrdersReservation.getReservationDate() != null) {
            clientOrdersReservation.setClientOrdersId(clientOrder);
            clientReservationFacade.create(clientOrdersReservation);
            clientOrdersReservation = new ClientOrdersReservations();
        }
        if (clientOrdersAgent.getAgentsId()!= null) {
            clientOrdersAgent.setClientOrdersId(clientOrder);
            clientOrdersAgentFacade.create(clientOrdersAgent);
            clientOrdersAgent = new ClientOrdersAgents();
        }
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

    public void setClientOrder(ClientOrders clientOrder) {
        this.clientOrder = clientOrder;
    }

    public ClientOrders getClientOrder() {
        return clientOrder;
    }

    public void setClientOrdersReservation(ClientOrdersReservations clientOrdersReservation) {
        this.clientOrdersReservation = clientOrdersReservation;
    }

    public ClientOrdersReservations getClientOrdersReservation() {
        return clientOrdersReservation;
    }

    public void setClientOrdersAgent(ClientOrdersAgents clientOrdersAgent) {
        this.clientOrdersAgent = clientOrdersAgent;
    }

    public ClientOrdersAgents getClientOrdersAgent() {
        return clientOrdersAgent;
    }

    // Ovaj metod koristimo samo u result.xhtml str. zarad provere vrednosti array-a kada se cekiraju odredjena polja.
    public String getAllServicesArrayAsString() {
        return checkedServices;
    }

}