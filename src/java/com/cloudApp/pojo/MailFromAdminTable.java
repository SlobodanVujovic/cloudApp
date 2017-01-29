package com.cloudApp.pojo;

public class MailFromAdminTable {
    // TODO Promeniti emailFrom adresu u "cloud@mds.rs".
    private String emailFrom = "mdstac@mds.rs";
    private String emailTo;
    private String emailSubject;
    private String emailBody;

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
    
}
