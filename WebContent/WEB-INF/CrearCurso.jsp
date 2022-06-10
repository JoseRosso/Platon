<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="modelo.Categoria" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Curso</title>
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
	Categoria categorias[];

	categorias = (Categoria[]) session.getAttribute("categorias");
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
			      
			      <li class="nav-item">
			        <a class="nav-link" href="Cursos">CURSOS</a>
			      </li>
			      
			      <li class="nav-item active">
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
		        		<a class="nav-link" href="Registro">REGISTRARSE</a>
		      		</li>
		      		<li class="nav-item">
		        		<a class="nav-link" href="Cursos">CURSOS</a>
		      		</li>
		     		<li class="nav-item active">
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
		            <div class="fondo-formulario">
		              <img src="./img/crearcurso.jpg"
		                alt="Foto de registro" class="img-form"
		                style="border-top-left-radius: .25rem; border-bottom-left-radius: .25rem;" />
		            </div>
		            <div class="col-xl-6">
		              <div class="card-body p-md-5 text-black">
		                <h3 class="mb-5 text-uppercase">Crear curso</h3>
		                
						<%if (session.getAttribute("CursoCreado") != null){ %>
							<div class="alert  alert-success mt-3">
						<%out.println("Se ha creado su curso con éxito"); %>
							</div>
						<%}%>
						
						<%if (session.getAttribute("ErrorCurso") != null){ %>
							<div class="alert  alert-success mt-3">
						<%out.println("Se ha producido un error"); %>
							</div>
						<%}%>
						
		                <div class="row">
		                  <div class="mb-4">
		                    <div class="form-outline">
		                    	<label class="form-label">Título: </label>
		                      	<input type="text" name="titulo" placeholder="Escriba su nombre" class="form-control form-control-lg"  placeholder="Escriba el titulo" maxlength="45" required/>
		                    </div>
		                </div>
		                
		                <div class="row">
		                  <div class="mb-4">
		                    <div class="form-outline">
		                    	<label class="form-label">Descripción: </label>
		                      	<textarea name="descripcion" placeholder="Escriba una descripción" class="form-control form-control-lg" maxlength="400" required></textarea>
		                    </div>
		                </div>
		                
		                <%if (session.getAttribute("Precio") != null){ %>
							<div class="alert  alert-success mt-3">
						<%out.println("El formato del precio es inválido: NN.NN"); %>
							</div>
						<%}%>
		                
		                <div class="row">
		                  <div class="mb-4">
		                    <div class="form-outline">
		                    	<label class="form-label">Precio: </label>
		                      	<input name="precio" placeholder="0.00"  pattern="^(\d+(\.\d{0,2})?|\.?\d{1,2})$" class="form-control form-control-lg" maxlength="400" required/>
		                    </div>
		                </div>
		                		                
		                 <div class="row">
		                  <div class="mb-4">
		                    <div class="form-outline">
		                    	<label class="form-label">Categoría: </label>
		                      	<select name="categoria"  class="form-control form-control-lg" maxlength="400" required>
		                      		<%for(Categoria cat : categorias){ %>
				 						<option value="<%=cat.getIdCategoria()%>"><%=cat.getNombre()%></option>
				 					<%} %>
		                      	</select>
		                    </div>
		                </div>
         
		                <div class="form-outline mb-4">
		                	<label>Imagen: </label>
							<input type="file" name="imagen" accept="image/*">              
		                </div>
		                
		                <div class="d-flex justify-content-end pt-3">
	                  		<button type="submit" class="btn button-estandar btn-lg ms-2">Crear curso</button>
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