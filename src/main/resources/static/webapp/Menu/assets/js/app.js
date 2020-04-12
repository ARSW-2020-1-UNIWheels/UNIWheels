var app = (function(){

	class Conductor{
		constructor(){
		}
	}

	var stompClient = null;

	var callback = function (frame) {
		alert("aqui entra");
		console.log('Connected: ');
	};

	var addConductor = function(){
		//var lista = apiclient.añadirConductorDisponible();
		console.info('Connecting to WS...');

		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, callback);
		stompClient.send("/app/nuevoConductor",{},JSON.stringify(new Conductor()));
	};
	

	return{	
	    addConductor: addConductor
	};
	
})();
