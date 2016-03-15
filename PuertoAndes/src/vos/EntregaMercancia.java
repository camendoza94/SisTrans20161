package vos;

import java.sql.Date;

public class EntregaMercancia {
	private Mercancia mercancia;
	private Date fecha;
	public Mercancia getMercancia() {
		return mercancia;
	}
	public void setMercancia(Mercancia mercancia) {
		this.mercancia = mercancia;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
