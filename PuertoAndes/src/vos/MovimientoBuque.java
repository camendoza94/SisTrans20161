package vos;

import java.sql.Date;
import java.sql.Time;

public class MovimientoBuque {
	public enum tipoMovimiento {
		SALIDA, ARRIBO
	}
	private Date fecha;
	private Time hora;
	private Puerto puertoAnterior;
	private Puerto puertoSiguiente;	
	private Buque buque;
	private tipoMovimiento tipo;
	public MovimientoBuque(Date fecha, Time hora, Puerto puertoAnterior, Puerto puertoSiguiente, Buque buque,
			tipoMovimiento tipo) {
		super();
		this.fecha = fecha;
		this.hora = hora;
		this.puertoAnterior = puertoAnterior;
		this.puertoSiguiente = puertoSiguiente;
		this.buque = buque;
		this.tipo = tipo;
	}
	public tipoMovimiento getTipo() {
		return tipo;
	}
	public void setTipo(tipoMovimiento tipo) {
		this.tipo = tipo;
	}
	public MovimientoBuque(){
		
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
	public Time getHora() {
		return hora;
	}
	public void setHora(Time hora) {
		this.hora = hora;
	}	
}
