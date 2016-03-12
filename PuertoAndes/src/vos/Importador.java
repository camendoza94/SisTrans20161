package vos;

public class Importador extends Usuario{
	public enum tipoImportador {
		HABITUAL, NO_HABITUAL;
	}
	private String registroAduana;
	private tipoImportador tipo;
	
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
