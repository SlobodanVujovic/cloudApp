package com.cloudApp.controller;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class AgentInput implements Serializable{
    private String inputType;
    
    public AgentInput(){
        inputType = "oneByOne";
    }
    
    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

}
