package etiqueta;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import modelo.Categoria;
import modelo.Curso;
import modelo.Usuario;

public class EtiInfoCurso extends SimpleTagSupport {

	HttpSession sesion;
	Curso curso;
	Categoria categoria;
	Usuario usuario;
	int idCurso;
	BigDecimal precio;
	String titulo;
	String descripcion;
	int idCategoria;
	int profesor;
	Object consultaCategoria[];
	Object consultaUsuario[];

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		curso = new Curso();
		categoria = new Categoria();
		usuario = new Usuario();

		consultaCategoria = categoria.cargarCategoria(new String[] { "nombre" }, new String[] { "idCategoria" },
				new Object[] { idCategoria }, 1);

		consultaUsuario = usuario.cargarUsuario(new String[] { "nombre" }, new String[] { "idUsuario" },
				new Object[] { profesor }, 1);

		out.print("<img width='200' height='200' src ='PintarCurso?idCurso=" + getIdCurso() + "'>");

		out.print("<p class='pt-3'>Categoría: " + consultaCategoria[0] + "</p>");
		out.print("<p>Titulo: " + getTitulo() + "</p>");
		out.print("<p>Descripción: " + getDescripcion() + "</p>");
		out.print("<p>Precio: " + getPrecio() + "€</p>");
		out.print("<p>Profesor: " + consultaUsuario[0] + "</p>");

		out.print("<a  href='AdquirirCurso?idCurso=" + getIdCurso() + "'class='btn button-estandar'>¡Apúntate ya!</a>");
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getProfesor() {
		return profesor;
	}

	public void setProfesor(int profesor) {
		this.profesor = profesor;
	}
}
