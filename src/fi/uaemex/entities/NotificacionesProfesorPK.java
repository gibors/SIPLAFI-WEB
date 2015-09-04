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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author IEEM
 */
@Embeddable
public class NotificacionesProfesorPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_GRUPO")
    private int idGrupo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_NOTIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraNotificacion;

    public NotificacionesProfesorPK() {
    }

    public NotificacionesProfesorPK(int idGrupo, Date fechaHoraNotificacion) {
        this.idGrupo = idGrupo;
        this.fechaHoraNotificacion = fechaHoraNotificacion;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Date getFechaHoraNotificacion() {
        return fechaHoraNotificacion;
    }

    public void setFechaHoraNotificacion(Date fechaHoraNotificacion) {
        this.fechaHoraNotificacion = fechaHoraNotificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idGrupo;
        hash += (fechaHoraNotificacion != null ? fechaHoraNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionesProfesorPK)) {
            return false;
        }
        NotificacionesProfesorPK other = (NotificacionesProfesorPK) object;
        if (this.idGrupo != other.idGrupo) {
            return false;
        }
        if ((this.fechaHoraNotificacion == null && other.fechaHoraNotificacion != null) || (this.fechaHoraNotificacion != null && !this.fechaHoraNotificacion.equals(other.fechaHoraNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NotificacionesProfesorPK[ idGrupo=" + idGrupo + ", fechaHoraNotificacion=" + fechaHoraNotificacion + " ]";
    }
    
}
