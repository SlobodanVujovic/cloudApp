/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.Owners;
import com.cloudApp.entity.OwnersContacts;
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
public class OwnersContactsFacade extends AbstractFacade<OwnersContacts> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OwnersContactsFacade() {
        super(OwnersContacts.class);
    }
    
    public List<OwnersContacts> getOwnersContactsByOwnerId(Owners ownersId){
        TypedQuery<OwnersContacts> query = em.createNamedQuery("OwnersContacts.findByOwnersId", OwnersContacts.class);
        query.setParameter("ownersId", ownersId);
        List<OwnersContacts> resultSet = query.getResultList();
        return resultSet;
    }
    
}
