package es.uma.informatica.sii.jpa.trazabilidad;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity @Table(name = "PRODUCTO")
	
public class Producto {
		
		@Id @GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "CODIGO_BARRAS")
	private String codigoBarras;
	private String nombre;
	private String descripcion;
	private List<Lote> lotes;
	public Producto(String codigoBarras, String nombre, String descripcion) {
		super();
		this.codigoBarras = codigoBarras;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public Producto() {
	}
	
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<Lote> getLotes() {
		return lotes;
	}
	public void setLotes(List<Lote> lotes) {
		this.lotes = lotes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoBarras == null) ? 0 : codigoBarras.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((lotes == null) ? 0 : lotes.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Producto other = (Producto) obj;
		if (codigoBarras == null) {
			if (other.codigoBarras != null)
				return false;
		} else if (!codigoBarras.equals(other.codigoBarras))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (lotes == null) {
			if (other.lotes != null)
				return false;
		} else if (!lotes.equals(other.lotes))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Producto [codigoBarras=" + codigoBarras + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", lotes=" + lotes + "]";
	}
	
	

}
