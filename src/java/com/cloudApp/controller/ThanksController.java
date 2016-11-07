package com.cloudApp.controller;

import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.sessions.CompanyOrderFacade;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ThanksController {

    private String url;

    public ThanksController() {

    }

    @Inject
    private CompanyController companyController;
    @Inject
    private CompanyOrderFacade orderFacade;

    public String getUrl() {
        Companies selectedCompany = companyController.getSelectedCompany();
        List<CompanyOrder> orders = orderFacade.getOrdersByCompanyId(selectedCompany);
        if (orders.size() > 1) {
            CompanyOrder lastOrder = orders.get(orders.size() - 1);
            url = lastOrder.getUrl();
        } else {
            url = "";
        }
        return url;
    }

}
