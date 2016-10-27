/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "bricks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bricks.findAll", query = "SELECT b FROM Bricks b"),
    @NamedQuery(name = "Bricks.findById", query = "SELECT b FROM Bricks b WHERE b.id = :id"),
    @NamedQuery(name = "Bricks.findByName", query = "SELECT b FROM Bricks b WHERE b.name = :name"),
    @NamedQuery(name = "Bricks.findByPrice", query = "SELECT b FROM Bricks b WHERE b.price = :price")})
public class Bricks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bricks")
    private Collection<CompanyOrderHasBrick> companyOrderHasBrickCollection;

    public Bricks() {
    }

    public Bricks(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @XmlTransient
    public Collection<CompanyOrderHasBrick> getCompanyOrderHasBrickCollection() {
        return companyOrderHasBrickCollection;
    }

    public void setCompanyOrderHasBrickCollection(Collection<CompanyOrderHasBrick> companyOrderHasBrickCollection) {
        this.companyOrderHasBrickCollection = companyOrderHasBrickCollection;
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
        if (!(object instanceof Bricks)) {
            return false;
        }
        Bricks other = (Bricks) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.Bricks[ id=" + id + " ]";
    }
    
}
