/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author svujovic
 */
@Entity
@Table(name = "reservations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservations.findAll", query = "SELECT r FROM Reservations r"),
    @NamedQuery(name = "Reservations.findById", query = "SELECT r FROM Reservations r WHERE r.id = :id"),
    @NamedQuery(name = "Reservations.findByReservationDate", query = "SELECT r FROM Reservations r WHERE r.reservationDate = :reservationDate"),
    @NamedQuery(name = "Reservations.findByReservationTime", query = "SELECT r FROM Reservations r WHERE r.reservationTime = :reservationTime"),
    @NamedQuery(name = "Reservations.findBySendingDate", query = "SELECT r FROM Reservations r WHERE r.sendingDate = :sendingDate"),
    @NamedQuery(name = "Reservations.findBySendingTime", query = "SELECT r FROM Reservations r WHERE r.sendingTime = :sendingTime"),
    @NamedQuery(name = "Reservations.findByNotificationsSent", query = "SELECT r FROM Reservations r WHERE r.notificationsSent = :notificationsSent"),
    @NamedQuery(name = "Reservations.findByClientOrdersId", query = "SELECT r FROM Reservations r WHERE r.clientOrdersId = :clientOrdersId"),
//    Query koji izvlaci Reservations koji ispunjavaju sledece uslove:
//    sendingData jednak datumu kada se query poziva,
//    hour deo sendingTime-a = argumentu koji se prosledjuje query-ju i
//    notificationsSent vrednost = false.
    @NamedQuery(name = "Reservations.findBySendingDateAndTime", query = "SELECT r FROM Reservations r WHERE r.sendingDate = CURRENT_DATE AND SUBSTRING(r.sendingTime, 1, 2) = :sendingTime AND r.notificationsSent = false")})
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "reservation_date")
    @Temporal(TemporalType.DATE)
    private Date reservationDate;
    // reservation_time menjamo u String tip (umesto da ga ostavimo kao Date tip, kako ga NetBeans pravi) i brisemo
    // @Temporal anotaciju.
    @Column(name = "reservation_time")
    private String reservationTime;
    @Column(name = "sending_date")
    @Temporal(TemporalType.DATE)
    private Date sendingDate;
    // sending_time menjamo u String tip (umesto da ga ostavimo kao Date tip, kako ga NetBeans pravi) i brisemo
    // @Temporal anotaciju.
    @Column(name = "sending_time")
    private String sendingTime;
    @Column(name = "notifications_sent")
    private Boolean notificationsSent = Boolean.FALSE;
    @JoinColumn(name = "client_orders_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClientOrders clientOrdersId;

    public Reservations() {
    }

    public Reservations(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(String sendingTime) {
        this.sendingTime = sendingTime;
    }

    public Boolean getNotificationsSent() {
        return notificationsSent;
    }

    public void setNotificationsSent(Boolean notificationsSent) {
        this.notificationsSent = notificationsSent;
    }

    public ClientOrders getClientOrdersId() {
        return clientOrdersId;
    }

    public void setClientOrdersId(ClientOrders clientOrdersId) {
        this.clientOrdersId = clientOrdersId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservations)) {
            return false;
        }
        Reservations other = (Reservations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.Reservations[ id=" + id + " ]";
    }
    
}
