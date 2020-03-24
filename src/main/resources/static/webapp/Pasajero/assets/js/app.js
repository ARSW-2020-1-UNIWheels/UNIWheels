var app = (function(){


	var getConductores = function(){
		var lista = apiclient.getConductoresDisponibles(addConductores);
		
	};
	
	var addConductores = function(conductores){
		$("#tableConductoresDisponibles > tbody").empty();
		conductores.map(function(element){
			$("#tableConductoresDisponibles > tbody").append(
				"<tr> <td>" +
				element.usuario.username +
				"</td>" +
				"<td>" +
				element.tiempoRecorrido +
				"</td> " +
				"<td><form><button type='button' onclick='apiclient.agregarPosibleConductor("+"\""+element.usuario.username+"&quot)' >Agregar</button></form></td>" +
				"</tr>"
			);
		});
	};
	return{	
	    getConductores: getConductores,
		addConductores: addConductores
	};
	
})();
