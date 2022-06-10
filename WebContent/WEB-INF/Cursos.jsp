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
	
<title>Cursos</title>
</head>
<body>

<%
	Curso cursos[];

	cursos = (Curso[]) session.getAttribute("cursos");
	
	int cursosPorPag;
	int paginasTotales;
	String orden;
	int pag;
	
	paginasTotales = (Integer) (session.getAttribute("paginasTotales"));
	
	
	if(request.getParameter("orden") == null || !request.getParameter("orden").equals("precio") && !request.getParameter("orden").equals("titulo")){
		orden = "idCurso";
	}else orden = request.getParameter("orden");
	
	
	try {
		cursosPorPag = Integer.parseInt(request.getParameter("cantidad"));
	}catch(Exception e) {
		cursosPorPag = 4;	
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
	<div class="fondo-catalogo">
		<div class="d-flex p-3 align-items-center filtros">			
				<div class="margen-form-cursos">			
					<label>Ordenar por:</label>
					<select name="orden" onchange="window.location.href=`Cursos?orden=\${this.value}&cantidad=<%=cursosPorPag%>`">
						<option value="codigo" <%= orden.equals("idCurso") ? "selected" : null %>>Código</option>
						<option value="titulo" <%= orden.equals("titulo") ? "selected" : null %>>Título</option>
						<option value="precio" <%= orden.equals("precio") ? "selected" : null %>>Precio</option>
					</select>
				</div>	
				
				<div>
					<label>Mostrar</label>
						<select name="articulosPorPag" onchange="window.location.href=`Cursos?cantidad=\${this.value}&orden=<%=orden%>`">
							<option value="4" <%= cursosPorPag == 4 ? "selected" : null %>>4</option>
							<option value="8" <%= cursosPorPag == 8 ? "selected" : null %>>8</option>
							<option value="12" <%= cursosPorPag == 12 ? "selected" : null %>>12</option>
						</select> 
					<label>artículos por página</label>
				</div>
								
				<form method="post" class="form-search">
					<input type="text" placeholder="Buscar por título del curso..." name="busqueda">	
					<input type="submit"  value="Buscar">
				</form>
		</div>
		
		<%if(cursos[0] != null ){ %>
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
					    			<a href='InfoCurso?idCurso=<%=cur.getIdCurso()%>' class="btn button-estandar w-100">Ver</a>
					    		</div>
					  		</div>
						</div>	
										
					<%}%>
				<%} %>
			</div>
				<nav>
			  		<ul class="pagination pagination-md justify-content-center">
				  		<%for(int i = 1; i <= paginasTotales; i++){ %>
								<li class="page-item"><a class="page-link bg-dark text-white" href="Cursos?pag=<%= i %>&cantidad=<%= cursosPorPag %>&orden=<%=orden%>"><%=i%></a></li>
						<%}; %>
			  		</ul>
				</nav>
			<%}else{ %>
				<h2 class="text-center p-3">¡No se han encontrado resultados!</h2>
			<%} %>				
	</div>
	
	<jsp:include page="Footer.jsp"></jsp:include>
</body>
</html>