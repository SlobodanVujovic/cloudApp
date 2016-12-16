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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companiesId")
    private List<Agents> agentsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companiesId")
    private List<CompanyOrder> companyOrderList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companiesId")
    private List<CompanyActivities> companyActivitiesList;

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

    /* Ako se na stranici adminLoginPage.xhtml setuje prazno polje kao ime firme, polje ce biti prazno sve dok se vrednost ne
    povuce ponovo iz baze jer se setuje vrednost polja u bean-u koji se onda ispisuje na stranici. Da se ovo ne bi 
    desilo, zabranimo setovanje polja u bean-u ako je vrednost prazno*/
    public void setCompanyName(String companyName) {
        if (!companyName.isEmpty()) {
            this.companyName = companyName;
        }
    }
    
    @XmlTransient
    public List<Agents> getAgentsList() {
        return agentsList;
    }

    public void setAgentsList(List<Agents> agentsList) {
        this.agentsList = agentsList;
    }
    
    @XmlTransient
    public List<CompanyOrder> getCompanyOrderList() {
        return companyOrderList;


    }

    public void setCompanyOrderList(List<CompanyOrder> companyOrderList) {
        this.companyOrderList = companyOrderList;


    }

    @XmlTransient
    public List<CompanyActivities> getCompanyActivitiesList() {
        return companyActivitiesList;
    }

    public void setCompanyActivitiesList(List<CompanyActivities> companyActivitiesList) {
        this.companyActivitiesList = companyActivitiesList;
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
