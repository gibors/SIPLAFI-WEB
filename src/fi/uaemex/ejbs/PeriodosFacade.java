/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.ejbs;

import fi.uaemex.entities.Periodos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author IEEM
 */
@Stateless
public class PeriodosFacade extends AbstractFacade<Periodos> {
    @PersistenceContext(unitName = "SIPLAFI-WEB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodosFacade() {
        super(Periodos.class);
    }
    
}
