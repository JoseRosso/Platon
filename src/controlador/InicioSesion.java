package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Usuario;
import utilidades.Encriptacion;

/**
 * Servlet implementation class Login
 */
@WebServlet("/InicioSesion")
public class InicioSesion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/InicioSesion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Object respuesta[];
		Usuario usuario;
		Encriptacion encriptar;

		HttpSession sesion;
		String correo;
		String password;
		String redireccion;

		sesion = request.getSession();
		usuario = new Usuario();
		encriptar = new Encriptacion();

		correo = request.getParameter("correo");
		password = encriptar.encriptarSha256(request.getParameter("password"));

		respuesta = respuesta = usuario.cargarUsuario(new String[] { "correo", "password" }, new String[] { "correo" },
				new Object[] { correo }, 1);

		if (correo.equals(respuesta[0]) && password.equals(respuesta[1])) {
			usuario.setCorreo((String) respuesta[0]);
			sesion.setAttribute("usuario", usuario);
			redireccion = "Inicio";
		} else {
			redireccion = "InicioSesion";
			sesion.setAttribute("DatosIncorrectos", "Compruebe su correo o cotraseña");
		}

		response.sendRedirect(redireccion);
	}
}
