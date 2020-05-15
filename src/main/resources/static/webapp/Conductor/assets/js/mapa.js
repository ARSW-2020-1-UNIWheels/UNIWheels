document.addEventListener('DOMContentLoaded', function () {
    if (document.querySelectorAll('#map').length > 0)
    {
        if (document.querySelector('html').lang)
            lang = document.querySelector('html').lang;
        else
            lang = 'en';

        var js_file = document.createElement('script');
        js_file.type = 'text/javascript';
        js_file.src = 'https://maps.googleapis.com/maps/api/js?callback=initMap&key=AIzaSyCSUb7IAuFKhlL_F9aLzOFJ7lxd66u_ZaQ&language=' + lang;

        document.getElementsByTagName('head')[0].appendChild(js_file);
    }
});

var map;

function initMap()
{
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 8
    });


}

var markers;
var bounds;

function plotMarkers(m)
{
    initMap();
    markers = [];
    bounds = new google.maps.LatLngBounds();

    m.forEach(function (marker) {

        var position = new google.maps.LatLng(marker.latitud, marker.longitud);

        markers.push(
            new google.maps.Marker({
                position: position,
                map: map,
                animation: google.maps.Animation.DROP,
                title:marker.title,
                label:marker.title
            })
        );

        bounds.extend(position);

    });

    map.fitBounds(bounds);
}