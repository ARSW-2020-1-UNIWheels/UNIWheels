var app = (function(){

    var dic = {};
	
	/**
		Agregar funciones que:
		1. Agreguen un nuevo pasajero a la lista de solicitudes de pasajeros en el Socket.
		2. Eliminar el pasajero de lista de solicitudes cuando es aceptado.
		3. Agregar el pasajero a la lista de pasajeros aceptados, con un botón de cancelar.
	**/

	var getPasajeros = function(){
		apiclient.getConductoresDisponibles(addConductores);
	};


	var getCarros = function(){
	    console.log("estamos en JS");
	    apiclient.getCarros(addCarros);
	};

	var addCarros = function(info){
	    console.log("Vamos a poner mis carros");

	    info.map(function(element){
	        var nombre = element.marca +" "+ element.modelo;
            $("#carro").append(
                "<option value= "+nombre+">"+nombre+"</option>");
            console.log(nombre);
	    });

	};

	var infoViaje = function(){
	    var inicio = $("#ubicacionActual").val();
	    var destino = $("#destino").val();
	    var carro = $("#carro").val();

	    console.log(inicio+" "+destino+" "+carro);
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
			//);
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
			//);
		});
	};
	
	return{	
	    getPasajeros: getPasajeros,
		addPasajeros: addPasajeros,
		addSolicitudes: addSolicitudes,
		getCarros: getCarros,
		addCarros: addCarros,
		infoViaje: infoViaje
	};
	
})();
