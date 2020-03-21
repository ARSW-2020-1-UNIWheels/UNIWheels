package edu.eci.arsw.uniwheels.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name="conductor")
public class Conductor {
    @Id
    public int id;
    public Time tiempoRecorrido;
    @OneToOne
    public Carro carro;
    @OneToMany
    public List<Pasajero> pasajeros;
    @OneToOne
    public Ruta ruta;
    @OneToOne
    public Calificacion calificacion;

    public Conductor(){

    }
    public Conductor(int id,Time tiempoRecorrido){
        this.id = id;
        this.tiempoRecorrido = tiempoRecorrido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTiempoRecorrido() {
        return tiempoRecorrido;
    }

    public void setTiempoRecorrido(Time tiempoRecorrido) {
        this.tiempoRecorrido = tiempoRecorrido;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }
}
