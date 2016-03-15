/**-------------------------------------------------------------------
 * $Id$
 * 
 * Universidad de los Andes (Bogot�- Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n
 *
 * Materia: Sistemas Transaccionales
 * Extractos tomados de VideoAndes por Juan Felipe Garc�a - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import vos.*;
import vos.Buque.tipoMercancia;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaci�n
 * @author Juan
 */
public class DAOConsultas {


	/**
	 * Arraylits de recursos que se usan para la ejecuci�n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi�n a la base de datos
	 */
	private Connection conn;

	/**
	 * M�todo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOConsultas() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * M�todo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * M�todo que inicializa la connection del DAO a la base de datos con la conexi�n que entra como par�metro.
	 * @param con  - connection a la base de datos
	 */
	public void  setConn(Connection con){
		this.conn = con;
	}
	//RFC1
	/**
	 * 
	 * @param fechaIni
	 * @param fechaFin
	 * @param nombreBuque
	 * @param tipoMercancia
	 * @param hora
	 * @param orderBy
	 * @param groupBy
	 * @return
	 */
	public ArrayList<MovimientoBuque> consultarArribosSalidas(Integer idPuerto, Date fechaIni, Date fechaFin, String nombreBuque,
			tipoMercancia tipoMercancia, Time hora, String orderBy, String groupBy) {
		String sql = "SELECT * FROM MOVIMIENTO_BUQUES WHERE ID_PUERTO=" + idPuerto;
		sql+= ""
		

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
	}
}