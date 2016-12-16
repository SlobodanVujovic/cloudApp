/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyActivities;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author svujovic
 */
@Stateless
public class CompanyActivitiesFacade extends AbstractFacade<CompanyActivities> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompanyActivitiesFacade() {
        super(CompanyActivities.class);
    }

    public Set<CompanyActivities> getCompanyActivitiesByCompanyId(Companies company) {
        TypedQuery<CompanyActivities> query = getEntityManager().createNamedQuery("CompanyActivities.findByCompanyId", CompanyActivities.class);
        query.setParameter("companiesId", company);
        List<CompanyActivities> resultList = query.getResultList();
        Set<CompanyActivities> resultSet = new HashSet<>();
        for (CompanyActivities tempCompanyActivity : resultList) {
            resultSet.add(tempCompanyActivity);
        }
        return resultSet;
    }
}
