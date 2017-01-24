package com.cloudApp.controller;

import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.Reservations;

/**
 *
 * @author svujovic
 */
public class ClientOrderPresenter {
    private ClientOrders clientOrder;
    private Reservations reservation;

    public ClientOrders getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrders clientOrder) {
        this.clientOrder = clientOrder;
    }

    public Reservations getReservation() {
        return reservation;
    }

    public void setReservation(Reservations reservation) {
        this.reservation = reservation;
    }
    
}
