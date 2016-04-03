package vos;

import java.sql.Date;
import java.util.ArrayList;

import vos.Buque.tipoMercancia;

public class AreaAlmacenamiento {
	
	public enum estado {
		DISPONIBLE, RESERVA, EN_PROCESO_DE_CARGA;
	}
	
	private int id;
	private boolean lleno;
	private tipoMercancia tipoMercancia;
	private estado estado;
	private Date fechaReserva;
	private float capacidad;
	private ArrayList<Mercancia> mercancias;
	
	public AreaAlmacenamiento(int id, boolean lleno, tipoMercancia tipoMercancia, estado estado, Date fechaReserva, float capacidad, ArrayList<Mercancia> mercancias) {
		super();
		this.id = id;
		this.lleno = lleno;
		this.tipoMercancia = tipoMercancia;
		this.estado = estado;
		this.fechaReserva = fechaReserva;
		this.capacidad = capacidad;
		this.mercancias = mercancias;
	}
	public AreaAlmacenamiento() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean getLleno() {
		return lleno;
	}
	public void setLleno(boolean lleno) {
		this.lleno = lleno;
	}
	public ArrayList<Mercancia> getMercancias() {
		return mercancias;
	}
	public void setMercancias(ArrayList<Mercancia> mercancias) {
		this.mercancias = mercancias;
	}
	public tipoMercancia getTipoMercancia() {
		return tipoMercancia;
	}
	public void setTipoMercancia(tipoMercancia tipoMercancia) {
		this.tipoMercancia = tipoMercancia;
	}
	public estado getEstado() {
		return estado;
	}
	public void setEstado(estado estado) {
		this.estado = estado;
	}
	public Date getFechaReserva() {
		return fechaReserva;
	}
	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	public float getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(float capacidad) {
		this.capacidad = capacidad;
	}
}
