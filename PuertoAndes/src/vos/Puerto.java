package vos;

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
	private ArrayList<SalidaBuque> salidas;
	private ArrayList<ArriboBuque> arribos;
	private ArrayList<EntregaMercancia> entregas;
	private ArrayList<LlegadaMercancia> llegadas;
	private ArrayList<Factura> facturas;
	
	public Puerto(int id, String nombre, String pais, String ciudad, ArrayList<Usuario> usuarios,
			ArrayList<Equipo> equipos, ArrayList<Camion> camiones, ArrayList<Muelle> muelles, ArrayList<Buque> buques,
			ArrayList<AreaAlmacenamiento> areasAlmacenamiento, ArrayList<SalidaBuque> salidas,
			ArrayList<ArriboBuque> arribos, ArrayList<EntregaMercancia> entregas, ArrayList<LlegadaMercancia> llegadas,
			ArrayList<Factura> facturas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.usuarios = usuarios;
		this.equipos = equipos;
		this.camiones = camiones;
		this.muelles = muelles;
		this.buques = buques;
		this.areasAlmacenamiento = areasAlmacenamiento;
		this.salidas = salidas;
		this.arribos = arribos;
		this.entregas = entregas;
		this.llegadas = llegadas;
		this.setFacturas(facturas);
	}
	public Puerto(){
		
	}
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
	public ArrayList<SalidaBuque> getSalidas() {
		return salidas;
	}
	public void setSalidas(ArrayList<SalidaBuque> salidas) {
		this.salidas = salidas;
	}
	public ArrayList<ArriboBuque> getArribos() {
		return arribos;
	}
	public void setArribos(ArrayList<ArriboBuque> arribos) {
		this.arribos = arribos;
	}
	public ArrayList<EntregaMercancia> getEntregas() {
		return entregas;
	}
	public void setEntregas(ArrayList<EntregaMercancia> entregas) {
		this.entregas = entregas;
	}
	public ArrayList<LlegadaMercancia> getLlegadas() {
		return llegadas;
	}
	public void setLlegadas(ArrayList<LlegadaMercancia> llegadas) {
		this.llegadas = llegadas;
	}
	public ArrayList<Factura> getFacturas() {
		return facturas;
	}
	public void setFacturas(ArrayList<Factura> facturas) {
		this.facturas = facturas;
	}
}
