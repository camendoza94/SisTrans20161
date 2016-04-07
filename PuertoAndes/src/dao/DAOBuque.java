package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Buque;
import vos.Mercancia;
import vos.Usuario;
import vos.AreaAlmacenamiento.estado;
import vos.Mercancia.claseMercancia;

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
	public void updateCapacidadBuque(int idBuque, float volumenMercancia) throws SQLException{
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
	
	
		public Object[] addCargaTipoABuque(int idMercancia, int idBuque) throws SQLException {
			Object[] respuesta = infoMercanciaBuque(idMercancia, idBuque);
			String sql = "INSERT INTO MERCANCIA_EN_BUQUE VALUES (";
			sql += idMercancia + ",";
			sql += idBuque + "')";

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
			String sql2 = "UPDATE BUQUES SET CAPACIDAD=CAPACIDAD-"+ ((Mercancia) respuesta[1]).getVolumen();
			sql2 += " WHERE ID_BUQUE = " + idBuque;

			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();
			return respuesta;
		}
	
	//RF6.1
		private Object[] infoMercanciaBuque(int idMercancia, int idBuque) throws SQLException{
			Object[] respuesta = new Object[2];
			String sql = "SELECT * FROM MERCANCIA WHERE ID_MERCANCIA = " + idMercancia;
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()){
				respuesta[0] = new Mercancia(idMercancia,rs.getFloat("PRECIO"), claseMercancia.valueOf(rs.getString("PROPOSITO")), rs.getFloat("VOLUMEN"), new Usuario(rs.getInt("PROPIETARIO")), rs.getString("ORIGEN"), rs.getString("DESTINO"), vos.Buque.tipoMercancia.valueOf(rs.getString("TIPO_CARGA")), rs.getFloat("PESO"));
			}
			String sql2 = "SELECT * FROM BUQUES WHERE ID_BUQUE = " + idBuque;
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next()){
				respuesta[1] = new Buque(idBuque, rs2.getString("NOMBRE"), rs2.getString("NOMBRE_AGENTE"), rs2.getFloat("CAPACIDAD"), rs2.getBoolean("LLENO"), rs2.getString("REGISTRO_CAPITANIA"), rs2.getString("DESTINO"), rs2.getString("ORIGEN"), vos.Buque.tipoBuque.valueOf(rs2.getString("TIPO_BUQUE")) , estado.valueOf(rs2.getString("ESTADO")), null);
			}
			return respuesta;
		}
		
	
	//RF7
		/**
		 * 
		 * @param area
		 * @param idPuerto
		 * @throws SQLException
		 * @throws Exception
		 */
		public Object[] addCargaAlmacenamiento(int idMercancia, int idArea) throws SQLException {
			Object[] respuesta = infoMercanciaBuque(idMercancia, idArea);
			String sql = "INSERT INTO MERCANCIA_EN_ALMACENAMIENTO VALUES (";
			sql += idMercancia + ",";
			sql += idArea + "')";

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			
			String sql2 = "UPDATE AREAS_ALMACENAMIENTO SET CAPACIDAD=CAPACIDAD-"+ ((Mercancia) respuesta[1]).getVolumen();
			sql2 += " WHERE ID_AREA = " + idArea;
			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();
			return respuesta;
		}
}
		
		
		
		