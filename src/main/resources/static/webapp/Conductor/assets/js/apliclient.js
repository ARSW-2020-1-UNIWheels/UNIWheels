var apiclient = ( function () {

	/* Implementar API de conductor !! */ 
	
    var getPasajeros = (function (callback) {

	
        axios({
            method: 'GET',
            url: '/uniwheels/getPasajeros',

        })
            .then(response => callback(response.data))
            .catch(error => console.log(error));
    });
	
	var getSolicitudesPasajeros = (function (callback) {

        axios({
            method: 'GET',
            url: '/uniwheels/getSolicitudesPasajeros',

        })
            .then(response => callback(response.data))
            .catch(error => console.log(error));
    });

    var getCarros = (function (callback) {

        axios({
            method: 'GET',
            url: '/uniwheels/getCarros',

        })
            .then(response => callback(response.data))
            .catch(error => console.log(error));
    });

	/*
    var agregarPasajero = (function (username) {

        axios({
            method: 'POST',
            url: '/uniwheels/addPassanger/'+username,

        })
            .catch(error => console.log(error));
    });
	*/

    return{
        getPasajeros: getPasajeros,
        getSolicitudesPasajeros: getSolicitudesPasajeros,
        getCarros: getCarros
		/* agregarPasajero: agregarPasajero*/
		
    }
})();