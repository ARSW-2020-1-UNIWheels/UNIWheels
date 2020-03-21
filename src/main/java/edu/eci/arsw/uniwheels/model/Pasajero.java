package edu.eci.arsw.uniwheels.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "pasajero")
public class Pasajero {
    @Id
    public int id;
    public Time tiempoRecorrido;
    @OneToOne
    public Calificacion calificacion;
    @OneToOne
    public Ruta ruta;

    public Pasajero(){

    }
    public Pasajero(int id,Time tiempoRecorrido){
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
}
