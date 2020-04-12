var app = (function(){

	var getConductores = function(){
		alert("aqui si");
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function () {
			console.log('Connected: ');
			stompClient.subscribe("/app/conductoresDisponibles", function (conductores) {
				$("#tableConductoresDisponibles > tbody").empty();
				conductores.map(function(element){
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
		});
	};

	return{	
	    getConductores: getConductores

	};
	
})();
