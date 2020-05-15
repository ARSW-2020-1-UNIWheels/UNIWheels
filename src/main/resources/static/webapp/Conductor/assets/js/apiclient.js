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

    var getExisteConductor = (function (callback) {

        axios({
            method: 'GET',
            url: '/uniwheels/existConductor',

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
    var getUser = (function (callback) {
        console.log("estamos en el axios");
        axios({
            method: 'GET',
            url: '/auth/getUser',

        })
            .then(response => callback(response.data))
            .catch(error => console.log(error));

    });

    var agregarPuntuacion = (function (id,val) {
        axios({
            method: 'POST',
            url: '/uniwheels/addValoracion/'+0+"/"+id+"/"+val,

        })
            .catch(error => console.log(error));
    });

    var getPuntuacion = (function (name,tipo) {
        axios({
            method: 'GET',
            url: '/uniwheels/getValoracion/'+name+"/"+tipo,

        })
            .then(response => app.calificaciones.push(response.data))
            .catch(error => console.log(error));
    });

    return{
        getPasajeros: getPasajeros,
        getSolicitudesPasajeros: getSolicitudesPasajeros,
        getCarros: getCarros,
        getUser:getUser,
        getExisteConductor:getExisteConductor,
        agregarPuntuacion:agregarPuntuacion,
        getPuntuacion:getPuntuacion
		/* agregarPasajero: agregarPasajero*/
		
    }
})();