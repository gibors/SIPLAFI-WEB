package fi.uaemex.beans;

import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Profesor;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Pattern;


@ManagedBean(name="registrarCorreo")
@RequestScoped
public class RegistrarCorreoBean
{
    @Pattern(regexp="[A-Z0-9a-z._%-]+@[A-Z0-9a-z.-]+\\.[A-Za-z]{2,4}",message="formato de correo invalido")
    private String correo;												// Patron para validar el formato de correo
    @Pattern(regexp="[A-Z0-9a-z._%-]+@[A-Z0-9a-z.-]+\\.[A-Za-z]{2,4}",message="formato de correo invalido")
    private String correoConfirma;    									// Patron para validar el formato del correo de confirmación
    private Profesor profe;    											// Profesor al cual se le asignara el nuevo correo
    @EJB private ProfesorFacade profeSB;								// EJB para acceso al modelo del profesor  
    @ManagedProperty(value = "#{login}") private LoginBean login;		// Referencia al bean del login para obtener el objeto profesor.
    
    public RegistrarCorreoBean() 
    {        
    }
    
    public String ingresarMail()
    {
        if(correo.trim().equals(correoConfirma.trim()))
        { // Si la confirmacion del correo y el correo son iguales (TOP)
            profe = login.getProfe();
            profe.setEmailProfe(correo.trim());
            profeSB.setCorreo(profe);
            
            return "profesor?faces-redirect=true";
        } // Si la confirmacion del correo y el correo son iguales (BOTTOM)
        else
        { // Si los correos son diferentes se envia mensaje de error (TOP)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Los correos no coinciden, veriquelos nuevamente", null));
            return "";
        } // Si los correos son diferentes se envia mensaje de error (BOTTOM)
               
    }

    public void setLogin(LoginBean login) 
    {
        this.login = login;
    }

    public String getCorreo() 
    {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }    

    public String getCorreoConfirma() {
        return correoConfirma;
    }

    public void setCorreoConfirma(String correoConfirma) {
        this.correoConfirma = correoConfirma;
    }
    
    
}
