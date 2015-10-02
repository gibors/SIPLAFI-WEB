/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.beans;

import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class ProfesoUDBean implements Serializable{

    private static final Logger log = Logger.getLogger(ProfesoUDBean.class.getName());
    
    private List<Profesor> listaProfs;
    
    @EJB
    private ProfesorFacade profEJB;
    
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String grado;
    private Profesor profeSelected;
    private List<Profesor> filteredProfesor;
    @PostConstruct
    public void init()
    {
        listaProfs = new ArrayList<>();
        listaProfs = profEJB.getAllProfesores();
        
       // for(Profesor p: listaProfs)
        //log.log(Level.INFO,p.getNombreProfe() + " " + p.getApePatProfe() );
    }
    
    public ProfesoUDBean() 
    {
    }
    
    public String actualizaProfesor()
    {
        log.log(Level.INFO, " actualizado...");
        
        if(profeSelected == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Selecciona un Profesor",null));
            return "";
        }
        else
        {
            profEJB.edit(profeSelected);
            return "profesorUpDel?faces-redirect=true";
        }
       
    }
    
    public void deleteProfesor()
    {
        profEJB.remove(profeSelected);
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
            RequestContext.getCurrentInstance().execute("PF('profDiag').show()");
        }
    }
    
    public void nuevoProfesor()
    {        
        RequestContext.getCurrentInstance().openDialog("altaProfesor.xhtml");
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

    public String getGrado() 
    {
        return grado;
    }

    public void setGrado(String grado) 
    {
        this.grado = grado;
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

}
