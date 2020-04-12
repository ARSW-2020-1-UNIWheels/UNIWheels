var apiclient = (function(){

	 var agregarUsuario = (function (info) {
		console.log("estamos en el client");
        axios({
            method: 'POST',
            url: '/uniwheels/addUsuario/',
			data: JSON.stringify(info)
	
        })			
			.then(response => {
				location.href = "../Menu/menu.html";};
				console.log("AXIOS FUNCIONANDO...");)
            .catch(error => console.log(error));
    });
	
	return{
		agregarUsuario: agregarUsuario
	}

})();
