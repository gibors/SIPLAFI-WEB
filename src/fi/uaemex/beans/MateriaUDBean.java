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
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean(eager = true)
@ViewScoped
public class MateriaUDBean implements Serializable
{    
    private static final Logger log = Logger.getLogger(MateriaUDBean.class.getName());
    private List<Materia> matList;
    private Materia matSelected;
    private List<Materia> filteredMat;
    private List<Academia> academiaList;
    private Academia academia;
    @EJB
    private MateriaFacade matEJB;
    @EJB
    private AcademiaFacade academyEJB;
    
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
            log.log(Logger.Level.ERROR, "Hubó un error al obtener los datos desde el EJB: " + exEJB.toString());
        } // Si falla el EJB (BOTTOM)
    } // Se ejecuta antes de crear el bean (BOTTOM)
    
    public MateriaUDBean()
    {        
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
        
       log.log(Logger.Level.INFO, " actualizado...");
        
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
                log.log(Logger.Level.ERROR, "Error al actualizar la materia" + exPrs.toString());
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
            log.log(Logger.Level.ERROR, "Error al actualizar la materia" + exPrs.toString());
        } // Si hay error en el delete(BOTTOM)
       return "materiaUpDel";
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

    public Academia getAcademia() {
        return academia;
    }

    public void setAcademia(Academia academia) {
        this.academia = academia;
    }
       
    
}