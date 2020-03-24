var apiclient = ( function () {

    var getConductoresDisponibles = (function (callback) {

        axios({
            method: 'GET',
            url: '/uniwheels/getConducDispo',

        })
            .then(response => callback(response.data))
            .catch(error => console.log(error));
    });

    return{
        getConductoresDisponibles: getConductoresDisponibles,
    }
})();