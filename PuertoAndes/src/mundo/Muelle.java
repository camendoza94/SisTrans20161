package mundo;

public class Muelle {
	private Integer id;
	private String nombre;
	private Buque buqueAtracado;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Buque getBuqueAtracado() {
		return buqueAtracado;
	}
	public void setBuqueAtracado(Buque buqueAtracado) {
		this.buqueAtracado = buqueAtracado;
	}
}
