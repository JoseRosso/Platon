package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class Usuario {

	int idUsuario;
	String nombre;
	String correo;
	String password;
	boolean verificado;
	String codVerificacion;
	String dni;
	byte[] imagen;

	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;

	public Usuario() {
		c = new Conexion();
	}

	public void insertarUsuario(String nombre, String correo, String password, boolean verificado,
			String codVerificacion, String dni, byte[] imagen) {

		con = c.crearConexion();
		sql = "INSERT INTO platon.usuario values(default,?,?,?,?,?,?,?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, nombre);
			ps.setString(2, correo);
			ps.setBoolean(3, verificado);
			ps.setString(4, codVerificacion);
			ps.setString(5, dni);
			ps.setBytes(6, imagen);
			ps.setString(7, password);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarUsuario(String correo) {
		con = c.crearConexion();
		sql = "DELETE from platon.usuario WHERE correo = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, correo);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarUsuario(String nombre, String correoNuevo, String dni, byte[] imagen, String correo) {
		con = c.crearConexion();
		sql = "UPDATE platon.usuario SET nombre = ?, correo = ?, dni = ?, imagen = ? WHERE correo = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setString(1, nombre);
			ps.setString(2, correoNuevo);
			ps.setString(3, dni);
			ps.setBytes(4, imagen);
			ps.setString(5, correo);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[] cargarUsuario(String[] columnas, String[] busqueda, Object[] valores, int operador) {
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

		sql = "SELECT " + queryColumnas + " FROM platon.usuario WHERE " + condicion;

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
						ps.setBoolean(i + 1, (Boolean) valores[i]);
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

	public void modificarVerificacion(String correo, boolean verificacion) {
		con = c.crearConexion();

		sql = "UPDATE platon.usuario SET verificado = ? WHERE correo = ?";

		try {

			ps = con.prepareStatement(sql);

			ps.setBoolean(1, verificacion);
			ps.setString(2, correo);

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarCodVerificacion(String correo, String codVerificacion) {
		con = c.crearConexion();

		sql = "UPDATE platon.usuario SET codVerificacion = ? WHERE correo = ?";

		try {

			ps = con.prepareStatement(sql);

			ps.setString(1, codVerificacion);
			ps.setString(2, correo);

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarPassword(String correo, String newPassword) {
		con = c.crearConexion();

		sql = "UPDATE platon.usuario SET password = ? WHERE correo = ?";

		try {

			ps = con.prepareStatement(sql);

			ps.setString(1, newPassword);
			ps.setString(2, correo);

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public String getCodVerificacion() {
		return codVerificacion;
	}

	public void setCodVerificacion(String codVerificacion) {
		this.codVerificacion = codVerificacion;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

}
