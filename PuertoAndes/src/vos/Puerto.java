package vos;

import java.util.ArrayList;

public class Puerto {
	private String nombre;
	private String pais;
	private String ciudad;
	private ArrayList<Usuario> usuarios;
	private ArrayList<Equipo> equipos;
	private ArrayList<Camion> camiones;
	private ArrayList<Muelle> muelles;
	private ArrayList<AreaAlmacenamiento> areasAlmacenamiento;
	private ArrayList<MovimientoBuque> movimientos;
	private ArrayList<EntregaMercancia> entregas;
	private ArrayList<Factura> facturas;

	public Puerto(){
		
	}
	public Puerto(String nombre, String pais, String ciudad, ArrayList<Usuario> usuarios,
			ArrayList<Equipo> equipos, ArrayList<Camion> camiones, ArrayList<Muelle> muelles,
			ArrayList<AreaAlmacenamiento> areasAlmacenamiento, ArrayList<MovimientoBuque> movimientos,
			ArrayList<EntregaMercancia> entregas, ArrayList<Factura> facturas) {
		super();
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
		this.usuarios = usuarios;
		this.equipos = equipos;
		this.camiones = camiones;
		this.muelles = muelles;
		this.areasAlmacenamiento = areasAlmacenamiento;
		this.movimientos = movimientos;
		this.entregas = entregas;
		this.facturas = facturas;
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
	public ArrayList<AreaAlmacenamiento> getAreasAlmacenamiento() {
		return areasAlmacenamiento;
	}
	public void setAreasAlmacenamiento(ArrayList<AreaAlmacenamiento> areasAlmacenamiento) {
		this.areasAlmacenamiento = areasAlmacenamiento;
	}
	public ArrayList<EntregaMercancia> getEntregas() {
		return entregas;
	}
	public void setEntregas(ArrayList<EntregaMercancia> entregas) {
		this.entregas = entregas;
	}
	public ArrayList<Factura> getFacturas() {
		return facturas;
	}
	public void setFacturas(ArrayList<Factura> facturas) {
		this.facturas = facturas;
	}
	public ArrayList<MovimientoBuque> getMovimientos() {
		return movimientos;
	}
	public void setMovimientos(ArrayList<MovimientoBuque> movimientos) {
		this.movimientos = movimientos;
	}
}
