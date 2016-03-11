package mundo;

import java.sql.Date;

public class Buque {
	private Integer id;
	private String nombre;
	private String nombreAgente;
	private Integer capacidad;
	private Boolean lleno;
	private Mercancia tipoCarga;
	private Date fechaIngreso;
	private Date fechaSalida;
	private String registroCapitania;
	private String destino;
	private String origen;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Integer getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	public Boolean getLleno() {
		return lleno;
	}
	public void setLleno(Boolean lleno) {
		this.lleno = lleno;
	}
	public Mercancia getCarga() {
		return tipoCarga;
	}
	public void setCarga(Mercancia carga) {
		this.tipoCarga = carga;
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
}
