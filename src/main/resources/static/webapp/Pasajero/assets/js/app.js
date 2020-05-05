var app = (function(){

	var name;
	var _getUser = function(info){
		name = info.username;
	};

	function get(){
		apiclient.getUser(_getUser)
	}
    var infoViaje = function(){
        var inicio = $("#ubicacionActual").val();
        var destino = $("#destino").val();
        console.log(inicio+" "+destino);
        return destino.toString();
    };

	var getConductores = function(){
		get();
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/conductoresDisponibles", function (conductores) {
				console.log("Entramos al socket");
				console.log(conductores);
				var conductoresData = JSON.parse(conductores.body);
				console.log(conductoresData);
				$("#conductoresDisponibles").empty();
				conductoresData.map(function(element){
					console.log(element);
					console.log(element.carro);
					var markup = '<tr><td>' + element.conductorName +
						"</td><td>" +
						element.carro.marca+" "+element.carro.modelo +
						"</td><td>"+
						element.ruta.direccionOrigen +
						"</td><td>"+
						element.ruta.direccionDestino +
						"</td><td>"+
							element.ruta.precio+
						"</td>"+
						//"<td><form><button type='button' onclick='apiclient.agregarPosibleConductor("+"\""+element.conductorName+"&quot)' >Agregar</button></form></td>" +
						"<td><form><button type='button' onclick='app.agregarPosiblePasajero("+"\""+element.conductorName+"&quot)' >Agregar</button></form></td>" +
						"</tr>";


					$("#conductoresDisponibles").append(markup);

				});
			});
			stompClient.send("/app/conductoresDisponibles");
		});

		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/pasajeroAceptado."+name);
		});
	};

	var agregarPosiblePasajero = function(conductorName){
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			stompClient.send("/app/agregarPosiblePasajero",{},conductorName);
		});
	};

	return{	
	    getConductores: getConductores,
		agregarPosiblePasajero:agregarPosiblePasajero,
		get:get

	};
	
})();
