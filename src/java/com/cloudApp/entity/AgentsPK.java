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
public class AgentsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "company_order_has_brick_company_order_id")
    private int companyOrderHasBrickCompanyOrderId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "company_order_has_brick_bricks_id")
    private int companyOrderHasBrickBricksId;

    public AgentsPK() {
    }

    public AgentsPK(int id, int companyOrderHasBrickCompanyOrderId, int companyOrderHasBrickBricksId) {
        this.id = id;
        this.companyOrderHasBrickCompanyOrderId = companyOrderHasBrickCompanyOrderId;
        this.companyOrderHasBrickBricksId = companyOrderHasBrickBricksId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyOrderHasBrickCompanyOrderId() {
        return companyOrderHasBrickCompanyOrderId;
    }

    public void setCompanyOrderHasBrickCompanyOrderId(int companyOrderHasBrickCompanyOrderId) {
        this.companyOrderHasBrickCompanyOrderId = companyOrderHasBrickCompanyOrderId;
    }

    public int getCompanyOrderHasBrickBricksId() {
        return companyOrderHasBrickBricksId;
    }

    public void setCompanyOrderHasBrickBricksId(int companyOrderHasBrickBricksId) {
        this.companyOrderHasBrickBricksId = companyOrderHasBrickBricksId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) companyOrderHasBrickCompanyOrderId;
        hash += (int) companyOrderHasBrickBricksId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgentsPK)) {
            return false;
        }
        AgentsPK other = (AgentsPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.companyOrderHasBrickCompanyOrderId != other.companyOrderHasBrickCompanyOrderId) {
            return false;
        }
        if (this.companyOrderHasBrickBricksId != other.companyOrderHasBrickBricksId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.AgentsPK[ id=" + id + ", companyOrderHasBrickCompanyOrderId=" + companyOrderHasBrickCompanyOrderId + ", companyOrderHasBrickBricksId=" + companyOrderHasBrickBricksId + " ]";
    }
    
}
