package com.cloudApp.controller;

/*
Ovo je klasa koja sadrzi osnovne informacije o servisu. Ove informacije se definisu na strani
ordering-define-services.xhtml
*/
public class BasicServiceInfo {
    private String serviceName;
    private boolean reservationRequired;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isReservationRequired() {
        return reservationRequired;
    }

    public void setReservationRequired(boolean reservationRequired) {
        this.reservationRequired = reservationRequired;
    }
    
}
