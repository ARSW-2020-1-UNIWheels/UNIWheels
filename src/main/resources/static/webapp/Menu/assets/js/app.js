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
			location.href ="../Conductor/conductor.html";
		}
	};

	return{	

		_getUser: _getUser,
		get:get,
		init: init
	};
	
})();


