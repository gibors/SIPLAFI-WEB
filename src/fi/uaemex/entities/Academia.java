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
@Table(name = "academia", catalog = "SIPLAFI_DB", schema = "")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Academia.findAll", query = "SELECT a FROM Academia a"),
   @NamedQuery(name = "Academia.findByIdAcademia", query = "SELECT a FROM Academia a WHERE a.idAcademia = :idAcademia"),
   @NamedQuery(name = "Academia.findByNombre", query = "SELECT a FROM Academia a WHERE a.nombre = :nombre")})
public class Academia implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Basic(optional = false)
   @Column(name = "ID_ACADEMIA")
   private Integer idAcademia;
   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 45)
   @Column(name = "NOMBRE")
   private String nombre;
   @OneToMany(mappedBy = "idAcademia")
   private List<Materia> materiaList;

   public Academia() {
   }

   public Academia(Integer idAcademia) {
       this.idAcademia = idAcademia;
   }

   public Academia(Integer idAcademia, String nombre) {
       this.idAcademia = idAcademia;
       this.nombre = nombre;
   }

   public Integer getIdAcademia() {
       return idAcademia;
   }

   public void setIdAcademia(Integer idAcademia) {
       this.idAcademia = idAcademia;
   }

   public String getNombre() {
       return nombre;
   }

   public void setNombre(String nombre) {
       this.nombre = nombre;
   }

   @XmlTransient
   public List<Materia> getMateriaList() {
       return materiaList;
   }

   public void setMateriaList(List<Materia> materiaList) {
       this.materiaList = materiaList;
   }

   @Override
   public int hashCode() {
       int hash = 0;
       hash += (idAcademia != null ? idAcademia.hashCode() : 0);
       return hash;
   }

   @Override
   public boolean equals(Object object) {
       // TODO: Warning - this method won't work in the case the id fields are not set
       if (!(object instanceof Academia)) {
           return false;
       }
       Academia other = (Academia) object;
       if ((this.idAcademia == null && other.idAcademia != null) || (this.idAcademia != null && !this.idAcademia.equals(other.idAcademia))) {
           return false;
       }
       return true;
   }

   @Override
   public String toString() {
       return "entities.Academia[ idAcademia=" + idAcademia + " ]";
   }
   
}
