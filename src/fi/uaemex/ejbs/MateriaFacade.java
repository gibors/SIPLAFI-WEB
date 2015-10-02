/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.ejbs;

import fi.uaemex.entities.Materia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author gibors
 */
@Stateless
public class MateriaFacade extends AbstractFacade<Materia> {
    @PersistenceContext(unitName = "SIPLAFI-WEB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MateriaFacade() {
        super(Materia.class);
    }
 
    public List<Materia> getAllMaterias()
    {
        List<Materia> listMate =  getEntityManager().createNamedQuery("Materia.findAll", Materia.class).getResultList();
        
        if(listMate != null)
            return listMate;
        else
            return null;
    }
}
