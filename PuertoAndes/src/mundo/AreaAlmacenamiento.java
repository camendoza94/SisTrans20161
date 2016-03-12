package mundo;

import java.util.ArrayList;

public class AreaAlmacenamiento {
	private Integer id;
	private String tipo;
	private Boolean lleno;
	private ArrayList<Mercancia> mercancias;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Boolean getLleno() {
		return lleno;
	}
	public void setLleno(Boolean lleno) {
		this.lleno = lleno;
	}
	public ArrayList<Mercancia> getMercancias() {
		return mercancias;
	}
	public void setMercancias(ArrayList<Mercancia> mercancias) {
		this.mercancias = mercancias;
	}
}
