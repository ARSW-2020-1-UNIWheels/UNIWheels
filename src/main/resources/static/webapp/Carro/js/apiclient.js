var apiclient = (function(){

	 var agregarCarro = (function (info) {
		console.log("estamos en el client");
        axios({
            method: 'POST',
            url: '/uniwheels/addCarro/',
			data: info,
			headers: {
				'content-type': 'application/json'
			}
	
        })			
			.then(response => {
				location.href = "../Conductor/conductor.html";
				console.log("AXIOS FUNCIONANDO...");})
            .catch(error => console.log(error));
    });
	
	return{
		agregarCarro: agregarCarro
	}

})();