package vos;

import java.util.Date;
import java.util.ArrayList;

public class Patio extends AreaAlmacenamiento{
	private float dimension;
	
	public Patio(int id, boolean lleno, vos.Buque.tipoMercancia tipoMercancia, vos.AreaAlmacenamiento.estado estado,
			Date fechaReserva, float capacidad, ArrayList<Mercancia> mercancias, float dimension) {
		super(id, lleno, tipoMercancia, estado, fechaReserva, capacidad, mercancias);
		this.dimension = dimension;
	}
	public Patio(){
		
	}
	public float getDimension() {
		return dimension;
	}
	public void setDimension(float dimension) {
		this.dimension = dimension;
	}
}

