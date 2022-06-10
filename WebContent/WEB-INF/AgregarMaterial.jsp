<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="modelo.Curso" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="icon" href="./img/Logo.png">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
	crossorigin="anonymous"></script>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">

<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="./css/estilos.css">

<title>Agregar material</title>
</head>
<body>

	<%
		Curso curso;
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
			      <li class="nav-item">
			        	<a class="nav-link" href="Perfil">PERFIL</a>
			      </li>
			     
			      <li class="nav-item active">
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
		      		<li class="nav-item active">
		        		<a class="nav-link" href="Cursos">CURSOS</a>
		      		</li>
		     		<li class="nav-item">
		        		<a class="nav-link" href="CrearCurso">CREAR CURSO</a>
		      		</li>
	     		</ul>
	     	</div>
     	</nav>
	<%}%>
<!-- FIN NAVBAR -->
	<div>
	<div class="d-flex justify-content-center mt-4 p-3">	
		<form method="post" enctype="multipart/form-data" class="bg-form text-dark rounded form-generico p-3">
			<%if(session.getAttribute("ArchivoPesado") != null){%>
				<div class="alert alert-danger mt-3">
					<%out.print("El archivo es demasiado pesado, asegurese de que pesa menos de 10MG"); %>
				</div>
			<% }%>
			<%session.setAttribute("ArchivoPesado",null);%>
			
			<%if(session.getAttribute("FalloArchivo") != null){%>
				<div class="alert alert-danger mt-3">
					<%out.print("Fallo al procesar el archivo"); %>
				</div>
			<% }%>
			<%session.setAttribute("FalloArchivo",null);%>
			
			<%if(session.getAttribute("ExitoSubida") != null){%>
				<div class="alert alert-success mt-3">
					<%out.print("Su archivo se ha subido con éxito"); %>
				</div>
			<% }%>
			<%session.setAttribute("ExitoSubida",null);%>
			
			<div class="row">	
				<div class="form-group mb-3">
					<label class="form-label">Título </label>
					<br>
					<input type="text" name="titulo" placeholder="Escriba un titulo" maxlength="150" required> 	
				</div>
														
				<div class=" form-group mb-3">
					<label class="form-label">Descripción </label>
			        <textarea name="descripcion" placeholder="Escriba una descripción" class="form-control" rows="6" maxlength="400"></textarea>		
				</div>
						
				<div class="mb-3 row">
					<label class="form-label">Subir archivo</label>
					<input name="archivo" onchange="limitarArchivo()"  type="file" required>
				</div>
				<button class="btn button-estandar" id="subirContenido" type="submit">Añadir contenido</button>
			</form>
			</div>
		</div>	
		<jsp:include page="Footer.jsp"></jsp:include>
	
<script src="./js/LimitSizeArchivo.js"></script>
</body>
</html>