var apiclient = ( function () {

    var getUser = (function (callback) {
        console.log("estamos en el axios");
        axios({
            method: 'GET',
            url: '/auth/getUser',

        })
            .then(response => callback(response.data))
            .catch(error => console.log(error));

    });

    var getConductoresDisponibles = (function (callback) {

        axios({
            method: 'GET',
            url: '/uniwheels/getConducDispo',

        })
            .then(response => callback(response.data))
            .catch(error => console.log(error));
    });

    var agregarPosibleConductor = (function (username) {

        axios({
            method: 'POST',
            url: '/uniwheels/addPassanger/'+username,

        })
            .catch(error => console.log(error));
    });

    return{
        getConductoresDisponibles: getConductoresDisponibles,
        agregarPosibleConductor:agregarPosibleConductor,
        getUser:getUser
    }
})();