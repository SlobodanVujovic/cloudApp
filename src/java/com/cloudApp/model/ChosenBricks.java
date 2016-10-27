package com.cloudApp.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ChosenBricks implements Serializable {
    private String selectedBricks;
    
    public ChosenBricks(){
    }

    public String getSelectedBricks() {
        return selectedBricks;
    }

    public void setSelectedBricks(String selectedBricks) {
        this.selectedBricks = selectedBricks;
    }
}
