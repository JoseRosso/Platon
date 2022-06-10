<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">

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

<title>Registro</title>

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
		      		
		      		<li class="nav-item">
		        		<a class="nav-link" href="InicioSesion">INICIAR SESIÓN</a>
		      		</li>
		      		
		      		<li class="nav-item">
		        		<a class="nav-link active" href="Registro">REGISTRARSE</a>
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

		<form method="post" enctype="multipart/form-data" >
			<section class="h-100 bg-dark">
			  <div class="container py-5 h-100">
			    <div class="row d-flex justify-content-center align-items-center h-100">
			      <div class="col">
			        <div class="card card-registration my-4">
			          <div class="formulario-foto">
			            <div class="foto-formulario">
			              <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/img4.webp"
			                alt="Foto de registro" class="img-form img-registro"
			                style="border-top-left-radius: .25rem; border-bottom-left-radius: .25rem;" />
			            </div>
			            <div class="col-xl-6">
			              <div class="card-body p-md-5 text-black">
			                <h3 class="mb-5 text-uppercase">Formulario de registro</h3>
			
			                <div class="row">
			                  <div class="mb-4">
			                    <div class="form-outline">
			                    	<%if (session.getAttribute("Nombre") != null){ %>
										<div class="alert alert-danger mt-3">
											<%out.println("Nombre inválido, uno o más caracterés son inválidos"); %>
										</div>
									<%}%>
									<%session.setAttribute("Nombre", null);%>
			                    	<label class="form-label">Nombre: </label>
			                      	<input type="text" name="nombre" placeholder="Escriba su nombre" class="form-control form-control-lg" pattern="[a-z,A-Z,á,é,í,ó,ú,â,ê,ô,ã,õ,ç,Á,É,Í,Ó,Ú,Â,Ê,Ô,Ã,Õ,Ç,ü,ñ,Ü,Ñ,' ',']+" placeholder="Escriba su nombre" maxlength="45" required/>
			                    </div>
			                </div>
							<%if (session.getAttribute("Correo") != null){ %>
								<div class="alert alert-danger mt-3">
									<%out.println("Correo inválido"); %>
								</div>
							<%}%>
							<%session.setAttribute("Correo", null);%>
			                <div class="row">
			                  <div class="mb-4">
			                    <div class="form-outline">
   									<label class="form-label">Correo electrónico: </label>
			                      	<input type="email" name="mail" placeholder="Escriba su correo" class="form-control form-control-lg" maxlength="45" required />
			                    </div>
			                  </div>
			                </div>
								
			                <div class="form-outline mb-4">
			                	<%if (session.getAttribute("Password") != null){ %>
									<div class="alert alert-danger mt-3">
								<%out.println("Ambas contraseñas no coinciden"); %>
									</div>
								<%}%>
								<%session.setAttribute("Password", null);%>
			                	<label class="form-label">Contraseña: </label>                
			                 	<input type="password" placeholder="Escriba su contraseña" name="password" class="form-control form-control-lg" maxlength="15" required />                 
			                </div>
			                
			                <div class="form-outline mb-4">
			                	<label class="form-label">Confirme su contraseña: </label>                
			                 	 <input type="password" placeholder="Repita su contraseña" name="veriPass"  class="form-control form-control-lg" maxlength="15" required />                 
			                </div>
			                
			                <div class="form-outline mb-4">
			                	<%if (session.getAttribute("dni") != null){ %>
									<div class="alert alert-danger mt-3">
								<%out.println("Formato de dni inválido"); %>
									</div>
								<%}%>
								<%session.setAttribute("Password", null);%>
			                	<label class="form-label">DNI: </label>                
			                 	<input type="text" placeholder="NNNNNNNNNX" name="dni" class="form-control form-control-lg" max-length="9"/>                 
			                </div>
			                
			                <div class="form-outline mb-4">
			                	<label>Imagen: </label>
								<input type="file" name="imagen" accept="image/*">              
			                </div>
			                
			                <div class="d-flex justify-content-end pt-3">
		                  		<button type="submit" class="btn button-estandar btn-lg ms-2">Registrarse</button>
		                	</div>		                	                	                
			              </div>
			            </div>
			          </div>
			        </div>
			      </div>
			    </div>
			  </div>
			</section>
		</form>
	<jsp:include page="Footer.jsp"></jsp:include>
</body>
</html>