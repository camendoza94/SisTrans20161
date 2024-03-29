package vos;

import java.util.ArrayList;

import vos.AreaAlmacenamiento.estado;

public class Buque {
	public enum tipoBuque{
		RORO, PORTACONTENEDORES, MULTIPROPOSITO;
	}
	
	public enum tipoMercancia{
		GRANEL_SOLIDO, GRANEL_LIQUIDO, CONTENEDORES, GENERAL, RODADA;
	}
	private int id;
	private String nombre;
	private String nombreAgente;
	private float capacidad;
	private boolean lleno;
	private String registroCapitania;
	private String destino;
	private String origen;
	private tipoBuque tipo;
	private estado estado;
	private ArrayList<Mercancia> mercancias;

	public Buque(int id, String nombre, String nombreAgente, float capacidad, boolean lleno, String registroCapitania,
			String destino, String origen, tipoBuque tipo, vos.AreaAlmacenamiento.estado estado,
			ArrayList<Mercancia> mercancias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nombreAgente = nombreAgente;
		this.capacidad = capacidad;
		this.lleno = lleno;
		this.registroCapitania = registroCapitania;
		this.destino = destino;
		this.origen = origen;
		this.tipo = tipo;
		this.estado = estado;
		this.mercancias = mercancias;
	}
	public Buque(){
		
	}
	public Buque(int id){
		this.id = id;
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
	public estado getEstado() {
		return estado;
	}
	public void setEstado(estado estado) {
		this.estado = estado;
	}
}
