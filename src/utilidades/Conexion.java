package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private Connection con;

	// METODO CONECTARSE A LA BASE DE DATOS
	public Connection crearConexion() {

		String url;
		String usuario;
		String password;

		// TODO cambiar
//		url = "jdbc:postgresql://localhost:5432/Jose";
//		usuario = "usuario";
//		password = "usuaria";

		url = "jdbc:postgresql://ns3034756.ip-91-121-81.eu:5432/a21_jrossa";
		usuario = "a21_jrossa";
		password = "a21_jrossa";

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, usuario, password);
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}// FIN METODO CONECTASE

}
