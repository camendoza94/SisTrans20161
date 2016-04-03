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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaci�n
 * @author Juan
 */
public class DAOOperadorPortuario {


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
	public DAOOperadorPortuario() {
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
	
	//RF6
	/**
	 * 
	 * @param buque
	 * @param idPuerto
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addCargaTipoABuque(Buque buque) throws SQLException, Exception{
		
	}
	
	//RF9
	/**
	 * 
	 * @param factura
	 * @param idPuerto
	 * @return
	 * @throws SQLException, Exception 
	 */
	public void addFactura(Factura factura) throws SQLException, Exception {
		String sql = "INSERT INTO FACTURAS VALUES (";
		sql += factura.getId() + ",";
		sql += "TO_DATE('" +factura.getFecha() + "','YYYY-MM-DD'),";
		sql += factura.getExportador().getId() + ",";
		sql += factura.getBuque().getId() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		addMercanciasFactura(factura);
	}
	
	//RF9-1
	/**
	 * 
	 * @param factura
	 * @throws SQLException
	 * @throws Exception
	 */
	private void addMercanciasFactura(Factura factura)throws SQLException, Exception {
		String sql = "SELECT ID_MERCANCIA FROM MERCANCIAS WHERE ID_BUQUE=";
		sql += factura.getBuque().getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next()){
			String sql2 = "INSERT INTO MERCANCIAS_ASOCIADAS_FACTURAS VALUES (";
			sql2 += factura.getId() + ",";
			sql2 += rs.getInt("ID_MERCANCIA") + ")";

			System.out.println("SQL stmt:" + sql2);

			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();	
		}
	}
	//RF8
	//Se asume que quien pide la carga es el propietario de esta
	/**
	 * 
	 * @param entregaMercancia
	 * @param idPuerto
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addEntregaMercanciaImportador(EntregaMercancia mercancia, Integer idAA)throws SQLException, Exception {
		buscarMercanciaAA(mercancia, idAA);
		String sql = "INSERT INTO ENTREGA_MERCANCIA VALUES (";
		sql += mercancia.getMercancia().getId() + ",";
		sql += "TO_DATE('" + mercancia.getFecha() + "','YYYY-MM-DD'),";
		sql += idAA + ",'";
		sql += "A_IMPORTADOR" + "')";
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void buscarMercanciaAA(EntregaMercancia mercancia, Integer idAA) throws SQLException, Exception{
		String sql = "SELECT * FROM MERCANCIAS WHERE ID_AREA_ALMACENAMIENTO=";
		sql += idAA;
		sql += " AND ID_MERCANCIA=" + mercancia.getMercancia().getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()){
			System.out.println("Esta mercancia s� est� en el �rea de almacenamiento dada");
		}
		else{
			throw new Exception("Esta mercancia no est� en el �rea de almacenamiento dada");
		}
	}
}