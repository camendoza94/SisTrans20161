package mundo;

import java.sql.Date;

public class Mercancia {
	private enum claseMercancia{
		IMPORTACION, EXPORTACION;
	}
	private String id;
	private Float precio;
	private Date fecha;
	private claseMercancia proposito;
	private Integer cantidad;
	private String propietario;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public claseMercancia getProposito() {
		return proposito;
	}
	public void setProposito(claseMercancia proposito) {
		this.proposito = proposito;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getPropietario() {
		return propietario;
	}
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}	
}
