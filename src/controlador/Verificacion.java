package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Usuario;

/**
 * Servlet implementation class Verificacion
 */
@WebServlet("/Verificacion")
public class Verificacion extends HttpServlet {
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
		Object respuesta[];
		boolean verificado;

		sesion = request.getSession();

		usuario = (Usuario) sesion.getAttribute("usuario");

		if (usuario != null) {
			respuesta = usuario.cargarUsuario(new String[] { "verificado" }, new String[] { "correo" },
					new Object[] { usuario.getCorreo() }, 1);

			verificado = (boolean) respuesta[0];

			if (!verificado) {
				request.getRequestDispatcher("WEB-INF/Verificacion.jsp").forward(request, response);
			} else {
				response.sendRedirect("Perfil");
			}

		} else {
			response.sendRedirect("InicioSesion");
			sesion.setAttribute("AccesoDenegado", "Inicie sesión para acceder");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String codverificacion;
		Object[] respuesta;
		HttpSession sesion;
		Usuario usuario;
		String correo;

		respuesta = null;
		sesion = request.getSession();

		usuario = (Usuario) sesion.getAttribute("usuario");

		correo = usuario.getCorreo();

		respuesta = usuario.cargarUsuario(new String[] { "codverificacion" }, new String[] { "correo" },
				new Object[] { usuario.getCorreo() }, 1);

		codverificacion = request.getParameter("codverificacion");

		if (respuesta[0].equals(codverificacion)) {
			sesion.setAttribute("verificado", "Te has verificado correctamente");
			usuario.modificarVerificacion(correo, true);
			usuario.setVerificado(true);
			sesion.setAttribute("usuario", usuario);
		} else
			sesion.setAttribute("verificado", "Error al verificarse");

		response.sendRedirect("Verificacion");
	}

}
