package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.AreaAlmacenamiento.estado;

public class DAOAreaAlmacenamiento {
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
	public DAOAreaAlmacenamiento() {
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
	
	public void updateEstado(int idArea, estado estado) throws SQLException{
		String sql = "UPDATE AREAS_ALMACENAMIENTO SET ESTADO='" + estado.name() + "'";
		sql += " WHERE ID_AREA=" + idArea;
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
	}
	
	public void updateCapacidad(int idArea, float volumenMercancia) throws SQLException{
		String sql = "UPDATE AREAS_ALMACENAMIENTO SET CAPACIDAD=CAPACIDAD+"+ volumenMercancia;
		sql += " WHERE ID_AREA = " + idArea;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	//RF10
	public ResultSet getAreasDisponibles() throws SQLException{
		String sql = "SELECT * FROM AREAS_ALMACENAMIENTO WHERE ESTADO ='" +  estado.DISPONIBLE + "'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) {
			return rs;
		}
		return null;
	}

	// RF11

	public ResultSet getArea(int idArea) throws SQLException {
		String sql = "SELECT * FROM AREAS_ALMACENAMIENTO WHERE ID_AREA =" + idArea;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if (rs.next()) {
			return rs;
		}
		return null;
	}

	// RF11.1

	public void reservarAlmacenamiento(int idArea, Date fecha)
			throws SQLException, Exception {
		String sql = "UPDATE AREAS_ALMACENAMIENTO SET FECHA_RESERVA=TO_DATE('" + fecha + "','YYYY-MM-DD'),";
		sql += " ESTADO_RESERVA='" + estado.RESERVA + "'";
		sql += " WHERE ID_AREA = " + idArea;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
