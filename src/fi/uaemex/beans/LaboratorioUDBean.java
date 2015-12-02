
package fi.uaemex.beans;

import fi.uaemex.ejbs.AulaFacade;
import fi.uaemex.entities.Aula;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class LaboratorioUDBean {
    
    private static final Logger logg = Logger.getLogger(LaboratorioUDBean.class.getName());
    @EJB
    private AulaFacade aulaEJB;
    private String nombreLaboratorio;
    private String siglas;
    private List<Aula> listaAula;
    private Aula selectedAula;
    private List<Aula> aulaFilteredList;
    
    @PostConstruct
    public void init()
    {
    	listaAula = new ArrayList<>();
    	listaAula = aulaEJB.findAll();
    }
    
    public LaboratorioUDBean()
    {        
    }
    
    public void validaSeleccion(String opcion)
    {
        if(selectedAula == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Seleccione un laboratorio",null));
        }
        else
        {
        	logg.info("Selected aula >>>>> " + selectedAula.getNombre());
            if(opcion.equals("update"))
                RequestContext.getCurrentInstance().execute("PF('labDiag').show()");
            else
                RequestContext.getCurrentInstance().execute("PF('labDiag2').show()");
        }
    }    
    
    public String registrarLab()
    {
        Aula aula = new Aula();
        aula.setNombre(nombreLaboratorio.toUpperCase());
        aula.setTipoAula(siglas.toUpperCase());
        try
        {
            aulaEJB.create(aula);
            return "laboratoriosUpDel?faces-redirect=true";
        }
        catch(PersistenceException exP)
        {
            logg.log(Level.SEVERE, "Error al insertar el nuevo laboratorio");
        }
     return null;
    }
    
    public String editarLab()
    {    	
    	try
    	{    		  
    		logg.info(">>> aqui entro bien.. ");
    		aulaEJB.edit(selectedAula);
    		logg.info(">>> termino proceso.. ");
    		
            return "laboratoriosUpDel?faces-redirect=true";    		
    	}
    	catch(PersistenceException exP)
    	{
    		logg.log(Level.WARNING,"ocurrio un error al eidtar el laboratorio " + exP.toString());
    	}
    	return null;
    }
    
    public String deleteLab()
    {
    	try
    	{
        	aulaEJB.remove(selectedAula);
            return "laboratoriosUpDel?faces-redirect=true";    		
    	}
    	catch(PersistenceException exP)
    	{
    		logg.log(Level.WARNING,"ocurrio un error al editar el laboratorio " + exP.toString());
    	}
    	return null;    }

    public String getNombreLaboratorio() 
    {
        return nombreLaboratorio;
    }

    public void setNombreLaboratorio(String nombreLaboratorio) 
    {
        this.nombreLaboratorio = nombreLaboratorio;
    }

    public String getSiglas() 
    {
        return siglas;
    }

    public void setSiglas(String siglas) 
    {
        this.siglas = siglas;
    }

	public List<Aula> getListaAula() {
		return listaAula;
	}

	public void setListaAula(List<Aula> listaAula) {
		this.listaAula = listaAula;
	}

	public Aula getSelectedAula() {
		return selectedAula;
	}

	public void setSelectedAula(Aula selectedAula) 
	{
		this.selectedAula = selectedAula;
	}

	public List<Aula> getAulaFilteredList() 
	{
		return aulaFilteredList;
	}

	public void setAulaFilteredList(List<Aula> aulaFilteredList) 
	{
		this.aulaFilteredList = aulaFilteredList;
	}        
		        
}