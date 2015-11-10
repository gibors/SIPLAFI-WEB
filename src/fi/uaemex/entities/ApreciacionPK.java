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
public class ApreciacionPK implements Serializable {
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

    public ApreciacionPK() {
    }

    public ApreciacionPK(String rfcProfesor, String periodo) {
        this.rfcProfesor = rfcProfesor;
        this.periodo = periodo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rfcProfesor != null ? rfcProfesor.hashCode() : 0);
        hash += (periodo != null ? periodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApreciacionPK)) {
            return false;
        }
        ApreciacionPK other = (ApreciacionPK) object;
        if ((this.rfcProfesor == null && other.rfcProfesor != null) || (this.rfcProfesor != null && !this.rfcProfesor.equals(other.rfcProfesor))) {
            return false;
        }
        if ((this.periodo == null && other.periodo != null) || (this.periodo != null && !this.periodo.equals(other.periodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ApreciacionPK[ rfcProfesor=" + rfcProfesor + ", periodo=" + periodo + " ]";
    }
    
}
