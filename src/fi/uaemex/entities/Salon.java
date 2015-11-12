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
@Table(name = "salon", catalog = "SIPLAFI_DB", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salon.findAll", query = "SELECT s FROM Salon s"),
    @NamedQuery(name = "Salon.findByIdSalon", query = "SELECT s FROM Salon s WHERE s.idSalon = :idSalon"),
    @NamedQuery(name = "Salon.findByNombre", query = "SELECT s FROM Salon s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Salon.findByCapacidad", query = "SELECT s FROM Salon s WHERE s.capacidad = :capacidad")})
public class Salon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SALON")
    private Integer idSalon;
    @Size(max = 20)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CAPACIDAD")
    private Integer capacidad;
    @OneToMany(mappedBy = "idSalonMie")
    private List<Grupo> grupoList;
    @OneToMany(mappedBy = "idSalonSab")
    private List<Grupo> grupoList1;
    @OneToMany(mappedBy = "idSalonMar")
    private List<Grupo> grupoList2;
    @OneToMany(mappedBy = "idSalonJue")
    private List<Grupo> grupoList3;
    @OneToMany(mappedBy = "idSalonVie")
    private List<Grupo> grupoList4;
    @OneToMany(mappedBy = "idSalonLun")
    private List<Grupo> grupoList5;
    @OneToMany(mappedBy = "idSalonSab")
    private List<GrupoRespaldo> grupoRespaldoList;
    @OneToMany(mappedBy = "idSalonLun")
    private List<GrupoRespaldo> grupoRespaldoList1;
    @OneToMany(mappedBy = "idSalonMar")
    private List<GrupoRespaldo> grupoRespaldoList2;
    @OneToMany(mappedBy = "idSalonMie")
    private List<GrupoRespaldo> grupoRespaldoList3;
    @OneToMany(mappedBy = "idSalonVie")
    private List<GrupoRespaldo> grupoRespaldoList4;
    @OneToMany(mappedBy = "idSalonJue")
    private List<GrupoRespaldo> grupoRespaldoList5;

    public Salon() {
    }

    public Salon(Integer idSalon) {
        this.idSalon = idSalon;
    }

    public Integer getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(Integer idSalon) {
        this.idSalon = idSalon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
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
        hash += (idSalon != null ? idSalon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salon)) {
            return false;
        }
        Salon other = (Salon) object;
        if ((this.idSalon == null && other.idSalon != null) || (this.idSalon != null && !this.idSalon.equals(other.idSalon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Salon[ idSalon=" + idSalon + " ]";
    }
    
}
