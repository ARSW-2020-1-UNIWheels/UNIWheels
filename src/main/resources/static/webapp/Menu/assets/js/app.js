var app = (function(){
	
	console.log("vamos en el app");


	//toastr["success"]("Hola que tal?", "Prueba");
	
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
<<<<<<< HEAD
			alert(" ¡Para Ofrecer un WHEELS debes registrar un carro!");
			putAlert(" ¡Para Ofrecer un WHEELS debes registrar un carro!");
=======
			//alert(" ¡Para Ofrecer un WHEELS debes registrar un carro!");
             $(document).ready(function() {
                  console.log("¡Para Ofrecer un WHEELS debes registrar un carro!");
                  toastr.options = { "positionClass": "toast-bottom-right"};
                  toastr.info('¡Para Ofrecer un WHEELS debes registrar un carro!');
             });
>>>>>>> 694493eb10353bdd347af73eef960fb509bc2c92
		}
		else{
			location.href ="../Conductor/conductor.html";
		}
	};
	
	
	var putAlert = function(message){
		
		/*
		var markup = '<div class="alert alert-warning alert-dismissible fade show" role="alert">'+
			'<strong>Atencion!</strong>'+message+'.'+
			'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'+
				'<span aria-hidden="true">&times;</span>'+
			'</button>'+
			'</div>';
		
		document.body.innerHTML(markup);
		*/
		console.log("jejej");
		toastr.info(message);
	}

	return{	
		putAlert: putAlert,
		_getUser: _getUser,
		get:get,
		init: init
	};
	
})();


