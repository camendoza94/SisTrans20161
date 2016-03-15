package vos;

import java.sql.Date;

public class SalidaBuque {
	private Date fecha;
	private Buque buque;
	
	public SalidaBuque(Date fecha, Buque buque) {
		super();
		this.fecha = fecha;
		this.buque = buque;
	}
	public SalidaBuque(){
		
	}
	public Buque getBuque() {
		return buque;
	}
	public void setBuque(Buque buque) {
		this.buque = buque;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
