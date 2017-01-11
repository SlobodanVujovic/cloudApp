package com.cloudApp.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = LocalTime.class)
public class LocalTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (!value.isEmpty()) {
            return LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm"));
        } else{
            return "";
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LocalTime timeValue = (LocalTime) value;
        return timeValue.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}
