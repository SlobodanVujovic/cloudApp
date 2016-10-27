/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author svujovic
 */
@Entity
@Table(name = "companies")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Companies.findAll", query = "SELECT c FROM Companies c"),
    @NamedQuery(name = "Companies.findById", query = "SELECT c FROM Companies c WHERE c.id = :id"),
    @NamedQuery(name = "Companies.findByCompanyName", query = "SELECT c FROM Companies c WHERE c.companyName = :companyName")})
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "company_name")
    private String companyName;
    @JoinTable(name = "companies_has_owners", joinColumns = {
        @JoinColumn(name = "companies_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "owners_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Owners> ownersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companiesId")
    private Collection<CompanysLocation> companysLocationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companiesId")
    private Collection<CompanysContacts> companysContactsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companiesId")
    private Collection<CompanyOrder> companyOrderCollection;

    public Companies() {
    }

    public Companies(Integer id) {
        this.id = id;
    }

    public Companies(Integer id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @XmlTransient
    public Collection<Owners> getOwnersCollection() {
        return ownersCollection;
    }

    public void setOwnersCollection(Collection<Owners> ownersCollection) {
        this.ownersCollection = ownersCollection;
    }

    @XmlTransient
    public Collection<CompanysLocation> getCompanysLocationCollection() {
        return companysLocationCollection;
    }

    public void setCompanysLocationCollection(Collection<CompanysLocation> companysLocationCollection) {
        this.companysLocationCollection = companysLocationCollection;
    }

    @XmlTransient
    public Collection<CompanysContacts> getCompanysContactsCollection() {
        return companysContactsCollection;
    }

    public void setCompanysContactsCollection(Collection<CompanysContacts> companysContactsCollection) {
        this.companysContactsCollection = companysContactsCollection;
    }

    @XmlTransient
    public Collection<CompanyOrder> getCompanyOrderCollection() {
        return companyOrderCollection;
    }

    public void setCompanyOrderCollection(Collection<CompanyOrder> companyOrderCollection) {
        this.companyOrderCollection = companyOrderCollection;
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
        if (!(object instanceof Companies)) {
            return false;
        }
        Companies other = (Companies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.Companies[ id=" + id + " ]";
    }
    
}
