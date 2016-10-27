package com.cloudApp.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class SchedulePeriod implements Serializable{
    private String timePeriod;
    
    public SchedulePeriod(){
        timePeriod = "anytime";
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }
    
}
