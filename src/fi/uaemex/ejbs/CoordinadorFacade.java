
package fi.uaemex.ejbs;

import fi.uaemex.entities.Coordinador;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@Stateless
public class CoordinadorFacade extends AbstractFacade<Coordinador> {
    @PersistenceContext(unitName = "SIPLAFI-WEB")

    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() 
    {
        return em;
    }

    public CoordinadorFacade() 
    {
        super(Coordinador.class);
    }
    
    public Coordinador findUser(String user, String pass)
    {
        try
        {
        	Coordinador coor = (Coordinador)getEntityManager().createNamedQuery("findByRfcCoord").setParameter("rfcCoord", user)
            .setParameter("passwdCoord", pass ).getSingleResult();

            return coor;
        }
        catch(Exception ex)
        {
            System.out.println("error al buscar coordinador login " + ex.toString());
            return null;
        }
    }
}