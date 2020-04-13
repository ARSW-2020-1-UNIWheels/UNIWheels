var apiclient = ( function () {

    var añadirConductorDisponible = (function () {

        axios({
            method: 'POST',
            url: '/uniwheels/addConducDispo',

        })
            .catch(error => console.log(error));
    });
	
	var getUser = (function () {
		console.log("estamos en el axios");
		axios({
            method: 'GET',
            url: '/auth/getUser',

        })
			.then(response => response.data)
            .catch(error => console.log(error));

	});		

    return{
        añadirConductorDisponible: añadirConductorDisponible,
		getUser: getUser
    }
})();




