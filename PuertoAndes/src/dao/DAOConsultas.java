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
	public ArrayList<MovimientoBuque> consultarArribosSalidas(Date fechaIni, Date fechaFin, String nombreBuque,
			tipoMercancia tipoMercancia, Time hora, String orderBy, String groupBy) throws SQLException, Exception {
		ArrayList<MovimientoBuque> movimientos = new ArrayList<MovimientoBuque>();
		String sql = "SELECT * FROM MOVIMIENTO_BUQUES";
		String sql2 = "";
		boolean otro = false;
		if(fechaIni != null && fechaFin != null){
			otro = true;
			sql += " WHERE FECHA BETWEEN " + fechaIni  + " AND " + fechaFin;
		}
		if(nombreBuque != null){
			String temp =  "ID_BUQUE = (SELECT ID_BUQUE FROM BUQUES WHERE NOMBRE ='" + nombreBuque + "')";
			if(otro == true){
				sql += " AND " + temp;
			}
			else {
				otro = true;
				sql += " WHERE " + temp;
			}
		}
		if(tipoMercancia != null){
			String temp = "ID_BUQUE IN (SELECT ID_BUQUE FROM BUQUES WHERE TIPO_MERCANCIA ='" + tipoMercancia + "')";
			if(otro == true){
				sql += " AND " + temp;
			}
			else {
				otro = true;
				sql += " WHERE " + temp;
			}
		}
		if(hora != null){
			String temp = " AND TO_CHAR(FECHA, HH:MM) = " + hora;
			if(otro == true){
				sql += " AND " + temp;
			}
			else {
				otro = true;
				sql += " WHERE " + temp;
			}
		}
		if(groupBy != null){
			sql2 = "SELECT " + groupBy + ", COUNT(*) FROM MOVIMIENTO_BUQUES GROUP BY " + groupBy;
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
			MovimientoBuque movimiento = new MovimientoBuque(rs.getDate("FECHA"), rs.getString("PUERTO_ANTERIOR"), rs.getString("PUERTO_SIGUIENTE"), new Buque(rs.getInt("ID_BUQUE")), tipoMovimiento.valueOf(rs.getString("TIPO")));
			movimientos.add(movimiento);
		}
		return movimientos;
	}
	
	//RFC4
	/**
	 * 
	 * @param idPuerto
	 * @param fechaIni
	 * @param fechaFin
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<AreaAlmacenamiento> consultarAAMasUtilizada(Date fechaIni, Date fechaFin) throws SQLException, Exception{
		ArrayList<AreaAlmacenamiento> areasMasUtilizadas = new ArrayList<AreaAlmacenamiento>();
		String sql = "SELECT A.*, B.USOS FROM (SELECT ID_AREA_ALMACENAMIENTO, COUNT(*) AS USOS FROM ENTREGA_MERCANCIA";
		sql += " WHERE FECHA BETWEEN TO_DATE('" + fechaIni + "','YYYY-MM-DD') AND TO_DATE('" + fechaFin + "','YYYY-MM-DD') HAVING COUNT(*) = (SELECT MAX(COUNT(*)) FROM ENTREGA_MERCANCIA WHERE FECHA BETWEEN TO_DATE('" + fechaIni + "','YYYY-MM-DD') AND TO_DATE('" + fechaFin +"','YYYY-MM-DD') GROUP BY ID_AREA_ALMACENAMIENTO) GROUP BY ID_AREA_ALMACENAMIENTO) B JOIN AREAS_ALMACENAMIENTO A ON B.ID_AREA_ALMACENAMIENTO = A.ID_AREA";
		System.out.println("SQL stmt:" + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next()){
			String sql2 = "SELECT ID_MERCANCIA FROM MERCANCIAS WHERE ID_AREA_ALMACENAMIENTO =" + rs.getInt("ID_AREA");
			System.out.println("SQL stmt:" + sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			ArrayList<Mercancia> mercancias = new ArrayList<Mercancia>();
			while(rs2.next()){
				mercancias.add(new Mercancia(rs2.getInt("ID_MERCANCIA")));
			}
			// TODO
			AreaAlmacenamiento AA = new AreaAlmacenamiento(rs.getInt("ID_AREA"),rs.getBoolean("LLENO"), null, null, null, 0, mercancias);
			areasMasUtilizadas.add(AA);
		}
		return areasMasUtilizadas;
	}
}