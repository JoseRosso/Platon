package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class Actividad {
	
	int idActividad;
	byte[] enunciado;
	Date fechaEntrega;
	int idCurso;
	
	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;
	
	public Actividad() {
		c = new Conexion();
	}
	
	public void insertarActividad(byte[] enunciado, Date fechaEntrega, int idCurso) {
		con = c.crearConexion();

		sql = "INSERT INTO platon.actividad VALUES (default,?,?,?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setBytes(1, enunciado);
			ps.setDate(2, fechaEntrega);
			ps.setInt(3, idCurso);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarActividad(int idActividad) {
		con = c.crearConexion();
		sql = "DELETE from platon.actividad WHERE idActividad = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, idActividad);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarActividad(byte[] actividad, Date fechaEntrega, int idActividad) {
		con = c.crearConexion();
		sql = "UPDATE platon.actividad SET actividad= ?, fechaEntrega = ? WHERE idActividad = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setBytes(1, actividad);
			ps.setDate(2, fechaEntrega);
			ps.setInt(3, idActividad);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[] cargarActividad(String[] columnas, String[] busqueda, Object[] valores, int operador) {
		String queryColumnas = "";
		String condicion = "";
		Object[] consulta = null;
		String queryColumnasTemp;
		con = c.crearConexion();

		// Obtenemos las columnas que queremos obtener de la BD
		for (int i = 0; i < columnas.length; i++) {
			if ((i + 1) == columnas.length)
				queryColumnasTemp = queryColumnas + columnas[i];
			else
				queryColumnasTemp = queryColumnas + columnas[i] + ",";

			queryColumnas = queryColumnasTemp;
		}

		// Obtemos el nombre de las columnas por las que realizaremos la consulta
		for (int i = 0; i < busqueda.length; i++) {
			if ((i + 1) == busqueda.length)
				condicion += busqueda[i] + "=?";
			else if (operador == 1)
				condicion += busqueda[i] + "=? AND";
			else if (operador == 2)
				condicion += busqueda[i] + "=? OR";
		}

		sql = "SELECT " + queryColumnas + " FROM platon.actividad WHERE " + condicion;

		// Hacemos la precompilacion de la query y añadimos los parametros de busqueda
		try {
			ps = con.prepareStatement(sql);
			for (int i = 0; i < busqueda.length; i++) {
				if (valores[i] != null) {
					switch (valores[i].getClass().getName()) {
					case "java.lang.Integer":
						ps.setInt(i + 1, (Integer) valores[i]);
						break;

					case "java.lang.String":
						ps.setString(i + 1, (String) valores[i]);
						break;

					case "java.lang.Boolean":
						consulta[i] = (Boolean) rs.getObject(i + 1);
						break;

					case "java.sql.Date":
						consulta[i] = (Date) rs.getObject(i + 1);
						break;
					default:
						System.out.println("El tipo de dato de uno o más campos son/es inválidos");
						break;
					}
				} else
					System.out.println("El valor de uno de los valores es null");
			}

			// Obtenemos los ResultSet
			rs = ps.executeQuery();
			consulta = new Object[columnas.length];

			if (rs.next()) {
				for (int i = 0; i < consulta.length; i++) {
					if (rs.getObject(i + 1) == null)
						consulta[i] = null;
					else {
						switch (rs.getObject(i + 1).getClass().getName()) {
						case "java.lang.Integer":
							consulta[i] = (Integer) rs.getObject(i + 1);
							break;

						case "java.lang.String":
							consulta[i] = (String) rs.getObject(i + 1);
							break;

						case "[B":
							consulta[i] = (byte[]) rs.getObject(i + 1);
							break;

						case "java.lang.Boolean":
							consulta[i] = (Boolean) rs.getObject(i + 1);
							break;

						case "java.sql.Date":
							consulta[i] = (Date) rs.getObject(i + 1);
							break;
						default:
							System.out.println("El tipo de dato de uno o más campos son inválidos");
						}
					}
				}
				rs.close();
				ps.close();
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return consulta;
	}

	
	
	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public byte[] getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(byte[] enunciado) {
		this.enunciado = enunciado;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	
}
