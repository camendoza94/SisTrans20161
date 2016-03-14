package dao;

public class DAOAdmin {

	//----------------------------------------------------------
	//Constantes
	//---------------------------------------------------------
	
	
	/**
	 * ruta donde se encuentra el archivo de conexión.
	 */
	private static final String ARCHIVO_CONEXION = "/conexion.properties";
	
	
	//---------------------------------------------------------
	//Sentencias SQL
	//--------------------------------------------------------
	
	private final static String REGISTRAR_USUARIO = "INSERT INTO USUARIOS"+
												"(ID_USUARIO,NOMBRE,TIPO_PERSONA,TIPO_USUARIO)VALUES";
	
	private final static String REGISTRAR_IMPORTADOR= "INSERT INTO IMPORTADORES"+ 
														"(ID_USUARIO, REGISTRO_ADUANA,TIPO) VALUES ";
	
	private final static String REGISTRAR_EXPORTADOR= "INSERT INTO EXPORTADORES"+
														"(ID_USUARIO,RUT )VALUES";
	
	private final static String REGISTRAR_AREA_ALMACENAMIENTO= "INSERT INTO AREAS_ALMACENAMIENTO" +
																	"(ID_AREA,LLENO )VALUES";
	
	private final static String REGISTRAR_SILO= "INSERT INTO SILOS"+
												"(ID_SILO,NOMBRE,CAPACIDAD)VALUES";
	
	private final static String REGISTRAR_PATIO="INSERT INTO PATIOS"+
												"(ID_PATIO,DIMEN,TIPO_CARGA)VALUES";
	
	private final static String REGISTRAR_BODEGA="INSERT INTO BODEGAS"+
												"(ID_BODEGA,ANCHO,LARGO,PLATAFORMA,SEPARACION_COLUMNA)VALUES";
	
	private final static String REGISTRAR_CUARTO_FRIO= "INSERT INTO CUARTOS_FRIOS"+
													"(ID_CUARTO,ID_BODEGA,AREA,LARGO,ALTURA,AREA_F_BODEGA)VALUES";
	
	private final static String REGISTRAR_COBERTIZO= "INSERT INTO COBERTIZOS"+
													"(ID_COBERTIZO, DIMEN, TIPO_CARGA)VALUES";
	
	private final static String REGISTRAR_BUQUE= "INSERT INTO BUQUES"+
												"(ID_BUQUE,NOMBRE,NOMBRE_AGENTE,CAPACIDAD,LLENO,FECHA_INGRESO,FECHA_SALIDA,REGISTRO_CAPITANIA,DESTINO,ORIGEN,TIPOBUQUE)VALUES";
	
	
	/**
	 * TODO
	 */
	private final static String REGISTRAR_ARRIBO_BUQUE="";
	 
}
