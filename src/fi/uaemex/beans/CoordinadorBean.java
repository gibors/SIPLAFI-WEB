
package fi.uaemex.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.uaemex.ejbs.CoordinadorFacade;
import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.NotificacionesCoordFacade;
import fi.uaemex.entities.Coordinador;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.NotificacionesCoord;

@ManagedBean(name="coord")
@ViewScoped
public class CoordinadorBean implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private static final Logger logg = LoggerFactory.getLogger(CoordinadorBean.class);
	@ManagedProperty(value = "#{login}") private LoginBean login;	// Propiedad para usar el bean de session login  
    private List<NotificacionesCoord> listNotCoord;					// Lista que guarda las notificaciones para los diversos grupos que necesiten ser validados
    private NotificacionesCoord notifSelected;						// Entidad para las  notificaciones para el coordinador 
    private Grupo selecteGpo;										// Grupo que se selecciona para validacion 
    private Coordinador coordinador;								// Entidad para el coordinador
    private List<Grupo> listGposAValidar;							// Lista con todos los grupos con notificacoin para validacion.
    private List<Grupo> listGposSemester;							// Lista de grupos en el mismo semestre del grupo seleccionado para validaci�n
    private DateFormat fmt = new SimpleDateFormat("HH:mm");			// Formato de fecha/hora para mostrar el horario
    @EJB private CoordinadorFacade coorFac;							// EJB para acceso a datos del coordinador 
    @EJB private NotificacionesCoordFacade notifCoordEJB;			// EJB para acceso a datos de las notificaciones para el coordinador
    @EJB private GrupoFacade gpoEJB;    							// EJB para acceso a datos del grupo   
    
    @PostConstruct
    public void init()
    {
        coordinador = login.getCoord();
        //coordinador = coorFac.findUser("QH5Q0S7NYHJTM40", "QH5Q0S7NYHJTM40");        
        listNotCoord = notifCoordEJB.findNewNotif();
        listGposAValidar = new ArrayList<>();
        for(NotificacionesCoord nt: listNotCoord)
        { // Por cada notificacion se agrega a la lista de grupo con notificaciones (TOP)           
           Grupo g = gpoEJB.find(nt.getNotificacionesCoordPK().getIdGrupo());
           g.setDescripcion(nt.getDescripcion());
           listGposAValidar.add(g);
        }  // Por cada notificacion se agrega a la lista de grupo con notificaciones (BOTTOM)
      //  logg.info(">>> Grupos a validar : " + listGposAValidar.size() + " -- " + listGposAValidar.get(0).toString());
    }
    
    public CoordinadorBean() 
    {
    }
    
    public void validarGrupo()
    {
        logg.info("SELECTED GRUPO " + selecteGpo.getNombre());
        listGposSemester = new ArrayList<>();
        listGposSemester = gpoEJB.findGrupoSemestre(selecteGpo.getClaveMateria().getSemestre(),selecteGpo.getIdGrupo());
        logg.info(">>> GRUPOS EN EL MISMO SEMESTRE : " + listGposSemester.size());
        for(NotificacionesCoord nt:listNotCoord)
        { // Buscamos la seleccion de notificacion (TOP)
            if(selecteGpo.getIdGrupo() == nt.getNotificacionesCoordPK().getIdGrupo())
            {
                notifSelected = nt;
                logg.info("notificacion encontrada.. " + notifSelected.getDescripcion());
            }
        } // Buscamos la seleccion de notificacion (BOTTOM)
        
    	RequestContext.getCurrentInstance().execute("PF('dlgValida').show()");
    }
    
    public String aceptarHorario()
    {
    	logg.info(">>> SELECTED GPO TO VALIDATE " + (selecteGpo == null ? "nulo" : selecteGpo.getNombre()));    	
    	selecteGpo.setValidado(1); // Aceptado
    	notifSelected.setEstado(1);
    	notifSelected.setFechaHoraValidacion(new Date());
    	notifCoordEJB.edit(notifSelected);
    	gpoEJB.edit(selecteGpo);
    	String mensaje = "El(La) Coordinador(a) " + 
    		" ha validado el grupo <b>" + selecteGpo.getNombre() +  "</b> de la materia <b>" + selecteGpo.getClaveMateria().getNombreMateria() + "</b> \n " +
    		" y fue aceptado, Ingrese a la liga para imprimir el formato #1 el(los) grupo(s) \n http://localhost:8282/SIPLAFI-WEB/index.jsf � http://localhost:8080/SIPLAFI-WEB/index.jsf";
       	enviarMail(selecteGpo, mensaje);
    	
    	logg.info(">>> HORARIO ACEPTADO");
    	return "";
    }
    
    public String rechazarHorario()
    {
    	selecteGpo.setValidado(2); // Rechazado
    	gpoEJB.edit(selecteGpo);
    	notifSelected.setEstado(2);
    	notifSelected.setFechaHoraValidacion(new Date());
    	notifCoordEJB.edit(notifSelected);
    	String mensaje = "El(La) Coordinador(a) " + 
          	" ha validado el grupo <b>" + selecteGpo.getNombre() +  "</b> de la materia <b>" + selecteGpo.getClaveMateria().getNombreMateria() + "</b> \n " +
          	" y rechazo el horario propuesto, Ingrese a la liga para modificar el(los) grupo(s) \n http://localhost:8282/SIPLAFI-WEB/index.jsf � http://localhost:8080/SIPLAFI-WEB/index.jsf";
       	enviarMail(selecteGpo, mensaje);
    	logg.info(">>>> GRUPO RECHAZADOO");
    	return "";
    }
    
    public void enviarMail(Grupo g,String mensaje)
    {
        final String user = "gibran_skato@hotmail.com";
        final String password ="sirenito_88";
        
        String para = "giresa.ico@gmail.com";
        String subject = "Se ha valido un grupo";
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.live.com");
        props.put("mail.smtp.port", "587");
        
        Authenticator aut = new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(user, password);
            }
        };        
        Session session = Session.getInstance(props,aut);                
        try 
        {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(para));
            message.setSubject(subject);
            message.setContent(mensaje,"text/html");
            //message.setText(mensaje);
            Transport.send(message);
            logg.info("EL CORREO FUE ENVIADO EXITOSAMENTE");
            //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(""));

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }    
    
    public LoginBean getLogin()
    {
        return login;
    }

    public void setLogin(LoginBean login)
    {
        this.login = login;
    }
    public String cerrarSession()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "index?faces-redirect=true";
    }
    
    public List<NotificacionesCoord> getListNotCoord()
    {
        return listNotCoord;
    }

    public void setListNotCoord(List<NotificacionesCoord> listNotCoord)
    {
        this.listNotCoord = listNotCoord;
    }

    public Grupo getSelecteGpo()
    {
        return selecteGpo;
    }

    public void setSelecteGpo(Grupo selecteGpo) 
    {
        this.selecteGpo = selecteGpo;
    }
    
    
    public List<Grupo> getListGposAValidar() 
    {
        return listGposAValidar;
    }

    public void setListGposAValidar(List<Grupo> listGposAValidar) 
    {
        this.listGposAValidar = listGposAValidar;
    }

    public NotificacionesCoord getNotifSelected() 
    {
        return notifSelected;
    }

    public void setNotifSelected(NotificacionesCoord notifSelected) 
    {
        this.notifSelected = notifSelected;
    }

	public Coordinador getCoordinador() 
	{
		return coordinador;
	}

	public void setCoordinador(Coordinador coordinador) 
	{
		this.coordinador = coordinador;
	}

	public DateFormat getFmt() {
		return fmt;
	}

	public void setFmt(DateFormat fmt) {
		this.fmt = fmt;
	}

	public List<Grupo> getListGposSemester() {
		return listGposSemester;
	}

	public void setListGposSemester(List<Grupo> listGposSemester) {
		this.listGposSemester = listGposSemester;
	}
    
	
}
