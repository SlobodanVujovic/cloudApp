package com.cloudApp.utility;

import java.util.Properties;

public class ConfirmationMailArgument {
    private String recipientEmail, recipientName, companyName;
    private Properties mailProperties = new Properties();
    
    public void setMailProperties(){
        mailProperties.setProperty("companyName", companyName);
        mailProperties.setProperty("recipientEmail", recipientEmail);
        mailProperties.setProperty("recipientName", recipientName);
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Properties getMailProperties() {
        return mailProperties;
    }

    public void setMailProperties(Properties mailProperties) {
        this.mailProperties = mailProperties;
    }
    
}
