package vos;

import java.util.ArrayList;

public class Camion {
	private int id;
	private String nombre;
	private ArrayList<Mercancia> mercancias;
	
	public Camion(int id, String nombre, ArrayList<Mercancia> mercancias) {
		super();
		this.id = id;
		this.nombre = nombre;
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
	public ArrayList<Mercancia> getMercancias() {
		return mercancias;
	}
	public void setMercancias(ArrayList<Mercancia> mercancias) {
		this.mercancias = mercancias;
	}
}
