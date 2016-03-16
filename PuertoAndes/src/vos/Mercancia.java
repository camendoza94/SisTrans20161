package vos;

import java.sql.Date;

public class Mercancia {
	private enum claseMercancia{
		IMPORTACION, EXPORTACION;
	}
	private Integer id;
	private float precio;
	private Date fecha;
	private claseMercancia proposito;
	private float cantidad;
	private Usuario propietario;
	public Mercancia(){
		
	}
	public Mercancia(Integer id, float precio, Date fecha, claseMercancia proposito, float cantidad,
			Usuario propietario) {
		super();
		this.id = id;
		this.precio = precio;
		this.fecha = fecha;
		this.proposito = proposito;
		this.cantidad = cantidad;
		this.propietario = propietario;
	}
	public Mercancia(Integer id) {
		super();
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Usuario getPropietario() {
		return propietario;
	}
	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}	
}
