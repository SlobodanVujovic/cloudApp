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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author svujovic
 */
@Entity
@Table(name = "owners")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Owners.findAll", query = "SELECT o FROM Owners o"),
    @NamedQuery(name = "Owners.findById", query = "SELECT o FROM Owners o WHERE o.id = :id"),
    @NamedQuery(name = "Owners.findByFirstName", query = "SELECT o FROM Owners o WHERE o.firstName = :firstName"),
    @NamedQuery(name = "Owners.findByLastName", query = "SELECT o FROM Owners o WHERE o.lastName = :lastName"),
    @NamedQuery(name = "Owners.findByUsername", query = "SELECT o FROM Owners o WHERE o.username = :username"),
    @NamedQuery(name = "Owners.findByPassword", query = "SELECT o FROM Owners o WHERE o.password = :password")})
public class Owners implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownersId")
    private List<OwnersContacts> ownersContactsList;
    @JoinColumn(name = "companies_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Companies companiesId;

    public Owners() {
    }

    public Owners(Integer id) {
        this.id = id;
    }

    public Owners(Integer id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<OwnersContacts> getOwnersContactsList() {
        return ownersContactsList;
    }

    public void setOwnersContactsList(List<OwnersContacts> ownersContactsList) {
        this.ownersContactsList = ownersContactsList;
    }

    public Companies getCompaniesId() {
        return companiesId;
    }

    public void setCompaniesId(Companies companiesId) {
        this.companiesId = companiesId;
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
        if (!(object instanceof Owners)) {
            return false;
        }
        Owners other = (Owners) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.Owners[ id=" + id + " ]";
    }
    
}
