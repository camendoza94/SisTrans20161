package vos;

import java.util.ArrayList;

public class AreaAlmacenamiento {
	private int id;
	private boolean lleno;
	private ArrayList<Mercancia> mercancias;
	
	public AreaAlmacenamiento(int id, boolean lleno, ArrayList<Mercancia> mercancias) {
		super();
		this.id = id;
		this.lleno = lleno;
		this.mercancias = mercancias;
	}
	public AreaAlmacenamiento() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean getLleno() {
		return lleno;
	}
	public void setLleno(boolean lleno) {
		this.lleno = lleno;
	}
	public ArrayList<Mercancia> getMercancias() {
		return mercancias;
	}
	public void setMercancias(ArrayList<Mercancia> mercancias) {
		this.mercancias = mercancias;
	}
}
