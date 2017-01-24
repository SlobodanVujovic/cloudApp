package com.cloudApp.controller;

import com.cloudApp.entity.Owners;
import com.cloudApp.sessions.OwnersFacade;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named
@RequestScoped
public class AuthenticationController implements Serializable{

    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());
    private boolean login = false;
    private String username;
    private String password;
    private UIComponent usernameComponent;
    private UIComponent passwordComponent;
    
    public AuthenticationController() {
        
    }
    
    @Inject
    private OwnersFacade ownersFacade;

    public boolean isLogin() {
        return login;
    }
    
    public void adminLogin(){
        Owners owner = ownersFacade.getOwnerByUsername(username);
        if (owner != null) {
            String resultPassword = owner.getPassword();
            if (resultPassword.equals(password)) {
                login = true;
                // Cuvamo owner-a u map-u u flash scope-u koji brise podatke nakon redirekcije.
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("owner", owner);
                LOGGER.log(Level.INFO, "Password for username: {0} is valid.", username);
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(passwordComponent.getClientId(), new FacesMessage("Incorrect password."));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(usernameComponent.getClientId(), new FacesMessage("Incorrect username."));
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UIComponent getUsernameComponent() {
        return usernameComponent;
    }

    public void setUsernameComponent(UIComponent usernameComponent) {
        this.usernameComponent = usernameComponent;
    }

    public UIComponent getPasswordComponent() {
        return passwordComponent;
    }

    public void setPasswordComponent(UIComponent passwordComponent) {
        this.passwordComponent = passwordComponent;
    }
    
}
