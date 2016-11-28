/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.ClientOrders;
import com.cloudApp.entity.ClientOrdersAgents;
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
public class ClientOrdersAgentsFacade extends AbstractFacade<ClientOrdersAgents> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientOrdersAgentsFacade() {
        super(ClientOrdersAgents.class);
    }
    
    public ClientOrdersAgents getClientOrdersAgentsByClientOrdersId(ClientOrders clientOrders) {
        TypedQuery<ClientOrdersAgents> query = getEntityManager().createNamedQuery("ClientOrdersAgents.findByClientOrderId", ClientOrdersAgents.class);
        query.setParameter("clientOrdersId", clientOrders);
        List<ClientOrdersAgents> resultList = query.getResultList();
        ClientOrdersAgents resultClientOrdersAgents = null;
        if (!resultList.isEmpty()) {
            resultClientOrdersAgents = resultList.get(0);
        }
        return resultClientOrdersAgents;
    }
}
