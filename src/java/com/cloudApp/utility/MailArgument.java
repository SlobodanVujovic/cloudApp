package com.cloudApp.utility;

import java.util.Properties;

public class MailArgument {
    private String recipientEmail, recipientName, companyName, appointmentDate, appointmentTime;
    private Properties mailProperties = new Properties();
    
    public void setMailProperties(){
        mailProperties.setProperty("companyName", companyName);
        mailProperties.setProperty("recipientEmail", recipientEmail);
        mailProperties.setProperty("recipientName", recipientName);
        mailProperties.setProperty("appointmentDate", appointmentDate);
        mailProperties.setProperty("appointmentTime", appointmentTime);
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Properties getMailProperties() {
        return mailProperties;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

}
