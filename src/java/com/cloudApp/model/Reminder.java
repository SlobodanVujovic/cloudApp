package com.cloudApp.model;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Reminder implements Serializable{
    private String reminderUnit;
    
    public Reminder(){
        reminderUnit = "reminderHour";
    }

    public String getReminderUnit() {
        return reminderUnit;
    }

    public void setReminderUnit(String reminderUnit) {
        this.reminderUnit = reminderUnit;
    }
}
