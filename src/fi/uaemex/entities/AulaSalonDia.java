/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.uaemex.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author IEEM
 */
@Entity
@Table(name = "aula_salon_dia", catalog = "SIPLAFI_DB", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AulaSalonDia.findAll", query = "SELECT a FROM AulaSalonDia a"),
    @NamedQuery(name = "AulaSalonDia.findByIdDia", query = "SELECT a FROM AulaSalonDia a WHERE a.aulaSalonDiaPK.idDia = :idDia"),
    @NamedQuery(name = "AulaSalonDia.findByClaveMateria", query = "SELECT a FROM AulaSalonDia a WHERE a.aulaSalonDiaPK.claveMateria = :claveMateria"),
    @NamedQuery(name = "AulaSalonDia.findByRfcProfesor", query = "SELECT a FROM AulaSalonDia a WHERE a.aulaSalonDiaPK.rfcProfesor = :rfcProfesor"),
    @NamedQuery(name = "AulaSalonDia.findByPeriodo", query = "SELECT a FROM AulaSalonDia a WHERE a.aulaSalonDiaPK.periodo = :periodo"),
    @NamedQuery(name = "AulaSalonDia.findByNombreGpo", query = "SELECT a FROM AulaSalonDia a WHERE a.aulaSalonDiaPK.nombreGpo = :nombreGpo")})
public class AulaSalonDia implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AulaSalonDiaPK aulaSalonDiaPK;
    @JoinColumns({
        @JoinColumn(name = "CLAVE_MATERIA", referencedColumnName = "CLAVE_MATERIA", insertable = false, updatable = false),
        @JoinColumn(name = "RFC_PROFESOR", referencedColumnName = "RFC_PROFESOR", insertable = false, updatable = false),
        @JoinColumn(name = "PERIODO", referencedColumnName = "PERIODO", insertable = false, updatable = false),
        @JoinColumn(name = "NOMBRE_GPO", referencedColumnName = "NOMBRE", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "ID_AULA", referencedColumnName = "ID_AULA")
    @ManyToOne
    private Aula idAula;
    @JoinColumn(name = "ID_DIA", referencedColumnName = "id_dia", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Dia dia;
    @JoinColumn(name = "ID_SALON", referencedColumnName = "ID_SALON")
    @ManyToOne
    private Salon idSalon;

    public AulaSalonDia() {
    }

    public AulaSalonDia(AulaSalonDiaPK aulaSalonDiaPK) {
        this.aulaSalonDiaPK = aulaSalonDiaPK;
    }

    public AulaSalonDia(int idDia, String claveMateria, String rfcProfesor, String periodo, String nombreGpo) {
        this.aulaSalonDiaPK = new AulaSalonDiaPK(idDia, claveMateria, rfcProfesor, periodo, nombreGpo);
    }

    public AulaSalonDiaPK getAulaSalonDiaPK() {
        return aulaSalonDiaPK;
    }

    public void setAulaSalonDiaPK(AulaSalonDiaPK aulaSalonDiaPK) {
        this.aulaSalonDiaPK = aulaSalonDiaPK;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Aula getIdAula() {
        return idAula;
    }

    public void setIdAula(Aula idAula) {
        this.idAula = idAula;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Salon getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(Salon idSalon) {
        this.idSalon = idSalon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aulaSalonDiaPK != null ? aulaSalonDiaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AulaSalonDia)) {
            return false;
        }
        AulaSalonDia other = (AulaSalonDia) object;
        if ((this.aulaSalonDiaPK == null && other.aulaSalonDiaPK != null) || (this.aulaSalonDiaPK != null && !this.aulaSalonDiaPK.equals(other.aulaSalonDiaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AulaSalonDia[ aulaSalonDiaPK=" + aulaSalonDiaPK + " ]";
    }
    
}
