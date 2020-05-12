package edu.eci.arsw.uniwheels.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.websocket.OnOpen;

@Entity
@Table(name="calificacion")
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public int valor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public Conductor conductor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public Pasajero pasajero;

    public Calificacion(){

    }
    public Calificacion( int valor){
        this.valor = valor;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
