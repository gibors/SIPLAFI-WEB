/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IEEM
 */
@Entity
@Table(name = "apreciacion", catalog = "SIPLAFI_DB", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apreciacion.findAll", query = "SELECT a FROM Apreciacion a"),
    @NamedQuery(name = "Apreciacion.findAllCurrent", query = "SELECT a FROM Apreciacion a, Periodos p WHERE a.apreciacionPK.periodo = p.periodo AND p.actual = 1"),    
    @NamedQuery(name = "Apreciacion.findByRfcProfesor", query = "SELECT a FROM Apreciacion a WHERE a.apreciacionPK.rfcProfesor = :rfcProfesor"),
    @NamedQuery(name = "Apreciacion.findByPeriodo", query = "SELECT a FROM Apreciacion a WHERE a.apreciacionPK.periodo = :periodo"),
    @NamedQuery(name = "Apreciacion.findByCalificacion", query = "SELECT a FROM Apreciacion a WHERE a.calificacion = :calificacion")})
public class Apreciacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ApreciacionPK apreciacionPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CALIFICACION")
    private Double calificacion;
    @JoinColumn(name = "PERIODO", referencedColumnName = "PERIODO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Periodos periodos;
    @JoinColumn(name = "RFC_PROFESOR", referencedColumnName = "RFC_PROFESOR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Profesor profesor;

    public Apreciacion() {
    }

    public Apreciacion(ApreciacionPK apreciacionPK) {
        this.apreciacionPK = apreciacionPK;
    }

    public Apreciacion(String rfcProfesor, String periodo) {
        this.apreciacionPK = new ApreciacionPK(rfcProfesor, periodo);
    }

    public ApreciacionPK getApreciacionPK() {
        return apreciacionPK;
    }

    public void setApreciacionPK(ApreciacionPK apreciacionPK) {
        this.apreciacionPK = apreciacionPK;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Periodos getPeriodos() {
        return periodos;
    }

    public void setPeriodos(Periodos periodos) {
        this.periodos = periodos;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apreciacionPK != null ? apreciacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apreciacion)) {
            return false;
        }
        Apreciacion other = (Apreciacion) object;
        if ((this.apreciacionPK == null && other.apreciacionPK != null) || (this.apreciacionPK != null && !this.apreciacionPK.equals(other.apreciacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Apreciacion[ apreciacionPK=" + apreciacionPK + " ]";
    }
    
}
