
var apiclient = (function(){

    var getBlueprintsByAuthor = (function (callback,author) {
        var success = false;
        $.getJSON("http://localhost:8080/blueprints/"+author,function(data){
            success=true;
            callback(data);

        },null);
        setTimeout(function() {
            if (!success) {
                alert("El autor que estas colocando no existe");
            }
        }, 60);
    })

    var getBlueprintsByNameAndAuthor = (function(name, author, callback) {
            $.getJSON("http://localhost:8080/blueprints/"+author+"/"+name,function(data){
                callback(data);
            },null);
        }

    )


    return {
        getBlueprintsByAuthor: getBlueprintsByAuthor ,
        getBlueprintsByNameAndAuthor: getBlueprintsByNameAndAuthor
    }
})();