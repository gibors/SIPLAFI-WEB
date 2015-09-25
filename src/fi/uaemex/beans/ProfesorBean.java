
package fi.uaemex.beans;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import fi.uaemex.ejbs.AulaFacade;
import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.ProfesorFacade;
import fi.uaemex.entities.Aula;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.Horario;
import fi.uaemex.entities.Profesor;

@ManagedBean(name = "profesorBean",eager=true)
@ViewScoped
public class ProfesorBean implements Serializable
{

	private static final long serialVersionUID = 1L;
	private static final Logger logg = Logger.getLogger(ProfesorBean.class.getName());
    private DateFormat format = new SimpleDateFormat("HH:mm "); 	// Formato para el horario
    private Date lunIn;								
    private Date lunFn; 
    private Date marIn;
    private Date marFn; 
    private Date mieIn;
    private Date mieFn; 
    private Date jueIn;
    private Date jueFn; 
    private Date vieIn;
    private Date vieFn; 
    private Date sabIn;
    private Date sabFn;
    private List<String> mensajesTraslape;
    private Horario horarioanterior;								// guardara el horario anterior para guardarlo en la tabla temporal
    private Profesor profe;                         				// Profesor logged
    private String nombreProfe;                     				// nombre para mostrar del profesor
    private List<Grupo> gposProfe;                  				// obtiene la lista de los grupos del profesor
    private List<Grupo> listGposInSemester;							// Almacena los grupos de las materias en el mismo horario del grupo seleccionado para modificar
    private List<Aula> listAula; 									// Lista de las aulas requeridas para la clase
    private Grupo selectedGpo;										// Almacena el grupo que es seleccionado para editar el horario
    @EJB private ProfesorFacade profFacade;              			// EJB para acceso a datos del profesor
    @EJB private GrupoFacade gpoEJB;                     			// EJB para acceso a datos del grupo
    @EJB private AulaFacade aulaEJB;								// EJB para acceso a datos de la entidad aula    
    @ManagedProperty(value = "#{login}") private LoginBean login;   // Propiedad para usar variables de session del bean de login

    
   public ProfesorBean() 
   {
	// TODO Auto-generated constructor stub
   } 
    @PostConstruct
    public void init()
    { // Se ejecuta antes de construir el objeto (TOP)
    	//profe = login.getProfe();
    	profe = profFacade.findUser("QH5Q0S7NYHJTM30", "sirenito88");
        gposProfe = new ArrayList<>();
        gposProfe = profe.getGrupoList();
        listAula = aulaEJB.findAll();
        nombreProfe = profe.getNombreProfe() + " " + profe.getApePatProfe() + " " + profe.getApeMatProfe();
    } // Se ejecuta antes de construir el objeto (BOTTOM)
        
    public void onGrupoSelected(Grupo gs)
    {
    	this.selectedGpo = gs;    	
    	listGposInSemester = gpoEJB.findGrupoSemestre(selectedGpo.getClaveMateria().getSemestre(), selectedGpo.getIdGrupo());
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
    	
    	System.out.println(">>> se selecciono un grupo ..." + selectedGpo.getNombre() + " oooo.... " + listGposInSemester.size() + " ::: " +sabIn);
    	
    	RequestContext.getCurrentInstance().execute("PF('dlgModHora').show()");    	    	
    }
    
    public String aceptar()
    {
    	
    	return "";
    }
    public String confirmar()
    {
        float min = 0;
        float minutos = 0;
        float horas= 0;       
        float horaXDia = 0;
        mensajesTraslape = new ArrayList<>();
        
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
        //    System.out.println(" Aula " + horarioGpo.getAulaLun());
            
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
            else if(selectedGpo.getHorario().getAulaLun().equals("0") || selectedGpo.getHorario().getAulaLun() == null)
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
            else if(selectedGpo.getHorario().getAulaMar().equals("0") || selectedGpo.getHorario().getAulaMar() == null)
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
            else if(selectedGpo.getHorario().getAulaMie().equals("0") || selectedGpo.getHorario().getAulaMie() == null)
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
            else if(selectedGpo.getHorario().getAulaJue().equals("0") || selectedGpo.getHorario().getAulaJue()== null)
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
            else if(selectedGpo.getHorario().getAulaVie().equals("0") || selectedGpo.getHorario().getAulaVie()==null)
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
            else if(selectedGpo.getHorario().getAulaSab().equals("0") || selectedGpo.getHorario().getAulaSab()==null)
            {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione el tipo de aula para el sabado",null));
                return "";
            }
        } // Si se ingresa hora de inicio y hora final en sabado (BOTTOM)
        horas = horas + minutos;
        if(horas != (float)selectedGpo.getClaveMateria().getHoras() )
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR," el total de horas de la materia debe ser " + selectedGpo.getClaveMateria().getHoras() + " la suma actual es : " + horas,null));
            return "";    
        }
// ******  valida que la suma de horas corresponda a las horas de la materia y el horario de lunes a jueves no puede ser mayor a 2.5 horas (BOTTOM) 
        
        if (lunIn.equals(selectedGpo.getHorario().getLunHoraIni()) && lunFn.equals(selectedGpo.getHorario().getLunHoraFin()) && 
        		marIn.equals(selectedGpo.getHorario().getMarHoraIni()) && marFn.equals(selectedGpo.getHorario().getMarHoraFin()) &&
        		mieIn.equals(selectedGpo.getHorario().getMieHoraIni()) && mieFn.equals(selectedGpo.getHorario().getMieHoraFin()) &&
        		jueIn.equals(selectedGpo.getHorario().getJueHoraIni()) && jueFn.equals(selectedGpo.getHorario().getJueHoraFin()) &&
        		vieIn.equals(selectedGpo.getHorario().getVieHoraIni()) && vieFn.equals(selectedGpo.getHorario().getVieHoraFin()) &&
        		sabIn.equals(selectedGpo.getHorario().getSabHoraIni()) && sabFn.equals(selectedGpo.getHorario().getSabHoraFin()) )
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"No realizaste ninguna modficación del horario",null));
        	return "";
        }
        
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
        {
            mensajesTraslape.add("No existen taslapes");
        }
        
        RequestContext.getCurrentInstance().execute("PF('traslapesDlg').show()");
              
        return "";
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

	public List<Grupo> getGposProfe() {
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

	public Grupo getSelectedGpo() {
		return selectedGpo;
	}

	public void setSelectedGpo(Grupo selectedGpo) {
		this.selectedGpo = selectedGpo;
	}

	public List<Grupo> getListGposInSemester() {
		return listGposInSemester;
	}

	public void setListGposInSemester(List<Grupo> listGposInSemester) {
		this.listGposInSemester = listGposInSemester;
	}

	public List<Aula> getListAula() {
		return listAula;
	}

	public void setListAula(List<Aula> listAula) {
		this.listAula = listAula;
	}

	public Date getLunIn() {
		return lunIn;
	}
	public void setLunIn(Date lunIn) {
		this.lunIn = lunIn;
	}
	public Date getLunFn() {
		return lunFn;
	}
	public void setLunFn(Date lunFn) {
		this.lunFn = lunFn;
	}
	public Date getMarIn() {
		return marIn;
	}
	public void setMarIn(Date marIn) {
		this.marIn = marIn;
	}
	public Date getMarFn() {
		return marFn;
	}
	public void setMarFn(Date marFn) {
		this.marFn = marFn;
	}
	public Date getMieIn() {
		return mieIn;
	}
	public void setMieIn(Date mieIn) {
		this.mieIn = mieIn;
	}
	public Date getMieFn() {
		return mieFn;
	}
	public void setMieFn(Date mieFn) {
		this.mieFn = mieFn;
	}
	public Date getJueIn() {
		return jueIn;
	}
	public void setJueIn(Date jueIn) {
		this.jueIn = jueIn;
	}
	public Date getJueFn() {
		return jueFn;
	}
	public void setJueFn(Date jueFn) {
		this.jueFn = jueFn;
	}
	public Date getVieIn() {
		return vieIn;
	}
	public void setVieIn(Date vieIn) {
		this.vieIn = vieIn;
	}
	public Date getVieFn() {
		return vieFn;
	}
	public void setVieFn(Date vieFn) {
		this.vieFn = vieFn;
	}
	public Date getSabIn() {
		return sabIn;
	}
	public void setSabIn(Date sabIn) {
		this.sabIn = sabIn;
	}
	public Date getSabFn() {
		return sabFn;
	}
	public void setSabFn(Date sabFn) {
		this.sabFn = sabFn;
	}
	public List<String> getMensajesTraslape() {
		return mensajesTraslape;
	}
	public void setMensajesTraslape(List<String> mensajesTraslape) {
		this.mensajesTraslape = mensajesTraslape;
	}
	
}
