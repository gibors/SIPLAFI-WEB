
package fi.uaemex.converters;

import fi.uaemex.ejbs.MateriaFacade;
import fi.uaemex.entities.Materia;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;



@FacesConverter("materiaConverter")
public class MateriaConverter implements Converter{

@EJB
private MateriaFacade matFacade;

@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) 
    {
        if(value != null || !value.isEmpty())
        {
            try
            {
                 return matFacade.find(value);
            }
            catch(NumberFormatException nfE)
            {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "No es un valor valido."));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value != null)
        {
            return ((Materia) value).getClaveMateria();
        }
        else
        {
            return "";
        }
    }
    
}
