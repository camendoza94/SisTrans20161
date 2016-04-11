/**-------------------------------------------------------------------
 * $Id$
 * 
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Extractos tomados de VideoAndes por Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Properties;

import dao.*;
import vos.*;
import vos.AreaAlmacenamiento.estado;
import vos.Buque.tipoBuque;
import vos.Buque.tipoMercancia;
import vos.EntregaMercancia.tipoEntrega;

/**
 * Fachada en patron singleton de la aplicación
 * @author Juan
 */
public class PuertoAndesMaster {


	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene los datos de la conexión
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * Conexión a la base de datos
	 */
	private Connection conn;


	/**
	 * Método constructor de la clase PuertoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto PuertoAndesMaster, se inicializa el path absoluto de el archivo de conexión y se
	 * inicializa los atributos que se usan par la conexión a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public PuertoAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que  retorna la conexión a la base de datos
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexión a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////
	
	//RF5
	public void addSalidaBuque(MovimientoBuque salidaBuque) throws Exception{
		DAOAgentePortuario daoAgentePortuario = new DAOAgentePortuario();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAgentePortuario.setConn(conn);
			daoAgentePortuario.addSalidaBuque(salidaBuque);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAgentePortuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
	}
	
	//RF6
	public void addCargaTipoABuque(EntregaMercancia entrega) throws Exception {
		DAOBuque daoBuque = new DAOBuque();
		DAOMercancia daoMercancia = new DAOMercancia();
		DAOAreaAlmacenamiento daoAreaAlmacenamiento = new DAOAreaAlmacenamiento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoMercancia.setConn(conn);
			daoAreaAlmacenamiento.setConn(conn);
			conn.setAutoCommit(false);
			int idMercancia = entrega.getMercancia().getId();
			int idBuque = entrega.getBuque().getId();;
			float volumenMercancia = daoMercancia.getVolumenMercancia(idMercancia);
			int idArea = daoMercancia.getAreaMercancia(idMercancia);
			daoMercancia.deleteMercanciaArea(idMercancia, idArea);
			daoAreaAlmacenamiento.updateCapacidad(idArea, volumenMercancia);
			daoMercancia.insertMercanciaBuque(idMercancia, idBuque );
			daoBuque.updateCapacidad(idBuque, -volumenMercancia);
			daoMercancia.insertEntregaMercanciaBuque(entrega, idArea);
			
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoBuque.cerrarRecursos();
				daoMercancia.cerrarRecursos();
				daoAreaAlmacenamiento.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
	}
	
	//RF7
	public void addCargaTipoAArea(EntregaMercancia entrega) throws Exception {
		DAOBuque daoBuque = new DAOBuque();
		DAOMercancia daoMercancia = new DAOMercancia();
		DAOAreaAlmacenamiento daoAreaAlmacenamiento = new DAOAreaAlmacenamiento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoMercancia.setConn(conn);
			daoAreaAlmacenamiento.setConn(conn);
			conn.setAutoCommit(false);
			int idMercancia = entrega.getMercancia().getId();
			int idArea = entrega.getArea().getId();
			float volumenMercancia = daoMercancia.getVolumenMercancia(idMercancia);
			int idBuque = daoMercancia.getBuqueMercancia(idMercancia);
			daoMercancia.deleteMercanciaBuque(idMercancia, idBuque);
			daoBuque.updateCapacidad(idBuque, volumenMercancia);
			daoMercancia.insertMercanciaAreaAlmacenamiento(idMercancia, idArea);
			daoAreaAlmacenamiento.updateCapacidad(idArea, -volumenMercancia);
			daoMercancia.insertEntregaMercanciaArea(entrega, idArea);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoBuque.cerrarRecursos();
				daoMercancia.cerrarRecursos();
				daoAreaAlmacenamiento.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
	}	
	
	// RF8
	public void addEntregaMercanciaImportador(EntregaMercancia mercancia, int idAA) throws Exception {
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try {
			////// Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			daoOperadorPortuario.addEntregaMercanciaImportador(mercancia, idAA);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RF9
	public void addFactura(Factura factura) throws Exception {
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
		try {
			////// Transacción
			this.conn = darConexion();
			daoOperadorPortuario.setConn(conn);
			daoOperadorPortuario.addFactura(factura);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperadorPortuario.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RF10
	public Buque cargarBuque(int idBuque) throws SQLException, Exception {
		DAOBuque daoBuque = new DAOBuque();
		DAOMercancia daoMercancia = new DAOMercancia();
		DAOAreaAlmacenamiento daoAlmacenamiento = new DAOAreaAlmacenamiento();
		try {
			////// Transacción
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoMercancia.setConn(conn);
			daoAlmacenamiento.setConn(conn);
			conn.setAutoCommit(false);
			ResultSet rs = daoBuque.getBuque(idBuque);
			Buque buque = new Buque(idBuque, rs.getString("NOMBRE"), rs.getString("NOMBRE_AGENTE"),
					rs.getFloat("CAPACIDAD"), rs.getBoolean("LLENO"), rs.getString("ERGISTRO_CAPITANIA"),
					rs.getString("DESTINO"), rs.getString("ORIGEN"), tipoBuque.valueOf(rs.getString("TIPO")),
					estado.valueOf(rs.getString("ESTADO")), null);
			if (rs.getString("ESTADO").compareTo(estado.DISPONIBLE.name()) == 0) {
				// TODO Check ResultSet is pointing to BeforeFirst
				ResultSet rs2 = daoMercancia.getMercanciasDestino(rs.getString("DESTINO"));
				int cantidad = 0;
				while (rs2.next()) {
					String tipoBuque = rs.getString("TIPO_BUQUE");
					String tipoMercancia = rs2.getString("TIPO_CARGA");
					if (tipoBuque.compareTo(vos.Buque.tipoBuque.RORO.name()) == 0
							&& tipoMercancia.compareTo(vos.Buque.tipoMercancia.RODADA.name()) != 0) {
						throw new Exception("Este buque no puede llevar una mercancia de este tipo");
					} else if (tipoBuque.compareTo(vos.Buque.tipoBuque.PORTACONTENEDORES.name()) == 0
							&& tipoMercancia.compareTo(vos.Buque.tipoMercancia.CONTENEDORES.name()) != 0) {
						throw new Exception("Este buque no puede llevar una mercancia de este tipo");
					} else {
						if (rs2.getString("ESTADO").compareTo("DISPONIBLE") != 0) {
							throw new Exception(
									"El area de almacenamiento no está disponible en el momento para movimientos de carga");
						}
					}
					cantidad += rs2.getFloat("VOLUMEN");
				}
				if (cantidad > rs.getFloat("CAPACIDAD")) {
					throw new Exception("El buque no tiene la capacidad suficiente");
				}
				rs2.beforeFirst();
				while (rs2.next()) {
					daoAlmacenamiento.updateEstado(rs2.getInt("ID_AREA"), estado.EN_PROCESO_DE_CARGA);
				}
				daoBuque.updateEstado(idBuque, estado.EN_PROCESO_DE_CARGA);
				rs2.beforeFirst();
				while (rs2.next()) {
					Date hoy = new java.sql.Date(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)).getTime());
					EntregaMercancia entrega = new EntregaMercancia(new Mercancia(rs2.getInt("ID_MERCANCIA")), hoy, hoy,
							tipoEntrega.DESDE_AREA_ALMACENAMIENTO, new AreaAlmacenamiento(rs2.getInt("ID_AREA")), null,
							new Buque(idBuque));
					addCargaTipoABuque(entrega);
				}
				rs2.beforeFirst();
				while (rs2.next()) {
					daoAlmacenamiento.updateEstado(rs2.getInt("ID_AREA"), estado.DISPONIBLE);
				}
				daoBuque.updateEstado(idBuque, estado.DISPONIBLE);
			} else {
				throw new Exception("El barco no se encuentra disponible");
			}
			conn.commit();
			return buque;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoBuque.cerrarRecursos();
				daoMercancia.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RF11
	public AreaAlmacenamiento descargarBuque(int idBuque, String destino) throws SQLException, Exception {
		DAOBuque daoBuque = new DAOBuque();
		DAOMercancia daoMercancia = new DAOMercancia();
		DAOAreaAlmacenamiento daoAlmacenamiento = new DAOAreaAlmacenamiento();
		try {
			////// Transacción
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoMercancia.setConn(conn);
			daoAlmacenamiento.setConn(conn);
			conn.setAutoCommit(false);
			ResultSet rs = daoMercancia.getMercanciasBuqueDescarga(idBuque, destino);
			int idArea = -1;
			Date hoy = new java.sql.Date(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)).getTime());
			if (rs.getString("ESTADO").compareTo(estado.DISPONIBLE.name()) == 0) {
				// TODO Check ResultSet is pointing to BeforeFirst
				ResultSet rs2 = daoAlmacenamiento.getAreasDisponibles();
				boolean encontrada = false;
				float volumen = 0;
				while (rs2.next()) {
					String tipoArea = rs2.getString("TIPO_MERCANCIA");
					boolean adecuada = true;
					volumen = 0;
					while(rs.next()){
						String tipoMercancia = rs.getString("TIPO_CARGA");
						volumen += rs.getFloat("VOLUMEN");
						if (tipoArea.compareTo(vos.Buque.tipoMercancia.RODADA.name()) == 0
								&& tipoMercancia.compareTo(vos.Buque.tipoMercancia.RODADA.name()) != 0) {
							adecuada = false;
							break;
						} else if (tipoArea.compareTo(vos.Buque.tipoMercancia.CONTENEDORES.name()) == 0
								&& tipoMercancia.compareTo(vos.Buque.tipoMercancia.CONTENEDORES.name()) != 0) {
							adecuada = false;
							break;						
						} else if (tipoArea.compareTo(vos.Buque.tipoMercancia.GENERAL.name()) == 0
								&& tipoMercancia.compareTo(vos.Buque.tipoMercancia.GENERAL.name()) != 0) {
							adecuada = false;
							break;						
						} else if (tipoArea.compareTo(vos.Buque.tipoMercancia.GRANEL_LIQUIDO.name()) == 0
								&& tipoMercancia.compareTo(vos.Buque.tipoMercancia.GRANEL_LIQUIDO.name()) != 0) {
							adecuada = false;
							break;						
						} else if (tipoArea.compareTo(vos.Buque.tipoMercancia.GRANEL_SOLIDO.name()) == 0
								&& tipoMercancia.compareTo(vos.Buque.tipoMercancia.GRANEL_SOLIDO.name()) != 0) {
							adecuada = false;
							break;						
						}					
					}
					if(adecuada == true){
						float capacidad = rs2.getFloat("CAPACIDAD");
						if(capacidad >= volumen){
							encontrada = true;
							break;
						}
					}
				}
				if(encontrada == true){
					idArea =rs2.getInt("ID_AREA");
					daoAlmacenamiento.reservarAlmacenamiento(idArea, hoy);
					daoBuque.updateEstado(idBuque, estado.EN_PROCESO_DE_DESCARGUE);
					rs.beforeFirst();
					while(rs.next()){
						EntregaMercancia entrega = new EntregaMercancia(new Mercancia(rs.getInt("ID_MERCANCIA")), hoy, hoy,
								tipoEntrega.DESDE_BUQUE, new AreaAlmacenamiento(idArea), null,
								new Buque(idBuque));
						addCargaTipoAArea(entrega);
					}
					daoBuque.updateEstado(idBuque, estado.DISPONIBLE);
					daoAlmacenamiento.updateEstado(idArea, estado.DISPONIBLE);
				} else {
					throw new Exception("No se encontró un area adecuada para el descague");
				}
			} else {
				throw new Exception("El barco no se encuentra disponible para descargas");
			}
			conn.commit();
			ResultSet rsA = daoAlmacenamiento.getArea(idArea);
			AreaAlmacenamiento area = new AreaAlmacenamiento(idArea, rsA.getBoolean("LLENO"), tipoMercancia.valueOf(rsA.getString("TIPO_MERCANCIA")), estado.valueOf(rsA.getString("ESTADO")), hoy, rsA.getFloat("CAPACIDAD"), null);
			return area;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			conn.rollback();
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoBuque.cerrarRecursos();
				daoMercancia.cerrarRecursos();
				daoAlmacenamiento.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RF12
	public Buque deshabilitarBuque(int idBuque, String tipo) throws Exception {
		DAOBuque daoBuque = new DAOBuque();
		DAOMercancia daoMercancia = new DAOMercancia();
		Savepoint descarga = null;
		Savepoint carga = null;
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoMercancia.setConn(conn);
			conn.setAutoCommit(false);
			ResultSet rsRes = daoBuque.getBuque(idBuque);
			Buque buque = new Buque(idBuque, rsRes.getString("NOMBRE"), rsRes.getString("NOMBRE_AGENTE"),
					rsRes.getFloat("CAPACIDAD"), rsRes.getBoolean("LLENO"), rsRes.getString("REGISTRO_CAPITANIA"),
					rsRes.getString("DESTINO"), rsRes.getString("ORIGEN"), tipoBuque.valueOf(rsRes.getString("TIPO")),
					estado.valueOf(rsRes.getString("ESTADO")), null);
			if(tipo.compareTo("RAZONES_LEGALES") != 0){
				ResultSet rs = daoMercancia.getDestinosMercanciaBuque(idBuque);
				while(rs.next()){
					try {
						descargarBuque(idBuque, rs.getString("DESTINO"));
					} catch (Exception e){
						System.out.println("No se lograron descargar las mercancias con destino " + rs.getString("DESTINO"));
					}
				}
				descarga = conn.setSavepoint("Descarga");
				rs.beforeFirst();
				while(rs.next()){
					boolean ok = false;
					String destino = rs.getString("DESTINO");
					if(destino.compareTo("PUERTO_ANDES") != 0){
						ResultSet rsB = daoBuque.getBuquesConDestino(destino);
						while(rsB.next()){
							try {
								cargarBuque(rsB.getInt("ID_BUQUE"));
								ok = true;
								break;
							} catch (Exception e ){
								System.out.println("Buscando otro buque...");
							}
						}
						if(ok == false){
							System.out.println("No se lograron acomodar las mercancias en buques con destino " + destino);
						}
					}
				}
				carga = conn.setSavepoint("Carga");
			}
			daoBuque.updateEstado(idBuque, estado.DESHABILITADO);
			conn.commit();
			return buque;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			if(carga != null){
				conn.rollback(carga);
			} else if(descarga != null){
				conn.rollback(descarga);
			} else {
				conn.rollback();
			}
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			if(carga != null){
				conn.rollback(carga);
			} else if(descarga != null){
				conn.rollback(descarga);
			} else {
				conn.rollback();
			}
			throw e;
		} finally {
			try {
				daoBuque.cerrarRecursos();
				daoMercancia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RFC1
	public ArrayList<MovimientoBuque> consultarArribosSalidas(Date fechaIni, Date fechaFin, String nombreBuque,
			tipoMercancia tipoMercancia, Time hora, String orderBy, String groupBy) throws Exception {
		ArrayList<MovimientoBuque> movimientos = new ArrayList<MovimientoBuque>();
		DAOConsultas daoPuertoAndes = new DAOConsultas();
		try {
			////// Transacción
			this.conn = darConexion();
			daoPuertoAndes.setConn(conn);
			movimientos = daoPuertoAndes.consultarArribosSalidas(fechaIni, fechaFin, nombreBuque, tipoMercancia, hora,
					orderBy, groupBy);
			conn.commit();
			return movimientos;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertoAndes.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	// RFC4
	public ArrayList<AreaAlmacenamiento> consultarAAMasUtilizada(Date fechaIni, Date fechaFin) throws Exception {
		ArrayList<AreaAlmacenamiento> areas = new ArrayList<AreaAlmacenamiento>();
		DAOConsultas daoPuertoAndes = new DAOConsultas();
		try {
			////// Transacción
			this.conn = darConexion();
			daoPuertoAndes.setConn(conn);
			areas = daoPuertoAndes.consultarAAMasUtilizada(fechaIni, fechaFin);
			conn.commit();
			return areas;
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPuertoAndes.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

}
