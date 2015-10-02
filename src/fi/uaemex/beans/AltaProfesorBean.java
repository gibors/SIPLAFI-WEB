
package fi.uaemex.beans;

//import javax.inject.Named;
import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Profesor;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


/**
 *
 * @author IEEM
 */
//@Named(value = "altaProfesorBean")
@ManagedBean
@RequestScoped
public class AltaProfesorBean implements Serializable{
    
    private static final String EMAIL_PATTERN = "[A-Z0-9a-z._%-]+@[A-Z0-9a-z.-]+\\.[A-Za-z]{2,4}";
    private String rfcProfesor;
    private String gradoProfesor;
    private String nombreProfesor;
    private String apePatProfesor;
    private String apeMatProfesor;
    //@Pattern(regexp="[A-Z0-9a-z._%-]+@[A-Z0-9a-z.-]+\\.[A-Za-z]{2,4}",message="formato de correo invalido")
    private String emailProfesor;
    private Matcher matcher;
    private final Pattern pat;
    
    @EJB
    private ProfesorFacade profeEJB;
    
    public AltaProfesorBean()
    {
       pat = Pattern.compile(EMAIL_PATTERN);
    }

//    public void listener()
//    {
//        
//    }
    public String registrarProf()
    {
        System.out.println("registrar profesor");
        Profesor profe = new Profesor();
        profe.setRfcProfesor(rfcProfesor.trim().toUpperCase());
        profe.setGradoProfe(gradoProfesor.trim().toUpperCase());
        profe.setNombreProfe(nombreProfesor.trim().toUpperCase());
        profe.setApePatProfe(apePatProfesor.trim().toUpperCase());
        profe.setApeMatProfe(apeMatProfesor.trim().toUpperCase());
        profe.setEmailProfe(emailProfesor.trim());
        
        profeEJB.create(profe);
        
        return "coordinador";
    }
    public String getRfcProfesor() 
    {
        return rfcProfesor;
    }

    public void setRfcProfesor(String rfcProfesor) {
        this.rfcProfesor = rfcProfesor;
    }

    public String getGradoProfesor() {
        return gradoProfesor;
    }

    public void setGradoProfesor(String gradoProfesor) {
        this.gradoProfesor = gradoProfesor;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getApePatProfesor() {
        return apePatProfesor;
    }

    public void setApePatProfesor(String apePatProfesor) {
        this.apePatProfesor = apePatProfesor;
    }

    public String getApeMatProfesor() {
        return apeMatProfesor;
    }

    public void setApeMatProfesor(String apeMatProfesor) {
        this.apeMatProfesor = apeMatProfesor;
    }

    public String getEmailProfesor() {
        return emailProfesor;
    }

    public void setEmailProfesor(String emailProfesor) {
        this.emailProfesor = emailProfesor;
    }

    public String validateEmail()
    {
        matcher = pat.matcher(emailProfesor);
        System.out.println("st: " + emailProfesor);
      //  System.out.println("match: " + matcher.find());
        
        if(!matcher.find())
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de correo invalido", null));
            //return "";
        }
        else
        {
            System.out.println("si es valido");
            //return "";
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return "";
    }
    
    
    
}
