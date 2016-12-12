/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author svujovic
 */
@Entity
@Table(name = "company_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanyOrder.findAll", query = "SELECT c FROM CompanyOrder c"),
    @NamedQuery(name = "CompanyOrder.findById", query = "SELECT c FROM CompanyOrder c WHERE c.id = :id"),
    @NamedQuery(name = "CompanyOrder.findByDateCreated", query = "SELECT c FROM CompanyOrder c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "CompanyOrder.findByUrl", query = "SELECT c FROM CompanyOrder c WHERE c.url = :url"),
    @NamedQuery(name = "CompanyOrder.findByAmount", query = "SELECT c FROM CompanyOrder c WHERE c.amount = :amount"),
    @NamedQuery(name = "CompanyOrder.findByNotification", query = "SELECT c FROM CompanyOrder c WHERE c.notification = :notification"),
    @NamedQuery(name = "CompanyOrder.findByCompanyId", query = "SELECT c FROM CompanyOrder c WHERE c.companiesId = :companyId")})
public class CompanyOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "url")
    private String url = "http://www.mds.rs/INVALID_URL";
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private int amount = 0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "notification")
    private int notification = 0;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyOrderId")
    private List<ClientOrders> clientOrdersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyOrderId")
    private List<Services> servicesList;
    @JoinColumn(name = "companies_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Companies companiesId;

    public CompanyOrder() {
    }

    public CompanyOrder(Integer id) {
        this.id = id;
    }

    public CompanyOrder(Integer id, Date dateCreated, String url, int amount) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.url = url;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @XmlTransient
    public List<ClientOrders> getClientOrdersList() {
        return clientOrdersList;
    }

    public void setClientOrdersList(List<ClientOrders> clientOrdersList) {
        this.clientOrdersList = clientOrdersList;
    }
     
    @XmlTransient
    public List<Services> getServicesList() {
        return servicesList;
    }

    public void setServicesList(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    public Companies getCompaniesId() {
        return companiesId;
    }

    public void setCompaniesId(Companies companiesId) {
        this.companiesId = companiesId;
    }

    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
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
        if (!(object instanceof CompanyOrder)) {
            return false;
        }
        CompanyOrder other = (CompanyOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.CompanyOrder[ id=" + id + " ]";
    }

}
