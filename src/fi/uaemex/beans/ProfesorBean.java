
package fi.uaemex.beans;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.primefaces.context.RequestContext;

import fi.uaemex.ejbs.AulaFacade;
import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.Horario2Facade;
import fi.uaemex.ejbs.HorarioFacade;
import fi.uaemex.ejbs.NotificacionesCoordFacade;
import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Aula;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.Horario;
import fi.uaemex.entities.Horario2;
import fi.uaemex.entities.NotificacionesCoord;
import fi.uaemex.entities.NotificacionesCoordPK;
import fi.uaemex.entities.Profesor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

@ManagedBean(name = "profesorBean",eager=true)
@ViewScoped
public class ProfesorBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final Logger logg = Logger.getLogger(ProfesorBean.class.getName());
    private static final String rutajasper = "/reporte/reporte1.1.jasper";	
    private DateFormat format = new SimpleDateFormat("HH:mm "); 		// Formato para el horario
    private Date lunIn;													// Hora de inicio de la clase para el dia lunes 
    private Date lunFn; 												// Hora de fin de la clase para el dia lunes
    private Date marIn;													// Hora de inicio de la clase para el dia martes
    private Date marFn; 												// Hora de fin de la clase para el dia martes
    private Date mieIn;													// Hora de inicio de la clase para el dia miercoles
    private Date mieFn; 												// Hora de fin de la clase para el dia miercoles
    private Date jueIn;													// Hora de inicio de la clase para el dia jueves
    private Date jueFn; 												// Hora de fin de la clase para el dia jueves
    private Date vieIn;													// Hora de inicio de la clase para el dia viernes
    private Date vieFn; 												// Hora de fin de la clase para el dia viernes
    private Date sabIn;													// Hora de inicio de la clase para el dia sabado
    private Date sabFn;													// Hora de fin de la clase para el dia sabado
    private String aulaLunes;											// Guarda el aula para el lunes 
    private String aulaMartes;											// Guarda el aula para el Martes
    private String aulaMiercoles;										// Guarda el aula para el Miercoles
    private String aulaJueves;											// Guarda el aula para el Jueves	
    private String aulaViernes;											// Guarda el aula para el Viernes
    private String aulaSabado;    										// Guarda el aula para el Sabado
    private List<String> mensajesTraslape;								// Lista con todos los traslapes que se encontraron al cambiar el horario
    private String mensajeBoton;										// Mensaje del boton para enviar a validacion o imprimir el formato #1 
    private Profesor profe;                         					// Profesor logged
    private String nombreProfe;                     					// nombre para mostrar del profesor
    private List<Grupo> gposProfe;                  					// obtiene la lista de los grupos del profesor
    private List<Grupo> listGposInSemester;								// Almacena los grupos de las materias en el mismo horario del grupo seleccionado para modificar
    private List<Aula> listAula; 										// Lista de las aulas requeridas para la clase
    private List<Horario2> listaModificados;							// almacena todos los horaios modificados para guardarlos al enviar a validacion.. 
    private Grupo selectedGpo;											// Almacena el grupo que es seleccionado para editar el horario
    private boolean todosConfirmadosOAceptados;							// Obtiene verdadero si todos los grupos del profesor estan confirmados o aceptados
    private boolean conGrupoAValidar;									// Obtiene verdadero si algun grupo necesita ser validado
    private boolean seHaConfirmadoTodo;									// Si todos los grupos estan confirmados o aceptados
    @EJB private ProfesorFacade profFacade;              				// EJB para acceso a datos del profesor
    @EJB private GrupoFacade gpoEJB;                     				// EJB para acceso a datos del grupo
    @EJB private AulaFacade aulaEJB;									// EJB para acceso a datos de la entidad aula
    @EJB private HorarioFacade HorarioEJB;								// EJB para acceso a datos del Horario modificado para validacion .. 
    @EJB private Horario2Facade hora2EJB;								// EJB para accesos a datos de la entidad Horario2
    @EJB private NotificacionesCoordFacade notifEJB;					// EJB para acceso a datos de las notificaciones del coordinador 
    @ManagedProperty(value = "#{login}") private LoginBean login;   	// Propiedad para usar variables de session del bean de login
    private Properties props;											// Agrega las propiedades para la conexion al servidor de correo
    private Session session = null;										// Variable de session para conectarse al servidor de correo
    private String para;												// Almacena el destino del correo
    private String subject;												// Guarda el asunto del correo 
    private String mensaje;												// Almacena el mensaje que se enviara por correo
    
   public ProfesorBean()
   {
   }
   
   @PostConstruct
   public void init()
   { // Se ejecuta antes de construir el objeto (TOP)
	   listaModificados = new ArrayList<>();
	   profe = login.getProfe();	
	   //profe = profFacade.findUser("QH5Q0S7NYHJTM33", "QH5Q0S7NYHJTM33");
	   gposProfe = new ArrayList<>();
	   gposProfe = profe.getGrupoList();
       listAula = aulaEJB.findAll();
       todosConfirmadosOAceptados = gpoEJB.todosAceptadosOConfirmados(profe.getRfcProfesor(), gposProfe.size());
       if(todosConfirmadosOAceptados) seHaConfirmadoTodo = true;
       conGrupoAValidar = gpoEJB.hayGruposParaValidar(profe.getRfcProfesor());
       logg.info(">>>> " + todosConfirmadosOAceptados  + " -- Con grupo a validar:  " + conGrupoAValidar);       
       nombreProfe = profe.getNombreProfe() + " " + profe.getApePatProfe() + " " + profe.getApeMatProfe();
   } // Se ejecuta antes de construir el objeto (BOTTOM)
        
     
   public void onGrupoSelected(Grupo gs)
   { // Al seleccionar grupo para modificar abre el dialog para modificarlo (TOP)
    	this.selectedGpo = gs;    	
    	listGposInSemester = gpoEJB.findGrupoSemestre(selectedGpo.getClaveMateria().getSemestre(), selectedGpo.getIdGrupo()); // Se obtienen las materias o grupos en el mismo semestre
    	/****** Se utilizan variables auxiliares para el horario del grupo seleccionado porque maraca error en nulos, el componente pcalendar (TOP) ********/
    	lunIn = selectedGpo.getHorario().getLunHoraIni();
    	lunFn = selectedGpo.getHorario().getLunHoraFin();
    	marIn = selectedGpo.getHorario().getMarHoraIni();
    	marFn = selectedGpo.getHorario().getMarHoraFin();
    	mieIn = selectedGpo.getHorario().getMieHoraIni();
    	mieFn = selectedGpo.getHorario().getMieHoraFin();
    	jueIn = selectedGpo.getHorario().getJueHoraIni();
    	jueFn = selectedGpo.getHorario().getJueHoraFin();
    	vieIn = selectedGpo.getHorario().getVieHoraIni();
    	vieFn = selectedGpo.getHorario().getVieHoraFin();
    	sabIn = selectedGpo.getHorario().getSabHoraIni();
    	sabFn = selectedGpo.getHorario().getSabHoraFin();
    	aulaLunes = selectedGpo.getHorario().getAulaLun();
    	aulaMartes = selectedGpo.getHorario().getAulaMar();
    	aulaMiercoles = selectedGpo.getHorario().getAulaMie();
    	aulaJueves = selectedGpo.getHorario().getAulaJue();
    	aulaViernes = selectedGpo.getHorario().getAulaVie();
    	aulaSabado = selectedGpo.getHorario().getAulaSab();    	
    	/****** Se utilizan variables auxiliares para el horario del grupo seleccionado porque maraca error en nulos, el componente pcalendar (TOP) ********/    	
    	logg.info(">>> se selecciono un grupo ..." + selectedGpo.getNombre() + " oooo.... " + listGposInSemester.size());    	
    	logg.info("aula mar: " + aulaMartes + "-- aulaSab: " + aulaSabado);
    	RequestContext.getCurrentInstance().execute("PF('dlgModHora').show()");    	    	
    } // Al seleccionar grupo para modificar abre el dialog para modificarlo (BOTTOM)
       
   public void confirmarHorario(Grupo gpo)
   { // Marca el grupo para confirmacion (TOP)
    	this.selectedGpo = gpo;
        boolean hayModificado = false;
        boolean faltan = false;
        
        if(selectedGpo.getValidado() != null && selectedGpo.getValidado() == 2)
        { // Si el horario del grupo ya fue modificado y rechazado por la coordinación (TOP)
        	Horario2 hora = hora2EJB.find(selectedGpo.getHorario().getIdHorario());
        	if(hora != null)
        	{
            	selectedGpo.getHorario().setLunHoraIni(hora.getLunHoraIni());
            	selectedGpo.getHorario().setLunHoraFin(hora.getLunHoraFin());
            	selectedGpo.getHorario().setMarHoraIni(hora.getMarHoraIni());
            	selectedGpo.getHorario().setMarHoraFin(hora.getMarHoraFin());
            	selectedGpo.getHorario().setMieHoraIni(hora.getMieHoraIni());
            	selectedGpo.getHorario().setMieHoraFin(hora.getMieHoraFin());
            	selectedGpo.getHorario().setJueHoraIni(hora.getJueHoraIni());
            	selectedGpo.getHorario().setJueHoraFin(hora.getJueHoraFin());
            	selectedGpo.getHorario().setVieHoraIni(hora.getVieHoraIni());
            	selectedGpo.getHorario().setVieHoraFin(hora.getVieHoraFin());
            	selectedGpo.getHorario().setSabHoraIni(hora.getSabHoraIni());
            	selectedGpo.getHorario().setSabHoraFin(hora.getSabHoraFin());
            	selectedGpo.getHorario().setAulaLun(aulaLunes);
            	selectedGpo.getHorario().setAulaMar(aulaMartes);
            	selectedGpo.getHorario().setAulaMie(aulaMiercoles);
            	selectedGpo.getHorario().setAulaJue(aulaJueves);
            	selectedGpo.getHorario().setAulaVie(aulaViernes);
            	selectedGpo.getHorario().setAulaSab(aulaSabado);        
            	//selectedGpo.setValidado(null);
        		//hora2EJB.remove(hora);
        	}
        } // Si el horario del grupo ya fue modificado y rechazado por la coordinación (BOTTOM)
        
        if(selectedGpo.getEstado() == 2 ) // Si ya fue modificado
        	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("El grupo ya ha sido modificado, si desea cambiarlo eliga la opción modificar "));
        else
        {
        	selectedGpo.setEstado(1); // Estado Confirmado 
        	for(Grupo g : gposProfe)
        	{ // For que valida si hay algun grupo modificado o no (TOP)
        		if(g.getEstado() == 2)
        			hayModificado = true;
        		if((g.getEstado() == 0 && g.getValidado() != null && g.getValidado() != 1 && g.getValidado() != 3) || (g.getEstado() == 0 && g.getValidado() == null))
        			faltan = true;
        	} // For que valida si hay algun grupo modificado o no (BOTTOM)
        	
        	if(!hayModificado)
        	{ // Si no hay algun grupo modificado (TOP)
        		if(faltan)
        		{
        			todosConfirmadosOAceptados = false;
        		}
        		else 
        			todosConfirmadosOAceptados = true;
        	} // Si no hay algun grupo modificado (BOTTOM)  
            else
            {
        		if(faltan) 
        			todosConfirmadosOAceptados = false;
            }
        }
    	logg.info(" Confirmar horario >>> todos confirmadosoAcapetados " + todosConfirmadosOAceptados);
        
   } // Marca el grupo para confirmacion (BOTTOM)    
   
   public String confirmarModificacion()
   { // Modifica el horario para enviar a validación  (TOP)
        float min = 0;
        float minutos = 0;
        float horas= 0;       
        float horaXDia = 0;
        mensajesTraslape = new ArrayList<>();
        
//        if(selectedGpo != null && selectedGpo.getValidado() == 2)
//        { // Si el grupo ya fue validado y fue rechazado (TOP)
//        	Horario2 horarioRespaldo = hora2EJB.find(selectedGpo.getHorario().getIdHorario()); // Se busca si se guardo un horario anterior
//        	if(horarioRespaldo != null)
//        	{ // Si el horario de respaldo es encontrado (TOP)
//        		if
//        		(
//        			(lunIn == null ? horarioRespaldo.getLunHoraIni() == null : lunIn.equals(horarioRespaldo.getLunHoraIni())) && 
//        			(marIn == null ? horarioRespaldo.getMarHoraIni() == null : marIn.equals(horarioRespaldo.getMarHoraIni())) &&
//        			(mieIn == null ? horarioRespaldo.getMieHoraIni() == null : mieIn.equals(horarioRespaldo.getMieHoraIni())) &&
//        			(jueIn == null ? horarioRespaldo.getJueHoraIni() == null : jueIn.equals(horarioRespaldo.getJueHoraIni())) &&
//        			(vieIn == null ? horarioRespaldo.getVieHoraIni() == null : vieIn.equals(horarioRespaldo.getVieHoraIni())) &&
//        			(sabIn == null ? horarioRespaldo.getSabHoraIni() == null : sabIn.equals(horarioRespaldo.getSabHoraIni())) && 
//        			(lunFn == null ? horarioRespaldo.getLunHoraFin() == null : lunFn.equals(horarioRespaldo.getLunHoraFin())) && 
//        			(marFn == null ? horarioRespaldo.getMarHoraFin() == null : marFn.equals(horarioRespaldo.getMarHoraFin())) &&
//        			(mieFn == null ? horarioRespaldo.getMieHoraFin() == null : mieFn.equals(horarioRespaldo.getMieHoraFin())) &&
//        			(jueFn == null ? horarioRespaldo.getJueHoraFin() == null : jueFn.equals(horarioRespaldo.getJueHoraFin())) &&
//        			(vieFn == null ? horarioRespaldo.getVieHoraFin() == null : vieFn.equals(horarioRespaldo.getVieHoraFin())) &&
//        			(sabFn == null ? horarioRespaldo.getSabHoraFin() == null : sabFn.equals(horarioRespaldo.getSabHoraFin()))
//        		)
//        		{ // Verifica que se haya realizado alguna modificación al horario si no muestra mensaje (TOP)
//        			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Si va a elegir el horario anterior,cancele y vaya a la opcion deshacer modificación",null));
//        			return "";
//        		} // Verifica que se haya realizado alguna modificación al horario si no muestra mensaje (BOTTOM)
//        	} // Si el horario de respaldo es encontrado (BOTTOM)
//        } // Si el grupo ya fue validado y fue rechazado (BOTTOM)
		List<Grupo> listTrasLun = gpoEJB.findTraslapeLun(lunIn,lunFn,selectedGpo.getIdGrupo(),selectedGpo.getClaveMateria().getSemestre());
        List<Grupo> listTrasMar = gpoEJB.findTraslapeMar(marIn,marFn,selectedGpo.getIdGrupo(),selectedGpo.getClaveMateria().getSemestre());
        List<Grupo> listTrasMie = gpoEJB.findTraslapeMie(mieIn,mieFn,selectedGpo.getIdGrupo(),selectedGpo.getClaveMateria().getSemestre());
        List<Grupo> listTrasJue = gpoEJB.findTraslapeJue(jueIn,jueFn,selectedGpo.getIdGrupo(),selectedGpo.getClaveMateria().getSemestre());
        List<Grupo> listTrasVie = gpoEJB.findTraslapeVie(vieIn,vieFn,selectedGpo.getIdGrupo(),selectedGpo.getClaveMateria().getSemestre());
        List<Grupo> listTrasSab = gpoEJB.findTraslapeSab(sabIn,sabFn,selectedGpo.getIdGrupo(),selectedGpo.getClaveMateria().getSemestre());
 // ************* valida que si selecciona ya sea hora de inicio o de fin tiene que seleccionar hora de fin o de inicio (TOP) ***********************
        if(lunIn!= null && lunFn== null )
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de inicio para el lunes, seleccione hora de fin", null));
            return "";
        }
        else if(lunFn== null && lunIn!= null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de fin para el lunes, seleccione hora de inicio",null));
            return "";
        }
        else if(marIn != null && marFn== null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de inicio para el martes, seleccione hora de fin",null));
            return "";
        }
        else if(marIn == null && marFn != null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de fin para el martes, seleccione hora de inicio",null));
            return "";
        }
        else if(mieIn != null && mieFn== null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de inicio para el miercoles, seleccione hora de fin",null));
            return "";
        }            
        else if(mieIn == null && mieFn != null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de fin para el miercoles, seleccione hora de inicio",null));
            return "";
        }
        else if(jueIn != null && jueFn== null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de inicio para el jueves, seleccione hora de fin",null));
            return "";
        }
        else if(jueIn == null && jueFn != null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de fin para el jueves, seleccione hora de inicio",null));
            return "";
        }
        else if(vieIn != null && vieFn== null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de inicio para el viernes, seleccione hora de fin",null));
            return "";
        }
        else if(vieIn == null && vieFn != null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de fin para el viernes, seleccione hora de inicio",null));
            return "";
        }
        else if(sabIn != null && sabFn== null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de inicio para el Sabado, seleccione hora de fin",null));
            return "";
        }
        else if(sabIn == null && sabFn != null)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccionó hora de fin para el Sabado, seleccione hora de inicio",null));
            return "";
        }
 // ************* valida que si selecciona ya sea hora de inicio o de fin tiene que seleccionar hora de fin o de inicio (BOTTOM) ***********************
        
// ******  valida que la suma de horas corresponda a las horas de la materia y el horario de lunes a jueves no puede ser mayor a 2.5 horas (TOP) 
        if(lunIn != null && lunFn != null)
        { // Si se ingresa hora inicial y final del lunes (TOP)
            horaXDia = (lunFn.getHours() - lunIn.getHours());
            min = Math.abs(lunFn.getMinutes() - lunIn.getMinutes());
            horas = horas + horaXDia;
            horaXDia = horaXDia + (min > 0 ? min/60 : 0);
            if(min > 0) minutos = minutos + min/60;
            // System.out.println(" Aula " + horarioGpo.getAulaLun());
            
            if(lunIn.getTime() >= lunFn.getTime())
            { // Si la hora inicial es mayor o igual que la hora final (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"La hora de inicio del lunes debe ser menor que la hora de fin",null));
                return "";
            } // Si la hora inicial es mayor o igual que la hora final (BOTTOM)
            else if(horaXDia > 2.5)
            { // Si la suma de las horas en lunes es mayor a 2.5 (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"El horario del lunes debe ser menor o igual a 2 horas y media",null));
                return "";
            } // Si la suma de las horas en lunes es mayor a 2.5 (BOTTOM)
            else if(aulaLunes.equals("0") || aulaLunes == null)
            {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione el tipo de aula para el lunes",null));
                return "";
            }
        } // Si se ingresa hora inicial y final del lunes (BOTTOM)
        if(marIn != null && marFn != null)
        { // Si se ingresa hora de inicio y hora final en martes (TOP)
            horaXDia = (marFn.getHours() - marIn.getHours());
            min =  Math.abs(marFn.getMinutes() - marIn.getMinutes());
            horas = horas + horaXDia;
            horaXDia = horaXDia + (min > 0 ? min/60 : 0);
            if(min > 0) minutos = minutos + min/60;
            
            if(marIn.getTime() >= marFn.getTime())
            { // Si la hora inicial es mayor que la hora final (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"La hora de inicio del martes debe ser menor que la hora de fin",null));
                return "";
            } // Si la hora inicial es mayor que la hora final (BOTTOM)
            else if(horaXDia > 2.5)
            { // Si el horario del martes es mayor que 2.5 horas (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"El horario del martes debe ser menor o igual a 2 horas y media",null));
                return "";
            } // Si el horario del martes es mayor que 2.5 horas (BOTTOM)
            else if(aulaMartes.equals("0") || aulaMartes == null)
            {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione el tipo de aula para el martes",null));
                return "";
            }
        } // Si se ingresa hora de inicio y hora final en martes (BOTTOM)
        if(mieIn != null && mieFn != null)
        { // Si se ingresa hora de inicio y hora final en miercoles (TOP)
            horaXDia = (mieFn.getHours() - mieIn.getHours());
            min =  Math.abs(mieFn.getMinutes() - mieIn.getMinutes());
            horas = horas + horaXDia;
            horaXDia = horaXDia + (min > 0 ? min/60 : 0);
            if(min > 0) minutos = minutos + min/60;
            
            if(mieIn.getTime() >= mieFn.getTime())
            { // Si la hora inicial es mayor que la hora final (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"La hora de inicio del miercoles debe ser menor que la hora de fin",null));
                return "";
            } // Si la hora inicial es mayor que la hora final (BOTTOM)
            else if(horaXDia > 2.5)
            { // Si el horario del martes es mayor que 2.5 horas (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"El horario del miercoles debe ser menor o igual a 2 horas y media",null));
                return "";
            } // Si el horario del martes es mayor que 2.5 horas (BOTTOM)
            else if(aulaMiercoles.equals("0") || aulaMiercoles == null)
            {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione el tipo de aula para el miercoles",null));
                return "";
            }
        } // Si se ingresa hora de inicio y hora final en jueves (BOTTOM)
        if(jueIn != null && jueFn != null)
        { // Si se ingresa hora de inicio y hora final en jueves (TOP)
            horaXDia = (jueFn.getHours() - jueIn.getHours());
            min =  Math.abs(jueFn.getMinutes() - jueIn.getMinutes());
            horas = horas + horaXDia;
            horaXDia = horaXDia + (min > 0 ? min/60 : 0);
            if(min > 0) minutos = minutos + min/60;
            
            if(jueIn.getTime() >= jueFn.getTime())
            { // Si la hora inicial es mayor que la hora final (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"La hora de inicio del jueves debe ser menor que la hora de fin",null));
                return "";
            } // Si la hora inicial es mayor que la hora final (BOTTOM)
            else if(horaXDia > 2.5)
            { // Si el horario del martes es mayor que 2.5 horas (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"El horario del jueves debe ser menor o igual a 2 horas y media",null));
                return "";
            } // Si el horario del martes es mayor que 2.5 horas (BOTTOM)
            else if(aulaJueves.equals("0") || aulaJueves == null)
            {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione el tipo de aula para el jueves",null));
                return "";
            }
        } // Si se ingresa hora de inicio y hora final en jueves (BOTTOM)
        if(vieIn != null && vieFn != null)
        { // Si se ingresa hora de inicio y hora final en viernes (TOP)
            horas = horas + (vieFn.getHours() - vieIn.getHours());
            min =  Math.abs(vieFn.getMinutes() - vieIn.getMinutes());
            if(min > 0) minutos = minutos + min/60;
            
            if(vieIn.getTime() >= vieFn.getTime())
            { // Si la hora inicial es mayor que la hora final (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"La hora de inicio del viernes debe ser menor que la hora de fin",null));
                return "";
            } // Si la hora inicial es mayor que la hora final (BOTTOM
            else if(aulaViernes.equals("0") || aulaViernes==null)
            {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione el tipo de aula para el viernes",null));
                return "";
            }
        } // Si se ingresa hora de inicio y hora final en viernes (BOTTOM)
        if(sabIn != null && sabFn != null)
        { // Si se ingresa hora de inicio y hora final en sabado (TOP)
            horas = horas + (sabFn.getHours() - sabIn.getHours());
            min =  Math.abs(sabFn.getMinutes() - sabIn.getMinutes());
            if(min > 0) minutos = minutos + min/60;
            
            if(sabIn.getTime() >= sabFn.getTime())
            { // Si la hora inicial es mayor que la hora final (TOP)
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"La hora de inicio del sabado debe ser menor que la hora de fin",null));
                return "";
            } // Si la hora inicial es mayor que la hora final (BOTTOM
            else if(aulaSabado.equals("0") || aulaSabado == null)
            {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione el tipo de aula para el sabado",null));
                return "";
            }
        } // Si se ingresa hora de inicio y hora final en sabado (BOTTOM)
        horas = horas + minutos;
        if(horas != (float)selectedGpo.getClaveMateria().getHoras())
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR," el total de horas de la materia debe ser " + selectedGpo.getClaveMateria().getHoras() + " la suma actual es : " + horas,null));
            return "";    
        }
// ******  valida que la suma de horas corresponda a las horas de la materia y el horario de lunes a jueves no puede ser mayor a 2.5 horas (BOTTOM) 
        if (
        		(lunIn == null ? selectedGpo.getHorario().getLunHoraIni() == null : lunIn.equals(selectedGpo.getHorario().getLunHoraIni())) && 
        		(marIn == null ? selectedGpo.getHorario().getMarHoraIni() == null : marIn.equals(selectedGpo.getHorario().getMarHoraIni())) &&
        		(mieIn == null ? selectedGpo.getHorario().getMieHoraIni() == null : mieIn.equals(selectedGpo.getHorario().getMieHoraIni())) &&
        		(jueIn == null ? selectedGpo.getHorario().getJueHoraIni() == null : jueIn.equals(selectedGpo.getHorario().getJueHoraIni())) &&
        		(vieIn == null ? selectedGpo.getHorario().getVieHoraIni() == null : vieIn.equals(selectedGpo.getHorario().getVieHoraIni())) &&
        		(sabIn == null ? selectedGpo.getHorario().getSabHoraIni() == null : sabIn.equals(selectedGpo.getHorario().getSabHoraIni())) && 
        		(lunFn == null ? selectedGpo.getHorario().getLunHoraFin() == null : lunFn.equals(selectedGpo.getHorario().getLunHoraFin())) && 
        		(marFn == null ? selectedGpo.getHorario().getMarHoraFin() == null : marFn.equals(selectedGpo.getHorario().getMarHoraFin())) &&
        		(mieFn == null ? selectedGpo.getHorario().getMieHoraFin() == null : mieFn.equals(selectedGpo.getHorario().getMieHoraFin())) &&
        		(jueFn == null ? selectedGpo.getHorario().getJueHoraFin() == null : jueFn.equals(selectedGpo.getHorario().getJueHoraFin())) &&
        		(vieFn == null ? selectedGpo.getHorario().getVieHoraFin() == null : vieFn.equals(selectedGpo.getHorario().getVieHoraFin())) &&
        		(sabFn == null ? selectedGpo.getHorario().getSabHoraFin() == null : sabFn.equals(selectedGpo.getHorario().getSabHoraFin())) &&
        		(aulaLunes == null ? selectedGpo.getHorario().getAulaLun() == null : aulaLunes.equals(selectedGpo.getHorario().getAulaLun())) &&
        		(aulaMartes == null ? selectedGpo.getHorario().getAulaMar() == null : aulaMartes.equals(selectedGpo.getHorario().getAulaMar())) &&
        		(aulaMiercoles == null ? selectedGpo.getHorario().getAulaMie() == null : aulaMiercoles.equals(selectedGpo.getHorario().getAulaMie())) &&
        		(aulaJueves == null ? selectedGpo.getHorario().getAulaJue() == null : aulaJueves.equals(selectedGpo.getHorario().getAulaJue())) &&
        		(aulaViernes == null ? selectedGpo.getHorario().getAulaVie() == null : aulaViernes.equals(selectedGpo.getHorario().getAulaVie())) &&
        		(aulaSabado == null ? selectedGpo.getHorario().getAulaSab() == null : aulaSabado.equals(selectedGpo.getHorario().getAulaSab()))        		
        	)
        { // Verifica que se haya realizado alguna modificación al horario si no muestra mensaje (TOP)
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"No realizaste ninguna modficación del horario",null));
        	return "";
        } // Verifica que se haya realizado alguna modificación al horario si no muestra mensaje (BOTTOM)

        if (	
        		((lunIn == null ? selectedGpo.getHorario().getLunHoraIni() == null : lunIn.equals(selectedGpo.getHorario().getLunHoraIni())) && 
        		(marIn == null ? selectedGpo.getHorario().getMarHoraIni() == null : marIn.equals(selectedGpo.getHorario().getMarHoraIni())) &&
        		(mieIn == null ? selectedGpo.getHorario().getMieHoraIni() == null : mieIn.equals(selectedGpo.getHorario().getMieHoraIni())) &&
        		(jueIn == null ? selectedGpo.getHorario().getJueHoraIni() == null : jueIn.equals(selectedGpo.getHorario().getJueHoraIni())) &&
        		(vieIn == null ? selectedGpo.getHorario().getVieHoraIni() == null : vieIn.equals(selectedGpo.getHorario().getVieHoraIni())) &&
        		(sabIn == null ? selectedGpo.getHorario().getSabHoraIni() == null : sabIn.equals(selectedGpo.getHorario().getSabHoraIni())) && 
        		(lunFn == null ? selectedGpo.getHorario().getLunHoraFin() == null : lunFn.equals(selectedGpo.getHorario().getLunHoraFin())) && 
        		(marFn == null ? selectedGpo.getHorario().getMarHoraFin() == null : marFn.equals(selectedGpo.getHorario().getMarHoraFin())) &&
        		(mieFn == null ? selectedGpo.getHorario().getMieHoraFin() == null : mieFn.equals(selectedGpo.getHorario().getMieHoraFin())) &&
        		(jueFn == null ? selectedGpo.getHorario().getJueHoraFin() == null : jueFn.equals(selectedGpo.getHorario().getJueHoraFin())) &&
        		(vieFn == null ? selectedGpo.getHorario().getVieHoraFin() == null : vieFn.equals(selectedGpo.getHorario().getVieHoraFin())) &&
        		(sabFn == null ? selectedGpo.getHorario().getSabHoraFin() == null : sabFn.equals(selectedGpo.getHorario().getSabHoraFin())))   &&
        		((aulaLunes == null ? selectedGpo.getHorario().getAulaLun() != null : !aulaLunes.equals(selectedGpo.getHorario().getAulaLun())) ||
        		(aulaMartes == null ? selectedGpo.getHorario().getAulaMar() != null : !aulaMartes.equals(selectedGpo.getHorario().getAulaMar())) ||
        		(aulaMiercoles == null ? selectedGpo.getHorario().getAulaMie() != null : !aulaMiercoles.equals(selectedGpo.getHorario().getAulaMie())) ||     
        		(aulaJueves == null ? selectedGpo.getHorario().getAulaJue() != null : !aulaJueves.equals(selectedGpo.getHorario().getAulaJue())) ||
        		(aulaViernes == null ? selectedGpo.getHorario().getAulaVie() != null : !aulaViernes.equals(selectedGpo.getHorario().getAulaVie())) ||
        		(aulaSabado == null ? selectedGpo.getHorario().getAulaSab() != null : !aulaSabado.equals(selectedGpo.getHorario().getAulaSab())))		        		
        	)
        { // Si solo se modifica el aula que se usará para la materia (TOP)
            //FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"No realizaste ninguna modficación del horario",null));
        	selectedGpo.setEstado(3);
        //	return "";
        } // Si solo se modifica el aula que se usará para la materia (BOTTOM)
// *********** Verifica que no exista traslapes con las materias del mismo semestre (TOP)******************************
        if(!listTrasLun.isEmpty())
        { // Si hay traslape en lunes (TOP)
            for(Grupo g:listTrasLun)
                mensajesTraslape.add(" Hay traslape el lunes con " + g.getClaveMateria().getNombreMateria());
        } // Si hay traslape en lunes (BOTTOM)
        if(!listTrasMar.isEmpty())
        { // Si hay traslape en martes (TOP)
            for(Grupo g:listTrasMar)
                mensajesTraslape.add(" Hay traslape el martes con " + g.getClaveMateria().getNombreMateria());
        }  // Si hay traslape en martes (BOTTOM)
        if(!listTrasMie.isEmpty())
        {  // Si hay traslape en miercoles (TOP)
            for(Grupo g:listTrasMie)
                mensajesTraslape.add( " Hay traslape el miercoles con " + g.getClaveMateria().getNombreMateria());
        } // Si hay traslape en miercoles (BOTTOM)
        if(!listTrasJue.isEmpty())
        {  // Si hay traslape en jueves (TOP)
            for(Grupo g:listTrasJue)
                mensajesTraslape.add( " Hay traslape jueves con " + g.getClaveMateria().getNombreMateria());
        } // Si hay traslape en jueves (BOTTOM)
        if(!listTrasVie.isEmpty())
        { // Si hay traslape en viernes (TOP)
            for(Grupo g:listTrasVie)
                mensajesTraslape.add( " Hay traslape el viernes con " + g.getClaveMateria().getNombreMateria());
        } // Si hay traslape en viernes (BOTTOM)
        if(!listTrasSab.isEmpty())
        { // Si hay traslape en sabado (TOP)
            for(Grupo g:listTrasSab)
                mensajesTraslape.add( " Hay traslape el sabado con " + g.getClaveMateria().getNombreMateria());
        } // Si hay traslape en sabado (BOTTOM)        
        if(mensajesTraslape.isEmpty())
        { // Si no hay traslapes (TOP)
            mensajesTraslape.add("No existen taslapes");
        } // Si no hay traslapes (BOTTOM)
        
        RequestContext.getCurrentInstance().execute("PF('traslapesDlg').show()");
              
        return "";
   }// Modifica el horario para enviar a validación  (BOTTOM)
    
    public void aceptarHorarioConTraslapes()
    { // Acepta los cambios para posterior enviar el horario a validacion (TOP)
    	boolean hayModificado = false;
    	boolean faltan = false;
    	Horario2 hora2 = new Horario2();
    	hora2.setIdGrupo(selectedGpo);
    	hora2.setIdHorario(selectedGpo.getHorario().getIdHorario());
    	hora2.setLunHoraIni(selectedGpo.getHorario().getLunHoraIni());
    	hora2.setLunHoraFin(selectedGpo.getHorario().getLunHoraFin());
    	hora2.setMarHoraIni(selectedGpo.getHorario().getMarHoraIni());
    	hora2.setMarHoraFin(selectedGpo.getHorario().getMarHoraFin());
    	hora2.setMieHoraIni(selectedGpo.getHorario().getMieHoraIni());
    	hora2.setMieHoraFin(selectedGpo.getHorario().getMieHoraFin());
    	hora2.setJueHoraIni(selectedGpo.getHorario().getJueHoraIni());
    	hora2.setJueHoraFin(selectedGpo.getHorario().getJueHoraFin());
    	hora2.setVieHoraIni(selectedGpo.getHorario().getVieHoraIni());
    	hora2.setVieHoraFin(selectedGpo.getHorario().getVieHoraFin());
    	hora2.setSabHoraIni(selectedGpo.getHorario().getSabHoraIni());
    	hora2.setSabHoraFin(selectedGpo.getHorario().getSabHoraFin());
    	hora2.setAulaLun(selectedGpo.getHorario().getAulaLun());
    	hora2.setAulaMar(selectedGpo.getHorario().getAulaMar());
    	hora2.setAulaMie(selectedGpo.getHorario().getAulaMie());
    	hora2.setAulaJue(selectedGpo.getHorario().getAulaJue());
    	hora2.setAulaVie(selectedGpo.getHorario().getAulaVie());
    	hora2.setAulaVie(selectedGpo.getHorario().getAulaSab());
    	
    	listaModificados.add(hora2);
    	
    	selectedGpo.getHorario().setLunHoraIni(lunIn);
    	selectedGpo.getHorario().setLunHoraFin(lunFn);
    	selectedGpo.getHorario().setMarHoraIni(marIn);
    	selectedGpo.getHorario().setMarHoraFin(marFn);
    	selectedGpo.getHorario().setMieHoraIni(mieIn);
    	selectedGpo.getHorario().setMieHoraFin(mieFn);
    	selectedGpo.getHorario().setJueHoraIni(jueIn);
    	selectedGpo.getHorario().setJueHoraFin(jueFn);
    	selectedGpo.getHorario().setVieHoraIni(vieIn);
    	selectedGpo.getHorario().setVieHoraFin(vieFn);
    	selectedGpo.getHorario().setSabHoraIni(sabIn);
    	selectedGpo.getHorario().setSabHoraFin(sabFn);
    	selectedGpo.getHorario().setAulaLun(aulaLunes);
    	selectedGpo.getHorario().setAulaMar(aulaMartes);
    	selectedGpo.getHorario().setAulaMie(aulaMiercoles);
    	selectedGpo.getHorario().setAulaJue(aulaJueves);
    	selectedGpo.getHorario().setAulaVie(aulaViernes);
    	selectedGpo.getHorario().setAulaSab(aulaSabado);
    	
    	StringBuilder strBuild = new StringBuilder("");
        for(int i=0; i < mensajesTraslape.size();i++)
        {
    		String s = mensajesTraslape.get(i);
        	if(i < mensajesTraslape.size() -1)        	
        		strBuild.append(s + ",");        	
        	else
        		strBuild.append(s + ".");        	        		
        }
        selectedGpo.setDescripcion(strBuild.toString());
        
    	if(selectedGpo.getEstado() != 3)
    		selectedGpo.setEstado(2); // Se pone en estado modificado al grupo seleccionado

		//selectedGpo.setEstado(1); // Solo se enviara como notificacion y se confirma el grupo..
		for(Grupo g : gposProfe)
    	{ // For que valida si hay algun grupo modificado o no (TOP)
    		if(g.getEstado() == 2)
    			hayModificado = true;
    		if((g.getEstado() == 0 && g.getValidado() != null && g.getValidado() != 1 && g.getValidado() != 3) || (g.getEstado() == 0 && g.getValidado() == null))
    			faltan = true;
    	} // For que valida si hay algun grupo modificado o no (BOTTOM)
    	
    	if(!hayModificado)
    	{ // Si no hay algun grupo modificado (TOP)
    		if(faltan)
    		{
    			todosConfirmadosOAceptados = false;
    		}
    		else 
    			todosConfirmadosOAceptados = true;
    	} // Si no hay algun grupo modificado (BOTTOM)  
        else
        {
    		if(faltan) 
    			todosConfirmadosOAceptados = false;
        }        
    	logg.info(" Acepta traslapes >>> todos confirmadosoAcapetados " + todosConfirmadosOAceptados);
    } // Acepta los cambios para posterior enviar el horario a validacion (BOTTOM)
    
    public String enviarAvalidacion() throws NamingException, SQLException
    { // Envia el cambio de horario del grupo seleccionado a validacion (TOP)
    	boolean faltan = false; 
    	for(Grupo g : gposProfe)
    	{ // For que valida si hay algun grupo modificado o no (TOP)
    		if((g.getEstado() == 0 && g.getValidado() != null && g.getValidado() != 1 && g.getValidado() != 3) || (g.getEstado() == 0 && g.getValidado() == null))
    			faltan = true;
    	} // For que valida si hay algun grupo modificado o no (BOTTOM)    	    	
    
    	if(faltan)
    	{ // Si hubo modificaciones o faltan grupos por confirmar o modificar (TOP)
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Faltan materias por confirmar o modificar",null));
            todosConfirmadosOAceptados = false;
            return "";
    	}      	         
        else
        {
    		for(Grupo g: gposProfe)
    		{ // For para verificar los grupos del profesor (TOP)
    			if(g.getEstado() == 1)
    			{ // Si el grupo fue confirmado (TOP)
    				g.setValidado(3);
    				gpoEJB.edit(g);
    			} // Si el grupo fue confirmado (BOTTOM)
    			else if(g.getEstado() == 3)
    			{ // Si solo se modificaron las aulas del grupo (TOP)
    				g.setValidado(3);
    				gpoEJB.edit(g);
    				HorarioEJB.edit(g.getHorario());
                    enviarMail(g);    				    				
    			} // Si solo se modificaron las aulas del grupo (BOTTOM)
    			else if(g.getEstado() == 2)
    			{ // Si el grupo fue modificado (TOP)
    				try
    			    {    					
    					g.setValidado(0); // Se marca como grupo para validación
    			    	for(Horario2 h : listaModificados)
    			    	{
    			    		if(g.getHorario().getIdHorario() == h.getIdHorario())
    			    			hora2EJB.create(h);
    			    	}
    			    	gpoEJB.edit(g);
    			    	HorarioEJB.edit(g.getHorario());  		
                        NotificacionesCoord coord = new NotificacionesCoord();
                        coord.setNotificacionesCoordPK(new NotificacionesCoordPK(g.getIdGrupo(),new Date()));
                        coord.setDescripcion(g.getDescripcion());
                        coord.setEstado(0); // Estado no leido 
                        
                        notifEJB.create(coord);
                        enviarMail(g);
    		    		conGrupoAValidar = true;    			    	
    			    }
    			    catch(PersistenceException exP)
    			    {
    			    	logg.info("Ocurrio un error al guardar el grupo " + exP.toString());
    			    }
    			} // Si el grupo fue modificado (BOTTOM)
    		} // For para verificar los grupos del profesor (BOTTOM)
    	} // Si hubo modificaciones o faltan grupos por confirmar o modificar (BOTTOM)
    	return "";
    } // Envia el cambio de horario del grupo seleccionado a validacion (BOTTOM)
    
    public String confirmarTodoElHorario()
    { // Si hay pura confirmacion en el ultimo ingreso al sistema (TOP)
    	for(Grupo g : gposProfe)
        { // For que valida si hay algun grupo modificado o no (TOP)
    		if(g.getValidado() != null && g.getValidado() == 2)
    		{
    			hora2EJB.remove(hora2EJB.find(g.getHorario().getIdHorario()));
    			g.setValidado(3);
    		}
        	if(g.getEstado() == 1)
        	{
       			g.setValidado(3); // Se setea el grupo a confirmado
       			HorarioEJB.edit(g.getHorario()); 
       			gpoEJB.edit(g);
       		}
        	else if(g.getEstado() == 3)
        	{
       			g.setValidado(3); // Se setea el grupo a confirmado 
       			gpoEJB.edit(g);
       			HorarioEJB.edit(g.getHorario());
       			enviarMail(g);
        	}
       	} // For que valida si hay algun grupo modificado o no (BOTTOM)    	    	
    	seHaConfirmadoTodo = true;
    	return "profesor?faces-redirect=true";
    } // Si hay pura confirmacion en el ultimo ingreso al sistema (BOTTOM)
    
    public String generarFormatoNo1() throws NamingException, SQLException
    { // Si odos los grupos fueron aceptados y/0 confirmados IMPRIME (TOP)

    	logg.info(">>>>>>	Se generara el formato # 1.1 ....");
    	           		
   		Connection conexion = null;
        String outputFileName = "formato_1.pdf";
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("mydb");
        conexion = ds.getConnection();
        conexion.setAutoCommit(true);        
        FacesContext context = FacesContext.getCurrentInstance();
        File reportFile = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath(rutajasper));
           
        if(reportFile.exists())
        {
        	Map<String,Object> parameters = new HashMap<>();
        	parameters.put("rfc_profe",profe.getRfcProfesor());
        	try
        	{
	            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),parameters, conexion);
	            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
	            response.setHeader ("Content-Disposition", "attachment; filename=" + outputFileName);
	            response.setContentType ("application/pdf");                                    
	            ServletOutputStream stream = response.getOutputStream();
	            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
	            context.getApplication().getStateManager().saveView(context);
	            stream.flush();
	            stream.close();
	            context.responseComplete();
	            return "profesor?faces-redirect=true";            
            //logg.info(">>>> ENTRO AL TRY PERO ALGO PASO .... " + response.getStatus());
        	}    
	        catch(IOException ioEx)
	        {
	            System.out.println("Ocurrio un error al leer el archivo JASPER " + ioEx.toString());            
	        }            
	        catch(JRException jreEx)
	        {
	            System.out.println("Ocurrio un error al generar el reporte " + jreEx.toString());            
	        }
        }
        else
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("NO SE ENCONTRO EL ARCHIVO ESPECIFICO"));
            
        return "";
    } // Si odos los grupos fueron aceptados y/0 confirmados IMPRIME (BOTTOM)
    
    public void enviarMail(Grupo g)
    {
        final String user = "gibran_skato@hotmail.com";
        final String password ="sirenito_88";
        
        if(g.getEstado() == 2)
        {
        	mensaje = "El profesor <b>" + g.getRfcProfesor().getNombreProfe() + " " + g.getRfcProfesor().getApePatProfe() + " " + g.getRfcProfesor().getApeMatProfe() + 
        		"</b> ha enviado el grupo <b>" + g.getNombre() +  "</b> de la materia <b>" + g.getClaveMateria().getNombreMateria() + "</b> para validacion \n " +
        				 "Ingrese a la liga para validar el (los) grupo(s)  http://localhost:8282/SIPLAFI-WEB/index.jsf  o http://localhost:8080/SIPLAFI-WEB/index.jsf ";
        	subject = "Nuevo grupo para validacion";
        }
        else if(g.getEstado() == 3)
        {
            mensaje = "El profesor <b>" + g.getRfcProfesor().getNombreProfe() + " " + g.getRfcProfesor().getApePatProfe() + " " + g.getRfcProfesor().getApeMatProfe() + 
            		"</b> modifico solo el tipo de aula para el grupo <b>" + g.getNombre() +  "</b> de la materia <b>" + g.getClaveMateria().getNombreMateria() + "</b> \n " +
            				 "Ingrese a la liga para ver la planilla http://localhost:8282/SIPLAFI-WEB/index.jsf  o http://localhost:8080/SIPLAFI-WEB/index.jsf ";
        	subject = "Se realizó la modificacion solo de aulas";            
        }
        para = "giresa.ico@gmail.com";
        
        props = new Properties();
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
        session = Session.getInstance(props,aut);                
        try 
        {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(para));
            message.setSubject(subject);
            message.setContent(mensaje,"text/html");
            Transport.send(message);
            logg.info("EL CORREO FUE ENVIADO EXITOSAMENTE");
            //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(""));

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }    

    public String cerrarSession()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "index?faces-redirect=true";
    }

	public DateFormat getFormat() 
	{
		return format;
	}

	public void setFormat(DateFormat format) 
	{
		this.format = format;
	}

	public Profesor getProfe()
	{
		return profe;
	}

	public void setProfe(Profesor profe) 
	{
		this.profe = profe;
	}

	public String getNombreProfe() 
	{
		return nombreProfe;
	}

	public void setNombreProfe(String nombreProfe) 
	{
		this.nombreProfe = nombreProfe;
	}

	public List<Grupo> getGposProfe() 
	{
		return gposProfe;
	}

	public void setGposProfe(List<Grupo> gposProfe) 
	{
		this.gposProfe = gposProfe;
	}

	public void setLogin(LoginBean login) 
	{
		this.login = login;
	}

	public Grupo getSelectedGpo() 
	{
		return selectedGpo;
	}

	public void setSelectedGpo(Grupo selectedGpo) 
	{
		this.selectedGpo = selectedGpo;
	}

	public List<Grupo> getListGposInSemester() 
	{
		return listGposInSemester;
	}

	public void setListGposInSemester(List<Grupo> listGposInSemester) 
	{
		this.listGposInSemester = listGposInSemester;
	}

	public List<Aula> getListAula() 
	{
		return listAula;
	}

	public void setListAula(List<Aula> listAula) 
	{
		this.listAula = listAula;
	}

	public Date getLunIn() 
	{
		return lunIn;
	}
	public void setLunIn(Date lunIn) 
	{
		this.lunIn = lunIn;
	}
	public Date getLunFn() 
	{
		return lunFn;
	}
	public void setLunFn(Date lunFn) 
	{
		this.lunFn = lunFn;
	}
	public Date getMarIn() 
	{
		return marIn;
	}
	public void setMarIn(Date marIn) 
	{
		this.marIn = marIn;
	}
	public Date getMarFn() 
	{
		return marFn;
	}
	public void setMarFn(Date marFn) 
	{
		this.marFn = marFn;
	}
	public Date getMieIn() 
	{
		return mieIn;
	}
	public void setMieIn(Date mieIn) 
	{
		this.mieIn = mieIn;
	}
	public Date getMieFn() 
	{
		return mieFn;
	}
	public void setMieFn(Date mieFn) 
	{
		this.mieFn = mieFn;
	}
	public Date getJueIn() 
	{
		return jueIn;
	}
	public void setJueIn(Date jueIn) 
	{
		this.jueIn = jueIn;
	}
	public Date getJueFn() 
	{
		return jueFn;
	}
	public void setJueFn(Date jueFn) 
	{
		this.jueFn = jueFn;
	}
	public Date getVieIn() 
	{
		return vieIn;
	}
	public void setVieIn(Date vieIn) 
	{
		this.vieIn = vieIn;
	}
	public Date getVieFn() 
	{
		return vieFn;
	}
	public void setVieFn(Date vieFn) 
	{
		this.vieFn = vieFn;
	}
	public Date getSabIn() 
	{
		return sabIn;
	}
	public void setSabIn(Date sabIn) 
	{
		this.sabIn = sabIn;
	}
	public Date getSabFn() 
	{
		return sabFn;
	}
	public void setSabFn(Date sabFn) 
	{
		this.sabFn = sabFn;
	}
	public List<String> getMensajesTraslape() 
	{
		return mensajesTraslape;
	}
	public void setMensajesTraslape(List<String> mensajesTraslape) 
	{
		this.mensajesTraslape = mensajesTraslape;
	}
	public String getMensajeBoton() 
	{
		return mensajeBoton;
	}
	public void setMensajeBoton(String mensajeBoton) 
	{
		this.mensajeBoton = mensajeBoton;
	}

	public boolean isTodosConfirmadosOAceptados() 
	{
		return todosConfirmadosOAceptados;
	}

	public void setTodosConfirmadosOAceptados(boolean todosConfirmadosOAceptados) 
	{
		this.todosConfirmadosOAceptados = todosConfirmadosOAceptados;
	}

	public boolean isConGrupoAValidar() 
	{
		return conGrupoAValidar;
	}

	public void setConGrupoAValidar(boolean conGrupoAValidar) 
	{
		this.conGrupoAValidar = conGrupoAValidar;
	}

	public boolean isSeHaConfirmadoTodo() 
	{
		return seHaConfirmadoTodo;
	}

	public void setSeHaConfirmadoTodo(boolean seHaConfirmadoTodo) 
	{
		this.seHaConfirmadoTodo = seHaConfirmadoTodo;
	}

	public String getAulaLunes() 
	{
		return aulaLunes;
	}

	public void setAulaLunes(String aulaLunes) 
	{
		this.aulaLunes = aulaLunes;
	}

	public String getAulaMartes() 
	{
		return aulaMartes;
	}

	public void setAulaMartes(String aulaMartes) 
	{
		this.aulaMartes = aulaMartes;
	}

	public String getAulaMiercoles() 
	{
		return aulaMiercoles;
	}

	public void setAulaMiercoles(String aulaMiercoles) 
	{
		this.aulaMiercoles = aulaMiercoles;
	}

	public String getAulaJueves() 
	{
		return aulaJueves;
	}

	public void setAulaJueves(String aulaJueves) 
	{
		this.aulaJueves = aulaJueves;
	}

	public String getAulaViernes() 
	{
		return aulaViernes;
	}

	public void setAulaViernes(String aulaViernes) 
	{
		this.aulaViernes = aulaViernes;
	}

	public String getAulaSabado() 
	{
		return aulaSabado;
	}

	public void setAulaSabado(String aulaSabado) 
	{
		this.aulaSabado = aulaSabado;
	}

}
