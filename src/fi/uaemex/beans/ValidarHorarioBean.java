/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.beans;

import fi.uaemex.ejbs.CoordinadorFacade;
import fi.uaemex.ejbs.GrupoFacade;
import fi.uaemex.ejbs.Horario2Facade;
import fi.uaemex.ejbs.HorarioFacade;
import fi.uaemex.ejbs.NotificacionesCoordFacade;
import fi.uaemex.entities.Grupo;
import fi.uaemex.entities.Horario2;
import fi.uaemex.entities.NotificacionesCoord;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ValidarHorarioBean implements Serializable
{
    @ManagedProperty(value = "#{coordinadorBean}")
    private CoordinadorBean coordBean;    
    @EJB
    private CoordinadorFacade coordEJB;
    @EJB
    private Horario2Facade hora2EJB;
    @EJB
    private GrupoFacade  gpoEJB;    
    @EJB
    private HorarioFacade horaEJB;
    @EJB
    private NotificacionesCoordFacade notifEJB;
    
    private List<Grupo> listGpos;
    private Grupo selectedGpo;
    private Horario2 horario2;
    private NotificacionesCoord selectedNotif;
    private DateFormat fmt = new SimpleDateFormat("HH:mm");  
    
    @PostConstruct
    public void init()
    {
     selectedGpo = coordBean.getSelecteGpo();
     selectedNotif = coordBean.getNotifSelected();
     horario2 = hora2EJB.find(selectedGpo.getIdGrupo());
     listGpos = new ArrayList<>();
     listGpos = gpoEJB.findGrupoSemestre(selectedGpo.getClaveMateria().getSemestre(),selectedGpo.getIdGrupo());     
     System.out.println(listGpos.get(0).getNombre());
    }
    
    public ValidarHorarioBean() 
    {        
    }
    
    public String aceptarHorario()
    {
        selectedGpo.getHorario().setLunHoraIni(horario2.getLunHoraIni());        
        selectedGpo.getHorario().setLunHoraFin(horario2.getLunHoraFin());        
        selectedGpo.getHorario().setMarHoraIni(horario2.getMarHoraIni());        
        selectedGpo.getHorario().setMarHoraFin(horario2.getMarHoraFin());        
        selectedGpo.getHorario().setMieHoraIni(horario2.getMieHoraIni());        
        selectedGpo.getHorario().setMieHoraFin(horario2.getMieHoraFin());        
        selectedGpo.getHorario().setJueHoraIni(horario2.getJueHoraIni());        
        selectedGpo.getHorario().setJueHoraFin(horario2.getJueHoraFin());        
        selectedGpo.getHorario().setVieHoraIni(horario2.getVieHoraIni());        
        selectedGpo.getHorario().setVieHoraFin(horario2.getVieHoraFin());        
        selectedGpo.getHorario().setSabHoraIni(horario2.getSabHoraIni());        
        selectedGpo.getHorario().setSabHoraFin(horario2.getSabHoraFin());
        selectedGpo.setValidado(1);
        selectedNotif.setFechaHoraValidacion(new Date());
        gpoEJB.edit(selectedGpo);
        horaEJB.edit(selectedGpo.getHorario());
        notifEJB.edit(selectedNotif);
        return "coordinador?faces-redirect=true";
    }
    
    public String rechazarHorario()
    {
        selectedGpo.setValidado(2); // No aceptado
        selectedNotif.setFechaHoraValidacion(new Date());        
        hora2EJB.remove(horario2);
        gpoEJB.edit(selectedGpo);
        notifEJB.edit(selectedNotif);        
        
        return "coordinador?faces-redirect=true";
    }
    public CoordinadorBean getCoordBean() {
        return coordBean;
    }

    public void setCoordBean(CoordinadorBean coordBean) {
        this.coordBean = coordBean;
    }

    public List<Grupo> getListGpos() {
        return listGpos;
    }

    public void setListGpos(List<Grupo> listGpos) {
        this.listGpos = listGpos;
    }

    public Grupo getSelectedGpo() {
        return selectedGpo;
    }

    public void setSelectedGpo(Grupo selectedGpo) {
        this.selectedGpo = selectedGpo;
    }

    public Horario2 getHorario2() {
        return horario2;
    }
    
    public void setHorario2(Horario2 horario2) {
        this.horario2 = horario2;
    }

    public DateFormat getFmt() {
        return fmt;
    }

    public void setFmt(DateFormat fmt) {
        this.fmt = fmt;
    }

    public GrupoFacade getGpoEJB() {
        return gpoEJB;
    }

    public void setGpoEJB(GrupoFacade gpoEJB) {
        this.gpoEJB = gpoEJB;
    }

    public NotificacionesCoord getSelectedNotif() {
        return selectedNotif;
    }

    public void setSelectedNotif(NotificacionesCoord selectedNotif) {
        this.selectedNotif = selectedNotif;
    }
    
}
