package vos;

public class Importador extends Usuario{
	public enum tipoImportador {
		HABITUAL, NO_HABITUAL;
	}
	private String registroAduana;
	private tipoImportador tipo;
	
	public Importador(int id, String nombre, vos.Usuario.tipoPersona tipoPersona, vos.Usuario.tipoUsuario tipoUsuario,
			String registroAduana, tipoImportador tipo) {
		super(id, nombre, tipoPersona, tipoUsuario);
		this.registroAduana = registroAduana;
		this.tipo = tipo;
	}
	public Importador(){
		
	}
	public String getRegistroAduana() {
		return registroAduana;
	}
	public void setRegistroAduana(String registroAduana) {
		this.registroAduana = registroAduana;
	}
	public tipoImportador getTipo() {
		return tipo;
	}
	public void setTipo(tipoImportador tipo) {
		this.tipo = tipo;
	}
	
	
}
