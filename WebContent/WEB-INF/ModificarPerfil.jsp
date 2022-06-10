<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="modelo.Usuario"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
	crossorigin="anonymous"></script>
<link rel="icon" href="./img/Logo.png">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">

<meta name="viewport" content="width=device-width, initial-scale=1">
	
<link rel="stylesheet" href="./css/estilos.css">

<title>Modificar Perfil</title>
</head>
<body>
	<%
		Usuario usuario;
		
		usuario = (Usuario) session.getAttribute("usuario"); 
	%>
	
	<!-- Navbar -->
	<%if(session.getAttribute("usuario") != null){ %>
		<nav class="navbar navbar-expand-lg nav-header" >
		  	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		    	<span class="navbar-toggler-icon"></span>
		  	</button>
		  	
		  	<img src="./img/Logo.png" class="nav-logo">
		  	
		  	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		    	<ul class="navbar-nav mr-auto">
		    	  <li class="nav-item">
		        		<a class="nav-link" href="Inicio">INICIO</a>
		      		</li>
			      <li class="nav-item active">
			        <a class="nav-link" href="Perfil">PERFIL</a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link" href="Cursos">CURSOS</a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link" href="CrearCurso">CREAR CURSOS</a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link" href="MisCursos">MIS CURSOS</a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link" href="CerrarSesion">CERRAR SESIÓN</a>
			      </li>
		     	</ul>
		     </div>
	     </nav>
	<%}else{ %>
		<nav class="navbar navbar-expand-lg nav-header">
	  		
	  		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    		<span class="navbar-toggler-icon"></span>
	  		</button>
	  	
	  		<img src="./img/Logo.png" class="nav-logo">
	  		
	  		<div class="collapse navbar-collapse" id="navbarSupportedContent">
	   			 <ul class="navbar-nav mr-auto">
	   			 	<li class="nav-item">
		        		<a class="nav-link" href="Inicio">INICIO</a>
		      		</li>
		      		<li class="nav-item active">
		        		<a class="nav-link" href="InicioSesion">INICIAR SESIÓN</a>
		      		</li>
		      		<li class="nav-item">
		        		<a class="nav-link" href="Registro">REGISTRARSE</a>
		      		</li>
		      		<li class="nav-item">
		        		<a class="nav-link" href="Cursos">CURSOS</a>
		      		</li>
		     		<li class="nav-item">
		        		<a class="nav-link" href="CrearCurso">CREAR CURSO</a>
		      		</li>
	     		</ul>
	     	</div>
     	</nav>
	<%}%>
	<br>
<!-- FIN NAVBAR -->
	<div class="d-flex justify-content-center mt-4 pb-4">
		<form method="post" class="bg-form text-dark rounded form-generico p-3" enctype="multipart/form-data">
			<div class="form-group mb-3">
				<%if (session.getAttribute("NombreM") != null){ %>
						<div class="alert alert-danger mt-3">
							<%out.println("Nombre inválido, uno o más caracterés son inválidos"); %>
						</div>
					<%}%>
				<%session.setAttribute("NombreM", null);%>
				<label for="nombre">Nombre </label>
				<br>
				<input type="text" name="nombre" value="<%= usuario.getNombre()%>"> 	
			</div>
			
			<div class="form-group mb-3">
				<%if (session.getAttribute("CorreoM") != null){ %>
						<div class="alert alert-danger mt-3">
							<%out.println("Correo inválido"); %>
						</div>
					<%}%>
				<%session.setAttribute("CorreoM", null);%>
				<label>Correo </label>
				<br>
				<input type="email" class="form-control"
					id="mail" name="mail" maxlength="45" value="<%= usuario.getCorreo()%>">
			</div>
			
		
			<div class="form-group mb-3">
				<%if (session.getAttribute("dniM") != null){ %>
						<div class="alert alert-danger mt-3">
							<%out.println("El formato del dni es inválido"); %>
						</div>
					<%}%>
				<%session.setAttribute("dniM", null);%>
				<label>DNI </label> 
				<br>
				<input class="form-control"
					id="dni" pattern="^[0-9]{8}[A-Za-z]{1}" name="dni" maxlength="9" value="<%=usuario.getDni()%>">
			</div>
			
			<div class="form-group mb-3 row">
			<label class="text-center mb-3">Imagen: </label>
			<br>
			<input type="file" class="row" name="imagen" accept="image/*">
			</div>
			
			<div class="mb-3 text-center">
				<label>Foto actual:</label>
				<br>
				<br>
				<%if(usuario.getImagen() != null){ %>
				<img class="imagen-perfil" src="PintarPerfil"></img>
				<br>
				<br>
				
				<%}else {%>
				<img width="100" height="100" src="img/defaultPerfil.png"></img>
				<br>
				<br>
				<%}; %>
			</div>
			
			<button class="btn button-estandar" type="submit">Modificar perfil</button>
		</form>
	</div>
	
	<jsp:include page="Footer.jsp"></jsp:include>
</body>
</html>