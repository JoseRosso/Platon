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
import utilidades.EnviarCorreo;

/**
 * Servlet implementation class RecuperarCuenta
 */
@WebServlet("/RecuperarCuenta")
public class RecuperarCuenta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("WEB-INF/RecuperarCuenta.jsp").forward(request, response);
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
		EnviarCorreo envio;
		Encriptacion encriptacion;
		Object consultaUsuario[];
		String correo;
		String nuevaPassword;
		int indice;
		String caracteres;

		sesion = request.getSession();
		usuario = new Usuario();
		envio = new EnviarCorreo();
		encriptacion = new Encriptacion();
		consultaUsuario = null;
		nuevaPassword = "";
		caracteres = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

		correo = request.getParameter("correo");

		consultaUsuario = usuario.cargarUsuario(new String[] { "correo" }, new String[] { "correo" },
				new Object[] { correo }, 1);

		if (((String) consultaUsuario[0]).equals(correo)) {

			for (int i = 0; i < 15; i++) {
				indice = (int) Math.round(Math.random() * 62);
				nuevaPassword += caracteres.charAt(indice);
			}

			System.out.println(nuevaPassword);
			envio.enviar(correo, "Su nueva contrase??a",
					"Se ha cambiado la contrase??a con ??xito. Su nueva contrase??a es: " + nuevaPassword);

			nuevaPassword = encriptacion.encriptarSha256(nuevaPassword);

			usuario.modificarPassword(correo, nuevaPassword);
			response.sendRedirect("RecuperarCuenta");
			sesion.setAttribute("CambioPassword",
					"En caso de que exista su correo se enviar?? uno con su nueva contrase??a");
		} else {
			response.sendRedirect("RecuperarCuenta");
			sesion.setAttribute("CambioPassword",
					"En caso de que exista su correo se enviar?? uno con su nueva contrase??a");
		}

	}

}
