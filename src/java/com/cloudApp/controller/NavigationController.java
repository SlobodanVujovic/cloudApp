package com.cloudApp.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class NavigationController {

    public NavigationController() {

    }

    public String goHome() {
        return "HOME";
    }

    public String goToCompany() {
        return "COMPANY";
    }

    public String goToAdmin() {
        return "ADMIN";
    }

    public String goToDefineServices() {
        return "DEF_SERVICE";
    }

    public String goToServices() {
        return "SERVICES";
    }

    public String goToThanks() {
        return "THANKS";
    }
    
    public String goToLogin() {
        return "LOGIN";
    }
    
    public String goToLoginAdmin(){
        return "LOGIN_ADMIN";
    }

}
