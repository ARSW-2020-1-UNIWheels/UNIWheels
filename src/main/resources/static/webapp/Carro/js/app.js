var app = (function(){
	
	var agregarCarro = function(datos){
		var datosDos = JSON.stringify(datos);
		apiclient.agregarCarro(datosDos);
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
			var info = {
				"marca": datos.get("marca") ,
				"placa": datos.get("placa"),
				"modelo": datos.get("modelo"),
				"color": datos.get("color")}
			
			agregarCarro(info);
		}
	};
	
	var init = function(){
		console.log("si funciona");
		document.addEventListener("submit",verificarInformacion);
	};
	
	return{
		agregarCarro: agregarCarro,
		verificarInformacion: verificarInformacion,
		init: init
	};
})();
