

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
		//infoViaje();
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
		var precio = $("#precio").val();

	    console.log(inicio+" "+destino+" "+carro+" "+precio);
		console.log(typeof(inicio)+" "+typeof(precio));
		
		
		if(inicio==="Donde otás?" || destino==="Para donde vas?" || carro==="Que carro vas a usar?" || precio===""){
			alert("Debes ingresar todos los datospara iniciar tu viaje!!");
		}
		else{
			addConductor();
		}
		
	};
	
	var addPasajeros = function(pasajeros){
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/pasajero."+pasajeros.name, function (pasajeros){
				console.log(pasajeros);		
				$("#tablePasajeros").empty();
				pasajeros.map(function(element){
					var markup = "<tr> <td>" +
						element.name +
						"</td>" +
						"<td>" +
						element.universidad +
						"</td>" +
						"<td>" +
						element.puntuacion +
						"</td>" +
						"<td>" +
						element.ubicacionActual+
						"</tr>";
					$("#tablePasajeros").append(markup);
				})
			});
		});
	};
	
	var aceptarPasajero = function (pasajero,estado) {
		console.log("vamos a enviar el nombre "+pasajero+" "+estado);
		addPasajeros(pasajeros);
		stompClient.send("/app/agregarPasajero."+name,{},pasajero+estado);
	};

	var addSolicitudes = function(){
		get();
		//alert(name);
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
					var markup = "<tr> <td>" +
						element.usuario.username +
						"</td>" +
						"<td>" +
						element.usuario.universidad +
						"</td>"+
						"<td>" +
						element.calificacion +
						"</td>"+
						"<td><form><button type='button' onclick='app.aceptarPasajero("+element+",'true') >Aceptar</button></form></td>" +
						"<td><form><button type='button' onclick='app.aceptarPasajero("+element+",'false') >Rechazar</button></form></td>" +
						"</tr>";
					$("#tableSolicitudes > tbody").append(markup);
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
