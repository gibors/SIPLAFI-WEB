
package fi.uaemex.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gibors
 */
@Entity
@Table(name = "GRUPO")
@XmlRootElement
@NamedQueries
    (
    {
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findByIdGrupo", query = "SELECT g FROM Grupo g WHERE g.idGrupo = :idGrupo"),
    @NamedQuery(name = "Grupo.findByNombre", query = "SELECT g FROM Grupo g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Grupo.findByValidado", query = "SELECT g FROM Grupo g WHERE g.validado = :validado"),
    @NamedQuery(name = "Grupo.findGrupos" , query = "SELECT g FROM Grupo g WHERE g.claveMateria.semestre = :semestre AND"
            + " g.idGrupo <> :idGrupo"),
    @NamedQuery(name="BuscaTraslapeLunes",query=" "
            + " SELECT g FROM Grupo g "
            + " WHERE ((g.horario.lunHoraIni > :lunIni AND g.horario.lunHoraIni < :lunFin ) OR "
            + "  (g.horario.lunHoraFin >  :lunIni AND g.horario.lunHoraFin < :lunFin )) "
            + "  AND g.idGrupo <> :idGrupo  "
            + " AND g.claveMateria.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeMartes",query=" "
            + " SELECT g FROM Grupo g "
            + " WHERE ((g.horario.marHoraIni > :marIni AND g.horario.marHoraIni < :marFin ) OR "
            + "  (g.horario.marHoraFin >  :marIni AND g.horario.marHoraFin < :marFin )) "
            + "  AND g.idGrupo <> :idGrupo  "
            + " AND g.claveMateria.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeMiercoles",query=" "
            + " SELECT g FROM Grupo g "
            + " WHERE ((g.horario.mieHoraIni > :mieIni AND g.horario.mieHoraIni < :mieFin ) OR "
            + "  (g.horario.mieHoraFin >  :mieIni AND g.horario.mieHoraFin < :mieFin )) "
            + "  AND g.idGrupo <> :idGrupo  "
            + " AND g.claveMateria.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeJueves",query=" "
            + " SELECT g FROM Grupo g "
            + " WHERE ((g.horario.jueHoraIni > :jueIni AND g.horario.jueHoraIni < :jueFin ) OR "
            + "  (g.horario.jueHoraFin >  :jueIni AND g.horario.jueHoraFin < :jueFin )) "
            + "  AND g.idGrupo <> :idGrupo  "
            + " AND g.claveMateria.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeViernes",query=" "
            + " SELECT g FROM Grupo g "
            + " WHERE ((g.horario.vieHoraIni > :vieIni AND g.horario.vieHoraIni < :vieFin ) OR "
            + "  (g.horario.vieHoraFin >  :vieIni AND g.horario.vieHoraFin < :vieFin )) "
            + "  AND g.idGrupo <> :idGrupo  "
            + " AND g.claveMateria.semestre = :semestre"),
    @NamedQuery(name="BuscaTraslapeSabado",query=" "
            + " SELECT g FROM Grupo g "
            + " WHERE ((g.horario.sabHoraIni > :sabIni AND g.horario.sabHoraIni < :sabFin ) OR "
            + "  (g.horario.sabHoraFin >  :sabIni AND g.horario.sabHoraFin < :sabFin )) "
            + "  AND g.idGrupo <> :idGrupo  "
            + " AND g.claveMateria.semestre = :semestre")   
    }
    )
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_GRUPO")
    private Integer idGrupo;
    @Size(max = 10)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "VALIDADO")
    private Integer validado;
    @OneToOne(mappedBy = "idGrupo")
    private Horario horario;
    @JoinColumn(name = "RFC_PROFESOR", referencedColumnName = "RFC_PROFESOR")
    @ManyToOne
    private Profesor rfcProfesor;
    @JoinColumn(name = "CLAVE_MATERIA", referencedColumnName = "CLAVE_MATERIA")
    @ManyToOne
    private Materia claveMateria;
    @Transient
    private Integer estado=0;
    @Transient
    private String descripcion;   
    
    public Grupo() 
    {
    }

    public Grupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getValidado() {
        return validado;
    }

    public void setValidado(Integer validado) {
        this.validado = validado;
    }

    @XmlTransient
    public Horario getHorario() {
        return horario == null ? new Horario() : horario;
    }

    public void setHorario(Horario horarioList) {
        this.horario = horarioList;
    }

    public Profesor getRfcProfesor() {
        return rfcProfesor;
    }

    public void setRfcProfesor(Profesor rfcProfesor) {
        this.rfcProfesor = rfcProfesor;
    }

    public Materia getClaveMateria() {
        return claveMateria;
    }

    public void setClaveMateria(Materia claveMateria) {
        this.claveMateria = claveMateria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
       
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) 
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Grupo[ idGrupo=" + idGrupo + " ]";
    }
    
}
