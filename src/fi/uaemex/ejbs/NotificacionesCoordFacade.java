/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  fi.uaemex.ejbs;

import  fi.uaemex.entities.NotificacionesCoord;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author IEEM
 */
@Stateless
public class NotificacionesCoordFacade extends AbstractFacade<NotificacionesCoord> {
    @PersistenceContext(unitName = "SIPLAFI-WEB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificacionesCoordFacade() {
        super(NotificacionesCoord.class);
    }
    
    public List<NotificacionesCoord> findNewNotif()
    {
        String query = "SELECT n FROM NotificacionesCoord n WHERE n.fechaHoraValida IS NULL ORDER BY n.notificacionesCoordPK.fechaHoraNotif";
        
        Query q = getEntityManager().createQuery(query);
        
        return q.getResultList();
        
    }
    
    public List<NotificacionesCoord> getHistorialNotificaciones()
    {
        String query = "SELECT n FROM NotificacionesCoord n WHERE n.fechaHoraValida IS NOT NULL ORDER BY n.notificacionesCoordPK.fechaHoraNotif";
    	Query q = getEntityManager().createQuery(query);
    	return  q.getResultList();
    }
    
    public void newNotificacion(NotificacionesCoord coord)    
    {
    	try
    	{
    		System.out.println(">>>>>> ENTRO AL METODO NEW NOTIFICACION");
    		if(coord != null)
    			getEntityManager().persist(coord);
    	}
    	catch(PersistenceException jpEx)
    	{
    		jpEx.printStackTrace();
    		System.out.println(">>>>>>> Ocurrio un error al crear la notificacion : " + jpEx.toString());
    	}
    }
}
