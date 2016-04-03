package vos;

import vos.Buque.tipoMercancia;

public class Mercancia {
	private enum claseMercancia{
		IMPORTACION, EXPORTACION;
	}
	private Integer id;
	private float precio;
	private claseMercancia proposito;
	private float volumen;
	private Usuario propietario;
	private String origen;
	private String destino;
	private tipoMercancia tipoMercancia;
	private float peso;
	public Mercancia(){
		
	}
	public Mercancia(Integer id, float precio, claseMercancia proposito, float volumen, Usuario propietario,
			String origen, String destino, vos.Buque.tipoMercancia tipoMercancia, float peso) {
		super();
		this.id = id;
		this.precio = precio;
		this.proposito = proposito;
		this.volumen = volumen;
		this.propietario = propietario;
		this.origen = origen;
		this.destino = destino;
		this.tipoMercancia = tipoMercancia;
		this.peso = peso;
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
	public claseMercancia getProposito() {
		return proposito;
	}
	public void setProposito(claseMercancia proposito) {
		this.proposito = proposito;
	}
	public float getCantidad() {
		return volumen;
	}
	public void setCantidad(float cantidad) {
		this.volumen = cantidad;
	}
	public Usuario getPropietario() {
		return propietario;
	}
	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}
	public float getVolumen() {
		return volumen;
	}
	public void setVolumen(float volumen) {
		this.volumen = volumen;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public tipoMercancia getTipoMercancia() {
		return tipoMercancia;
	}
	public void setTipoMercancia(tipoMercancia tipoMercancia) {
		this.tipoMercancia = tipoMercancia;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
}
