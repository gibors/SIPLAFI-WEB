/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IEEM
 */
@Entity
@Table(name = "grupo", catalog = "SIPLAFI_DB", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findGruposSemestre", query = "SELECT g FROM Grupo g WHERE g.materia.semestre = :semestre AND g.grupoPK.claveMateria = :cveMat AND g.grupoPK.rfcProfesor = :rfcPro AND g.grupoPK.periodo = :period AND g.grupoPK.nombre = :name"),    
    @NamedQuery(name = "Grupo.findByClaveMateria", query = "SELECT g FROM Grupo g WHERE g.grupoPK.claveMateria = :claveMateria"),
    @NamedQuery(name = "Grupo.findByRfcProfesor", query = "SELECT g FROM Grupo g WHERE g.grupoPK.rfcProfesor = :rfcProfesor"),
    @NamedQuery(name = "Grupo.findByPeriodo", query = "SELECT g FROM Grupo g WHERE g.grupoPK.periodo = :periodo"),
    @NamedQuery(name = "Grupo.findByNombre", query = "SELECT g FROM Grupo g WHERE g.grupoPK.nombre = :nombre"),
    @NamedQuery(name = "Grupo.findByLunHoraIni", query = "SELECT g FROM Grupo g WHERE g.lunHoraIni = :lunHoraIni"),
    @NamedQuery(name = "Grupo.findByLunHoraFin", query = "SELECT g FROM Grupo g WHERE g.lunHoraFin = :lunHoraFin"),
    @NamedQuery(name = "Grupo.findByMarHoraIni", query = "SELECT g FROM Grupo g WHERE g.marHoraIni = :marHoraIni"),
    @NamedQuery(name = "Grupo.findByMarHoraFin", query = "SELECT g FROM Grupo g WHERE g.marHoraFin = :marHoraFin"),
    @NamedQuery(name = "Grupo.findByMieHoraIni", query = "SELECT g FROM Grupo g WHERE g.mieHoraIni = :mieHoraIni"),
    @NamedQuery(name = "Grupo.findByMieHoraFin", query = "SELECT g FROM Grupo g WHERE g.mieHoraFin = :mieHoraFin"),
    @NamedQuery(name = "Grupo.findByJueHoraIni", query = "SELECT g FROM Grupo g WHERE g.jueHoraIni = :jueHoraIni"),
    @NamedQuery(name = "Grupo.findByJueHoraFin", query = "SELECT g FROM Grupo g WHERE g.jueHoraFin = :jueHoraFin"),
    @NamedQuery(name = "Grupo.findByVieHoraIni", query = "SELECT g FROM Grupo g WHERE g.vieHoraIni = :vieHoraIni"),
    @NamedQuery(name = "Grupo.findByVieHoraFin", query = "SELECT g FROM Grupo g WHERE g.vieHoraFin = :vieHoraFin"),
    @NamedQuery(name = "Grupo.findBySabHoraIni", query = "SELECT g FROM Grupo g WHERE g.sabHoraIni = :sabHoraIni"),
    @NamedQuery(name = "Grupo.findBySabHoraFin", query = "SELECT g FROM Grupo g WHERE g.sabHoraFin = :sabHoraFin"),
    @NamedQuery(name = "Grupo.findByValidado", query = "SELECT g FROM Grupo g WHERE g.validado = :validado"),
    @NamedQuery(name="BuscaTraslapeLunes",query="  SELECT g FROM Grupo g  WHERE ((g.lunHoraIni > :lunIni AND g.lunHoraIni < :lunFin ) OR   (g.lunHoraFin >  :lunIni AND g.lunHoraFin < :lunFin ))   AND g.grupoPK <> :gpoPk   AND g.materia.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeMartes",query="  SELECT g FROM Grupo g  WHERE ((g.marHoraIni > :marIni AND g.marHoraIni < :marFin ) OR   (g.marHoraFin > :marIni AND g.marHoraFin < :marFin ))   AND g.grupoPK <> :gpoPk   AND g.materia.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeMiercoles",query="  SELECT g FROM Grupo g  WHERE ((g.mieHoraIni > :mieIni AND g.mieHoraIni < :mieFin ) OR   (g.mieHoraFin >  :mieIni AND g.mieHoraFin < :mieFin ))   AND g.grupoPK <> :gpoPk   AND g.materia.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeJueves",query="  SELECT g FROM Grupo g  WHERE ((g.jueHoraIni > :jueIni AND g.jueHoraIni < :jueFin ) OR   (g.jueHoraFin > :jueIni AND g.jueHoraFin < :jueFin ))   AND g.grupoPK <> :gpoPk   AND g.materia.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeViernes",query="  SELECT g FROM Grupo g  WHERE ((g.vieHoraIni > :vieIni AND g.vieHoraIni < :vieFin ) OR   (g.vieHoraFin >  :vieIni AND g.vieHoraFin < :viein ))   AND g.grupoPK <> :gpoPk   AND g.materia.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeSabado",query="  SELECT g FROM Grupo g  WHERE ((g.sabHoraIni > :sabIni AND g.sabHoraIni < :sabFin ) OR   (g.sabHoraFin > :sabIni AND g.sabHoraFin < :sabFin ))   AND g.grupoPK <> :gpoPk   AND g.materia.semestre = :semestre"),
     
    })
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GrupoPK grupoPK;
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
    @Column(name = "VALIDADO")
    private Integer validado;
    @JoinColumn(name = "AULA_LUN", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaLun;
    @JoinColumn(name = "AULA_JUE", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaJue;
    @JoinColumn(name = "AULA_MAR", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaMar;
    @JoinColumn(name = "ID_SALON_MIE", referencedColumnName = "ID_SALON")
    @ManyToOne
    private Salon idSalonMie;
    @JoinColumn(name = "AULA_MIE", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaMie;
    @JoinColumn(name = "AULA_SAB", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaSab;
    @JoinColumn(name = "AULA_VIE", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula aulaVie;
    @JoinColumn(name = "CLAVE_MATERIA", referencedColumnName = "CLAVE_MATERIA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materia materia;
    @JoinColumn(name = "PERIODO", referencedColumnName = "PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodos periodos;
    @JoinColumn(name = "ID_SALON_SAB", referencedColumnName = "ID_SALON")
    @ManyToOne
    private Salon idSalonSab;
    @JoinColumn(name = "ID_SALON_MAR", referencedColumnName = "ID_SALON")
    @ManyToOne
    private Salon idSalonMar;
    @JoinColumn(name = "RFC_PROFESOR", referencedColumnName = "RFC_PROFESOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Profesor profesor;
    @JoinColumn(name = "ID_SALON_JUE", referencedColumnName = "ID_SALON")
    @ManyToOne
    private Salon idSalonJue;
    @JoinColumn(name = "ID_SALON_VIE", referencedColumnName = "ID_SALON")
    @ManyToOne
    private Salon idSalonVie;
    @JoinColumn(name = "ID_SALON_LUN", referencedColumnName = "ID_SALON")
    @ManyToOne
    private Salon idSalonLun;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "grupo")
    private GrupoRespaldo grupoRespaldo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<NotificacionesCoord> notificacionesCoordList;
    @Transient
    private Integer estado=0;
    @Transient
    private String descripcion;  
    public Grupo() {
    }

    public Grupo(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
    }

    public Grupo(String claveMateria, String rfcProfesor, String periodo, String nombre) {
        this.grupoPK = new GrupoPK(claveMateria, rfcProfesor, periodo, nombre);
    }

    public GrupoPK getGrupoPK() {
        return grupoPK;
    }

    public void setGrupoPK(GrupoPK grupoPK) {
        this.grupoPK = grupoPK;
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

    public Integer getValidado() {
        return validado;
    }

    public void setValidado(Integer validado) {
        this.validado = validado;
    }

    public Aula getAulaLun() {
        return aulaLun;
    }

    public void setAulaLun(Aula aulaLun) {
        this.aulaLun = aulaLun;
    }

    public Aula getAulaJue() {
        return aulaJue;
    }

    public void setAulaJue(Aula aulaJue) {
        this.aulaJue = aulaJue;
    }

    public Aula getAulaMar() {
        return aulaMar;
    }

    public void setAulaMar(Aula aulaMar) {
        this.aulaMar = aulaMar;
    }

    public Salon getIdSalonMie() {
        return idSalonMie;
    }

    public void setIdSalonMie(Salon idSalonMie) {
        this.idSalonMie = idSalonMie;
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

    public Salon getIdSalonSab() {
        return idSalonSab;
    }

    public void setIdSalonSab(Salon idSalonSab) {
        this.idSalonSab = idSalonSab;
    }

    public Salon getIdSalonMar() {
        return idSalonMar;
    }

    public void setIdSalonMar(Salon idSalonMar) {
        this.idSalonMar = idSalonMar;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Salon getIdSalonJue() {
        return idSalonJue;
    }

    public void setIdSalonJue(Salon idSalonJue) {
        this.idSalonJue = idSalonJue;
    }

    public Salon getIdSalonVie() {
        return idSalonVie;
    }

    public void setIdSalonVie(Salon idSalonVie) {
        this.idSalonVie = idSalonVie;
    }

    public Salon getIdSalonLun() {
        return idSalonLun;
    }

    public void setIdSalonLun(Salon idSalonLun) {
        this.idSalonLun = idSalonLun;
    }

    public GrupoRespaldo getGrupoRespaldo() {
        return grupoRespaldo;
    }

    public void setGrupoRespaldo(GrupoRespaldo grupoRespaldo) {
        this.grupoRespaldo = grupoRespaldo;
    }

    @XmlTransient
    public List<NotificacionesCoord> getNotificacionesCoordList() {
        return notificacionesCoordList;
    }

    public void setNotificacionesCoordList(List<NotificacionesCoord> notificacionesCoordList) {
        this.notificacionesCoordList = notificacionesCoordList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoPK != null ? grupoPK.hashCode() : 0);
        return hash;
    }

    public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupoPK == null && other.grupoPK != null) || (this.grupoPK != null && !this.grupoPK.equals(other.grupoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Grupo[ grupoPK=" + grupoPK + " ]";
    }
    
}
