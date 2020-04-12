var app = (function(){
	
	var agregarUsuario = function(datos){
		apiclient.agregarUsuario(datos);
	};	
	
	var verificarInformacion = function(e){
		console.log("entramos....");
		e.preventDefault();
		var formulario = document.getElementById("formulario");

		console.log("damos click");
		var datos = new FormData(formulario);
			
		if( datos.get("pass")!= datos.get("confirmar pass")){
			alert("La contraseña no coincide !!");
			location.reload();
		}
		else{
			var info = {"nombre": datos.get("usuario") ,
						"correo": datos.get("correo"),
						"universidad": datos.get("uuniversidad"),
						"direccion": datos.get("direccion"),
						"pass": datos.get("pass"),
						"rol": "usuario"};
			
			agregarUsuario(info);
		}
	};
	
	var init = function(){
		console.log("si funciona");
		document.addEventListener("submit",verificarInformacion);
	};
	
	return{
		agregarUsuario: agregarUsuario,
		verificarInformacion: verificarInformacion,
		init: init
	};
})();

		
