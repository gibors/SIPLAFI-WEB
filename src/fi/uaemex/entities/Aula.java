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
   @OneToMany(mappedBy = "idAula")
   private List<AulaSalonDia> aulaSalonDiaList;

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
   public List<AulaSalonDia> getAulaSalonDiaList() {
       return aulaSalonDiaList;
   }

   public void setAulaSalonDiaList(List<AulaSalonDia> aulaSalonDiaList) {
       this.aulaSalonDiaList = aulaSalonDiaList;
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

