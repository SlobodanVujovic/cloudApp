/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.Agents;
import com.cloudApp.entity.ClientOrders;
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
public class ClientOrdersFacade extends AbstractFacade<ClientOrders> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientOrdersFacade() {
        super(ClientOrders.class);
    }

    public List<ClientOrders> getClientOrdersByCompanyOrderId(CompanyOrder companyOrder) {
        TypedQuery<ClientOrders> query = getEntityManager().createNamedQuery("ClientOrders.findByCompanyOrderId", ClientOrders.class);
        query.setParameter("companyOrderId", companyOrder);
        List<ClientOrders> resultList = query.getResultList();
        return resultList;
    }
    
    public List<ClientOrders> getClientOrdersByAgentId(Agents agent) {
        TypedQuery<ClientOrders> query = getEntityManager().createNamedQuery("ClientOrders.findByAgentId", ClientOrders.class);
        query.setParameter("agentsId", agent);
        List<ClientOrders> resultList = query.getResultList();
        return resultList;
    }
}
