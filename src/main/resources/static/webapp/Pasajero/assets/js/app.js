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
	};

	var pasajeroAceptado = function () {
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/pasajeroAceptado."+name,function(conductor){
				console.log("vamos a eliminar");
				$("#div-table").empty();
				console.log(conductor);
				var conductorData = JSON.parse(conductor.body);
				console.log(conductorData);

				var card = '<div class="card" style="width: 30rem; text-align: center">'+
					'<img src="..." class="card-img-top" alt="...">'+
					'<div class="card-body">'+
					'<h5 class="card-title">'+conductorData.conductorName+'</h5>'+
					'<p class="card-text">El valor de la carrera es de: '+conductorData.ruta.precio+'.</p>'+
					'<p class="card-text">Información de su carro:</p>'+
					'</div>'+
					'<ul class="list-group list-group-flush">'+
					'<li class="list-group-item">'+conductorData.carro.marca+'</li>'+
					'<li class="list-group-item">'+conductorData.carro.modelo+'</li>'+
					'<li class="list-group-item">'+conductorData.carro.placa+'</li>'+
					'</ul>'+
					'<div class="card-body">'+
					'<a href="#" class="card-link" onclick="alert("Metodo en construcción")">Cancelar viaje</a>'+
					'</div>'+
					'</div>';


				var markup = '<div id="div-table" class="content" style="text-align: center;">'+
					'<br></br>'+
					'<header>'+
					'<h2>Mi Condutor</h2>'+
					'</header>'+
					'<br></br>'+card+
					'<br></br>';

				$("#div-table").append(markup);
			});
		});
	}

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
		get:get,
		infoViaje: infoViaje,
		pasajeroAceptado:pasajeroAceptado

	};
	
})();
