/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.CompanyOrder;
import com.cloudApp.entity.Reservations;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.xml.transform.Source;

/**
 *
 * @author svujovic
 */
@Stateless
public class ReservationsFacade extends AbstractFacade<Reservations> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReservationsFacade() {
        super(Reservations.class);
    }

    public Reservations getReservationByClientOrdersId(ClientOrders clientOrders) {
        TypedQuery<Reservations> query = em.createNamedQuery("Reservations.findByClientOrdersId", Reservations.class);
        query.setParameter("clientOrdersId", clientOrders);
        List<Reservations> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

}
