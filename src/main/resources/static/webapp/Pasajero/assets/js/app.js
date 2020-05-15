

var app = (function(){

	var name;
	var conduc;
	var ubicaciones= new Array();
	var socket = new SockJS('/stompendpoint');
	var calificaciones= new Array();

	function misCoordenadas(){
		console.log("calculando coordenadas");
		navigator.geolocation.watchPosition(mostrarPosicion);
	};

	function mostrarPosicion(position){
		alert("Ey entré a Mostrar Posicion");
		console.log(position.coords.latitude+" "+position.coords.longitude);
		var datos = {"latitud":position.coords.latitude,"longitud":position.coords.longitude};
		console.log(typeof(position.coords.longitude)+" "+typeof(position.coords.latitude));
		ubicaciones.push(datos);
		plotMarkers(ubicaciones);
	};

	var _getUser = function(info){
		name = info.username;
	};

	function get(){
		apiclient.getUser(_getUser)
	};
    var infoViaje = function(){
        var inicio = $("#ubicacionActual").val();
        var destino = $("#destino").val();
        console.log(inicio+" "+destino);
        return destino.toString();
    };

	var stompClient = null;

	var getConductores = function(){
		get();
		console.info('Connecting to WS...');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/conductoresDisponibles", function (conductores) {
				console.log("Entramos al socket");
				console.log(conductores);
				var conductoresData = JSON.parse(conductores.body);
				console.log(conductoresData);
				$("#conductoresDisponibles").empty();
				calificaciones = new Array();

				toastr.options = { "positionClass": "toast-bottom-right"};
				toastr.info('Danos un minuto mientras cargamos la información');
				conductoresData.map(async function(element){
					let data = await fetch('/uniwheels/getValoracion/'+element.conductorName+"/conductor");
					let calificacion = await data.json();
					//getCali(element.conductorName,"conductor");
					console.log(element);
					console.log(element.carro);
					var markup = '<tr><td>' + element.conductorName +
						"</td><td>" +
						calificacion +
						"</td><td>"+
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
			console.info('Connecting to WS...');


			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/pasajeroAceptado." + name, function (conductor) {
				console.log("vamos a eliminar");
				$("#div-table").empty();
				console.log(conductor);
				var conductorData = JSON.parse(conductor.body);
				console.log(conductorData);
				conduc = conductorData;
				stompClient.subscribe("/uniwheels/recibirPosicion."+conduc.conductorName, function (datos){
					console.log(datos.body);
					var position = datos.body.split(",");
					let aux = {"coords":{"latitude":Number(position[0]),"longitude":Number(position[1])}};
					mostrarPosicion(aux);
				});

				stompClient.subscribe("/uniwheels/conductorFinalizado."+conduc.conductorName, function (){
                    console.log("PROBANDO .... SI SIRVE");
                    $(document).ready(function() {
                         console.log("¡Tu vaije terminó");
                         toastr.options = { "positionClass": "toast-bottom-right"};
                         toastr.success('¡Tu vaije terminó. Por favor califica a tu conductor!');
                    });
				});
                 $(document).ready(function() {
                      console.log("¡Tu solicitud fue aceptada!");
                      toastr.options = { "positionClass": "toast-bottom-right"};
                      toastr.success('¡Tu solicitud fue aceptada!');
                 });
				getCali(conductorData.conductorName ,"conductor");
				var card = '<div class="card" style="width: 30rem; text-align: center; background-color: #333333">' +
					'<div class="card-body">' +
					'<h5 class="card-title">' + conductorData.conductorName + '</h5>' +
					'<p class="card-text" style="color:#FFFFFF">El valor de la carrera es de: ' + conductorData.ruta.precio + '.</p>' +
					'<p class="card-text" style="color:#FFFFFF">Información de su carro:</p>' +
					'</div>' +
					'<ul class="list-group list-group-flush" style=" background-color: #333333">' +
					'<li class="list-group-item" style="color:#FFFFFF; background-color: #333333">' + conductorData.carro.marca + '</li>' +
					'<li class="list-group-item" style="color:#FFFFFF; background-color: #333333">' + conductorData.carro.modelo + '</li>' +
					'<li class="list-group-item" style="color:#FFFFFF; background-color: #333333">' + conductorData.carro.placa + '</li>' +
					'<li class="list-group-item" style="color:#FFFFFF; background-color: #333333">' + calificaciones[calificaciones.length-1] + '</li>' +
					'</ul>' +
					'<div class="card-body">' +
					'</div>' +
					'</div>';
					
				
				var markup2 = '<div class="inner">'+
					'<br></br>'+
					'<header>'+
						'<h2>Mi Condutor</h2>'+
					'</header>'+
					'<br></br>'+
					'<div class="container">'+
						'<div class="row">'+
							'<div class="col">'+
								'<div id="card">'+
									card+
								'</div>'+
							'</div>'+
							'<div class="col">'+
								'<div id="map">'+
								'</div>'+
							'</div>'+
						'</div>'+
					'</div>'+
					'</div>'+
					'<br></br>';

				$("#div-table").append(markup2);
				var puntuacion = '<div class="inner">'+
						'<br></br>'+
						"<select id='numeros' name='numeros'>"+
							"<option value='5'>5</option>"+
							"<option value='4'>4</option>"+
							"<option value='3'>3</option>"+
							"<option value='2'>2</option>"+
							"<option value='1'>1</option>"+
						"</select>" +
						'<div>' +
							"<td><form><button type='button' onclick='app.agregarPuntuacion($(\"#numeros\").val())' >Calificar</button></form></td>" +
						'</div>'+
					'<br></br>'+
					'</div>';
				$("#div-table").append(puntuacion);

				console.log("vamos a poner el mapa");
				misCoordenadas();
			});
			stompClient.subscribe("/obtenerPasajeroEnViaje");
			stompClient.send("/app/obtenerPasajeroEnViaje");

			stompClient.subscribe("/uniwheels/pasajeroRechazado." + name, function (conductor) {
				/*
				$("#alertas").empty();
				var conductorData = JSON.parse(conductor.body);
				var alerta = '<div class="alert alert-info" role="alert">'+
					'Disculpa, tu solicitud fue rechazada por ' +conductorData.conductorName + '. Intenta nuevamente o con otro conductor' +
					'</div>';
				$("#alertas").append(alerta);

				*/
				var conductorData = JSON.parse(conductor.body);
                 $(document).ready(function() {
                      console.log("Viaje rechazado por "+conductorData.conductorName);
                      toastr.options = { "positionClass": "toast-bottom-right"};
                      toastr.info('Disculpa, '+conductorData.conductorName+" no puede aceptar tu solicitud. Intenta nuevamente con otro conductor!!");
                 });

			});
		});
	};
	var agregarPuntuacion = function (punt) {
		apiclient.agregarPuntuacion(conduc.id,punt);
	};

	var pasajeroAceptado = function () {
		console.info('Connecting to WS...');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/pasajeroAceptado."+name,function(conductor){
				console.log("vamos a eliminar");
				$("#div-table").empty();
				console.log(conductor);
				var conductorData = JSON.parse(conductor.body);
				console.log(conductorData);

				var card = '<div class="card" style="width: 30rem; text-align: center; background-color: #333333">'+
					'<img src="../../images/pic01.jpg" class="card-img-top" alt="...">'+
					'<div class="card-body">'+
					'<h5 class="card-title">'+conductorData.conductorName+'</h5>'+
					'<p class="card-text" style="color:#FFFFFF">El valor de la carrera es de: '+conductorData.ruta.precio+'.</p>'+
					'<p class="card-text" style="color:#FFFFFF">Información de su carro:</p>'+
					'</div>'+
					'<ul class="list-group list-group-flush" style=" background-color: #333333">'+
					'<li class="list-group-item" style="color:#FFFFFF; background-color: #333333">'+conductorData.carro.marca+'</li>'+
					'<li class="list-group-item" style="color:#FFFFFF; background-color: #333333">'+conductorData.carro.modelo+'</li>'+
					'<li class="list-group-item" style="color:#FFFFFF; background-color: #333333">'+conductorData.carro.placa+'</li>'+
					'</ul>'+
					'<div class="card-body">'+
					'</div>'+
					'</div>';
					
				var markup2 = '<div id="div-table" class="inner">'+
					'<br></br>'+
					'<header>'+
						'<h2>Mi Condutor</h2>'+
					'</header>'+
					'<br></br>'+
					'<div class="container">'+
						'<div class="row">'+
							'<div class="col">'+
								'<div id="card">'+
									card+
								'</div>'+
							'</div>'+
							'<div class="col">'+
								'<div id="map">'+
								'</div>'+
							'</div>'+
						'</div'>+
					'</div>';
					
				$("#div-table").append(markup2);
				//Agregar etiqueta donde llama al mapa 
			});
		});
	}
	
	
	var agregarPosiblePasajero = function(conductorName){
		console.info('Connecting to WS...');

		stompClient = Stomp.over(socket);

		stompClient.send("/app/agregarPosiblePasajero",{},conductorName);

	};
	var getCali = function(name,tipo){
		apiclient.getPuntuacion(name,tipo);
	}

	return{	
	    getConductores: getConductores,
		agregarPosiblePasajero:agregarPosiblePasajero,
		get:get,
		infoViaje: infoViaje,
		pasajeroAceptado:pasajeroAceptado,
		agregarPuntuacion:agregarPuntuacion,
		getCali:getCali
	};
	
})();
