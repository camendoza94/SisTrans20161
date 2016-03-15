package vos;

import java.util.ArrayList;

public class Silo extends AreaAlmacenamiento{
	private String nombre;
	private float capacidad;
	
	public Silo(int id, boolean lleno, ArrayList<Mercancia> mercancias, String nombre, float capacidad) {
		super(id, lleno, mercancias);
		this.nombre = nombre;
		this.capacidad = capacidad;
	}
	public Silo(){
		
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(float capacidad) {
		this.capacidad = capacidad;
	}
}
