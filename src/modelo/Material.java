package modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class Material {

	int idMaterial;
	String titulo;
	String descripcion;
	byte[] archivo;
	int idCurso;
	String extension;

	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;

	public Material() {
		c = new Conexion();
	}

	public void insertarMaterial(String titulo, String descripcion, byte[] archivo, int idCurso, String extension) {
		con = c.crearConexion();

		sql = "INSERT INTO platon.material VALUES (default,?,?,?,?,?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, titulo);
			ps.setString(2, descripcion);
			ps.setBytes(3, archivo);
			ps.setInt(4, idCurso);
			ps.setString(5, extension);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarMaterial(int idMaterial) {
		con = c.crearConexion();
		sql = "DELETE from platon.material WHERE idMaterial = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, idMaterial);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarMaterial(String titulo, String descripcion, byte[] archivo, String extension, int idMaterial) {
		con = c.crearConexion();
		sql = "UPDATE platon.material SET titulo = ?, descripcion = ?, archivo = ?, extension = ? WHERE idMaterial= ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, titulo);
			ps.setString(2, descripcion);
			ps.setBytes(3, archivo);
			ps.setString(4, extension);
			ps.setInt(5, idMaterial);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[] cargarMaterial(String[] columnas, String[] busqueda, Object[] valores, int operador) {
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

		sql = "SELECT " + queryColumnas + " FROM platon.material WHERE " + condicion;

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
						System.out.println("(ps) El tipo de dato de uno o más campos son/es inválidos "
								+ valores[i].getClass().getName());
						break;
					}
				} else
					System.out
							.println("(ps) El valor de uno de los valores es null " + valores[i].getClass().getName());
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
							System.out.println("(rs) El tipo de dato de uno o más campos son inválidos "
									+ rs.getObject(i + 1).getClass().getName());
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

	public Material[] cargarMateriales(String[] columnas, String[] busqueda, Object[] valores, int operador, int limit,
			int pagina, String orden, String ilikes, String columIlike, String ascdes) {
		int offset;

		Material[] cursos = new Material[limit];
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

		sql = "SELECT " + queryColumnas + " FROM platon.material " + condicion + " LIMIT " + limit + " OFFSET "
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
							System.out.println("El tipo de dato de uno o más campos son/es inválidos");
							break;
						}
					} else
						System.out.println("El valor de uno de los valores es null");
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

	public Material agregarValores(String[] columnasARecoger) throws SQLException {
		Material material = new Material();
		for (int i = 0; i < columnasARecoger.length; i++) {
			Object valor = rs.getObject(i + 1);
			if (valor == null)
				i++;
			else {
				switch (columnasARecoger[i].toLowerCase()) {
				case "idmaterial":
					material.setIdMaterial((int) valor);
					break;

				case "titulo":
					material.setTitulo((String) valor);
					break;

				case "descripcion":
					material.setDescripcion((String) valor);
					break;

				case "archivo":
					material.setArchivo((byte[]) valor);
					break;

				case "idcurso":
					material.setIdCurso((int) valor);
					break;

				case "extension":
					material.setExtension((String) valor);

				default:
					System.out.println("El tipo de dato de uno o más campos son/es inválido/s num del ?: Curso " + i);
					break;
				}
			}
		}
		return material;
	}

	public int getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(int idMaterial) {
		this.idMaterial = idMaterial;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
