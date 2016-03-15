package vos;

import java.sql.Date;

public class Factura {
	private int id;
	private Date fecha;
	private Buque buque;
	private Exportador exportador;
	public Factura(int id, Date fecha, Buque buque, Exportador exportador) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.buque = buque;
		this.exportador = exportador;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public Exportador getExportador() {
		return exportador;
	}
	public void setExportador(Exportador exportador) {
		this.exportador = exportador;
	}
}
