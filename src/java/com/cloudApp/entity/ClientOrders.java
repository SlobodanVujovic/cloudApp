/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    @NamedQuery(name = "ClientOrders.findByOrderedService", query = "SELECT c FROM ClientOrders c WHERE c.orderedService = :orderedService"),
    @NamedQuery(name = "ClientOrders.findByClientName", query = "SELECT c FROM ClientOrders c WHERE c.clientName = :clientName"),
    @NamedQuery(name = "ClientOrders.findByClientPhone", query = "SELECT c FROM ClientOrders c WHERE c.clientPhone = :clientPhone"),
    @NamedQuery(name = "ClientOrders.findByClientEmail", query = "SELECT c FROM ClientOrders c WHERE c.clientEmail = :clientEmail"),
    @NamedQuery(name = "ClientOrders.findByCompanyOrderId", query = "SELECT c FROM ClientOrders c WHERE c.companyOrderId = :companyOrderId")})
public class ClientOrders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "ordered_service")
    private String orderedService;
    @Size(max = 45)
    @Column(name = "client_name")
    private String clientName;
    @Size(max = 45)
    @Column(name = "client_phone")
    private String clientPhone;
    @Size(max = 45)
    @Column(name = "client_email")
    private String clientEmail;
    @JoinColumn(name = "company_order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CompanyOrder companyOrderId;

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

    public String getOrderedService() {
        return orderedService;
    }

    public void setOrderedService(String orderedService) {
        this.orderedService = orderedService;
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

    public CompanyOrder getCompanyOrderId() {
        return companyOrderId;
    }

    public void setCompanyOrderId(CompanyOrder companyOrderId) {
        this.companyOrderId = companyOrderId;
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
        return "com.cloudApp.entity.ClientOrders[ id=" + id + " ]";
    }
    
}
