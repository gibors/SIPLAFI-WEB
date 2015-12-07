/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IEEM
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodos.findAll", query = "SELECT p FROM Periodos p"),
    @NamedQuery(name = "Periodos.findByPeriodo", query = "SELECT p FROM Periodos p WHERE p.periodo = :periodo"),
    @NamedQuery(name = "Periodos.findByDescripcion", query = "SELECT p FROM Periodos p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Periodos.findByActual", query = "SELECT p FROM Periodos p WHERE p.actual = 1")})
public class Periodos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(nullable = false, length = 6)
    private String periodo;
    @Size(max = 150)
    @Column(length = 150)
    private String descripcion;
    @Size(max = 1)
    @Column(length = 1)
    private String actual;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
    private List<Grupo> grupoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
    private List<GrupoRespaldo> grupoRespaldoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
    private List<AulaSalonDia> aulaSalonDiaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
    private List<NotificacionesCoord> notificacionesCoordList;

    public Periodos() {
    }

    public Periodos(String periodo, String desc , String actual) {
    	
        this.periodo = periodo;
        this.descripcion = desc;
        this.actual = actual;
    }

    public Periodos(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @XmlTransient
    public List<GrupoRespaldo> getGrupoRespaldoList() {
        return grupoRespaldoList;
    }

    public void setGrupoRespaldoList(List<GrupoRespaldo> grupoRespaldoList) {
        this.grupoRespaldoList = grupoRespaldoList;
    }

    @XmlTransient
    public List<AulaSalonDia> getAulaSalonDiaList() {
        return aulaSalonDiaList;
    }

    public void setAulaSalonDiaList(List<AulaSalonDia> aulaSalonDiaList) {
        this.aulaSalonDiaList = aulaSalonDiaList;
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
        hash += (periodo != null ? periodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodos)) {
            return false;
        }
        Periodos other = (Periodos) object;
        if ((this.periodo == null && other.periodo != null) || (this.periodo != null && !this.periodo.equals(other.periodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Periodos[ periodo=" + periodo + " ]";
    }
    
}
