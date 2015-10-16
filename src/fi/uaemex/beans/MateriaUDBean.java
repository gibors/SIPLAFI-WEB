package fi.uaemex.beans;

import fi.uaemex.ejbs.AcademiaFacade;
import fi.uaemex.ejbs.MateriaFacade;
import fi.uaemex.entities.Academia;
import fi.uaemex.entities.Materia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(eager = true)
@ViewScoped
public class MateriaUDBean implements Serializable
{    

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(MateriaUDBean.class);	// Logger para mostrar información 
    private String nombreMateria;													// Nombre de la materia nueva
    private String claveMateria;													// Clave de la materia
    private Integer horas;															// Horas a la semana de la materia
    private Integer semestre;														// En que semestre se impartira la materia
    private List<Materia> matList;													// Lista de todas las materias en la actualidad
    private Materia matSelected;													// Materia seleccinada para editar o eliminar
    private List<Materia> filteredMat;												// Lista para Guardar el filtro de de materias en la busqueda
    private List<Academia> academiaList;											// Lista del area academica de la materia
    private Academia academia;														// Academia seleccionada para el grupo
    @EJB private MateriaFacade matEJB;												// EJB para acceso a datos de la materia
    @EJB private AcademiaFacade academyEJB;											// EJB para acceso a datos de la entidad academia
    
    @PostConstruct
    public void init()
    { // Se ejecuta antes de crear el bean (TOP)
        try
        { // Obtiene todas las materias desde el EJB (TOP)
            matList =  matEJB.findAll();
            academiaList = academyEJB.findAll();
        } // Obtiene todas las materias desde el EJB (BOTTOM)
        catch(EJBException exEJB)
        { // Si falla el EJB (TOP)
            log.warn("Hubó un error al obtener los datos desde el EJB: " + exEJB.toString());
        } // Si falla el EJB (BOTTOM)
    } // Se ejecuta antes de crear el bean (BOTTOM)
    
    public MateriaUDBean()
    {     	
    }
       
    public String registrarMateria()
    {        
        Materia materia = new Materia();
        materia.setClaveMateria(claveMateria.trim().toUpperCase());
        materia.setNombreMateria(nombreMateria.trim().toUpperCase());
        materia.setSemestre(semestre);
        materia.setHoras(horas);
        materia.setIdAcademia(academia);       
        
        try
        { // Try para crear la nueva materia (TOP)
        	if(matEJB.find(materia.getClaveMateria()) == null)
        	{ // Si la clave de materia no existe en la base de datos se crea la nueva materia (TOP)
            	matEJB.create(materia);
        	} // Si la clave de materia no existe en la base de datos se crea la nueva materia (BOTTOM)
        	else
        	{
        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La clave de la materia ya existe, ingrese una nueva clave"));
        		return "";
        	}
        } // Try para crear la nueva materia (BOTTOM) 
        catch(PersistenceException ex)
        {        	
            log.warn("Ocurrió un error al obtener la academia o al insertar la nueva materia: " +  ex.toString());
        }
        return "materiaUpDel?faces-redirect=true";            	
    }  
    
    public void validaSeleccion(String opcion)
    {
        if(matSelected == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Seleccione una Materia",null));
        }
        else
        {
            if(opcion.equals("update"))
                RequestContext.getCurrentInstance().execute("PF('matDiag').show()");
            else
                RequestContext.getCurrentInstance().execute("PF('matDiag2').show()");
        }
    }
    
    public void onItemSelect(SelectEvent event)
    {
        if(event.getObject() != null)
        {
            System.out.println("selecctionaste "  + ((Academia)event.getObject()).getNombre());
            //this.matSelected.setIdAcademia((Academia)event.getObject());
            System.out.println("academia nueva " + matSelected.getIdAcademia().getNombre());            
        }
        else
            System.out.println("No seleccionaste nada ");            
    }
    
    public List<Academia> getAcademy(String query)
    { // Obtiene la lista de los datos filtrados con el query (TOP)
       Collections.sort(academiaList);
        List<Academia> res = new ArrayList<>();
        for(Academia a : academiaList)
        {
            if(a.getNombre().toLowerCase().startsWith(query))
                res.add(a);
        }
        return res;
    } // Obtiene la lista de los datos filtrados con el query (BOTTOM)
        
    public String updateMateria()
    {
        
       log.info(">>> actualizado...");
        
        if(matSelected == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Selecciona una Materia",null));
        }
        else
        {
            try
            { // Try - para actualizar la materia seleccionada (TOP)
                matEJB.edit(matSelected);
            } // Try - para actualizar la materia seleccionada (BOTTOM)
            catch(PersistenceException exPrs)
            { // Si hay error en el update(TOP)
                log.info("Error al actualizar la materia" + exPrs.toString());
            } // Si hay error en el update(BOTTOM)
        }
        return "materiaUpDel?faces-redirect=true";
    }
    
    public String deleteMateria()
    {
        try
        { // Try - para eliminar la materia seleccionada (TOP)
            matEJB.remove(matSelected);
        } // Try - para eliminar la materia seleccionada (BOTTOM)
        catch(PersistenceException exPrs)
        { // Si hay error en el delete(TOP)
            log.info("Error al actualizar la materia" + exPrs.toString());
        } // Si hay error en el delete(BOTTOM)
       return "materiaUpDel?faces-redirect=true";
    }

    public List<Materia> getMatList() 
    {
        return matList;
    }

    public void setMatList(List<Materia> matList) 
    {
        this.matList = matList;
    }

    public Materia getMatSelected()
    {
        return matSelected;
    }

    public void setMatSelected(Materia matSelected) 
    {
        this.matSelected = matSelected;
    }

    public List<Materia> getFilteredMat() 
    {
        return filteredMat;
    }

    public void setFilteredMat(List<Materia> filteredMat) 
    {
        this.filteredMat = filteredMat;
    }           

    public List<Academia> getAcademiaList() 
    {
        return academiaList;
    }

    public void setAcademiaList(List<Academia> academiaList) 
    {
        this.academiaList = academiaList;
    }

    public Academia getAcademia() 
    {
        return academia;
    }

    public void setAcademia(Academia academia) 
    {
        this.academia = academia;
    }

	public String getNombreMateria() 
	{
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) 
	{
		this.nombreMateria = nombreMateria;
	}

	public String getClaveMateria() 
	{
		return claveMateria;
	}

	public void setClaveMateria(String claveMateria) 
	{
		this.claveMateria = claveMateria;
	}

	public Integer getHoras() 
	{
		return horas;
	}

	public void setHoras(Integer horas) 
	{
		this.horas = horas;
	}

	public Integer getSemestre() 
	{
		return semestre;
	}

	public void setSemestre(Integer semestre) 
	{
		this.semestre = semestre;
	}
       
    
}