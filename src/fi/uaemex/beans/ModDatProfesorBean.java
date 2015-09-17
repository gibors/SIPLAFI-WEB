package fi.uaemex.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Profesor;

@ManagedBean(name="modDat")
@ViewScoped
public class ModDatProfesorBean 
{
	private static final Logger logg = LoggerFactory.getLogger(ModDatProfesorBean.class);	
	private String passwd;
	private String passwdConfirm;
	private Profesor profesor;
	@EJB private ProfesorFacade profeEJB;	
	@ManagedProperty(value="#{login}") private LoginBean login;
	
	@PostConstruct
	public void init()
	{
		profesor = login.getProfe();		
	}
	
	public String actualizarDatos()
	{
		logg.info(">>>>> Datos ingresados... correo:  " + profesor.getEmailProfe() + " --telef: " + profesor.getTelefono());
		try
		{					
			profeEJB.edit(profesor);
			logg.info(" >> DATOS DEL PROFESOR ACTUALIZADOS ");
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sus datos han sido actualizados con exito!",null));
			return "";
		}
		catch(PersistenceException perEx)
		{
			logg.error(">>> ERROR AL ACTUALIZAR LOS DATOS " + perEx.toString());
			return "";
		}
	}
	
	public String cambiarPassword()
	{
		try
		{	
			if(passwd.trim().equals(passwdConfirm.trim()))
			{
				profesor.setPasswordProfe(passwd);
				
				profeEJB.edit(profesor);
				logg.info(" >> DATOS DEL PROFESOR ACTUALIZADOS ");
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sus datos han sido actualizados con exito!",null));
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Las contraseñas no coinciden, verifique sus datos",null));
			}
			return "";
		}
		catch(PersistenceException perEx)
		{
			logg.error(">>> ERROR AL ACTUALIZAR LOS DATOS " + perEx.toString());
			return "";
		}
	}

	public String getPasswd() 
	{
		return passwd;
	}

	public void setPasswd(String passwd) 
	{
		this.passwd = passwd;
	}

	public void setLogin(LoginBean login) 
	{
		this.login = login;
	}
	
	public Profesor getProfesor() 
	{
		return profesor;
	}

	public void setProfesor(Profesor profesor) 
	{
		this.profesor = profesor;
	}

	public String getPasswdConfirm() {
		return passwdConfirm;
	}

	public void setPasswdConfirm(String passwdConfirm) {
		this.passwdConfirm = passwdConfirm;
	}
	
	
	
}
