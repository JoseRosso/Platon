package modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class MetodoPago {

	int idMetodoPago;
	String numeroTarjeta;
	int mesCaduca;
	int anoCaduca;
	int cvv;
	String nombre;
	int idUsuario;

	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;

	public MetodoPago() {
		c = new Conexion();
	}

	public void insertarMetodoPago(String numTarjeta, int mesCaduca, int anoCaduca, int cvv, String nombre,
			int idUsuario) {

		con = c.crearConexion();
		sql = "INSERT INTO platon.metodoPago values(default,?,?,?,?,?,?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, numTarjeta);
			ps.setInt(2, mesCaduca);
			ps.setInt(3, anoCaduca);
			ps.setInt(4, cvv);
			ps.setString(5, nombre);
			ps.setInt(6, idUsuario);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarMetodoPago(int idMetodoPago) {
		con = c.crearConexion();

		sql = "DELETE FROM platon.metodoPago WHERE idMetodoPago = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, idMetodoPago);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarMetodoPago(int idMetodoPago, String numeroTarjeta, int mesCaduca, int anoCaduca, int cvv,
			String nombre, int idUsuario) {
		con = c.crearConexion();

		sql = "UPDATE platon.metodopago SET numeroTarjeta = ?, mesCaduca = ?, anoCaduca = ?, nombre = ?, idUsuario = ? WHERE idMetodoPago = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, numeroTarjeta);
			ps.setInt(2, mesCaduca);
			ps.setInt(3, anoCaduca);
			ps.setInt(4, cvv);
			ps.setString(5, nombre);
			ps.setInt(6, idUsuario);
			ps.setInt(7, idMetodoPago);

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[] cargarMetodoPago(String[] columnas, String[] busqueda, Object[] valores, int operador) {
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

		sql = "SELECT " + queryColumnas + " FROM platon.metodoPago " + condicion;

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

	public MetodoPago[] cargarMetodosPago(String[] columnas, String[] busqueda, Object[] valores, int operador,
			int limit, int pagina, String orden, String ilikes, String columIlike) {
		int offset;

		MetodoPago[] metodosPago = new MetodoPago[limit];
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

		sql = "SELECT " + queryColumnas + " FROM platon.metodoPago " + condicion + " LIMIT " + limit + " OFFSET "
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
							System.out.println("(get) El tipo de dato de uno o más campos son/es inválidos");
							break;
						}
					} else
						System.out.println("(get) El valor de uno de los valores es null");
				}
			}
			rs = ps.executeQuery();

			while (rs.next()) {

				metodosPago[contador] = agregarValores(columnas);
				contador++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return metodosPago;

	}

	public MetodoPago agregarValores(String[] columnasARecoger) throws SQLException {
		MetodoPago metodoPago = new MetodoPago();
		for (int i = 0; i < columnasARecoger.length; i++) {
			Object valor = rs.getObject(i + 1);
			if (valor == null)
				i++;
			else {
				switch (columnasARecoger[i].toLowerCase()) {
				case "idmetodopago":
					metodoPago.setIdMetodoPago((int) valor);
					break;

				case "numerotarjeta":
					metodoPago.setNumeroTarjeta((String) valor);
					break;

				case "mescaduca":
					metodoPago.setMesCaduca((int) valor);
					break;

				case "anocaduca":
					metodoPago.setAnoCaduca((int) valor);
					break;

				case "ccv":
					metodoPago.setCvv((int) valor);
					break;

				case "nombre":
					metodoPago.setNombre((String) valor);
					break;

				case "idusuario":
					metodoPago.setIdUsuario((int) valor);
					break;

				default:
					System.out.println(
							"(set) El tipo de dato de uno o más campos son/es inválido/s num del ?: Curso " + i);
					break;
				}
			}
		}
		return metodoPago;
	}

	public int getIdMetodoPago() {
		return idMetodoPago;
	}

	public void setIdMetodoPago(int idMetodoPago) {
		this.idMetodoPago = idMetodoPago;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public int getMesCaduca() {
		return mesCaduca;
	}

	public void setMesCaduca(int mesCaduca) {
		this.mesCaduca = mesCaduca;
	}

	public int getAnoCaduca() {
		return anoCaduca;
	}

	public void setAnoCaduca(int anoCaduca) {
		this.anoCaduca = anoCaduca;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

}
