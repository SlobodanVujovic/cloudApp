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
public class CompanyOrderHasBrickPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "company_order_id")
    private int companyOrderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "bricks_id")
    private int bricksId;

    public CompanyOrderHasBrickPK() {
    }

    public CompanyOrderHasBrickPK(int companyOrderId, int bricksId) {
        this.companyOrderId = companyOrderId;
        this.bricksId = bricksId;
    }

    public int getCompanyOrderId() {
        return companyOrderId;
    }

    public void setCompanyOrderId(int companyOrderId) {
        this.companyOrderId = companyOrderId;
    }

    public int getBricksId() {
        return bricksId;
    }

    public void setBricksId(int bricksId) {
        this.bricksId = bricksId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) companyOrderId;
        hash += (int) bricksId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyOrderHasBrickPK)) {
            return false;
        }
        CompanyOrderHasBrickPK other = (CompanyOrderHasBrickPK) object;
        if (this.companyOrderId != other.companyOrderId) {
            return false;
        }
        if (this.bricksId != other.bricksId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.CompanyOrderHasBrickPK[ companyOrderId=" + companyOrderId + ", bricksId=" + bricksId + " ]";
    }
    
}
