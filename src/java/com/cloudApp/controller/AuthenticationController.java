package com.cloudApp.controller;

import com.cloudApp.entity.Owners;
import com.cloudApp.sessions.OwnersFacade;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class AuthenticationController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class.getName());
    private boolean login = false;
    private String username;
    private String password;
    private Owners owner;

    public AuthenticationController() {

    }

    @Inject
    private OwnersFacade ownersFacade;
    @Inject
    private NavigationController navigationController;

    public boolean isLogin() {
        return login;
    }

    public void adminLogin() {
        owner = ownersFacade.getOwnerByUsername(username);
        if (owner != null) {
            String resultPassword = owner.getPassword();
            if (resultPassword.equals(password)) {
                login = true;
                HttpSession session = SessionController.getSession();
                session.setAttribute("username", username);
                LOGGER.log(Level.INFO, "Password for username: {0} is valid.", username);
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("logInPassword", new FacesMessage("Incorrect password."));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("userName", new FacesMessage("Incorrect username."));
        }
    }

    // Kada se admin izloguje, potrebno je uraditi invalidate session.
    public String adminLogout() {
        HttpSession session = SessionController.getSession();
        session.invalidate();
        return navigationController.goToLogin();
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

    public Owners getOwner() {
        return owner;
    }

}
