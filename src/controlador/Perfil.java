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
 * Servlet implementation class Perfil
 */
@WebServlet("/Perfil")
public class Perfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion;

		sesion = request.getSession();

		if (sesion.getAttribute("usuario") != null) {

			Usuario usuario;
			Object respuesta[];
			String correo;

			usuario = (Usuario) sesion.getAttribute("usuario");
			respuesta = null;
			correo = usuario.getCorreo();

			respuesta = usuario.cargarUsuario(new String[] { "idUsuario", "nombre", "correo", "dni", "imagen" },
					new String[] { "correo" }, new Object[] { correo }, 1);

			usuario.setIdUsuario((int) respuesta[0]);
			usuario.setNombre((String) respuesta[1]);
			usuario.setCorreo((String) respuesta[2]);
			usuario.setDni((String) respuesta[3]);
			if (respuesta[4] != null) {
				usuario.setImagen((byte[]) respuesta[4]);
			}

			sesion.setAttribute("usuario", usuario);

			request.getRequestDispatcher("WEB-INF/Perfil.jsp").forward(request, response);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
