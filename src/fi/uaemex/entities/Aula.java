/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "aula", catalog = "SIPLAFI_DB", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aula.findAll", query = "SELECT a FROM Aula a"),
    @NamedQuery(name = "Aula.findByIdAula", query = "SELECT a FROM Aula a WHERE a.idAula = :idAula"),
    @NamedQuery(name = "Aula.findByTipoAula", query = "SELECT a FROM Aula a WHERE a.tipoAula = :tipoAula"),
    @NamedQuery(name = "Aula.findByNombre", query = "SELECT a FROM Aula a WHERE a.nombre = :nombre")})
public class Aula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_AULA")
    private Integer idAula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TIPO_AULA")
    private String tipoAula;
    @Size(max = 75)
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(mappedBy = "aulaLun")
    private List<Grupo> grupoList;
    @OneToMany(mappedBy = "aulaJue")
    private List<Grupo> grupoList1;
    @OneToMany(mappedBy = "aulaMar")
    private List<Grupo> grupoList2;
    @OneToMany(mappedBy = "aulaMie")
    private List<Grupo> grupoList3;
    @OneToMany(mappedBy = "aulaSab")
    private List<Grupo> grupoList4;
    @OneToMany(mappedBy = "aulaVie")
    private List<Grupo> grupoList5;
    @OneToMany(mappedBy = "aulaJue")
    private List<GrupoRespaldo> grupoRespaldoList;
    @OneToMany(mappedBy = "aulaLun")
    private List<GrupoRespaldo> grupoRespaldoList1;
    @OneToMany(mappedBy = "aulaMar")
    private List<GrupoRespaldo> grupoRespaldoList2;
    @OneToMany(mappedBy = "aulaMie")
    private List<GrupoRespaldo> grupoRespaldoList3;
    @OneToMany(mappedBy = "aulaSab")
    private List<GrupoRespaldo> grupoRespaldoList4;
    @OneToMany(mappedBy = "aulaVie")
    private List<GrupoRespaldo> grupoRespaldoList5;

    public Aula() {
    }

    public Aula(Integer idAula) {
        this.idAula = idAula;
    }

    public Aula(Integer idAula, String tipoAula) {
        this.idAula = idAula;
        this.tipoAula = tipoAula;
    }

    public Integer getIdAula() {
        return idAula;
    }

    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public String getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(String tipoAula) {
        this.tipoAula = tipoAula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @XmlTransient
    public List<Grupo> getGrupoList1() {
        return grupoList1;
    }

    public void setGrupoList1(List<Grupo> grupoList1) {
        this.grupoList1 = grupoList1;
    }

    @XmlTransient
    public List<Grupo> getGrupoList2() {
        return grupoList2;
    }

    public void setGrupoList2(List<Grupo> grupoList2) {
        this.grupoList2 = grupoList2;
    }

    @XmlTransient
    public List<Grupo> getGrupoList3() {
        return grupoList3;
    }

    public void setGrupoList3(List<Grupo> grupoList3) {
        this.grupoList3 = grupoList3;
    }

    @XmlTransient
    public List<Grupo> getGrupoList4() {
        return grupoList4;
    }

    public void setGrupoList4(List<Grupo> grupoList4) {
        this.grupoList4 = grupoList4;
    }

    @XmlTransient
    public List<Grupo> getGrupoList5() {
        return grupoList5;
    }

    public void setGrupoList5(List<Grupo> grupoList5) {
        this.grupoList5 = grupoList5;
    }

    @XmlTransient
    public List<GrupoRespaldo> getGrupoRespaldoList() {
        return grupoRespaldoList;
    }

    public void setGrupoRespaldoList(List<GrupoRespaldo> grupoRespaldoList) {
        this.grupoRespaldoList = grupoRespaldoList;
    }

    @XmlTransient
    public List<GrupoRespaldo> getGrupoRespaldoList1() {
        return grupoRespaldoList1;
    }

    public void setGrupoRespaldoList1(List<GrupoRespaldo> grupoRespaldoList1) {
        this.grupoRespaldoList1 = grupoRespaldoList1;
    }

    @XmlTransient
    public List<GrupoRespaldo> getGrupoRespaldoList2() {
        return grupoRespaldoList2;
    }

    public void setGrupoRespaldoList2(List<GrupoRespaldo> grupoRespaldoList2) {
        this.grupoRespaldoList2 = grupoRespaldoList2;
    }

    @XmlTransient
    public List<GrupoRespaldo> getGrupoRespaldoList3() {
        return grupoRespaldoList3;
    }

    public void setGrupoRespaldoList3(List<GrupoRespaldo> grupoRespaldoList3) {
        this.grupoRespaldoList3 = grupoRespaldoList3;
    }

    @XmlTransient
    public List<GrupoRespaldo> getGrupoRespaldoList4() {
        return grupoRespaldoList4;
    }

    public void setGrupoRespaldoList4(List<GrupoRespaldo> grupoRespaldoList4) {
        this.grupoRespaldoList4 = grupoRespaldoList4;
    }

    @XmlTransient
    public List<GrupoRespaldo> getGrupoRespaldoList5() {
        return grupoRespaldoList5;
    }

    public void setGrupoRespaldoList5(List<GrupoRespaldo> grupoRespaldoList5) {
        this.grupoRespaldoList5 = grupoRespaldoList5;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAula != null ? idAula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aula)) {
            return false;
        }
        Aula other = (Aula) object;
        if ((this.idAula == null && other.idAula != null) || (this.idAula != null && !this.idAula.equals(other.idAula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Aula[ idAula=" + idAula + " ]";
    }
    
}
