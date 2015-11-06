
package fi.uaemex.ejbs;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.uaemex.entities.Apreciacion;
import fi.uaemex.entities.Profesor;

@Stateless
public class ProfesorFacade extends AbstractFacade<Profesor> 
{ // EJB stateless para realizar operaciones an nivel de modelo para la entidad Profesor (TOP)
	
	private static final Logger logger = LoggerFactory.getLogger(ProfesorFacade.class);
	@PersistenceContext(unitName = "SIPLAFI-WEB") 	// Contexto de persistencia
    private EntityManager em;						// interfaz de JPA para acceder y realizar operaciones en la capa de modelo 				
	
    @Override
    protected EntityManager getEntityManager() 
    {
        return em;
    }

    public ProfesorFacade() 
    {
        super(Profesor.class);
    }
    
    public Profesor findUser(String user, String pass)
    { // Metodo de negocio para buscar al usuario en base de datos (TOP)
        try
        {
        	Profesor profe = (Profesor)getEntityManager().createNamedQuery("findUser").setParameter("rfc", user)
            .setParameter("pass", pass ).getSingleResult();
        
        	return profe;
        }
        catch(Exception ex)
        {
        	logger.error(">>>> Error al buscar profesor login " + ex.toString());
            return null;
        }
    } // Metodo de negocio para buscar al usuario en base de datos (TOP)
    
    public boolean setCorreo(Profesor p)
    { // Guarda el correo del profesor en la base de datos (TOP)
        try
        {
            em.merge(p);
            return true;
        }
        catch(Exception ex)
        {
        	logger.error("Error al actualizar el correo del profesor " + ex.toString());
            return false;
        }
    } // Guarda el correo del profesor en la base de datos (BOTTOM)
    
    public List<Profesor> getAllProfesores()
    { // Obtiene la lista de todos los profesores (TOP)
        List<Profesor> listProf = getEntityManager().createNamedQuery("Profesor.findAll", Profesor.class).getResultList();
        
        if(listProf!= null || listProf.size() == 0)
            return  listProf;
        else 
            return null;
    } // Obtiene la lista de todos los profesores (BOTTOM)
    
    public List<Profesor> getAllProfesoresCurrent()
    {
    	List<Profesor> listaProf = getEntityManager().createNamedQuery("Profesor.findAllCurr").getResultList();
    	//List<Apreciacion> listaApreciacion = getEntityManager().createQuery("SELECT a FROM Apreciacion WHERE a.actual = 1").getResultList();
    	if(listaProf != null)
    	{    		
    		return listaProf;
    	}
    	else
    		return null;
    }
    
    
} // EJB stateless para realizar operaciones an nivel de modelo para la entidad Profesor (TOP)
