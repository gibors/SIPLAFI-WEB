package fi.uaemex.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import fi.uaemex.ejbs.ApreciacionFacade;
import fi.uaemex.entities.Apreciacion;

@ManagedBean(name="aprecBean")
public class ApreciacionBean implements Serializable 
{

	private static final long serialVersionUID = 1L;
	private Apreciacion apreciacion;
	private List<Apreciacion> listApreciacion;
	private List<Apreciacion> filteredListApr;
	@EJB private ApreciacionFacade apreciacionEJB;
	
	public ApreciacionBean() 
	{
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init()
	{
		//listProfPeriodActual = profeEJB.getAllProfesoresCurrent();
		listApreciacion = apreciacionEJB.findAll();		
	}
	
	public String guardarApreciacion()
	{
		return "";
	}
		
	public Apreciacion getApreciacion() {
		return apreciacion;
	}

	public void setApreciacion(Apreciacion apreciacion) {
		this.apreciacion = apreciacion;
	}

	public List<Apreciacion> getListApreciacion() {
		return listApreciacion;
	}

	public void setListApreciacion(List<Apreciacion> listApreciacion) {
		this.listApreciacion = listApreciacion;
	}

	public List<Apreciacion> getFilteredListApr() {
		return filteredListApr;
	}

	public void setFilteredListApr(List<Apreciacion> filteredListApr) {
		this.filteredListApr = filteredListApr;
	}
		
}
