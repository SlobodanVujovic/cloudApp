package com.cloudApp.controller;

public class ClientOrderPresenter {
    private String clientName;
    private String orderedService;
    private String reservationDate;
    private String reservationTime;
    private String agent;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrderedService() {
        return orderedService;
    }

    public void setOrderedService(String orderedService) {
        this.orderedService = orderedService;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
    
}
