package modelo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilidades.Conexion;

public class Factura {
	int idFactura;
	int idMetodoPago;
	int idCurso;
	Date fechaFacturacion;
	BigDecimal importe;

	String sql;
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	Conexion c;

	public Factura() {
		c = new Conexion();
	}

	public void insertarFactura(int idMetodoPago, int idCurso, Date fechaFacturacion, BigDecimal importe) {
		con = c.crearConexion();

		sql = "INSERT INTO platon.factura VALUES (default,?,?,?,?)";

		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, idMetodoPago);
			ps.setInt(2, idCurso);
			ps.setDate(3, fechaFacturacion);
			ps.setBigDecimal(4, importe);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void eliminarFactura(int idFactura) {
		con = c.crearConexion();
		sql = "DELETE from platon.factura WHERE idFactura = ?";

		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, idFactura);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modificarFactura(int idMetodoPago, int idCurso, Date fechaFacturacion, BigDecimal importe,
			int idFactura) {
		con = c.crearConexion();
		sql = "UPDATE platon.factura SET idMetodoPago = ?, idCurso = ?, fechaFacturacion = ?, importe = ? WHERE idFactura= ?";

		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, idMetodoPago);
			ps.setInt(2, idCurso);
			ps.setDate(3, fechaFacturacion);
			ps.setBigDecimal(4, importe);
			ps.setInt(5, idFactura);

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[] cargarFactura(String[] columnas, String[] busqueda, Object[] valores, int operador) {
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

		sql = "SELECT " + queryColumnas + " FROM platon.factura WHERE " + condicion;

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

	
	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public int getIdMetodoPago() {
		return idMetodoPago;
	}

	public void setIdMetodoPago(int idMetodoPago) {
		this.idMetodoPago = idMetodoPago;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

}
