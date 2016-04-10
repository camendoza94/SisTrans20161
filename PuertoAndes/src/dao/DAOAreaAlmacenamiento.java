package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.AreaAlmacenamiento;
import vos.AreaAlmacenamiento.estado;
import vos.Mercancia;
import vos.Mercancia.claseMercancia;
import vos.Usuario;

public class DAOAreaAlmacenamiento {
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
	public DAOAreaAlmacenamiento() {
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
	
	public void updateEstado(int idArea, estado estado) throws SQLException{
		String sql = "UPDATE AREAS_ALMACENAMIENTO SET ESTADO='" + estado.name() + "'";
		sql += " WHERE ID_AREA=" + idArea;
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
	}
	
	public void updateCapacidad(int idArea, float volumenMercancia) throws SQLException{
		String sql = "UPDATE AREAS_ALMACENAMIENTO SET CAPACIDAD=CAPACIDAD+"+ volumenMercancia;
		sql += " WHERE ID_AREA = " + idArea;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	//RF7
		/**
		 * 
		 * @param idArea
		 * @param volumenMercancia
		 * @throws SQLException
		 */
		public void updateCapacidadAreaAlmacenamiento(int idArea, float volumenMercancia) throws SQLException{
			String sql = "UPDATE AREAS_ALMACENAMIENTO SET CAPACIDAD=CAPACIDAD-"+ volumenMercancia;
			sql += " WHERE ID_AREA = " + idArea;

			System.out.println("SQL stmt:" + sql);

			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
		
		public ResultSet getArea(int idArea) throws SQLException{
			String sql = "SELECT * FROM AREAS_ALAMACENAMIENTO WHERE ID_AREA =" + idArea;
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()){
				return rs;
			}
			return null;
		}
		
		private Object[] infoMercanciaAlmacenamiento(int idMercancia, int idArea) throws SQLException{
			Object[] respuesta = new Object[2];
			String sql = "SELECT * FROM MERCANCIA WHERE ID_MERCANCIA = " + idMercancia;
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()){
				respuesta[0] = new Mercancia(idMercancia,rs.getFloat("PRECIO"), claseMercancia.valueOf(rs.getString("PROPOSITO")), rs.getFloat("VOLUMEN"), new Usuario(rs.getInt("PROPIETARIO")), rs.getString("ORIGEN"), rs.getString("DESTINO"), vos.Buque.tipoMercancia.valueOf(rs.getString("TIPO_CARGA")), rs.getFloat("PESO"));
			}
			String sql2 = "SELECT * FROM AREAS_ALMACENAMIENTO WHERE ID_AREA = " + idArea;
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next()){
				respuesta[1] = new AreaAlmacenamiento(idArea, rs2.getBoolean("LLENO"),vos.Buque.tipoMercancia.valueOf(rs2.getString("TIPO_MERCANCIA")), vos.AreaAlmacenamiento.estado.valueOf(rs2.getString("ESTADO")),rs2.getDate("FECHA_RESERVA") , rs2.getFloat("CAPACIDAD"), null);
			}
			return respuesta;
		}
		
				/**
				 * 
				 * @param area
				 * @param idPuerto
				 * @throws SQLException
				 * @throws Exception
				 */
				public Object[] addCargaAlmacenamiento(int idMercancia, int idArea) throws SQLException {
					Object[] respuesta = infoMercanciaAlmacenamiento(idMercancia, idArea);
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
				
				//RF11          
				
				public void descargarBuque(int idBuque, int idArea, Date fecha) throws SQLException, Exception{
					
					String sql = "SELECT * FROM BUQUES WHERE ID_BUQUE =" + idBuque;
					PreparedStatement prepStmt = conn.prepareStatement(sql);
					recursos.add(prepStmt);
					ResultSet rs = prepStmt.executeQuery();
					if(rs.next()){
						if(rs.getString("ESTADO").compareTo("CARGADO") != 0){
							throw new Exception("El buque no contiene carga");
						}
					
					}
					String sql3 = "SELECT * FROM MERCANCIA_EN_BUQUE A JOIN MERCANCIAS B ON A.ID_MERCANCIA=B.ID_MERCANCIA;" ;
					PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
					recursos.add(prepStmt3);
					ResultSet rs3 = prepStmt3.executeQuery();
					
					
					
					String sql2 = "SELECT * FROM AREAS_ALMACENAMIENTO WHERE ID_AREA='" + idArea+ ";";
					PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
					recursos.add(prepStmt2);
					ResultSet rs2 = prepStmt2.executeQuery();
					if(rs2.next()){
						float capacidad = 0;
						rs2.beforeFirst();
						while(rs2.next()){
							if(rs3.getInt("ID_BUQUE")== idBuque){
							if(reservarAlmacenamiento(rs2.getInt("ID_AREA"), fecha, rs3.getFloat("VOLUMEN") , rs3.getInt("ID_MERCANCIA"))== true){
							
							String sql8= "UPDATE BUQUES SET ESTADO='EN_PROCESO_DE_DESCARGA' WHERE ID_BUQUE="+idBuque+";";
							PreparedStatement prepStmt8 = conn.prepareStatement(sql8);
							recursos.add(prepStmt8);
							prepStmt8.executeQuery();
							}
							
							addCargaAlmacenamiento(rs3.getInt("ID_MERCANCIA"), rs2.getInt("ID_AREA"));
							capacidad=rs2.getFloat("CAPACIDAD");												
						}}
						
						
						rs2.beforeFirst();
						while(rs2.next()){
							if(capacidad ==0){
							String sql6 = "UPDATE AREAS_ALMACENAMIENTO SET LLENO='1',ESTADO_RESERVA=CARGADO WHERE ID_AREA=" + rs2.getInt("ID_AREA");
							PreparedStatement prepStmt6 = conn.prepareStatement(sql6);
							recursos.add(prepStmt6);
							prepStmt6.executeQuery();
							}
							else{
								String sql9 = "UPDATE AREAS_ALMACENAMIENTO SET ESTADO_RESERVA=DISPONIBLE WHERE ID_AREA=" + rs2.getInt("ID_AREA");
								PreparedStatement prepStmt9 = conn.prepareStatement(sql9);
								recursos.add(prepStmt9);
								prepStmt9.executeQuery();
							}
						}
							
						String sql7 = "UPDATE BUQUES SET ESTADO='DISPONIBLE' WHERE ID_BUQUE=" + idBuque;
						PreparedStatement prepStmt7 = conn.prepareStatement(sql7);
						recursos.add(prepStmt7);
						prepStmt7.executeQuery();
					}
					
				}
				
				//RF11.1          
				
				public boolean reservarAlmacenamiento(int idArea, Date fecha, float volumen, int idMercancia ) throws SQLException, Exception {
					boolean reservado = true;
					String sql = "SELECT * FROM AREAS_ALMACENAMIENTO " ;
					PreparedStatement prepStmt = conn.prepareStatement(sql);
					recursos.add(prepStmt);
					ResultSet rs = prepStmt.executeQuery();
					if(rs.next()){
						String sql2 = "SELECT TIPO_CARGA FROM MERCANCIA_EN_BUQUE WHERE ID_MERCANCIA = " + idMercancia;
						PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
						recursos.add(prepStmt2);
						ResultSet rs2 = prepStmt2.executeQuery();
						if(rs2.next()){
							String tipoArea = rs2.getString("TIPO_MERCANCIA");
							String tipoMercancia = rs.getString("TIPO_CARGA");
							if(tipoArea.compareTo(vos.Buque.tipoMercancia.RODADA.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.RODADA.name()) != 0){
								reservado=false;
								throw new Exception("Esta area de almacenamiento no puede guardar una mercancia de este tipo");
							}
							else if(tipoArea.compareTo(vos.Buque.tipoMercancia.CONTENEDORES.name()) == 0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.CONTENEDORES.name()) != 0){
								reservado=false;
								throw new Exception("Esta area de almacenamiento no puede guardar una mercancia de este tipo");
							}
							else if(tipoArea.compareTo(vos.Buque.tipoMercancia.GRANEL_LIQUIDO.name())==0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.GRANEL_LIQUIDO.name())!=0)
							{	reservado=false;
								throw new Exception("Esta area de almacenamiento no puede guardar una mercancia de este tipo");							
							}
							else if (tipoArea.compareTo(vos.Buque.tipoMercancia.GRANEL_SOLIDO.name())==0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.GRANEL_SOLIDO.name())!=0)
							{	reservado=false;
								throw new Exception ("Esta area de almacenamiento no puede guardar una mercancia de este tipo");
							}
							else if (tipoArea.compareTo(vos.Buque.tipoMercancia.GENERAL.name())==0 && tipoMercancia.compareTo(vos.Buque.tipoMercancia.GENERAL.name())!=0)
							{ reservado=false;
								throw new Exception("Esta area de almacenamiento no puede guardar una mercancia de este tipo");
							}
							else if(rs.getFloat("CAPACIDAD") < volumen){
								reservado=false;
								throw new Exception("Esta area de almacenamiento no cuenta con suficiente capacidad para guardar la mercancia");
							}
							else {
								if(rs.getString("ESTADO_RESERVA").compareTo("DISPONIBLE") != 0){
									reservado=false;
									throw new Exception("El area de almacenamiento no está disponible en el momento para movimientos de carga");
								}
							}
						} 
						else {
							reservado=false;
							throw new Exception("Esta area de almacenamiento no existe");
						}
					}
					else{
						reservado=false;
						throw new Exception("Esta mercancia no existe");
					}
					
								
					String sql2 = "UPDATE AREAS_ALMACENAMIENTO SET FECHA_RESERVA="+ fecha +", ESTADO_RESERVA=RESERVA" ;
					sql2 += " WHERE ID_AREA = " + idArea+";";

					System.out.println("SQL stmt:" + sql2);

					PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
					recursos.add(prepStmt2);
					prepStmt2.executeQuery();
					return reservado;
				}
		}

