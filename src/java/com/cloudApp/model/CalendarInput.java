package com.cloudApp.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CalendarInput implements Serializable{
    private String inputType;
    
    public CalendarInput(){
        inputType = "md";
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
    
}
