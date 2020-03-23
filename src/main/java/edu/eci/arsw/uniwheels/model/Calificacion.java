package edu.eci.arsw.uniwheels.model;

import javax.persistence.*;
import javax.websocket.OnOpen;

@Entity
@Table(name="calificacion")
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public int valor;
    public Calificacion(){

    }
    public Calificacion( int valor){
        this.valor = valor;
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
