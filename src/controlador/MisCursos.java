package controlador;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;
import modelo.Matricula;
import modelo.Usuario;

/**
 * Servlet implementation class MisCursos
 */
@WebServlet("/MisCursos")
public class MisCursos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sesion;
		Usuario usuario;
		Curso curso;
		Matricula matricula;
		Curso cursos[];
		Object consultaUsuario[];
		Object consultaMatricula[];
		Object consultaCurso[];
		int totalCursos;
		int totalCursosProfesor;
		int paginasTotales;
		int contador;

		int idUsuario;
		curso = new Curso();
		matricula = new Matricula();

		contador = 0;

		sesion = request.getSession();
		if (sesion.getAttribute("usuario") != null) {

			usuario = (Usuario) sesion.getAttribute("usuario");

			consultaUsuario = usuario.cargarUsuario(new String[] { "idUsuario" }, new String[] { "correo" },
					new Object[] { usuario.getCorreo() }, 1);

			idUsuario = (int) consultaUsuario[0];

			totalCursos = ((Long) matricula.cargarMatricula(new String[] { "count(idMatricula)" },
					new String[] { "idUsuario" }, new Object[] { idUsuario }, 1)[0]).intValue();

			cursos = new Curso[totalCursos];

			// paginasTotales = (int) Math.ceil((double) totalCursos / 8);

			consultaMatricula = matricula.cargarMatriculas(new String[] { "idCurso" }, new String[] { "idUsuario" },
					new Object[] { idUsuario }, 1, totalCursos, 1, null, null, null, null);

			for (Matricula mat : (Matricula[]) consultaMatricula) {
				Curso cursoFor = new Curso();
				consultaCurso = cursoFor.cargarCurso(new String[] { "idCurso", "titulo", "descripcion", "precio" },
						new String[] { "idCurso" }, new Object[] { mat.getIdCurso() }, 1);

				cursoFor.setIdCurso((int) consultaCurso[0]);
				cursoFor.setTitulo((String) consultaCurso[1]);
				cursoFor.setDescripcion((String) consultaCurso[2]);
				cursoFor.setPrecio((BigDecimal) consultaCurso[3]);

				cursos[contador] = cursoFor;
				contador++;
			}

			totalCursosProfesor = ((Long) curso.cargarCurso(new String[] { "count(idCurso)" },
					new String[] { "profesor" }, new Object[] { idUsuario }, 1)[0]).intValue();

			consultaCurso = curso.cargarCursos(new String[] { "idCurso", "titulo", "descripcion", "precio" },
					new String[] { "profesor" }, new Object[] { idUsuario }, 1, totalCursosProfesor, 1, null, null,
					null, null);

			sesion.setAttribute("Profesor", consultaCurso);

			sesion.setAttribute("MisCursos", cursos);
			request.getRequestDispatcher("WEB-INF/MisCursos.jsp").forward(request, response);
		} else {
			response.sendRedirect("InicioSesion");
			sesion.setAttribute("AccesoDenegado", "acceso denegado");
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
