//package fi.uaemex.beans;
//
//import java.io.Serializable;
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.faces.context.FacesContext;
//
//import org.primefaces.event.CellEditEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import fi.uaemex.ejbs.ApreciacionFacade;
//import fi.uaemex.entities.Apreciacion;
//
//@ManagedBean(name="aprecBean")
//@ViewScoped
//public class ApreciacionBean implements Serializable 
//{
//	private static final Logger logg = LoggerFactory.getLogger(ApreciacionBean.class);
//	private static final long serialVersionUID = 1L;
//	private Apreciacion apreciacion;
//	private List<Apreciacion> listApreciacion;
//	private List<Apreciacion> filteredListApr;
//	@EJB private ApreciacionFacade apreciacionEJB;
//	private boolean hayModificado; 
//	public ApreciacionBean() 
//	{
//		// TODO Auto-generated constructor stub
//	}
//	
//	@PostConstruct
//	public void init()
//	{
//		//listProfPeriodActual = profeEJB.getAllProfesoresCurrent();
//		listApreciacion = apreciacionEJB.findCurrentAprec();
//	}
//	
//	public String guardarApreciacion()
//	{
//        logg.info(">> guardaApreciacion hayModificado: " + hayModificado);
//        if(hayModificado)
//        {
//        	Apreciacion aprec = apreciacionEJB.updateManyAprec(listApreciacion);
//        	if(aprec == null)
//        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"¡Los datos fueron actualizados con exito!",null));
//        	else
//        		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocurrio un error al ingresar la apreciacion del profesor con RFC: " + aprec.getProfesor().getRfcProfesor(),null));
//        }
//        else
//        {
//    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"¡No ha realizado ninguna modificación!",null));        	
//        }
//		return "";
//	}
//	
//	public void onCellEdit(CellEditEvent event) 
//	{
//        Object oldValue = event.getOldValue();
//        Object newValue = event.getNewValue();
//         
//        if(newValue != null && newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingrese un valor diferente o edite otro registro, ingereso el mismo valor", null);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//        else
//        {
//        	hayModificado = true;
//        }
//        logg.info(">> OncellEdit hayModificado: " + hayModificado);
//    }	
//		
//	public Apreciacion getApreciacion() {
//		return apreciacion;
//	}
//
//	public void setApreciacion(Apreciacion apreciacion) {
//		this.apreciacion = apreciacion;
//	}
//
//	public List<Apreciacion> getListApreciacion() {
//		return listApreciacion;
//	}
//
//	public void setListApreciacion(List<Apreciacion> listApreciacion) {
//		this.listApreciacion = listApreciacion;
//	}
//
//	public List<Apreciacion> getFilteredListApr() {
//		return filteredListApr;
//	}
//
//	public void setFilteredListApr(List<Apreciacion> filteredListApr) {
//		this.filteredListApr = filteredListApr;
//	}
//		
//}
