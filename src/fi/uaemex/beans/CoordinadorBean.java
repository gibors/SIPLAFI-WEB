
package fi.uaemex.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.sun.faces.context.flash.ELFlash;

import fi.uaemex.ejbs.CoordinadorFacade;
import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.NotificacionesCoordFacade;
import fi.uaemex.entities.Coordinador;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.NotificacionesCoord;

@ManagedBean
@ViewScoped
public class CoordinadorBean implements Serializable 
{
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{login}") private LoginBean login;	// Propiedad para usar el bean de session login  
    private List<NotificacionesCoord> listNotCoord;					// Lista que guarda las notificaciones para los diversos grupos que necesiten ser validados
    private NotificacionesCoord notifSelected;						// Entidad para las  notificaciones para el coordinador 
    private Grupo selecteGpo;										// Grupo que se selecciona para validacion 
    private Coordinador coordinador;								// Entidad para el coordinador
    private List<Grupo> listGposAValidar;							// Lista con todos los grupos con notificacoin para validacion.
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
    }
    
    public CoordinadorBean() 
    {               
    }
    
    public String validarGrupo()
    {
        System.out.println("SELECTED GRUPO " + selecteGpo.getNombre());
        
        for(NotificacionesCoord nt:listNotCoord)
        { // Buscamos la seleccion de notificacion (TOP)
            if(selecteGpo.getIdGrupo() == nt.getNotificacionesCoordPK().getIdGrupo())
            {
                notifSelected = nt;
                System.out.println("notificacion encontrada.. " + notifSelected.getDescripcion());
            }// Buscamos la seleccion de notificacion (TOP)
        }               
        //FacesContext.getCurrentInstance()
        ELFlash.getFlash().put("grupo", selecteGpo);
        return "validarHorario?faces-redirect=true";
    }

    public LoginBean getLogin()
    {
        return login;
    }

    public void setLogin(LoginBean login)
    {
        this.login = login;
    }

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
    
}
