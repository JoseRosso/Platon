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

/**
 * Servlet implementation class InfoCurso
 */
@WebServlet("/InfoCurso")
public class InfoCurso extends HttpServlet {
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
		Object respuesta[];
		int idCurso;

		sesion = request.getSession();
		curso = new Curso();
		respuesta = null;

		try {

			idCurso = Integer.parseInt(request.getParameter("idCurso"));

			respuesta = curso.cargarCurso(new String[] { "idCurso", "titulo", "precio", "descripcion", "idCategoria",
					"profesor", "estudiantes" }, new String[] { "idCurso" }, new Object[] { idCurso }, 1);

			curso.setIdCurso((int) respuesta[0]);
			curso.setTitulo((String) respuesta[1]);
			curso.setPrecio((BigDecimal) respuesta[2]);
			curso.setDescripcion((String) respuesta[3]);
			curso.setIdCategoria((int) respuesta[4]);
			curso.setProfesor((int) respuesta[5]);
			curso.setEstudiantes((int) respuesta[6]);

			sesion.setAttribute("InfoCurso", curso);

			request.getRequestDispatcher("WEB-INF/InfoCurso.jsp").forward(request, response);
		} catch (Exception e) {
			response.sendRedirect("Cursos");
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
