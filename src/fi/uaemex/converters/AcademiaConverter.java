
package fi.uaemex.converters;

import fi.uaemex.ejbs.AcademiaFacade;
import fi.uaemex.entities.Academia;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("academiaConverter")
public class AcademiaConverter implements Converter
{
    @EJB
    private AcademiaFacade academiaEJB;
    
    public AcademiaConverter() 
    {
        
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null || !value.isEmpty()) 
        {
            try
            {
                return (Academia)academiaEJB.find(Integer.valueOf(value));
            } catch(NumberFormatException e)
            {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la conversion as object", "No es un valor valido."));
            }
        }
            return null;
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object)
    {
        
        if(object != null)
        {
            try
            {                            
            return ((Academia)object).getIdAcademia().toString();
            }
            catch(ConverterException ex)
            {
                    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la conversion as string", "No es un valor valido."));
            }
        }
        
        return "";
    }               
}
