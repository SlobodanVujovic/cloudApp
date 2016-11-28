/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanyOrder;
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
public class CompanyOrderFacade extends AbstractFacade<CompanyOrder> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompanyOrderFacade() {
        super(CompanyOrder.class);
    }
    
    public List<CompanyOrder> getOrdersByCompanyId(Companies company) {
        TypedQuery<CompanyOrder> query = em.createNamedQuery("CompanyOrder.findByCompanyId", CompanyOrder.class);
        query.setParameter("companyId", company);
        List<CompanyOrder> resultList = query.getResultList();
        return resultList;
    }
}
