package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.AreaAlmacenamiento.estado;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 */
public class DAOBuque {

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
	public DAOBuque() {
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
	
	//RF6
	/**
	 * 
	 * @param idBuque
	 * @param volumenMercancia
	 * @throws SQLException
	 */
	public void updateCapacidad(int idBuque, float volumenMercancia) throws SQLException{
		String sql = "UPDATE BUQUES SET CAPACIDAD=CAPACIDAD-"+ volumenMercancia;
		sql += " WHERE ID_BUQUE = " + idBuque;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ResultSet getBuque(int idBuque) throws SQLException{
		String sql = "SELECT * FROM BUQUES WHERE ID_BUQUE =" + idBuque;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()){
			return rs;
		}
		return null;
	}
	
	public void updateEstado(int idBuque, estado estado) throws SQLException{
		String sql = "UPDATE BUQUES SET ESTADO='" + estado.name() + "'";
		sql += " WHERE ID_BUQUE=" + idBuque;
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
		
		
		
		