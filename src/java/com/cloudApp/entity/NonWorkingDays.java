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
@Table(name = "non_working_days")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NonWorkingDays.findAll", query = "SELECT n FROM NonWorkingDays n"),
    @NamedQuery(name = "NonWorkingDays.findById", query = "SELECT n FROM NonWorkingDays n WHERE n.id = :id"),
    @NamedQuery(name = "NonWorkingDays.findByDate", query = "SELECT n FROM NonWorkingDays n WHERE n.date = :date")})
public class NonWorkingDays implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "company_order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CompanyOrder companyOrderId;

    public NonWorkingDays() {
    }

    public NonWorkingDays(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof NonWorkingDays)) {
            return false;
        }
        NonWorkingDays other = (NonWorkingDays) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.NonWorkingDays[ id=" + id + " ]";
    }
    
}
