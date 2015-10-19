
package fi.uaemex.beans;

import java.util.logging.Logger;
import fi.uaemex.ejbs.HorarioFacade;
import fi.uaemex.ejbs.PeriodosFacade;
import fi.uaemex.entities.Horario;
import fi.uaemex.entities.Periodos;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private List<Horario> listHorario;
    private DateFormat fmt = new SimpleDateFormat("HH:mm");
    @EJB
    private HorarioFacade horarioEJB;
    @EJB
    private PeriodosFacade periodoEJB;
    private String periodo;
    private List<Periodos> listPeriodos;
    
    @PostConstruct
    public void init()
    {
        try
        {
        	listPeriodos = periodoEJB.findAll(); 
            listHorario = horarioEJB.findAll();            
        }
        catch(EJBException exEJB)
        {                
            logg.log(Level.SEVERE, "Ocurrió un error al obtener los datos de los horarios o los periodos" + exEJB.toString());
        }      
    }
    public ReportePlanillaBean() 
    {
        
    }

    public List<Horario> getListHorario() 
    {
        return listHorario;
    }

    public void setListHorario(List<Horario> listHorario) 
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
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public List<Periodos> getListPeriodos() {
		return listPeriodos;
	}
	public void setListPeriodos(List<Periodos> listPeriodos) {
		this.listPeriodos = listPeriodos;
	}
    
    
       
}
