var app = (function(){

    var listaImagenes = {};
	
	var getCarro = function(){
		apiclient.getCarros(addCarros);
	};

	var getImagen = function(carro){
	    console.log("vamos a poner la imagen "+carro);

        if(carro in listaImagenes){
            console.log("esta la imagen");
            $("#imagen").empty();
            var img = "<img src="+'"'+carro+".jpg"+'"'+" />";
            $('#imagen').append(img);
        }
        else{
            $("#imagen").empty();
            $("#imagen").append("...");
        }
	};

	var volver = function(){
	    location.href = "../Conductor/conductor.html";
	};

	var añadir = function(){
	    location.href = "../Carro/carro.html";
	};

	var addCarros = function(carros){
	    var imagen = "alaska.jpg";
	    console.log("vamos a añadir el carro");
        $("#tableMis Carros > tbody").empty();
        carros.map(function(element){
            $("#tableMisCarros > tbody").append(
				//var boton = "<button type='button' onclick='apiclient.deleteCarro("+element.id+")"+">Agregar</button>";
                var boton = "<button type='button'">Agregar</button>";
				console.log(boton);
				'<tr> <th scope="row"> </th>'+
                 "<td> " +
                element.marca +
                "</td>" +
                "<td>" +
                element.modelo +
                "</td> " +
                "<td>"+
                element.placa+
                "</td>"+
                //"<td id='imagen'>"+ getImagen(element.modelo)+"</td>" +
				"<td id='imagen'>"+boton+"</td>"+
                "</tr>"
            );
        });

	};
	return{
	    getCarro : getCarro,
	    addCarros: addCarros,
	    getImagen: getImagen,
	    volver: volver,
	    añadir: añadir
	};
})();
