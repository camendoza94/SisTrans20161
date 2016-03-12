package mundo;

import java.util.ArrayList;

public class Puerto {
	private int id;
	private String nombre;
	private String pais;
	private String ciudad;
	private ArrayList<Usuario> usuarios;
	private ArrayList<Equipo> equipos;
	private ArrayList<Camion> camiones;
	private ArrayList<Muelle> muelles;
	private ArrayList<Buque> buques;
	private ArrayList<AreaAlmacenamiento> areasAlmacenamiento;
	
	public int getId() {
		return id;
	}
	public void setId(int idPuerto) {
		this.id = idPuerto;
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
	public ArrayList<Buque> getBuques() {
		return buques;
	}
	public void setBuques(ArrayList<Buque> buques) {
		this.buques = buques;
	}
	public ArrayList<AreaAlmacenamiento> getAreasAlmacenamiento() {
		return areasAlmacenamiento;
	}
	public void setAreasAlmacenamiento(ArrayList<AreaAlmacenamiento> areasAlmacenamiento) {
		this.areasAlmacenamiento = areasAlmacenamiento;
	}
}
