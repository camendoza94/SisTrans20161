package vos;

import java.util.Date;

public class MovimientoBuque {
	public enum tipoMovimiento {
		SALIDA, ARRIBO
	}
	private Date fecha;
	private String puertoAnterior;
	private String puertoSiguiente;	
	private Buque buque;
	private tipoMovimiento tipo;
	public MovimientoBuque(Date fecha, String puertoAnterior, String puertoSiguiente, Buque buque,
			tipoMovimiento tipo) {
		super();
		this.fecha = fecha;
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
	public String getPuertoAnterior() {
		return puertoAnterior;
	}
	public void setPuertoAnterior(String puertoAnterior) {
		this.puertoAnterior = puertoAnterior;
	}
	public String getPuertoSiguiente() {
		return puertoSiguiente;
	}
	public void setPuertoSiguiente(String puertoSiguiente) {
		this.puertoSiguiente = puertoSiguiente;
	}
	public Buque getBuque() {
		return buque;
	}
	public void setBuque(Buque buque) {
		this.buque = buque;
	}
}
