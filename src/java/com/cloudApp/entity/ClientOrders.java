/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author svujovic
 */
@Entity
@Table(name = "client_orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientOrders.findAll", query = "SELECT c FROM ClientOrders c"),
    @NamedQuery(name = "ClientOrders.findById", query = "SELECT c FROM ClientOrders c WHERE c.id = :id"),
    @NamedQuery(name = "ClientOrders.findByClientName", query = "SELECT c FROM ClientOrders c WHERE c.clientName = :clientName"),
    @NamedQuery(name = "ClientOrders.findByClientPhone", query = "SELECT c FROM ClientOrders c WHERE c.clientPhone = :clientPhone"),
    @NamedQuery(name = "ClientOrders.findByClientEmail", query = "SELECT c FROM ClientOrders c WHERE c.clientEmail = :clientEmail"),
    @NamedQuery(name = "ClientOrders.findByCompanyOrderId", query = "SELECT c FROM ClientOrders c WHERE c.companyOrderId = :companyOrderId"),
    @NamedQuery(name = "ClientOrders.findByAgentId", query = "SELECT c FROM ClientOrders c WHERE c.agentsId = :agentsId"),
    // Ovaj query spaja client_orders tabelu sa reservations tabelom uzimajuci samo record-e sa setovanim companyOrderId-jem i sortira, u rastucem poretku, na osnovu reservationDate kolone iz reservations tabele. Ako neki client order nema
    // reservations input onda taj record ide na pocetak tabele.
    @NamedQuery(name = "ClientOrders.joinWithReservationsAndSortByReservationDate", query = "SELECT c FROM ClientOrders c LEFT JOIN c.reservationsList r WHERE c.companyOrderId = :companyOrderId ORDER BY r.reservationDate")})
public class ClientOrders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "client_name")
    private String clientName;
    @Size(max = 45)
    @Column(name = "client_phone")
    private String clientPhone;
    @Size(max = 45)
    @Column(name = "client_email")
    private String clientEmail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientOrdersId")
    private List<Reservations> reservationsList;
    @JoinColumn(name = "agents_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Agents agentsId;
    @JoinColumn(name = "company_order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CompanyOrder companyOrderId;
    @JoinColumn(name = "services_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Services servicesId;

    public ClientOrders() {
    }

    public ClientOrders(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @XmlTransient
    public List<Reservations> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(List<Reservations> reservationsList) {
        this.reservationsList = reservationsList;
    }

    public Agents getAgentsId() {
        return agentsId;
    }

    public void setAgentsId(Agents agentsId) {
        this.agentsId = agentsId;
    }

    public CompanyOrder getCompanyOrderId() {
        return companyOrderId;
    }

    public void setCompanyOrderId(CompanyOrder companyOrderId) {
        this.companyOrderId = companyOrderId;
    }

    public Services getServicesId() {
        return servicesId;
    }

    public void setServicesId(Services servicesId) {
        this.servicesId = servicesId;
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
        if (!(object instanceof ClientOrders)) {
            return false;
        }
        ClientOrders other = (ClientOrders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClientOrders:"
                + "id=" + id
                + "servicesId=" + servicesId
                + "clientName=" + clientName
                + "clientPhone=" + clientPhone
                + "clientEmail=" + clientEmail
                + "agentsId=" + agentsId;
    }

//    @Override
//    public int compareTo(ClientOrders otherOrders) {
//        // Ako ne postoji rezervacija dodelimo datum 1.1.2000. da bi ti zapisi bili na pocetku tabele.
//        LocalDate date = LocalDate.of(2000, 1, 1);
//        LocalDate otherDate = LocalDate.of(2000, 1, 1);
//        List<Reservations> reservations = this.getReservationsList();
//        if (!reservations.isEmpty()) {
//            date = reservations.get(0).getReservationDate();
//        }
//        List<Reservations> otherReservations = otherOrders.getReservationsList();
//        if (!otherReservations.isEmpty()) {
//            otherDate = otherReservations.get(0).getReservationDate();
//        }
//        int cmp = date.getYear() - otherDate.getYear();
//        if (cmp == 0) {
//            cmp = (date.getMonthValue() - otherDate.getMonthValue());
//            if (cmp == 0) {
//                cmp = (date.getDayOfMonth() - otherDate.getDayOfMonth());
//            }
//        }
//        return cmp;
//    }
    
}