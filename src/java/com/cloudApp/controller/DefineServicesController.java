package com.cloudApp.controller;

import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.CompanyOrderHasDaysOfWeek;
import com.cloudApp.entity.Services;
import com.cloudApp.sessions.CompanyOrderFacade;
import com.cloudApp.sessions.CompanyOrderHasDaysOfWeekFacade;
import com.cloudApp.sessions.DaysOfWeekFacade;
import com.cloudApp.sessions.ServicesFacade;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import javax.faces.flow.FlowScoped;
import org.primefaces.context.RequestContext;

@Named
@FlowScoped(value = "ordering")
public class DefineServicesController implements Serializable {

    public DefineServicesController() {

    }

    private CompanyOrder companyOrder;
    private Services services;
    private int serviceNotification;
    private boolean enableNotificationInput;
    private int numberOfServicesWithReservation;
    private List<BasicServiceInfo> listOfBasicServices;
    private BasicServiceInfo initialBasicService;
    private CompanyOrderHasDaysOfWeek tempCompanyOrderWorkingDays;

    @PostConstruct
    public void init() {
        companyOrder = new CompanyOrder();
        services = new Services();
        listOfBasicServices = new ArrayList();
        initialBasicService = new BasicServiceInfo();
        listOfBasicServices.add(initialBasicService);
    }

    @Inject
    private CompanyController companyController;
    @Inject
    private ServicesFacade servicesFacade;
    @Inject
    private CompanyOrderFacade companyOrderFacade;
    @Inject
    private PublicUrlController publicUrlController;
    @Inject
    private DaysOfWeekFacade daysOfWeekFacade;
    @Inject
    private CompanyOrderHasDaysOfWeekFacade companyOrderHasDaysOfWeekFacade;

    public void writeToDatabase() {
        companyOrder.setCompaniesId(companyController.getSelectedCompany());
        if (serviceNotification > 0) {
            companyOrder.setNotification(serviceNotification);
        }
        companyOrderFacade.create(companyOrder);
        setPublicUrl();
        for (BasicServiceInfo tempBasicService : listOfBasicServices) {
            services.setName(tempBasicService.getServiceName());
            if (tempBasicService.isReservationRequired()) {
                services.setReservation(true);
            } else {
                services.setReservation(false);
            }
            services.setCompanyOrderId(companyOrder);
            servicesFacade.create(services);
        }
        // Ovde unosimo default-ne radne dane (od ponedeljka do petka) za svaki company order. Kasnije administrator moze ovo promeniti kada se
        // uloguje na adminLoginPage.xhtml stranicu.
        for (int i = 0; i < 5; i++) {
            // Obrati paznju da je 1. potrebno definisati PK za entity a zatim set-ovati i vrednosti polja CompanyOrderId i DayOfWeekId.
            tempCompanyOrderWorkingDays = new CompanyOrderHasDaysOfWeek(companyOrder.getId(), daysOfWeekFacade.find(i + 1).getId());
            tempCompanyOrderWorkingDays.setCompanyOrder(companyOrder);
            tempCompanyOrderWorkingDays.setDaysOfWeek(daysOfWeekFacade.find(i + 1));
            tempCompanyOrderWorkingDays.setSortIdx(i);
            tempCompanyOrderWorkingDays.setStartTime(LocalTime.of(8, 0));
            tempCompanyOrderWorkingDays.setCloseTime(LocalTime.of(20, 0));
            companyOrderHasDaysOfWeekFacade.create(tempCompanyOrderWorkingDays);
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

    public int getServiceNotification() {
        return serviceNotification;
    }

    public void setServiceNotification(int serviceNotification) {
        this.serviceNotification = serviceNotification;
    }

    public boolean isEnableNotificationInput() {
        return enableNotificationInput;
    }

    public void setEnableNotificationInput(boolean enableNotificationInput) {
        this.enableNotificationInput = enableNotificationInput;
    }

    public int getNumberOfServicesWithReservation() {
        return numberOfServicesWithReservation;
    }

    public void setNumberOfServicesWithReservation(int numberOfServicesWithReservation) {
        this.numberOfServicesWithReservation = numberOfServicesWithReservation;
    }

    public List<BasicServiceInfo> getListOfBasicServices() {
        return listOfBasicServices;
    }

    public void setListOfBasicServices(List<BasicServiceInfo> listOfBasicServices) {
        this.listOfBasicServices = listOfBasicServices;
    }

    public void add() {
        listOfBasicServices.add(new BasicServiceInfo());
    }

    public void remove(BasicServiceInfo service) {
        if (service.isReservationRequired()) {
            --numberOfServicesWithReservation;
        }
        // Obrati paznju kako se iz bean-a update-uje komponenta u JSF-u.
        RequestContext.getCurrentInstance().update("hiddenInput3");
        // Obrati paznju kako se poziva JavaScript funkcija iz bean-a. Mora se koristiti PrimeFaces jer standardna JSF
        // specifikacija ne podrzava ovo (moci ce u JSF 2.3).
        RequestContext.getCurrentInstance().execute("showNotificationCheckbox();");
        listOfBasicServices.remove(service);
    }

}
