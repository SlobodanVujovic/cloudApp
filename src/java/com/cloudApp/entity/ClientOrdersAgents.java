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
@Table(name = "client_orders_agents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientOrdersAgents.findAll", query = "SELECT c FROM ClientOrdersAgents c"),
    @NamedQuery(name = "ClientOrdersAgents.findById", query = "SELECT c FROM ClientOrdersAgents c WHERE c.id = :id"),
    @NamedQuery(name = "ClientOrdersAgents.findByAgent", query = "SELECT c FROM ClientOrdersAgents c WHERE c.agent = :agent"),
    @NamedQuery(name = "ClientOrdersAgents.findByClientOrderId", query = "SELECT c FROM ClientOrdersAgents c WHERE c.clientOrdersId = :clientOrdersId")})
public class ClientOrdersAgents implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "agent")
    private String agent;
    @JoinColumn(name = "client_orders_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClientOrders clientOrdersId;

    public ClientOrdersAgents() {
    }

    public ClientOrdersAgents(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public ClientOrders getClientOrdersId() {
        return clientOrdersId;
    }

    public void setClientOrdersId(ClientOrders clientOrdersId) {
        this.clientOrdersId = clientOrdersId;
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
        if (!(object instanceof ClientOrdersAgents)) {
            return false;
        }
        ClientOrdersAgents other = (ClientOrdersAgents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.ClientOrdersAgents[ id=" + id + " ]";
    }
    
}
