
package fi.uaemex.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.uaemex.entities.Grupo;

@ManagedBean
@ViewScoped
public class ValidarHorarioBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final Logger logg = LoggerFactory.getLogger(ValidarHorarioBean.class);
	
	@ManagedProperty("#{coord}")
	private CoordinadorBean coordinadorB;
	private Grupo selectedGpo;
	
	
	@PostConstruct
	public void init()
	{
		selectedGpo = coordinadorB.getSelecteGpo();
		logg.info(">> grupo seleccionado ");
	}
	
	public void setCoordinadorB(CoordinadorBean coordinadorB) 
	{
		this.coordinadorB = coordinadorB;
	}
	
	public Grupo getSelectedGpo() 
	{
		return selectedGpo;
	}

	public void setSelectedGpo(Grupo selectedGpo) 
	{
		this.selectedGpo = selectedGpo;
	}		
}
