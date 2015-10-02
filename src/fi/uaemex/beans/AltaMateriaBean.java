
package fi.uaemex.beans;

import fi.uaemex.ejbs.AcademiaFacade;
import fi.uaemex.ejbs.MateriaFacade;
import fi.uaemex.entities.Academia;
import fi.uaemex.entities.Materia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;
import org.primefaces.event.SelectEvent;
/**
 *
 * @author IEEM
 */
@ManagedBean(eager = true)
@ViewScoped
public class AltaMateriaBean implements Serializable
{

    private static final Logger log = Logger.getLogger(AltaMateriaBean.class.getName());
    private String nombreMateria;
    private String claveMateria;
    private Integer horas;
    private Integer semestre;
    private Academia academia;
    List<Academia> academiaList;
    
    @EJB
    private AcademiaFacade academEJB;    
    @EJB
    private MateriaFacade materEJB;
    
    public AltaMateriaBean() 
    {

    }
     
    @PostConstruct
    public void init()
    {
        try
        { // Try - obtiene lista de academia (TOP)
            academiaList = academEJB.getAllAcademia();
            
        } // Try - obtiene lista de academia (BOTTOM)
        catch(PersistenceException pEx)
        { // Si ocurre un error muestra mensaje (TOP)
            log.log(Level.SEVERE, "Ocurrió un error al obtener la lista de academias: " +  pEx.toString());            
        } // Si ocurre un error muestra mensaje (BOTTOM)
    }

    public List<Academia> getAcademy(String query)
    {
       Collections.sort(academiaList);
        List<Academia> res = new ArrayList<>();
        for(Academia a : academiaList)
        {  
            if(a.getNombre().toLowerCase().startsWith(query.toLowerCase()))
            {
                //System.out.println("coincide con " + a.getNombre());
                res.add(a);
            }
        }
        return res;
    }
    
    public void onItemSelect(SelectEvent event){
        
        if(event.getObject() != null)
        {
            System.out.println("seleccionaste " + ((Academia)event.getObject()).getNombre() + ", nueva " + academia.getNombre());
//            this.academia = (Academia)event.getObject();
        }
        else
            System.out.println("no seleccionaste nada");
    }
    public String registrarMateria()
    {        
        //academia = academEJB.find(idAcademia);        
        //log.log(Level.OFF, "academia " +  materia.getIdAcademia().getNombre() + "," + academia.getNombre());        
        Materia materia = new Materia();
        materia.setClaveMateria(claveMateria.trim().toUpperCase());
        materia.setNombreMateria(nombreMateria.trim().toUpperCase());
        materia.setSemestre(semestre);
        materia.setHoras(horas);
        materia.setIdAcademia(academia);       
      //log.log(Level.OFF, "academia " +  materia.getIdAcademia().getNombre() + "," + academia.getNombre());
        
        try
        { // Se obtiene el id de academia y se persiste la nueva materia (TOP)
            materEJB.create(materia);
            //log.log(Level.INFO, "Se insertó correctamente la nueva materia");
        } // Se obtiene el id de academia y se persiste la nueva materia (BOTTOM)
        catch(PersistenceException ex)
        {
            log.log(Level.SEVERE, "Ocurrió un error al obtener la academia o al insertar la nueva materia: " +  ex.toString());
        }
       return "coordinador?redirect=true";
      //log.log(Level.OFF, "academia " +  materia.getIdAcademia().getNombre() + "," + academia.getNombre());
        //return "";
    }

    public String getNombreMateria() 
    {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getClaveMateria() {
        return claveMateria;
    }

    public void setClaveMateria(String claveMateria) {
        this.claveMateria = claveMateria;
    }

    public List<Academia> getAcademiaList() 
    {
        return academiaList;
    }

    public void setAcademiaList(List<Academia> academiaList) 
    {
        this.academiaList = academiaList;
    }
    
    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Academia getAcademia() {
        return academia;
    }

    public void setAcademia(Academia academia) {
        this.academia = academia;
    }

//    @Override
//    public int compareTo(Academia o) {
//     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    return academia.getNombre().compareTo(o.getNombre());
//    }
         
}
