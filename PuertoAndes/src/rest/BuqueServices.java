package rest;

import javax.servlet.ServletContext;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.AreaAlmacenamiento;
import vos.Buque;
import vos.EntregaMercancia;

@Path("buques")
public class BuqueServices {
	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	//RF6
	@POST
	@Path("/carga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCargaTipoABuque(EntregaMercancia entrega){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addCargaTipoABuque(entrega);;
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(entrega).build();
	}
	
	// RF7
	@POST
	@Path("/descarga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCargaAreaAlmacenamiento(EntregaMercancia entrega) {
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		try {
			tm.addCargaTipoAArea(entrega);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(entrega).build();
	}
	
	//RF10
	@POST
	@Path("/carga/{idBuque: \\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cargarBuque(@PathParam("idBuque") int idBuque){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		Buque buque = new Buque();
		try {
			buque = tm.cargarBuque(idBuque);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(buque).build();
	}
	
	//RF11
	@POST
	@Path("/descarga/{idBuque: \\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response descargarBuque(@PathParam("idBuque") int idBuque, String destino){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		AreaAlmacenamiento area = new AreaAlmacenamiento();
		try {
			area = tm.descargarBuque(idBuque, destino);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(area).build();
	}
	
	//RF12
	@POST
	@Path("/deshabilitar/{idBuque: \\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deshabilitarrBuque(@PathParam("idBuque") int idBuque, String tipo){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		Buque buque = new Buque();
		try {
			buque = tm.deshabilitarBuque(idBuque, tipo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(buque).build();
	}
}
