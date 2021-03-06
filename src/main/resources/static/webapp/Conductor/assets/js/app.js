var app = (function(){

	var name;
	var calificaciones= new Array();
	var socket=new SockJS('/stompendpoint');
	var pasaj = new Array();
    var ubicaciones = new Array();
    var yaSeAgregoConductor = true;


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

	function devolverString(coordenadas) {
		let cadenaDevolver = "";
		let arrayTMP = new Array;
		let i = 0;
		coordenadas.map(function(element){
			let cadenaTMP = element.latitud+","+element.longitud+","+element.title;
			arrayTMP.push(cadenaTMP);
			i++;
		})
		let cadenaTMP = arrayTMP.join("|");
		return cadenaTMP;
	}

	function enviarPosicion(position){
	    if(yaSeAgregoConductor) {
            ubicaciones.push({
                "latitud": position.coords.latitude,
                "longitud": position.coords.longitude,
                "title": "Conductor"
            });
            yaSeAgregoConductor = false;
        }
		let datosConductor = position.coords.latitude+','+position.coords.longitude;
        pasaj.map( async function(element) {
            if(ubicaciones.find(iterator => iterator.title === element) ===undefined) {
                let datos = await fetch('/uniwheels/getLocalization/'+element);
                let datosJSON = JSON.parse(JSON.stringify(await datos.json()));
                let dataTMP = {"latitud":datosJSON.latitud,"longitud":datosJSON.longitud,"title":element}
                ubicaciones.push(dataTMP);
            }
        });

        plotMarkers(ubicaciones);

        let cadenaTMP = devolverString(ubicaciones);
		stompClient.send("/app/ofrecerPosicion."+name,{},cadenaTMP);

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

		if(inicio==="Donde estás?" || destino==="Para donde vas?" || carro==="Que carro vas a usar?" || precio===""){
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
			desabilitar(true);
		}

	};

	
	var aceptarPasajero = function (pasajero,estado) {
		console.log("vamos a enviar el nombre "+pasajero+" "+estado);

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
			stompClient.subscribe("/uniwheels/posiblesConductores."+name, async function (conductores) {
				let conductoresData = JSON.parse(conductores.body);
				await $("#tableSolicitudes > tbody").empty();
				conductoresData.map(async function(element){
					let data = await fetch('/uniwheels/getValoracion/'+element.usuario.username+"/pasajero");
					let calificacion = await data.json();
					console.log("Entre en posible conductor");
					console.log(element);
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
			stompClient.subscribe("/uniwheels/pasajero."+name, async function (pasajeros){
				var pasajerosData = JSON.parse(pasajeros.body);
				console.log(pasajeros);
				await $("#pasajerosAceptados").empty();
				$("#calificaciones").empty();
				calificaciones = new Array();
                pasaj = new Array()
				toastr.options = { "positionClass": "toast-bottom-right"};
				toastr.info('Danos un minuto mientras cargamos la información');
				pasajerosData.map(async function(element){
					let data = await fetch('/uniwheels/getValoracion/'+element.usuario.username+"/pasajero");
					let calificacion = await data.json();
					console.log(calificaciones);
                    pasaj.push(element.usuario.username);
                    //alert(pasaj);
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
					misCoordenadas();
				});
				console.log("vamos a poner el mapa");

				setInterval(misCoordenadas,30000);
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
		calificaciones:calificaciones,
		pasaj:pasaj
	};
	
})();
