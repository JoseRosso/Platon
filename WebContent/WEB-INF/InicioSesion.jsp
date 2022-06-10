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

<title>Inicio Sesión</title>
</head>
<body>

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
			      
			      <li class="nav-item active">
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

		

	<section class="vh-100">
	  <div class="container-fluid h-custom">
	    <div class="row d-flex justify-content-center align-items-center h-100">
	      <div class="col-md-9 col-lg-6 col-xl-5">
	        <img src="./img/coding-man.jpg"
	          class="img-fluid rounded" alt="Imagen de login">
	      </div>
	      <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
	        
	        <form method="post">
	        	<%if (session.getAttribute("AccesoDenegado") != null){ %>
					<div class="alert alert-danger mt-3">
				<%out.println("Por favor, inicie sesión"); %>
					</div>
				<%}%>
				<%session.setAttribute("AccesoDenegado", null);%>
			
				<%if(session.getAttribute("DatosIncorrectos") != null) {%>
					<div class="alert alert-danger mt-3">
				<%out.println("Su correo o contraseña no coinciden"); %>
					</div>
				<%} %>
				<%session.setAttribute("DatosIncorrectos",null);%>
				
	          <!-- Email input -->
	          <div class="form-outline mb-4">
	           <label class="form-label" for="form3Example3">Correo electrónico:</label>
	            <input type="email" name="correo" id="form3Example3" class="form-control form-control-lg"
	              placeholder="Introduzca su correo"/>
	           
	          </div>
	
	          <!-- Password input -->
	          <div class="form-outline mb-3">
	           <label class="form-label" for="form3Example4">Contraseña:</label>
	            <input type="password" name="password" id="form3Example4" class="form-control form-control-lg"
	              placeholder="Introduzca su contraseña"/>
	           
	          </div>
	
	          <div class="d-flex justify-content-between align-items-center">
	            <a href="RecuperarCuenta" class="text-body">Recuperar contraseña</a>
	          </div>
		
	          <div class="text-center text-lg-start mt-4 pt-2">
	            <button type="submit" class="btn button-estandar"
	              style="padding-left: 2.5rem; padding-right: 2.5rem;">Iniciar sesión</button>
	            <p class="small fw-bold mt-2 pt-1 mb-0">¿Aún no tienes cuenta? <a href="Registro"
	                class="link-danger">Regístrate</a></p>
	          </div>
	
	        </form>
	      </div>
	    </div>
	  </div>
	  <br>
	  <br>
	  
	<jsp:include page="Footer.jsp"></jsp:include>
</section>

</body>
</html>