package vos;

import java.sql.Date;
import java.util.ArrayList;

public class Buque {
	public enum tipoBuque{
		RORO, PORTACONTENEDORES, MULTIPROPOSITO;
	}
	private int id;
	private String nombre;
	private String nombreAgente;
	private float capacidad;
	private boolean lleno;
	private Mercancia tipoCarga; //Revisar
	private Date fechaIngreso;
	private Date fechaSalida;
	private String registroCapitania;
	private String destino;
	private String origen;
	private tipoBuque tipo;
	private ArrayList<Mercancia> mercancias;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreAgente() {
		return nombreAgente;
	}
	public void setNombreAgente(String nombreAgente) {
		this.nombreAgente = nombreAgente;
	}
	public float getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(float capacidad) {
		this.capacidad = capacidad;
	}
	public boolean getLleno() {
		return lleno;
	}
	public void setLleno(boolean lleno) {
		this.lleno = lleno;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public String getRegistroCapitania() {
		return registroCapitania;
	}
	public void setRegistroCapitania(String registroCapitania) {
		this.registroCapitania = registroCapitania;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public Mercancia getTipoCarga() {
		return tipoCarga;
	}
	public void setTipoCarga(Mercancia tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
	public tipoBuque getTipo() {
		return tipo;
	}
	public void setTipo(tipoBuque tipo) {
		this.tipo = tipo;
	}
	public ArrayList<Mercancia> getMercancias() {
		return mercancias;
	}
	public void setMercancias(ArrayList<Mercancia> mercancias) {
		this.mercancias = mercancias;
	}
}
