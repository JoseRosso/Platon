package controlador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import modelo.Categoria;
import modelo.Curso;
import modelo.Usuario;
import utilidades.EnviarCorreo;
import utilidades.FileToByte;
import utilidades.ValidacionDatos;

/**
 * Servlet implementation class CrearCurso
 */
@WebServlet("/CrearCurso")
public class CrearCurso extends HttpServlet {
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
		Categoria categoria;
		Object repuestaUsuario[];
		Object respuestaCategoria[];
		int categoriasTotal;
		boolean verificado;

		usuario = new Usuario();
		categoria = new Categoria();
		sesion = request.getSession();

		verificado = false;
		repuestaUsuario = null;

		if (sesion.getAttribute("usuario") != null) {

			usuario = (Usuario) sesion.getAttribute("usuario");

			categoriasTotal = ((Long) categoria.cargarCategoria(new String[] { "count(idCategoria)" }, null, null,
					1)[0]).intValue();

			respuestaCategoria = categoria.cargarCategorias(new String[] { "idCategoria", "nombre" }, null, null, 1,
					categoriasTotal, 1, null, null, "idCategoria");

			sesion.setAttribute("categorias", respuestaCategoria);

			repuestaUsuario = usuario.cargarUsuario(new String[] { "verificado" }, new String[] { "correo" },
					new Object[] { usuario.getCorreo() }, 1);

			verificado = (boolean) repuestaUsuario[0];

			if (verificado) {
				request.getRequestDispatcher("WEB-INF/CrearCurso.jsp").forward(request, response);
			} else {
				response.sendRedirect("Verificacion");
				sesion.setAttribute("AccesoDenegado", "Verifiquese para poder crear cursos");
			}
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

		HttpSession sesion;
		Usuario usuario;
		int idUsuario;
		FileToByte ftb;
		Curso curso;
		ValidacionDatos validacion;
		EnviarCorreo enviar;
		Object respuestaUsuario[];

		File file = null;
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;
		ServletContext servletContext;
		String filePath;
		String contentType;
		PrintWriter out;
		FileItem fi;
		List<FileItem> fileItems;
		DiskFileItemFactory factory;
		String fileName = null;
		boolean isInMemory;
		String fieldName;
		long sizeInBytes;

		String titulo;
		String descripcion;
		BigDecimal precio;
		int idCategoria;
		byte[] imagen;
		boolean errorDatos;

		sesion = request.getSession();
		usuario = new Usuario();
		validacion = new ValidacionDatos();
		curso = new Curso();
		enviar = new EnviarCorreo();
		ftb = new FileToByte();

		titulo = "";
		descripcion = "";
		precio = new BigDecimal(0);
		idCategoria = 0;
		imagen = null;
		errorDatos = false;

		usuario = (Usuario) sesion.getAttribute("usuario");

		respuestaUsuario = usuario.cargarUsuario(new String[] { "idUsuario" }, new String[] { "correo" },
				new Object[] { usuario.getCorreo() }, 1);

		idUsuario = (int) respuestaUsuario[0];

		servletContext = getServletContext();
		filePath = this.getServletContext().getRealPath("/WEB-INF/data/");
		out = response.getWriter();
		contentType = request.getContentType();

		if ((contentType.indexOf("multipart/form-data") >= 0)) {

			factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
			factory.setRepository(new File("."));

			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setSizeMax(maxFileSize);

			try {
				fileItems = upload.parseRequest(request);
				Iterator<FileItem> i = fileItems.iterator();

				while (i.hasNext()) {
					fi = i.next();

					if (fi.getFieldName().equals("titulo"))
						titulo = fi.getString("UTF-8");

					if (fi.getFieldName().equals("descripcion"))
						descripcion = fi.getString("UTF-8");

					if (fi.getFieldName().equals("precio"))
						precio = BigDecimal.valueOf(Double.parseDouble((fi.getString("UTF-8"))));

					if (fi.getFieldName().equals("categoria"))
						idCategoria = Integer.parseInt(fi.getString("UTF-8"));

					if (!fi.isFormField()) {
						fieldName = fi.getFieldName();
						fileName = fi.getName();
						isInMemory = fi.isInMemory();
						sizeInBytes = fi.getSize();

						if (fileName.lastIndexOf("/") >= 0) {
							file = new File(filePath + fileName.substring(fileName.lastIndexOf("/")));
						} else
							file = new File(filePath + "/" + fileName.substring(fileName.lastIndexOf("/") + 1));

						fi.write(file);

						imagen = ftb.convertFileToByteArray(file);

						file.delete();
					} // fin if isFormField
				} // fin while

			} catch (Exception ex) {
				ex.getStackTrace();
			}
		} // fin del if multipart

		if (!validacion.validarPrecio(String.valueOf(precio))) {
			errorDatos = true;
			sesion.setAttribute("Precio", precio);
		}

		if (!errorDatos) {
			curso.insertarCurso(precio, 0, titulo, descripcion, idCategoria, idUsuario, imagen);

			enviar.enviar(usuario.getCorreo(), "Curso creado",
					"Su curso " + titulo + " se ha creado correctamente. ¡Gracias por confiar en Platón!");

			sesion.setAttribute("CursoCreado", "curso creado con éxito");
			response.sendRedirect("CrearCurso");
		} else {
			sesion.setAttribute("ErrorCurso", "Curso no creado");
			response.sendRedirect("CrearCurso");
		}
	}

}
