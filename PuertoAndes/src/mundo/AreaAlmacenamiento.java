package mundo;

import java.util.ArrayList;

public class AreaAlmacenamiento {
	private int id;
	private String tipo;
	private boolean lleno;
	private ArrayList<Mercancia> mercancias;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
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
