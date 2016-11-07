/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudApp.sessions;

import com.cloudApp.entity.Owners;
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
public class OwnersFacade extends AbstractFacade<Owners> {

    @PersistenceContext(unitName = "cloudAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OwnersFacade() {
        super(Owners.class);
    }

    public Owners getOwnerByUsername(String username) {
        TypedQuery<Owners> query = em.createNamedQuery("Owners.findByUsername", Owners.class);
        query.setParameter("username", username);
        /*
        Treba da vratimo 1 owner-a ali ipak koristimo getResultList() metod koji vraca listu owner-a jer
        getSingleResult() izbacuje unchecked (runtime) exception, NoResultException, ako ne nadje poklapanje
        (ako bi ovaj metod vracao null umesto sto izbacuje exception onda ne bi znali da li to znaci da zaista 
        nema poklapanja ili je vratio red u kome se nalazi null vrednost).
        Problem kod ovoga je sto runtime exceptions treba da ukazuju na greske u programiranju a checked exceptions
        koristimo u situacijama iz kojih obicno mozemo da se "oporavimo" (Effective Java by Joshua Bloch) 
         */
        List<Owners> results = query.getResultList();
        Owners foundOwner = null;
        if (!results.isEmpty()) {
            foundOwner = results.get(0);
        }
        return foundOwner;
    }
}
