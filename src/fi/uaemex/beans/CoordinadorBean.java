
package fi.uaemex.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.uaemex.ejbs.CoordinadorFacade;
import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.NotificacionesCoordFacade;
import fi.uaemex.entities.Coordinador;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.NotificacionesCoord;

@ManagedBean(name="coord")
@ViewScoped
public class CoordinadorBean implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private static final Logger logg = LoggerFactory.getLogger(CoordinadorBean.class);
	//@ManagedProperty(value = "#{login}") private LoginBean login;	// Propiedad para usar el bean de session login  
    private List<NotificacionesCoord> listNotCoord;					// Lista que guarda las notificaciones para los diversos grupos que necesiten ser validados
    private NotificacionesCoord notifSelected;						// Entidad para las  notificaciones para el coordinador 
    private Grupo selecteGpo;										// Grupo que se selecciona para validacion 
    private Coordinador coordinador;								// Entidad para el coordinador
    private List<Grupo> listGposAValidar;							// Lista con todos los grupos con notificacoin para validacion.
    private DateFormat fmt = new SimpleDateFormat("HH:mm");			// Formato de fecha/hora para mostrar el horario
    @EJB private CoordinadorFacade coorFac;							// EJB para acceso a datos del coordinador 
    @EJB private NotificacionesCoordFacade notifCoordEJB;			// EJB para acceso a datos de las notificaciones para el coordinador
    @EJB private GrupoFacade gpoEJB;    							// EJB para acceso a datos del grupo   
    
    @PostConstruct
    public void init()
    {
        //coordinador = login.getCoord();
        coordinador = coorFac.findUser("QH5Q0S7NYHJTM40", "QH5Q0S7NYHJTM40");
        listNotCoord = notifCoordEJB.findNewNotif();
        listGposAValidar = new ArrayList<>();
        for(NotificacionesCoord nt: listNotCoord)
        { // Por cada notificacion se agrega a la lista de grupo con notificaciones (TOP)           
           Grupo g = gpoEJB.find(nt.getNotificacionesCoordPK().getIdGrupo());
           g.setDescripcion(nt.getDescripcion());
           listGposAValidar.add(g);
        }  // Por cada notificacion se agrega a la lista de grupo con notificaciones (BOTTOM)
        logg.info(">>> Grupos a validar : " + listGposAValidar.size() + " -- " + listGposAValidar.get(0).toString());
    }
    
    public CoordinadorBean() 
    {
    }
    
    public void validarGrupo()
    {
        logg.info("SELECTED GRUPO " + selecteGpo.getNombre());
        
        for(NotificacionesCoord nt:listNotCoord)
        { // Buscamos la seleccion de notificacion (TOP)
            if(selecteGpo.getIdGrupo() == nt.getNotificacionesCoordPK().getIdGrupo())
            {
                notifSelected = nt;
                logg.info("notificacion encontrada.. " + notifSelected.getDescripcion());
            }// Buscamos la seleccion de notificacion (TOP)
        }
        
    	RequestContext.getCurrentInstance().execute("PF('dlgValida').show()");
    }

//    public LoginBean getLogin()
//    {
//        return login;
//    }
//
//    public void setLogin(LoginBean login)
//        this.login = login;
//    }

    public List<NotificacionesCoord> getListNotCoord()
    {
        return listNotCoord;
    }

    public void setListNotCoord(List<NotificacionesCoord> listNotCoord)
    {
        this.listNotCoord = listNotCoord;
    }

    public Grupo getSelecteGpo()
    {
        return selecteGpo;
    }

    public void setSelecteGpo(Grupo selecteGpo) 
    {
        this.selecteGpo = selecteGpo;
    }
    
    
    public List<Grupo> getListGposAValidar() 
    {
        return listGposAValidar;
    }

    public void setListGposAValidar(List<Grupo> listGposAValidar) 
    {
        this.listGposAValidar = listGposAValidar;
    }

    public NotificacionesCoord getNotifSelected() 
    {
        return notifSelected;
    }

    public void setNotifSelected(NotificacionesCoord notifSelected) 
    {
        this.notifSelected = notifSelected;
    }

	public Coordinador getCoordinador() 
	{
		return coordinador;
	}

	public void setCoordinador(Coordinador coordinador) 
	{
		this.coordinador = coordinador;
	}

	public DateFormat getFmt() {
		return fmt;
	}

	public void setFmt(DateFormat fmt) {
		this.fmt = fmt;
	}
    
	
}
