package com.cloudApp.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
//@FacesValidator("confirmPasswordValidator")
//Annotation moze da zameni definisanje validatora u xml file-u.
public class ConfirmPasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = value.toString();
        UIInput uiInputConfirmPassword = (UIInput) component.getAttributes().get("confirm_password");
        String confirm_password = uiInputConfirmPassword.getSubmittedValue().toString();

        if (!password.equals(confirm_password)) {
            uiInputConfirmPassword.setValid(false);
            throw new ValidatorException(new FacesMessage("Password and Confirm Password must match."));
        }
    }
}
