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

    var agregarPuntuacion = (function (id,val) {
        axios({
            method: 'POST',
            url: '/uniwheels/addValoracion/'+id+"/"+0+"/"+val,

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

    var agregarPosicion = (function (coordenadas,name) {
        axios({
            method: 'PATCH',
            url: '/uniwheels/putLocalization/'+name+"/"+coordenadas,

        })
            .catch(error => console.log(error));
    });

    return{
        getConductoresDisponibles: getConductoresDisponibles,
        agregarPosibleConductor:agregarPosibleConductor,
        getUser:getUser,
        agregarPuntuacion:agregarPuntuacion,
        getPuntuacion:getPuntuacion,
        agregarPosicion:agregarPosicion
    }
})();