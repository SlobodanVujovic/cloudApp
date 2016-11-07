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
@Table(name = "companys_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanysLocation.findAll", query = "SELECT c FROM CompanysLocation c"),
    @NamedQuery(name = "CompanysLocation.findById", query = "SELECT c FROM CompanysLocation c WHERE c.id = :id"),
    @NamedQuery(name = "CompanysLocation.findByAddress", query = "SELECT c FROM CompanysLocation c WHERE c.address = :address"),
    @NamedQuery(name = "CompanysLocation.findByCity", query = "SELECT c FROM CompanysLocation c WHERE c.city = :city"),
    @NamedQuery(name = "CompanysLocation.findByZipCode", query = "SELECT c FROM CompanysLocation c WHERE c.zipCode = :zipCode"),
    @NamedQuery(name = "CompanysLocation.findByState", query = "SELECT c FROM CompanysLocation c WHERE c.state = :state"),
    @NamedQuery(name = "CompanysLocation.findByCompanyId", query = "SELECT c FROM CompanysLocation c WHERE c.companiesId = :companyId")})
public class CompanysLocation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "address")
    private String address;
    @Size(max = 45)
    @Column(name = "city")
    private String city;
    @Column(name = "zip_code")
    private Short zipCode;
    @Size(max = 45)
    @Column(name = "state")
    private String state;
    @JoinColumn(name = "companies_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Companies companiesId;

    public CompanysLocation() {
    }

    public CompanysLocation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Short getZipCode() {
        return zipCode;
    }

    public void setZipCode(Short zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Companies getCompaniesId() {
        return companiesId;
    }

    public void setCompaniesId(Companies companiesId) {
        this.companiesId = companiesId;
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
        if (!(object instanceof CompanysLocation)) {
            return false;
        }
        CompanysLocation other = (CompanysLocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CompanysLocation\n"
                + "companyId= " + companiesId + "\n"
                + "id= " + id + "\n"
                + "address= " + address + "\n"
                + "city= " + city + "\n"
                + "ZIP code= " + zipCode + "\n"
                + "state= " + state + "\n"
                + "----------------------------------------------------------------------";
    }

}
