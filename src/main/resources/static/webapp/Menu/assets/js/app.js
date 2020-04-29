var app = (function(){
	
	console.log("vamos en el app");

	class Conductor{
		constructor(){
		}
	}

	//window.onload() = getUser;

	var stompClient = null;
	
	var init = function(){
		apiclient.getCarros(verificar);
		// Despues llamar a addConductor
		//addConductor
	}

	var addConductor = function(){
		//var lista = apiclient.añadirConductorDisponible();
		console.info('Connecting to WS...');
		location.href = "../Conductor/conductor.html";

		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			alert("aqui entra");
			console.log('Connected: ');
			stompClient.send("/app/nuevoConductor",{},JSON.stringify(new Conductor()));
		});

	};
	
	var _getUser = function(info){
		$("#nombreUsuario >h1").text("Bienvenido "+ info.username);
	};

	function get(){
		apiclient.getUser(_getUser)
	}
	
	var verificar = function(info){
		if(info.length ==0){
			alert(" ¡Para Ofrecer un WHEELS debes registrar un carro!");
		}
		else{
			addConductor();
		}
	};

	return{	
	    addConductor: addConductor,
		_getUser: _getUser,
		get:get,
		init: init
	};
	
})();


