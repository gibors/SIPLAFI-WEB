package fi.uaemex.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.PeriodosFacade;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.Periodos;

@ManagedBean(name="periodBean")
public class PeriodosBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String periodo;
	private String descripcionPeriodo;
	private Periodos periodoActual;
	private List<Grupo> listGruposProxPeriod;
	private boolean asignarNuevo;
	@EJB private PeriodosFacade periodoEJB; 
	@EJB private GrupoFacade grupoEJB;
	
	public PeriodosBean() 
	{
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init()
	{
		periodoActual = periodoEJB.getPeriodoActual();
		System.out.println(">>>periodo : " + periodoActual.getPeriodo());
	}
		
	public void seleccionarGruposNuevoPeriodo()
	{
		listGruposProxPeriod = grupoEJB.findAll();
		asignarNuevo = true;
	}
	
	public String altaNuevoPeriodo()
	{
		if(periodoEJB.find(periodo) == null)
		{
			periodoEJB.create(new Periodos(periodo,descripcionPeriodo,"1"));
		}
		return null;
	}	
			
	public List<Grupo> getListGruposProxPeriod() {
		return listGruposProxPeriod;
	}

	public void setListGruposProxPeriod(List<Grupo> listGruposProxPeriod) {
		this.listGruposProxPeriod = listGruposProxPeriod;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodos) {
		periodo = periodos;
	}

	public String getDescripcionPeriodo() {
		return descripcionPeriodo;
	}

	public void setDescripcionPeriodo(String descripcionPeriodo) {
		this.descripcionPeriodo = descripcionPeriodo;
	}

	public Periodos getPeriodoActual() {
		return periodoActual;
	}

	public void setPeriodoActual(Periodos periodoActual) {
		this.periodoActual = periodoActual;
	}

	public boolean isAsignarNuevo() {
		return asignarNuevo;
	}

	public void setAsignarNuevo(boolean asignarNuevo) {
		this.asignarNuevo = asignarNuevo;
	}
	
}
