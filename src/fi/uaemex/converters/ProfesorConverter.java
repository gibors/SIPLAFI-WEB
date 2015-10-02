
package fi.uaemex.converters;

import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Profesor;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author IEEM
 */
@FacesConverter("profesorConverter")
public class ProfesorConverter implements Converter
{
    @EJB
    private ProfesorFacade profEJB;
            
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) 
    {
        if(value != null || !value.isEmpty())
        {
            try
            {
                return  profEJB.find(value);
            }
            catch(ConverterException exCon)
            {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error de conversion","Hubo un error al convertir el valor a un objeto"));

            }
       }
       return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value != null)
            return ((Profesor) value).getRfcProfesor();
        else 
            return null;
    }
    
}
