package vos;

import java.sql.Date;

public class EntregaMercancia {
	public enum tipoEntrega{
		A_IMPORTADOR, A_AREA_ALMACENAMIENTO;
	}
	private Mercancia mercancia;
	private Date fecha;
	private tipoEntrega tipo;
	private AreaAlmacenamiento area;
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
}
