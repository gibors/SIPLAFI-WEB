package fi.uaemex.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQuery(name="Apreciacion.findAll", query="SELECT a FROM Apreciacion a, Periodos p  WHERE a.id.periodo = p.periodo AND p.actual = 1 ")
public class Apreciacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApreciacionPK id;
	private Profesor profesor;
	private Double calificacion;

	public Apreciacion() {
		// TODO Auto-generated constructor stub
	}
	
	public Apreciacion(ApreciacionPK pk) 
	{
		this.id = pk;
	}

	public ApreciacionPK getId() {
		return this.id;
	}

	public void setId(ApreciacionPK id) {
		this.id = id;
	}

	public Double getCalificacion() {
		return this.calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}

}