package controlador;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Material;
import modelo.Matricula;
import modelo.Usuario;

/**
 * Servlet implementation class DescargarArchivo
 */
@WebServlet("/DescargarArchivo")
public class DescargarArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesion;
		int idMaterial;
		Material material;
		Matricula matricula;
		Usuario usuario;
		Object consultaMaterial[];
		Object consultaMatricula[];
		Object consultaUsuario[];
		String extension;
		String titulo;
		byte[] archivo;
		int idCurso;
		int idUsuario;

		sesion = request.getSession();
		material = new Material();
		matricula = new Matricula();
		usuario = new Usuario();

		if (sesion.getAttribute("usuario") != null) {
			try {
				usuario = (Usuario) sesion.getAttribute("usuario");

				idMaterial = Integer.parseInt(request.getParameter("idMaterial"));

				consultaMaterial = material.cargarMaterial(new String[] { "extension", "archivo", "titulo", "idCurso" },
						new String[] { "idMaterial" }, new Object[] { idMaterial }, 1);

				extension = (String) consultaMaterial[0];

				archivo = (byte[]) consultaMaterial[1];

				titulo = (String) consultaMaterial[2];

				idCurso = (int) consultaMaterial[3];

				consultaUsuario = usuario.cargarUsuario(new String[] { "idUsuario" }, new String[] { "correo" },
						new Object[] { usuario.getCorreo() }, 1);

				idUsuario = (int) consultaUsuario[0];

				consultaMatricula = matricula.cargarMatricula(new String[] { "count(idMatricula)" },
						new String[] { "idCurso", "idUsuario" }, new Object[] { idCurso, idUsuario }, 1);

				if ((long) consultaMatricula[0] > 0) {
					// Tells the browser to output
					response.setHeader("Content-disposition",
							"inline;filename=" + titulo.replace(" ", "-") + extension);
					response.setContentType("application/" + extension.replace(".", ""));
					// Output image output stream
					OutputStream out = response.getOutputStream();
					// Output to the input of the buffer page
					out.write(archivo);
					// Input is completed, clear buffer
					out.flush();
					out.close();
				} else {
					response.sendRedirect("Cursos");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("Cursos");
			}
		} else {
			response.sendRedirect("Cursos");
		}
	}

}
