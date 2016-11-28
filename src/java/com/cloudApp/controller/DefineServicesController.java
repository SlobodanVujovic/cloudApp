package com.cloudApp.controller;

import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.Services;
import com.cloudApp.sessions.CompanyOrderFacade;
import com.cloudApp.sessions.ServicesFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import javax.faces.flow.FlowScoped;

@Named
@FlowScoped(value = "ordering")
public class DefineServicesController implements Serializable {

    public DefineServicesController() {

    }

    private CompanyOrder companyOrder;
    private Services services;
    private String serviceNames;
    private String serviceReservations;

    @PostConstruct
    public void init() {
        companyOrder = new CompanyOrder();
        services = new Services();
    }
    
    @Inject
    private CompanyController companyController;
    @Inject
    private ServicesFacade servicesFacade;
    @Inject
    private CompanyOrderFacade companyOrderFacade;
    @Inject
    private PublicUrlController publicUrlController;

    public void writeToDatabase() {
        companyOrder.setCompaniesId(companyController.getSelectedCompany());
        companyOrderFacade.create(companyOrder);
        setPublicUrl();
        String[] servicesArray = serviceNames.split(",");
        for (int i = 0; i < servicesArray.length; i++) {
            services.setName(servicesArray[i]);
            if (serviceReservations.contains(i + 1 + "")) {
                services.setReservation(true);
            } else {
                services.setReservation(false);
            }
            services.setCompanyOrderId(companyOrder);
            servicesFacade.create(services);
        }
    }

    public void setPublicUrl() {
        Companies selectedCompany = companyController.getSelectedCompany();
        List<CompanyOrder> orders = companyOrderFacade.getOrdersByCompanyId(selectedCompany);
        if (orders.size() > 0) {
            CompanyOrder lastOrder = orders.get(orders.size() - 1);
            int orderId = lastOrder.getId();
            String url = "localhost:8080/cloudapp/faces/cloud/services.xhtml?poId=" + orderId;
            lastOrder.setUrl(url);
            companyOrderFacade.edit(lastOrder);
            publicUrlController.setUrl(url);
        }
    }

    public String getServiceReservations() {
        return serviceReservations;
    }

    public void setServiceReservations(String serviceReservations) {
        this.serviceReservations = serviceReservations;
    }

    public String getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(String serviceNames) {
        this.serviceNames = serviceNames;
    }

    public CompanyOrder getCompanyOrder() {
        return companyOrder;
    }

    public void setCompanyOrder(CompanyOrder companyOrder) {
        this.companyOrder = companyOrder;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }
}
