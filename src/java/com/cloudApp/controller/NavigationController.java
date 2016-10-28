package com.cloudApp.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class NavigationController {
    
    public NavigationController(){
        
    }
    
    public String goHome(){
        return "HOME";
    }
    
    public String goToAdmin(){
        return "ordering-admin-info";
    }
    
    public String goToCompany(){
        return "COMPANY";
    }
    
    public String goToServices(){
        return "SERVICES";
    }
    
    public String goToThanks(){
        return "ordering-thanks";
    }
    
    public String goToDefineServices(){
        return "DEF_SERVICE";
    }
}
