/**-------------------------------------------------------------------
 * $Id$
 * 
 * Universidad de los Andes (Bogotá- Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Extractos tomados de VideoAndes por Juan Felipe García - jf.garcia268@uniandes.edu.co
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
import vos.MovimientoBuque.tipoMovimiento;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOConsultas {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOConsultas() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
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
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
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
	 * @throws SQLException, Exception 
	 */
	public ArrayList<MovimientoBuque> consultarArribosSalidas(Integer idPuerto, Date fechaIni, Date fechaFin, String nombreBuque,
			tipoMercancia tipoMercancia, Time hora, String orderBy, String groupBy) throws SQLException, Exception {
		ArrayList<MovimientoBuque> movimientos = new ArrayList<MovimientoBuque>();
		String sql = "SELECT * FROM MOVIMIENTO_BUQUES WHERE ID_PUERTO=" + idPuerto;
		String sql2 = "";
		if(fechaIni != null && fechaFin != null){
			sql += " AND FECHA BETWEEN " + fechaIni  + " AND " + fechaFin;
		}
		if(nombreBuque != null){
			sql += " AND ID_BUQUE = (SELECT ID FROM BUQUES WHERE NOMBRE ='" + nombreBuque + "'";
		}
		if(tipoMercancia != null){
			sql += " AND ID_BUQUE IN (SELECT ID FROM BUQUES WHERE TIPO_MERCANCIA ='" + tipoMercancia + "'";
		}
		if(hora != null){
			sql += " AND TO_CHAR(FECHA, HH:MM) = " + hora;
		}
		if(groupBy != null){
			sql2 = "SELECT " + groupBy + ", COUNT(*) FROM MOVIMIENTO_BUQUES WHERE ID_PUERTO=" + idPuerto + " GROUP BY " + groupBy;
		}
		if(orderBy != null){
			sql += " ORDER BY " + orderBy;
			sql2 += " ORDER BY " + orderBy;
		}
		ResultSet rs;
		if(groupBy == null){
			System.out.println("SQL stmt:" + sql);
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			 rs = prepStmt.executeQuery();
		}
		else {
			System.out.println("SQL stmt:" + sql2);
			PreparedStatement prepStmt = conn.prepareStatement(sql2);
			recursos.add(prepStmt);
			rs = prepStmt.executeQuery();
		}
		while(rs.next()){
			MovimientoBuque movimiento = new MovimientoBuque(rs.getDate("FECHA"), new Puerto(rs.getInt("ID_PUERTO_ANTERIOR")), new Puerto (rs.getInt("ID_PUERTO_SIGUIENTE")), new Buque(rs.getInt("ID_BUQUE")), (tipoMovimiento)rs.getObject("TIPO"));
			movimientos.add(movimiento);
		}
		return movimientos;
	}
}