package com.cloudApp.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ShortPasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = value.toString();
        if (password.length() < 6) {
            FacesMessage message = new FacesMessage();
            String messageStr = "Password must be at least 6 charecters long.";
            message.setDetail(messageStr);
            message.setSummary(messageStr);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
