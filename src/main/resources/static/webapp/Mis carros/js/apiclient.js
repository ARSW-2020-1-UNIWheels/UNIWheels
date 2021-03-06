var apiclient = (function(){

	 var getCarros = (function (callback) {
		console.log("estamos en el client");
        axios({
            method: 'GET',
            url: '/uniwheels/getCarros/',
        })			
			.then(response => callback(response.data))
            .catch(error => console.log(error));
    });

	var deleteCarro = (function (carro) {
		console.log("estamos en el client para eliminar");
        axios({
            method: 'DELETE',
            url: '/uniwheels/deleteCarro/'+carro+"/'",
			data: null,
			headers: {
				'content-type': 'application/json',
			}
        })			
			.then(response => console.log(response.data))
            .catch(error => console.log(error));
    });

	
	return{
		getCarros: getCarros,
		deleteCarro: deleteCarro
	}

})();