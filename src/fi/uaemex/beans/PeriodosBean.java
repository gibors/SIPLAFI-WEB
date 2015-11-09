package fi.uaemex.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import fi.uaemex.ejbs.PeriodosFacade;
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
	@EJB PeriodosFacade periodoEJB; 
	
	public PeriodosBean() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public String altaNuevoPeriodo()
	{
		if(periodoEJB.find(periodo) == null)
		{
			periodoEJB.create(new Periodos(periodo,descripcionPeriodo));
		}
		return null;
	}
	
	@PostConstruct
	public void init()
	{
		periodoActual = periodoEJB.getPeriodoActual();
		System.out.println(">>>periodo : " + periodoActual.getPeriodo());
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
	
	
	
}
