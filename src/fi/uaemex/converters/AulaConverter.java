package fi.uaemex.converters;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import fi.uaemex.ejbs.AulaFacade;
import fi.uaemex.entities.Aula;


@FacesConverter("aulaConverter")
public class AulaConverter implements Converter {

	@EJB AulaFacade aulaEJB;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) 
	{
		// TODO Auto-generated method stub
		  if(value != null || !value.isEmpty()) 
	        {
	            try
	            {
	                return (Aula)aulaEJB.find(Integer.valueOf(value));
	            } catch(NumberFormatException e)
	            {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la conversion as object", "No es un valor valido."));
	            }
	        }
	            return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) 
	{
		// TODO Auto-generated method stub
        if(value != null)
        {
            try
            {                            
            	return ((Aula)value).getIdAula().toString();
            }
            catch(ConverterException ex)
            {
            	throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error en la conversion as string", "No es un valor valido."));
            }
        }
        
        return "";	
	}

}
