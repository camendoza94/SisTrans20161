package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.EntregaMercancia;

public class DAOMercancia {

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
	public DAOMercancia() {
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
	 * @param idMercancia
	 * @param idBuque
	 * @throws SQLException
	 */
	public void insertMercanciaBuque(int idMercancia, int idBuque) throws SQLException {
		String sql = "INSERT INTO MERCANCIA_EN_BUQUE VALUES (";
		sql += idMercancia + ",";
		sql += idBuque + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public float getVolumenMercancia(int idMercancia) throws Exception, SQLException {
		String sql = "SELECT VOLUMEN FROM MERCANCIAS WHERE ID_MERCANCIA=" + idMercancia;
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs  = prepStmt.executeQuery();
		if(rs.next()) {
			return rs.getFloat("VOLUMEN");
		} 
		else {
			throw new Exception("La mercancia no existe");
		}
	}
	
	public void deleteMercanciaArea(int idMercancia, int idArea) throws SQLException{
		String sql = "DELETE FROM MERCANCIA_EN_ALMACENAMIENTO WHERE ID_MERCANCIA=" + idMercancia;
		sql += " AND ID_AREA_ALMACENAMIENTO = " + idArea;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public int getAreaMercancia(int idMercancia) throws Exception, SQLException{
		String sql = "SELECT ID_AREA_ALMACENAMIENTO FROM MERCANCIA_EN_ALMACENAMIENTO WHERE ID_MERCANCIA=" + idMercancia;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs= prepStmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("ID_AREA_ALMACENAMIENTO");
		} 
		else {
			throw new Exception("La mercancia no está en ninguna área");
		}
	}
	
	//RF6
	public void insertEntregaMercanciaBuque(EntregaMercancia entrega, int idArea) throws SQLException{
		String sql = "INSERT INTO ENTREGA_MERCANCIA VALUES (";
		sql += entrega.getMercancia().getId() + ",";
		sql += "TO_DATE('" + entrega.getFechaOrden() + "','YYYY-MM-DD'),";
		if(idArea == -1){
			sql += "NULL,";
		}
		else {
			sql += idArea + ",'";
		}
		sql += entrega.getTipo() + "',";
		sql += entrega.getBuque().getId() + ",";
		sql += "TO_DATE('" + entrega.getFechaRealizacion() + "','YYYY-MM-DD'))";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public int getBuqueMercancia(int idMercancia) throws SQLException, Exception {
		String sql = "SELECT ID_BUQUE FROM MERCANCIA_EN_BUQUE WHERE ID_MERCANCIA=" + idMercancia;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs= prepStmt.executeQuery();
		if(rs.next()) {
			return rs.getInt("ID_BUQUE");
		} 
		else {
			throw new Exception("La mercancia no está en ningun buque");
		}
	}

	public void deleteMercanciaBuque(int idMercancia, int idBuque2) throws SQLException {
		String sql = "DELETE FROM MERCANCIA_EN_BUQUE WHERE ID_MERCANCIA=" + idMercancia;
		sql += " AND ID_BUQUE = " + idBuque2;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();		
	}
	
	//RF7
	public void insertMercanciaAreaAlmacenamiento (int idMercancia, int idArea) throws SQLException
	{
		String sql = "INSERT INTO MERCANCIA_EN_ALMACENAMIENTO VALUES (";
		sql += idMercancia + ",";
		sql += idArea + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void insertEntregaMercanciaArea(EntregaMercancia entrega, int idBuque) throws SQLException{
		String sql = "INSERT INTO ENTREGA_MERCANCIA VALUES (";
		sql += entrega.getMercancia().getId() + ",";
		sql += "TO_DATE('" + entrega.getFechaOrden() + "','YYYY-MM-DD'),";
		if(idBuque == -1){
			sql += "NULL,";
		}
		else {
			sql += idBuque + ",'";
		}
		sql += entrega.getTipo() + "',";
		sql += entrega.getArea().getId() + ",";
		sql += "TO_DATE('" + entrega.getFechaRealizacion() + "','YYYY-MM-DD'))";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	//RF10
	public ResultSet getMercanciasDestino(String destinoBuque) throws SQLException{
		String sql = "SELECT * FROM (MERCANCIA_EN_ALMACENAMENTO A JOIN MERCANCIAS B ON A.ID_MERCANCIA = B.ID_MERCANCIA) C JOIN AREAS_ALMACENAMIENTO D ON C.ID_AREA_ALMACENAMIENTO = D.ID_AREA WHERE DESTINO='" + destinoBuque + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()){
			return rs;
		} else {
			return null;
		}
	}
}
