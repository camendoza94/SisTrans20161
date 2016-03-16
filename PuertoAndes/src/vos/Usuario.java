package vos;

public class Usuario {
	public enum tipoPersona{
		NATURAL, JURIDICA;
	}
	
	public enum tipoUsuario{
		OPERADOR_PORTUARIO, AGENTE_PORTUARIO;
	}
	
	private int id; //String?
	private String nombre;
	private tipoPersona tipoPersona;
	private tipoUsuario tipoUsuario;
	
	public Usuario(int id, String nombre, vos.Usuario.tipoPersona tipoPersona, vos.Usuario.tipoUsuario tipoUsuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipoPersona = tipoPersona;
		this.tipoUsuario = tipoUsuario;
	}
	public Usuario(){
		
	}
	public Usuario(int id){
		this.id =id;
	}
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
