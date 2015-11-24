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
   @OneToMany(mappedBy = "idSalon")
   private List<AulaSalonDia> aulaSalonDiaList;

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
   public List<AulaSalonDia> getAulaSalonDiaList() {
       return aulaSalonDiaList;
   }

   public void setAulaSalonDiaList(List<AulaSalonDia> aulaSalonDiaList) {
       this.aulaSalonDiaList = aulaSalonDiaList;
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
