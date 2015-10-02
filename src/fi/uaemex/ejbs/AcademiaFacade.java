/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.ejbs;

import fi.uaemex.entities.Academia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gibors
 */
@Stateless
public class AcademiaFacade extends AbstractFacade<Academia> {
    @PersistenceContext(unitName = "SIPLAFI-WEB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AcademiaFacade() {
        super(Academia.class);
    }
    
    public List<Academia> getAllAcademia()
    {
        List<Academia> listAcadem =  getEntityManager().createNamedQuery("Academia.findAll", Academia.class).getResultList();
        
        if(listAcadem != null)
            return listAcadem;
        else
            return null;
    }
    
}