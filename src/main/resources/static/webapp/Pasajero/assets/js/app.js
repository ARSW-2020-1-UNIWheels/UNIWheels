var app = (function(){


	var getConductores = function(){
		console.log("vamos a consultar");
		var lista = apiclient.getConductoresDisponibles(addConductores);
		
	};
	
	var addConductores = function(conductores){
		$("#tableConductoresDisponibles > tbody").empty();
		conductores.map(function(element){
			$("#tableConductoresDisponibles > tbody").append(
				"<tr> <td>" +
				element.nombreEstado +
				"</td>" +
				"<td>" +
				element.tiempoRecorrido +
				"</td> " +
				"<td><form><button type='button' onclick='/addPassanger/"+element.usuario.username+"' >Agregar</button></form></td>" +
				"</tr>"
			);
		});
	};
	return{	
	    getConductores: getConductores,
		addConductores: addConductores
	};
	
})();
