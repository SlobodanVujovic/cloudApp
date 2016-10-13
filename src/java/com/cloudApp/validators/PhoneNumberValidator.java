package com.cloudApp.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class PhoneNumberValidator implements Validator{
   
    private static final String PHONE_PATTERN = "\\(\\d{3|4|5}\\)\\d{2} \\d{3}-\\d{4}";

    private Pattern pattern;
    private Matcher matcher;

    public PhoneNumberValidator() {
        pattern = Pattern.compile(PHONE_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage("Phone number validation failed.", "Invalid Phone format.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
