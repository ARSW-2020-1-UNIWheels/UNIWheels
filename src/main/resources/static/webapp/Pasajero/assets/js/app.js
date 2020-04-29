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
				console.log("Entramos al socket");
				var conductoresData = JSON.parse(conductores.body);
				alert(conductoresData);
				$("#tableConductoresDisponibles > tbody").empty();
				conductoresData.map(function(element){
					console.log(element);
					$("#tableConductoresDisponibles > tbody").append(
						"<tr> <td>" +
						element.conductorName +
						"</td>" +
						"<td>" +
						"carro" +
						"</td> " +
						"<td>"+
						"origen"+
						"</td>"+
						"<td>"+
						"destino"+
						"<td">+
						"precio"+
						"</td>"+
						"<td><form><button type='button' onclick='apiclient.agregarPosibleConductor("+"\""+element.conductorName+"&quot)' >Agregar</button></form></td>" +
						"</tr>"
					);
				});
			});
			console.log(infoViaje());
			console.log(typeof(infoViaje()));
			stompClient.send("/app/conductoresDisponibles/"+infoViaje()+"/");
		});
	};

	return{	
	    getConductores: getConductores

	};
	
})();
