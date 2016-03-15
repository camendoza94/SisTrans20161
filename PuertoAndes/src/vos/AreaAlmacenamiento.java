package vos;

import java.util.List;

public class AreaAlmacenamiento {
	private int id;
	private boolean lleno;
	private List<Mercancia> mercancias;
	
	public AreaAlmacenamiento(int id, boolean lleno, List<Mercancia> mercancias) {
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
	public List<Mercancia> getMercancias() {
		return mercancias;
	}
	public void setMercancias(List<Mercancia> mercancias) {
		this.mercancias = mercancias;
	}
}
