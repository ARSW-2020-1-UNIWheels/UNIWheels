var app = (function(){

	var getConductores = function(){
		alert("aqui si");
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/uniwheels/conductoresDisponibles", function (conductores) {
				var conductoresData = JSON.parse(conductores.body);
				alert(conductoresData);
				$("#tableConductoresDisponibles > tbody").empty();
				conductoresData.map(function(element){
					$("#tableConductoresDisponibles > tbody").append(
						"<tr> <td>" +
						element.conductorName +
						"</td>" +
						"<td>" +
						element.tiempoRecorrido +
						"</td> " +
						"<td><form><button type='button' onclick='apiclient.agregarPosibleConductor("+"\""+element.conductorName+"&quot)' >Agregar</button></form></td>" +
						"</tr>"
					);
				});
			});
			stompClient.send("/app/conductoresDisponibles");
		});
	};

	return{	
	    getConductores: getConductores

	};
	
})();
