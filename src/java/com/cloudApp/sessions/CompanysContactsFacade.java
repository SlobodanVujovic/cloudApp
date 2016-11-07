/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.Companies;
import com.cloudApp.entity.CompanysContacts;
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
public class CompanysContactsFacade extends AbstractFacade<CompanysContacts> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompanysContactsFacade() {
        super(CompanysContacts.class);
    }

    public CompanysContacts getContactByCompanyId(Companies companty) {
        TypedQuery<CompanysContacts> query = em.createNamedQuery("CompanysContacts.findByCompanyId", CompanysContacts.class);
        query.setParameter("companyId", companty);
        List<CompanysContacts> resultList = query.getResultList();
        CompanysContacts resultContact = null;
        if (!resultList.isEmpty()) {
            resultContact = resultList.get(0);
        }
        return resultContact;
    }
}
