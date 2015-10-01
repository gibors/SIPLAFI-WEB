
package fi.uaemex.beans;

import fi.uaemex.ejbs.CoordinadorFacade;
import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Coordinador;
import fi.uaemex.entities.Profesor;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ManagedBean(name = "login",eager = true)
@SessionScoped
public class LoginBean implements Serializable
{ // Bean de session para profesor o coordinador (TOP)
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(LoginBean.class); // Variable para mostrar informaciÛn de log
    private String user;						// USUARIO para ingresar al sistema 
    private String password;					// PASSWORD para ingresar al sistema
    private int tipo;							// Verifica si el usuario es profesor = 0 , o coordinador = 1
    private boolean isLoggin;					// Verifica si existe un usuaerio loggeado    
    private Profesor profe;						// Entity para obtener propiedades del profesor 
    private Coordinador coord;					// Entity para obtener propiedades del coordinador
    @EJB private ProfesorFacade profeSB;		// EJB para acceso al modelo del profesor
    @EJB private CoordinadorFacade coordSB;		// EJB para acceso al modelo del coordinador
            
    public LoginBean() 
    {             
    }
    
    public String login() throws IOException
    { // Verifica que las credenciales sean validas para ingresar al sistema (TOP)
        if(tipo==0)
        { // Si el usuario es profesor (TOP)
            profe = profeSB.findUser(user, password);
            if ( profe != null)
            { // Si se encuentra el usuario (TOP)
               
//            	session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//                Subject subject = (Subject) session.getAttribute("javax.security.auth.subject");
//                if(subject != null)
//                	subject.getPrincipals().add( new role);
            	
                isLoggin = true;
                logger.info(">> si se encontro el usuario profesor : " + user);                
                if(profe.getEmailProfe() == null)
                { // Si NO tiene coreo registrado (TOP)                	
//                     FacesContext.getCurrentInstance().getExternalContext().redirect("registrarCorreo.xhtml");
                     return  "registrarCorreo?faces-redirect=true";
                } // Si NO tiene coreo registrado (BOTTOM)
                else
                	return "profesor?faces-redirect=true";
            } // Si se encuentra el usuario (BOTTOM)
            else
            { // Si NO se encuentra el usuario.. (TOP)
                logger.info("no esta el usuario");
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario y/o contrase√±a invalida",null));
                return "";
            } // Si NO se encuentra el usuario.. (BOTTOM)
        }  // Si el usuario es profesor (BOTTOM)
        else if(tipo==1)
        { // Si el usuario es coordinador (TOP)
            coord = coordSB.findUser(user, password);
            if(coord != null)
            { // si Se encuentra el coordinador (TOP)
                isLoggin = true;
                return "coordinador?faces-redirect=true";                                
            } // si Se encuentra el coordinador (BOTTOM)
            else
                return "";
        } // Si el usuario es coordinador (BOTTOM)
        return null;
   } // Verifica que las credenciales sean validas para ingresar al sistema (BOTTOM)

   public String getUser() 
   {
        return user;
   }

    public void setUser(String user) 
    {
        this.user = user;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public int getTipo() 
    {
        return tipo;
    }

    public void setTipo(int tipo) 
    {
        this.tipo = tipo;
    }

    public Profesor getProfe() 
    {
        return profe;
    }

    public void setProfe(Profesor profe) 
    {
        this.profe = profe;
    }

    public Coordinador getCoord() 
    {
        return coord;
    }

    public void setCoord(Coordinador coord) 
    {
        this.coord = coord;
    }

    public boolean isIsLoggin() 
    {
        return isLoggin;
    }

    public void setIsLoggin(boolean isLoggin) 
    {
        this.isLoggin = isLoggin;
    }
       
    
}  // Bean de session para profesor o coordinador (BOTTOM)
