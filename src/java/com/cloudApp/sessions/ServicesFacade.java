/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.Services;
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
public class ServicesFacade extends AbstractFacade<Services> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ServicesFacade() {
        super(Services.class);
    }

    public List<Services> getServicesByCompanyOrderId(CompanyOrder companyOrder) {
        TypedQuery<Services> query = em.createNamedQuery("Services.findByCompanyOrderId", Services.class);
        query.setParameter("companyOrderId", companyOrder);
        List<Services> resultList = query.getResultList();
        return resultList;
    }
}
