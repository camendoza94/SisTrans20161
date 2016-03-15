package vos;

import java.sql.Date;

public class ArriboBuque {
	private Date fecha;
	private Puerto puertoAnterior;
	private Puerto puertoSiguiente;	
	private Buque buque;
	public ArriboBuque(Date fecha, Puerto puertoAnterior, Puerto puertoSiguiente, Buque buque) {
		super();
		this.fecha = fecha;
		this.puertoAnterior = puertoAnterior;
		this.puertoSiguiente = puertoSiguiente;
		this.buque = buque;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Puerto getPuertoAnterior() {
		return puertoAnterior;
	}
	public void setPuertoAnterior(Puerto puertoAnterior) {
		this.puertoAnterior = puertoAnterior;
	}
	public Puerto getPuertoSiguiente() {
		return puertoSiguiente;
	}
	public void setPuertoSiguiente(Puerto puertoSiguiente) {
		this.puertoSiguiente = puertoSiguiente;
	}
	public Buque getBuque() {
		return buque;
	}
	public void setBuque(Buque buque) {
		this.buque = buque;
	}
	
	
}
