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
import javax.validation.constraints.Size;

/**
 *
 * @author IEEM
 */
@Embeddable
public class NotificacionesCoordPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    //@Size(min = 1, max = 8)
    @Column(name = "CLAVE_MATERIA", nullable = false, length = 8)
    private String claveMateria;
    @Basic(optional = false)
    @NotNull
    //@Size(min = 1, max = 6)
    @Column(nullable = false, length = 6)
    private String periodo;
    @Basic(optional = false)
    @NotNull
    //@Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA_NOTIF", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraNotif;

    public NotificacionesCoordPK() 
    {
    }

    public NotificacionesCoordPK(String claveMateria, String periodo, String nombre, Date fechaHoraNotif) {
        this.claveMateria = claveMateria;
        this.periodo = periodo;
        this.nombre = nombre;
        this.fechaHoraNotif = fechaHoraNotif;
    }

    public String getClaveMateria() {
        return claveMateria;
    }

    public void setClaveMateria(String claveMateria) {
        this.claveMateria = claveMateria;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaHoraNotif() {
        return fechaHoraNotif;
    }

    public void setFechaHoraNotif(Date fechaHoraNotif) {
        this.fechaHoraNotif = fechaHoraNotif;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (claveMateria != null ? claveMateria.hashCode() : 0);
        hash += (periodo != null ? periodo.hashCode() : 0);
        hash += (nombre != null ? nombre.hashCode() : 0);
        hash += (fechaHoraNotif != null ? fechaHoraNotif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificacionesCoordPK)) {
            return false;
        }
        NotificacionesCoordPK other = (NotificacionesCoordPK) object;
        if ((this.claveMateria == null && other.claveMateria != null) || (this.claveMateria != null && !this.claveMateria.equals(other.claveMateria))) {
            return false;
        }
        if ((this.periodo == null && other.periodo != null) || (this.periodo != null && !this.periodo.equals(other.periodo))) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        if ((this.fechaHoraNotif == null && other.fechaHoraNotif != null) || (this.fechaHoraNotif != null && !this.fechaHoraNotif.equals(other.fechaHoraNotif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.NotificacionesCoordPK[ claveMateria=" + claveMateria + ", periodo=" + periodo + ", nombre=" + nombre + ", fechaHoraNotif=" + fechaHoraNotif + " ]";
    }
    
}
