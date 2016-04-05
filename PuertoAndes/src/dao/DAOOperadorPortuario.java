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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.*;
import vos.AreaAlmacenamiento.estado;
import vos.Mercancia.claseMercancia;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOOperadorPortuario {

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
	public DAOOperadorPortuario() {
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
	 * @param buque
	 * @param idPuerto
	 * @throws SQLException
	 * @throws Exception
	 */
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
	public void addEntregaMercanciaImportador(EntregaMercancia mercancia, int idAA)throws SQLException, Exception {
		buscarMercanciaAA(mercancia, idAA);
		String sql = "INSERT INTO ENTREGA_MERCANCIA VALUES (";
		sql += mercancia.getMercancia().getId() + ",";
		sql += "TO_DATE('" + mercancia.getFechaOrden() + "','YYYY-MM-DD'),";
		sql += idAA + ",'";
		sql += "A_IMPORTADOR" + "')";
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	//RF8.1
	public void buscarMercanciaAA(EntregaMercancia mercancia, int idAA) throws SQLException, Exception{
		String sql = "SELECT * FROM MERCANCIAS WHERE ID_AREA_ALMACENAMIENTO=";
		sql += idAA;
		sql += " AND ID_MERCANCIA=" + mercancia.getMercancia().getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()){
			System.out.println("Esta mercancia sí está en el área de almacenamiento dada");
		}
		else{
			throw new Exception("Esta mercancia no está en el área de almacenamiento dada");
		}
	}
	
	//RF10
	public void cargarBuque(Integer idBuque) throws SQLException, Exception{
		String destinoBuque = "";
		String sql = "SELECT * FROM BUQUES WHERE ID_BUQUE =" + idBuque;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()){
			if(rs.getString("ESTADO").compareTo("DISPONIBLE") != 0){
				throw new Exception("El buque no se encuentra disponible");
			}
			destinoBuque = rs.getString("DESTINO");
		}
		String sql2 = "SELECT * FROM (MERCANCIA_EN_ALMACENAMENTO A JOIN MERCANCIAS B ON A.ID_MERCANCIA = B.ID_MERCANCIA) C JOIN AREAS_ALMACENAMIENTO D ON C.ID_AREA_ALMACENAMIENTO = D.ID_AREA WHERE DESTINO='" + destinoBuque + "'";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();
		if(rs2.next()){
			int cantidad = 0;
			rs2.beforeFirst();
			while(rs2.next()){
				verificarTipoMercanciaYBuque(rs2.getInt("ID_MERCANCIA"), idBuque);
				cantidad += rs2.getFloat("VOLUMEN");
			}
			if(cantidad > rs.getFloat("CAPACIDAD")){
				throw new Exception("El buque no tiene la capacidad suficiente");
			}
			rs2.beforeFirst();
			while(rs2.next()){
				String sql4 = "UPDATE AREAS_ALMACENAMIENTO SET ESTADO='EN_PROCESO_DE_CARGA' WHERE ID_AREA=" + rs2.getInt("ID_AREA");
				PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
				recursos.add(prepStmt4);
				prepStmt4.executeQuery();
			}
			String sql3 = "UPDATE BUQUES SET ESTADO='EN_PROCESO_DE_CARGA' WHERE ID_BUQUE=" + idBuque;
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			prepStmt3.executeQuery();
			rs2.beforeFirst();
			while(rs2.next()){
				addCargaTipoABuque(rs2.getInt("ID_MERCANCIA"), idBuque);
			}
			rs2.beforeFirst();
			while(rs2.next()){
				String sql4 = "UPDATE AREAS_ALMACENAMIENTO SET ESTADO='DISPONIBLE' WHERE ID_AREA=" + rs2.getInt("ID_AREA");
				PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
				recursos.add(prepStmt4);
				prepStmt4.executeQuery();
			}
			String sql5 = "UPDATE BUQUES SET ESTADO='DISPONIBLE' WHERE ID_BUQUE=" + idBuque;
			PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
			recursos.add(prepStmt5);
			prepStmt5.executeQuery();
		}
		else{
			throw new Exception("No hay mercancias con el mismo destino del buque");
		}
	}
	
	//RF10.1
	public void verificarTipoMercanciaYBuque(int idMercancia, int idBuque) throws SQLException, Exception {
		String sql = "SELECT * FROM (MERCANCIA A JOIN MERCANCIA_EN_ALMACENAMIENTO B ON A.ID_MERCANCIA = B.ID_MERCANCIA) C JOIN AREAS_ALMACENAMIENTO D ON C.ID_AREA_ALMACENAMIENTO = D.ID_AREA WHERE ID_MERCANCIA = " + idMercancia;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()){
			String sql2 = "SELECT TIPO_BUQUE FROM BUQUES WHERE ID_BUQUE = " + idBuque;
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next()){
				String tipoBuque = rs2.getString("TIPO_BUQUE");
				String tipoMercancia = rs.getString("TIPO_CARGA");
				if(tipoBuque.compareTo(vos.Buque.tipoBuque.RORO.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.RODADA.name()) != 0){
					throw new Exception("Este buque no puede llevar una mercancia de este tipo");
				}
				else if(tipoBuque.compareTo(vos.Buque.tipoBuque.PORTACONTENEDORES.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.CONTENEDORES.name()) != 0){
					throw new Exception("Este buque no puede llevar una mercancia de este tipo");
				}
				else {
					if(rs.getString("ESTADO").compareTo("DISPONIBLE") != 0){
						throw new Exception("El area de almacenamiento no está disponible en el momento para movimientos de carga");
					}
				}
			} 
			else {
				throw new Exception("Este buque no existe");
			}
		}
		else{
			throw new Exception("Esta mercancia no existe");
		}
	}
}