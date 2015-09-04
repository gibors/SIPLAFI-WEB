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
    @NamedQuery(name = "NotificacionesCoord.findAll", query = "SELECT n FROM NotificacionesCoord n ORDER BY n.fechaHoraValidacion"),
    @NamedQuery(name = "NotificacionesCoord.findByIdGrupo", query = "SELECT n FROM NotificacionesCoord n WHERE n.notificacionesCoordPK.idGrupo = :idGrupo"),
    @NamedQuery(name = "NotificacionesCoord.findByFechaHoraNotificacion", query = "SELECT n FROM NotificacionesCoord n WHERE n.notificacionesCoordPK.fechaHoraNotificacion = :fechaHoraNotificacion"),
    @NamedQuery(name = "NotificacionesCoord.findByEstado", query = "SELECT n FROM NotificacionesCoord n WHERE n.estado = :estado"),
    @NamedQuery(name = "NotificacionesCoord.findByDescripcion", query = "SELECT n FROM NotificacionesCoord n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "NotificacionesCoord.findByFechaHoraValidacion", query = "SELECT n FROM NotificacionesCoord n WHERE n.fechaHoraValidacion = :fechaHoraValidacion")})
public class NotificacionesCoord implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotificacionesCoordPK notificacionesCoordPK;
    @Column(name = "ESTADO")
    private Integer estado;
    @Size(max = 256)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "FECHA_HORA_VALIDACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraValidacion;

    public NotificacionesCoord() {
    }

    public NotificacionesCoord(NotificacionesCoordPK notificacionesCoordPK) {
        this.notificacionesCoordPK = notificacionesCoordPK;
    }

    public NotificacionesCoord(int idGrupo, Date fechaHoraNotificacion) {
        this.notificacionesCoordPK = new NotificacionesCoordPK(idGrupo, fechaHoraNotificacion);
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

    public Date getFechaHoraValidacion() {
        return fechaHoraValidacion;
    }

    public void setFechaHoraValidacion(Date fechaHoraValidacion) {
        this.fechaHoraValidacion = fechaHoraValidacion;
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
