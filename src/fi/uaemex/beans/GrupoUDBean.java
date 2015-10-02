
package fi.uaemex.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import org.primefaces.context.RequestContext;

import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.MateriaFacade;
import fi.uaemex.ejbs.PeriodosFacade;
import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.Materia;
import fi.uaemex.entities.Periodos;
import fi.uaemex.entities.Profesor;

@ManagedBean
@ViewScoped
public class GrupoUDBean implements Serializable{
   
    private static final Logger logger = Logger.getLogger(GrupoUDBean.class.getName());
    private Grupo grupoSelected ;
    private List<Grupo> grupoFiltered;
    private List<Profesor> profeList;
    private List<Materia> mateList;
    private List<Grupo> grupoList;
    private List<Periodos> periodList;
    @EJB
    private MateriaFacade mateEJB;
    @EJB
    private ProfesorFacade profeEJB;
    @EJB
    private GrupoFacade grupoEJB;
    @EJB
    private PeriodosFacade periodoEJB;

    
    public GrupoUDBean()
    {
        
    }
    
    @PostConstruct
    public void init()
    {
        try
        {
            profeList = profeEJB.getAllProfesores();
            mateList = mateEJB.getAllMaterias();
            grupoList = grupoEJB.findAll();
            periodList = periodoEJB.findAll();
        }
        catch(EJBException exEJB)
        {
            logger.log(Level.INFO,"Ocurrió un error al obtener los datos de las materias y los profesor " + exEJB.toString());
        }
    }
    
    public void validaSeleccion(String opcion)
    {
        if(grupoSelected == null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Seleccione un grupo",null));
        }
        else
        {
            if(opcion.equals("update"))
                RequestContext.getCurrentInstance().execute("PF('gpoDiag').show()");
            else
                RequestContext.getCurrentInstance().execute("PF('gpoDiag2').show()");
        }
    }
    public List<Materia> getMateriaList(String query)
    {
        List<Materia> listMat = new ArrayList<>();
        for(Materia mat:mateList)
        {
            if(mat.getNombreMateria().toLowerCase().startsWith(query))
                listMat.add(mat);
        }
        return listMat;
    }
    
    public List<Profesor> getProfesorList(String que)
    {
        List<Profesor> listProf = new ArrayList<>();
        for(Profesor p: profeList)
        {
            if(p.getNombreProfe().trim().toLowerCase().startsWith(que))
                listProf.add(p);
        }        
        return listProf;
    }    
    public String actualizarGrupo()
    {
        try
        {
            grupoEJB.edit(grupoSelected);
        }
        catch(PersistenceException exJPA)
        {
            logger.log(Level.WARNING,"Ocurrió un error al actualizar el grupo " + exJPA.toString());
        }
        return "grupoUpDel?faces-redirect=true";
    }
    
    public String eliminarGrupo()
    {
        try
        {
            grupoEJB.remove(grupoSelected);
        }
        catch(PersistenceException exJPA)
        {
            logger.log(Level.WARNING,"Ocurrió un error al eliminar el grupo " + exJPA.toString());
        }
        return "grupoUpDel";
    }

    public Grupo getGrupoSelected() 
    {
        return grupoSelected;
    }

    public void setGrupoSelected(Grupo grupoSelected) 
    {
        this.grupoSelected = grupoSelected;
    }

    public List<Profesor> getProfeList() {
        return profeList;
    }

    public void setProfeList(List<Profesor> profeList) {
        this.profeList = profeList;
    }

    public List<Materia> getMateList() {
        return mateList;
    }

    public void setMateList(List<Materia> mateList) {
        this.mateList = mateList;
    }

    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    public List<Grupo> getGrupoFiltered() {
        return grupoFiltered;
    }

    public void setGrupoFiltered(List<Grupo> grupoFiltered) {
        this.grupoFiltered = grupoFiltered;
    }

    public List<Periodos> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<Periodos> periodList) {
        this.periodList = periodList;
    }
    
    
    
}

