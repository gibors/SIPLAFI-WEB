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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IEEM
 */
@Entity
@Table(name = "notificaciones_coord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionesCoord.findAll", query = "SELECT n FROM NotificacionesCoord n"),
    @NamedQuery(name = "NotificacionesCoord.findByClaveMateria", query = "SELECT n FROM NotificacionesCoord n WHERE n.notificacionesCoordPK.claveMateria = :claveMateria"),
    @NamedQuery(name = "NotificacionesCoord.findByPeriodo", query = "SELECT n FROM NotificacionesCoord n WHERE n.notificacionesCoordPK.periodo = :periodo"),
    @NamedQuery(name = "NotificacionesCoord.findByNombre", query = "SELECT n FROM NotificacionesCoord n WHERE n.notificacionesCoordPK.nombre = :nombre"),
    @NamedQuery(name = "NotificacionesCoord.findByFechaHoraNotif", query = "SELECT n FROM NotificacionesCoord n WHERE n.notificacionesCoordPK.fechaHoraNotif = :fechaHoraNotif"),
    @NamedQuery(name = "NotificacionesCoord.findByEstado", query = "SELECT n FROM NotificacionesCoord n WHERE n.estado = :estado"),
    @NamedQuery(name = "NotificacionesCoord.findByDescripcion", query = "SELECT n FROM NotificacionesCoord n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "NotificacionesCoord.findByFechaHoraValida", query = "SELECT n FROM NotificacionesCoord n WHERE n.fechaHoraValida = :fechaHoraValida")})
public class NotificacionesCoord implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotificacionesCoordPK notificacionesCoordPK;
    private Integer estado;
    @Size(max = 256)
    @Column(length = 256)
    private String descripcion;
    @Column(name = "FECHA_HORA_VALIDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraValida;
    @JoinColumns({
        @JoinColumn(name = "CLAVE_MATERIA", referencedColumnName = "CLAVE_MATERIA", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "PERIODO", referencedColumnName = "PERIODO", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "NOMBRE", referencedColumnName = "NOMBRE", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "CLAVE_MATERIA", referencedColumnName = "CLAVE_MATERIA", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Materia materia;
    @JoinColumn(name = "PERIODO", referencedColumnName = "PERIODO", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodos periodos;

    public NotificacionesCoord() {
    }

    public NotificacionesCoord(NotificacionesCoordPK notificacionesCoordPK) {
        this.notificacionesCoordPK = notificacionesCoordPK;
    }

    public NotificacionesCoord(String claveMateria, String periodo, String nombre, Date fechaHoraNotif) {
        this.notificacionesCoordPK = new NotificacionesCoordPK(claveMateria, periodo, nombre, fechaHoraNotif);
    }

    public NotificacionesCoordPK getNotificacionesCoordPK() {
        return notificacionesCoordPK;
    }

    public void setNotificacionesCoordPK(NotificacionesCoordPK notificacionesCoordPK) {
        this.notificacionesCoordPK = notificacionesCoordPK;
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

    public Date getFechaHoraValida() {
        return fechaHoraValida;
    }

    public void setFechaHoraValida(Date fechaHoraValida) {
        this.fechaHoraValida = fechaHoraValida;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificacionesCoordPK != null ? notificacionesCoordPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionesCoord)) {
            return false;
        }
        NotificacionesCoord other = (NotificacionesCoord) object;
        if ((this.notificacionesCoordPK == null && other.notificacionesCoordPK != null) || (this.notificacionesCoordPK != null && !this.notificacionesCoordPK.equals(other.notificacionesCoordPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NotificacionesCoord[ notificacionesCoordPK=" + notificacionesCoordPK + " ]";
    }
    
}
