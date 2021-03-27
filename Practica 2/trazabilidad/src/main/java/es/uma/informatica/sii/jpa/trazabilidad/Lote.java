package es.uma.informatica.sii.jpa.trazabilidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

	@Entity @Table(name = "LOTE")
public class Lote {
		
		@Id @GeneratedValue
	private Long id;
		
	@Column(name="FABRICACION")
	@Temporal(TemporalType.DATE)
	private Date fechaFabricacion;
	
	@Column(name="CONSUMO_PREFERENTE")
	@Temporal(TemporalType.DATE)
	
	private Date fechaConsumoPreferente;
	private String codigoLote;
	private Double cantidad;
	
	public Lote(Date fechaFabricacion, Date fechaConsumoPreferente, String codigoLote, Double cantidad) {
		super();
		this.fechaFabricacion = fechaFabricacion;
		this.fechaConsumoPreferente = fechaConsumoPreferente;
		this.codigoLote = codigoLote;
		this.cantidad = cantidad;
	}
	
	public Lote() {
	}
	
	public Date getFechaFabricacion() {
		return fechaFabricacion;
	}
	public void setFechaFabricacion(Date fechaFabricacion) {
		this.fechaFabricacion = fechaFabricacion;
	}
	public Date getFechaConsumoPreferente() {
		return fechaConsumoPreferente;
	}
	public void setFechaConsumoPreferente(Date fechaConsumoPreferente) {
		this.fechaConsumoPreferente = fechaConsumoPreferente;
	}
	public String getCodigoLote() {
		return codigoLote;
	}
	public void setCodigoLote(String codigoLote) {
		this.codigoLote = codigoLote;
	}
	public Double getCantidad() {
		return cantidad;
	}
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lote other = (Lote) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lote [id=" + id + ", fechaFabricacion=" + fechaFabricacion + ", fechaConsumoPreferente="
				+ fechaConsumoPreferente + ", codigoLote=" + codigoLote + ", cantidad=" + cantidad + "]";
	}
	
}
