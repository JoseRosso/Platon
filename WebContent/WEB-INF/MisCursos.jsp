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
<title>Mis Cursos</title>

<%
	Curso cursos[];
	Curso cursosProfesor[];

	cursos = (Curso[]) session.getAttribute("MisCursos");
	cursosProfesor = (Curso[]) session.getAttribute("Profesor");
%>
</head>
<body>

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
			    
			      <li class="nav-item">
			        <a class="nav-link" href="Cursos">CURSOS</a>
			      </li>
			      
			      <li class="nav-item">
			        <a class="nav-link" href="CrearCurso">CREAR CURSOS</a>
			      </li>
			      
			      <li class="nav-item active">
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
		      		<li class="nav-item">
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
		<h1 class="justify-content-center">Cursos adquiridos</h1>
			<%if(cursos.length == 0) {%>
				<div class="text-center">
					<h2 class="fw-bold">No tienes cursos aún</h2>
					<h4>¿A qué esperas? Apuntate a través de este enlance <a href="Cursos">Apuntarse</a></h4>
				</div>
			<%} %>
		<div class="cursos-grid p-3">		
				<%for(Curso cur : cursos){%>	
					<%if(cur != null){ %>	
						<div class="card card-size">
					  		<img class='card-img-top img-curso' src="PintarCurso?idCurso=<%=cur.getIdCurso()%>" alt='Imagen del curso'/>
					  		<div class="card-body cuerpo-curso">
					    		<h5 class="card-title"><%=cur.getTitulo()%></h5>
					    		<p class="card-text descripcion"><%=cur.getDescripcion()%></p>
					    		<div class="mt-auto">
					    			<p class="card-text fw-bold"><%= cur.getPrecio()%>€</p>
					    			<a href='VerMaterial?idCurso=<%=cur.getIdCurso()%>' class="btn button-estandar w-100">Ver contenidos</a>
					    		</div>
					  		</div>
						</div>
							
					<%} %>
				<%} %>
			</div>
			
			
			<h1 class="pt-3">Mis cursos creados</h1>
			<%if(cursosProfesor.length == 0) {%>
				<div class="text-center">
					<h2>Aún no has creado ningún curso</h2>
					<h4>Crea tu propio curso a través de este enlace <a href="CrearCurso">¡Crear curso!</a></h4>
				<div>
			<%} %>
			<div class="cursos-grid p-3">		
			
				<%for(Curso cur : cursosProfesor){%>	
					<%if(cur != null){ %>	
						<div class="card card-size">
					  		<img class='card-img-top img-curso' src="PintarCurso?idCurso=<%=cur.getIdCurso()%>" alt='Imagen del curso'/>
					  		<div class="card-body cuerpo-curso">
					    		<h5 class="card-title"><%=cur.getTitulo()%></h5>
					    		<p class="card-text descripcion"><%=cur.getDescripcion()%></p>
					    		<div class="mt-auto">
					    			<p class="card-text"><%= cur.getPrecio()%>€</p>
					    			<a href='AgregarMaterial?idCurso=<%=cur.getIdCurso()%>' class="btn button-estandar w-100">Añadir contenido</a>
					    		</div>
					  		</div>
						</div>						
					<%} %>
				<%} %>
	</div>
<jsp:include page="Footer.jsp"></jsp:include>
</body>
</html>