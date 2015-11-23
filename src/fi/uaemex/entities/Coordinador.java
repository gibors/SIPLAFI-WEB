/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
*
* @author IEEM
*/
@Entity
@Table(name = "coordinador", catalog = "SIPLAFI_DB", schema = "")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "Coordinador.findAll", query = "SELECT c FROM Coordinador c"),
   @NamedQuery(name = "findByRfcCoord", query = "SELECT c FROM Coordinador c WHERE c.rfcCoord = :rfcCoord and c.passwordCoord = :passwdCoord"),
   @NamedQuery(name = "Coordinador.findByGradoCoord", query = "SELECT c FROM Coordinador c WHERE c.gradoCoord = :gradoCoord"),
   @NamedQuery(name = "Coordinador.findByNombreCoord", query = "SELECT c FROM Coordinador c WHERE c.nombreCoord = :nombreCoord"),
   @NamedQuery(name = "Coordinador.findByApePatCoord", query = "SELECT c FROM Coordinador c WHERE c.apePatCoord = :apePatCoord"),
   @NamedQuery(name = "Coordinador.findByApeMatCoord", query = "SELECT c FROM Coordinador c WHERE c.apeMatCoord = :apeMatCoord"),
   @NamedQuery(name = "Coordinador.findByPasswordCoord", query = "SELECT c FROM Coordinador c WHERE c.passwordCoord = :passwordCoord"),
   @NamedQuery(name = "Coordinador.findByEmailCoord", query = "SELECT c FROM Coordinador c WHERE c.emailCoord = :emailCoord")})
public class Coordinador implements Serializable {
   private static final long serialVersionUID = 1L;
   @Id
   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 15)
   @Column(name = "RFC_COORD")
   private String rfcCoord;
   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 15)
   @Column(name = "GRADO_COORD")
   private String gradoCoord;
   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 35)
   @Column(name = "NOMBRE_COORD")
   private String nombreCoord;
   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 35)
   @Column(name = "APE_PAT_COORD")
   private String apePatCoord;
   @Basic(optional = false)
   @NotNull
   @Size(min = 1, max = 35)
   @Column(name = "APE_MAT_COORD")
   private String apeMatCoord;
   @Size(max = 15)
   @Column(name = "PASSWORD_COORD")
   private String passwordCoord;
   @Size(max = 50)
   @Column(name = "EMAIL_COORD")
   private String emailCoord;

   public Coordinador() {
   }

   public Coordinador(String rfcCoord) {
       this.rfcCoord = rfcCoord;
   }

   public Coordinador(String rfcCoord, String gradoCoord, String nombreCoord, String apePatCoord, String apeMatCoord) {
       this.rfcCoord = rfcCoord;
       this.gradoCoord = gradoCoord;
       this.nombreCoord = nombreCoord;
       this.apePatCoord = apePatCoord;
       this.apeMatCoord = apeMatCoord;
   }

   public String getRfcCoord() {
       return rfcCoord;
   }

   public void setRfcCoord(String rfcCoord) {
       this.rfcCoord = rfcCoord;
   }

   public String getGradoCoord() {
       return gradoCoord;
   }

   public void setGradoCoord(String gradoCoord) {
       this.gradoCoord = gradoCoord;
   }

   public String getNombreCoord() {
       return nombreCoord;
   }

   public void setNombreCoord(String nombreCoord) {
       this.nombreCoord = nombreCoord;
   }

   public String getApePatCoord() {
       return apePatCoord;
   }

   public void setApePatCoord(String apePatCoord) {
       this.apePatCoord = apePatCoord;
   }

   public String getApeMatCoord() {
       return apeMatCoord;
   }

   public void setApeMatCoord(String apeMatCoord) {
       this.apeMatCoord = apeMatCoord;
   }

   public String getPasswordCoord() {
       return passwordCoord;
   }

   public void setPasswordCoord(String passwordCoord) {
       this.passwordCoord = passwordCoord;
   }

   public String getEmailCoord() {
       return emailCoord;
   }

   public void setEmailCoord(String emailCoord) {
       this.emailCoord = emailCoord;
   }

   @Override
   public int hashCode() {
       int hash = 0;
       hash += (rfcCoord != null ? rfcCoord.hashCode() : 0);
       return hash;
   }

   @Override
   public boolean equals(Object object) {
       // TODO: Warning - this method won't work in the case the id fields are not set
       if (!(object instanceof Coordinador)) {
           return false;
       }
       Coordinador other = (Coordinador) object;
       if ((this.rfcCoord == null && other.rfcCoord != null) || (this.rfcCoord != null && !this.rfcCoord.equals(other.rfcCoord))) {
           return false;
       }
       return true;
   }

   @Override
   public String toString() {
       return "entities.Coordinador[ rfcCoord=" + rfcCoord + " ]";
   }
   
}

