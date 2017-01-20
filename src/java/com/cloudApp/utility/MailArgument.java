package com.cloudApp.utility;

import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.recipientEmail);
        hash = 53 * hash + Objects.hashCode(this.recipientName);
        hash = 53 * hash + Objects.hashCode(this.companyName);
        hash = 53 * hash + Objects.hashCode(this.appointmentDate);
        hash = 53 * hash + Objects.hashCode(this.appointmentTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MailArgument other = (MailArgument) obj;
        if (!Objects.equals(this.recipientEmail, other.recipientEmail)) {
            return false;
        }
        if (!Objects.equals(this.recipientName, other.recipientName)) {
            return false;
        }
        if (!Objects.equals(this.companyName, other.companyName)) {
            return false;
        }
        if (!Objects.equals(this.appointmentDate, other.appointmentDate)) {
            return false;
        }
        if (!Objects.equals(this.appointmentTime, other.appointmentTime)) {
            return false;
        }
        return true;
    }
    
}
