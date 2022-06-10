package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;
import modelo.Material;
import modelo.Matricula;
import modelo.Usuario;

/**
 * Servlet implementation class VerMaterial
 */
@WebServlet("/VerMaterial")
public class VerMaterial extends HttpServlet {
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
		Matricula matricula;
		Material material;
		Curso curso;
		Object consultaUsuario[];
		Object consultaMatricula[];
		Object consultaMaterial[];

		int idCurso;
		int idUsuario;
		int totalMaterial;

		sesion = request.getSession();
		matricula = new Matricula();
		material = new Material();

		if (sesion.getAttribute("usuario") != null) {

			usuario = (Usuario) sesion.getAttribute("usuario");

			try {
				idCurso = (Integer.parseInt(request.getParameter("idCurso")));

				consultaUsuario = usuario.cargarUsuario(new String[] { "idUsuario" }, new String[] { "correo" },
						new Object[] { usuario.getCorreo() }, 1);

				idUsuario = (int) consultaUsuario[0];

				consultaMatricula = matricula.cargarMatricula(new String[] { "count(idMatricula)" },
						new String[] { "idCurso", "idUsuario" }, new Object[] { idCurso, idUsuario }, 1);

				if ((long) consultaMatricula[0] > 0) {

					totalMaterial = ((Long) material.cargarMaterial(new String[] { "count(idMaterial)" },
							new String[] { "idCurso" }, new Object[] { idCurso }, 1)[0]).intValue();

					consultaMaterial = material.cargarMateriales(
							new String[] { "idMaterial", "titulo", "descripcion", "idCurso", "archivo" },
							new String[] { "idCurso" }, new Object[] { idCurso }, 1, totalMaterial, 1, "titulo", null,
							null, "asc");

					sesion.setAttribute("Materiales", consultaMaterial);

					request.getRequestDispatcher("WEB-INF/VerMaterial.jsp").forward(request, response);
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("Cursos");
			}

		} else {
			response.sendRedirect("InicioSesion");
			sesion.setAttribute("AccesoDenegado", "Acceso denegado");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
