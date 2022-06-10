<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<title>Verificacion</title>
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

 		<%if(session.getAttribute("verificado") != null) {%>
			<div class="alert alert-danger mt-3 text-center">
				<span><%= (String) session.getAttribute("verificado")%></span> <%}%>
			</div>
		<%session.setAttribute("verificado", null);%>
		
		<%if(session.getAttribute("AccesoDenegado") != null){ %>
			<div class="alert alert-danger mt-3 text-center">
				<%out.println("Para adquirir o crear un curso es necesario estar verificado"); %>
			</div>
		<%}; %>	
		<%session.setAttribute("AccesoDenegado", null);%>
		
		<%if(session.getAttribute("NuevaVerificacion") != null){ %>
			<div class="alert alert-success mt-3 text-center">
				<%out.println("Se le ha enviado un nuevo código de verificación a su correo"); %>
			</div>
		<%}; %>	
		<%session.setAttribute("NuevaVerificacion", null);%>
	
	
	<div class="d-flex justify-content-center mt-4 pb-4">
		<div class="d-flex flex-column">	
			 <form method="post" class="bg-form text-dark rounded-top form-generico p-3">
			 	<h3 class="fw-bold">Verificación</h3>
			 	<label class="mb-3">Introduzca su código de verificación: </label>
				<input class="mb-3" type="text" name="codverificacion">
				
				<input class="btn button-estandar" type="submit" value="Verificarse">
					
			</form>
			<div class="bg-form text-dark rounded-bottom form-generico">
			<label class="mb-3">Si lo desea puede verificarse más tarde</label>
			<a class="mb-3" href="Perfil">Verificarse en otro momento</a>
			
			<label class="mb-3">¿Ha perdido su código de verificación?</label>
			<a class="mb-3" href="ReenviarVerificacion">Recibir nuevo código de verificación</a>
			</div>
		</div>
	</div> 
	
	<jsp:include page="Footer.jsp"></jsp:include>

</body>
</html>