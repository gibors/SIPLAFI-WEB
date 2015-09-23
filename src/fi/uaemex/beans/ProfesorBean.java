
package fi.uaemex.beans;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.Profesor;

@ManagedBean(name = "profesorBean",eager=true)
@ViewScoped
public class ProfesorBean implements Serializable
{

	private static final long serialVersionUID = 1L;
	private static final Logger logg = Logger.getLogger(ProfesorBean.class.getName());
    private DateFormat format = new SimpleDateFormat("HH:mm "); 	// Formato para el horario
    private Profesor profe;                         				// Profesor logged
    private String nombreProfe;                     				// nombre para mostrar del profesor
    private List<Grupo> gposProfe;                  				// obtiene la lista de los grupos del profesor
    private Grupo selectedGpo;										// Almacena el grupo que es seleccionado para editar el horario
    @EJB private ProfesorFacade profFacade;              			// EJB para acceso a datos del profesor
    @EJB private GrupoFacade gpoEJB;                     			// EJB para acceso a datos del grupo    
    @ManagedProperty(value = "#{login}") private LoginBean login;   // Propiedad para usar variables de session del bean de login
    
    @PostConstruct
    public void init()
    { // Se ejecuta antes de construir el objeto (TOP)
        profe = login.getProfe();
        gposProfe = new ArrayList<>();
        gposProfe = profe.getGrupoList();      
        nombreProfe = profe.getNombreProfe() + " " + profe.getApePatProfe() + " " + profe.getApeMatProfe();
    } // Se ejecuta antes de construir el objeto (BOTTOM)
        
    public void onGrupoSelected(Grupo gs)
    {
    	this.selectedGpo = gs;
    	RequestContext.getCurrentInstance().execute("PF('dlgModHora').show()");
    	
    	System.out.println(">>> se selecciono un grupo ..." + selectedGpo.getNombre());
    	
    }
    
    public String cerrarSession()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "index?faces-redirect=true";
    }

	public DateFormat getFormat() {
		return format;
	}

	public void setFormat(DateFormat format) {
		this.format = format;
	}

	public Profesor getProfe() {
		return profe;
	}

	public void setProfe(Profesor profe) {
		this.profe = profe;
	}

	public String getNombreProfe() {
		return nombreProfe;
	}

	public void setNombreProfe(String nombreProfe) {
		this.nombreProfe = nombreProfe;
	}

	public List<Grupo> getGposProfe() {
		return gposProfe;
	}

	public void setGposProfe(List<Grupo> gposProfe) 
	{
		this.gposProfe = gposProfe;
	}

	public void setLogin(LoginBean login) 
	{
		this.login = login;
	}

	public Grupo getSelectedGpo() {
		return selectedGpo;
	}

	public void setSelectedGpo(Grupo selectedGpo) {
		this.selectedGpo = selectedGpo;
	}
        
	
  
}
