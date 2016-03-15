package vos;

public class CuartoFrio {
	private float ancho;
	private float largo;
	private float altura;
	private float porcentajeBodega;
	public CuartoFrio(float ancho, float largo, float altura, float porcentajeBodega) {
		super();
		this.ancho = ancho;
		this.largo = largo;
		this.altura = altura;
		this.porcentajeBodega = porcentajeBodega;
	}
	public float getAncho() {
		return ancho;
	}
	public void setAncho(float ancho) {
		this.ancho = ancho;
	}
	public float getLargo() {
		return largo;
	}
	public void setLargo(float largo) {
		this.largo = largo;
	}
	public float getAltura() {
		return altura;
	}
	public void setAltura(float altura) {
		this.altura = altura;
	}
	public float getPorcentajeBodega() {
		return porcentajeBodega;
	}
	public void setPorcentajeBodega(float porcentajeBodega) {
		this.porcentajeBodega = porcentajeBodega;
	}
}
