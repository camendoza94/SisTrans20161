package vos;

import java.sql.Date;

public class EntregaMercancia {
	public enum tipoEntrega{
		A_IMPORTADOR, DESDE_AREA_ALMACENAMIENTO, DESDE_BUQUE;
	}
	private Mercancia mercancia;
	private Date fechaOrden;
	private Date fechaRealizacion;
	private tipoEntrega tipo;
	private AreaAlmacenamiento area;
	private AreaAlmacenamiento area2;
	private Buque buque;
	public EntregaMercancia(Mercancia mercancia, Date fechaOrden, Date fechaRealizacion, tipoEntrega tipo,
			AreaAlmacenamiento area, AreaAlmacenamiento area2, Buque buque) {
		super();
		this.mercancia = mercancia;
		this.fechaOrden = fechaOrden;
		this.fechaRealizacion = fechaRealizacion;
		this.tipo = tipo;
		this.area = area;
		this.area2 = area2;
		this.buque = buque;
	}
	public EntregaMercancia(){
	}
	public Mercancia getMercancia() {
		return mercancia;
	}
	public void setMercancia(Mercancia mercancia) {
		this.mercancia = mercancia;
	}
	public tipoEntrega getTipo() {
		return tipo;
	}
	public void setTipo(tipoEntrega tipo) {
		this.tipo = tipo;
	}
	public AreaAlmacenamiento getArea() {
		return area;
	}
	public void setArea(AreaAlmacenamiento area) {
		this.area = area;
	}
	public AreaAlmacenamiento getArea2() {
		return area2;
	}
	public void setArea2(AreaAlmacenamiento area2) {
		this.area2 = area2;
	}
	public Buque getBuque() {
		return buque;
	}
	public void setBuque(Buque buque) {
		this.buque = buque;
	}
	public Date getFechaOrden() {
		return fechaOrden;
	}
	public void setFechaOrden(Date fechaOrden) {
		this.fechaOrden = fechaOrden;
	}
	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}
	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}	
}
