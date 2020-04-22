var app = (function(){

	var getPasajeros = function(){
		//var lista = apiclient.getConductoresDisponibles(addConductores);
		
	};


	var getCarros = function(){
	    var carros = apiclient.getCarros(addCarros);
	};

	var addCarros = function(carros){
	    console.log("ESTOS SON MIS CARROS");
	    console.log(carros);
	};
	
	var addPasajeros = function(pasajeros){
		$("#tablePasajeros > tbody").empty();
		pasajeros.map(function(element){
			/*
			$("#pasajerosAceptados > tbody").append(
				"<tr> <td>" +
				element.conductorName +
				"</td>" +
				"<td>" +
				element.tiempoRecorrido +
				"</td> " +
				"<td><form><button type='button' onclick='apiclient.agregarPosibleConductor("+"\""+element.conductorName+"&quot)' >Agregar</button></form></td>" +
				"</tr>"
			*/
			);
		});
	};
	
	
	var addSolicitudes = function(pasajeros){
		$("#tableSolicitudes > tbody").empty();
		pasajeros.map(function(element){
			/*
			$("solicitudesPasajeros > tbody").append(
				"<tr> <td>" +
				element.conductorName +
				"</td>" +
				"<td>" +
				element.tiempoRecorrido +
				"</td> " +
				"<td><form><button type='button' onclick='apiclient.agregarPosibleConductor("+"\""+element.conductorName+"&quot)' >Agregar</button></form></td>" +
				"</tr>"
			*/
			);
		});
	};
	
	return{	
	    getPasajeros: getPasajeros,
		addPasajeros: addPasajeros,
		addSolicitudes: addSolicitudes
	};
	
})();
