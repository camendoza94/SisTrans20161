package mundo;

import java.util.ArrayList;

public class Puerto {
	private String idPuerto;
	private String nombre;
	private String pais;
	private String ciudad;
	private ArrayList<Usuario> usuarios;
	private ArrayList<Equipo> equipos;
	private ArrayList<Camion> camiones;
	private ArrayList<Muelle> muelles;
	
	public String getIdPuerto() {
		return idPuerto;
	}
	public void setIdPuerto(String idPuerto) {
		this.idPuerto = idPuerto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public ArrayList<Equipo> getEquipos() {
		return equipos;
	}
	public void setEquipos(ArrayList<Equipo> equipos) {
		this.equipos = equipos;
	}
	public ArrayList<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public ArrayList<Camion> getCamiones() {
		return camiones;
	}
	public void setCamiones(ArrayList<Camion> camiones) {
		this.camiones = camiones;
	}
	public ArrayList<Muelle> getMuelles() {
		return muelles;
	}
	public void setMuelles(ArrayList<Muelle> muelles) {
		this.muelles = muelles;
	}	
}
