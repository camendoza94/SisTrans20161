/**-------------------------------------------------------------------
 * $Id$
 * 
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n
 *
 * Materia: Sistemas Transaccionales
 * Extractos tomados de VideoAndes por Juan Felipe Garc�a - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import dao.*;
import vos.*;

/**
 * Fachada en patron singleton de la aplicaci�n
 * @author Juan
 */
public class PuertoAndesMaster {


	/**
	 * Atributo est�tico que contiene el path relativo del archivo que tiene los datos de la conexi�n
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo est�tico que contiene el path absoluto del archivo que tiene los datos de la conexi�n
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
	 * Conexi�n a la base de datos
	 */
	private Connection conn;


	/**
	 * M�todo constructor de la clase PuertoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto PuertoAndesMaster, se inicializa el path absoluto de el archivo de conexi�n y se
	 * inicializa los atributos que se usan par la conexi�n a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public PuertoAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/**
	 * M�todo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexi�n a la base de datos.
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
	 * M�todo que  retorna la conexi�n a la base de datos
	 * @return Connection - la conexi�n a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexi�n a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////


	/**
	 * M�todo que modela la transacci�n que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacci�n
	 */
	public ListaVideos darVideos() throws Exception {
		ArrayList<Video> videos;
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoVideos.setConn(conn);
			videos = daoVideos.darVideos();

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
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVideos(videos);
	}

	/**
	 * M�todo que modela la transacci�n que busca el/los videos en la base de datos con el nombre entra como par�metro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacci�n
	 */
	public ListaVideos buscarVideosPorName(String name) throws Exception {
		ArrayList<Video> videos;
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoVideos.setConn(conn);
			videos = daoVideos.buscarVideosPorName(name);

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
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVideos(videos);
	}
	
	/**
	 * M�todo que modela la transacci�n que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como par�metro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addVideo(Video video) throws Exception {
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoVideos.setConn(conn);
			daoVideos.addVideo(video);
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
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * M�todo que modela la transacci�n que agrega los videos que entran como par�metro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como par�metro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addVideos(ListaVideos videos) throws Exception {
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try 
		{
			//////Transacci�n - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoVideos.setConn(conn);
			for(Video video : videos.getVideos())
				daoVideos.addVideo(video);
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
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	/**
	 * M�todo que modela la transacci�n que actualiza el video que entra como par�metro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como par�metro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateVideo(Video video) throws Exception {
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoVideos.setConn(conn);
			daoVideos.updateVideo(video);

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
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * M�todo que modela la transacci�n que elimina el video que entra como par�metro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como par�metro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteVideo(Video video) throws Exception {
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoVideos.setConn(conn);
			daoVideos.deleteVideo(video);

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
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * M�todo que modela la transacci�n que retorna el/los videos mas alquilados
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacci�n
	 */
	public ListaVideos videosMasAlquilados() throws Exception {
		ArrayList<Video> videos;
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoVideos.setConn(conn);
			videos = daoVideos.darVideoMasAlquilado();

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
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVideos(videos);
	}
	
}

