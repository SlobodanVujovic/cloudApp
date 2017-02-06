package com.cloudApp.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

// Kada se ime konvertora definise kao vrednost atributa "value" onda se on
// ne mora definisti i kroz faces-config.xml file.
@FacesConverter(value="listOfIntegersConverter", forClass = List.class)
public class ListOfIntegersConverter implements Converter {

    public ListOfIntegersConverter() {

    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (value == null || value.trim().length() == 0) {
            return value;
        }

        List<Integer> tempList = new ArrayList<>();
        boolean conversionError = false;
        // Sklonimo uglaste zagrade sa kraja i pocetka string-a.
        value = value.substring(1, value.length() - 1);
        // Podelimo string na token-e koji predstavljaju clanove niza.
        StringTokenizer commaTokenizer = new StringTokenizer(value, ", ");
        while (commaTokenizer.hasMoreTokens()) {
            String token = commaTokenizer.nextToken();
            try {
                tempList.add(Integer.getInteger(token));
            } catch (Exception e) {
                conversionError = true;
            }
        }
        if (conversionError) {
            throw new ConverterException();
        }
        return tempList;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        List<Integer> tempList;
        String listAsString;
        if (value instanceof List) {
            tempList = (List) value;
            listAsString = tempList.toString();
            return listAsString;
        }
        return "[]";
    }

}
