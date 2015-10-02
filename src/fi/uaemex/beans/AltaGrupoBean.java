
package fi.uaemex.beans;

import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.MateriaFacade;
import fi.uaemex.ejbs.PeriodosFacade;
import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.Materia;
import fi.uaemex.entities.Periodos;
import fi.uaemex.entities.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.PersistenceException;

@ManagedBean(name = "altaGrupoBean",eager = true)
@RequestScoped
public class AltaGrupoBean implements Serializable
{
    private static final Logger log = Logger.getLogger(AltaGrupoBean.class.getName());
    private Materia materia;
    private Profesor profesor;
    private String nombreGrupo;
    private String periodo;
    private int validado;
    private List<Profesor> profesorList;
    private List<Materia> materiasList;
    private List<Periodos> periodList;
    @EJB
    private ProfesorFacade profEJB;
    @EJB
    private MateriaFacade matEJB;
    @EJB
    private GrupoFacade grupoEJB;
    @EJB 
    private PeriodosFacade periodoEJB;
    
    public AltaGrupoBean()
    {        
        
    }
    
    @PostConstruct
    public void init()
    { // Se inicia antes de construir el objeto del bean (TOP)
        try
        {
            profesorList = profEJB.getAllProfesores();
            materiasList = matEJB.getAllMaterias();
            periodList = periodoEJB.findAll();
        }
        catch(PersistenceException exP)
        {
            log.log(Level.SEVERE,"Ocurrió un error al obtener a los profesor y las materias para el grupo");
        }
    } // Se inicia antes de construir el objeto del bean (BOTTOM)
    
    public List<Materia> getMateriaList(String query)
    {
        List<Materia> listMat = new ArrayList<>();
        for(Materia mat:materiasList)
        {
            if(mat.getNombreMateria().toLowerCase().startsWith(query))
                listMat.add(mat);
        }
        return listMat;
    }
    
    public List<Profesor> getProfesorList(String que)
    {
        List<Profesor> listProf = new ArrayList<>();
        for(Profesor p: profesorList)
        {
            if(p.getNombreProfe().trim().toLowerCase().startsWith(que))
                listProf.add(p);
        }        
        return listProf;
    }

    public String registrarGrupo()
    {
        Grupo group = new Grupo();
        
        group.setClaveMateria(materia);
        group.setRfcProfesor(profesor);
        group.setNombre(nombreGrupo.trim().toUpperCase());
        group.setPeriodo(periodo);
        
        try
        {
            grupoEJB.create(group);
            return "coordinador?faces-redirect=true";
        }
        catch(PersistenceException exP)
        {
            log.log(Level.SEVERE,"Ocurrió un error al insertar el grupo");            
            return null;
        }
        //log.log(Level.INFO, "mat: {0}, prof: {1}", new Object[]{group.getClaveMateria().getNombreMateria(), group.getRfcProfesor().getNombreProfe()});
        //return "";
    }

    public Materia getMateria()
    {
        return materia;
    }

    public void setMateria(Materia materia) 
    {
        this.materia = materia;
    }

    public Profesor getProfesor() 
    {
        return profesor;
    }

    public void setProfesor(Profesor profesor) 
    {
        this.profesor = profesor;
    }

    public String getNombreGrupo() 
    {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) 
    {
        this.nombreGrupo = nombreGrupo;
    }

    public int getValidado() 
    {
        return validado;
    }

    public void setValidado(int validado) 
    {
        this.validado = validado;
    }

    public List<Profesor> getProfesorList() 
    {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) 
    {
        this.profesorList = profesorList;
    }

    public List<Materia> getMateriasList() 
    {
        return materiasList;
    }

    public void setMateriasList(List<Materia> materiasList) 
    {
        this.materiasList = materiasList;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public List<Periodos> getPeriodList() {
        return periodList;
    }

    public void setPeriodList(List<Periodos> periodList) {
        this.periodList = periodList;
    }
    
    
     
}
