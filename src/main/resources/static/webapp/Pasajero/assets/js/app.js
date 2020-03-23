var app = (function(){


	var getConductores = function(){
		console.log("vamos a consultar");
		var lista = apiclient.getConductoresDisponibles(addConductores);
		
	};
	
	var addConductores = function(conductores){
		
		//$("#cond").empty();
		conductores.map(function(element){
			//var seccion = "<tr> <td>"+ element.code +"</td> <td>"+element.name+"</td> <td>"+ element.city +"</td> <td>"+ element.countryCode +"</td> </tr>";
            /*var seccion = "<div class="inner">"+
							"<article>"+
								"<div class="content">"+
									"<div class="image fit">"+
										"<img src="/webapp/Principal/images/logo.jpg" alt="" />"+
									"</div>"+
								"</div>"+
							"</article>"+
							"<article class="alt">"+
								"<div class="content">"+	
									"<header>"+
										"<h3>"+Conductor+"</h3>"+
									"</header>"+
									"<div class="image fit">"+
									"</div>"+
									"<p>Somos una aplicación que te da la oportunidad de llegar a tu casa o a tu universidad fácil y comodamente. Interactuando con miembros de la
										comunidad universitaria de Bogotá, donde tu decides si quieres ser conductor o ser pasajero.
									</p>"+
								"</div>"+
							"</article>"+
						"</div>";
						*/
			var seccion  = "<h1>"+prueba+"</h1>";
			$("#cond").append(seccion);
		}
	};
	return{	
	    getConductores: getConductores;
	};
	
})();
