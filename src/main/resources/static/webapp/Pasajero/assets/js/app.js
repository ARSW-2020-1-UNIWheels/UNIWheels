var app = (function(){


	var getConductores = function(){
		var lista = apiclient.getConductoresDisponibles(addConductores);
		
	};
	
	var addConductores = function(conductores){
		$("#tableConductoresDisponibles > tbody").empty();
		conductores.map(function(element){
			$("#tableConductoresDisponibles > tbody").append(
				"<tr> <td>" +
				element.conductorName +
				"</td>" +
				"<td>" +
				element.tiempoRecorrido +
				"</td> " +
				"<td><form><button type='button' onclick='apiclient.agregarPosibleConductor("+"\""+element.conductorName+"&quot)' >Agregar</button></form></td>" +
				"</tr>"
			);
		});
	};
	return{	
	    getConductores: getConductores,
		addConductores: addConductores
	};
	
})();
