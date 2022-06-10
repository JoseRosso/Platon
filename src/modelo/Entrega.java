package modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class Entrega {
	
	int idEntrega;
	byte[] entrega;
	int idUsuario;
	Date fechaSubida;
	
	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;
	
	public Entrega() {
		c = new Conexion();
	}
	
	public void insertarEntrega(byte[] entrega, int idUsuario, Date fechaSubida) {
		con = c.crearConexion();

		sql = "INSERT INTO platon.entrega VALUES (default,?,?,?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setBytes(1, entrega);
			ps.setInt(2, idUsuario);
			ps.setDate(3, fechaSubida);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarActividad(int idEntrega) {
		con = c.crearConexion();
		sql = "DELETE from platon.actividad WHERE idEntrega = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, idEntrega);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarEntrega(byte[] entrega, Date fechaSubida, int idEntrega) {
		con = c.crearConexion();
		sql = "UPDATE platon.actividad SET nombre= ?, descripcion = ? WHERE idEntrega = ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setBytes(1, entrega);
			ps.setDate(2, fechaSubida);
			ps.setInt(3, idEntrega);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
