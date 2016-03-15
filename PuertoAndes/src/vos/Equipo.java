package vos;

public class Equipo {
	private int id;
	private String nombre;
	private float precio;
	private float capacidad;
	private boolean disponible;
	
	public Equipo(int id, String nombre, float precio, float capacidad, boolean disponible) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.capacidad = capacidad;
		this.disponible = disponible;
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
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public float getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(float capacidad) {
		this.capacidad = capacidad;
	}
	public boolean getDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}	
}
