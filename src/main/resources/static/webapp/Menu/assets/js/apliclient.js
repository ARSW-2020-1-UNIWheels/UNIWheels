var apiclient = ( function () {

    var añadirConductorDisponible = (function () {

        axios({
            method: 'POST',
            url: '/uniwheels/addConducDispo',

        })
            .catch(error => console.log(error));
    });

    return{
        añadirConductorDisponible: añadirConductorDisponible,
    }
})();