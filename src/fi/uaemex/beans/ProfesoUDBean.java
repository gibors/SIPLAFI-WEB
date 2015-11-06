/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.beans;

import fi.uaemex.ejbs.ApreciacionFacade;
import fi.uaemex.ejbs.PeriodosFacade;
import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Apreciacion;
import fi.uaemex.entities.ApreciacionPK;
import fi.uaemex.entities.Periodos;
import fi.uaemex.entities.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class ProfesoUDBean implements Serializable{

	private static final long serialVersionUID = 1L;												// Serial version serializable
	private static final Logger LOGG = LoggerFactory.getLogger(ProfesoUDBean.class);				// LOGGER para mostrar informacion  
    private static final String EMAIL_PATTERN = "[A-Z0-9a-z._%-]+@[A-Z0-9a-z.-]+\\.[A-Za-z]{2,4}";	// Patron para validar el formato de correo
    private final Pattern pat;    																	// Variable para validar el patron	
    private String rfcProfesor;    																	// RFC del profesor
    private String nombre;																			// Nombre del profesor
    private String apellidoPaterno;																	// Apellido Paterno del profesor
    private String apellidoMaterno;																	// Apellido Materno del profesor
    private String gradoProfesor;																	// Grado de estudios del profesor
    private String emailProfesor;																	// Correo electronico del profesor
    private Matcher matcher;																		// Valida o hace match si el correo es valido
    private Double apreciacion;																		// Guarda la calificacion obtenida en la apreciacion estudiantil    
    private Profesor profeSelected;																	// Profesor que se selecciona para ser modificado o eliminado
    private Periodos periodo;
	private List<Profesor> filteredProfesor;														// Filtrado para busqueda del profesor 
    private List<Profesor> listaProfs;																// Lista de todos los profesores en la base de datos
    private Apreciacion apreciacionProfSelected;
    @EJB private ProfesorFacade profEJB;															// EJB para acceso a datos del profesor    
    @EJB private ApreciacionFacade apreciacionEJB;
    @EJB private PeriodosFacade periodoEJB;
    
    @PostConstruct
    public void init()
    {
        listaProfs = new ArrayList<>();
        //listaProfs = profEJB.getAllProfesores();
        periodo = periodoEJB.getPeriodoActual();
        listaProfs =profEJB.getAllProfesoresCurrent();
       // for(Profesor p: listaProfs)
        //log.log(Level.INFO,p.getNombreProfe() + " " + p.getApePatProfe() );
    }
    
    public ProfesoUDBean() 
    {
        pat = Pattern.compile(EMAIL_PATTERN);
    	
    }
    
    
    public String registrarProf()
    {
        LOGG.info(">>>> Registro del profesor");
        
        Profesor profe = new Profesor();
        profe.setRfcProfesor(rfcProfesor.trim().toUpperCase());
        profe.setGradoProfe(gradoProfesor.trim().toUpperCase());
        profe.setNombreProfe(nombre.trim().toUpperCase());
        profe.setApePatProfe(apellidoPaterno.trim().toUpperCase());
        profe.setApeMatProfe(apellidoMaterno.trim().toUpperCase());
        profe.setEmailProfe(emailProfesor.trim());
        profe.setPasswordProfe(rfcProfesor.trim().toUpperCase());
                
        Apreciacion apr = new Apreciacion();
        ApreciacionPK apk = new ApreciacionPK();
        apk.setRfcProfesor(rfcProfesor.trim().toUpperCase());
        apk.setPeriodo(periodo.getPeriodo());
        apr.setId(apk);
        
        if(apreciacion != null && (apreciacion >= 0 && apreciacion <= 10.0))
        {
        	apr.setCalificacion(apreciacion);
        }
        else if(apreciacion != null && (apreciacion < 0 || apreciacion > 10))
        {
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El valor que ingreso en la apreciación no es valido"));
        	return "";
        }
                        
        try
        {
        	profEJB.create(profe);
        	apreciacionEJB.create(apr);
        }
        catch(PersistenceException persistenceEx)
        {
        	LOGG.info(">> Ocurrio un error al registrar profesor " + persistenceEx.toString());
        }
        
        return "profesorUpDel?faces-redirect=true";
    }
    
    public String validateEmail()
    {
        matcher = pat.matcher(emailProfesor);
        LOGG.info(">>> st: " + emailProfesor);
        
        if(!matcher.find())
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de correo invalido", null));
        }
        else
        {
            LOGG.info(">>>si es valido");
            //return "";
        }
        return "";
    }    
    
    public String actualizaProfesor()
    {
    	LOGG.info(" actualizado...");
        
        if(profeSelected == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Selecciona un Profesor",null));
            return "";
        }
        else
        {
            profEJB.edit(profeSelected);
            apreciacionEJB.edit(apreciacionProfSelected);
            return "profesorUpDel?faces-redirect=true";
        }
       
    }
    
    public String deleteProfesor()
    {    	
        apreciacionEJB.remove(new Apreciacion(new ApreciacionPK(profeSelected.getRfcProfesor(),periodo.getPeriodo())));
        profEJB.remove(profeSelected);        
        return "profesorUpDel?faces-redirect=true";
    }
    
    public void validaSeleccion()
    {
        if(profeSelected == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Seleccione un Profesor",null));
        }
        else
        {
            RequestContext.getCurrentInstance().execute("PF('profDiag2').show()");
        }        
    }
    
    public void valida()
    {
        if(profeSelected == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Seleccione un Profesor",null));
        }
        else
        {
        	apreciacionProfSelected =apreciacionEJB.find(new ApreciacionPK(profeSelected.getRfcProfesor(),periodo.getPeriodo()));
            RequestContext.getCurrentInstance().execute("PF('profDiag').show()");
        }
    }
    
   
    public void eliminaProfesor()
    {
        profEJB.remove(profeSelected);
    }

    public List<Profesor> getListaProfs() 
    {
        return listaProfs;
    }

    public void setListaProfs(List<Profesor> listaProfs) 
    {
        this.listaProfs = listaProfs;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellidoPaterno()
    {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno)
    {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno()
    {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno)
    {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getGradoProfesor() 
    {
		return gradoProfesor;
	}

	public void setGradoProfesor(String gradoProfesor) 
	{
		this.gradoProfesor = gradoProfesor;
	}

	public Profesor getProfeSelected() 
    {
        return profeSelected;
    }

    public void setProfeSelected(Profesor profeSelected) 
    {
        this.profeSelected = profeSelected;
    }

    public List<Profesor> getFilteredProfesor() 
    {
        return filteredProfesor;
    }

    public void setFilteredProfesor(List<Profesor> filteredProfesor) 
    {
        this.filteredProfesor = filteredProfesor;
    }

	public String getRfcProfesor() {
		return rfcProfesor;
	}

	public void setRfcProfesor(String rfcProfesor) {
		this.rfcProfesor = rfcProfesor;
	}

	public String getEmailProfesor() {
		return emailProfesor;
	}

	public void setEmailProfesor(String emailProfesor) {
		this.emailProfesor = emailProfesor;
	}

	public Double getApreciacion() {
		return apreciacion;
	}

	public void setApreciacion(Double apreciacion) {
		this.apreciacion = apreciacion;
	}

    public Periodos getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodos periodo) {
		this.periodo = periodo;
	}

	public Apreciacion getApreciacionProfSelected() {
		return apreciacionProfSelected;
	}

	public void setApreciacionProfSelected(Apreciacion apreciacionProfSelected) {
		this.apreciacionProfSelected = apreciacionProfSelected;
	}
	
	
}
