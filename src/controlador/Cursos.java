package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;

/**
 * Servlet implementation class Cursos
 */
@WebServlet("/Cursos")
public class Cursos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesion;
		Curso curso;
		Curso cursos[];
		int totalCursos;
		int paginasTotales;
		int pagina;
		int cursosPorPag;
		String orden;
		String busqueda;

		curso = new Curso();
		cursos = null;
		sesion = request.getSession();

		busqueda = request.getParameter("busqueda");

		paginasTotales = 1;

		// Comprobamos que los tipos de valores que nos llegan sean válidos
		try {
			cursosPorPag = Integer.parseInt(request.getParameter("cantidad"));
		} catch (Exception e) {
			cursosPorPag = 4;
		}

		try {
			pagina = Integer.parseInt(request.getParameter("pag"));
		} catch (Exception e) {
			pagina = 1;
		}

		// En caso de introducir una forma de ordenar inválida ordenaremos por idCurso
		if (request.getParameter("orden") == null
				|| !request.getParameter("orden").equals("precio") && !request.getParameter("orden").equals("titulo")) {
			orden = "idCurso";
		} else
			orden = request.getParameter("orden");

		// En caso de error o de introducir un valor no esperado seteamos la cantidad de
		// articulos por defecto a 2
		if (cursosPorPag != 4 && cursosPorPag != 8 && cursosPorPag != 12) {
			cursosPorPag = 4;
		}

		// Vemos cuantos cursos hay en total en la BD
		totalCursos = ((Long) curso.cargarCurso(new String[] { "count(idCurso)" }, null, null, 1)[0]).intValue();

		// Calculamos el total de páginas necesarias
		paginasTotales = (int) Math.ceil((double) totalCursos / (double) cursosPorPag);

		// Controlamos que el usuario no intente acceder a una pagina inexistente
		if (pagina > paginasTotales || pagina < 0 || pagina == 0)
			pagina = 1;

		sesion.setAttribute("paginasTotales", paginasTotales);

		// Cargamos los datos de los cursos
		cursos = curso.cargarCursos(
				new String[] { "idCurso", "titulo", "descripcion", "precio", "idCategoria", "imagen" }, null, null, 1,
				cursosPorPag, pagina, orden, busqueda, "titulo", null);

		sesion.setAttribute("cursos", cursos);

		request.getRequestDispatcher("WEB-INF/Cursos.jsp").forward(request, response);

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
