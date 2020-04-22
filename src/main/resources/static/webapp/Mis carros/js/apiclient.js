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
	
	return{
		getCarros: getCarros
	}

})();