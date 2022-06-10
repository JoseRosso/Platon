package modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class Curso {

	int idCurso;
	BigDecimal precio;
	int estudiantes;
	String titulo;
	String descripcion;
	int idCategoria;
	int profesor; // Clave foranea que contiene el id del usuario que ha creado el curso
	byte[] imagen;

	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;

	public Curso() {
		c = new Conexion();
	}

	public void insertarCurso(BigDecimal precio, int estudiantes, String titulo, String descripcion, int idCategoria,
			int profesor, byte[] imagen) {

		con = c.crearConexion();
		sql = "INSERT INTO platon.curso values(default,?,?,?,?,?,?,?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setBigDecimal(1, precio);
			ps.setInt(2, estudiantes);
			ps.setString(3, titulo);
			ps.setString(4, descripcion);
			ps.setInt(5, idCategoria);
			ps.setInt(6, profesor);
			ps.setBytes(7, imagen);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarCurso(int idCurso) {
		con = c.crearConexion();
		sql = "DELETE from platon.curso WHERE idCurso = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, idCurso);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarCurso(int idCurso, double precio, String titulo, String descripcion) {
		con = c.crearConexion();
		sql = "UPDATE platon.usuario SET precio = ?, titulo = ?, descripcion = ? WHERE idCurso = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setDouble(1, precio);
			ps.setString(2, titulo);
			ps.setString(3, descripcion);
			ps.setInt(4, idCurso);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[] cargarCurso(String[] columnas, String[] busqueda, Object[] valores, int operador) {
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
		if (busqueda != null) {
			condicion = "WHERE ";
			for (int i = 0; i < busqueda.length; i++) {
				if ((i + 1) == busqueda.length)
					condicion += busqueda[i] + "=?";
				else if (operador == 1)
					condicion += busqueda[i] + "=? AND";
				else if (operador == 2)
					condicion += busqueda[i] + "=? OR";
			}
		}

		sql = "SELECT " + queryColumnas + " FROM platon.curso " + condicion;

		// Hacemos la precompilacion de la query y añadimos los parametros de busqueda
		try {
			ps = con.prepareStatement(sql);
			if (busqueda != null) {
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
							consulta[i] = rs.getObject(i + 1);
							break;

						case "java.sql.Date":
							consulta[i] = rs.getObject(i + 1);
							break;

						case "java.math.BigDecimal":
							consulta[i] = rs.getObject(i + 1);
							break;

						case "java.lang.Double":
							consulta[i] = rs.getObject(i + 1);
							break;
						default:
							System.out.println("(get) El tipo de dato de uno o más campos son/es inválidos");
							break;
						}
					} else
						System.out.println("(get) El valor de uno de los valores es null");
				}
			}

			// Obtenemos los ResultSet
			rs = ps.executeQuery();
			consulta = new Object[columnas.length];

			if (rs.next()) {
				for (int i = 0; i < consulta.length; i++) {
					if (rs.getObject(i + 1) == null)
						consulta[i] = "";
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

						case "java.math.BigDecimal":
							consulta[i] = rs.getObject(i + 1);
							break;

						case "java.lang.Double":
							consulta[i] = BigDecimal.valueOf((double) rs.getObject(i + 1));
							break;
						default:
							System.out.println("(set) El tipo de dato de uno o más campos son inválidos");
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

	public Curso[] cargarCursos(String[] columnas, String[] busqueda, Object[] valores, int operador, int limit,
			int pagina, String orden, String ilikes, String columIlike, String ascdes) {
		int offset;

		Curso[] cursos = new Curso[limit];
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

		sql = "SELECT " + queryColumnas + " FROM platon.curso " + condicion + " LIMIT " + limit + " OFFSET " + offset;

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

	public Curso agregarValores(String[] columnasARecoger) throws SQLException {
		Curso Curso = new Curso();
		for (int i = 0; i < columnasARecoger.length; i++) {
			Object valor = rs.getObject(i + 1);
			if (valor == null)
				i++;
			else {
				switch (columnasARecoger[i].toLowerCase()) {
				case "idcurso":
					Curso.setIdCurso((int) valor);
					break;

				case "precio":
					Curso.setPrecio(BigDecimal.valueOf((double) (valor)));
					break;

				case "estudiantes":
					Curso.setEstudiantes((int) valor);
					break;

				case "titulo":
					Curso.setTitulo((String) valor);
					break;

				case "descripcion":
					Curso.setDescripcion((String) valor);
					break;

				case "idcategoria":
					Curso.setIdCategoria((int) valor);
					break;

				case "profesor":
					Curso.setProfesor((int) valor);
					break;

				case "imagen":
					Curso.setImagen((byte[]) valor);
					break;

				default:
					System.out.println("El tipo de dato de uno o más campos son/es inválido/s num del ?: Curso " + i);
					break;
				}
			}
		}
		return Curso;
	}

	public void aumentarAlumnos(int idCurso, int cantidad) {
		con = c.crearConexion();
		sql = "UPDATE platon.curso SET estudiantes = ? WHERE idCurso = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, cantidad);
			ps.setInt(2, idCurso);

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public int getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(int estudiantes) {
		this.estudiantes = estudiantes;
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

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getProfesor() {
		return profesor;
	}

	public void setProfesor(int profesor) {
		this.profesor = profesor;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

}
