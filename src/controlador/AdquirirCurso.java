package controlador;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;
import modelo.Factura;
import modelo.Matricula;
import modelo.MetodoPago;
import modelo.Usuario;
import utilidades.EnviarCorreo;

/**
 * Servlet implementation class AdquirirCurso
 */
@WebServlet("/AdquirirCurso")
public class AdquirirCurso extends HttpServlet {
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
		Usuario usuario;
		Matricula matricula;
		MetodoPago metodPago;
		MetodoPago metodosPago[];
		Object consultaCurso[];
		Object consultaUsuario[];
		boolean matriculado;
		int idCurso;
		int idUsuario;
		int idMetodoPago;
		int totalMetodos;
		boolean verificado;

		sesion = request.getSession();
		curso = new Curso();
		metodPago = new MetodoPago();
		matricula = new Matricula();
		verificado = false;
		matriculado = false;
		idUsuario = 0;

		usuario = (Usuario) sesion.getAttribute("usuario");
		curso = (Curso) sesion.getAttribute("InfoCurso");

		if (usuario != null) {
			consultaUsuario = usuario.cargarUsuario(new String[] { "verificado", "idUsuario" },
					new String[] { "correo" }, new Object[] { usuario.getCorreo() }, 1);

			verificado = (boolean) consultaUsuario[0];
			idUsuario = (int) consultaUsuario[1];
			try {

				totalMetodos = ((Long) metodPago.cargarMetodoPago(new String[] { "count(idMetodoPago)" },
						new String[] { "idUsuario" }, new Object[] { idUsuario }, 1)[0]).intValue();

				metodosPago = metodPago.cargarMetodosPago(
						new String[] { "idMetodoPago", "numeroTarjeta", "mesCaduca", "anoCaduca", "ccv", "nombre" },
						new String[] { "idUsuario" }, new Object[] { idUsuario }, 1, totalMetodos, 1, null, null,
						"idMetodoPago");
				if (totalMetodos != 0) {
					sesion.setAttribute("MetodosPago", metodosPago);
				} else {
					sesion.setAttribute("MetodosPago", null);
				}
			} catch (Exception e) {
				sesion.setAttribute("MetodosPago", null);
			}

		}

		// Comprobamos si esta verificado en caso de no estar verificado le mandamos al
		// login
		if (verificado) {
			if (sesion.getAttribute("usuario") != null) {
				if (sesion.getAttribute("InfoCurso") != null) {
					idCurso = curso.getIdCurso();
					// Comprobamos si es el profesor del curso
					if (idUsuario == curso.getProfesor()) {
						response.sendRedirect("MisCursos");
					} else {
						// Comprobamos si esta ya matriculado
						matriculado = matricula.comprobarMatricula(idUsuario, curso.getIdCurso());
						if (!matriculado) {
							request.getRequestDispatcher("WEB-INF/AdquirirCurso.jsp").forward(request, response);
						} else {
							response.sendRedirect("Cursos");
							sesion.setAttribute("Matriculado", "Ya estas matriculado");
						}
					}
				} else {
					response.sendRedirect("Cursos");
				}
			} else {
				response.sendRedirect("InicioSesion");
				sesion.setAttribute("AccesoDenegado", "Es necesario iniciar sesión para adquirir un curso");
			}
		} else {
			response.sendRedirect("Verificacion");
			sesion.setAttribute("AccesoDenegado", "Es necesario estar verificado para adquirir un curso");
		}

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
		Curso curso;
		Factura factura;
		Matricula matricula;
		MetodoPago metodPago;
		Object consultaUsuario[];
		Object consultaCurso[];
		Date fechaMatriculacion;
		java.sql.Date fechaMatriculacionSQL;
		EnviarCorreo envio;

		String tarjeta;
		long timeInMilliSeconds;
		int idUsuario;
		int totalAlumnos;

		sesion = request.getSession();
		curso = new Curso();
		factura = new Factura();
		matricula = new Matricula();
		metodPago = new MetodoPago();
		envio = new EnviarCorreo();
		fechaMatriculacion = new Date();

		timeInMilliSeconds = fechaMatriculacion.getTime();
		fechaMatriculacionSQL = new java.sql.Date(timeInMilliSeconds);

		tarjeta = request.getParameter("tarjeta");

		usuario = (Usuario) sesion.getAttribute("usuario");
		curso = (Curso) sesion.getAttribute("InfoCurso");

		consultaUsuario = usuario.cargarUsuario(new String[] { "idUsuario" }, new String[] { "correo" },
				new Object[] { usuario.getCorreo() }, 1);

		totalAlumnos = curso.getEstudiantes() + 1;

		curso.aumentarAlumnos(curso.getIdCurso(), totalAlumnos);

		idUsuario = (int) consultaUsuario[0];

		matricula.insertarMatricula(idUsuario, curso.getIdCurso(), fechaMatriculacionSQL);

		factura.insertarFactura(Integer.parseInt(tarjeta), curso.getIdCurso(), fechaMatriculacionSQL,
				curso.getPrecio());

		envio.enviar(usuario.getCorreo(), "¡Te has matriculado en un nuevo curso!",
				"Te has matriculado correctamente en: " + curso.getTitulo() + ". ¡Gracias por confiar en Platón!");

		response.sendRedirect("Cursos");
	}

}
