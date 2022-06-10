package etiqueta;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import modelo.Usuario;

public class EtiPerfil extends SimpleTagSupport {

	Usuario usuario;
	int idUsuario;
	String nombre;
	String correo;
	String dni;
	byte[] imagen;
	Object resultado[];

	@Override
	public void doTag() throws JspException, IOException {
		usuario = new Usuario();

		resultado = usuario.cargarUsuario(new String[] { "imagen" }, new String[] { "idUsuario" },
				new Object[] { getIdUsuario() }, 1);

		JspWriter out = getJspContext().getOut();

		out.print("<h3>Bienvenid@ " + getNombre() + "</h3>");

		if (resultado[0] != null) {
			out.print("<img class='imagen-perfil' src ='PintarPerfil'>");
		} else {
			out.print("<img class='imagen-perfil' src='./img/defaultPerfil.png'>");
		}
		out.print("<div>Nombre: " + getNombre() + "</div>");
		out.print("<div>Correo: " + getCorreo() + "</div>");
		out.print("<div>DNI: " + getDni() + "</div>");
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

}
