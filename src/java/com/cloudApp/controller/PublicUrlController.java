package com.cloudApp.controller;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "publicUrlController")
@RequestScoped
public class PublicUrlController {
    private String url;
    
    public PublicUrlController() {
        
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
