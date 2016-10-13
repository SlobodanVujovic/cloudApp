package com.cloudApp.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Services implements Serializable{

    public Services() {}
    
    private int[] services;

    public int[] getServices() {
        return services;
    }

    public void setServices(int[] services) {
        this.services = services;
    }
    
}
