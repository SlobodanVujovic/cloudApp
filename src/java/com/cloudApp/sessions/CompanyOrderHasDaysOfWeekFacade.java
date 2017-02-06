/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.CompanyOrderHasDaysOfWeek;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author svujovic
 */
@Stateless
public class CompanyOrderHasDaysOfWeekFacade extends AbstractFacade<CompanyOrderHasDaysOfWeek> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompanyOrderHasDaysOfWeekFacade() {
        super(CompanyOrderHasDaysOfWeek.class);
    }

    public List<CompanyOrderHasDaysOfWeek> getCompanyOrderWorkingDaysByCompanyOrderId(int companyOrderId) {
        TypedQuery<CompanyOrderHasDaysOfWeek> query = getEntityManager().createNamedQuery("CompanyOrderHasDaysOfWeek.findByCompanyOrderId", CompanyOrderHasDaysOfWeek.class);
        query.setParameter("companyOrderId", companyOrderId);
        List<CompanyOrderHasDaysOfWeek> resultList = query.getResultList();
        return resultList;
    }

    public CompanyOrderHasDaysOfWeek getCompanyOrderWorkingDaysByCompanyOrderIdAndDayOfWeekId(int companyOrderId, int dayOfWeekId) {
        TypedQuery<CompanyOrderHasDaysOfWeek> query = getEntityManager().createNamedQuery("CompanyOrderHasDaysOfWeek.findByCompanyOrderIdAndDaysOfWeekId", CompanyOrderHasDaysOfWeek.class);
        query.setParameter("companyOrderId", companyOrderId);
        query.setParameter("dayOfWeekId", dayOfWeekId);
        List<CompanyOrderHasDaysOfWeek> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
}
