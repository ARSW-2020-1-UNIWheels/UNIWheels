var app = (function(){
	
	console.log("vamos en el app");


	
	var _getUser = function(info){
		$("#nombreUsuario >h1").text("Bienvenido "+ info.username);
	};

	function get(){
		apiclient.getUser(_getUser)
	}

	return{	

		_getUser: _getUser,
		get:get
	};
	
})();


