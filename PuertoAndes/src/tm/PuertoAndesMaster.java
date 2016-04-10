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
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import dao.*;
import vos.*;
import vos.AreaAlmacenamiento.estado;
import vos.Buque.tipoBuque;
import vos.Buque.tipoMercancia;
import vos.EntregaMercancia.tipoEntrega;
import vos.Usuario.tipoPersona;

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
	public void addCargaTipoABuque(EntregaMercancia entrega) throws Exception{
		DAOBuque daoBuque = new DAOBuque();
		DAOMercancia daoMercancia = new DAOMercancia();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoMercancia.setConn(conn);
			conn.setAutoCommit(false);
			int idMercancia = entrega.getMercancia().getId();
			int idBuque = entrega.getBuque().getId();
			int idArea = -1;
			if(entrega.getTipo().compareTo(tipoEntrega.DESDE_AREA_ALMACENAMIENTO) == 0){
				idArea = daoMercancia.getAreaMercancia(idMercancia);
				daoMercancia.deleteMercanciaArea(idMercancia, idArea);
				//TODO Actualizar capacidad areaAlmacenamiento
			}
			else if(entrega.getTipo().compareTo(tipoEntrega.DESDE_BUQUE) == 0){
				int idBuque2 = daoMercancia.getBuqueMercancia(idMercancia);
				daoMercancia.deleteMercanciaBuque(idMercancia, idBuque2);
				//TODO Actualizar capacidadBuque2
			}
			
			daoMercancia.insertMercanciaBuque(idMercancia, idBuque );
			float volumenMercancia = daoMercancia.getVolumenMercancia(idMercancia);
			daoBuque.updateCapacidadBuque(idBuque, volumenMercancia);
			if(idArea != -1){
				daoMercancia.insertEntregaMercanciaBuque(entrega, idArea);
			} else {
				//TODO INSERT en nueva tabla Buque a Buque
			}
			
			conn.commit();
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
		public void addCargaAlmacenamiento(EntregaMercancia entrega) throws Exception{
			DAOAreaAlmacenamiento daoareaAlmacenamiento = new DAOAreaAlmacenamiento();
			DAOMercancia daoMercancia = new DAOMercancia();
			try 
			{
				//////Transacción
				this.conn = darConexion();
				daoareaAlmacenamiento.setConn(conn);
				daoMercancia.setConn(conn);
				conn.setAutoCommit(false);
				int idMercancia = entrega.getMercancia().getId();
				int idarea = entrega.getArea().getId();
				int idBuque = -1;
				if(entrega.getTipo().compareTo(tipoEntrega.DESDE_BUQUE) == 0){
					idBuque = daoMercancia.getBuqueMercancia(idMercancia);
					daoMercancia.deleteMercanciaBuque(idMercancia, idBuque);;
				}
				else if(entrega.getTipo().compareTo(tipoEntrega.DESDE_AREA_ALMACENAMIENTO) == 0){
					int idArea2 = daoMercancia.getAreaMercancia(idMercancia);
					daoMercancia.deleteMercanciaArea(idMercancia, idArea2);;
					//TODO INSERT en nueva tabla almacenamiento a almacenamiento
				}
				
				
				daoMercancia.insertMercanciaAreaAlmacenamiento(idMercancia, idarea);
				float volumenMercancia = daoMercancia.getVolumenMercancia(idMercancia);
				daoareaAlmacenamiento.updateCapacidadAreaAlmacenamiento(idarea, volumenMercancia);
				daoMercancia.insertEntregaMercanciaArea(entrega, idBuque);
				conn.commit();
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
					daoareaAlmacenamiento.cerrarRecursos();
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
		
	

	
	
	
	
	//RF8
	public void addEntregaMercanciaImportador(EntregaMercancia mercancia, int idAA) throws Exception {
		DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();	
		try 
		{
			//////Transacción
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
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	//RF9
		public void addFactura(Factura factura) throws Exception{
			DAOOperadorPortuario daoOperadorPortuario = new DAOOperadorPortuario();
			try 
			{
				//////Transacción
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
					if(this.conn!=null)
						this.conn.close();
				} catch (SQLException exception) {
					System.err.println("SQLException closing resources:" + exception.getMessage());
					exception.printStackTrace();
					throw exception;
				}
			}		
		}
	
	//RF10
	public Buque cargarBuque (int idBuque) throws SQLException, Exception{
		DAOBuque daoBuque = new DAOBuque();
		DAOMercancia daoMercancia = new DAOMercancia();
		DAOAreaAlmacenamiento daoAlmacenamiento = new DAOAreaAlmacenamiento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoMercancia.setConn(conn);
			daoAlmacenamiento.setConn(conn);
			conn.setAutoCommit(false);
			ResultSet rs = daoBuque.getBuque(idBuque);
			Buque buque = new Buque(idBuque, rs.getString("NOMBRE"), rs.getString("NOMBRE_AGENTE"), rs.getFloat("CAPACIDAD"), rs.getBoolean("LLENO"), rs.getString("RGISTRO_CAPITANIA"), rs.getString("DESTINO"), rs.getString("ORIGEN"), tipoBuque.valueOf(rs.getString("TIPO")), estado.valueOf(rs.getString("ESTADO")), null);
			if(rs.getString("ESTADO").compareTo(estado.DISPONIBLE.name()) == 0){
				//TODO Check ResultSet is pointing to BeforeFirst
				ResultSet rs2 = daoMercancia.getMercanciasDestino(rs.getString("DESTINO"));
				int cantidad = 0;
				while(rs2.next()){
					String tipoBuque = rs.getString("TIPO_BUQUE");
					String tipoMercancia = rs2.getString("TIPO_CARGA");
					if(tipoBuque.compareTo(vos.Buque.tipoBuque.RORO.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.RODADA.name()) != 0){
						throw new Exception("Este buque no puede llevar una mercancia de este tipo");
					}
					else if(tipoBuque.compareTo(vos.Buque.tipoBuque.PORTACONTENEDORES.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.CONTENEDORES.name()) != 0){
						throw new Exception("Este buque no puede llevar una mercancia de este tipo");
					}
					else {
						if(rs2.getString("ESTADO").compareTo("DISPONIBLE") != 0){
							throw new Exception("El area de almacenamiento no está disponible en el momento para movimientos de carga");
						}
					}
					cantidad += rs2.getFloat("VOLUMEN");
				}
				if(cantidad > rs.getFloat("CAPACIDAD")){
					throw new Exception("El buque no tiene la capacidad suficiente");
				}
				rs2.beforeFirst();
				while(rs2.next()){
					daoAlmacenamiento.updateEstado(rs2.getInt("ID_AREA"), estado.EN_PROCESO_DE_CARGA);
				}
				daoBuque.updateEstado(idBuque, estado.EN_PROCESO_DE_CARGA);
				rs2.beforeFirst();
				while(rs2.next()){
					Date hoy = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
					EntregaMercancia entrega = new EntregaMercancia(new Mercancia(rs2.getInt("ID_MERCANCIA")), hoy, hoy, tipoEntrega.DESDE_AREA_ALMACENAMIENTO , new AreaAlmacenamiento(rs2.getInt("ID_AREA")), new Buque(idBuque));
					addCargaTipoABuque(entrega);
				}
				rs2.beforeFirst();
				while(rs2.next()){
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
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	//RF11 
	public AreaAlmacenamiento descargarBuque (int idArea, int idBuque, Date fecha) throws SQLException, Exception 
	{
		DAOBuque daoBuque = new DAOBuque();
		DAOMercancia daoMercancia = new DAOMercancia();
		DAOAreaAlmacenamiento daoAlmacenamiento = new DAOAreaAlmacenamiento();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoBuque.setConn(conn);
			daoMercancia.setConn(conn);
			daoAlmacenamiento.setConn(conn);
			conn.setAutoCommit(false);
			ResultSet rs = daoBuque.getBuque(idBuque);
			ResultSet rsA=daoAlmacenamiento.getArea(idArea);
			AreaAlmacenamiento area= new AreaAlmacenamiento(idArea, rsA.getBoolean("LLENO"),  tipoMercancia.valueOf(rsA.getString("TIPO_MERCANCIA")), estado.valueOf(rsA.getString("ESTADO")), rsA.getDate("FECHA_RESERVA"), rsA.getFloat("CAPACIDAD"), null);
			if(rs.getString("ESTADO").compareTo(estado.CARGADO.name()) == 0 ){
				//TODO Check ResultSet is pointing to BeforeFirst
				ResultSet rs2 = daoMercancia.getMercanciasDestino(rs.getString("DESTINO"));
				
				while(rs2.next()){
					String tipoArea = rsA.getString("TIPO_MERCANCIA");
					String tipoMercancia = rs2.getString("TIPO_CARGA");
					if(tipoArea.compareTo(vos.Buque.tipoMercancia.RODADA.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.RODADA.name()) != 0){
						throw new Exception("Esta area de almacenamiento no puede guardar una mercancia de este tipo");
					}
					else if(tipoArea.compareTo(vos.Buque.tipoMercancia.CONTENEDORES.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.CONTENEDORES.name()) != 0){
						throw new Exception("Esta area de almacenamiento no puede guardar una mercancia de este tipo");
					}
					else if(tipoArea.compareTo(vos.Buque.tipoMercancia.GENERAL.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.GENERAL.name()) != 0){
						throw new Exception("Esta area de almacenamiento no puede guardar una mercancia de este tipo");
					}
					else if(tipoArea.compareTo(vos.Buque.tipoMercancia.GRANEL_LIQUIDO.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.GRANEL_LIQUIDO.name()) != 0){
						throw new Exception("Esta area de almacenamiento no puede guardar una mercancia de este tipo");
					}
					else if(tipoArea.compareTo(vos.Buque.tipoMercancia.GRANEL_SOLIDO.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.GRANEL_SOLIDO.name()) != 0){
						throw new Exception("Esta area de almacenamiento no puede guardar una mercancia de este tipo");
					}
					else {
						if( rsA.getString("ESTADO_RESERVA").compareTo(estado.DISPONIBLE.name())!=0){
							throw new Exception("El area de almacenamiento no está disponible en el momento para movimientos de carga");
						}
					}
					daoAlmacenamiento.descargarBuque(idBuque, idArea, fecha);
					
				}
			}
			conn.commit();
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
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		}
		
	
	
	
	//RFC1
	public ArrayList<MovimientoBuque> consultarArribosSalidas(Date fechaIni, Date fechaFin, String nombreBuque,tipoMercancia tipoMercancia, Time hora, String orderBy, String groupBy) throws Exception{
		ArrayList<MovimientoBuque> movimientos = new ArrayList<MovimientoBuque>();
		DAOConsultas daoPuertoAndes = new DAOConsultas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertoAndes.setConn(conn);
			movimientos = daoPuertoAndes.consultarArribosSalidas(fechaIni,fechaFin,nombreBuque,tipoMercancia,hora,orderBy,groupBy);
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
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}
	//RFC4
	public ArrayList<AreaAlmacenamiento> consultarAAMasUtilizada(Date fechaIni, Date fechaFin) throws Exception{
		ArrayList<AreaAlmacenamiento> areas = new ArrayList<AreaAlmacenamiento>();
		DAOConsultas daoPuertoAndes = new DAOConsultas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoPuertoAndes.setConn(conn);
			areas = daoPuertoAndes.consultarAAMasUtilizada(fechaIni,fechaFin);
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
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}	
	}

}

