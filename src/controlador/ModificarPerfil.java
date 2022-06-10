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

import modelo.Usuario;
import utilidades.FileToByte;
import utilidades.ValidacionDatos;

/**
 * Servlet implementation class ModificarPerfil
 */
@WebServlet("/ModificarPerfil")
public class ModificarPerfil extends HttpServlet {
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
		Object respuesta[];

		sesion = request.getSession();
		usuario = new Usuario();

		if (sesion.getAttribute("usuario") != null) {
			usuario = (Usuario) sesion.getAttribute("usuario");

			respuesta = usuario.cargarUsuario(new String[] { "idUsuario", "nombre", "correo", "dni", "imagen" },
					new String[] { "correo" }, new Object[] { usuario.getCorreo() }, 1);

			usuario.setIdUsuario((int) respuesta[0]);
			usuario.setNombre((String) respuesta[1]);
			usuario.setCorreo((String) respuesta[2]);
			usuario.setDni((String) respuesta[3]);
			if (respuesta[4] != null) {
				usuario.setImagen((byte[]) respuesta[4]);
			}

			request.getRequestDispatcher("WEB-INF/ModificarPerfil.jsp").forward(request, response);
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
		FileToByte ftb;
		ValidacionDatos validar;

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

		sesion = request.getSession();
		usuario = new Usuario();
		ftb = new FileToByte();
		validar = new ValidacionDatos();

		String redireccion = "";
		String nombre = "";
		String correoNuevo = "";
		String dni = "";
		byte[] imagen = null;
		boolean errorDatos = false;

		usuario = (Usuario) sesion.getAttribute("usuario");

		redireccion = "ModificarPerfil";

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

					if (fi.getFieldName().equals("nombre"))
						nombre = fi.getString("UTF-8");

					if (fi.getFieldName().equals("mail"))
						correoNuevo = fi.getString("UTF-8");

					if (fi.getFieldName().equals("dni"))
						dni = fi.getString("UTF-8");

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

		if (imagen == null)
			imagen = usuario.getImagen();

		if (!validar.validarNombre(nombre)) {
			sesion.setAttribute("NombreM", "Uno o más carácteres no están permitidos");
			errorDatos = true;
		}

		if (!validar.validarCorreo(correoNuevo)) {
			sesion.setAttribute("CorreoM", "Correo inválido");
			errorDatos = true;
		}

		if (!dni.equals("")) {
			if (!validar.validarDocumento(dni)) {
				sesion.setAttribute("dniM", "Formato de DNI inválido: NNNNNNNNL");
				errorDatos = true;
			}
		}

		if (!errorDatos) {
			usuario.modificarUsuario(nombre, correoNuevo, dni, imagen, usuario.getCorreo());

			usuario.setCorreo(correoNuevo);
			sesion.setAttribute("usuario", usuario);

			response.sendRedirect("Perfil");
		} else {
			response.sendRedirect("ModificarPerfil");
		}
	}

}
