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
public class AulaSalonDiaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DIA")
    private int idDia;
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
    @Column(name = "NOMBRE_GPO")
    private String nombreGpo;

    public AulaSalonDiaPK() {
    }

    public AulaSalonDiaPK(int idDia, String claveMateria, String rfcProfesor, String periodo, String nombreGpo) {
        this.idDia = idDia;
        this.claveMateria = claveMateria;
        this.rfcProfesor = rfcProfesor;
        this.periodo = periodo;
        this.nombreGpo = nombreGpo;
    }

    public int getIdDia() {
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
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

    public String getNombreGpo() {
        return nombreGpo;
    }

    public void setNombreGpo(String nombreGpo) {
        this.nombreGpo = nombreGpo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDia;
        hash += (claveMateria != null ? claveMateria.hashCode() : 0);
        hash += (rfcProfesor != null ? rfcProfesor.hashCode() : 0);
        hash += (periodo != null ? periodo.hashCode() : 0);
        hash += (nombreGpo != null ? nombreGpo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AulaSalonDiaPK)) {
            return false;
        }
        AulaSalonDiaPK other = (AulaSalonDiaPK) object;
        if (this.idDia != other.idDia) {
            return false;
        }
        if ((this.claveMateria == null && other.claveMateria != null) || (this.claveMateria != null && !this.claveMateria.equals(other.claveMateria))) {
            return false;
        }
        if ((this.rfcProfesor == null && other.rfcProfesor != null) || (this.rfcProfesor != null && !this.rfcProfesor.equals(other.rfcProfesor))) {
            return false;
        }
        if ((this.periodo == null && other.periodo != null) || (this.periodo != null && !this.periodo.equals(other.periodo))) {
            return false;
        }
        if ((this.nombreGpo == null && other.nombreGpo != null) || (this.nombreGpo != null && !this.nombreGpo.equals(other.nombreGpo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AulaSalonDiaPK[ idDia=" + idDia + ", claveMateria=" + claveMateria + ", rfcProfesor=" + rfcProfesor + ", periodo=" + periodo + ", nombreGpo=" + nombreGpo + " ]";
    }
    
}
