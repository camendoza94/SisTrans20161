package vos;

import java.util.Date;
import java.util.ArrayList;

public class Cobertizo extends AreaAlmacenamiento{
	private float dimension;
	
	public Cobertizo(int id, boolean lleno, vos.Buque.tipoMercancia tipoMercancia, vos.AreaAlmacenamiento.estado estado,
			Date fechaReserva, float capacidad, ArrayList<Mercancia> mercancias, float dimension) {
		super(id, lleno, tipoMercancia, estado, fechaReserva, capacidad, mercancias);
		this.dimension = dimension;
	}
	public Cobertizo(){
		
	}
	public float getDimension() {
		return dimension;
	}
	public void setDimension(float dimension) {
		this.dimension = dimension;
	}
}
