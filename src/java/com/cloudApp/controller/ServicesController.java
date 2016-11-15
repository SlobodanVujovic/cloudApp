package com.cloudApp.controller;

import com.cloudApp.entity.Agents;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.Services;
import com.cloudApp.sessions.CompanyOrderFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@Named(value = "servicesController")
@SessionScoped
public class ServicesController implements Serializable {

    // orderId treba da bude u URL-u (tu vrednost uzimamo iz GET-a) i na osnovu ovog parametra odredjujemo sve ostalo sto
    // se tice podataka vezanih za tu kompaniju.
    private int orderId = 6;
    private CompanyOrder order;
    private List<Services> allServices;
    private List<String> serviceIdWithRequiredReservation;
    private String[] checkedServices;
    private Companies company;
    private List<Agents> allAgents;
    private String choosenAgent;

    public ServicesController() {

    }

    @PostConstruct
    public void init() {
        setCompanyOrder();
        setCompany();
        setServiceIdWithRequiredReservation();
    }

    @Inject
    private CompanyOrderFacade orderFacade;

    public void setCompanyOrder() {
        order = orderFacade.find(orderId);
    }

    public List<Services> getAllServices() {
        allServices = order.getServicesList();
        return allServices;
    }

    public List<String> getServiceIdWithRequiredReservation() {
        return serviceIdWithRequiredReservation;
    }

    public void setServiceIdWithRequiredReservation() {
        allServices = order.getServicesList();
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

    public String[] getCheckedServices() {
        return checkedServices;
    }

    public void setCheckedServices(String[] checkedServices) {
        this.checkedServices = checkedServices;
    }

    public Companies getCompany() {
        return company;
    }

    public void setCompany() {
        company = order.getCompaniesId();
    }

    public String getChoosenAgent() {
        return choosenAgent;
    }

    public void setChoosenAgent(String choosenAgent) {
        this.choosenAgent = choosenAgent;
    }

    public List<Agents> getAllAgents() {
        allAgents = company.getAgentsList();
        return allAgents;
    }
    
    public void scheduleClient(){
        
    }

    // Ovaj metod koristimo samo u result.xhtml str. zarad provere vrednosti array-a kada se cekiraju odredjena polja.
    public String getAllServicesArrayAsString() {
        return Arrays.toString(checkedServices);
    }

}
