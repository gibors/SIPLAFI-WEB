
package fi.uaemex.beans;

import java.util.logging.Logger;

import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.entities.Grupo;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ReportePlanillaBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final Logger logg = Logger.getLogger(ReportePlanillaBean.class.getName());
    private List<Grupo> listHorario;
    private DateFormat fmt = new SimpleDateFormat("HH:mm");
    @EJB
    private GrupoFacade horarioEJB;
    
    @PostConstruct
    public void init()
    {
        try
        {
            listHorario = horarioEJB.findAll();            
        }
        catch(EJBException exEJB)
        {                
            logg.log(Level.SEVERE, "Ocurrió un error al obtener los datos de los horarios " + exEJB.toString());
        }      
    }
    public ReportePlanillaBean() 
    {
        
    }

    public List<Grupo> getListHorario() 
    {
        return listHorario;
    }

    public void setListHorario(List<Grupo> listHorario) 
    {
        this.listHorario = listHorario;
    }

    public DateFormat getFmt() 
    {
        return fmt;
    }

    public void setFmt(DateFormat fmt) 
    {
        this.fmt = fmt;
    }
       
}
