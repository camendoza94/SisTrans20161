package rest;

import java.util.Date;
import java.sql.Time;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.PuertoAndesMaster;
import vos.AreaAlmacenamiento;
import vos.Buque.tipoMercancia;
import vos.MovimientoBuque;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/PuertoAndes/rest/agentePortuario/...
 */

@Path("consultas")
public class ConsultasServices {

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
	
	//RFC1
	@GET
	@Path("/arribosSalidas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarArribosSalidas(@QueryParam("fechaIni")Date fechaIni, @QueryParam("fechaFin")Date fechaFin,@QueryParam("nombreBuque") String nombreBuque, @QueryParam("tipoMercancia") tipoMercancia tipoMercancia,@QueryParam("hora") Time hora, @QueryParam("orderBy")String orderBy, @QueryParam("groupBy")String groupBy){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<MovimientoBuque> movimientos;
		try {
			movimientos = tm.consultarArribosSalidas(fechaIni,fechaFin,nombreBuque,tipoMercancia,hora,orderBy,groupBy);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(movimientos).build();
	}
	
	//RFC4
	@GET
	@Path("/areaMasUtilizada")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarAAMasUtilizada(@QueryParam("fechaIni")Date fechaIni, @QueryParam("fechaFin")Date fechaFin){
		PuertoAndesMaster tm = new PuertoAndesMaster(getPath());
		ArrayList<AreaAlmacenamiento> areas;
		try {
			areas = tm.consultarAAMasUtilizada(fechaIni,fechaFin);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(areas).build();
	}
}