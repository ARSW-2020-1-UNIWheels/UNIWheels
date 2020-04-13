package edu.eci.arsw.uniwheels.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

import java.sql.Time;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="conductor")
@DynamicUpdate
@DynamicInsert
@Transactional
public class Conductor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public int tiempoRecorrido;
    @OneToOne
    public Carro carro;
    @OneToMany
    @JsonIgnore
    public List<Pasajero> pasajeros;
    @OneToOne
    public Ruta ruta;
    @OneToOne
    public Calificacion calificacion;
    public String nombreEstado;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "conductor")
    @JsonIgnore
    public Set<Pasajero> posiblesPasajeros;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name="usuario")
    @JsonIgnore
    public Usuario usuario;

    public String conductorName;
    public Conductor(){

    }
    public Conductor(int tiempoRecorrido){
        this.tiempoRecorrido = tiempoRecorrido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempoRecorrido() {
        return tiempoRecorrido;
    }

    public void setTiempoRecorrido(int tiempoRecorrido) {
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

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public void addPasajero(Pasajero pasajero){
        this.pasajeros.add(pasajero);
    }

    public Set<Pasajero> getPosiblesPasajeros() {
        return posiblesPasajeros;
    }

    public void setPosiblesPasajeros(Set<Pasajero> posiblesPasajeros) {
        this.posiblesPasajeros = posiblesPasajeros;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConductorName() {
        return conductorName;
    }

    public void setConductorName(String conductorName) {
        this.conductorName = conductorName;
    }

}
