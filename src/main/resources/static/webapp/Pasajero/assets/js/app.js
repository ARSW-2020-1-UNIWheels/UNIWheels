document.addEventListener('DOMContentLoaded', function () {
  if (document.querySelectorAll('#map').length > 0)
  {
	if (document.querySelector('html').lang)
	  lang = document.querySelector('html').lang;
	else
	  lang = 'en';

	var js_file = document.createElement('script');
	js_file.type = 'text/javascript';
	js_file.src = 'https://maps.googleapis.com/maps/api/js?callback=initMap&AIzaSyCuKZ_scrWAaiekIHPLWfXHuDhgZJKUpvM&language=' + lang;
	document.getElementsByTagName('head')[0].appendChild(js_file);
  }
});



var app = (function(){

	var name;
	var conduc;
	var socket = new SockJS('/stompendpoint');



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
			console.info('Connecting to WS...');


			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/pasajeroAceptado." + name, function (conductor) {
				console.log("vamos a eliminar");
				$("#div-table").empty();
				console.log(conductor);
				var conductorData = JSON.parse(conductor.body);
				console.log(conductorData);
				conduc = conductorData;
				stompClient.subscribe("/uniwheels/conductorFinalizado."+conduc.conductorName, function (){

				});
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
								'<div id="map" class="image fit">'+
									'<img src="/webapp/Pasajero/images/mapa.jpg" width="100%" height="80%" />'+
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
			stompClient.send("/app/obtenerPasajeroEnViaje")
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
					'<a href="#" class="card-link" onclick="alert("Metodo en construcción")">Cancelar viaje</a>'+
					'</div>'+
					'</div>';
					
				var markup2 = '<div id="div-table" class="inner">'
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
									'<h1>AQUI VA EL MAPA</h1>'+
									card+
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
	
	
	var map;

	function initMap(){
		map = new google.maps.Map(document.getElementById('map'), {
		center: {lat: -34.397, lng: 150.644},
		zoom: 8
		});
	};


	var markers;
	var bounds;

	function plotMarkers(m){
		initMap();	
		markers = [];
		bounds = new google.maps.LatLngBounds();
		
		console.log(m);
		console.log(typeof(m));
		m.map(function(info){
			console.log(info);
		});

		m.forEach(function (marker) {
		var position = new google.maps.LatLng(marker.lat, marker.lng);

		console.log("estamos en el map");
		markers.push(
		  new google.maps.Marker({
			position: position,
			map: map,
			animation: google.maps.Animation.DROP
		  })
		);



		bounds.extend(position);
		});

		map.fitBounds(bounds);
	};
	
	function misCoordenadas(){
		console.log("calculando coordenadas");
		navigator.geolocation.watchPosition(mostrarPosicion);
	};
	
	function mostrarPosicion(position){
		console.log(position.coords.latitude+" "+position.coords.longitude);
		console.log(typeof(position.coords.latitude));
		var datos = {"lat":position.coords.latitude,"lng":position.coords.longitude};
		var datos_arr = JSON.parse(datos.body);
		plotMarkers(datos_arr);
	}

	return{	
	    getConductores: getConductores,
		agregarPosiblePasajero:agregarPosiblePasajero,
		get:get,
		infoViaje: infoViaje,
		pasajeroAceptado:pasajeroAceptado,
		agregarPuntuacion:agregarPuntuacion
	};
	
})();
