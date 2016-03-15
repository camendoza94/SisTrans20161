package vos;

import java.sql.Date;

public class SalidaBuque {
	private Date fecha;
	private Buque buque;
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Buque getBuque() {
		return buque;
	}
	public void setBuque(Buque buque) {
		this.buque = buque;
	}
}
