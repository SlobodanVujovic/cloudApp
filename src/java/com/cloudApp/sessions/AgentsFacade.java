/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.Agents;
import com.cloudApp.entity.Companies;
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
public class AgentsFacade extends AbstractFacade<Agents> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgentsFacade() {
        super(Agents.class);
    }

    public List<Agents> getAgentsByCompanyId(Companies company) {
        TypedQuery<Agents> query = getEntityManager().createNamedQuery("Agents.findByCompanyId", Agents.class);
        query.setParameter("companyId", company);
        List<Agents> resultList = query.getResultList();
        return resultList;
    }

}
