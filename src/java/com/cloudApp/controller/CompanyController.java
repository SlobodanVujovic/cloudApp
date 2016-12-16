package com.cloudApp.controller;

import com.cloudApp.entity.Activity;
import com.cloudApp.entity.Agents;
import javax.inject.Inject;
import javax.inject.Named;
import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyActivities;
import com.cloudApp.entity.CompanysContacts;
import com.cloudApp.entity.CompanysLocation;
import com.cloudApp.sessions.ActivityFacade;
import com.cloudApp.sessions.AgentsFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.flow.FlowScoped;
// Deo neophodan za writeToDatabase() metod.
import com.cloudApp.sessions.CompaniesFacade;
import com.cloudApp.sessions.CompanyActivitiesFacade;
import com.cloudApp.sessions.CompanysContactsFacade;
import com.cloudApp.sessions.CompanysLocationFacade;

@Named
@FlowScoped(value = "ordering")
public class CompanyController implements Serializable {

    public CompanyController() {

    }

    /* Da bi nesto inject-ovali ta klasa mora biti POJO stateful beans (stateful beans are those that retain 
    information across invocation). CompanysLocation je persistence entity klasa (predstavlja tabelu u relation database)
    i kao takva je deo Java Persistence API-ja (JPA) i ne moze se ubaciti u klasu koristeci @Inject.
    
    Fron-end (JSF) kroz ovaj kontroler i referencu ka persistence entity-u ("companyLocation", "selectedCompany"),
    koristeci EL popunjava/uzima vrednosti iz entiteta (objekat koji kreiramo a onda ga kao parametar prosledjujemo metodi
    fasade koja ga unosi u bazu koristeci EntityManager).
     */
    private Companies selectedCompany;
    private CompanysLocation companyLocation;
    private CompanysContacts companyContact;
    private Agents currentAgent;
    private List<Agents> agentList;
    private Activity selectedActivity;
    private List<Activity> listOfActivities;
    private CompanyActivities companyActivities;
 
    @PostConstruct
    public void init() {
        selectedCompany = new Companies();
        companyLocation = new CompanysLocation();
        companyContact = new CompanysContacts();
        currentAgent = new Agents();
        agentList = new ArrayList<>();
        selectedActivity = new Activity();
        listOfActivities = new ArrayList();
        companyActivities = new CompanyActivities();
    }

    /* Fasada je EJB koji inject-ujemo i nju koristimo kada vrsimo CRUD (Create, Read, Update and Delete) operacije nad
    entity-jem u odnosu na bazu (bilo sta da radimo, sto ima veze sa bazom i entity-jem, ide kroz fasadu, jer ona koristi
    EntityManager.    
     */
    @Inject
    private CompaniesFacade companiesFacade;
    @Inject
    private CompanysLocationFacade locationFacade;
    @Inject
    private CompanysContactsFacade contactsFacade;
    @Inject
    private AgentsFacade agentsFacade;
    @Inject
    private ActivityFacade activityFacade;
    @Inject
    private CompanyActivitiesFacade companyActivitiesFacade;

    public void addNewAgent() {
        agentList.add(currentAgent);
        currentAgent = new Agents();
    }

    public String writeToDatabase() {
        /* Kada unosimo podatke u bazu, u tabelu koja ima spoljni kljuc ka nekoj drugoj tabeli, onda u set metod fasade
         prosedjujemo ceo objekat iz koga je spoljasnji kljuc uzet.
         */
        companiesFacade.create(selectedCompany);

        companyLocation.setCompaniesId(selectedCompany);
        locationFacade.create(companyLocation);

        companyContact.setCompaniesId(selectedCompany);
        contactsFacade.create(companyContact);

        agentList.stream().map((tempAgent) -> {
            tempAgent.setCompaniesId(selectedCompany);
            return tempAgent;
        }).forEach((tempAgent) -> {
            agentsFacade.create(tempAgent);
        });
        
        companyActivities.setCompaniesId(selectedCompany);
        companyActivities.setActivityId(selectedActivity);
        companyActivitiesFacade.create(companyActivities);
                
        return "";
    }

    // Getters and setters:
    public Companies getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Companies selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public CompanysContacts getCompanyContact() {
        return companyContact;
    }

    public Agents getCurrentAgent() {
        return currentAgent;
    }

    public void setCurrentAgent(Agents currentAgent) {
        this.currentAgent = currentAgent;
    }

    public void setCompanyContact(CompanysContacts companyContact) {
        this.companyContact = companyContact;
    }

    public List<Agents> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<Agents> agentList) {
        this.agentList = agentList;
    }

    public CompanysLocation getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(CompanysLocation companyLocation) {
        this.companyLocation = companyLocation;
    }

    public Activity getSelectedActivity() {
        return selectedActivity;
    }

    public void setSelectedActivity(Activity selectedActivity) {
        this.selectedActivity = selectedActivity;
    }

    public List<Activity> getListOfActivities() {
        listOfActivities = activityFacade.findAll();
        return listOfActivities;
    }

}
