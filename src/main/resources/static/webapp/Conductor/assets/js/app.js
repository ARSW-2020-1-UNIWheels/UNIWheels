var app = (function(){

	var placa;
	var name;
	var calificaciones= new Array();
	var socket=new SockJS('/stompendpoint');
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
    var posiciones = new Array;

	let options = {
		enableHighAccuracy: true,
		timeout: 5000,
		maximumAge: 0
	};

	function misCoordenadas(){
			console.log("calculando coordenadas");
			navigator.geolocation.watchPosition(app.enviarPosicion,null,options);
	};

	function enviarPosicion(position){
		posiciones[0] = {"latitud":position.coords.latitude,"longitud":position.coords.longitude,"title":"Conductor"};
		plotMarkers(posiciones);
		let datos = position.coords.latitude+','+position.coords.longitude;
		stompClient.send("/app/ofrecerPosicion."+name,{},datos);

	};

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
		stompClient = Stomp.over(socket);
		console.log('Connected: ');
		stompClient.send("/app/nuevoConductor",{},JSON.stringify(new Ruta($("#ubicacionActual").val(),$("#destino").val(),parseInt($("#precio").val(),10)))+$("#carro").val());

         $(document).ready(function() {
              console.log("¡Tu viaje a iniciado!");
              toastr.options = { "positionClass": "toast-bottom-right"};
              toastr.success('¡Tu viaje a iniciado!');
         });

        /*
         var boton = '<button class="login100-form-btn" onclick="app.terminarViaje()" id="finalizar">Finalizar</button>'
         $("#boton-fin").append(boton);
         */

	};

	var infoViaje = function(){
	    var inicio = $("#ubicacionActual").val();
	    var destino = $("#destino").val();
	    var carro = $("#carro").val();
		var precio = $("#precio").val();

	    console.log(inicio+" "+destino+" "+carro+" "+precio);
		console.log(typeof(inicio)+" "+typeof(precio));

		if(inicio==="Donde otás?" || destino==="Para donde vas?" || carro==="Que carro vas a usar?" || precio===""){
			//alert("Debes ingresar todos los datos para iniciar tu viaje!!");
			$(document).ready(function() {
                  console.log("Debes ingresar todos los datos para iniciar tu viaje!!");
                  toastr.options = { "positionClass": "toast-bottom-right"};
                  toastr.info('Debes ingresar todos los datos para iniciar tu viaje!!');
             });
		}
		else if( inicio==destino){
            $(document).ready(function() {
                  console.log("Tu punto de destino debe ser diferente a tu viaje!!");
                  toastr.options = { "positionClass": "toast-bottom-right"};
                  toastr.info('Tu punto de destino debe ser diferente a tu viaje!!');
             });
		}
		else{
			addConductor();
		}
		desabilitar(true);

	};

	var addPasajeros = function(){
		stompClient.subscribe("/uniwheels/pasajero."+name, function (pasajeros){
			//console.log(pasajeros);
			var pasajerosData = JSON.parse(pasajeros.body);

			console.log(pasajeros);
			$("#pasajerosAceptados").empty();
			pasajerosData.map(function(element){
				getCali(element.usuario.username,"pasajero");
				//alert(calificacion);
				var markup = "<tr> <td>" +
					element.usuario.username +
					"</td>" +
					"<td>" +
					element.usuario.universidad +
					"</td>" +
					"<td>" +
					calificaciones[calificaciones.length-1] +
					"</td>"

				$("#pasajerosAceptados").append(markup);
				console.log(calificaciones[calificaciones.length-1]);
			});

		});
		stompClient.send("/app/recibirPasajeros");

	};
	
	var aceptarPasajero = function (pasajero,estado) {
		console.log("vamos a enviar el nombre "+pasajero+" "+estado);
		addPasajeros();

		stompClient = Stomp.over(socket);
		stompClient.send("/app/agregarPasajero." + name, {}, pasajero.id +","+ estado);

	};

	var desabilitar = function (data) {
		$("#ubicacionActual").attr("disabled",data);
		$("#destino").attr("disabled",data);
		$("#carro").attr("disabled",data);
		$("#precio").attr("disabled",data);
		$("#iniciar").attr("disabled",data);
	};


	var addSolicitudes = function(){
		get();
		apiclient.getExisteConductor(desabilitar);
		console.info('Connecting to WS...');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/posiblesConductores."+name, function (conductores) {
				var conductoresData = JSON.parse(conductores.body);
				$("#tableSolicitudes > tbody").empty();
				conductoresData.map(async function(element){
					let data = await fetch('/uniwheels/getValoracion/'+element.usuario.username+"/pasajero");
					let calificacion = await data.json();
					console.log(element);
					getCali(element.usuario.username,"conductor");
					console.log(calificaciones);
					var markup = "<tr> <td>" +
						element.usuario.username +
						"</td>" +
						"<td>" +
						element.usuario.universidad +
						"</td>"+
						"<td>" +
						calificacion +
						"</td>"+
						"<td><form><button type='button' onclick='app.aceptarPasajero("+JSON.stringify(element)+",\"true\")' >Aceptar</button></form></td>" +
						"<td><form><button type='button' onclick='app.aceptarPasajero("+JSON.stringify(element)+",\"false\")' >Rechazar</button></form></td>" +
						"</tr>";
					$("#tableSolicitudes > tbody").append(markup);
				});
			});
			stompClient.subscribe("/uniwheels/pasajero."+name, function (pasajeros){
				var pasajerosData = JSON.parse(pasajeros.body);
				console.log(pasajeros);
				$("#pasajerosAceptados").empty();
				$("#calificaciones").empty();
				calificaciones = new Array();


				toastr.options = { "positionClass": "toast-bottom-right"};
				toastr.info('Danos un minuto mientras cargamos la información');
				pasajerosData.map(async function(element){
					let data = await fetch('/uniwheels/getValoracion/'+element.usuario.username+"/pasajero");
					let calificacion = await data.json();
					console.log(calificaciones);
					var markup = "<tr> <td>" +
						element.usuario.username +
						"</td>" +
						"<td>" +
						element.usuario.universidad +
						"</td>" +
						"<td>" +
						calificacion +
						"</td>"+
						"</tr>";
					$("#pasajerosAceptados").append(markup);
					var aiuda = $("#numeros:selected").val();
					var a = "<tr> <td>" +
						element.usuario.username +
						"</td>" +
						"<td>" +
						calificacion +
						"</td>" +
						"<td>" +
						"<select id='numeros' name='numeros'>"+
							"<option value='5'>5</option>"+
							"<option value='4'>4</option>"+
							"<option value='3'>3</option>"+
							"<option value='2'>2</option>"+
							"<option value='1'>1</option>"+
						"</select>" +
						"</td>"+
						"<td><form><button type='button' id='"+element.id+"' onclick='app.agregarPuntuacion(\"" +
						aiuda +
						'" , "' +
						element.id +
						"\")' >Calificar</button></form></td>" +
						"</tr>";
					$("#calificaciones").append(a);
				});
				console.log("vamos a poner el mapa");
				setInterval(misCoordenadas,15000);
			});
			stompClient.send("/app/recibirPasajeros");

			stompClient.subscribe("/uniwheels/conductorFinalizado."+name, function (){

			});
		});
	};

	var terminarViaje = function () {
		desabilitar(false);
		//alert("Termino su viaje exitosamente");
         $(document).ready(function() {
              console.log("Termino su viaje exitosamente");
              toastr.options = { "positionClass": "toast-bottom-right"};
              toastr.success('Termino su viaje exitosamente');
         });


		$("#pasajerosAceptados").empty();
		$("#tableSolicitudes > tbody").empty();
		stompClient.send("/app/terminarCarrera."+name);
		//location.href = "../Menu/menu.html";
		//Verifcar que calificó a sus conductores y ahí si finalizar
		//location.href = "../Menu/menu.html";
	};

	var agregarPuntuacion = function (punt,id) {
		apiclient.agregarPuntuacion(id,Math.floor(Math.random() * 5) + 1);
		var a  = "#" + id;
		$(a).attr("disabled",true);
	};



	var getCali = function(name,tipo){
		 apiclient.getPuntuacion(name,tipo);
	}

	return{	
	    getPasajeros: getPasajeros,
		addPasajeros: addPasajeros,
		addSolicitudes: addSolicitudes,
		getCarros: getCarros,
		addCarros: addCarros,
		infoViaje: infoViaje,
		addConductor: addConductor,
		get:get,
		aceptarPasajero:aceptarPasajero,
		desabilitar:desabilitar,
		terminarViaje:terminarViaje,
		agregarPuntuacion:agregarPuntuacion,
		misCoordenadas:misCoordenadas,
		enviarPosicion:enviarPosicion,
		getCali:getCali,
		calificaciones:calificaciones
	};
	
})();
