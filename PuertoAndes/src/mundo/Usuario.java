package mundo;

public class Usuario {
	public enum tipoPersona{
		NATURAL, JURIDICA;
	}
	
	public enum tipoUsuario{
		// TODO
		OPERADOR_PORTUARIO; 
	}
	
	private int id; //String?
	private String nombre;
	private tipoPersona tipoPersona;
	private tipoUsuario tipoUsuario;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public tipoPersona getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(tipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public tipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(tipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	
}
