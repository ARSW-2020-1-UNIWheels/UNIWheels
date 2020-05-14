package edu.eci.arsw.uniwheels.model;

public class Ubicacion {
    public float latitude;
    public float longitude;

    public Ubicacion(){

    }
    public Ubicacion(float latitud,float longitud){
        this.latitude = latitud;
        this.longitude = longitud;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
