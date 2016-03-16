package vos;

public class Exportador extends Usuario {
	
	private String RUT;

	public Exportador(int id, String nombre, vos.Usuario.tipoPersona tipoPersona, vos.Usuario.tipoUsuario tipoUsuario,
			String rUT) {
		super(id, nombre, tipoPersona, tipoUsuario);
		RUT = rUT;
	}
	public Exportador(){
		
	}
	public Exportador(int id){
		super(id);
	}
	public String getRUT() {
		return RUT;
	}

	public void setRUT(String rUT) {
		RUT = rUT;
	}
}
