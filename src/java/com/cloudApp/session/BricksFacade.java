/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.session;

import com.cloudApp.entity.Bricks;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author svujovic
 */
@Stateless
public class BricksFacade extends AbstractFacade<Bricks> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BricksFacade() {
        super(Bricks.class);
    }
    
}
