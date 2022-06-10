package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.MetodoPago;
import modelo.Usuario;
import utilidades.ValidacionDatos;

/**
 * Servlet implementation class AgregarMetodoPago
 */
@WebServlet("/AgregarMetodoPago")
public class AgregarMetodoPago extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion;
		Usuario usuario;
		Object consultaUsuario[];
		boolean verificado;

		sesion = request.getSession();
		usuario = new Usuario();

		verificado = false;
		usuario = (Usuario) sesion.getAttribute("usuario");

		if (usuario != null) {
			consultaUsuario = usuario.cargarUsuario(new String[] { "verificado" }, new String[] { "correo" },
					new Object[] { usuario.getCorreo() }, 1);

			verificado = (boolean) consultaUsuario[0];
		}

		if (verificado) {
			if (sesion.getAttribute("usuario") != null) {

				request.getRequestDispatcher("WEB-INF/AgregarMetodoPago.jsp").forward(request, response);

			} else {
				response.sendRedirect("InicioSesion");
				sesion.setAttribute("AccesoDenegado", "Es necesario iniciar sesión para adquirir un curso");
			}
		} else {
			response.sendRedirect("Verificacion");
			sesion.setAttribute("AccesoDenegado", "Es necesario estar verificado para adquirir un curso");

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesion;
		Usuario usuario;
		MetodoPago metodoPago;
		ValidacionDatos validacion;
		Object consultaUsuario[];

		String nombre;
		int cvv;
		int mesCaduca;
		int anoCaduca;
		int idUsuario;
		String numTarjeta;
		boolean errorDatos;

		sesion = request.getSession();
		usuario = new Usuario();
		metodoPago = new MetodoPago();
		validacion = new ValidacionDatos();

		nombre = "";
		cvv = 0;
		mesCaduca = 0;
		anoCaduca = 0;
		numTarjeta = "";
		errorDatos = false;
		idUsuario = 0;

		try {
			nombre = request.getParameter("nombre");
			cvv = Integer.parseInt(request.getParameter("cvv"));
			mesCaduca = Integer.parseInt(request.getParameter("mesCaduca"));
			anoCaduca = Integer.parseInt(request.getParameter("anoCaduca"));
			numTarjeta = request.getParameter("numTarjeta");

			usuario = (Usuario) sesion.getAttribute("usuario");

			consultaUsuario = usuario.cargarUsuario(new String[] { "idUsuario" }, new String[] { "correo" },
					new Object[] { usuario.getCorreo() }, 1);

			idUsuario = (int) consultaUsuario[0];
		} catch (Exception e) {
			sesion.setAttribute("FormatoInvalido", "formato invalido");
			response.sendRedirect("AgregarMetodoPago");
		}

		if (!validacion.validarCvv(String.valueOf(cvv))) {
			sesion.setAttribute("Cvv", "cvv inválido");
			errorDatos = true;
		}

		if (!validacion.validarAnio(String.valueOf(anoCaduca))) {
			sesion.setAttribute("anoCaduca", "anoCaduca inválido");
			errorDatos = true;
		}

		if (!validacion.validarNumTarjeta(numTarjeta)) {
			sesion.setAttribute("numTarjeta", "numTarjeta inválido");
			errorDatos = true;
		}

		if (!errorDatos) {
			metodoPago.insertarMetodoPago(numTarjeta, mesCaduca, anoCaduca, cvv, nombre, idUsuario);
			response.sendRedirect("AgregarMetodoPago");
			sesion.setAttribute("MetodoPago", "Se ha agregado correctamente");
		} else {
			response.sendRedirect("AgregarMetodoPago");
			sesion.setAttribute("ErrorMetodoPago", "Error");
		}

	}

}
