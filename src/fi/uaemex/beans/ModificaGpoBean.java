
package fi.uaemex.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import fi.uaemex.ejbs.AulaFacade;
import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.entities.Aula;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.Horario;

@ManagedBean
@ViewScoped

public class ModificaGpoBean implements  Serializable
{
    @ManagedProperty(value = "#{profesorBean}")
    private ProfesorBean profBean;
    @EJB private GrupoFacade gpoEJB;    
    @EJB private AulaFacade aulaEJB;
    private List<Aula> listAula;
    private DateFormat fmt = new SimpleDateFormat("HH:mm");
    private Grupo gpoSelected ;
    private List<Grupo> listGposInSemester;
    
    @PostConstruct
    public void init()
    {        
        gpoSelected = profBean.getSelectedGpo();
        
        listAula = aulaEJB.findAll();
        listGposInSemester = gpoEJB.findGrupoSemestre(gpoSelected.getClaveMateria().getSemestre(),gpoSelected.getIdGrupo());
    }

    public ModificaGpoBean()
    {      
       // RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Procure evitar traslapes" ));
    }   

    public List<Aula> getListAula() {
        return listAula;
    }

    public void setListAula(List<Aula> listAula) {
        this.listAula = listAula;
    }
    
    public void setProfBean(ProfesorBean profBean)
    {
        this.profBean = profBean;
    }
        
    public Grupo getGpoSelected() {
        return gpoSelected;
    }

    public void setGpoSelected(Grupo gpoSelected) 
    {
        this.gpoSelected = gpoSelected;
    }

    public List<Grupo> getListGposInSemester() {
        return listGposInSemester;
    }

    public void setListGposInSemester(List<Grupo> listGposInSemester) {
        this.listGposInSemester = listGposInSemester;
    }

    public DateFormat getFmt() {
        return fmt;
    }

    public void setFm(DateFormat fm) {
        this.fmt = fmt;
    }

  
    
    
}