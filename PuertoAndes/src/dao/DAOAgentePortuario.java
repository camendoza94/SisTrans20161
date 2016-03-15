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

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOAgentePortuario {


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
	public DAOAgentePortuario() {
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
	
	//RF4
	/**
	 * 
	 * @param arriboBuque
	 * @throws SQLException
	 * @throws Exception
	 */
	public void addArriboBuque(ArriboBuque arriboBuque) throws SQLException, Exception{
		String sql = "INSERT INTO ARRIBO_BUQUES VALUES (";
		sql += arriboBuque.getFecha() + ",";
		sql += arriboBuque.getPuertoAnterior() + ",";
		sql += arriboBuque.getPuertoSiguiente() + ",";
		sql += arriboBuque.getBuque() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	//RF5-1
	public boolean buscarBuquePuerto(Buque buque, Puerto puerto) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM MUELLES WHERE ID_PUERTO =" + puerto.getId();
		sql += " AND ID_BUQUE =" + buque.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next()){         
			System.out.println("Este muelle sí está atracado");
			encontrado = true;
		}
		else{
			System.out.println("Este muelle no está atracado");
		}
		return encontrado;
	}
	
	//RF5
	/**
	 * 
	 * @param salidaBuque
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean addSalidaBuque(SalidaBuque salidaBuque, Puerto puerto) throws SQLException, Exception{
		if(buscarBuquePuerto(salidaBuque.getBuque(), puerto)){
			String sql = "INSERT INTO SALIDA_BUQUES VALUES (";
			sql += salidaBuque.getFecha() + ",";
			sql += salidaBuque.getBuque() + ",";
			sql += puerto.getId() + ")";
	
			System.out.println("SQL stmt:" + sql);
	
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
			return true;
		}
		else{//Throw exception?
			System.out.println("El buque no está atracado a ningun muelle del puerto");
			return false;
		}
	}
}