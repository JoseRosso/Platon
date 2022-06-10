package controlador;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;
import utilidades.FileToByte;

/**
 * Servlet implementation class PintarCurso
 */
@WebServlet("/PintarCurso")
public class PintarCurso extends HttpServlet {
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
		int idCurso;
		Object respuesta[];
		File imagenDefault;
		FileToByte ftb;
		sesion = request.getSession();

		curso = new Curso();
		respuesta = null;
		imagenDefault = new File("./WebContent/img/fondocurso.png");
		ftb = new FileToByte();

		idCurso = Integer.parseInt(request.getParameter("idCurso"));

		respuesta = curso.cargarCurso(new String[] { "imagen" }, new String[] { "idCurso" }, new Object[] { idCurso },
				1);
		try {

			response.setContentType("image/jpg");

			OutputStream out = response.getOutputStream();

			if (respuesta[0] != "") {
				out.write((byte[]) respuesta[0]);
			} else {
				out.write(ftb.convertFileToByteArray(imagenDefault));
			}

			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
