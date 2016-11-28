package com.cloudApp.controller;

import com.cloudApp.entity.Owners;
import com.cloudApp.entity.OwnersContacts;
import com.cloudApp.sessions.OwnersContactsFacade;
import com.cloudApp.sessions.OwnersFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@FlowScoped(value = "ordering")
public class AdminController implements Serializable {

    public AdminController() {

    }

    private Owners owner;
    private OwnersContacts ownersContacts;

    @PostConstruct
    public void init() {
        owner = new Owners();
        ownersContacts = new OwnersContacts();
    }

    @Inject
    private OwnersFacade ownersFacade;
    @Inject
    private OwnersContactsFacade ownersContactsFacade;
    @Inject
    private CompanyController companyController;

    public void writeToDatabase() {
        owner.setCompaniesId(companyController.getSelectedCompany());
        ownersFacade.create(owner);

        ownersContacts.setOwnersId(owner);
        ownersContactsFacade.create(ownersContacts);
    }

    public Owners getOwner() {
        return owner;
    }

    public void setOwner(Owners owner) {
        this.owner = owner;
    }

    public OwnersContacts getOwnersContacts() {
        return ownersContacts;
    }

    public void setOwnersContacts(OwnersContacts ownersContacts) {
        this.ownersContacts = ownersContacts;
    }

}
