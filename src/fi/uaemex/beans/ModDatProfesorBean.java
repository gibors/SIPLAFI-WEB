package fi.uaemex.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;

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
	
	public ModDatProfesorBean() 
	{
	}
	
	@PostConstruct
	public void init()
	{
		profesor = login.getProfe();		
	}
	
    public String cerrarSession()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "index?faces-redirect=true";
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
		logg.info(">>>> password: " + passwd + " --  confirmpass: " + passwdConfirm);
		try
		{	
			if ((passwd == null || passwd.isEmpty()) && (passwdConfirm == null || passwdConfirm.isEmpty()))
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"No ha introducido ninguna contraseña",null));
				return "";
			}			
			else if(passwd.trim().length() < 7 && passwdConfirm.trim().length() < 7)
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"La longitud de la contraseña debe ser mayor que 6 y menor que 15",null));
				passwd = "";
				passwdConfirm = "";
				return "";				
			}
			else if(passwd.trim().equals(passwdConfirm.trim()))
			{
				profesor.setPasswordProfe(passwd);				
				profeEJB.edit(profesor);
				logg.info(" >> DATOS DEL PROFESOR ACTUALIZADOS ");
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Sus datos han sido actualizados con exito!",null));
				return "";
			}		
			else
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Las contraseñas no coinciden, verifique sus datos",null));
				passwd = "";
				passwdConfirm = "";				
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
