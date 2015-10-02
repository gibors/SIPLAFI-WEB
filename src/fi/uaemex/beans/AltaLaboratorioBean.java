
package fi.uaemex.beans;

import fi.uaemex.ejbs.AulaFacade;
import fi.uaemex.entities.Aula;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.PersistenceException;

/**
 *
 * @author IEEM
 */

@ManagedBean
@RequestScoped
public class AltaLaboratorioBean {
    
    private static final Logger logg = Logger.getLogger(AltaLaboratorioBean.class.getName());
    @EJB
    private AulaFacade aulaEJB;
    private String nombreLaboratorio;
    private String siglas;

    public AltaLaboratorioBean()
    {        
    }
    
    public String registrarLab()
    {
        Aula aula = new Aula();
        aula.setNombre(nombreLaboratorio.toUpperCase());
        aula.setTipoAula(siglas.toUpperCase());
        try
        {
            aulaEJB.create(aula);
            return "coordinador?faces-redirect=true";
        }
        catch(PersistenceException exP)
        {
            logg.log(Level.SEVERE, "Error al insertar el nuevo laboratorio");
        }
     return null;
    }

    public String getNombreLaboratorio() 
    {
        return nombreLaboratorio;
    }

    public void setNombreLaboratorio(String nombreLaboratorio) 
    {
        this.nombreLaboratorio = nombreLaboratorio;
    }

    public String getSiglas() 
    {
        return siglas;
    }

    public void setSiglas(String siglas) 
    {
        this.siglas = siglas;
    }        
}