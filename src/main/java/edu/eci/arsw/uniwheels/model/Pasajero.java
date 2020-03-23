package edu.eci.arsw.uniwheels.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "pasajero")
public class Pasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public Time tiempoRecorrido;
    @OneToOne
    public Calificacion calificacion;
    @OneToOne
    public Ruta ruta;
    @OneToOne
    public Conductor conductor;
    public String nombreEstado;

    public Pasajero(){

    }
    public Pasajero(Time tiempoRecorrido, Conductor conductor, Ruta ruta, Calificacion calificacion, String nombreEstado){
        this.tiempoRecorrido = tiempoRecorrido;
        this.conductor = conductor;
        this.ruta = ruta;
        this.calificacion = calificacion;
        this.nombreEstado = nombreEstado;
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

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Calificacion calificacion) {
        this.calificacion = calificacion;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
