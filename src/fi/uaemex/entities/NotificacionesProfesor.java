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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IEEM
 */
@Entity
@Table(name = "notificaciones_profesor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotificacionesProfesor.findAll", query = "SELECT n FROM NotificacionesProfesor n"),
    @NamedQuery(name = "NotificacionesProfesor.findByIdGrupo", query = "SELECT n FROM NotificacionesProfesor n WHERE n.notificacionesProfesorPK.idGrupo = :idGrupo"),
    @NamedQuery(name = "NotificacionesProfesor.findByFechaHoraNotificacion", query = "SELECT n FROM NotificacionesProfesor n WHERE n.notificacionesProfesorPK.fechaHoraNotificacion = :fechaHoraNotificacion"),
    @NamedQuery(name = "NotificacionesProfesor.findByEstado", query = "SELECT n FROM NotificacionesProfesor n WHERE n.estado = :estado"),
    @NamedQuery(name = "NotificacionesProfesor.findByDescripcion", query = "SELECT n FROM NotificacionesProfesor n WHERE n.descripcion = :descripcion")})
public class NotificacionesProfesor implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotificacionesProfesorPK notificacionesProfesorPK;
    @Column(name = "ESTADO")
    private Integer estado;
    @Size(max = 256)
    @Column(name = "DESCRIPCION")
    private String descripcion;

    public NotificacionesProfesor() {
    }

    public NotificacionesProfesor(NotificacionesProfesorPK notificacionesProfesorPK) {
        this.notificacionesProfesorPK = notificacionesProfesorPK;
    }

    public NotificacionesProfesor(int idGrupo, Date fechaHoraNotificacion) {
        this.notificacionesProfesorPK = new NotificacionesProfesorPK(idGrupo, fechaHoraNotificacion);
    }

    public NotificacionesProfesorPK getNotificacionesProfesorPK() {
        return notificacionesProfesorPK;
    }

    public void setNotificacionesProfesorPK(NotificacionesProfesorPK notificacionesProfesorPK) {
        this.notificacionesProfesorPK = notificacionesProfesorPK;
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
    public int hashCode() {
        int hash = 0;
        hash += (notificacionesProfesorPK != null ? notificacionesProfesorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionesProfesor)) {
            return false;
        }
        NotificacionesProfesor other = (NotificacionesProfesor) object;
        if ((this.notificacionesProfesorPK == null && other.notificacionesProfesorPK != null) || (this.notificacionesProfesorPK != null && !this.notificacionesProfesorPK.equals(other.notificacionesProfesorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NotificacionesProfesor[ notificacionesProfesorPK=" + notificacionesProfesorPK + " ]";
    }
    
}
