/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IEEM
 */
@Entity
@Table(name = "grupo_respaldo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoRespaldo.findAll", query = "SELECT g FROM GrupoRespaldo g"),
    @NamedQuery(name = "GrupoRespaldo.findByClaveMateria", query = "SELECT g FROM GrupoRespaldo g WHERE g.grupoRespaldoPK.claveMateria = :claveMateria"),
    @NamedQuery(name = "GrupoRespaldo.findByPeriodo", query = "SELECT g FROM GrupoRespaldo g WHERE g.grupoRespaldoPK.periodo = :periodo"),
    @NamedQuery(name = "GrupoRespaldo.findByNombre", query = "SELECT g FROM GrupoRespaldo g WHERE g.grupoRespaldoPK.nombre = :nombre"),
    @NamedQuery(name = "GrupoRespaldo.findByLunHoraIni", query = "SELECT g FROM GrupoRespaldo g WHERE g.lunHoraIni = :lunHoraIni"),
    @NamedQuery(name = "GrupoRespaldo.findByLunHoraFin", query = "SELECT g FROM GrupoRespaldo g WHERE g.lunHoraFin = :lunHoraFin"),
    @NamedQuery(name = "GrupoRespaldo.findByMarHoraIni", query = "SELECT g FROM GrupoRespaldo g WHERE g.marHoraIni = :marHoraIni"),
    @NamedQuery(name = "GrupoRespaldo.findByMarHoraFin", query = "SELECT g FROM GrupoRespaldo g WHERE g.marHoraFin = :marHoraFin"),
    @NamedQuery(name = "GrupoRespaldo.findByMieHoraIni", query = "SELECT g FROM GrupoRespaldo g WHERE g.mieHoraIni = :mieHoraIni"),
    @NamedQuery(name = "GrupoRespaldo.findByMieHoraFin", query = "SELECT g FROM GrupoRespaldo g WHERE g.mieHoraFin = :mieHoraFin"),
    @NamedQuery(name = "GrupoRespaldo.findByJueHoraIni", query = "SELECT g FROM GrupoRespaldo g WHERE g.jueHoraIni = :jueHoraIni"),
    @NamedQuery(name = "GrupoRespaldo.findByJueHoraFin", query = "SELECT g FROM GrupoRespaldo g WHERE g.jueHoraFin = :jueHoraFin"),
    @NamedQuery(name = "GrupoRespaldo.findByVieHoraIni", query = "SELECT g FROM GrupoRespaldo g WHERE g.vieHoraIni = :vieHoraIni"),
    @NamedQuery(name = "GrupoRespaldo.findByVieHoraFin", query = "SELECT g FROM GrupoRespaldo g WHERE g.vieHoraFin = :vieHoraFin"),
    @NamedQuery(name = "GrupoRespaldo.findBySabHoraIni", query = "SELECT g FROM GrupoRespaldo g WHERE g.sabHoraIni = :sabHoraIni"),
    @NamedQuery(name = "GrupoRespaldo.findBySabHoraFin", query = "SELECT g FROM GrupoRespaldo g WHERE g.sabHoraFin = :sabHoraFin")})
public class GrupoRespaldo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoRespaldoPK grupoRespaldoPK;
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
    @JoinColumn(name = "AULA_JUE", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaJue;
    @JoinColumn(name = "AULA_LUN", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaLun;
    @JoinColumn(name = "AULA_MAR", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaMar;
    @JoinColumn(name = "AULA_MIE", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaMie;
    @JoinColumn(name = "AULA_SAB", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaSab;
    @JoinColumn(name = "AULA_VIE", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaVie;
    @JoinColumns({
        @JoinColumn(name = "CLAVE_MATERIA", referencedColumnName = "CLAVE_MATERIA", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "PERIODO", referencedColumnName = "PERIODO", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE", nullable = false, insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "CLAVE_MATERIA", referencedColumnName = "CLAVE_MATERIA", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materia materia;
    @JoinColumn(name = "PERIODO", referencedColumnName = "PERIODO", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodos periodos;
    @JoinColumn(name = "RFC_PROFESOR", referencedColumnName = "RFC_PROFESOR")
    @ManyToOne
    private Profesor rfcProfesor;

    public GrupoRespaldo() {
    }

    public GrupoRespaldo(GrupoRespaldoPK grupoRespaldoPK) {
        this.grupoRespaldoPK = grupoRespaldoPK;
    }

    public GrupoRespaldo(String claveMateria, String periodo, String nombre) {
        this.grupoRespaldoPK = new GrupoRespaldoPK(claveMateria, periodo, nombre);
    }

    public GrupoRespaldoPK getGrupoRespaldoPK() {
        return grupoRespaldoPK;
    }

    public void setGrupoRespaldoPK(GrupoRespaldoPK grupoRespaldoPK) {
        this.grupoRespaldoPK = grupoRespaldoPK;
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

    public Aula getAulaJue() {
        return aulaJue;
    }

    public void setAulaJue(Aula aulaJue) {
        this.aulaJue = aulaJue;
    }

    public Aula getAulaLun() {
        return aulaLun;
    }

    public void setAulaLun(Aula aulaLun) {
        this.aulaLun = aulaLun;
    }

    public Aula getAulaMar() {
        return aulaMar;
    }

    public void setAulaMar(Aula aulaMar) {
        this.aulaMar = aulaMar;
    }

    public Aula getAulaMie() {
        return aulaMie;
    }

    public void setAulaMie(Aula aulaMie) {
        this.aulaMie = aulaMie;
    }

    public Aula getAulaSab() {
        return aulaSab;
    }

    public void setAulaSab(Aula aulaSab) {
        this.aulaSab = aulaSab;
    }

    public Aula getAulaVie() {
        return aulaVie;
    }

    public void setAulaVie(Aula aulaVie) {
        this.aulaVie = aulaVie;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Periodos getPeriodos() {
        return periodos;
    }

    public void setPeriodos(Periodos periodos) {
        this.periodos = periodos;
    }

    public Profesor getRfcProfesor() {
        return rfcProfesor;
    }

    public void setRfcProfesor(Profesor rfcProfesor) {
        this.rfcProfesor = rfcProfesor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoRespaldoPK != null ? grupoRespaldoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoRespaldo)) {
            return false;
        }
        GrupoRespaldo other = (GrupoRespaldo) object;
        if ((this.grupoRespaldoPK == null && other.grupoRespaldoPK != null) || (this.grupoRespaldoPK != null && !this.grupoRespaldoPK.equals(other.grupoRespaldoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.GrupoRespaldo[ grupoRespaldoPK=" + grupoRespaldoPK + " ]";
    }
    
}
