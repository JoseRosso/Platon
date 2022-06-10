package modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class Matricula {

	int idMatricula;
	int idUsuario;
	int idCurso;
	Date fechaMatriculacion;

	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;

	public Matricula() {
		c = new Conexion();
	}

	public void insertarMatricula(int idUsuario, int idCurso, Date fechaMatriculacion) {
		con = c.crearConexion();

		sql = "INSERT INTO platon.matricula VALUES (default,?,?,?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, idUsuario);
			ps.setInt(2, idCurso);
			ps.setDate(3, fechaMatriculacion);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarMatricula(int idMatricula) {
		con = c.crearConexion();
		sql = "DELETE from platon.matricula WHERE idMaterial = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, idMatricula);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarMatricula(int idUsuario, int idCurso, Date fechaMatriculacion, int idMatricula) {
		con = c.crearConexion();
		sql = "UPDATE platon.matricula SET idUsuario = ?, idCurso = ?, fechaMatriculacion = ? WHERE idMaterial= ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, idUsuario);
			ps.setInt(2, idCurso);
			ps.setDate(3, fechaMatriculacion);
			ps.setInt(4, idMatricula);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[] cargarMatricula(String[] columnas, String[] busqueda, Object[] valores, int operador) {
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
				condicion += busqueda[i] + "=? ";
			else if (operador == 1)
				condicion += busqueda[i] + "=? AND ";
			else if (operador == 2)
				condicion += busqueda[i] + "=? OR ";
		}

		sql = "SELECT " + queryColumnas + " FROM platon.matricula WHERE " + condicion;

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

					case "java.lang.Long":
						ps.setLong(i + 1, (Long) valores[i]);
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
							consulta[i] = rs.getObject(i + 1);
							break;

						case "java.lang.String":
							consulta[i] = rs.getObject(i + 1);
							break;

						case "[B":
							consulta[i] = rs.getObject(i + 1);
							break;

						case "java.lang.Boolean":
							consulta[i] = rs.getObject(i + 1);
							break;

						case "java.sql.Date":
							consulta[i] = rs.getObject(i + 1);
							break;

						case "java.lang.Long":
							consulta[i] = rs.getObject(i + 1);
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

	public Matricula[] cargarMatriculas(String[] columnas, String[] busqueda, Object[] valores, int operador, int limit,
			int pagina, String orden, String ilikes, String columIlike, String ascdes) {
		int offset;

		Matricula[] cursos = new Matricula[limit];
		int contador = 0;
		String queryColumnas = "";
		String condicion = "";
		Object[] arrayConsulta = null;
		con = c.crearConexion();

		for (int i = 0; i < columnas.length; i++) {

			if ((i + 1) == columnas.length)
				queryColumnas += columnas[i];
			else
				queryColumnas += columnas[i] + ", ";

		}

		if (busqueda != null) {
			condicion = "WHERE ";
			for (int i = 0; i < busqueda.length; i++) {
				if ((i + 1) == busqueda.length)
					condicion += busqueda[i] + "=? ";
				else if (operador == 1)
					condicion += busqueda[i] + "=? AND ";
				else if (operador == 2)
					condicion += busqueda[i] + "=? OR ";
			}
		}

		if (ilikes != null) {
			if (busqueda == null)
				condicion = condicion + " WHERE " + columIlike + " ILIKE '%" + ilikes + "%'";
			else
				condicion = condicion + " ILIKE '%" + ilikes + "%'";
		}

		if (orden != null) {
			if (ascdes != null) {
				if (ascdes.equalsIgnoreCase("asc")) {
					condicion = condicion + " ORDER BY " + orden + " ASC ";
				}
				if (ascdes.equalsIgnoreCase("desc")) {
					condicion = condicion + " ORDER BY " + orden + " DESC ";
				}
			} else {
				condicion = condicion + " ORDER BY " + orden;
			}
		}

		offset = (limit * (pagina - 1));

		sql = "SELECT " + queryColumnas + " FROM platon.matricula " + condicion + " LIMIT " + limit + " OFFSET "
				+ offset;

		try {
			ps = con.prepareStatement(sql);

			if (busqueda != null) {
				for (int i = 0; i < busqueda.length; i++) {
					if (valores[i] == null)
						i++;
					if (valores[i] != null) {
						switch (valores[i].getClass().getName()) {
						case "java.lang.Integer":
							ps.setInt(i + 1, (Integer) valores[i]);
							break;

						case "java.lang.String":
							ps.setString(i + 1, (String) valores[i]);
							break;

						case "java.lang.Boolean":
							ps.setBoolean(i + 1, (Boolean) valores[i]);
							break;

						case "java.math.BigDecimal":
							ps.setBigDecimal(i + 1, (BigDecimal) valores[i]);
							break;

						case "java.lang.Long":
							ps.setLong(i + 1, (Long) valores[i]);
							break;
						default:
							System.out.println("(set) El tipo de dato de uno o más campos son/es inválidos");
							break;
						}
					} else
						System.out.println("(set) El valor de uno de los valores es null");
				}
			}
			rs = ps.executeQuery();

			while (rs.next()) {

				cursos[contador] = agregarValores(columnas);
				contador++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cursos;

	}

	public Matricula agregarValores(String[] columnasARecoger) throws SQLException {
		Matricula matricula = new Matricula();
		for (int i = 0; i < columnasARecoger.length; i++) {
			Object valor = rs.getObject(i + 1);
			if (valor == null)
				i++;
			else {
				switch (columnasARecoger[i].toLowerCase()) {
				case "idcurso":
					matricula.setIdCurso((int) valor);
					break;

				case "idmatricula":
					matricula.setIdMatricula((int) valor);
					break;

				case "idusuario":
					matricula.setIdUsuario((int) valor);
					break;

				default:
					System.out.println("El tipo de dato de uno o más campos son/es inválido/s num del ?: Curso " + i);
					break;
				}
			}
		}
		return matricula;
	}

	public boolean comprobarMatricula(int idUsuario, int idCurso) {
		con = c.crearConexion();
		boolean resultado = false;
		sql = "SELECT idMatricula FROM platon.matricula WHERE idUsuario = ? AND idCurso = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, idUsuario);
			ps.setInt(2, idCurso);

			rs = ps.executeQuery();

			if (rs.next()) {
				resultado = true;
			}

			rs.close();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultado;
	}

	public int getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(int idMatricula) {
		this.idMatricula = idMatricula;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public Date getFechaMatriculacion() {
		return fechaMatriculacion;
	}

	public void setFechaMatriculacion(Date fechaMatriculacion) {
		this.fechaMatriculacion = fechaMatriculacion;
	}

}
