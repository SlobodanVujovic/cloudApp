/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "emergency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emergency.findAll", query = "SELECT e FROM Emergency e"),
    @NamedQuery(name = "Emergency.findById", query = "SELECT e FROM Emergency e WHERE e.emergencyPK.id = :id"),
    @NamedQuery(name = "Emergency.findByLevel", query = "SELECT e FROM Emergency e WHERE e.level = :level"),
    @NamedQuery(name = "Emergency.findByName", query = "SELECT e FROM Emergency e WHERE e.name = :name"),
    @NamedQuery(name = "Emergency.findByCompanyOrderHasBrickCompanyOrderId", query = "SELECT e FROM Emergency e WHERE e.emergencyPK.companyOrderHasBrickCompanyOrderId = :companyOrderHasBrickCompanyOrderId"),
    @NamedQuery(name = "Emergency.findByCompanyOrderHasBrickBricksId", query = "SELECT e FROM Emergency e WHERE e.emergencyPK.companyOrderHasBrickBricksId = :companyOrderHasBrickBricksId")})
public class Emergency implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmergencyPK emergencyPK;
    @Column(name = "level")
    private Integer level;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @JoinColumns({
        @JoinColumn(name = "company_order_has_brick_company_order_id", referencedColumnName = "company_order_id", insertable = false, updatable = false),
        @JoinColumn(name = "company_order_has_brick_bricks_id", referencedColumnName = "bricks_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private CompanyOrderHasBrick companyOrderHasBrick;

    public Emergency() {
    }

    public Emergency(EmergencyPK emergencyPK) {
        this.emergencyPK = emergencyPK;
    }

    public Emergency(int id, int companyOrderHasBrickCompanyOrderId, int companyOrderHasBrickBricksId) {
        this.emergencyPK = new EmergencyPK(id, companyOrderHasBrickCompanyOrderId, companyOrderHasBrickBricksId);
    }

    public EmergencyPK getEmergencyPK() {
        return emergencyPK;
    }

    public void setEmergencyPK(EmergencyPK emergencyPK) {
        this.emergencyPK = emergencyPK;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyOrderHasBrick getCompanyOrderHasBrick() {
        return companyOrderHasBrick;
    }

    public void setCompanyOrderHasBrick(CompanyOrderHasBrick companyOrderHasBrick) {
        this.companyOrderHasBrick = companyOrderHasBrick;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emergencyPK != null ? emergencyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emergency)) {
            return false;
        }
        Emergency other = (Emergency) object;
        if ((this.emergencyPK == null && other.emergencyPK != null) || (this.emergencyPK != null && !this.emergencyPK.equals(other.emergencyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.Emergency[ emergencyPK=" + emergencyPK + " ]";
    }
    
}
