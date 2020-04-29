var app = (function(){

    var infoViaje = function(){
        var inicio = $("#ubicacionActual").val();
        var destino = $("#destino").val();
        console.log(inicio+" "+destino);
        return destino.toString();
    };

	var getConductores = function(){
		alert("aqui si");
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/conductoresDisponibles", function (conductores) {
			    console.log(conductores);
				var conductoresData = JSON.parse(conductores.body);
				//alert(conductoresData);
				$("#tableConductoresDisponibles > tbody").empty();
				conductoresData.map(function(element){
					$("#tableConductoresDisponibles > tbody").append(
						"<tr> <td>" +
						element.conductorName +
						"</td>" +
						"<td>" +
						element.tiempoRecorrido +
						"</td> " +
						"<td><form><button type='button' onclick='app.agregarPosiblePasajero("+"\""+element.conductorName+"&quot)' >Agregar</button></form></td>" +
						"</tr>"
					);
				});
			});
			//console.log(infoViaje());
			//console.log(typeof(infoViaje()));
			stompClient.send("/app/conductoresDisponibles" ,{},$("#destino").val());
		});
	};

	var agregarPosiblePasajero = function(conductorName){
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			alert("aqui entra");
			console.log('Connected: ');
			console.log($("#ubicacionActual").val());

			stompClient.send("/app/agregarPosiblePasajero",{},conductorName);
		});
	};

	return{	
	    getConductores: getConductores,
		agregarPosiblePasajero:agregarPosiblePasajero

	};
	
})();
