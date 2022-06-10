function limitarArchivo(){
	
	var oFReader = new FileReader();
	oFReader.readAsDataURL(document.getElementById("material").files[0]);
	
	oFReader.onload = function (oFREvent) {
		
		if(document.getElementById("material").files[0].size > 10000000){
			alert('El archivo no debe superar los 10MB');
			document.getElementById("material").value = '';
			document.getElementById("material").name = '';
			
			document.getElementById("subirContenido").disable = true;
		}else{
			document.getElementById("subirContenido").disable = false;
		}
	};
	
}

