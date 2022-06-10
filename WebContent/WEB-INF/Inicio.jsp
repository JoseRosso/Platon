<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="modelo.Curso" %>
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

<title>Inicio</title>
</head>
<body>
	<% 
		Curso cursos[];
	
		cursos = (Curso[]) session.getAttribute("Populares");
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
		    	  <li class="nav-item active">
			        <a class="nav-link" href="Inicio">INICIO</a>
			      </li>
			      <li class="nav-item">
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
<!-- FIN NAVBAR -->
	
<!-- CABECERA -->
	<div  class="jumbotron bg-cover text-white fondo-cabecera">
		<div class="container py-5 text-center">
		   <h1 class="display-4 font-weight-bold texto-cabecera">“El sabio querrá estar siempre con quien sea mejor que él” - Platón</h1>
		        <p class="font-italic mb-0 subtexto-cabecera">Según este filósofo, una de las características de las personas sabias es que se rodean de personas de las que pueden aprender. Esas personas son los profesores de nuestra plataforma.</p>
		  <br>
		  <a href="Cursos" role="button" class="btn button-estandar">Ver cursos</a>
	    </div>
	</div>
<!-- FIN CABECERA -->
	<div class="fondo-catalogo">
		<h3 class="text-center pt-3">Lo más vendido</h3>
		<div class="d-flex p-3 align-items-center">		
			<div class="cursos-grid">
				<%for(Curso cur : cursos){%>	
						<%if(cur != null){ %>	
							<div class="card card-size">
						  		<img class='card-img-top img-curso' src="PintarCurso?idCurso=<%=cur.getIdCurso()%>" alt='Imagen del curso'/>
						  		<div class="card-body cuerpo-curso">
						    		<h5 class="card-title"><%=cur.getTitulo()%></h5>
						    		<p class="card-text descripcion"><%=cur.getDescripcion()%></p>
						    		<div class="mt-auto">
						    			<p class="card-text fw-bold"><%= cur.getPrecio()%>€</p>
						    			<a href='InfoCurso?idCurso=<%=cur.getIdCurso()%>' class="btn button-estandar w-100">Ver</a>
						    		</div>
						  		</div>
							</div>							
						<%} %>
					<%} %>
			</div>
		</div>
	</div>	
	
<jsp:include page="Footer.jsp"></jsp:include>
</body>
</html>