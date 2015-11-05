/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gibors
 */
@Entity
@Table(name = "HORARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "SELECT h FROM Horario h"),
    @NamedQuery(name = "Horario.findByIdHorario", query = "SELECT h FROM Horario h WHERE h.idHorario = :idHorario"),
    @NamedQuery(name = "Horario.findByLunHoraIni", query = "SELECT h FROM Horario h WHERE h.lunHoraIni = :lunHoraIni"),
    @NamedQuery(name = "Horario.findByLunHoraFin", query = "SELECT h FROM Horario h WHERE h.lunHoraFin = :lunHoraFin"),
    @NamedQuery(name = "Horario.findByMarHoraIni", query = "SELECT h FROM Horario h WHERE h.marHoraIni = :marHoraIni"),
    @NamedQuery(name = "Horario.findByMarHoraFin", query = "SELECT h FROM Horario h WHERE h.marHoraFin = :marHoraFin"),
    @NamedQuery(name = "Horario.findByMieHoraIni", query = "SELECT h FROM Horario h WHERE h.mieHoraIni = :mieHoraIni"),
    @NamedQuery(name = "Horario.findByMieHoraFin", query = "SELECT h FROM Horario h WHERE h.mieHoraFin = :mieHoraFin"),
    @NamedQuery(name = "Horario.findByJueHoraIni", query = "SELECT h FROM Horario h WHERE h.jueHoraIni = :jueHoraIni"),
    @NamedQuery(name = "Horario.findByJueHoraFin", query = "SELECT h FROM Horario h WHERE h.jueHoraFin = :jueHoraFin"),
    @NamedQuery(name = "Horario.findByVieHoraIni", query = "SELECT h FROM Horario h WHERE h.vieHoraIni = :vieHoraIni"),
    @NamedQuery(name = "Horario.findByVieHoraFin", query = "SELECT h FROM Horario h WHERE h.vieHoraFin = :vieHoraFin"),
    @NamedQuery(name = "Horario.findBySabHoraIni", query = "SELECT h FROM Horario h WHERE h.sabHoraIni = :sabHoraIni"),
    @NamedQuery(name = "Horario.findBySabHoraFin", query = "SELECT h FROM Horario h WHERE h.sabHoraFin = :sabHoraFin"),
    @NamedQuery(name = "Horario.findByAulaLun", query = "SELECT h FROM Horario h WHERE h.aulaLun = :aulaLun"),
    @NamedQuery(name = "Horario.findByAulaMar", query = "SELECT h FROM Horario h WHERE h.aulaMar = :aulaMar"),
    @NamedQuery(name = "Horario.findByAulaMie", query = "SELECT h FROM Horario h WHERE h.aulaMie = :aulaMie"),
    @NamedQuery(name = "Horario.findByAulaJue", query = "SELECT h FROM Horario h WHERE h.aulaJue = :aulaJue"),
    @NamedQuery(name = "Horario.findByAulaVie", query = "SELECT h FROM Horario h WHERE h.aulaVie = :aulaVie"),
    @NamedQuery(name = "Horario.findByAulaSab", query = "SELECT h FROM Horario h WHERE h.aulaSab = :aulaSab")})
public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_HORARIO")
    private Integer idHorario;
    @Column(name = "LUN_HORA_INI")
    @Temporal(TemporalType.TIME)
    private Date lunHoraIni;
    @Column(name = "LUN_HORA_FIN")
    @Temporal(TemporalType.TIME)
    private Date lunHoraFin;
    @Column(name = "MAR_HORA_INI")
    @Temporal(TemporalType.TIME)
    private Date marHoraIni;
    @Column(name = "MAR_HORA_FIN")
    @Temporal(TemporalType.TIME)
    private Date marHoraFin;
    @Column(name = "MIE_HORA_INI")
    @Temporal(TemporalType.TIME)
    private Date mieHoraIni;
    @Column(name = "MIE_HORA_FIN")
    @Temporal(TemporalType.TIME)
    private Date mieHoraFin;
    @Column(name = "JUE_HORA_INI")
    @Temporal(TemporalType.TIME)
    private Date jueHoraIni;
    @Column(name = "JUE_HORA_FIN")
    @Temporal(TemporalType.TIME)
    private Date jueHoraFin;
    @Column(name = "VIE_HORA_INI")
    @Temporal(TemporalType.TIME)
    private Date vieHoraIni;
    @Column(name = "VIE_HORA_FIN")
    @Temporal(TemporalType.TIME)
    private Date vieHoraFin;
    @Column(name = "SAB_HORA_INI")
    @Temporal(TemporalType.TIME)
    private Date sabHoraIni;
    @Column(name = "SAB_HORA_FIN")
    @Temporal(TemporalType.TIME)
    private Date sabHoraFin;
    @Size(max = 20)
    @Column(name = "AULA_LUN")
    private String aulaLun;
    @Size(max = 20)
    @Column(name = "AULA_MAR")
    private String aulaMar;
    @Size(max = 20)
    @Column(name = "AULA_MIE")
    private String aulaMie;
    @Size(max = 20)
    @Column(name = "AULA_JUE")
    private String aulaJue;
    @Size(max = 20)
    @Column(name = "AULA_VIE")
    private String aulaVie;
    @Size(max = 20)
    @Column(name = "AULA_SAB")
    private String aulaSab;
    @JoinColumn(name = "ID_GRUPO", referencedColumnName = "ID_GRUPO")
    @OneToOne
    private Grupo idGrupo;
    @Column(name="OBSERVACIONES")
    private String observaciones;
    @JoinColumn(name="PERIODO")
    private String periodo;
    
    public Horario() 
    {
    }
    
    public String getPeriodo() {
		return periodo;
	}


	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}


	public Horario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Date getLunHoraIni() {
        return lunHoraIni;
    }

    public void setLunHoraIni(Date lunHoraIni) {
        this.lunHoraIni = lunHoraIni;
    }

    public Date getLunHoraFin() {
        return lunHoraFin;
    }

    public void setLunHoraFin(Date lunHoraFin) {
        this.lunHoraFin = lunHoraFin;
    }

    public Date getMarHoraIni() {
        return marHoraIni;
    }

    public void setMarHoraIni(Date marHoraIni) {
        this.marHoraIni = marHoraIni;
    }

    public Date getMarHoraFin() {
        return marHoraFin;
    }

    public void setMarHoraFin(Date marHoraFin) {
        this.marHoraFin = marHoraFin;
    }

    public Date getMieHoraIni() {
        return mieHoraIni;
    }

    public void setMieHoraIni(Date mieHoraIni) {
        this.mieHoraIni = mieHoraIni;
    }

    public Date getMieHoraFin() {
        return mieHoraFin;
    }

    public void setMieHoraFin(Date mieHoraFin) {
        this.mieHoraFin = mieHoraFin;
    }

    public Date getJueHoraIni() {
        return jueHoraIni;
    }

    public void setJueHoraIni(Date jueHoraIni) {
        this.jueHoraIni = jueHoraIni;
    }

    public Date getJueHoraFin() {
        return jueHoraFin;
    }

    public void setJueHoraFin(Date jueHoraFin) {
        this.jueHoraFin = jueHoraFin;
    }

    public Date getVieHoraIni() {
        return vieHoraIni;
    }

    public void setVieHoraIni(Date vieHoraIni) {
        this.vieHoraIni = vieHoraIni;
    }

    public Date getVieHoraFin() {
        return vieHoraFin;
    }

    public void setVieHoraFin(Date vieHoraFin) {
        this.vieHoraFin = vieHoraFin;
    }

    public Date getSabHoraIni() {
        return sabHoraIni;
    }

    public void setSabHoraIni(Date sabHoraIni) {
        this.sabHoraIni = sabHoraIni;
    }

    public Date getSabHoraFin() {
        return sabHoraFin;
    }

    public void setSabHoraFin(Date sabHoraFin) {
        this.sabHoraFin = sabHoraFin;
    }

    public String getAulaLun() {
        return aulaLun;
    }

    public void setAulaLun(String aulaLun) {
        this.aulaLun = aulaLun;
    }

    public String getAulaMar() {
        return aulaMar;
    }

    public void setAulaMar(String aulaMar) {
        this.aulaMar = aulaMar;
    }

    public String getAulaMie() {
        return aulaMie;
    }

    public void setAulaMie(String aulaMie) {
        this.aulaMie = aulaMie;
    }

    public String getAulaJue() {
        return aulaJue;
    }

    public void setAulaJue(String aulaJue) {
        this.aulaJue = aulaJue;
    }

    public String getAulaVie() {
        return aulaVie;
    }

    public void setAulaVie(String aulaVie) {
        this.aulaVie = aulaVie;
    }

    public String getAulaSab() {
        return aulaSab;
    }

    public void setAulaSab(String aulaSab) {
        this.aulaSab = aulaSab;
    }

    public Grupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Grupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHorario != null ? idHorario.hashCode() : 0);
        return hash;
    }
    
    

    public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horario)) {
            return false;
        }
        Horario other = (Horario) object;
        if ((this.idHorario == null && other.idHorario != null) || (this.idHorario != null && !this.idHorario.equals(other.idHorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Horario[ idHorario=" + idHorario + " ]";
    }
    
}
