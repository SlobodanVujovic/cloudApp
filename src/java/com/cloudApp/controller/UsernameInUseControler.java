package com.cloudApp.controller;

import com.cloudApp.entity.Owners;
import com.cloudApp.sessions.OwnersFacade;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UsernameInUseControler implements Validator{
    private Owners tempOwner;
    
    @Inject
    private OwnersFacade ownersFacade;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        tempOwner = ownersFacade.getOwnerByUsername(value.toString());
        if(tempOwner != null){
            FacesMessage message = new FacesMessage();
            String messageStr = "Username is already in use.";
            message.setDetail(messageStr);
            message.setSummary(messageStr);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
}
