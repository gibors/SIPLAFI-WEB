package fi.uaemex.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the apreciacion database table.
 * 
 */
@Embeddable
public class ApreciacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	
	@Column(name="RFC_PROFESOR")
	private String rfcProfesor;

	private String periodo;

	public ApreciacionPK() {
		// TODO Auto-generated constructor stub
	}
	public ApreciacionPK(String rfc, String periodo) 
	{
		this.rfcProfesor = rfc;
		this.periodo = periodo;
	}
	public String getRfcProfesor() {
		return this.rfcProfesor;
	}
	public void setRfcProfesor(String rfcProfesor) {
		this.rfcProfesor = rfcProfesor;
	}
	public String getPeriodo() {
		return this.periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ApreciacionPK)) {
			return false;
		}
		ApreciacionPK castOther = (ApreciacionPK)other;
		return 
			this.rfcProfesor.equals(castOther.rfcProfesor)
			&& this.periodo.equals(castOther.periodo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.rfcProfesor.hashCode();
		hash = hash * prime + this.periodo.hashCode();
		
		return hash;
	}
}