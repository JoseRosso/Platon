package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Usuario;
import utilidades.EnviarCorreo;

/**
 * Servlet implementation class ReenviarVerificacion
 */
@WebServlet("/ReenviarVerificacion")
public class ReenviarVerificacion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession sesion;
		Usuario usuario;
		Object consultaUsuario[];
		EnviarCorreo envio;
		String correo;
		String codVerificacion;

		sesion = request.getSession();
		envio = new EnviarCorreo();
		usuario = new Usuario();

		correo = "";

		if (sesion.getAttribute("usuario") != null) {

			usuario = (Usuario) sesion.getAttribute("usuario");

			correo = usuario.getCorreo();

			codVerificacion = String.valueOf(Math.round(Math.random() * 99999));

			usuario.modificarCodVerificacion(correo, codVerificacion);

			envio.enviar(correo, "Su nuevo código de verificación",
					"Aquí tienes su nuevo código de verificación:" + codVerificacion);

			sesion.setAttribute("NuevaVerificacion", "Se ha enviado con éxito");

			response.sendRedirect("Verificacion");

		} else {
			response.sendRedirect("InicioSesion");
			sesion.setAttribute("AccesoDenegado", "No puedes acceder");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
