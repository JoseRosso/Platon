package modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class Categoria {

	int idCategoria;
	String nombre;
	String descripcion;

	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;

	public Categoria() {
		c = new Conexion();
	}

	public void insertarCategoria(String nombre, String descripcion) {
		con = c.crearConexion();

		sql = "INSERT INTO platon.categoria VALUES (default,?,?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, nombre);
			ps.setString(2, descripcion);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarCategoria(int idCategoria) {
		con = c.crearConexion();
		sql = "DELETE from platon.categoria WHERE idCategoria = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, idCategoria);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarCategoria(String nombre, String descripicion, int idCategoria) {
		con = c.crearConexion();
		sql = "UPDATE platon.categoria SET nombre= ?, descripcion = ? WHERE idCategoria = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, nombre);
			ps.setString(2, descripcion);
			ps.setInt(3, idCategoria);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[] cargarCategoria(String[] columnas, String[] busqueda, Object[] valores, int operador) {
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

		sql = "SELECT " + queryColumnas + " FROM platon.categoria " + condicion;

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
						default:
							System.out.println("(set) El tipo de dato de uno o más campos son/es inválidos"
									+ valores[i].getClass().getName());
							break;
						}
					} else
						System.out.println("El valor de uno de los valores es null");
				}
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
							System.out.println("(get) El tipo de dato de uno o más campos son inválidos"
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

	public Categoria[] cargarCategorias(String[] columnas, String[] busqueda, Object[] valores, int operador, int limit,
			int pagina, String orden, String ilikes, String columIlike) {

		int offset;
		Categoria[] categorias = new Categoria[limit];
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
			condicion = condicion + " ORDER BY " + orden;
		}

		offset = (limit * (pagina - 1));

		sql = "SELECT " + queryColumnas + " FROM platon.categoria " + condicion + " LIMIT " + limit + " OFFSET "
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
						default:
							System.out.println("(set) El tipo de dato de uno o más campos son/es inválidos"
									+ valores[i].getClass().getName());
							break;
						}
					} else
						System.out.println("El valor de uno de los valores es null");
				}
			}
			rs = ps.executeQuery();

			while (rs.next()) {

				categorias[contador] = agregarValores(columnas);
				contador++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categorias;

	}

	public Categoria agregarValores(String[] columnasARecoger) throws SQLException {
		Categoria categoria = new Categoria();
		for (int i = 0; i < columnasARecoger.length; i++) {
			Object valor = rs.getObject(i + 1);
			if (valor == null)
				i++;
			else {
				switch (columnasARecoger[i].toLowerCase()) {

				case "idcategoria":
					categoria.setIdCategoria((int) valor);
					break;
				case "nombre":
					categoria.setNombre((String) valor);
					break;
				case "descripcion":
					categoria.setDescripcion((String) valor);
					break;

				default:
					System.out.println("El tipo de dato de uno o más campos son/es inválido/s num del ?: Curso " + i);
					break;
				}
			}
		}
		return categoria;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
