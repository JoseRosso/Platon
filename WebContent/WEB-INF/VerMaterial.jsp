<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="modelo.Material"%>
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

<title>Ver material</title>
</head>
<body>

	<%
		Material material[];
		
		material = (Material[]) session.getAttribute("Materiales");
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
	   			 	<li class="nav-item active">
		        		<a class="nav-link" href="Inicio">INICIO</a>
		      		</li>
		      		<li class="nav-item">
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

	<%if(material.length == 0) {%>
		<div class="text-center pb-3">
			<h2 class="fw-bold">Aún no hay contenido para este curso</h2>
			<h4>¡Sentimos las molestias!</h4>
		</div>
	<%}else{ %>
		<div class="text-center pb-3">
			<h2 class="fw-bold fs-2">Materiales</h2>
			<%for(Material mat : material){%>	
				<%if(mat != null){ %>	
					<ul class="ul-material">
						<li ><a class="material fs-5" href='DescargarArchivo?idMaterial=<%=mat.getIdMaterial()%>'><%=mat.getTitulo()%></a></li>
					</ul>
				<%} %>
			<%} %>
		</div>
	<%} %>
	
	<jsp:include page="Footer.jsp"></jsp:include>

</body>
</html>