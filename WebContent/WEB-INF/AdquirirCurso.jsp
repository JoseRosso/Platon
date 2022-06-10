<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="modelo.Curso" %>
<%@ page import="modelo.MetodoPago" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Adquirir Curso</title>
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
</head>
<body>

<%
	Curso curso;
	MetodoPago metodPago[];

	curso = (Curso) session.getAttribute("InfoCurso");
	if(session.getAttribute("MetodosPago") != null){
		metodPago = (MetodoPago[]) session.getAttribute("MetodosPago");
	}else{
		metodPago = null;
	}

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
	<br>
<!-- FIN NAVBAR -->
	<div class="text-center bg-dark text-white">
		<p>Complete sus datos para adquirir <%=curso.getTitulo()%><p>
		<p>Importe total <%=curso.getPrecio() %>€</p>
	</div>
	<%if(metodPago != null){ %>
		<div class="d-flex justify-content-center mt-4 pb-3">
			<form method="post" class="bg-form text-dark rounded form-generico p-4">
				<label>Seleccione su tarjeta:</label>
				<select name="tarjeta" class="form-select mb-3" required>
					<%for(MetodoPago metodos : metodPago){ 
						String censuraTarjeta = "*".repeat(metodos.getNumeroTarjeta().length()-4)+metodos.getNumeroTarjeta().substring(metodos.getNumeroTarjeta().length()-4);
					%>	
						<option value="<%=metodos.getIdMetodoPago()%>"><%=censuraTarjeta%></option>
			 		<%} %>
		 		</select>
		 		<a href="AgregarMetodoPago" class="pb-3">Agregar nuevo método de pago</a>
		 		<button type="submit" class="btn button-estandar">Finalizar trámite</button>
		 	</form>
	 	</div>
 						
	<%}else{ %>
		<div class="text-center pb-3">
			<%out.print("No tienes ninguna tarjeta vinculada"); %>
			<a href="AgregarMetodoPago">Vincular tarjeta</a>
		</div>
	<%};%>
	<jsp:include page="Footer.jsp"></jsp:include>
</body>
</html>