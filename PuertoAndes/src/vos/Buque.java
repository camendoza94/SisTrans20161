package vos;

import java.sql.Date;
import java.util.ArrayList;

public class Buque {
	public enum tipoBuque{
		RORO, PORTACONTENEDORES, MULTIPROPOSITO;
	}
	
	public enum tipoMercancia{
		GRANEL_SOLIDO, CONTENEDORES, GENERAL;
	}
	private int id;
	private String nombre;
	private String nombreAgente;
	private float capacidad;
	private boolean lleno;
	private tipoMercancia tipoCarga; //Revisar TODO cambiar por enumeración
	private Date fechaIngreso;
	private Date fechaSalida;
	private String registroCapitania;
	private String destino;
	private String origen;
	private tipoBuque tipo;
	private ArrayList<Mercancia> mercancias;
	public Buque(int id, String nombre, String nombreAgente, float capacidad, boolean lleno, tipoMercancia tipoCarga,
			Date fechaIngreso, Date fechaSalida, String registroCapitania, String destino, String origen,
			tipoBuque tipo, ArrayList<Mercancia> mercancias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreAgente = nombreAgente;
		this.capacidad = capacidad;
		this.lleno = lleno;
		this.tipoCarga = tipoCarga;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.registroCapitania = registroCapitania;
		this.destino = destino;
		this.origen = origen;
		this.tipo = tipo;
		this.mercancias = mercancias;
	}
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
	public tipoMercancia getTipoCarga() {
		return tipoCarga;
	}
	public void setTipoCarga(tipoMercancia tipoCarga) {
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
