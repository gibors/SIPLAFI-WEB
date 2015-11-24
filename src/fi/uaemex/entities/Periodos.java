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
@Table(name = "periodos", catalog = "SIPLAFI_DB", schema = "")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Periodos.findAll", query = "SELECT p FROM Periodos p"),
   @NamedQuery(name = "Periodos.findByPeriodo", query = "SELECT p FROM Periodos p WHERE p.periodo = :periodo"),
   @NamedQuery(name = "Periodos.findByDescripcion", query = "SELECT p FROM Periodos p WHERE p.descripcion = :descripcion"),
   @NamedQuery(name = "Periodos.findByActual", query = "SELECT p FROM Periodos p WHERE p.actual = :actual")})
public class Periodos implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 6)
   @Column(name = "PERIODO")
   private String periodo;
   @Size(max = 150)
   @Column(name = "DESCRIPCION")
   private String descripcion;
   @Size(max = 1)
   @Column(name = "ACTUAL")
   private String actual;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
   private List<Grupo> grupoList;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
   private List<Apreciacion> apreciacionList;
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
   private List<GrupoRespaldo> grupoRespaldoList;

   public Periodos() {
   }

   public Periodos(String periodo) {
       this.periodo = periodo;
   }
   
   public Periodos(String periodo2, String descripcionPeriodo,String act) {
		// TODO Auto-generated constructor stub
   	this.periodo = periodo2;
   	this.descripcion = descripcionPeriodo;
   	this.actual = act;
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
   public List<Apreciacion> getApreciacionList() {
       return apreciacionList;
   }

   public void setApreciacionList(List<Apreciacion> apreciacionList) {
       this.apreciacionList = apreciacionList;
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
