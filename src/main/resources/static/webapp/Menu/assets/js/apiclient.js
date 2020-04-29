var apiclient = ( function () {

    var añadirConductorDisponible = (function () {

        axios({
            method: 'POST',
            url: '/uniwheels/addConducDispo',

        })
            .catch(error => console.log(error));
    });
	
	var getUser = (function (callback) {
		console.log("estamos en el axios");
		axios({
            method: 'GET',
            url: '/auth/getUser',

        })
			.then(response => callback(response.data))
            .catch(error => console.log(error));

	});	
	
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
        añadirConductorDisponible: añadirConductorDisponible,
		getUser: getUser,
		getCarros: getCarros
    }
})();




