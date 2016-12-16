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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author svujovic
 */
@Entity
@Table(name = "company_activities")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanyActivities.findAll", query = "SELECT c FROM CompanyActivities c"),
    @NamedQuery(name = "CompanyActivities.findById", query = "SELECT c FROM CompanyActivities c WHERE c.id = :id"),
    @NamedQuery(name = "CompanyActivities.findByCompanyId", query = "SELECT c FROM CompanyActivities c WHERE c.companiesId = :companiesId")})
public class CompanyActivities implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Activity activityId;
    @JoinColumn(name = "companies_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Companies companiesId;

    public CompanyActivities() {
    }

    public CompanyActivities(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }

    public Companies getCompaniesId() {
        return companiesId;
    }

    public void setCompaniesId(Companies companiesId) {
        this.companiesId = companiesId;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + companiesId.getCompanyName().hashCode();
        hash = 31 * hash + activityId.getActivity().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CompanyActivities)) {
            System.out.println("Equals vraca false 1");
            return false;
        }
        CompanyActivities other = (CompanyActivities) object;
        if ((this.companiesId.getId() != other.companiesId.getId())
                || (!this.activityId.getActivity().equals(other.activityId.getActivity()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getActivityId().getId());
    }

}
