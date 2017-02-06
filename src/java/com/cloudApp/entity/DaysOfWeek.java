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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author svujovic
 */
@Entity
@Table(name = "days_of_week")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaysOfWeek.findAll", query = "SELECT d FROM DaysOfWeek d"),
    @NamedQuery(name = "DaysOfWeek.findById", query = "SELECT d FROM DaysOfWeek d WHERE d.id = :id"),
    @NamedQuery(name = "DaysOfWeek.findByDayName", query = "SELECT d FROM DaysOfWeek d WHERE d.dayName = :dayName")})
public class DaysOfWeek implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "day_name")
    private String dayName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "daysOfWeek")
    private List<CompanyOrderHasDaysOfWeek> companyOrderHasDaysOfWeekList;

    public DaysOfWeek() {
    }

    public DaysOfWeek(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    @XmlTransient
    public List<CompanyOrderHasDaysOfWeek> getCompanyOrderHasDaysOfWeekList() {
        return companyOrderHasDaysOfWeekList;
    }

    public void setCompanyOrderHasDaysOfWeekList(List<CompanyOrderHasDaysOfWeek> companyOrderHasDaysOfWeekList) {
        this.companyOrderHasDaysOfWeekList = companyOrderHasDaysOfWeekList;
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
        if (!(object instanceof DaysOfWeek)) {
            return false;
        }
        DaysOfWeek other = (DaysOfWeek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }
    
}
