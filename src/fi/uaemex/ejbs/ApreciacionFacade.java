package fi.uaemex.ejbs;

import fi.uaemex.entities.Apreciacion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ApreciacionFacade extends AbstractFacade<Apreciacion> {
    @PersistenceContext(unitName = "SIPLAFI-WEB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() 
    {
        return em;
    }

    public ApreciacionFacade() 
    {
        super(Apreciacion.class);
    }
    
    
}
