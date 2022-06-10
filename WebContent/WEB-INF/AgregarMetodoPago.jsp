<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agregar método de pago</title>
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
	<h3 class="text-center">Complete el formulario</h3>
	
	
	
	<div class="d-flex justify-content-center mt-4 mb-3">

		<form method="post" class="bg-form text-dark rounded form-generico" style="width:400px">
			<%if (session.getAttribute("MetodoPago") != null){ %>
				<div class="alert alert-danger mt-3">
			<%out.println("Su método de pago ha sido agregado correctamente"); %>
				</div>
			<%}%>
			<%session.setAttribute("MetodoPago", null);%>
			
			<%if (session.getAttribute("ErrorMetodoPago") != null){ %>
				<div class="alert alert-danger mt-3">
			<%out.println("No se llevo a cabo el proceso"); %>
				</div>
			<%}%>
			<%session.setAttribute("ErrorMetodoPago", null);%>
			
			
			<%if (session.getAttribute("FormatoInvalido") != null){ %>
				<div class="alert alert-danger mt-3">
			<%out.println("Aseguresé de que los datos son correctos"); %>
				</div>
			<%}%>
			<%session.setAttribute("FormatoInvalido", null);%>
			
			<div class="form-group mt-3 mb-3 ">
				<label>Nombre del titular</label>
				<br>
				<input type="text" name="nombre" required>
			</div>
			
			
			<%if (session.getAttribute("Cvv") != null){ %>
				<div class="alert alert-danger mt-3">
			<%out.println("No se ajusta al formato adecuado: NNN o NNNN"); %>
				</div>
			<%}%>
			
			
			<div class="form-group mb-3">
				<label>CVV</label>
				<br>
				<input type="text" name="cvv" maxlength="4" required>
			</div>
			
			<%if (session.getAttribute("numTarjeta") != null){ %>
				<div class="alert alert-danger mt-3">
			<%out.println("Aseguresé de que su número de tarjeta es válido"); %>
				</div>
			<%}%>
			<div class="form-group mb-3">
				<label>Número de tarjeta</label>
				<br>
				<input type="text" name="numTarjeta" required>
			</div>
			
			<%if (session.getAttribute("anoCaduca") != null){ %>
				<div class="alert alert-danger mt-3">
			<%out.println("Aseguresé de que la fecha es válida"); %>
				</div>
			<%}%>
			<div class="mb-3 d-flex justify-content-between form-fecha">
				<div class="form-group">
					<label>Mes: </label>
						<select name="mesCaduca" class="form-select" required >
							<option value="1">Enero</option>
							<option value="2">Febrero</option>
							<option value="3">Marzo</option>
							<option value="4">Abril</option>
							<option value="5">Mayo</option>
							<option value="6">Junio</option>
							<option value="7">Julio</option>
							<option value="8">Agosto</option>
							<option value="9">Septiembre</option>
							<option value="10">Octubre</option>
							<option value="11">Noviembre</option>
							<option value="12">Diciembre</option>
						</select>
				</div>
				
				<div class="form-group">
					<label>Año:</label>
					<input type="text" class="anio" size="1" name="anoCaduca" maxlength="4" required>
				</div>
			</div>
			<div class="text-center">
				<input type="submit" class="btn button-estandar mb-3" value="Agregar tarjeta"/>
			</div>
		</form>
	</div>
	
	<jsp:include page="Footer.jsp"></jsp:include>
</body>
</html>