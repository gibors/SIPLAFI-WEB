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
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class ValidarHorarioBean implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{param.grupo}")
	private Grupo selectedGpo;

	public Grupo getSelectedGpo() 
	{
		System.out.println(" >>>> Selected grupo : " + selectedGpo == null ? "no hay grupo " : selectedGpo);
		return selectedGpo;
	}

	public void setSelectedGpo(Grupo selectedGpo) 
	{
		this.selectedGpo = selectedGpo;
	}		
}
