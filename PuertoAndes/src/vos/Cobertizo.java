package vos;

import java.util.ArrayList;

public class Cobertizo extends AreaAlmacenamiento{
	private float dimension;
	private String tipoCarga;
	
	public Cobertizo(int id, boolean lleno, ArrayList<Mercancia> mercancias, float dimension, String tipoCarga) {
		super(id, lleno, mercancias);
		this.dimension = dimension;
		this.tipoCarga = tipoCarga;
	}
	public Cobertizo(){
		
	}
	public float getDimension() {
		return dimension;
	}
	public void setDimension(float dimension) {
		this.dimension = dimension;
	}
	public String getTipoCarga() {
		return tipoCarga;
	}
	public void setTipoCarga(String tipoCarga) {
		this.tipoCarga = tipoCarga;
	}
}
