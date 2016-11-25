/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.ClientOrdersReservations;
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
public class ClientOrdersReservationsFacade extends AbstractFacade<ClientOrdersReservations> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientOrdersReservationsFacade() {
        super(ClientOrdersReservations.class);
    }

    public ClientOrdersReservations getClientOrdersAgentsByClientOrdersId(ClientOrders clientOrders) {
        TypedQuery<ClientOrdersReservations> query = getEntityManager().createNamedQuery("ClientOrdersReservations.findByClientOrderId", ClientOrdersReservations.class);
        query.setParameter("clientOrdersId", clientOrders);
        List<ClientOrdersReservations> resultList = query.getResultList();
        ClientOrdersReservations resultClientOrdersReservations = null;
        if (!resultList.isEmpty()) {
            resultClientOrdersReservations = resultList.get(0);
        }
        return resultClientOrdersReservations;
    }
}
