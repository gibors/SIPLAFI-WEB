package fi.uaemex.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the apreciacion database table.
 * 
 */
@Entity
@NamedQuery(name="Apreciacion.findAll", query="SELECT a FROM Apreciacion a")
public class Apreciacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApreciacionPK id;

	private double calificacion;

	public Apreciacion() {
	}

	public ApreciacionPK getId() {
		return this.id;
	}

	public void setId(ApreciacionPK id) {
		this.id = id;
	}

	public double getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}

}