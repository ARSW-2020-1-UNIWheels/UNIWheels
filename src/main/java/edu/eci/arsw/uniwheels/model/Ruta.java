package edu.eci.arsw.uniwheels.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name="ruta")
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public String direccionOrigen;
    public String direccionDestino;
    public Time tiempoEstimado;
    public int precio;

    public Ruta(){

    }

    public Ruta(String direccionDestino, String direccionOrigen,Time tiempoEstimado, int precio){
        this.direccionDestino = direccionDestino;
        this.direccionOrigen = direccionOrigen;
        this.tiempoEstimado = tiempoEstimado;
        this.precio = precio;
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

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
