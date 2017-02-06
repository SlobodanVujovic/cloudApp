/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.entity;

import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "company_order_has_days_of_week")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanyOrderHasDaysOfWeek.findAll", query = "SELECT c FROM CompanyOrderHasDaysOfWeek c"),
    @NamedQuery(name = "CompanyOrderHasDaysOfWeek.findByCompanyOrderId", query = "SELECT c FROM CompanyOrderHasDaysOfWeek c WHERE c.companyOrderHasDaysOfWeekPK.companyOrderId = :companyOrderId"),
    @NamedQuery(name = "CompanyOrderHasDaysOfWeek.findByDaysOfWeekId", query = "SELECT c FROM CompanyOrderHasDaysOfWeek c WHERE c.companyOrderHasDaysOfWeekPK.daysOfWeekId = :daysOfWeekId"),
    @NamedQuery(name = "CompanyOrderHasDaysOfWeek.findBySortIdx", query = "SELECT c FROM CompanyOrderHasDaysOfWeek c WHERE c.sortIdx = :sortIdx"),
    @NamedQuery(name = "CompanyOrderHasDaysOfWeek.findByStartTime", query = "SELECT c FROM CompanyOrderHasDaysOfWeek c WHERE c.startTime = :startTime"),
    @NamedQuery(name = "CompanyOrderHasDaysOfWeek.findByCloseTime", query = "SELECT c FROM CompanyOrderHasDaysOfWeek c WHERE c.closeTime = :closeTime"),
    @NamedQuery(name = "CompanyOrderHasDaysOfWeek.findByCompanyOrderIdAndDaysOfWeekId", query = "SELECT c FROM CompanyOrderHasDaysOfWeek c WHERE c.companyOrderHasDaysOfWeekPK.companyOrderId = :companyOrderId AND c.companyOrderHasDaysOfWeekPK.daysOfWeekId = :dayOfWeekId")})
public class CompanyOrderHasDaysOfWeek implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompanyOrderHasDaysOfWeekPK companyOrderHasDaysOfWeekPK;
    @Column(name = "sort_idx")
    private Integer sortIdx;
    // startTime i closeTime menjamo u LocalTime tip (umesto da ga ostavimo kao Date tip, kako ga NetBeans pravi) i brisemo
    // @Temporal anotaciju.
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "close_time")
    private LocalTime closeTime;
    @JoinColumn(name = "company_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CompanyOrder companyOrder;
    @JoinColumn(name = "days_of_week_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DaysOfWeek daysOfWeek;

    public CompanyOrderHasDaysOfWeek() {
    }

    public CompanyOrderHasDaysOfWeek(CompanyOrderHasDaysOfWeekPK companyOrderHasDaysOfWeekPK) {
        this.companyOrderHasDaysOfWeekPK = companyOrderHasDaysOfWeekPK;
    }

    public CompanyOrderHasDaysOfWeek(int companyOrderId, int daysOfWeekId) {
        this.companyOrderHasDaysOfWeekPK = new CompanyOrderHasDaysOfWeekPK(companyOrderId, daysOfWeekId);
    }

    public CompanyOrderHasDaysOfWeekPK getCompanyOrderHasDaysOfWeekPK() {
        return companyOrderHasDaysOfWeekPK;
    }

    public void setCompanyOrderHasDaysOfWeekPK(CompanyOrderHasDaysOfWeekPK companyOrderHasDaysOfWeekPK) {
        this.companyOrderHasDaysOfWeekPK = companyOrderHasDaysOfWeekPK;
    }

    public Integer getSortIdx() {
        return sortIdx;
    }

    public void setSortIdx(Integer sortIdx) {
        this.sortIdx = sortIdx;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public CompanyOrder getCompanyOrder() {
        return companyOrder;
    }

    public void setCompanyOrder(CompanyOrder companyOrder) {
        this.companyOrder = companyOrder;
    }

    public DaysOfWeek getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(DaysOfWeek daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyOrderHasDaysOfWeekPK != null ? companyOrderHasDaysOfWeekPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyOrderHasDaysOfWeek)) {
            return false;
        }
        CompanyOrderHasDaysOfWeek other = (CompanyOrderHasDaysOfWeek) object;
        if ((this.companyOrderHasDaysOfWeekPK == null && other.companyOrderHasDaysOfWeekPK != null) || (this.companyOrderHasDaysOfWeekPK != null && !this.companyOrderHasDaysOfWeekPK.equals(other.companyOrderHasDaysOfWeekPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CompanyOrderHasDaysOfWeek\n"
                + "companyOrderHasDaysOfWeekPK = " + companyOrderHasDaysOfWeekPK + "\n"
                + "sortIdx = " + sortIdx;
    }

}
