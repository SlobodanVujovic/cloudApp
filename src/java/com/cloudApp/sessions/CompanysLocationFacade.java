/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanysLocation;
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
public class CompanysLocationFacade extends AbstractFacade<CompanysLocation> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompanysLocationFacade() {
        super(CompanysLocation.class);
    }

    public CompanysLocation getLocationByCompanyId(Companies company) {
        TypedQuery<CompanysLocation> query = em.createNamedQuery("CompanysLocation.findByCompanyId", CompanysLocation.class);
        query.setParameter("companyId", company);
        List<CompanysLocation> resultList = query.getResultList();
        CompanysLocation resultLocation = null;
        if (!resultList.isEmpty()) {
            resultLocation = resultList.get(0);
        }
        return resultLocation;
    }
}
