/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author IEEM
 */
@Embeddable
public class GrupoRespaldoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "CLAVE_MATERIA")
    private String claveMateria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "RFC_PROFESOR")
    private String rfcProfesor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "PERIODO")
    private String periodo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NOMBRE")
    private String nombre;

    public GrupoRespaldoPK() {
    }

    public GrupoRespaldoPK(String claveMateria, String rfcProfesor, String periodo, String nombre) {
        this.claveMateria = claveMateria;
        this.rfcProfesor = rfcProfesor;
        this.periodo = periodo;
        this.nombre = nombre;
    }

    public String getClaveMateria() {
        return claveMateria;
    }

    public void setClaveMateria(String claveMateria) {
        this.claveMateria = claveMateria;
    }

    public String getRfcProfesor() {
        return rfcProfesor;
    }

    public void setRfcProfesor(String rfcProfesor) {
        this.rfcProfesor = rfcProfesor;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (claveMateria != null ? claveMateria.hashCode() : 0);
        hash += (rfcProfesor != null ? rfcProfesor.hashCode() : 0);
        hash += (periodo != null ? periodo.hashCode() : 0);
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoRespaldoPK)) {
            return false;
        }
        GrupoRespaldoPK other = (GrupoRespaldoPK) object;
        if ((this.claveMateria == null && other.claveMateria != null) || (this.claveMateria != null && !this.claveMateria.equals(other.claveMateria))) {
            return false;
        }
        if ((this.rfcProfesor == null && other.rfcProfesor != null) || (this.rfcProfesor != null && !this.rfcProfesor.equals(other.rfcProfesor))) {
            return false;
        }
        if ((this.periodo == null && other.periodo != null) || (this.periodo != null && !this.periodo.equals(other.periodo))) {
            return false;
        }
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.GrupoRespaldoPK[ claveMateria=" + claveMateria + ", rfcProfesor=" + rfcProfesor + ", periodo=" + periodo + ", nombre=" + nombre + " ]";
    }
    
}
