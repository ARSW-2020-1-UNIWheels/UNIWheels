package edu.eci.arsw.uniwheels.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.websocket.OnOpen;

@Entity
@Table(name="calificacion")
public class Calificacion {
    @Id
    public int id;
    public int valor;
    public Calificacion(){

    }
    public Calificacion(int id, int valor){
        this.id = id;
        this.valor = valor;
    }

}
