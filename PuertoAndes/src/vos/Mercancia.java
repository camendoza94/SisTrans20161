package vos;

import java.sql.Date;

public class Mercancia {
	private enum claseMercancia{
		IMPORTACION, EXPORTACION;
	}
	private String id;
	private float precio;
	private Date fecha;
	private claseMercancia proposito;
	private float cantidad;
	private String propietario;
	
	public Mercancia(String id, float precio, Date fecha, claseMercancia proposito, float cantidad,
			String propietario) {
		super();
		this.id = id;
		this.precio = precio;
		this.fecha = fecha;
		this.proposito = proposito;
		this.cantidad = cantidad;
		this.propietario = propietario;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
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
	public float getCantidad() {
		return cantidad;
	}
	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}
	public String getPropietario() {
		return propietario;
	}
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}	
}
