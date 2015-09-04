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
 * @author gibors
 */
@Entity
@Table(name = "PROFESOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p ORDER BY p.nombreProfe"),
    @NamedQuery(name = "Profesor.findByRfcProfesor", query = "SELECT p FROM Profesor p WHERE p.rfcProfesor = :rfcProfesor"),
    @NamedQuery(name = "Profesor.findByGradoProfe", query = "SELECT p FROM Profesor p WHERE p.gradoProfe = :gradoProfe"),
    @NamedQuery(name = "Profesor.findByNombreProfe", query = "SELECT p FROM Profesor p WHERE p.nombreProfe = :nombreProfe"),
    @NamedQuery(name = "Profesor.findByApePatProfe", query = "SELECT p FROM Profesor p WHERE p.apePatProfe = :apePatProfe"),
    @NamedQuery(name = "Profesor.findByApeMatProfe", query = "SELECT p FROM Profesor p WHERE p.apeMatProfe = :apeMatProfe"),
    @NamedQuery(name = "Profesor.findByPasswordProfe", query = "SELECT p FROM Profesor p WHERE p.passwordProfe = :passwordProfe"),
    @NamedQuery(name = "Profesor.findByEmailProfe", query = "SELECT p FROM Profesor p WHERE p.emailProfe = :emailProfe"),
    @NamedQuery(name = "findUser", query = "SELECT p FROM Profesor p WHERE p.rfcProfesor = :rfc AND p.passwordProfe = :pass")})
public class Profesor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "RFC_PROFESOR")
    private String rfcProfesor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "GRADO_PROFE")
    private String gradoProfe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "NOMBRE_PROFE")
    private String nombreProfe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "APE_PAT_PROFE")
    private String apePatProfe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "APE_MAT_PROFE")
    private String apeMatProfe;
    @Size(max = 15)
    @Column(name = "PASSWORD_PROFE")
    private String passwordProfe;
    @Size(max = 50)
    @Column(name = "EMAIL_PROFE")
    private String emailProfe;
    @OneToMany(mappedBy = "rfcProfesor")
    private List<Grupo> grupoList;

    public Profesor() {
    }

    public Profesor(String rfcProfesor) {
        this.rfcProfesor = rfcProfesor;
    }

    public Profesor(String rfcProfesor, String gradoProfe, String nombreProfe, String apePatProfe, String apeMatProfe) {
        this.rfcProfesor = rfcProfesor;
        this.gradoProfe = gradoProfe;
        this.nombreProfe = nombreProfe;
        this.apePatProfe = apePatProfe;
        this.apeMatProfe = apeMatProfe;
    }

    public String getRfcProfesor() {
        return rfcProfesor;
    }

    public void setRfcProfesor(String rfcProfesor) {
        this.rfcProfesor = rfcProfesor;
    }

    public String getGradoProfe() {
        return gradoProfe;
    }

    public void setGradoProfe(String gradoProfe) {
        this.gradoProfe = gradoProfe;
    }

    public String getNombreProfe() {
        return nombreProfe;
    }

    public void setNombreProfe(String nombreProfe) {
        this.nombreProfe = nombreProfe;
    }

    public String getApePatProfe() {
        return apePatProfe;
    }

    public void setApePatProfe(String apePatProfe) {
        this.apePatProfe = apePatProfe;
    }

    public String getApeMatProfe() {
        return apeMatProfe;
    }

    public void setApeMatProfe(String apeMatProfe) {
        this.apeMatProfe = apeMatProfe;
    }

    public String getPasswordProfe() {
        return passwordProfe;
    }

    public void setPasswordProfe(String passwordProfe) {
        this.passwordProfe = passwordProfe;
    }

    public String getEmailProfe() {
        return emailProfe;
    }

    public void setEmailProfe(String emailProfe) {
        this.emailProfe = emailProfe;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rfcProfesor != null ? rfcProfesor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.rfcProfesor == null && other.rfcProfesor != null) || (this.rfcProfesor != null && !this.rfcProfesor.equals(other.rfcProfesor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Profesor[ rfcProfesor=" + rfcProfesor + " ]";
    }
    
}
