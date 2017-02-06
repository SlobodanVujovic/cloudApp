/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author svujovic
 */
@Embeddable
public class CompanyOrderHasDaysOfWeekPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "company_order_id")
    private int companyOrderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "days_of_week_id")
    private int daysOfWeekId;

    public CompanyOrderHasDaysOfWeekPK() {
    }

    public CompanyOrderHasDaysOfWeekPK(int companyOrderId, int daysOfWeekId) {
        this.companyOrderId = companyOrderId;
        this.daysOfWeekId = daysOfWeekId;
    }

    public int getCompanyOrderId() {
        return companyOrderId;
    }

    public void setCompanyOrderId(int companyOrderId) {
        this.companyOrderId = companyOrderId;
    }

    public int getDaysOfWeekId() {
        return daysOfWeekId;
    }

    public void setDaysOfWeekId(int daysOfWeekId) {
        this.daysOfWeekId = daysOfWeekId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) companyOrderId;
        hash += (int) daysOfWeekId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyOrderHasDaysOfWeekPK)) {
            return false;
        }
        CompanyOrderHasDaysOfWeekPK other = (CompanyOrderHasDaysOfWeekPK) object;
        if (this.companyOrderId != other.companyOrderId) {
            return false;
        }
        if (this.daysOfWeekId != other.daysOfWeekId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.CompanyOrderHasDaysOfWeekPK[ companyOrderId=" + companyOrderId + ", daysOfWeekId=" + daysOfWeekId + " ]";
    }
    
}
