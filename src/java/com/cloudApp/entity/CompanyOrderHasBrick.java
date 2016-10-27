/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "company_order_has_brick")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanyOrderHasBrick.findAll", query = "SELECT c FROM CompanyOrderHasBrick c"),
    @NamedQuery(name = "CompanyOrderHasBrick.findByCompanyOrderId", query = "SELECT c FROM CompanyOrderHasBrick c WHERE c.companyOrderHasBrickPK.companyOrderId = :companyOrderId"),
    @NamedQuery(name = "CompanyOrderHasBrick.findByBricksId", query = "SELECT c FROM CompanyOrderHasBrick c WHERE c.companyOrderHasBrickPK.bricksId = :bricksId"),
    @NamedQuery(name = "CompanyOrderHasBrick.findByBrickParameter", query = "SELECT c FROM CompanyOrderHasBrick c WHERE c.brickParameter = :brickParameter")})
public class CompanyOrderHasBrick implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompanyOrderHasBrickPK companyOrderHasBrickPK;
    @Size(max = 45)
    @Column(name = "brick_parameter")
    private String brickParameter;
    @JoinColumn(name = "bricks_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bricks bricks;
    @JoinColumn(name = "company_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CompanyOrder companyOrder;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyOrderHasBrick")
    private Collection<Emergency> emergencyCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyOrderHasBrick")
    private Collection<Agents> agentsCollection;

    public CompanyOrderHasBrick() {
    }

    public CompanyOrderHasBrick(CompanyOrderHasBrickPK companyOrderHasBrickPK) {
        this.companyOrderHasBrickPK = companyOrderHasBrickPK;
    }

    public CompanyOrderHasBrick(int companyOrderId, int bricksId) {
        this.companyOrderHasBrickPK = new CompanyOrderHasBrickPK(companyOrderId, bricksId);
    }

    public CompanyOrderHasBrickPK getCompanyOrderHasBrickPK() {
        return companyOrderHasBrickPK;
    }

    public void setCompanyOrderHasBrickPK(CompanyOrderHasBrickPK companyOrderHasBrickPK) {
        this.companyOrderHasBrickPK = companyOrderHasBrickPK;
    }

    public String getBrickParameter() {
        return brickParameter;
    }

    public void setBrickParameter(String brickParameter) {
        this.brickParameter = brickParameter;
    }

    public Bricks getBricks() {
        return bricks;
    }

    public void setBricks(Bricks bricks) {
        this.bricks = bricks;
    }

    public CompanyOrder getCompanyOrder() {
        return companyOrder;
    }

    public void setCompanyOrder(CompanyOrder companyOrder) {
        this.companyOrder = companyOrder;
    }

    @XmlTransient
    public Collection<Emergency> getEmergencyCollection() {
        return emergencyCollection;
    }

    public void setEmergencyCollection(Collection<Emergency> emergencyCollection) {
        this.emergencyCollection = emergencyCollection;
    }

    @XmlTransient
    public Collection<Agents> getAgentsCollection() {
        return agentsCollection;
    }

    public void setAgentsCollection(Collection<Agents> agentsCollection) {
        this.agentsCollection = agentsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyOrderHasBrickPK != null ? companyOrderHasBrickPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyOrderHasBrick)) {
            return false;
        }
        CompanyOrderHasBrick other = (CompanyOrderHasBrick) object;
        if ((this.companyOrderHasBrickPK == null && other.companyOrderHasBrickPK != null) || (this.companyOrderHasBrickPK != null && !this.companyOrderHasBrickPK.equals(other.companyOrderHasBrickPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.CompanyOrderHasBrick[ companyOrderHasBrickPK=" + companyOrderHasBrickPK + " ]";
    }
    
}
