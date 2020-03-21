package edu.eci.arsw.uniwheels.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table(name="ruta")
public class Ruta {
    @Id
    public int id;
    public String direccionOrigen;
    public String direccionDestino;
    public Time tiempoEstimado;

    public Ruta(){

    }

    public Ruta(int id,String direccionDestino, String direccionOrigen,Time tiempoEstimado){
        this.id = id;
        this.direccionDestino = direccionDestino;
        this.direccionOrigen = direccionOrigen;
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getDireccionOrigen() {
        return direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public Time getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Time tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
