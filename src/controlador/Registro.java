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
import utilidades.Encriptacion;
import utilidades.EnviarCorreo;
import utilidades.FileToByte;
import utilidades.ValidacionDatos;

/**
 * Servlet implementation class Registro
 */
@WebServlet("/Registro")
public class Registro extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/Registro.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Usuario usuario;
		Encriptacion encriptar;
		HttpSession sesion;
		EnviarCorreo envioCorreo;
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

		String redireccion = "";
		String nombre = "";
		String correo = "";
		String password = "";
		String veriPass = "";
		String dni = "";
		byte[] imagen = null;
		String codVerificacion = "";
		boolean errorDatos = false;
		int totalUsuarios;

		usuario = new Usuario();
		encriptar = new Encriptacion();
		envioCorreo = new EnviarCorreo();
		ftb = new FileToByte();
		validar = new ValidacionDatos();
		sesion = request.getSession();

		redireccion = "RegistroCliente";

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
						correo = fi.getString("UTF-8");

					if (fi.getFieldName().equals("password"))
						password = encriptar.encriptarSha256(fi.getString("UTF-8"));

					if (fi.getFieldName().equals("veriPass"))
						veriPass = encriptar.encriptarSha256(fi.getString("UTF-8"));

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

		if (!veriPass.equals(password)) {
			sesion.setAttribute("Password", "No coinciden las contraseñas");
			errorDatos = true;
		}

		if (!validar.validarNombre(nombre)) {
			sesion.setAttribute("Nombre", "Uno o más carácteres no están permitidos");
			errorDatos = true;
		}

		if (!validar.validarCorreo(correo)) {
			sesion.setAttribute("Correo", "Correo inválido");
			errorDatos = true;
		}

		if (!dni.equals("")) {
			if (!validar.validarDocumento(dni)) {
				sesion.setAttribute("dni", "Formato de DNI inválido: NNNNNNNNL");
				errorDatos = true;
			}
		}

		totalUsuarios = ((Long) usuario.cargarUsuario(new String[] { "count(idUsuario)" }, new String[] { "correo" },
				new Object[] { correo }, 1)[0]).intValue();

		if (totalUsuarios > 0) {
			sesion.setAttribute("Correo", "Correo inválido");
			errorDatos = true;
		}

		if (!errorDatos) {

			codVerificacion = String.valueOf(Math.round(Math.random() * 99999));

			usuario.insertarUsuario(nombre, correo, veriPass, false, codVerificacion, dni, imagen);

			envioCorreo.enviar(correo, "Bienvenido a Platón",
					"Aquí tienes tu código de verificación:" + codVerificacion);

			usuario.setCorreo(correo);
			sesion.setAttribute("usuario", usuario);

			redireccion = "Verificacion";
		} else {
			redireccion = "Registro";
		}

		response.sendRedirect(redireccion);

	}

}
