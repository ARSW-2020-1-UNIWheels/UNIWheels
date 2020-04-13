var app = (function(){
	
	console.log("vamos en el app");

	class Conductor{
		constructor(){
		}
	}

	//window.onload() = getUser;

	var stompClient = null;



	var addConductor = function(){
		//var lista = apiclient.añadirConductorDisponible();
		console.info('Connecting to WS...');

		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			alert("aqui entra");
			console.log('Connected: ');
			stompClient.send("/app/nuevoConductor",{},JSON.stringify(new Conductor()));
		});

	};
	
	
	var getUser = function(){
		console.log("vamos a poner nombre");
		var usuario = apiclient.getUser();
		//var usuario = "orlando";
		console.log("vamos a poner el usuario");
		console.log(usuario);
		$("#nombreUsuario >h1").text("Bienvenido "+ usuario);
	};

	return{	
	    addConductor: addConductor,
		getUser: getUser
	};
	
})();


