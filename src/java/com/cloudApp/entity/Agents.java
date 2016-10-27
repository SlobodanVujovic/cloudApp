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
@Table(name = "agents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agents.findAll", query = "SELECT a FROM Agents a"),
    @NamedQuery(name = "Agents.findById", query = "SELECT a FROM Agents a WHERE a.agentsPK.id = :id"),
    @NamedQuery(name = "Agents.findByFirstName", query = "SELECT a FROM Agents a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Agents.findByLastName", query = "SELECT a FROM Agents a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Agents.findByPhone", query = "SELECT a FROM Agents a WHERE a.phone = :phone"),
    @NamedQuery(name = "Agents.findByEmail", query = "SELECT a FROM Agents a WHERE a.email = :email"),
    @NamedQuery(name = "Agents.findByCompanyOrderHasBrickCompanyOrderId", query = "SELECT a FROM Agents a WHERE a.agentsPK.companyOrderHasBrickCompanyOrderId = :companyOrderHasBrickCompanyOrderId"),
    @NamedQuery(name = "Agents.findByCompanyOrderHasBrickBricksId", query = "SELECT a FROM Agents a WHERE a.agentsPK.companyOrderHasBrickBricksId = :companyOrderHasBrickBricksId")})
public class Agents implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgentsPK agentsPK;
    @Size(max = 25)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 25)
    @Column(name = "last_name")
    private String lastName;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "phone")
    private String phone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @JoinColumns({
        @JoinColumn(name = "company_order_has_brick_company_order_id", referencedColumnName = "company_order_id", insertable = false, updatable = false),
        @JoinColumn(name = "company_order_has_brick_bricks_id", referencedColumnName = "bricks_id", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private CompanyOrderHasBrick companyOrderHasBrick;

    public Agents() {
    }

    public Agents(AgentsPK agentsPK) {
        this.agentsPK = agentsPK;
    }

    public Agents(int id, int companyOrderHasBrickCompanyOrderId, int companyOrderHasBrickBricksId) {
        this.agentsPK = new AgentsPK(id, companyOrderHasBrickCompanyOrderId, companyOrderHasBrickBricksId);
    }

    public AgentsPK getAgentsPK() {
        return agentsPK;
    }

    public void setAgentsPK(AgentsPK agentsPK) {
        this.agentsPK = agentsPK;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        hash += (agentsPK != null ? agentsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agents)) {
            return false;
        }
        Agents other = (Agents) object;
        if ((this.agentsPK == null && other.agentsPK != null) || (this.agentsPK != null && !this.agentsPK.equals(other.agentsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.Agents[ agentsPK=" + agentsPK + " ]";
    }
    
}
