
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
import fi.uaemex.ejbs.GrupoRespaldoFacade;
import fi.uaemex.ejbs.NotificacionesCoordFacade;
import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Aula;
import fi.uaemex.entities.AulaSalonDia;
import fi.uaemex.entities.Dia;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.GrupoRespaldo;
import fi.uaemex.entities.GrupoRespaldoPK;
import fi.uaemex.entities.NotificacionesCoord;
import fi.uaemex.entities.Profesor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean(name = "profesorBean")
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
    private Aula aulaLunes;												// Guarda el aula para el Lunes
    private Aula aulaMartes;											// Guarda el aula para el Martes
    private Aula aulaMiercoles;											// Guarda el aula para el Miercoles
    private Aula aulaJueves;											// Guarda el aula para el Jueves	
    private Aula aulaViernes;											// Guarda el aula para el Viernes
    private Aula aulaSabado;    										// Guarda el aula para el Sabado
    private List<String> mensajesTraslape;								// Lista con todos los traslapes que se encontraron al cambiar el horario
    private String mensajeBoton;										// Mensaje del boton para enviar a validacion o imprimir el formato #1 
    private Profesor profe;                         					// Profesor logged
    private String nombreProfe;                     					// nombre para mostrar del profesor
    private List<Grupo> gposProfe;                  					// obtiene la lista de los grupos del profesor
    private List<Grupo> listGposInSemester;								// Almacena los grupos de las materias en el mismo horario del grupo seleccionado para modificar
    private List<Aula> listAula; 										// Lista de las aulas requeridas para la clase
    private List<GrupoRespaldo> listaModificados;						// almacena todos los horaios modificados para guardarlos al enviar a validacion..
    private Grupo selectedGpo;											// Almacena el grupo que es seleccionado para editar el horario
    private boolean todosConfirmadosOAceptados;							// Obtiene verdadero si todos los grupos del profesor estan confirmados o aceptados
    private boolean conGrupoAValidar;									// Obtiene verdadero si algun grupo necesita ser validado
    private boolean seHaConfirmadoTodo;									// Si todos los grupos estan confirmados o aceptados
    @EJB private ProfesorFacade profFacade;              				// EJB para acceso a datos del profesor
    @EJB private GrupoFacade gpoEJB;                     				// EJB para acceso a datos del grupo
    @EJB private GrupoRespaldoFacade gpoRespEJB;						// EJB para acceso a datos del grupo de respaldo..
    @EJB private AulaFacade aulaEJB;									// EJB para acceso a datos de la entidad aula
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
	   //gposProfe = profe.getGrupoList();
	   for(Grupo g: profe.getGrupoList())
	   {
		   if(Integer.parseInt(g.getPeriodos().getActual()) == 1)
			   gposProfe.add(g);
	   }	   
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
    	listGposInSemester = gpoEJB.findGrupoSemestre(selectedGpo.getMateria().getSemestre(), selectedGpo.getGrupoPK()); // Se obtienen las materias o grupos en el mismo semestre
    	/****** Se utilizan variables auxiliares para el horario del grupo seleccionado porque maraca error en nulos, el componente pcalendar (TOP) ********/
    	
    	lunIn = selectedGpo.getLunHoraIni();
    	lunFn = selectedGpo.getLunHoraFin();
    	marIn = selectedGpo.getMarHoraIni();
    	marFn = selectedGpo.getMarHoraFin();
    	mieIn = selectedGpo.getMieHoraIni();
    	mieFn = selectedGpo.getMieHoraFin();
    	jueIn = selectedGpo.getJueHoraIni();
    	jueFn = selectedGpo.getJueHoraFin();
    	vieIn = selectedGpo.getVieHoraIni();
    	vieFn = selectedGpo.getVieHoraFin();
    	sabIn = selectedGpo.getSabHoraIni();
    	sabFn = selectedGpo.getSabHoraFin();
    	
    	aulaLunes = selectedGpo.getAulaPordia(1);
    	aulaMartes =  selectedGpo.getAulaPordia(2);
    	aulaMiercoles =  selectedGpo.getAulaPordia(3);
    	aulaJueves =  selectedGpo.getAulaPordia(4);
    	aulaViernes =  selectedGpo.getAulaPordia(5);
    	aulaSabado =  selectedGpo.getAulaPordia(6);
    	
    	/****** Se utilizan variables auxiliares para el horario del grupo seleccionado porque maraca error en nulos, el componente pcalendar (TOP) ********/    	
    	logg.info(">>> se selecciono un grupo ..." + selectedGpo.getGrupoPK().getNombre() + " oooo.... " + listGposInSemester.size());    	
    	logg.info("aula lun: " + aulaLunes + "-- aulaSab: " + aulaSabado);
    	RequestContext.getCurrentInstance().execute("PF('dlgModHora').show()");    	    	
    } // Al seleccionar grupo para modificar abre el dialog para modificarlo (BOTTOM)
       
   public void confirmarHorario(Grupo gpo)
   { // Marca el grupo para confirmacion (TOP)
    	this.selectedGpo = gpo;
        boolean hayModificado = false;
        boolean faltan = false;
        
        if(selectedGpo.getValidado() != null && selectedGpo.getValidado() == 2)
        { // Si el horario del grupo ya fue modificado y rechazado por la coordinación (TOP)
        	//Horario2 hora = hora2EJB.find(selectedGpo.getHorario().getIdHorario());
        	logg.info(">> SI ENTRO AQIU ENTONCES FALLO LA CONSULTA ... ");
        	GrupoRespaldo hora = selectedGpo.getGrupoRespaldo();
        	logg.info(">>>> horario respaldo.... >> " + hora);
        	if(hora != null)
        	{
            	selectedGpo.setLunHoraIni(hora.getLunHoraIni());
            	selectedGpo.setLunHoraFin(hora.getLunHoraFin());
            	selectedGpo.setMarHoraIni(hora.getMarHoraIni());
            	selectedGpo.setMarHoraFin(hora.getMarHoraFin());
            	selectedGpo.setMieHoraIni(hora.getMieHoraIni());
            	selectedGpo.setMieHoraFin(hora.getMieHoraFin());
            	selectedGpo.setJueHoraIni(hora.getJueHoraIni());
            	selectedGpo.setJueHoraFin(hora.getJueHoraFin());
            	selectedGpo.setVieHoraIni(hora.getVieHoraIni());
            	selectedGpo.setVieHoraFin(hora.getVieHoraFin());
            	selectedGpo.setSabHoraIni(hora.getSabHoraIni());
            	selectedGpo.setSabHoraFin(hora.getSabHoraFin());
            	logg.info("Entro aqui>>> para regresar al horario anterior.");
        	}
        } // Si el horario del grupo ya fue modificado y rechazado por la coordinación (BOTTOM)
        
        if(selectedGpo.getEstado() == 2 ) // Si ya fue modificado
        	FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("El grupo ya ha sido modificado, si desea cambiarlo eliga la opción modificar "));
        else
        {
        	selectedGpo.setEstado(1);; // Estado Confirmado 
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
        		{
        			todosConfirmadosOAceptados = true;
        		}
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
        logg.info(">>>>>MISMAS AULAS >>>> " + mismasAulas());
        if(selectedGpo.getValidado() != null && selectedGpo.getValidado() == 2)
        { // Si el grupo ya fue validado y fue rechazado (TOP)
        	
        	GrupoRespaldo horarioRespaldo = selectedGpo.getGrupoRespaldo();// Se busca si se guardo un horario anterior
        	
        	if(horarioRespaldo != null)
        	{ // Si el horario de respaldo es encontrado (TOP)
        		if
        		(
        			(lunIn == null ? horarioRespaldo.getLunHoraIni() == null : lunIn.equals(horarioRespaldo.getLunHoraIni())) && 
        			(marIn == null ? horarioRespaldo.getMarHoraIni() == null : marIn.equals(horarioRespaldo.getMarHoraIni())) &&
        			(mieIn == null ? horarioRespaldo.getMieHoraIni() == null : mieIn.equals(horarioRespaldo.getMieHoraIni())) &&
        			(jueIn == null ? horarioRespaldo.getJueHoraIni() == null : jueIn.equals(horarioRespaldo.getJueHoraIni())) &&
        			(vieIn == null ? horarioRespaldo.getVieHoraIni() == null : vieIn.equals(horarioRespaldo.getVieHoraIni())) &&
        			(sabIn == null ? horarioRespaldo.getSabHoraIni() == null : sabIn.equals(horarioRespaldo.getSabHoraIni())) && 
        			(lunFn == null ? horarioRespaldo.getLunHoraFin() == null : lunFn.equals(horarioRespaldo.getLunHoraFin())) && 
        			(marFn == null ? horarioRespaldo.getMarHoraFin() == null : marFn.equals(horarioRespaldo.getMarHoraFin())) &&
        			(mieFn == null ? horarioRespaldo.getMieHoraFin() == null : mieFn.equals(horarioRespaldo.getMieHoraFin())) &&
        			(jueFn == null ? horarioRespaldo.getJueHoraFin() == null : jueFn.equals(horarioRespaldo.getJueHoraFin())) &&
        			(vieFn == null ? horarioRespaldo.getVieHoraFin() == null : vieFn.equals(horarioRespaldo.getVieHoraFin())) &&
        			(sabFn == null ? horarioRespaldo.getSabHoraFin() == null : sabFn.equals(horarioRespaldo.getSabHoraFin()))
        		)
        		{ // Verifica que se haya realizado alguna modificación al horario si no muestra mensaje (TOP)
        			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"Si va a elegir el horario anterior,cancele y vaya a la opcion deshacer modificación",null));
        			return "";
        		} // Verifica que se haya realizado alguna modificación al horario si no muestra mensaje (BOTTOM)
        	} // Si el horario de respaldo es encontrado (BOTTOM)
        } // Si el grupo ya fue validado y fue rechazado (BOTTOM)
		List<Grupo> listTrasLun = gpoEJB.findTraslapeLun(lunIn,lunFn,selectedGpo.getGrupoPK(),selectedGpo.getMateria().getSemestre());
        List<Grupo> listTrasMar = gpoEJB.findTraslapeMar(marIn,marFn,selectedGpo.getGrupoPK(),selectedGpo.getMateria().getSemestre());
        List<Grupo> listTrasMie = gpoEJB.findTraslapeMie(mieIn,mieFn,selectedGpo.getGrupoPK(),selectedGpo.getMateria().getSemestre());
        List<Grupo> listTrasJue = gpoEJB.findTraslapeJue(jueIn,jueFn,selectedGpo.getGrupoPK(),selectedGpo.getMateria().getSemestre());
        List<Grupo> listTrasVie = gpoEJB.findTraslapeVie(vieIn,vieFn,selectedGpo.getGrupoPK(),selectedGpo.getMateria().getSemestre());
        List<Grupo> listTrasSab = gpoEJB.findTraslapeSab(sabIn,sabFn,selectedGpo.getGrupoPK(),selectedGpo.getMateria().getSemestre());
// // ************* valida que si selecciona ya sea hora de inicio o de fin tiene que seleccionar hora de fin o de inicio (TOP) ***********************
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
            else if(aulaLunes == null)
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
            else if(aulaMartes == null)
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
            else if(aulaMiercoles == null)
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
            else if(aulaJueves == null)
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
            else if(aulaViernes==null)
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
            else if(aulaSabado == null)
            {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione el tipo de aula para el sabado",null));
                return "";
            }
        } // Si se ingresa hora de inicio y hora final en sabado (BOTTOM)
        horas = horas + minutos;
        if(horas != (float)selectedGpo.getMateria().getHoras())
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR," el total de horas de la materia debe ser " + selectedGpo.getMateria().getHoras() + " la suma actual es : " + horas,null));
            return "";    
        }
//// ******  valida que la suma de horas corresponda a las horas de la materia y el horario de lunes a jueves no puede ser mayor a 2.5 horas (BOTTOM) 
        if (
        		(lunIn == null ? selectedGpo.getLunHoraIni() == null : lunIn.equals(selectedGpo.getLunHoraIni())) && 
        		(marIn == null ? selectedGpo.getMarHoraIni() == null : marIn.equals(selectedGpo.getMarHoraIni())) &&
        		(mieIn == null ? selectedGpo.getMieHoraIni() == null : mieIn.equals(selectedGpo.getMieHoraIni())) &&
        		(jueIn == null ? selectedGpo.getJueHoraIni() == null : jueIn.equals(selectedGpo.getJueHoraIni())) &&
        		(vieIn == null ? selectedGpo.getVieHoraIni() == null : vieIn.equals(selectedGpo.getVieHoraIni())) &&
        		(sabIn == null ? selectedGpo.getSabHoraIni() == null : sabIn.equals(selectedGpo.getSabHoraIni())) && 
        		(lunFn == null ? selectedGpo.getLunHoraFin() == null : lunFn.equals(selectedGpo.getLunHoraFin())) && 
        		(marFn == null ? selectedGpo.getMarHoraFin() == null : marFn.equals(selectedGpo.getMarHoraFin())) &&
        		(mieFn == null ? selectedGpo.getMieHoraFin() == null : mieFn.equals(selectedGpo.getMieHoraFin())) &&
        		(jueFn == null ? selectedGpo.getJueHoraFin() == null : jueFn.equals(selectedGpo.getJueHoraFin())) &&
        		(vieFn == null ? selectedGpo.getVieHoraFin() == null : vieFn.equals(selectedGpo.getVieHoraFin())) &&
        		(sabFn == null ? selectedGpo.getSabHoraFin() == null : sabFn.equals(selectedGpo.getSabHoraFin())) &&
        		(mismasAulas())
        	)
        { // Verifica que se haya realizado alguna modificación al horario si no muestra mensaje (TOP)
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"No realizaste ninguna modficación del horario",null));
        	return "";
        } // Verifica que se haya realizado alguna modificación al horario si no muestra mensaje (BOTTOM)

        if (	
        		((lunIn == null ? selectedGpo.getLunHoraIni() == null : lunIn.equals(selectedGpo.getLunHoraIni())) && 
        		(marIn == null ? selectedGpo.getMarHoraIni() == null : marIn.equals(selectedGpo.getMarHoraIni())) &&
        		(mieIn == null ? selectedGpo.getMieHoraIni() == null : mieIn.equals(selectedGpo.getMieHoraIni())) &&
        		(jueIn == null ? selectedGpo.getJueHoraIni() == null : jueIn.equals(selectedGpo.getJueHoraIni())) &&
        		(vieIn == null ? selectedGpo.getVieHoraIni() == null : vieIn.equals(selectedGpo.getVieHoraIni())) &&
        		(sabIn == null ? selectedGpo.getSabHoraIni() == null : sabIn.equals(selectedGpo.getSabHoraIni())) && 
        		(lunFn == null ? selectedGpo.getLunHoraFin() == null : lunFn.equals(selectedGpo.getLunHoraFin())) && 
        		(marFn == null ? selectedGpo.getMarHoraFin() == null : marFn.equals(selectedGpo.getMarHoraFin())) &&
        		(mieFn == null ? selectedGpo.getMieHoraFin() == null : mieFn.equals(selectedGpo.getMieHoraFin())) &&
        		(jueFn == null ? selectedGpo.getJueHoraFin() == null : jueFn.equals(selectedGpo.getJueHoraFin())) &&
        		(vieFn == null ? selectedGpo.getVieHoraFin() == null : vieFn.equals(selectedGpo.getVieHoraFin())) &&
        		(sabFn == null ? selectedGpo.getSabHoraFin() == null : sabFn.equals(selectedGpo.getSabHoraFin()))) && 
        		(!mismasAulas())	        		
        	)
        { // Si solo se modifica el aula que se usará para la materia (TOP)
            //FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"No realizaste ninguna modficación del horario",null));
        	selectedGpo.setEstado(3);
        //	return "";
        } // Si solo se modifica el aula que se usará para la materia (BOTTOM)
//// *********** Verifica que no exista traslapes con las materias del mismo semestre (TOP)******************************
        if(!listTrasLun.isEmpty())
        { // Si hay traslape en lunes (TOP)
            for(Grupo g:listTrasLun)
                mensajesTraslape.add(" Hay traslape el lunes con " + g.getMateria().getNombreMateria());
        } // Si hay traslape en lunes (BOTTOM)
        if(!listTrasMar.isEmpty())
        { // Si hay traslape en martes (TOP)
            for(Grupo g:listTrasMar)
                mensajesTraslape.add(" Hay traslape el martes con " + g.getMateria().getNombreMateria());
        }  // Si hay traslape en martes (BOTTOM)
        if(!listTrasMie.isEmpty())
        {  // Si hay traslape en miercoles (TOP)
            for(Grupo g:listTrasMie)
                mensajesTraslape.add( " Hay traslape el miercoles con " + g.getMateria().getNombreMateria());
        } // Si hay traslape en miercoles (BOTTOM)
        if(!listTrasJue.isEmpty())
        {  // Si hay traslape en jueves (TOP)
            for(Grupo g:listTrasJue)
                mensajesTraslape.add( " Hay traslape jueves con " + g.getMateria().getNombreMateria());
        } // Si hay traslape en jueves (BOTTOM)
        if(!listTrasVie.isEmpty())
        { // Si hay traslape en viernes (TOP)
            for(Grupo g:listTrasVie)
                mensajesTraslape.add( " Hay traslape el viernes con " + g.getMateria().getNombreMateria());
        } // Si hay traslape en viernes (BOTTOM)
        if(!listTrasSab.isEmpty())
        { // Si hay traslape en sabado (TOP)
            for(Grupo g:listTrasSab)
                mensajesTraslape.add( " Hay traslape el sabado con " + g.getMateria().getNombreMateria());
        } // Si hay traslape en sabado (BOTTOM)        
        if(mensajesTraslape.isEmpty())
        { // Si no hay traslapes (TOP)
            mensajesTraslape.add("No existen taslapes");
        } // Si no hay traslapes (BOTTOM)
        
        RequestContext.getCurrentInstance().execute("PF('traslapesDlg').show()");
              
        return "";
   }// Modifica el horario para enviar a validación  (BOTTOM)
    
   public boolean mismasAulas()
   { // Verifica si se modifican las aulas del grupo seleccionad (TOP)
	   for(AulaSalonDia a : selectedGpo.getAulaSalonDiaList())
	   {		   
		   if(a.getDia().getIdDia() == 1 && !a.getIdAula().equals(aulaLunes))
		   {
			   return false;
		   }
		   else if(a.getDia().getIdDia() == 2 && !a.getIdAula().equals(aulaMartes))
		   {
			   return false;		   
		   }
		   else if(a.getDia().getIdDia() == 3 && !a.getIdAula().equals(aulaMiercoles))
		   {
			   return false;		   
		   }
		   else if(a.getDia().getIdDia() == 4 && !a.getIdAula().equals(aulaJueves))
		   {
			   return false;		   
		   }
		   else if(a.getDia().getIdDia() == 5 && !a.getIdAula().equals(aulaViernes))
		   {
			   return false;		   
		   }
		   else if(a.getDia().getIdDia() == 6 && !a.getIdAula().equals(aulaSabado))
		   {
			   return false;		   
		   }
	   }
	   return true;
   	} // Verifica si se modifican las aulas del grupo seleccionad (BOTTOM)
   
    public void aceptarHorarioConTraslapes()
    { // Acepta los cambios para posterior enviar el horario a validacion (TOP)
    	boolean hayModificado = false;
    	boolean faltan = false;
		GrupoRespaldo hora2 = new GrupoRespaldo();  
		if(selectedGpo.getEstado() != 3)
    	{ // Si se modifico el horario (TOP)
    		hora2.setGrupoRespaldoPK(new GrupoRespaldoPK(selectedGpo.getGrupoPK().getClaveMateria(),selectedGpo.getGrupoPK().getPeriodo(),selectedGpo.getGrupoPK().getNombre()));    	
    		hora2.setLunHoraIni(selectedGpo.getLunHoraIni());
    		hora2.setLunHoraFin(selectedGpo.getLunHoraFin());
    		hora2.setMarHoraIni(selectedGpo.getMarHoraIni());
    		hora2.setMarHoraFin(selectedGpo.getMarHoraFin());
    		hora2.setMieHoraIni(selectedGpo.getMieHoraIni());
    		hora2.setMieHoraFin(selectedGpo.getMieHoraFin());
    		hora2.setJueHoraIni(selectedGpo.getJueHoraIni());
    		hora2.setJueHoraFin(selectedGpo.getJueHoraFin());
    		hora2.setVieHoraIni(selectedGpo.getVieHoraIni());
    		hora2.setVieHoraFin(selectedGpo.getVieHoraFin());
    		hora2.setSabHoraIni(selectedGpo.getSabHoraIni());
    		hora2.setSabHoraFin(selectedGpo.getSabHoraFin());
    		        	
        	selectedGpo.setLunHoraIni(lunIn);
        	selectedGpo.setLunHoraFin(lunFn);
        	selectedGpo.setMarHoraIni(marIn);
        	selectedGpo.setMarHoraFin(marFn);
        	selectedGpo.setMieHoraIni(mieIn);
        	selectedGpo.setMieHoraFin(mieFn);
        	selectedGpo.setJueHoraIni(jueIn);
        	selectedGpo.setJueHoraFin(jueFn);
        	selectedGpo.setVieHoraIni(vieIn);
        	selectedGpo.setVieHoraFin(vieFn);
        	selectedGpo.setSabHoraIni(sabIn);
        	selectedGpo.setSabHoraFin(sabFn);
        	
        	if(!mismasAulas())
        	{ // Si tambien se cambian las aulas se respalda (TOP)
        		if(aulaLunes != null ) hora2.setAulaLun(aulaLunes);
        		if(aulaMartes != null ) hora2.setAulaMar(aulaMartes);
        		if(aulaMiercoles != null ) hora2.setAulaMie(aulaMiercoles);
        		if(aulaJueves != null ) hora2.setAulaJue(aulaJueves);        		
        		if(aulaViernes != null ) hora2.setAulaVie(aulaViernes);        		
        		if(aulaSabado != null ) hora2.setAulaSab(aulaSabado);        	
        	}// Si tambien se cambian las aulas se respalda (BOTTOM)
    	} // Si se modifico el horario (BOTTOM)   	
    	else
    	{
    		hora2.setGrupoRespaldoPK(new GrupoRespaldoPK(selectedGpo.getGrupoPK().getClaveMateria(),selectedGpo.getGrupoPK().getPeriodo(),selectedGpo.getGrupoPK().getNombre()));    	
    		if(aulaLunes != null ) hora2.setAulaLun(aulaLunes);
    		if(aulaMartes != null ) hora2.setAulaMar(aulaMartes);
    		if(aulaMiercoles != null ) hora2.setAulaMie(aulaMiercoles);
    		if(aulaJueves != null ) hora2.setAulaJue(aulaJueves);		
    		if(aulaViernes != null ) hora2.setAulaVie(aulaViernes);        		
    		if(aulaSabado != null ) hora2.setAulaSab(aulaSabado);
    	}
		
		if(selectedGpo.getValidado() == null || selectedGpo.getValidado() != 2)
			listaModificados.add(hora2);
    	
    	if(!mismasAulas())
    	{ // Si se cambiaron las aulas (TOP)
        	List<AulaSalonDia> listaAulSal = new ArrayList<>();    		
	    	if(aulaLunes != null)
	    	{	    		
	    		AulaSalonDia asd = new AulaSalonDia(1,selectedGpo.getGrupoPK().getClaveMateria(),selectedGpo.getGrupoPK().getPeriodo(),selectedGpo.getGrupoPK().getNombre());
	    		asd.setIdAula(aulaLunes);
	    		asd.setDia(new Dia(1));
	    		listaAulSal.add(asd);
	    	}
	    	if(aulaMartes != null)
	    	{
	    		AulaSalonDia asd = new AulaSalonDia(2,selectedGpo.getGrupoPK().getClaveMateria(),selectedGpo.getGrupoPK().getPeriodo(),selectedGpo.getGrupoPK().getNombre());
	    		asd.setIdAula(aulaMartes);
	    		asd.setDia(new Dia(2));
	    		listaAulSal.add(asd);
	    	}   
	    	if(aulaMiercoles != null)
	    	{
	    		AulaSalonDia asd = new AulaSalonDia(3,selectedGpo.getGrupoPK().getClaveMateria(),selectedGpo.getGrupoPK().getPeriodo(),selectedGpo.getGrupoPK().getNombre());
	    		asd.setIdAula(aulaMiercoles);
	    		asd.setDia(new Dia(3));
	    		listaAulSal.add(asd);
	    	}   
	    	if(aulaJueves != null)
	    	{
	    		AulaSalonDia asd = new AulaSalonDia(4,selectedGpo.getGrupoPK().getClaveMateria(),selectedGpo.getGrupoPK().getPeriodo(),selectedGpo.getGrupoPK().getNombre());
	    		asd.setIdAula(aulaJueves);
	    		asd.setDia(new Dia(4));
	    		listaAulSal.add(asd);
	    	}   
	    	if(aulaViernes != null)
	    	{
	    		AulaSalonDia asd = new AulaSalonDia(5,selectedGpo.getGrupoPK().getClaveMateria(),selectedGpo.getGrupoPK().getPeriodo(),selectedGpo.getGrupoPK().getNombre());
	    		asd.setIdAula(aulaViernes);
	    		asd.setDia(new Dia(5));
	    		listaAulSal.add(asd);
	    	}   
	    	if(aulaSabado != null)
	    	{
	    		AulaSalonDia asd = new AulaSalonDia(6,selectedGpo.getGrupoPK().getClaveMateria(),selectedGpo.getGrupoPK().getPeriodo(),selectedGpo.getGrupoPK().getNombre());
	    		asd.setIdAula(aulaSabado);
	    		asd.setDia(new Dia(6));
	    		listaAulSal.add(asd);
	    	}
	    	selectedGpo.setAulaSalonDiaList(listaAulSal);
    	}  // Si se cambiaron las aulas (BOTTOM)
    	       	
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
    	} // Si hubo modificaciones o faltan grupos por confirmar o modificar (BOTTOM)      	         
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
    				if(g.getGrupoRespaldo() != null)
    				{
    					gpoRespEJB.remove(g.getGrupoRespaldo());
    				}
    				g.setValidado(3);
                    enviarMail(g);    				    				    				
    				gpoEJB.edit(g);
			    	for(GrupoRespaldo h : listaModificados)
			    	{
			    		if((g.getGrupoPK().getClaveMateria().trim()).equals(h.getGrupoRespaldoPK().getClaveMateria().trim()) &&
			    				(g.getGrupoPK().getPeriodo().trim()).equals(h.getGrupoRespaldoPK().getPeriodo().trim()) &&
			    				(g.getGrupoPK().getNombre().trim()).equals(h.getGrupoRespaldoPK().getNombre().trim()))
			    		{
			    			gpoRespEJB.create(h);
			    		}
			    	}    	    				
    			} // Si solo se modificaron las aulas del grupo (BOTTOM)
    			else if(g.getEstado() == 2)
    			{ // Si el grupo fue modificado (TOP)
    				try
    			    {    					
    					g.setValidado(0); // Se marca como grupo para validación
                        enviarMail(g);    			    	
    			    	gpoEJB.edit(g);
    			    	for(GrupoRespaldo h : listaModificados)
    			    	{
    			    		if((g.getGrupoPK().getClaveMateria().trim()).equals(h.getGrupoRespaldoPK().getClaveMateria().trim()) &&
    			    				(g.getGrupoPK().getPeriodo().trim()).equals(h.getGrupoRespaldoPK().getPeriodo().trim()) &&
    			    				(g.getGrupoPK().getNombre().trim()).equals(h.getGrupoRespaldoPK().getNombre().trim()))
    			    		{
    			    			gpoRespEJB.create(h);
    			    		}
    			    	}
    			    	
                        NotificacionesCoord coord = new NotificacionesCoord(g.getGrupoPK().getClaveMateria(),g.getGrupoPK().getPeriodo(),g.getGrupoPK().getNombre(),new Date());
                        coord.setDescripcion(g.getDescripcion());
                        coord.setEstado(0); // Estado no leido
                        
                        logg.info(">>>>> datos.. notificaciones error :  " + coord.getNotificacionesCoordPK().getClaveMateria() + " : " + 
                        coord.getNotificacionesCoordPK().getPeriodo()+ " : " + coord.getNotificacionesCoordPK().getNombre());
                        
                        notifEJB.newNotificacion(coord);
                                                
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
    			if(g.getGrupoRespaldo() != null)
    				gpoRespEJB.remove(g.getGrupoRespaldo());
    		}
        	if(g.getEstado() == 1)
        	{
       			g.setValidado(3); // Se setea el grupo a confirmado
       			gpoEJB.edit(g);
       			g.setEstado(0);       			
       		}
        	else if(g.getEstado() == 3)
        	{
       			g.setValidado(3); // Se setea el grupo a confirmado        		
        		for(GrupoRespaldo h : listaModificados)
		    	{
		    		if((g.getGrupoPK().getClaveMateria().trim()).equals(h.getGrupoRespaldoPK().getClaveMateria().trim()) &&
		    				(g.getGrupoPK().getPeriodo().trim()).equals(h.getGrupoRespaldoPK().getPeriodo().trim()) &&
		    				(g.getGrupoPK().getNombre().trim()).equals(h.getGrupoRespaldoPK().getNombre().trim()))
		    		{
		    			gpoRespEJB.create(h);
		    		}
		    	}        		
       			enviarMail(g);       			
       			gpoEJB.edit(g);
       			g.setEstado(0);       			
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
        para = "giresa.ico@gmail.com";        
        if(g.getEstado() == 2)
        {
        	mensaje = "El profesor <b>" + g.getRfcProfesor().getNombreProfe() + " " + g.getRfcProfesor().getApePatProfe() + " " + g.getRfcProfesor().getApeMatProfe() + 
        		"</b> ha enviado el grupo <b>" + g.getGrupoPK().getNombre() +  "</b> de la materia <b>" + g.getMateria().getNombreMateria() + "</b> para validacion \n "  +
        				 "Ingrese a la liga para validar el (los) grupo(s)  http://localhost:8282/SIPLAFI-WEB/index.jsf  o http://localhost:8080/SIPLAFI-WEB/index.jsf ";
        	subject = "Nuevo grupo para validacion";
        }
        else if(g.getEstado() == 3)
        {
        	GrupoRespaldo gpoResp = null;
        	if(g.getGrupoRespaldo() != null)
        		gpoResp = g.getGrupoRespaldo();
        	
            mensaje = "El profesor <b>" + g.getRfcProfesor().getNombreProfe() + " " + g.getRfcProfesor().getApePatProfe() + " " + g.getRfcProfesor().getApeMatProfe() + 
            		"</b> solicito el cambio de aula del grupo "  + g.getGrupoPK().getNombre() +  
            		" Ingrese a la liga para ver la planilla http://localhost:8282/SIPLAFI-WEB/index.jsf  o http://localhost:8080/SIPLAFI-WEB/index.jsf ";
        	subject = "Se realizó la modificacion del tipo de aulas";
        }
        
        props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.live.com");
        props.put("mail.smtp.port", "587");
        try
        {
        	Authenticator aut = new Authenticator()
        	{
        		@Override
        		protected PasswordAuthentication getPasswordAuthentication()
        		{
        			return new PasswordAuthentication(user, password);
        		}
        	};        
        	session = Session.getInstance(props,aut);                
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(para));
            message.setSubject(subject);
            message.setContent(mensaje,"text/html");
            Transport.send(message);
            logg.info("EL CORREO FUE ENVIADO EXITOSAMENTE");
            //RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(""));
        } 
        catch (MessagingException e) 
        {
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocurrio un error al enviar el correo verifique su conexion a internet"));
        	logg.info("Error de conexion");        	
           // throw new RuntimeException(e);
        }
        catch(Exception ex)
        {
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocurrio un error al enviar el correo verifique su conexion a internet"));
        	logg.info("Error de conexion");
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

	public Aula getAulaLunes() 
	{
		return aulaLunes;
	}

	public void setAulaLunes(Aula aulaLunes) 
	{
		this.aulaLunes = aulaLunes;
	}

	public Aula getAulaMartes() 
	{
		return aulaMartes;
	}

	public void setAulaMartes(Aula aulaMartes) 
	{
		this.aulaMartes = aulaMartes;
	}

	public Aula getAulaMiercoles() 
	{
		return aulaMiercoles;
	}

	public void setAulaMiercoles(Aula aulaMiercoles) 
	{
		this.aulaMiercoles = aulaMiercoles;
	}

	public Aula getAulaJueves() 
	{
		return aulaJueves;
	}

	public void setAulaJueves(Aula aulaJueves) 
	{
		this.aulaJueves = aulaJueves;
	}

	public Aula getAulaViernes() 
	{
		return aulaViernes;
	}

	public void setAulaViernes(Aula aulaViernes) 
	{
		this.aulaViernes = aulaViernes;
	}

	public Aula getAulaSabado() 
	{
		return aulaSabado;
	}

	public void setAulaSabado(Aula aulaSabado) 
	{
		this.aulaSabado = aulaSabado;
	}

}
