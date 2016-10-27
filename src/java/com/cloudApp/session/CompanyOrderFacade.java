/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.session;

import com.cloudApp.entity.CompanyOrder;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
