package vos;

import java.util.ArrayList;

public class Bodega extends AreaAlmacenamiento {
	
	private float ancho;
	private float largo;
	private boolean plataformaExt;
	private float separacionColumna;
	private ArrayList<CuartoFrio> cuartosFrios;
	public Bodega(int id, boolean lleno, ArrayList<Mercancia> mercancias, float ancho, float largo,
			boolean plataformaExt, float separacionColumna, ArrayList<CuartoFrio> cuartosFrios) {
		super(id, lleno, mercancias);
		this.ancho = ancho;
		this.largo = largo;
		this.plataformaExt = plataformaExt;
		this.separacionColumna = separacionColumna;
		this.cuartosFrios = cuartosFrios;
	}
	public Bodega(){
		
	}
	public float getAncho() {
		return ancho;
	}
	public void setAncho(float ancho) {
		this.ancho = ancho;
	}
	public float getLargo() {
		return largo;
	}
	public void setLargo(float largo) {
		this.largo = largo;
	}
	public boolean isPlataformaExt() {
		return plataformaExt;
	}
	public void setPlataformaExt(boolean plataformaExt) {
		this.plataformaExt = plataformaExt;
	}
	public float getSeparacionColumna() {
		return separacionColumna;
	}
	public void setSeparacionColumna(float separacionColumna) {
		this.separacionColumna = separacionColumna;
	}
	public ArrayList<CuartoFrio> getCuartosFrios() {
		return cuartosFrios;
	}
	public void setCuartosFrios(ArrayList<CuartoFrio> cuartosFrios) {
		this.cuartosFrios = cuartosFrios;
	}
}
