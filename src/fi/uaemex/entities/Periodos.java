
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
@Table(name = "PERIODOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodos.findAll", query = "SELECT p FROM Periodos p"),
    @NamedQuery(name = "Periodos.findByPeriodo", query = "SELECT p FROM Periodos p WHERE p.periodo = :periodo"),
    @NamedQuery(name = "Periodos.findCurrent", query = "SELECT p FROM Periodos p WHERE p.actual = 1"),    
    @NamedQuery(name = "Periodos.findByDescripcion", query = "SELECT p FROM Periodos p WHERE p.descripcion = :descripcion")})
public class Periodos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "PERIODO")
    private String periodo;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "ACTUAL")
    private String actual;
    
    public Periodos() {
    }

    public Periodos(String periodo) {
        this.periodo = periodo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (periodo != null ? periodo.hashCode() : 0);
        return hash;
    }
        
    public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
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
