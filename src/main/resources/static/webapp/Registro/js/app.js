var app = (function(){
	var a=function () {
		apiclient.getUniversidades(agregarUniversidades);
	};

	var agregarUsuario = function(datos){
		var datosDos = JSON.stringify(datos);
		apiclient.agregarUsuario(datosDos);
	};

	var agregarUniversidades = function(info){
		info.map(function(element){
			$("#universidades").append(
				"<option value= \"" +element.nombre +"\">");
		});

	};

	var verificarInformacion = function(e){
		console.log("entramos....");
		e.preventDefault();
		var formulario = document.getElementById("formulario");

		console.log("damos click");
		var datos = new FormData(formulario);

		if( datos.get("pass")!= datos.get("confirmar pass")){
			location.reload();
		}
		else{
			var info = {
				"userId":9,
				"username":datos.get("usuario"),
				"password":datos.get("pass"),
				"email": datos.get("correo"),
				"rol": "user",
				"universidad":datos.get("universidad"),
				"direccionResidencia": datos.get("direccion")}

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
		init: init,
		agregarUniversidades:agregarUniversidades,
		a:a
	};
})();

/*
{
    "userId":9,
    "username":"pruebasexta",
    "password":"123",
    "email":"pruebss@hotmail.com",
    "rol":"admin",
    "universidad":"ECI",
    "direccionResidencia":"calle 186#150-basdas"

}

*/
		
