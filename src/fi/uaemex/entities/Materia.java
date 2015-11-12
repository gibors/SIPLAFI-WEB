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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author IEEM
 */
@Entity
@Table(name = "materia", catalog = "SIPLAFI_DB", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materia.findAll", query = "SELECT m FROM Materia m"),
    @NamedQuery(name = "Materia.findByClaveMateria", query = "SELECT m FROM Materia m WHERE m.claveMateria = :claveMateria"),
    @NamedQuery(name = "Materia.findByNombreMateria", query = "SELECT m FROM Materia m WHERE m.nombreMateria = :nombreMateria"),
    @NamedQuery(name = "Materia.findByHoras", query = "SELECT m FROM Materia m WHERE m.horas = :horas"),
    @NamedQuery(name = "Materia.findBySemestre", query = "SELECT m FROM Materia m WHERE m.semestre = :semestre")})
public class Materia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "CLAVE_MATERIA")
    private String claveMateria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "NOMBRE_MATERIA")
    private String nombreMateria;
    @Column(name = "HORAS")
    private Integer horas;
    @Column(name = "SEMESTRE")
    private Integer semestre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<Grupo> grupoList;
    @JoinColumn(name = "ID_ACADEMIA", referencedColumnName = "ID_ACADEMIA")
    @ManyToOne
    private Academia idAcademia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<GrupoRespaldo> grupoRespaldoList;

    public Materia() {
    }

    public Materia(String claveMateria) {
        this.claveMateria = claveMateria;
    }

    public Materia(String claveMateria, String nombreMateria) {
        this.claveMateria = claveMateria;
        this.nombreMateria = nombreMateria;
    }

    public String getClaveMateria() {
        return claveMateria;
    }

    public void setClaveMateria(String claveMateria) {
        this.claveMateria = claveMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    public Academia getIdAcademia() {
        return idAcademia;
    }

    public void setIdAcademia(Academia idAcademia) {
        this.idAcademia = idAcademia;
    }

    @XmlTransient
    public List<GrupoRespaldo> getGrupoRespaldoList() {
        return grupoRespaldoList;
    }

    public void setGrupoRespaldoList(List<GrupoRespaldo> grupoRespaldoList) {
        this.grupoRespaldoList = grupoRespaldoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (claveMateria != null ? claveMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.claveMateria == null && other.claveMateria != null) || (this.claveMateria != null && !this.claveMateria.equals(other.claveMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Materia[ claveMateria=" + claveMateria + " ]";
    }
    
}
