

var app = (function(){

	var placa;
	var name;
	class Conductor{
		constructor(){
		}
	}
	class Ruta{
		constructor(direccionOrigen, direccionDestino,precio){
			this.direccionOrigen = direccionOrigen;
			this.direccionDestino=direccionDestino;
			this.precio = precio;

		}
	}
	class Carro{
		constructor(placa,marca,modelo) {
			this.placa = placa;
			this.marca = marca;
			this.modelo = modelo;
		}
		 toString = function(){
			var t = this.marca + " " + this.modelo;
			return t;
		}
	}
    var dic = {};
	
	/**
		Agregar funciones que:
		1. Agreguen un nuevo pasajero a la lista de solicitudes de pasajeros en el Socket.
		2. Eliminar el pasajero de lista de solicitudes cuando es aceptado.
		3. Agregar el pasajero a la lista de pasajeros aceptados, con un botón de cancelar.
	**/

	var _getUser = function(info){
		name = info.username;
	};

	function get(){
		apiclient.getUser(_getUser)
	}

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
	    	var carro = new Carro(element.placa,element.marca,element.modelo);
            $("#carro").append(
				"<option value= \""+ carro.placa+"\"> "+ carro.toString()+" </option>");
	    });

	};

	var stompClient = null;

	var addConductor = function(){

		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			console.log('Connected: ');
			stompClient.send("/app/nuevoConductor",{},JSON.stringify(new Ruta($("#ubicacionActual").val(),$("#destino").val(),parseInt($("#precio").val(),10)))+$("#carro").val());
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
	
	
	var addSolicitudes = function(){
		get();
		alert(name);
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/posiblesConductores."+name, function (conductores) {
				//console.log(conductores);
				var conductoresData = JSON.parse(conductores.body);
				$("#tableSolicitudes > tbody").empty();
				conductoresData.map(function(element){
					console.log(element);
					$("tableSolicitudes > tbody").append(
						"<tr> <td>" +
						element.usuario.username +
						"</td>" +
						"<td>" +
						element.usuario.universidad +
						"</td>"+
						"<td>" +
						element.usuario.direccionResidencia +
						"</td></tr>"
					);
				});
			});
		});

	};
	
	return{	
	    getPasajeros: getPasajeros,
		addPasajeros: addPasajeros,
		addSolicitudes: addSolicitudes,
		getCarros: getCarros,
		addCarros: addCarros,
		infoViaje: infoViaje,
		addConductor: addConductor,
		get:get
	};
	
})();
