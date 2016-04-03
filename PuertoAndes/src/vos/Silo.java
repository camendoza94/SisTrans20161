package vos;

import java.sql.Date;
import java.util.ArrayList;

public class Silo extends AreaAlmacenamiento{
	private String nombre;
	
	public Silo(int id, boolean lleno, vos.Buque.tipoMercancia tipoMercancia, vos.AreaAlmacenamiento.estado estado,
			Date fechaReserva, float capacidad, ArrayList<Mercancia> mercancias, String nombre) {
		super(id, lleno, tipoMercancia, estado, fechaReserva, capacidad, mercancias);
		this.nombre = nombre;
	}
	public Silo(){
		
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
