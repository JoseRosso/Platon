package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class Entact {
	int idEntact;
	int idEntrega;
	int idActividad; 
	
	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;
	
	public Entact() {
		c = new Conexion();
	}
	
	public void insertarEntact(int idEntrega, int idActividad) {
		con = c.crearConexion();
		sql = "INSERT INTO platon.entact values(default,?,?)";

		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, idEntrega);
			ps.setInt(2, idActividad);
			

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getIdEntact() {
		return idEntact;
	}

	public void setIdEntact(int idEntact) {
		this.idEntact = idEntact;
	}

	public int getIdEntrega() {
		return idEntrega;
	}

	public void setIdEntrega(int idEntrega) {
		this.idEntrega = idEntrega;
	}

	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}
	
	
}
