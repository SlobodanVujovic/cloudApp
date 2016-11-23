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
@Table(name = "owners_contacts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OwnersContacts.findAll", query = "SELECT o FROM OwnersContacts o"),
    @NamedQuery(name = "OwnersContacts.findById", query = "SELECT o FROM OwnersContacts o WHERE o.id = :id"),
    @NamedQuery(name = "OwnersContacts.findByPhone", query = "SELECT o FROM OwnersContacts o WHERE o.phone = :phone"),
    @NamedQuery(name = "OwnersContacts.findByEmail", query = "SELECT o FROM OwnersContacts o WHERE o.email = :email"),
    @NamedQuery(name = "OwnersContacts.findByOwnersId", query = "SELECT o FROM OwnersContacts o WHERE o.ownersId = :ownersId")})
public class OwnersContacts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "phone")
    private String phone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "owners_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Owners ownersId;

    public OwnersContacts() {
    }

    public OwnersContacts(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Owners getOwnersId() {
        return ownersId;
    }

    public void setOwnersId(Owners ownersId) {
        this.ownersId = ownersId;
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
        if (!(object instanceof OwnersContacts)) {
            return false;
        }
        OwnersContacts other = (OwnersContacts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cloudApp.entity.OwnersContacts[ id=" + id + " ]";
    }

}
