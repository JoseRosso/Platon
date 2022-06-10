package controlador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

import modelo.Curso;
import modelo.Material;
import modelo.Usuario;
import utilidades.FileExtension;
import utilidades.FileToByte;

/**
 * Servlet implementation class AgregarMaterial
 */
@WebServlet("/AgregarMaterial")
public class AgregarMaterial extends HttpServlet {
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
		Curso curso;
		Object consultarCurso[];
		Object consultarUsuario[];

		int idCurso;
		int idProfesor;
		int idUsuario;

		sesion = request.getSession();
		curso = new Curso();

		if (sesion.getAttribute("usuario") != null) {
			usuario = (Usuario) (sesion.getAttribute("usuario"));
			try {
				idCurso = Integer.parseInt(request.getParameter("idCurso"));

				consultarCurso = curso.cargarCurso(new String[] { "profesor" }, new String[] { "idCurso" },
						new Object[] { idCurso }, 1);

				idProfesor = (int) consultarCurso[0];

				consultarUsuario = usuario.cargarUsuario(new String[] { "idUsuario" }, new String[] { "correo" },
						new Object[] { usuario.getCorreo() }, 1);

				idUsuario = (int) consultarUsuario[0];

				// Comprobamos que quien intenta agregar material al curso sea el profesor
				if (idProfesor == idUsuario) {
					request.getRequestDispatcher("WEB-INF/AgregarMaterial.jsp").forward(request, response);
				} else {
					response.sendRedirect("InfoCurso");
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("MisCursos");
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

		servletContext = getServletContext();
		filePath = this.getServletContext().getRealPath("/WEB-INF/data/");
		out = response.getWriter();
		contentType = request.getContentType();

		HttpSession sesion;
		Material material;
		Curso curso;
		Object consultaUsuario[];
		Object consultaCurso[];
		FileToByte ftb;
		FileExtension fileExtension;

		int idCurso;
		String titulo;
		String descripcion;
		byte[] archivo;
		String extension;

		sesion = request.getSession();
		material = new Material();
		curso = new Curso();
		ftb = new FileToByte();
		fileExtension = new FileExtension();

		titulo = "";
		descripcion = "";
		archivo = null;
		extension = "";

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

					if (!fi.isFormField()) {
						fieldName = fi.getFieldName();
						fileName = fi.getName();
						isInMemory = fi.isInMemory();
						sizeInBytes = fi.getSize();

						if (sizeInBytes < 10000000) {

							if (fileName.lastIndexOf("/") >= 0) {
								file = new File(filePath + fileName.substring(fileName.lastIndexOf("/")));
							} else
								file = new File(filePath + "/" + fileName.substring(fileName.lastIndexOf("/") + 1));

							fi.write(file);

							extension = fileExtension.getFileExtension(fileName);
							archivo = ftb.convertFileToByteArray(file);

							file.delete();

							sesion.setAttribute("ExitoSubida", "Subida correctamente");
						} else {
							response.sendRedirect(request.getHeader("Referer"));
							sesion.setAttribute("ArchivoPesado", "El archivo es demasiado pesado");
						}
					} // fin if isFormField
				} // fin while

			} catch (Exception ex) {
				ex.getStackTrace();
				response.sendRedirect(request.getHeader("Referer"));
				sesion.setAttribute("FalloArchivo", "Ha ocurrido un error al procesar el archivo ");
			}
		} // fin del if multipart

		idCurso = Integer.parseInt(request.getParameter("idCurso"));

		material.insertarMaterial(titulo, descripcion, archivo, idCurso, extension);

		response.sendRedirect(request.getHeader("Referer"));
	}

}
